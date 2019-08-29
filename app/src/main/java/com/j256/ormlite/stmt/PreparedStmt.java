package com.j256.ormlite.stmt;

import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;

public interface PreparedStmt<T> extends GenericRowMapper<T> {
    CompiledStatement compile(DatabaseConnection databaseConnection, StatementType statementType);

    CompiledStatement compile(DatabaseConnection databaseConnection, StatementType statementType, int i);

    String getStatement();

    StatementType getType();

    void setArgumentHolderValue(int i, Object obj);
}
