package pl.zajacp.tracker.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TournamentStage {
    ROUND_OF_16(1),
    QUARTER_FINALS(2),
    SEMI_FINALS(3),
    FINAL(4);

    private final int orderInCompetition;
}
