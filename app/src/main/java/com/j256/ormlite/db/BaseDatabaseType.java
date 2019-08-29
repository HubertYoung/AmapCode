package com.j256.ormlite.db;

import com.j256.ormlite.field.BaseFieldConverter;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDatabaseType implements DatabaseType {
    protected static String DEFAULT_SEQUENCE_SUFFIX = "_id_seq";
    protected Driver driver;

    public class BooleanNumberFieldConverter extends BaseFieldConverter {
        protected BooleanNumberFieldConverter() {
        }

        public SqlType getSqlType() {
            return SqlType.BOOLEAN;
        }

        public Object parseDefaultString(FieldType fieldType, String defaultStr) {
            return Boolean.parseBoolean(defaultStr) ? Byte.valueOf(1) : Byte.valueOf(0);
        }

        public Object javaToSqlArg(FieldType fieldType, Object obj) {
            return ((Boolean) obj).booleanValue() ? Byte.valueOf(1) : Byte.valueOf(0);
        }

        public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
            return Byte.valueOf(results.getByte(columnPos));
        }

        public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
            return ((Byte) sqlArg).byteValue() == 1 ? Boolean.valueOf(true) : Boolean.valueOf(false);
        }

        public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
            return sqlArgToJava(fieldType, Byte.valueOf(Byte.parseByte(stringValue)), columnPos);
        }
    }

    /* access modifiers changed from: protected */
    public abstract String getDriverClassName();

    public void loadDriver() {
        String className = getDriverClassName();
        if (className != null) {
            try {
                Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw SqlExceptionUtil.create("Driver class was not found for " + getDatabaseName() + " database.  Missing jar with class " + className + ".", e);
            }
        }
    }

    public void setDriver(Driver driver2) {
        this.driver = driver2;
    }

    public void appendColumnArg(String tableName, StringBuilder sb, FieldType fieldType, List<String> additionalArgs, List<String> statementsBefore, List<String> statementsAfter, List<String> queriesAfter) {
        appendEscapedEntityName(sb, fieldType.getColumnName());
        sb.append(' ');
        DataPersister dataPersister = fieldType.getDataPersister();
        int fieldWidth = fieldType.getWidth();
        if (fieldWidth == 0) {
            fieldWidth = dataPersister.getDefaultWidth();
        }
        switch (dataPersister.getSqlType()) {
            case STRING:
                appendStringType(sb, fieldType, fieldWidth);
                break;
            case LONG_STRING:
                appendLongStringType(sb, fieldType, fieldWidth);
                break;
            case BOOLEAN:
                appendBooleanType(sb, fieldType, fieldWidth);
                break;
            case DATE:
                appendDateType(sb, fieldType, fieldWidth);
                break;
            case CHAR:
                appendCharType(sb, fieldType, fieldWidth);
                break;
            case BYTE:
                appendByteType(sb, fieldType, fieldWidth);
                break;
            case BYTE_ARRAY:
                appendByteArrayType(sb, fieldType, fieldWidth);
                break;
            case SHORT:
                appendShortType(sb, fieldType, fieldWidth);
                break;
            case INTEGER:
                appendIntegerType(sb, fieldType, fieldWidth);
                break;
            case LONG:
                appendLongType(sb, fieldType, fieldWidth);
                break;
            case FLOAT:
                appendFloatType(sb, fieldType, fieldWidth);
                break;
            case DOUBLE:
                appendDoubleType(sb, fieldType, fieldWidth);
                break;
            case SERIALIZABLE:
                appendSerializableType(sb, fieldType, fieldWidth);
                break;
            case BIG_DECIMAL:
                appendBigDecimalNumericType(sb, fieldType, fieldWidth);
                break;
            default:
                throw new IllegalArgumentException("Unknown SQL-type " + dataPersister.getSqlType());
        }
        sb.append(' ');
        if (fieldType.isGeneratedIdSequence() && !fieldType.isSelfGeneratedId()) {
            configureGeneratedIdSequence(sb, fieldType, statementsBefore, additionalArgs, queriesAfter);
        } else if (fieldType.isGeneratedId() && !fieldType.isSelfGeneratedId()) {
            configureGeneratedId(tableName, sb, fieldType, statementsBefore, statementsAfter, additionalArgs, queriesAfter);
        } else if (fieldType.isId()) {
            configureId(sb, fieldType, statementsBefore, additionalArgs, queriesAfter);
        }
        if (!fieldType.isGeneratedId()) {
            Object defaultValue = fieldType.getDefaultValue();
            if (defaultValue != null) {
                sb.append("DEFAULT ");
                appendDefaultValue(sb, fieldType, defaultValue);
                sb.append(' ');
            }
            if (fieldType.isCanBeNull()) {
                appendCanBeNull(sb, fieldType);
            } else {
                sb.append("NOT NULL ");
            }
            if (fieldType.isUnique()) {
                addSingleUnique(sb, fieldType, additionalArgs, statementsAfter);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void appendStringType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        if (isVarcharFieldWidthSupported()) {
            sb.append("VARCHAR(").append(fieldWidth).append(")");
        } else {
            sb.append("VARCHAR");
        }
    }

    /* access modifiers changed from: protected */
    public void appendLongStringType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("TEXT");
    }

    /* access modifiers changed from: protected */
    public void appendDateType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("TIMESTAMP");
    }

    /* access modifiers changed from: protected */
    public void appendBooleanType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("BOOLEAN");
    }

    /* access modifiers changed from: protected */
    public void appendCharType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("CHAR");
    }

    /* access modifiers changed from: protected */
    public void appendByteType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("TINYINT");
    }

    /* access modifiers changed from: protected */
    public void appendShortType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("SMALLINT");
    }

    private void appendIntegerType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("INTEGER");
    }

    /* access modifiers changed from: protected */
    public void appendLongType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("BIGINT");
    }

    private void appendFloatType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("FLOAT");
    }

    private void appendDoubleType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("DOUBLE PRECISION");
    }

    /* access modifiers changed from: protected */
    public void appendByteArrayType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("BLOB");
    }

    /* access modifiers changed from: protected */
    public void appendSerializableType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("BLOB");
    }

    /* access modifiers changed from: protected */
    public void appendBigDecimalNumericType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
        sb.append("NUMERIC");
    }

    private void appendDefaultValue(StringBuilder sb, FieldType fieldType, Object defaultValue) {
        if (fieldType.isEscapedDefaultValue()) {
            appendEscapedWord(sb, defaultValue.toString());
        } else {
            sb.append(defaultValue);
        }
    }

    /* access modifiers changed from: protected */
    public void configureGeneratedIdSequence(StringBuilder sb, FieldType fieldType, List<String> statementsBefore, List<String> additionalArgs, List<String> queriesAfter) {
        throw new SQLException("GeneratedIdSequence is not supported by database " + getDatabaseName() + " for field " + fieldType);
    }

    /* access modifiers changed from: protected */
    public void configureGeneratedId(String tableName, StringBuilder sb, FieldType fieldType, List<String> statementsBefore, List<String> statementsAfter, List<String> additionalArgs, List<String> queriesAfter) {
        throw new IllegalStateException("GeneratedId is not supported by database " + getDatabaseName() + " for field " + fieldType);
    }

    /* access modifiers changed from: protected */
    public void configureId(StringBuilder sb, FieldType fieldType, List<String> statementsBefore, List<String> additionalArgs, List<String> queriesAfter) {
    }

    public void addPrimaryKeySql(FieldType[] fieldTypes, List<String> additionalArgs, List<String> statementsBefore, List<String> statementsAfter, List<String> queriesAfter) {
        StringBuilder sb = null;
        for (FieldType fieldType : fieldTypes) {
            if ((!fieldType.isGeneratedId() || generatedIdSqlAtEnd() || fieldType.isSelfGeneratedId()) && fieldType.isId()) {
                if (sb == null) {
                    sb = new StringBuilder(48);
                    sb.append("PRIMARY KEY (");
                } else {
                    sb.append(',');
                }
                appendEscapedEntityName(sb, fieldType.getColumnName());
            }
        }
        if (sb != null) {
            sb.append(") ");
            additionalArgs.add(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean generatedIdSqlAtEnd() {
        return true;
    }

    public void addUniqueComboSql(FieldType[] fieldTypes, List<String> additionalArgs, List<String> statementsBefore, List<String> statementsAfter, List<String> queriesAfter) {
        StringBuilder sb = null;
        for (FieldType fieldType : fieldTypes) {
            if (fieldType.isUniqueCombo()) {
                if (sb == null) {
                    sb = new StringBuilder(48);
                    sb.append("UNIQUE (");
                } else {
                    sb.append(',');
                }
                appendEscapedEntityName(sb, fieldType.getColumnName());
            }
        }
        if (sb != null) {
            sb.append(") ");
            additionalArgs.add(sb.toString());
        }
    }

    public void dropColumnArg(FieldType fieldType, List<String> statementsBefore, List<String> statementsAfter) {
    }

    public void appendEscapedWord(StringBuilder sb, String word) {
        sb.append('\'').append(word).append('\'');
    }

    public void appendEscapedEntityName(StringBuilder sb, String name) {
        sb.append('`').append(name).append('`');
    }

    public String generateIdSequenceName(String tableName, FieldType idFieldType) {
        String name = tableName + DEFAULT_SEQUENCE_SUFFIX;
        if (isEntityNamesMustBeUpCase()) {
            return name.toUpperCase();
        }
        return name;
    }

    public String getCommentLinePrefix() {
        return "-- ";
    }

    public FieldConverter getFieldConverter(DataPersister dataPersister) {
        return dataPersister;
    }

    public boolean isIdSequenceNeeded() {
        return false;
    }

    public boolean isVarcharFieldWidthSupported() {
        return true;
    }

    public boolean isLimitSqlSupported() {
        return true;
    }

    public boolean isOffsetSqlSupported() {
        return true;
    }

    public boolean isOffsetLimitArgument() {
        return false;
    }

    public boolean isLimitAfterSelect() {
        return false;
    }

    public void appendLimitValue(StringBuilder sb, long limit, Long offset) {
        sb.append("LIMIT ").append(limit).append(' ');
    }

    public void appendOffsetValue(StringBuilder sb, long offset) {
        sb.append("OFFSET ").append(offset).append(' ');
    }

    public void appendSelectNextValFromSequence(StringBuilder sb, String sequenceName) {
    }

    public void appendCreateTableSuffix(StringBuilder sb) {
    }

    public boolean isCreateTableReturnsZero() {
        return true;
    }

    public boolean isCreateTableReturnsNegative() {
        return false;
    }

    public boolean isEntityNamesMustBeUpCase() {
        return false;
    }

    public boolean isNestedSavePointsSupported() {
        return true;
    }

    public String getPingStatement() {
        return "SELECT 1";
    }

    public boolean isBatchUseTransaction() {
        return false;
    }

    public boolean isTruncateSupported() {
        return false;
    }

    public boolean isCreateIfNotExistsSupported() {
        return false;
    }

    public boolean isCreateIndexIfNotExistsSupported() {
        return isCreateIfNotExistsSupported();
    }

    public boolean isSelectSequenceBeforeInsert() {
        return false;
    }

    public boolean isAllowGeneratedIdInsertSupported() {
        return true;
    }

    public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> clazz) {
        return null;
    }

    public void appendInsertNoColumns(StringBuilder sb) {
        sb.append("() VALUES ()");
    }

    private void appendCanBeNull(StringBuilder sb, FieldType fieldType) {
    }

    private void addSingleUnique(StringBuilder sb, FieldType fieldType, List<String> additionalArgs, List<String> statementsAfter) {
        StringBuilder alterSb = new StringBuilder();
        alterSb.append(" UNIQUE (");
        appendEscapedEntityName(alterSb, fieldType.getColumnName());
        alterSb.append(")");
        additionalArgs.add(alterSb.toString());
    }
}
