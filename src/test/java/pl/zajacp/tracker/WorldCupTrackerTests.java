package pl.zajacp.tracker;

import org.junit.jupiter.api.Test;

public class WorldCupTrackerTests {

    private final WorldCupTracker tracker = new WorldCupTrackerImpl();

    @Test
    public void shouldInitializeWithSixteenTeams() {
        // given
        // when
        // then
    }

    @Test
    public void shouldThrowInvalidTeamCountExceptionIfTeamCountNotSixteen() {
        // given
        // when
        // then
    }

    @Test
    public void shouldThrowDuplicateTeamsExceptionForDuplicateTeams() {
        // given
        // when
        // then
    }

    @Test
    public void shouldRecordValidMatchResult() {
        // given
        // when
        // then
    }

    @Test
    public void shouldThrowMatchNotFoundExceptionForNonexistentMatch() {
        // given
        // when
        // then
    }

    @Test
    public void shouldThrowMatchAlreadyCompletedExceptionIfUpdatingFinishedMatch() {
        // given
        // when
        // then
    }

    @Test
    public void shouldRecordPenaltyShootoutCorrectlyForDrawMatch() {
        // given
        // when
        // then
    }

    @Test
    public void shouldThrowUnnecessaryPenaltyShootoutExceptionIfNotDraw() {
        // given
        // when
        // then
    }

    @Test
    public void shouldProvideSummaryOfPlannedMatchesPostInitialization() {
        // given
        // when
        // then
    }

    @Test
    public void shouldUpdateSummaryAfterRecordingResults() {
        // given
        // when
        // then
    }

    @Test
    public void shouldHandleAllMatchesCompletedWithoutError() {
        // given
        // when
        // then
    }

    @Test
    public void shouldThrowInvalidScoreExceptionForOutOfRangeScores() {
        // given
        // when
        // then
    }

    @Test
    public void shouldReturnEmptySetWhenNoMatchesAreInitialized() {
        // given
        // when
        // then
    }

    @Test
    public void shouldReturnMatchesInChronologicalOrder() {
        // given
        // when
        // then
    }

    @Test
    public void shouldReturnAllPlannedMatchesBeforeAnyAreCompleted() {
        // given
        // when
        // then
    }

    @Test
    public void shouldReturnCorrectMixOfCompletedAndPlannedMatches() {
        // given
        // when
        // then
    }

    @Test
    public void shouldCreateQuarterFinalMatchesAfterLastRoundOf16Match() {
        // given
        // when
        // then
    }

    @Test
    public void shouldCreateSemiFinalMatchesAfterLastQuarterFinalMatch() {
        // given
        // when
        // then
    }

    @Test
    public void shouldCreateFinalMatchAfterLastSemiFinalMatch() {
        // given
        // when
        // then
    }
}
