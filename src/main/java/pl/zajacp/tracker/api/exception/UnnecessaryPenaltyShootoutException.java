package pl.zajacp.tracker.api.exception;

public class UnnecessaryPenaltyShootoutException extends WorldCupTrackerException {
    public UnnecessaryPenaltyShootoutException() {
        super("Penalty shootout result provided for a match that was not a draw. Penalty shootouts are only valid for tied matches.");
    }
}
