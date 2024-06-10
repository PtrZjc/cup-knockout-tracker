package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.exception.DuplicateTeamsException;
import pl.zajacp.tracker.api.exception.InvalidTeamCountException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class WorldCupTrackerImpl implements WorldCupTracker {

    private final List<Match> matches = new ArrayList<>();

    @Override
    public List<Match> startWorldCup(List<Team> teams) {
        if (teams.size() != 16) {
            throw new InvalidTeamCountException(teams.size());
        }
        if (new HashSet<>(teams).size() != 16) {
            throw new DuplicateTeamsException();
        }

        IntStream.rangeClosed(0, 7)
                .forEach(i -> matches.add(Match.of(teams.get(2*i), teams.get(2*i + 1))));

        return matches.stream().toList();
    }

    @Override
    public Match recordMatchResult(Team teamA, Team teamB, MatchResult matchResult) {
        return null;
    }

    @Override
    public Set<Match> getMatches() {
        return Set.of();
    }

    @Override
    public String getWorldCupSummary() {
        return "";
    }
}
