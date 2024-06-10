package pl.zajacp.tracker.api.exception;

public class InvalidTeamCountException extends WorldCupTrackerException {
    public InvalidTeamCountException(int providedCount) {
        super("Invalid number of teams provided: " + providedCount + ". Exactly 16 teams are required.");
    }
}
