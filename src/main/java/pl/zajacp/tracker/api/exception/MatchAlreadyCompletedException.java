package pl.zajacp.tracker.api.exception;

public class MatchAlreadyCompletedException extends WorldCupTrackerException {
    public MatchAlreadyCompletedException(String teamA, String teamB) {
        super("The match between " + teamA + " and " + teamB + " has already been completed.");
    }
}
