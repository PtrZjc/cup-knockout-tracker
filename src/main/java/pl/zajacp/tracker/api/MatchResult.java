package pl.zajacp.tracker.api;

import pl.zajacp.tracker.api.exception.InvalidPenaltyWinnerException;
import pl.zajacp.tracker.api.exception.InvalidScoreException;
import pl.zajacp.tracker.api.exception.UnnecessaryPenaltyShootoutException;

import java.time.LocalDateTime;
import java.util.Optional;

public record MatchResult(
        int scoreTeamA,
        int scoreTeamB,
        Optional<Integer> penaltyScoreTeamA,
        Optional<Integer> penaltyScoreTeamB,
        LocalDateTime matchDateTime
) {

    public final static int MAX_SCORE = 100;

    public static MatchResult of(int scoreTeamA, int scoreTeamB) {
        return new MatchResult(scoreTeamA, scoreTeamB, Optional.empty(), Optional.empty(), LocalDateTime.now());
    }

    public static MatchResult of(int scoreTeamA, int scoreTeamB, int penaltyScoreTeamA, int penaltyScoreTeamB) {
        return new MatchResult(scoreTeamA, scoreTeamB, Optional.of(penaltyScoreTeamA), Optional.of(penaltyScoreTeamB), LocalDateTime.now());
    }

    public MatchResult {
        if (scoreTeamA < 0 || scoreTeamB < 0) {
            throw new InvalidScoreException(MAX_SCORE);
        }

        if (penaltyScoreTeamA.isPresent() || penaltyScoreTeamB.isPresent()) {
            if (scoreTeamA != scoreTeamB) {
                throw new UnnecessaryPenaltyShootoutException();
            }

            if (penaltyScoreTeamA.isEmpty() || penaltyScoreTeamB.isEmpty()) {
                throw new InvalidPenaltyWinnerException("Both penalty scores must be provided.");
            }

            if (penaltyScoreTeamA.orElse(0) < 0 || penaltyScoreTeamB.orElse(0) < 0) {
                throw new InvalidPenaltyWinnerException("Penalty scores must be non-negative.");
            }

            if (penaltyScoreTeamA.equals(penaltyScoreTeamB)) {
                throw new InvalidPenaltyWinnerException("Penalty shootout must have a distinct winner.");
            }
        }
    }
}
