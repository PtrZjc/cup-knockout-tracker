package pl.zajacp.tracker;

import org.junit.jupiter.api.Test;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.exception.InvalidMatchResultException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MatchResultTests {

    @Test
    public void shouldCreateMatchResultWithoutPenalties() {
        // given
        int scoreTeamA = 2;
        int scoreTeamB = 1;

        // when
        MatchResult result = MatchResult.of(scoreTeamA, scoreTeamB);

        // then
        assertThat(result.scoreTeamA()).isEqualTo(scoreTeamA);
        assertThat(result.scoreTeamB()).isEqualTo(scoreTeamB);
        assertThat(result.penaltyScoreTeamA()).isEmpty();
        assertThat(result.penaltyScoreTeamB()).isEmpty();
        assertThat(result.matchDateTime()).isNotNull();
    }

    @Test
    public void shouldCreateMatchResultWithPenalties() {
        // given
        int scoreTeamA = 1;
        int scoreTeamB = 1;
        int penaltyScoreTeamA = 4;
        int penaltyScoreTeamB = 3;

        // when
        MatchResult result = MatchResult.of(scoreTeamA, scoreTeamB, penaltyScoreTeamA, penaltyScoreTeamB);

        // then
        assertThat(result.scoreTeamA()).isEqualTo(scoreTeamA);
        assertThat(result.scoreTeamB()).isEqualTo(scoreTeamB);
        assertThat(result.penaltyScoreTeamA()).contains(penaltyScoreTeamA);
        assertThat(result.penaltyScoreTeamB()).contains(penaltyScoreTeamB);
        assertThat(result.matchDateTime()).isNotNull();
    }

    @Test
    public void shouldThrowUnnecessaryPenaltyShootoutExceptionIfNotDraw() {
        // given
        int scoreTeamA = 2;
        int scoreTeamB = 1;
        int penaltyScoreTeamA = 4;
        int penaltyScoreTeamB = 3;

        // when / then
        assertThatThrownBy(() -> MatchResult.of(scoreTeamA, scoreTeamB, penaltyScoreTeamA, penaltyScoreTeamB))
                .isInstanceOf(InvalidMatchResultException.class)
                .hasMessageContaining("Penalty shootout result provided for a match that was not a draw");
    }

    @Test
    public void shouldThrowInvalidScoreExceptionForNegativeScores() {
        // given
        int scoreTeamA = -1;
        int scoreTeamB = 1;

        // when / then
        assertThatThrownBy(() -> MatchResult.of(scoreTeamA, scoreTeamB))
                .isInstanceOf(InvalidMatchResultException.class)
                .hasMessageContaining("Invalid score provided. Score must be between 0 and 100.");
    }


    @Test
    public void shouldThrowInvalidPenaltyWinnerExceptionForNegativePenaltyScores() {
        // given
        int scoreTeamA = 1;
        int scoreTeamB = 1;
        int penaltyScoreTeamA = -1;
        int penaltyScoreTeamB = 3;

        // when / then
        assertThatThrownBy(() -> MatchResult.of(scoreTeamA, scoreTeamB, penaltyScoreTeamA, penaltyScoreTeamB))
                .isInstanceOf(InvalidMatchResultException.class)
                .hasMessageContaining("Penalty scores must be non-negative");
    }

    @Test
    public void shouldThrowInvalidPenaltyWinnerExceptionIfPenaltyScoresAreEqual() {
        // given
        int scoreTeamA = 1;
        int scoreTeamB = 1;
        int penaltyScoreTeamA = 3;
        int penaltyScoreTeamB = 3;

        // when / then
        assertThatThrownBy(() -> MatchResult.of(scoreTeamA, scoreTeamB, penaltyScoreTeamA, penaltyScoreTeamB))
                .isInstanceOf(InvalidMatchResultException.class)
                .hasMessageContaining("Penalty shootout must have a distinct winner");
    }

    @Test
    public void shouldTharowInvalidPenaltyWinnerExceptionIfPenaltyScoresAreEqual() {
        // given
        int scoreTeamA = 1;
        int scoreTeamB = 1;
        int penaltyScoreTeamB = 3;

        // when / then
        assertThatThrownBy(() -> new MatchResult(scoreTeamA, scoreTeamB, Optional.empty(), Optional.of(penaltyScoreTeamB), null))
                .isInstanceOf(InvalidMatchResultException.class)
                .hasMessageContaining("Penalty shootout must have a distinct winner");
    }
}
