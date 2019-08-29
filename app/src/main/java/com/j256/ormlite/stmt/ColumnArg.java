package com.j256.ormlite.stmt;

public class ColumnArg {
    private final String columnName;
    private final String tableName;

    public ColumnArg(String columnName2) {
        this.tableName = null;
        this.columnName = columnName2;
    }

    public ColumnArg(String tableName2, String columnName2) {
        this.tableName = tableName2;
        this.columnName = columnName2;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getColumnName() {
        return this.columnName;
    }
}
