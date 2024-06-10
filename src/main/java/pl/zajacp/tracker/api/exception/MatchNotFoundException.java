package pl.zajacp.tracker.api.exception;

public class MatchNotFoundException extends WorldCupTrackerException {
    public MatchNotFoundException(String teamA, String teamB) {
        super("No match found between " + teamA + " and " + teamB + ".");
    }
}
