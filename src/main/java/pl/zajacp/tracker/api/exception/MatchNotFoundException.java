package pl.zajacp.tracker.api.exception;

import pl.zajacp.tracker.api.Team;

public class MatchNotFoundException extends WorldCupTrackerException {
    public MatchNotFoundException(Team teamA, Team teamB) {
        super("No match found between " + teamA + " and " + teamB + ".");
    }
}
