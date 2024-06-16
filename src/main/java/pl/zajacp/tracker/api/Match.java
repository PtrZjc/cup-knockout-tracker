package pl.zajacp.tracker.api;

import lombok.NonNull;
import pl.zajacp.tracker.api.exception.IllegalMatchException;

import java.util.Optional;

public record Match
        (@NonNull Team teamA,
         @NonNull Team teamB,
         @NonNull TournamentBracket bracketPosition,
         @NonNull MatchStatus status,
         @NonNull Optional<MatchResult> finishedMatchResult) {
    public static Match of(Team teamA, Team teamB, TournamentBracket bracketPosition) {
        return new Match(teamA, teamB, bracketPosition, MatchStatus.PLANNED, Optional.empty());
    }

    public Match {
        if (status == MatchStatus.FINISHED && finishedMatchResult.isEmpty()) {
            throw new IllegalMatchException("Match is finished but no result provided");
        }
        if (status == MatchStatus.PLANNED && finishedMatchResult.isPresent()) {
            throw new IllegalMatchException("Match is not finished but the result is provided");
        }
    }

    public Match finishWithResult(MatchResult result) {
        return new Match(teamA, teamB, bracketPosition, MatchStatus.FINISHED, Optional.of(result));
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

    public Team getLoser() {
        return teamA == getWinner() ? teamB : teamA;
    }

    public Match inverted() {
        return new Match(teamB, teamA, bracketPosition, status, finishedMatchResult.map(MatchResult::inverted));
    }
}
