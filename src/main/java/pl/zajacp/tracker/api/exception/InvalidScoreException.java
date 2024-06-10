package pl.zajacp.tracker.api.exception;

public class InvalidScoreException extends WorldCupTrackerException {
    public InvalidScoreException(int maxScore) {
        super("Invalid score provided. Score must be between 0 and " + maxScore + ".");
    }
}
