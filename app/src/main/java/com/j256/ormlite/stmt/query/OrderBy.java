package com.j256.ormlite.stmt.query;

public class OrderBy {
    private final boolean ascending;
    private final String columnName;

    public OrderBy(String columnName2, boolean ascending2) {
        this.columnName = columnName2;
        this.ascending = ascending2;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public boolean isAscending() {
        return this.ascending;
    }
}
