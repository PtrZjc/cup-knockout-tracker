package pl.zajacp.tracker.api.exception;

import pl.zajacp.tracker.api.Team;

public class InvalidTeamOrderException extends WorldCupTrackerException {
    public InvalidTeamOrderException(Team incorrectTeamA, Team incorrectTeamB) {
        super("The match with provided teams exists, but they are in the " +
                "wrong order: " + incorrectTeamB + " is A and " + incorrectTeamA + " is B.");
    }
}
