package com.j256.ormlite.db;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.Driver;
import java.util.List;

public interface DatabaseType {
    void addPrimaryKeySql(FieldType[] fieldTypeArr, List<String> list, List<String> list2, List<String> list3, List<String> list4);

    void addUniqueComboSql(FieldType[] fieldTypeArr, List<String> list, List<String> list2, List<String> list3, List<String> list4);

    void appendColumnArg(String str, StringBuilder sb, FieldType fieldType, List<String> list, List<String> list2, List<String> list3, List<String> list4);

    void appendCreateTableSuffix(StringBuilder sb);

    void appendEscapedEntityName(StringBuilder sb, String str);

    void appendEscapedWord(StringBuilder sb, String str);

    void appendInsertNoColumns(StringBuilder sb);

    void appendLimitValue(StringBuilder sb, long j, Long l);

    void appendOffsetValue(StringBuilder sb, long j);

    void appendSelectNextValFromSequence(StringBuilder sb, String str);

    void dropColumnArg(FieldType fieldType, List<String> list, List<String> list2);

    <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> cls);

    String generateIdSequenceName(String str, FieldType fieldType);

    String getCommentLinePrefix();

    String getDatabaseName();

    FieldConverter getFieldConverter(DataPersister dataPersister);

    String getPingStatement();

    boolean isAllowGeneratedIdInsertSupported();

    boolean isBatchUseTransaction();

    boolean isCreateIfNotExistsSupported();

    boolean isCreateIndexIfNotExistsSupported();

    boolean isCreateTableReturnsNegative();

    boolean isCreateTableReturnsZero();

    boolean isDatabaseUrlThisType(String str, String str2);

    boolean isEntityNamesMustBeUpCase();

    boolean isIdSequenceNeeded();

    boolean isLimitAfterSelect();

    boolean isLimitSqlSupported();

    boolean isNestedSavePointsSupported();

    boolean isOffsetLimitArgument();

    boolean isOffsetSqlSupported();

    boolean isSelectSequenceBeforeInsert();

    boolean isTruncateSupported();

    boolean isVarcharFieldWidthSupported();

    void loadDriver();

    void setDriver(Driver driver);
}
