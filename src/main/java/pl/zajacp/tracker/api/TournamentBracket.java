package pl.zajacp.tracker.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static pl.zajacp.tracker.api.TournamentStage.FINAL;
import static pl.zajacp.tracker.api.TournamentStage.QUARTER_FINALS;
import static pl.zajacp.tracker.api.TournamentStage.ROUND_OF_16;
import static pl.zajacp.tracker.api.TournamentStage.SEMI_FINALS;

@Getter
@RequiredArgsConstructor
public enum TournamentBracket {
    R_1(ROUND_OF_16),
    R_2(ROUND_OF_16),
    R_3(ROUND_OF_16),
    R_4(ROUND_OF_16),
    R_5(ROUND_OF_16),
    R_6(ROUND_OF_16),
    R_7(ROUND_OF_16),
    R_8(ROUND_OF_16),

    Q_1(QUARTER_FINALS),
    Q_2(QUARTER_FINALS),
    Q_3(QUARTER_FINALS),
    Q_4(QUARTER_FINALS),

    S_1(SEMI_FINALS),
    S_2(SEMI_FINALS),

    F(FINAL),
    F_THIRD_PLACE(FINAL);

    private final TournamentStage stage;

    public TournamentBracket nextBracket() {
        return switch (this) {
            case R_1, R_2 -> Q_1;
            case R_3, R_4 -> Q_2;
            case R_5, R_6 -> Q_3;
            case R_7, R_8 -> Q_4;
            case Q_1, Q_2 -> S_1;
            case Q_3, Q_4 -> S_2;
            case S_1, S_2 -> F;
            default -> throw new IllegalStateException("There is are no next bracket for final matches");
        };
    }
}
