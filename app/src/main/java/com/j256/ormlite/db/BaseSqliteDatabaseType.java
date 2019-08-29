package com.j256.ormlite.db;

import com.j256.ormlite.db.BaseDatabaseType.BooleanNumberFieldConverter;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BigDecimalStringType;
import java.util.List;

public abstract class BaseSqliteDatabaseType extends BaseDatabaseType {
    private static final FieldConverter booleanConverter = new BooleanNumberFieldConverter();

    /* access modifiers changed from: protected */
    public void appendLongType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        if (fieldType.getSqlType() != SqlType.LONG || !fieldType.isGeneratedId()) {
            sb.append("BIGINT");
        } else {
            sb.append("INTEGER");
        }
    }

    /* access modifiers changed from: protected */
    public void configureGeneratedId(String tableName, StringBuilder sb, FieldType fieldType, List<String> statementsBefore, List<String> statementsAfter, List<String> additionalArgs, List<String> queriesAfter) {
        if (fieldType.getSqlType() == SqlType.INTEGER || fieldType.getSqlType() == SqlType.LONG) {
            sb.append("PRIMARY KEY AUTOINCREMENT ");
            return;
        }
        throw new IllegalArgumentException("Sqlite requires that auto-increment generated-id be integer or long type");
    }

    /* access modifiers changed from: protected */
    public boolean generatedIdSqlAtEnd() {
        return false;
    }

    public boolean isVarcharFieldWidthSupported() {
        return false;
    }

    public boolean isCreateTableReturnsZero() {
        return false;
    }

    public boolean isCreateIfNotExistsSupported() {
        return true;
    }

    public FieldConverter getFieldConverter(DataPersister dataPersister) {
        switch (dataPersister.getSqlType()) {
            case BOOLEAN:
                return booleanConverter;
            case BIG_DECIMAL:
                return BigDecimalStringType.getSingleton();
            default:
                return super.getFieldConverter(dataPersister);
        }
    }

    public void appendInsertNoColumns(StringBuilder sb) {
        sb.append("DEFAULT VALUES");
    }
}
