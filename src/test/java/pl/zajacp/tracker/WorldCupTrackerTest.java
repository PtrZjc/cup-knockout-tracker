package pl.zajacp.tracker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.exception.DuplicateTeamsException;
import pl.zajacp.tracker.api.exception.InvalidTeamCountException;
import pl.zajacp.tracker.api.exception.InvalidTeamOrderException;
import pl.zajacp.tracker.api.exception.MatchAlreadyCompletedException;
import pl.zajacp.tracker.api.exception.MatchNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.zajacp.tracker.api.MatchStatus.FINISHED;
import static pl.zajacp.tracker.api.MatchStatus.PLANNED;
import static pl.zajacp.tracker.api.Team.ARGENTINA;
import static pl.zajacp.tracker.api.Team.BELGIUM;
import static pl.zajacp.tracker.api.Team.BRAZIL;
import static pl.zajacp.tracker.api.Team.CROATIA;
import static pl.zajacp.tracker.api.Team.ENGLAND;
import static pl.zajacp.tracker.api.Team.FRANCE;
import static pl.zajacp.tracker.api.Team.GERMANY;
import static pl.zajacp.tracker.api.Team.ITALY;
import static pl.zajacp.tracker.api.Team.JAPAN;
import static pl.zajacp.tracker.api.Team.MEXICO;
import static pl.zajacp.tracker.api.Team.NETHERLANDS;
import static pl.zajacp.tracker.api.Team.PORTUGAL;
import static pl.zajacp.tracker.api.Team.SOUTH_KOREA;
import static pl.zajacp.tracker.api.Team.SPAIN;
import static pl.zajacp.tracker.api.Team.URUGUAY;
import static pl.zajacp.tracker.api.Team.USA;
import static pl.zajacp.tracker.api.TournamentBracket.Q_1;
import static pl.zajacp.tracker.api.TournamentBracket.Q_2;
import static pl.zajacp.tracker.api.TournamentBracket.Q_3;
import static pl.zajacp.tracker.api.TournamentBracket.Q_4;
import static pl.zajacp.tracker.api.TournamentBracket.R_1;
import static pl.zajacp.tracker.api.TournamentBracket.R_2;
import static pl.zajacp.tracker.api.TournamentBracket.R_3;
import static pl.zajacp.tracker.api.TournamentBracket.R_4;
import static pl.zajacp.tracker.api.TournamentBracket.R_5;
import static pl.zajacp.tracker.api.TournamentBracket.R_6;
import static pl.zajacp.tracker.api.TournamentBracket.R_7;
import static pl.zajacp.tracker.api.TournamentBracket.R_8;
import static pl.zajacp.tracker.api.TournamentStage.FINAL;
import static pl.zajacp.tracker.api.TournamentStage.QUARTER_FINALS;
import static pl.zajacp.tracker.api.TournamentStage.SEMI_FINALS;


public class WorldCupTrackerTest {

    private final WorldCupTracker tracker = new WorldCupTrackerImpl();

    private final static List<Team> INITIAL_TEAMS = List.of(
        BRAZIL, GERMANY,
        ITALY, FRANCE,
        SPAIN, ARGENTINA,
        ENGLAND, NETHERLANDS,
        PORTUGAL, CROATIA,
        USA, MEXICO,
        BELGIUM, JAPAN,
        URUGUAY, SOUTH_KOREA
    );

    private final static MatchResult TEAM_A_WINNING_RESULT = new MatchResult(3, 2);

    @Test
    public void shouldInitializeWithSixteenTeams() {
        // when
        var result = tracker.startWorldCup(INITIAL_TEAMS);

        // then
        assertThat(result).containsExactly(
                new Match(BRAZIL, GERMANY, R_1, PLANNED, null),
                new Match(ITALY, FRANCE, R_2, PLANNED, null),
                new Match(SPAIN, ARGENTINA, R_3, PLANNED, null),
                new Match(ENGLAND, NETHERLANDS, R_4, PLANNED, null),
                new Match(PORTUGAL, CROATIA, R_5, PLANNED, null),
                new Match(USA, MEXICO, R_6, PLANNED, null),
                new Match(BELGIUM, JAPAN, R_7, PLANNED, null),
                new Match(URUGUAY, SOUTH_KOREA, R_8, PLANNED, null)
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
                Stream.of(BRAZIL)
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
        tracker.recordMatchResult(BRAZIL, GERMANY, TEAM_A_WINNING_RESULT);

        // then
        assertThat(tracker.getMatches())
                .filteredOn(match -> match.teamA() == BRAZIL && match.teamB() == GERMANY)
                .containsExactly(new Match(BRAZIL, GERMANY, R_1, FINISHED, TEAM_A_WINNING_RESULT));
    }


    @Test
    public void shouldThrowWrongTeamOrder() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS);

