package pl.zajacp.tracker.api.exception;

public class InvalidTeamCountException extends WorldCupTrackerException {
    public InvalidTeamCountException(int providedCount) {
        super("Team count must be 16, but was " + providedCount);
    }
}
