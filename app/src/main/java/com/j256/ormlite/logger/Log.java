package com.j256.ormlite.logger;

public interface Log {

    public enum Level {
        TRACE(1),
        DEBUG(2),
        INFO(3),
        WARNING(4),
        ERROR(5),
        FATAL(6);
        
        private int level;

        private Level(int level2) {
            this.level = level2;
        }

        public final boolean isEnabled(Level otherLevel) {
            return this.level <= otherLevel.level;
        }
    }

    boolean isLevelEnabled(Level level);

    void log(Level level, String str);

    void log(Level level, String str, Throwable th);
}
