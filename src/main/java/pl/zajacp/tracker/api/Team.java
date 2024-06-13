package pl.zajacp.tracker.api;

public enum Team {
    BRAZIL,
    GERMANY,
    FRANCE,
    ARGENTINA,
    SPAIN,
    ENGLAND,
    BELGIUM,
    ITALY,
    PORTUGAL,
    NETHERLANDS,
    URUGUAY,
    CROATIA,
    DENMARK,
    SWITZERLAND,
    COLOMBIA,
    MEXICO,
    USA,
    JAPAN,
    SENEGAL,
    SOUTH_KOREA;

    public String getPrintName() {
        return switch (this) {
            case USA -> "USA";
            case SOUTH_KOREA -> "South Korea";
            default -> name().charAt(0) + name().substring(1).toLowerCase();
        };
    }
}
