package pl.zajacp.tracker.api;

import pl.zajacp.tracker.api.exception.InvalidMatchResultException;

import java.time.LocalDateTime;
import java.util.Optional;

public record MatchResult(
        int scoreTeamA,
        int scoreTeamB,
        Optional<Integer> penaltyScoreTeamA,
        Optional<Integer> penaltyScoreTeamB,
        LocalDateTime matchDateTime
) {
    public static final int MAX_SCORE = 100;

    public static MatchResult of(int scoreTeamA, int scoreTeamB) {
        return new MatchResult(scoreTeamA, scoreTeamB, Optional.empty(), Optional.empty(), LocalDateTime.now());
    }

    public static MatchResult of(int scoreTeamA, int scoreTeamB, int penaltyScoreTeamA, int penaltyScoreTeamB) {
        return new MatchResult(scoreTeamA, scoreTeamB, Optional.of(penaltyScoreTeamA), Optional.of(penaltyScoreTeamB), LocalDateTime.now());
    }

    public MatchResult {
        if (scoreTeamA < 0 || scoreTeamB < 0 || scoreTeamA > MAX_SCORE || scoreTeamB > MAX_SCORE) {
            throw new InvalidMatchResultException("Invalid score provided. Score must be between 0 and " + MAX_SCORE + ".");
        }

        if (penaltyScoreTeamA.isPresent() || penaltyScoreTeamB.isPresent()) {
            if (scoreTeamA != scoreTeamB) {
                throw new InvalidMatchResultException("Penalty shootout result provided for a match that was not a draw.");
            }

            if (penaltyScoreTeamA.equals(penaltyScoreTeamB)) {
                throw new InvalidMatchResultException("Penalty shootout must have a distinct winner.");
            }

            if (penaltyScoreTeamA.isEmpty() || penaltyScoreTeamB.isEmpty() ||
                    penaltyScoreTeamA.get() < 0 || penaltyScoreTeamB.get() < 0) {
                throw new InvalidMatchResultException("Invalid penalty scores provided.");
            }

        }
    }
}
