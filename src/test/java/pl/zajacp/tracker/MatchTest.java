package pl.zajacp.tracker;

import org.junit.jupiter.api.Test;
import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.exception.IllegalMatchException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.zajacp.tracker.api.Team.BRAZIL;
import static pl.zajacp.tracker.api.Team.FRANCE;
import static pl.zajacp.tracker.api.TournamentBracket.R_1;

public class MatchTest {

    @Test
    public void shouldCreateMatchWithoutResult() {
        // when
        Match match = Match.of(FRANCE, BRAZIL, R_1);

        // then
        assertThat(match).isEqualTo(new Match(FRANCE, BRAZIL, R_1, Optional.empty()));
    }

    @Test
    public void shouldFinishMatchWithResult() {
        // given
        Match match = Match.of(FRANCE, BRAZIL, R_1);
        MatchResult matchResult = MatchResult.of(3, 2);

        // when
        var finishedMatch = match.finishWithResult(matchResult);

        // then
        assertThat(finishedMatch).isNotEqualTo(match);
        assertThat(finishedMatch).isEqualTo(new Match(FRANCE, BRAZIL, R_1,Optional.of(matchResult)));
    }

    @Test
    public void shouldThrowIllegalMatchExceptionIfMatchNotFinished() {
        // given
        Match match = Match.of(FRANCE, BRAZIL, R_1);

        // when / then
        assertThatThrownBy(match::getWinner)
                .isInstanceOf(IllegalMatchException.class)
                .hasMessageContaining("Match is not finished yet");
    }

    @Test
    public void shouldGetWinnerForRegularMatch() {
        // given
        Match match = Match.of(FRANCE, BRAZIL, R_1);
        MatchResult matchResult = MatchResult.of(3, 2);

        // when
        match = match.finishWithResult(matchResult);

        // then
        assertThat(match.getWinner()).isEqualTo(FRANCE);
    }

    @Test
    public void shouldGetWinnerForPenaltyMatch() {
        // given
        Match match = Match.of(FRANCE, BRAZIL, R_1);
        MatchResult matchResult = MatchResult.of(2, 2, 4, 3);

        // when
        match = match.finishWithResult(matchResult);

        // then
        assertThat(match.getWinner()).isEqualTo(FRANCE);
    }
}
