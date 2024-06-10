package pl.zajacp.tracker.api.exception;

public class DuplicateTeamsException extends WorldCupTrackerException {
    public DuplicateTeamsException() {
        super("Duplicate teams found in the list. Each team must be unique.");
    }
}
