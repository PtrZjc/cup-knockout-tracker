package pl.zajacp.tracker.api;

import lombok.NonNull;

public record Match
        (@NonNull Team teamA,
         @NonNull Team teamB,
         @NonNull TournamentStage tournamentStage,
         @NonNull MatchStatus status,
         MatchResult finishedMatchResult) {
}
