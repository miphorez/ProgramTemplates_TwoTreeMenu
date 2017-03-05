package main.xml;

import java.util.Objects;
import java.util.logging.Level;

public enum ELogLevel {
    OFF(Level.OFF),
    SEVERE(Level.SEVERE),
    WARNING(Level.WARNING),
    INFO(Level.INFO),
    CONFIG(Level.CONFIG),
    FINE(Level.FINE),
    FINER(Level.FINER),
    FINEST(Level.FINEST),
    ALL(Level.ALL),;

    Level level;

    ELogLevel(Level level) {
        this.level = level;
    }

    public static Level getLevelbyName(String strLevel) {
        for (ELogLevel logLevel : values()) {
            if (Objects.equals(logLevel.name(), strLevel)) return logLevel.level;
        }
        return Level.ALL;
    }

}
