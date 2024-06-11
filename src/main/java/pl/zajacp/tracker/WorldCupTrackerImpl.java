package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.MatchStatus;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.TournamentBracket;
import pl.zajacp.tracker.api.TournamentStage;
import pl.zajacp.tracker.api.exception.DuplicateTeamsException;
import pl.zajacp.tracker.api.exception.InvalidTeamCountException;
import pl.zajacp.tracker.api.exception.InvalidTeamOrderException;
import pl.zajacp.tracker.api.exception.MatchNotFoundException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class WorldCupTrackerImpl implements WorldCupTracker {

    private final Map<MatchKey, Match> matches = new LinkedHashMap<>();

    @Override
    public List<Match> startWorldCup(List<Team> teams) {
        if (teams.size() != 16) {
            throw new InvalidTeamCountException(teams.size());
        }
        if (Set.copyOf(teams).size() != 16) {
            throw new DuplicateTeamsException();
        }

        var initialBracketPositions = Arrays.stream(TournamentBracket.values())
                .filter(b -> b.getStage() == TournamentStage.ROUND_OF_16)
                .toList();

        IntStream.rangeClosed(0, 7).forEach(i -> {
            var match = Match.of(teams.get(2 * i), teams.get(2 * i + 1), initialBracketPositions.get(i));
            matches.put(MatchKey.of(match), match);
        });

        return matches.values().stream().toList();
    }

    @Override
    public Match recordMatchResult(Team teamA, Team teamB, MatchResult matchResult) {
        var matchKey = new MatchKey(teamA, teamB);
        if (matches.containsKey(matchKey.inverted())) {
            throw new InvalidTeamOrderException(teamA, teamB);
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
        var map = matches.values().stream()
                .collect(groupingBy(Match::tournamentStage, toList()));
        var lastCompletedStage = map.entrySet().stream()
                .filter(e -> e.getValue().stream().allMatch(m -> m.status() == MatchStatus.FINISHED))
                .map(Map.Entry::getKey)
                .collect(toSet());

//        var lastCompletedStage = Collections.min(completedStages);
    }


    private record MatchKey(Team teamA, Team teamB) {
        static MatchKey of(Match match) {
            return new MatchKey(match.teamA(), match.teamB());
        }

        MatchKey inverted() {
            return new MatchKey(teamB(), teamA());
        }
    }
}
