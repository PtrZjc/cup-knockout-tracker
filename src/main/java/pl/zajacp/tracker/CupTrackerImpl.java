package pl.zajacp.tracker;

import pl.zajacp.tracker.api.Match;
import pl.zajacp.tracker.api.MatchResult;
import pl.zajacp.tracker.api.Team;
import pl.zajacp.tracker.api.TournamentBracket;
import pl.zajacp.tracker.api.TournamentStage;
import pl.zajacp.tracker.api.exception.InvalidTeamException;
import pl.zajacp.tracker.api.exception.MatchAlreadyCompletedException;
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

public class CupTrackerImpl implements CupTracker {

    private final Map<MatchKey, Match> matches = new LinkedHashMap<>();

    private final static Comparator<Match> MATCH_COMPARATOR =
            comparingInt((Match m) -> m.bracketPosition().getStage().getOrderInCompetition())
                    .reversed()
                    .thenComparing(Match::bracketPosition);

    @Override
    public List<Match> startCup(List<Team> teams) {
        if (teams.size() != 16) {
            throw new InvalidTeamException("Team count must be 16, but was " + teams.size());
        }
        if (Set.copyOf(teams).size() != 16) {
            throw new InvalidTeamException("Duplicate teams found in the list. Each team must be unique.");
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
            matchResult = matchResult.inverted();
        }
        var match = Optional.ofNullable(matches.get(matchKey));
        if (match.isEmpty()) {
            throw new MatchNotFoundException(teamA, teamB);
        }
        if (match.get().isFinished()) {
            throw new MatchAlreadyCompletedException(teamA, teamB);
        }
        var updatedMatch = match.get().finishWithResult(matchResult);
        matches.put(matchKey, updatedMatch);
        recalculateTournamentStage();
        return updatedMatch;
    }

    @Override
    public List<Match> getMatches() {
        return List.copyOf(matches.values());
    }

    @Override
    public String getWorldCupSummary() {
        var sortedMatches = matches.values().stream()
                .sorted(MATCH_COMPARATOR)
                .toList();

        String summary = IntStream.range(0, sortedMatches.size() - 1)
                .mapToObj(i -> getSummaryLineWithPossibleHeader(sortedMatches.get(i), sortedMatches.get(i + 1)))
                .collect(Collectors.joining("\n"));

        return String.join("\n",
                getFirstHeader(sortedMatches),
                summary,
                printMatchSummaryLine(sortedMatches.getLast()));
    }

    private void recalculateTournamentStage() {
        boolean finalOrAnyMatchUnfinished = matches.values().stream()
                .anyMatch(m -> !m.isFinished() || m.bracketPosition().getStage() == FINAL);
        if (finalOrAnyMatchUnfinished) {
            return;
        }

        Map<TournamentStage, List<Match>> matchesByStage = matches.values().stream()
                .collect(groupingBy(m -> m.bracketPosition().getStage(), toList()));

        TournamentStage lastCompletedStage = matchesByStage.entrySet().stream()
                .filter(e -> e.getValue().stream().allMatch(Match::isFinished))
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

    private String getSummaryLineWithPossibleHeader(Match currentMatch, Match nextMatch) {
        String summaryLine = printMatchSummaryLine(currentMatch);
        boolean shouldAddHeader = currentMatch.bracketPosition().getStage()
                != nextMatch.bracketPosition().getStage();

        if (!shouldAddHeader) {
            return summaryLine;
        }
        String header = switch (currentMatch.bracketPosition().getStage()) {
            case FINAL -> "Semi-finals";
            case SEMI_FINALS -> "Quarter-finals";
            case QUARTER_FINALS -> "Round of 16";
            default -> throw new RuntimeException("Unexpected");
        };
        return "%s%n%n%s".formatted(summaryLine, "Stage: " + header);
    }

    private String getFirstHeader(List<Match> sortedMatches) {
        String header = switch (sortedMatches.getFirst().bracketPosition().getStage()) {
            case FINAL -> "Final";
            case SEMI_FINALS -> "Semi-finals";
            case QUARTER_FINALS -> "Quarter-finals";
            case ROUND_OF_16 -> "Round of 16";
        };
        return "Stage: " + header;
    }

    private String printMatchSummaryLine(Match match) {
        match = orderTeamsAlphabetically(match);

        return "- %s%s vs %s%s%s%s%s".formatted(
                match.teamA().getPrintName(),
                match.isFinished() ? " " + match.finishedMatchResult().get().scoreTeamA() : "",
                match.teamB().getPrintName(),
                match.isFinished() ? " " + match.finishedMatchResult().get().scoreTeamB() : "",
                match.bracketPosition() == F_THIRD_PLACE ? ", 3rd place match" : "",
                !match.isFinished() ? " (upcoming)" : "",
                printPossiblePenaltyInfo(match)
        );
    }

    private Match orderTeamsAlphabetically(Match match) {
        return match.teamA().name().compareTo(match.teamB().name()) > 0 ? match.inverted() : match;
    }

    private String printPossiblePenaltyInfo(Match match) {
        boolean shouldPrint = match.isFinished() &&
                match.finishedMatchResult().get().penaltyScoreTeamA().isPresent();

        if (!shouldPrint) return "";

        int penaltyScoreTeamA = match.finishedMatchResult().get().penaltyScoreTeamA().get();
        int penaltyScoreTeamB = match.finishedMatchResult().get().penaltyScoreTeamB().get();
        return " (%s wins on penalties %s-%s)".formatted(
                match.getWinner().getPrintName(),
                Math.max(penaltyScoreTeamA, penaltyScoreTeamB),
                Math.min(penaltyScoreTeamA, penaltyScoreTeamB)
        );
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

    private record BracketWinner(Team winningTeam, TournamentBracket currentBracket) {
        static BracketWinner of(Match match) {
            return new BracketWinner(match.getWinner(), match.bracketPosition());
        }
    }
}
