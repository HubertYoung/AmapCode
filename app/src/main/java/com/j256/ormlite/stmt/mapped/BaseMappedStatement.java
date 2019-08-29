package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.table.TableInfo;
import java.util.List;

public abstract class BaseMappedStatement<T, ID> {
    protected static Logger logger = LoggerFactory.getLogger(BaseMappedStatement.class);
    protected final FieldType[] argFieldTypes;
    protected final Class<T> clazz;
    protected final FieldType idField;
    protected final String statement;
    protected final TableInfo<T, ID> tableInfo;

    protected BaseMappedStatement(TableInfo<T, ID> tableInfo2, String statement2, FieldType[] argFieldTypes2) {
        this.tableInfo = tableInfo2;
        this.clazz = tableInfo2.getDataClass();
        this.idField = tableInfo2.getIdField();
        this.statement = statement2;
        this.argFieldTypes = argFieldTypes2;
    }

    /* access modifiers changed from: protected */
    public Object[] getFieldObjects(Object data) {
        Object[] objects = new Object[this.argFieldTypes.length];
        for (int i = 0; i < this.argFieldTypes.length; i++) {
            FieldType fieldType = this.argFieldTypes[i];
            if (fieldType.isAllowGeneratedIdInsert()) {
                objects[i] = fieldType.getFieldValueIfNotDefault(data);
            } else {
                objects[i] = fieldType.extractJavaFieldToSqlArgValue(data);
            }
            if (objects[i] == null && fieldType.getDefaultValue() != null) {
                objects[i] = fieldType.getDefaultValue();
            }
        }
        return objects;
    }

    /* access modifiers changed from: protected */
    public Object convertIdToFieldObject(ID id) {
        return this.idField.convertJavaFieldToSqlArgValue(id);
    }

    static void appendWhereFieldEq(DatabaseType databaseType, FieldType fieldType, StringBuilder sb, List<FieldType> fieldTypeList) {
        sb.append("WHERE ");
        appendFieldColumnName(databaseType, sb, fieldType, fieldTypeList);
        sb.append("= ?");
    }

    static void appendTableName(DatabaseType databaseType, StringBuilder sb, String prefix, String tableName) {
        if (prefix != null) {
            sb.append(prefix);
        }
        databaseType.appendEscapedEntityName(sb, tableName);
        sb.append(' ');
    }

    static void appendFieldColumnName(DatabaseType databaseType, StringBuilder sb, FieldType fieldType, List<FieldType> fieldTypeList) {
        databaseType.appendEscapedEntityName(sb, fieldType.getColumnName());
        if (fieldTypeList != null) {
            fieldTypeList.add(fieldType);
        }
        sb.append(' ');
    }

    public String toString() {
        return "MappedStatement: " + this.statement;
    }
}
