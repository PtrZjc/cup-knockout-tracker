package pl.zajacp.tracker.api.exception;

/**
 * Parent exception class for all exceptions thrown by the WorldCupTracker.
 */
public class WorldCupTrackerException extends RuntimeException {
    public WorldCupTrackerException(String message) {
        super(message);
    }
}
