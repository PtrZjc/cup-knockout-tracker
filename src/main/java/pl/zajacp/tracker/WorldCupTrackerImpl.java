package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.MatchStatus;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.TournamentBracket;
import pl.zajacp.tracker.api.TournamentStage;
import pl.zajacp.tracker.api.exception.DuplicateTeamsException;
import pl.zajacp.tracker.api.exception.InvalidTeamCountException;
import pl.zajacp.tracker.api.exception.MatchNotFoundException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static pl.zajacp.tracker.api.TournamentBracket.F_THIRD_PLACE;
import static pl.zajacp.tracker.api.TournamentStage.FINAL;
import static pl.zajacp.tracker.api.TournamentStage.ROUND_OF_16;
import static pl.zajacp.tracker.api.TournamentStage.SEMI_FINALS;

public class WorldCupTrackerImpl implements WorldCupTracker {

    private final Map<MatchKey, Match> matches = new LinkedHashMap<>();

    private final static Comparator<Match> MATCH_COMPARATOR =
            Comparator.comparingInt((Match m) -> m.bracketPosition().getStage().getOrderInCompetition())
                    .reversed()
                    .thenComparing(Match::bracketPosition);

    @Override
    public List<Match> startWorldCup(List<Team> teams) {
        if (teams.size() != 16) {
            throw new InvalidTeamCountException(teams.size());
        }
        if (Set.copyOf(teams).size() != 16) {
            throw new DuplicateTeamsException();
        }

        var initialBracketPositions = Arrays.stream(TournamentBracket.values())
                .filter(b -> b.getStage() == ROUND_OF_16)
                .toList();

        IntStream.rangeClosed(0, 7).forEach(i -> {
            var match = Match.of(
                    teams.get(2 * i),
                    teams.get(2 * i + 1),
                    initialBracketPositions.get(i));
            matches.put(MatchKey.of(match), match);
        });

        return matches.values().stream().toList();
    }

    @Override
    public Match recordMatchResult(Team teamA, Team teamB, MatchResult matchResult) {
        var matchKey = new MatchKey(teamA, teamB);
        if (matches.containsKey(matchKey.inverted())) {
            matchKey = matchKey.inverted();
            matchResult = invert(matchResult);
        }
        var match = Optional.ofNullable(matches.get(matchKey));
        if (match.isEmpty()) {
            throw new MatchNotFoundException(teamA, teamB);
        }
        var finishedMatch = matches.put(matchKey, match.get().finishWithResult(matchResult));
        recalculateTournamentStage();
        return finishedMatch;
    }

    @Override
    public List<Match> getMatches() {
        return List.copyOf(matches.values());
    }

    @Override
    public String getWorldCupSummary() {
        return "";
    }

    private void recalculateTournamentStage() {
        boolean finalOrAnyMatchUnfinished = matches.values().stream()
                .anyMatch(m -> m.status() == MatchStatus.PLANNED
                        || m.bracketPosition().getStage() == FINAL);
        if (finalOrAnyMatchUnfinished) {
            return;
        }

        Map<TournamentStage, List<Match>> matchesByStage = matches.values().stream()
                .collect(groupingBy(m -> m.bracketPosition().getStage(), toList()));

        TournamentStage lastCompletedStage = matchesByStage.entrySet().stream()
                .filter(e -> e.getValue().stream().allMatch(m -> m.status() == MatchStatus.FINISHED))
                .map(Map.Entry::getKey)
                .distinct()
                .max(comparingInt(TournamentStage::getOrderInCompetition))
                .orElseThrow();

        List<Match> stageWinners = matchesByStage.get(lastCompletedStage).stream()
                .map(BracketWinner::of)
                .collect(groupingBy(bw -> bw.currentBracket.nextBracket(), mapping(BracketWinner::winningTeam, toList())))
                .entrySet().stream()
                .map(e -> Match.of(e.getValue().getFirst(), e.getValue().get(1), e.getKey()))
                .toList();

        if (lastCompletedStage == SEMI_FINALS) {
            var thirdPlaceMatch = getThirdPlaceMatch(matchesByStage);
            matches.put(MatchKey.of(thirdPlaceMatch), thirdPlaceMatch);
        }
        stageWinners.forEach(m -> matches.put(MatchKey.of(m), m));
    }

    private static Match getThirdPlaceMatch(Map<TournamentStage, List<Match>> matchesByStage) {
        var thirdPlaceTeams = matchesByStage.get(SEMI_FINALS).stream()
                .map(Match::getLoser)
                .toList();
        return Match.of(thirdPlaceTeams.get(0), thirdPlaceTeams.get(1), F_THIRD_PLACE);
    }


    private record MatchKey(Team teamA, Team teamB) {
        static MatchKey of(Match match) {
            return new MatchKey(match.teamA(), match.teamB());
        }

        MatchKey inverted() {
            return new MatchKey(teamB(), teamA());
        }
    }

    private MatchResult invert(MatchResult result) {
        return new MatchResult(result.scoreTeamB(),
                result.scoreTeamA(),
                result.penaltyScoreTeamB(),
                result.penaltyScoreTeamA(),
                result.matchDateTime());
    }

    private record BracketWinner(Team winningTeam, TournamentBracket currentBracket) {
        static BracketWinner of(Match match) {
            return new BracketWinner(match.getWinner(), match.bracketPosition());
        }
    }
}
