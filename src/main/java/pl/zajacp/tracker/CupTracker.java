package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.exception.InvalidTeamException;
import pl.zajacp.tracker.api.exception.MatchAlreadyCompletedException;
import pl.zajacp.tracker.api.exception.MatchNotFoundException;

import java.util.List;

public interface CupTracker {

    /**
     * Initializes the Round of 16 World Cup stage with a specified list of teams.
     *
     * @param teams the list of teams participating in the Round of 16, must be exactly 16 unique teams.
     *              Teams will be paired in the order they appear in the list.
     * @return a list of {@link Match} objects representing the initial Round of 16 matches
     * @throws InvalidTeamException if the list does not contain exactly 16 unique teams
     */
    List<Match> startCup(List<Team> teams);

    /**
     * Records the result of a match in the World Cup. Updates the match status to FINISHED and,
     * if it's the last match in the stage, the next tournament stage will be triggered.
     * <p>
     * If a match exists between two teams, it should be recognized regardless of the order of teamA or teamB.
     *
     * @param teamA       the first team in the match
     * @param teamB       the second team in the match
     * @param matchResult the result of the match including scores and, if applicable, the winner of a penalty shootout
     * @return the updated Match object with the result
     * @throws MatchNotFoundException         if the specified match does not exist
     * @throws MatchAlreadyCompletedException if the match has already been marked as FINISHED
     */
    Match recordMatchResult(Team teamA, Team teamB, MatchResult matchResult);

    /**
     * Retrieves a list of all matches in the World Cup,
     * ordered by place they were added or chronologically finished.
     * This order reflects the progression through the tournament stages from the Round of 16 onward.
     *
     * @return a list of Match objects, each representing a scheduled or completed match in the World Cup
     */
    List<Match> getMatches();

    /**
     * Returns the string summary of all stages of the Cup.
     * The summary includes completed and upcoming matches, each stage, and the progression of teams.
     *
     * @return a string summary of the Cup, detailing match results, stage completions, and future matches
     */
    String getWorldCupSummary();
}