        // when then
        assertThatThrownBy(() -> tracker.recordMatchResult(GERMANY, BRAZIL, TEAM_A_WINNING_RESULT))
                .isInstanceOf(InvalidTeamOrderException.class)
                .hasMessageContaining("The match with provided teams exists, but they are in the wrong order: BRAZIL is A and GERMANY is B.");
    }

    @Test
    public void shouldThrowMatchNotFoundExceptionForNonexistentMatch() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS);

        // when then
        assertThatThrownBy(() -> tracker.recordMatchResult(BRAZIL, ARGENTINA, TEAM_A_WINNING_RESULT))
                .isInstanceOf(MatchNotFoundException.class)
                .hasMessageContaining("No match found between BRAZIL and ARGENTINA");
    }

    @Disabled
    @Test
    public void shouldThrowMatchAlreadyCompletedExceptionIfUpdatingFinishedMatch() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS);
        tracker.recordMatchResult(BRAZIL, GERMANY, TEAM_A_WINNING_RESULT);

        // when then
        assertThatThrownBy(() -> tracker.recordMatchResult(BRAZIL, GERMANY, TEAM_A_WINNING_RESULT))
                .isInstanceOf(MatchAlreadyCompletedException.class)
                .hasMessageContaining("The match between BRAZIL and GERMANY has already been completed");
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

    @Test
    public void shouldCreateQuarterFinalMatchesAfterLastRoundOf16Match() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS)
                .forEach(m -> tracker.recordMatchResult(m.teamA(), m.teamB(), TEAM_A_WINNING_RESULT));

        // when
        var initalizedQuarterFinalMatches = tracker.getMatches();

        // then
        assertThat(initalizedQuarterFinalMatches).hasSize(8 + 4)
                .filteredOn(match -> match.status() == FINISHED)
                .hasSize(8);

        assertThat(initalizedQuarterFinalMatches)
                .filteredOn(match -> match.bracketPosition().getStage() == QUARTER_FINALS)
                .containsExactlyInAnyOrder(
                        new Match(BRAZIL, ITALY, Q_1, PLANNED, null),
                        new Match(SPAIN, ENGLAND, Q_2, PLANNED, null),
                        new Match(PORTUGAL, USA, Q_3, PLANNED, null),
                        new Match(BELGIUM, URUGUAY, Q_4, PLANNED, null)
                );
    }

    @Test
    public void shouldCreateSemiFinalMatchesAfterLastQuarterFinalMatch() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS)
                .forEach(m -> tracker.recordMatchResult(m.teamA(), m.teamB(), TEAM_A_WINNING_RESULT));

        tracker.getMatches().stream()
                .forEach(m -> tracker.recordMatchResult(m.teamA(), m.teamB(), TEAM_A_WINNING_RESULT));

        Set<Team> expectedTeamsInSemiFinals = Set.of(BRAZIL, SPAIN, PORTUGAL, BELGIUM);

        // when
        var initalizedSemiFinalMatches = tracker.getMatches();

        // then
        assertThat(initalizedSemiFinalMatches).hasSize(8 + 4 + 2)
                .filteredOn(match -> match.status() == FINISHED)
                .hasSize(8 + 4);

        assertThat(initalizedSemiFinalMatches)
                .filteredOn(match -> match.bracketPosition().getStage() == SEMI_FINALS)
                .hasSize(2) // nondeterministic test result after quarter finals
                .allSatisfy(match -> {
                    assertThat(match.status()).isEqualTo(PLANNED);
                    assertThat(expectedTeamsInSemiFinals).contains(match.teamA());
                    assertThat(expectedTeamsInSemiFinals).contains(match.teamB());
                });
    }

    @Test
    public void shouldCreateFinalMatchAfterLastSemiFinalMatch() {
        // given
        tracker.startWorldCup(INITIAL_TEAMS)
                .forEach(m -> tracker.recordMatchResult(m.teamA(), m.teamB(), TEAM_A_WINNING_RESULT));

        tracker.getMatches().stream()
                .filter(m -> m.status() == PLANNED)
                .forEach(m -> tracker.recordMatchResult(m.teamA(), m.teamB(), TEAM_A_WINNING_RESULT));

        tracker.getMatches().stream()
                .filter(m -> m.status() == PLANNED)
                .forEach(m -> tracker.recordMatchResult(m.teamA(), m.teamB(), TEAM_A_WINNING_RESULT));

        Set<Team> expectedTeamsInFinals = Set.of(BRAZIL, SPAIN, PORTUGAL, BELGIUM);

        // when
        var initalizedFinalMatches = tracker.getMatches();

        // then
        assertThat(initalizedFinalMatches).hasSize(8 + 4 + 2 + 2)
                .filteredOn(match -> match.status() == FINISHED)
                .hasSize(8 + 4 + 2);

        assertThat(initalizedFinalMatches)
                .filteredOn(match -> match.bracketPosition().getStage() == FINAL)
                .hasSize(2)  // nondeterministic test result after quarter finals
                .allSatisfy(match -> {
                            assertThat(match.status()).isEqualTo(PLANNED);
                            assertThat(expectedTeamsInFinals).contains(match.teamA());
                            assertThat(expectedTeamsInFinals).contains(match.teamB());
                        }
                );
    }
}
