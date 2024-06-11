package pl.zajacp.tracker.api;

import lombok.NonNull;
import pl.zajacp.tracker.api.exception.IllegalMatchException;

public record Match
        (@NonNull Team teamA,
         @NonNull Team teamB,
         @NonNull TournamentBracket bracketPosition,
         @NonNull MatchStatus status,
         MatchResult finishedMatchResult) {
    public static Match of(Team teamA, Team teamB, TournamentBracket bracketPosition) {
        return new Match(teamA, teamB, bracketPosition, MatchStatus.PLANNED, null);
    }

    public Match {
        if (status == MatchStatus.FINISHED && finishedMatchResult == null) {
            throw new IllegalMatchException("Match is finished but no result provided");
        }
    }

    public Match finishWithResult(MatchResult result) {
        return new Match(teamA, teamB, bracketPosition, MatchStatus.FINISHED, result);
    }

    public Team getWinner() {
        if (status != MatchStatus.FINISHED) {
            throw new IllegalMatchException("Match is not finished yet");
        }
        if (finishedMatchResult.scoreTeamA() != finishedMatchResult.scoreTeamB()) {
            return finishedMatchResult.scoreTeamA() > finishedMatchResult.scoreTeamB()
                    ? teamA
                    : teamB;
        }
        return finishedMatchResult.penaltyScoreTeamA().get() > finishedMatchResult.penaltyScoreTeamB().get()
                ? teamA
                : teamB;
    }

    public Team getLoser() {
        return teamA == getWinner() ? teamB : teamA;
    }
}
