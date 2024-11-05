package enums;

public enum ETournamentStatus {
    NOT_STARTED,
    IN_PROGRESS,
    FINISHED;

    ETournamentStatus() {
    }

    public String getMessage() {
        return switch (this) {
            case NOT_STARTED -> "El torneo aún no ha comenzado";
            case IN_PROGRESS -> "El torneo está en progreso";
            case FINISHED -> "El torneo ha finalizado";
        };
    }

    public String getDisplayName() {
        return switch (this) {
            case NOT_STARTED -> "Sin iniciar";
            case IN_PROGRESS -> "En progreso";
            case FINISHED -> "Finalizado";
        };
    }
}
