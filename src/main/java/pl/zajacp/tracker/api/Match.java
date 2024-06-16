package pl.zajacp.tracker.api;

import lombok.NonNull;
import pl.zajacp.tracker.api.exception.IllegalMatchException;

import java.util.Optional;

public record Match
        (@NonNull Team teamA,
         @NonNull Team teamB,
         @NonNull TournamentBracket bracketPosition,
         @NonNull Optional<MatchResult> finishedMatchResult) {
    public static Match of(Team teamA, Team teamB, TournamentBracket bracketPosition) {
        return new Match(teamA, teamB, bracketPosition, Optional.empty());
    }

    public Match finishWithResult(MatchResult result) {
        return new Match(teamA, teamB, bracketPosition, Optional.of(result));
    }

    public Team getWinner() {
        if (finishedMatchResult.isEmpty()) {
            throw new IllegalMatchException("Match is not finished yet");
        }
        if (finishedMatchResult.get().scoreTeamA() != finishedMatchResult.get().scoreTeamB()) {
            return finishedMatchResult.get().scoreTeamA() > finishedMatchResult.get().scoreTeamB()
                    ? teamA
                    : teamB;
        }
        return finishedMatchResult.get().penaltyScoreTeamA().get() > finishedMatchResult.get().penaltyScoreTeamB().get()
                ? teamA
                : teamB;
    }

    public boolean isFinished() {
        return finishedMatchResult.isPresent();
    }

    public Team getLoser() {
        return teamA == getWinner() ? teamB : teamA;
    }

    public Match inverted() {
        return new Match(teamB, teamA, bracketPosition, finishedMatchResult.map(MatchResult::inverted));
    }
}
