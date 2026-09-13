package utils;

public enum Genre {
    POP,
    ROCK,
    JAZZ,
    HIPHOP,
    CLASSICAL,
    REGGAE,
    ELECTRONIC,
    BLUES,
    FUNK,
    TRAP,
    RAP,
    DRILL,
    MUSICAPT,
    METAL;

    public static Genre fromString(String s) {
        for (Genre g : values()) {
            if (g.name().equalsIgnoreCase(s.trim())) {
                return g;
            }
        }
        return POP; // default se não encontrar
    }


}

