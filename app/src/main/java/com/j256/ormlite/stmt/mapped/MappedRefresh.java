package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedRefresh<T, ID> extends MappedQueryForId<T, ID> {
    private MappedRefresh(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes, FieldType[] resultFieldTypes) {
        super(tableInfo, statement, argFieldTypes, resultFieldTypes, "refresh");
    }

    public int executeRefresh(DatabaseConnection databaseConnection, T data, ObjectCache objectCache) {
        FieldType[] fieldTypeArr;
        Object result = super.execute(databaseConnection, this.idField.extractJavaFieldValue(data), null);
        if (result == null) {
            return 0;
        }
        for (FieldType fieldType : this.resultsFieldTypes) {
            if (fieldType != this.idField) {
                fieldType.assignField(data, fieldType.extractJavaFieldValue(result), false, objectCache);
            }
        }
        return 1;
    }

    public static <T, ID> MappedRefresh<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo) {
        FieldType idField = tableInfo.getIdField();
        if (idField == null) {
            throw new SQLException("Cannot refresh " + tableInfo.getDataClass() + " because it doesn't have an id field");
        }
        return new MappedRefresh<>(tableInfo, buildStatement(databaseType, tableInfo, idField), new FieldType[]{tableInfo.getIdField()}, tableInfo.getFieldTypes());
    }
}
