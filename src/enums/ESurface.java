package enums;

public enum ESurface {
    CARPET, CLAY, GRASS, HARD;

    public String getDisplayName() {
        return switch (this) {
            case CARPET -> "Carpeta";
            case CLAY -> "Arcilla";
            case GRASS -> "Césped";
            case HARD -> "Cemento";
        };
    }
}

