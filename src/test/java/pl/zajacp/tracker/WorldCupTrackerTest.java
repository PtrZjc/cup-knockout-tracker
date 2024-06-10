package pl.zajacp.tracker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.exception.DuplicateTeamsException;
import pl.zajacp.tracker.api.exception.InvalidTeamCountException;
import pl.zajacp.tracker.api.exception.InvalidTeamOrderException;
import pl.zajacp.tracker.api.exception.MatchNotFoundException;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.zajacp.tracker.api.MatchStatus.FINISHED;
import static pl.zajacp.tracker.api.MatchStatus.PLANNED;
import static pl.zajacp.tracker.api.Team.ARGENTINA;
import static pl.zajacp.tracker.api.Team.BELGIUM;
import static pl.zajacp.tracker.api.Team.BRAZIL;
import static pl.zajacp.tracker.api.Team.COLOMBIA;
import static pl.zajacp.tracker.api.Team.CROATIA;
import static pl.zajacp.tracker.api.Team.DENMARK;
import static pl.zajacp.tracker.api.Team.ENGLAND;
import static pl.zajacp.tracker.api.Team.FRANCE;
import static pl.zajacp.tracker.api.Team.GERMANY;
import static pl.zajacp.tracker.api.Team.ITALY;
import static pl.zajacp.tracker.api.Team.MEXICO;
import static pl.zajacp.tracker.api.Team.NETHERLANDS;
import static pl.zajacp.tracker.api.Team.PORTUGAL;
import static pl.zajacp.tracker.api.Team.SPAIN;
import static pl.zajacp.tracker.api.Team.SWITZERLAND;
import static pl.zajacp.tracker.api.Team.URUGUAY;
import static pl.zajacp.tracker.api.TournamentStage.ROUND_OF_16;


public class WorldCupTrackerTest {

    private final WorldCupTracker tracker = new WorldCupTrackerImpl();

    private final static List<Team> INITIAL_TEAMS = List.of(
            FRANCE, BRAZIL,
            GERMANY, ARGENTINA,
            SPAIN, ENGLAND,
            BELGIUM, ITALY,
            PORTUGAL, NETHERLANDS,
            URUGUAY, CROATIA,
            DENMARK, SWITZERLAND,
            COLOMBIA, MEXICO
    );

    private final static MatchResult REGULAR_MATCH_RESULT = new MatchResult(3, 2);

    @Test
    public void shouldInitializeWithSixteenTeams() {
        // when
        var result = tracker.startWorldCup(INITIAL_TEAMS);

        // then
        assertThat(result).containsExactly(
                new Match(FRANCE, BRAZIL, ROUND_OF_16, PLANNED, null),
                new Match(GERMANY, ARGENTINA, ROUND_OF_16, PLANNED, null),
                new Match(SPAIN, ENGLAND, ROUND_OF_16, PLANNED, null),
                new Match(BELGIUM, ITALY, ROUND_OF_16, PLANNED, null),
                new Match(PORTUGAL, NETHERLANDS, ROUND_OF_16, PLANNED, null),
                new Match(URUGUAY, CROATIA, ROUND_OF_16, PLANNED, null),
                new Match(DENMARK, SWITZERLAND, ROUND_OF_16, PLANNED, null),
                new Match(COLOMBIA, MEXICO, ROUND_OF_16, PLANNED, null)
        );
    }

    @Test
    public void shouldThrowInvalidTeamCountExceptionIfTeamCountNotSixteen() {
        // when then
        assertThatThrownBy(() -> tracker.startWorldCup(INITIAL_TEAMS.subList(0, 15)))
                .isInstanceOf(InvalidTeamCountException.class)
                .hasMessageContaining("Team count must be 16, but was 15");
    }

    @Test
    public void shouldThrowDuplicateTeamsExceptionForDuplicateTeams() {
        // given
        var initialTeamWithDuplicatedFrance = Stream.concat(
                INITIAL_TEAMS.subList(0, 15).stream(),
                Stream.of(FRANCE)
        ).toList();

        // when then
        assertThatThrownBy(() -> tracker.startWorldCup(initialTeamWithDuplicatedFrance))
                .isInstanceOf(DuplicateTeamsException.class)
                .hasMessageContaining("Duplicate teams found in the list. Each team must be unique.");
    }

    @Test
    public void shouldRecordValidMatchResult() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS);

        // when
        var result = tracker.recordMatchResult(FRANCE, BRAZIL, REGULAR_MATCH_RESULT);

        // then
        assertThat(tracker.getMatches())
                .filteredOn(match -> (match.teamA() == FRANCE && match.teamB() == BRAZIL) ||
                        (match.teamA() == BRAZIL && match.teamB() == FRANCE))
                .containsExactly(new Match(FRANCE, BRAZIL, ROUND_OF_16, FINISHED, REGULAR_MATCH_RESULT));
    }


    @Test
    public void shouldThrowWrongTeamOrder() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS);

        // when then
        assertThatThrownBy(() -> tracker.recordMatchResult(BRAZIL, FRANCE, REGULAR_MATCH_RESULT))
                .isInstanceOf(InvalidTeamOrderException.class)
                .hasMessageContaining("The match with provided teams exists, but they are in the wrong order: FRANCE is A and BRAZIL is B.");
    }

    @Test
    public void shouldThrowMatchNotFoundExceptionForNonexistentMatch() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS);

        // when then
        assertThatThrownBy(() -> tracker.recordMatchResult(FRANCE, ARGENTINA, REGULAR_MATCH_RESULT))
                .isInstanceOf(MatchNotFoundException.class)
                .hasMessageContaining("No match found between FRANCE and ARGENTINA");
    }

    @Disabled
    @Test
    public void shouldThrowMatchAlreadyCompletedExceptionIfUpdatingFinishedMatch() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldRecordPenaltyShootoutCorrectlyForDrawMatch() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldThrowUnnecessaryPenaltyShootoutExceptionIfNotDraw() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldProvideSummaryOfPlannedMatchesPostInitialization() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldUpdateSummaryAfterRecordingResults() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldHandleAllMatchesCompletedWithoutError() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldThrowInvalidScoreExceptionForOutOfRangeScores() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldReturnEmptySetWhenNoMatchesAreInitialized() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldReturnMatchesInChronologicalOrder() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldReturnAllPlannedMatchesBeforeAnyAreCompleted() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldReturnCorrectMixOfCompletedAndPlannedMatches() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldCreateQuarterFinalMatchesAfterLastRoundOf16Match() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldCreateSemiFinalMatchesAfterLastQuarterFinalMatch() {
        // given
        // when
        // then
    }

    @Disabled
    @Test
    public void shouldCreateFinalMatchAfterLastSemiFinalMatch() {
        // given
        // when
        // then
    }
}
