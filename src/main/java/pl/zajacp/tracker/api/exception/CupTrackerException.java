package pl.zajacp.tracker.api.exception;

/**
 * Parent exception class for all exceptions thrown by the CupTracker.
 */
public class CupTrackerException extends RuntimeException {
    public CupTrackerException(String message) {
        super(message);
    }
}
