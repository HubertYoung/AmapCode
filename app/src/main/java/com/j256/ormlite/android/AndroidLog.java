package com.j256.ormlite.android;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Log.Level;
import com.j256.ormlite.logger.LoggerFactory;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class AndroidLog implements Log {
    private static final String ALL_LOGS_NAME = "ORMLite";
    private static final int MAX_TAG_LENGTH = 23;
    private static final int REFRESH_LEVEL_CACHE_EVERY = 200;
    private String className;
    private final boolean[] levelCache;
    private volatile int levelCacheC = 0;

    public AndroidLog(String className2) {
        this.className = LoggerFactory.getSimpleClassName(className2);
        int length = this.className.length();
        if (length > 23) {
            this.className = this.className.substring(length - 23, length);
        }
        int maxLevel = 0;
        for (Level level : Level.values()) {
            int androidLevel = levelToAndroidLevel(level);
            if (androidLevel > maxLevel) {
                maxLevel = androidLevel;
            }
        }
        this.levelCache = new boolean[(maxLevel + 1)];
        refreshLevelCache();
    }

    public boolean isLevelEnabled(Level level) {
        int i = this.levelCacheC + 1;
        this.levelCacheC = i;
        if (i >= 200) {
            refreshLevelCache();
            this.levelCacheC = 0;
        }
        int androidLevel = levelToAndroidLevel(level);
        if (androidLevel < this.levelCache.length) {
            return this.levelCache[androidLevel];
        }
        return isLevelEnabledInternal(androidLevel);
    }

    public void log(Level level, String msg) {
        switch (level) {
            case TRACE:
                android.util.Log.v(this.className, msg);
                return;
            case DEBUG:
                android.util.Log.d(this.className, msg);
                return;
            case INFO:
                android.util.Log.i(this.className, msg);
                return;
            case WARNING:
                android.util.Log.w(this.className, msg);
                return;
            case ERROR:
                android.util.Log.e(this.className, msg);
                return;
            case FATAL:
                android.util.Log.e(this.className, msg);
                return;
            default:
                android.util.Log.i(this.className, msg);
                return;
        }
    }

    public void log(Level level, String msg, Throwable t) {
        switch (level) {
            case TRACE:
                android.util.Log.v(this.className, msg, t);
                return;
            case DEBUG:
                android.util.Log.d(this.className, msg, t);
                return;
            case INFO:
                android.util.Log.i(this.className, msg, t);
                return;
            case WARNING:
                android.util.Log.w(this.className, msg, t);
                return;
            case ERROR:
                android.util.Log.e(this.className, msg, t);
                return;
            case FATAL:
                android.util.Log.e(this.className, msg, t);
                return;
            default:
                android.util.Log.i(this.className, msg, t);
                return;
        }
    }

    private void refreshLevelCache() {
        for (Level level : Level.values()) {
            int androidLevel = levelToAndroidLevel(level);
            if (androidLevel < this.levelCache.length) {
                this.levelCache[androidLevel] = isLevelEnabledInternal(androidLevel);
            }
        }
    }

    private boolean isLevelEnabledInternal(int androidLevel) {
        return android.util.Log.isLoggable(this.className, androidLevel) || android.util.Log.isLoggable(ALL_LOGS_NAME, androidLevel);
    }

    private int levelToAndroidLevel(Level level) {
        switch (level) {
            case TRACE:
                return 2;
            case DEBUG:
                return 3;
            case WARNING:
                return 5;
            case ERROR:
                return 6;
            case FATAL:
                return 6;
            default:
                return 4;
        }
    }

    private String renderThrowable(Throwable tr) {
        return "{" + ThrowableToString(tr) + '}';
    }

    public static String ThrowableToString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        try {
            Writer info = new StringWriter();
            PrintWriter printWriter = new PrintWriter(info);
            Throwable cause = throwable.getCause();
            if (cause == null) {
                throwable.printStackTrace(printWriter);
            }
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            return info.toString();
        } catch (Throwable th) {
            return "";
        }
    }
}
