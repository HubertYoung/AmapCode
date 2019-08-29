package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.Collection;

public class MappedDeleteCollection<T, ID> extends BaseMappedStatement<T, ID> {
    private MappedDeleteCollection(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes) {
        super(tableInfo, statement, argFieldTypes);
    }

    public static <T, ID> int deleteObjects(DatabaseType databaseType, TableInfo<T, ID> tableInfo, DatabaseConnection databaseConnection, Collection<T> datas, ObjectCache objectCache) {
        MappedDeleteCollection deleteCollection = build(databaseType, tableInfo, datas.size());
        Object[] fieldObjects = new Object[datas.size()];
        FieldType idField = tableInfo.getIdField();
        int objC = 0;
        for (T data : datas) {
            fieldObjects[objC] = idField.extractJavaFieldToSqlArgValue(data);
            objC++;
        }
        return updateRows(databaseConnection, tableInfo.getDataClass(), deleteCollection, fieldObjects, objectCache);
    }

    public static <T, ID> int deleteIds(DatabaseType databaseType, TableInfo<T, ID> tableInfo, DatabaseConnection databaseConnection, Collection<ID> ids, ObjectCache objectCache) {
        MappedDeleteCollection deleteCollection = build(databaseType, tableInfo, ids.size());
        Object[] fieldObjects = new Object[ids.size()];
        FieldType idField = tableInfo.getIdField();
        int objC = 0;
        for (ID id : ids) {
            fieldObjects[objC] = idField.convertJavaFieldToSqlArgValue(id);
            objC++;
        }
        return updateRows(databaseConnection, tableInfo.getDataClass(), deleteCollection, fieldObjects, objectCache);
    }

    private static <T, ID> MappedDeleteCollection<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo, int dataSize) {
        FieldType idField = tableInfo.getIdField();
        if (idField == null) {
            throw new SQLException("Cannot delete " + tableInfo.getDataClass() + " because it doesn't have an id field defined");
        }
        StringBuilder sb = new StringBuilder(128);
        appendTableName(databaseType, sb, "DELETE FROM ", tableInfo.getTableName());
        FieldType[] argFieldTypes = new FieldType[dataSize];
        appendWhereIds(databaseType, idField, sb, dataSize, argFieldTypes);
        return new MappedDeleteCollection<>(tableInfo, sb.toString(), argFieldTypes);
    }

    private static <T, ID> int updateRows(DatabaseConnection databaseConnection, Class<T> clazz, MappedDeleteCollection<T, ID> deleteCollection, Object[] args, ObjectCache objectCache) {
        try {
            int rowC = databaseConnection.delete(deleteCollection.statement, args, deleteCollection.argFieldTypes);
            if (rowC > 0 && objectCache != null) {
                for (Object id : args) {
                    objectCache.remove(clazz, id);
                }
            }
            logger.debug((String) "delete-collection with statement '{}' and {} args, changed {} rows", (Object) deleteCollection.statement, (Object) Integer.valueOf(args.length), (Object) Integer.valueOf(rowC));
            if (args.length > 0) {
                logger.trace((String) "delete-collection arguments: {}", (Object) args);
            }
            return rowC;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Unable to run delete collection stmt: " + deleteCollection.statement, e);
        }
    }

    private static void appendWhereIds(DatabaseType databaseType, FieldType idField, StringBuilder sb, int numDatas, FieldType[] fieldTypes) {
        sb.append("WHERE ");
        databaseType.appendEscapedEntityName(sb, idField.getColumnName());
        sb.append(" IN (");
        boolean first = true;
        for (int i = 0; i < numDatas; i++) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append('?');
            if (fieldTypes != null) {
                fieldTypes[i] = idField;
            }
        }
        sb.append(") ");
    }
}
