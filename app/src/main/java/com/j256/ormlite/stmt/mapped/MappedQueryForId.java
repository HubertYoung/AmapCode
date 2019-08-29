package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedQueryForId<T, ID> extends BaseMappedQuery<T, ID> {
    private final String label;

    protected MappedQueryForId(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes, FieldType[] resultsFieldTypes, String label2) {
        super(tableInfo, statement, argFieldTypes, resultsFieldTypes);
        this.label = label2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0009, code lost:
        if (r6 != null) goto L_0x000b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T execute(com.j256.ormlite.support.DatabaseConnection r9, ID r10, com.j256.ormlite.dao.ObjectCache r11) {
        /*
            r8 = this;
            r7 = 1
            if (r11 == 0) goto L_0x000c
            java.lang.Class r0 = r8.clazz
            java.lang.Object r6 = r11.get(r0, r10)
            if (r6 == 0) goto L_0x000c
        L_0x000b:
            return r6
        L_0x000c:
            java.lang.Object[] r2 = new java.lang.Object[r7]
            r0 = 0
            java.lang.Object r1 = r8.convertIdToFieldObject(r10)
            r2[r0] = r1
            java.lang.String r1 = r8.statement
            com.j256.ormlite.field.FieldType[] r3 = r8.argFieldTypes
            r0 = r9
            r4 = r8
            r5 = r11
            java.lang.Object r6 = r0.queryForOne(r1, r2, r3, r4, r5)
            if (r6 != 0) goto L_0x0035
            com.j256.ormlite.logger.Logger r0 = logger
            java.lang.String r1 = "{} using '{}' and {} args, got no results"
            java.lang.String r3 = r8.label
            java.lang.String r4 = r8.statement
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
            r0.debug(r1, r3, r4, r5)
        L_0x0031:
            r8.logArgs(r2)
            goto L_0x000b
        L_0x0035:
            java.lang.Object r0 = com.j256.ormlite.support.DatabaseConnection.MORE_THAN_ONE
            if (r6 != r0) goto L_0x006c
            com.j256.ormlite.logger.Logger r0 = logger
            java.lang.String r1 = "{} using '{}' and {} args, got >1 results"
            java.lang.String r3 = r8.label
            java.lang.String r4 = r8.statement
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
            r0.error(r1, r3, r4, r5)
            r8.logArgs(r2)
            java.sql.SQLException r0 = new java.sql.SQLException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = r8.label
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = " got more than 1 result: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r8.statement
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x006c:
            com.j256.ormlite.logger.Logger r0 = logger
            java.lang.String r1 = "{} using '{}' and {} args, got 1 result"
            java.lang.String r3 = r8.label
            java.lang.String r4 = r8.statement
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)
            r0.debug(r1, r3, r4, r5)
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.stmt.mapped.MappedQueryForId.execute(com.j256.ormlite.support.DatabaseConnection, java.lang.Object, com.j256.ormlite.dao.ObjectCache):java.lang.Object");
    }

    public static <T, ID> MappedQueryForId<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo, FieldType idFieldType) {
        if (idFieldType == null) {
            idFieldType = tableInfo.getIdField();
            if (idFieldType == null) {
                throw new SQLException("Cannot query-for-id with " + tableInfo.getDataClass() + " because it doesn't have an id field");
            }
        }
        return new MappedQueryForId<>(tableInfo, buildStatement(databaseType, tableInfo, idFieldType), new FieldType[]{idFieldType}, tableInfo.getFieldTypes(), "query-for-id");
    }

    protected static <T, ID> String buildStatement(DatabaseType databaseType, TableInfo<T, ID> tableInfo, FieldType idFieldType) {
        StringBuilder sb = new StringBuilder(64);
        appendTableName(databaseType, sb, "SELECT * FROM ", tableInfo.getTableName());
        appendWhereFieldEq(databaseType, idFieldType, sb, null);
        return sb.toString();
    }

    private void logArgs(Object[] args) {
        if (args.length > 0) {
            logger.trace((String) "{} arguments: {}", (Object) this.label, (Object) args);
        }
    }
}
