package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedUpdateId<T, ID> extends BaseMappedStatement<T, ID> {
    private MappedUpdateId(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes) {
        super(tableInfo, statement, argFieldTypes);
    }

    public int execute(DatabaseConnection databaseConnection, T data, ID newId, ObjectCache objectCache) {
        try {
            Object[] args = {convertIdToFieldObject(newId), extractIdToFieldObject(data)};
            int rowC = databaseConnection.update(this.statement, args, this.argFieldTypes);
            if (rowC > 0) {
                if (objectCache != null) {
                    Object obj = objectCache.updateId(this.clazz, this.idField.extractJavaFieldValue(data), newId);
                    if (!(obj == null || obj == data)) {
                        this.idField.assignField(obj, newId, false, objectCache);
                    }
                }
                this.idField.assignField(data, newId, false, objectCache);
            }
            logger.debug((String) "updating-id with statement '{}' and {} args, changed {} rows", (Object) this.statement, (Object) Integer.valueOf(2), (Object) Integer.valueOf(rowC));
            logger.trace((String) "updating-id arguments: {}", (Object) args);
            return rowC;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Unable to run update-id stmt on object " + data + ": " + this.statement, e);
        }
    }

    public static <T, ID> MappedUpdateId<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo) {
        FieldType idField = tableInfo.getIdField();
        if (idField == null) {
            throw new SQLException("Cannot update-id in " + tableInfo.getDataClass() + " because it doesn't have an id field");
        }
        StringBuilder sb = new StringBuilder(64);
        appendTableName(databaseType, sb, "UPDATE ", tableInfo.getTableName());
        sb.append("SET ");
        appendFieldColumnName(databaseType, sb, idField, null);
        sb.append("= ? ");
        appendWhereFieldEq(databaseType, idField, sb, null);
        return new MappedUpdateId<>(tableInfo, sb.toString(), new FieldType[]{idField, idField});
    }

    private Object extractIdToFieldObject(T data) {
        return this.idField.extractJavaFieldToSqlArgValue(data);
    }
}
