package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;

import java.util.List;
import java.util.Set;

public class WorldCupTrackerImpl implements WorldCupTracker {
    @Override
    public List<Match> startWorldCup(List<Team> teams) {
        return List.of();
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
