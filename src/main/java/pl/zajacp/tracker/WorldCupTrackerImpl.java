package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;

import java.util.List;

public class WorldCupTrackerImpl implements WorldCupTracker {
    @Override
    public void startWorldCup(List<Team> teams) {

    }

    @Override
    public Match recordMatchResult(Team teamA, Team teamB, MatchResult matchResult) {
        return null;
    }

    @Override
    public List<Match> getMatches() {
        return List.of();
    }

    @Override
    public String getWorldCupSummary() {
        return "";
    }
}
