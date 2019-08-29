package com.alibaba.sqlcrypto.sqlite;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public final class SQLiteDatabaseConfiguration {
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    public static final String MEMORY_DB_PATH = ":memory:";
    public final ArrayList<SQLiteCustomFunction> customFunctions;
    public boolean foreignKeyConstraintsEnabled;
    public final String label;
    public Locale locale;
    public int maxSqlCacheSize;
    public int openFlags;
    public String password;
    public final String path;

    public SQLiteDatabaseConfiguration(String path2, int openFlags2) {
        this.customFunctions = new ArrayList<>();
        if (path2 == null) {
            throw new IllegalArgumentException("path must not be null.");
        }
        this.path = path2;
        this.label = stripPathForLogs(path2);
        this.openFlags = openFlags2;
        this.maxSqlCacheSize = 25;
        this.locale = Locale.getDefault();
    }

    public SQLiteDatabaseConfiguration(String path2, int openFlags2, String key) {
        this(path2, openFlags2);
        this.password = key;
    }

    public SQLiteDatabaseConfiguration(SQLiteDatabaseConfiguration other) {
        this.customFunctions = new ArrayList<>();
        if (other == null) {
            throw new IllegalArgumentException("other must not be null.");
        }
        this.path = other.path;
        this.label = other.label;
        this.password = other.password;
        updateParametersFrom(other);
    }

    public final void updateParametersFrom(SQLiteDatabaseConfiguration other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null.");
        } else if (!this.path.equals(other.path)) {
            throw new IllegalArgumentException("other configuration must refer to the same database.");
        } else {
            this.openFlags = other.openFlags;
            this.maxSqlCacheSize = other.maxSqlCacheSize;
            this.locale = other.locale;
            this.foreignKeyConstraintsEnabled = other.foreignKeyConstraintsEnabled;
            this.password = other.password;
            this.customFunctions.clear();
            this.customFunctions.addAll(other.customFunctions);
        }
    }

    public final boolean isInMemoryDb() {
        return this.path.equalsIgnoreCase(MEMORY_DB_PATH);
    }

    private static String stripPathForLogs(String path2) {
        return path2.indexOf(64) == -1 ? path2 : EMAIL_IN_DB_PATTERN.matcher(path2).replaceAll("XX@YY");
    }
}
