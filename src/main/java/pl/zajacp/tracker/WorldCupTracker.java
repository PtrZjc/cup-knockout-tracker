package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.exception.DuplicateTeamsException;
import pl.zajacp.tracker.api.exception.InvalidPenaltyWinnerException;
import pl.zajacp.tracker.api.exception.InvalidTeamCountException;
import pl.zajacp.tracker.api.exception.MatchAlreadyCompletedException;
import pl.zajacp.tracker.api.exception.MatchNotFoundException;
import pl.zajacp.tracker.api.exception.UnnecessaryPenaltyShootoutException;

import java.util.List;
import java.util.Set;

public interface WorldCupTracker {

    /**
     * Initializes the Round of 16 World Cup stage with a specified list of teams.
     *
     * @param teams the list of teams participating in the Round of 16, must be exactly 16 unique teams.
     *              Teams will be paired in the order they appear in the list.
     * @return a list of {@link Match} objects representing the initial Round of 16 matches
     * @throws InvalidTeamCountException if the list does not contain exactly 16 teams
     * @throws DuplicateTeamsException   if there are duplicate teams in the list
     */
    List<Match> startWorldCup(List<Team> teams);

    /**
     * Records the result of a match in the World Cup. Updates the match status to FINISHED and,
     * if it's the last match in the stage, the next tournament stage will be triggered.
     *
     * @param teamA       the first team in the match
     * @param teamB       the second team in the match
     * @param matchResult the result of the match including scores and, if applicable, the winner of a penalty shootout
     * @return the updated Match object with the result
     * @throws MatchNotFoundException              if the specified match does not exist
     * @throws MatchAlreadyCompletedException      if the match has already been marked as FINISHED
     * @throws InvalidPenaltyWinnerException       if the penalty shootout winner is not one of the match participants
     * @throws UnnecessaryPenaltyShootoutException if a penalty shootout winner is declared but the match result was not a draw
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
     * Returns the string summary of all stages of the World Cup. The summary includes completed and upcoming matches,
     * each stage, and the progression of teams.
     *
     * @return a string summary of the World Cup, detailing match results, stage completions, and future matches
     */
    String getWorldCupSummary();
}
