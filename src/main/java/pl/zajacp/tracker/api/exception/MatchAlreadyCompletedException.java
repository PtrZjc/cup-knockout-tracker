package pl.zajacp.tracker.api.exception;

import pl.zajacp.tracker.api.Team;

public class MatchAlreadyCompletedException extends CupTrackerException {
    public MatchAlreadyCompletedException(Team teamA, Team teamB) {
        super("The match between " + teamA + " and " + teamB + " has already been completed.");
    }
}
