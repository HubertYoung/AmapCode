package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedUpdate<T, ID> extends BaseMappedStatement<T, ID> {
    private final FieldType versionFieldType;
    private final int versionFieldTypeIndex;

    private MappedUpdate(TableInfo<T, ID> tableInfo, String statement, FieldType[] argFieldTypes, FieldType versionFieldType2, int versionFieldTypeIndex2) {
        super(tableInfo, statement, argFieldTypes);
        this.versionFieldType = versionFieldType2;
        this.versionFieldTypeIndex = versionFieldTypeIndex2;
    }

    public static <T, ID> MappedUpdate<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo) {
        FieldType[] fieldTypes;
        int argFieldC;
        FieldType idField = tableInfo.getIdField();
        if (idField == null) {
            throw new SQLException("Cannot update " + tableInfo.getDataClass() + " because it doesn't have an id field");
        }
        StringBuilder sb = new StringBuilder(64);
        appendTableName(databaseType, sb, "UPDATE ", tableInfo.getTableName());
        boolean first = true;
        int argFieldC2 = 0;
        FieldType versionFieldType2 = null;
        int versionFieldTypeIndex2 = -1;
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            if (isFieldUpdatable(fieldType, idField)) {
                if (fieldType.isVersion()) {
                    versionFieldType2 = fieldType;
                    versionFieldTypeIndex2 = argFieldC2;
                }
                argFieldC2++;
            }
        }
        int argFieldC3 = argFieldC2 + 1;
        if (versionFieldType2 != null) {
            argFieldC3++;
        }
        FieldType[] argFieldTypes = new FieldType[argFieldC3];
        FieldType[] fieldTypes2 = tableInfo.getFieldTypes();
        int length = fieldTypes2.length;
        int i = 0;
        int argFieldC4 = 0;
        while (i < length) {
            FieldType fieldType2 = fieldTypes2[i];
            if (isFieldUpdatable(fieldType2, idField)) {
                if (first) {
                    sb.append("SET ");
                    first = false;
                } else {
                    sb.append(", ");
                }
                appendFieldColumnName(databaseType, sb, fieldType2, null);
                argFieldC = argFieldC4 + 1;
                argFieldTypes[argFieldC4] = fieldType2;
                sb.append("= ?");
            } else {
                argFieldC = argFieldC4;
            }
            i++;
            argFieldC4 = argFieldC;
        }
        sb.append(' ');
        appendWhereFieldEq(databaseType, idField, sb, null);
        int argFieldC5 = argFieldC4 + 1;
        argFieldTypes[argFieldC4] = idField;
        if (versionFieldType2 != null) {
            sb.append(" AND ");
            appendFieldColumnName(databaseType, sb, versionFieldType2, null);
            sb.append("= ?");
            argFieldTypes[argFieldC5] = versionFieldType2;
        }
        return new MappedUpdate<>(tableInfo, sb.toString(), argFieldTypes, versionFieldType2, versionFieldTypeIndex2);
    }

    public int update(DatabaseConnection databaseConnection, T data, ObjectCache objectCache) {
        FieldType[] fieldTypes;
        try {
            if (this.argFieldTypes.length <= 1) {
                return 0;
            }
            Object[] args = getFieldObjects(data);
            Object newVersion = null;
            if (this.versionFieldType != null) {
                newVersion = this.versionFieldType.moveToNextValue(this.versionFieldType.extractJavaFieldValue(data));
                args[this.versionFieldTypeIndex] = this.versionFieldType.convertJavaFieldToSqlArgValue(newVersion);
            }
            int rowC = databaseConnection.update(this.statement, args, this.argFieldTypes);
            if (rowC > 0) {
                if (newVersion != null) {
                    this.versionFieldType.assignField(data, newVersion, false, null);
                }
                if (objectCache != null) {
                    Object cachedData = objectCache.get(this.clazz, this.idField.extractJavaFieldValue(data));
                    if (!(cachedData == null || cachedData == data)) {
                        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
                            if (fieldType != this.idField) {
                                fieldType.assignField(cachedData, fieldType.extractJavaFieldValue(data), false, objectCache);
                            }
                        }
                    }
                }
            }
            logger.debug((String) "update data with statement '{}' and {} args, changed {} rows", (Object) this.statement, (Object) Integer.valueOf(args.length), (Object) Integer.valueOf(rowC));
            if (args.length <= 0) {
                return rowC;
            }
            logger.trace((String) "update arguments: {}", (Object) args);
            return rowC;
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Unable to run update stmt on object " + data + ": " + this.statement, e);
        }
    }

    private static boolean isFieldUpdatable(FieldType fieldType, FieldType idField) {
        if (fieldType == idField || fieldType.isForeignCollection() || fieldType.isReadOnly()) {
            return false;
        }
        return true;
    }
}
