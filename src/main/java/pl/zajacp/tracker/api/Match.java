package pl.zajacp.tracker.api;

import lombok.NonNull;
import pl.zajacp.tracker.api.exception.IllegalMatchException;

public record Match
        (@NonNull Team teamA,
         @NonNull Team teamB,
         @NonNull TournamentStage tournamentStage,
         @NonNull MatchStatus status,
         MatchResult finishedMatchResult) {
    public static Match of(Team teamA, Team teamB) {
        return new Match(teamA, teamB, TournamentStage.ROUND_OF_16, MatchStatus.PLANNED, null);
    }

    public Match {
        if (status == MatchStatus.FINISHED && finishedMatchResult == null) {
            throw new IllegalMatchException("Match is finished but no result provided");
        }
    }
}
