package enums;

public enum ETournamentStatus {
    NOT_STARTED("El torneo aún no ha comenzado"),
    IN_PROGRESS("El torneo está en progreso"),
    FINISHED("El torneo ha finalizado");

    private final String message;

    ETournamentStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
