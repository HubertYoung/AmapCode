package com.j256.ormlite.table;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TableInfo<T, ID> {
    private static final FieldType[] NO_FOREIGN_COLLECTIONS = new FieldType[0];
    private final BaseDaoImpl<T, ID> baseDaoImpl;
    private final Constructor<T> constructor;
    private final Class<T> dataClass;
    private Map<String, FieldType> fieldNameMap;
    private final FieldType[] fieldTypes;
    private final boolean foreignAutoCreate;
    private final FieldType[] foreignCollections;
    private final FieldType idField;
    private final String tableName;

    public TableInfo(ConnectionSource connectionSource, BaseDaoImpl<T, ID> baseDaoImpl2, Class<T> dataClass2) {
        this(connectionSource.getDatabaseType(), baseDaoImpl2, DatabaseTableConfig.fromClass(connectionSource, dataClass2));
    }

    public TableInfo(DatabaseType databaseType, BaseDaoImpl<T, ID> baseDaoImpl2, DatabaseTableConfig<T> tableConfig) {
        FieldType[] fieldTypeArr;
        FieldType[] fieldTypeArr2;
        this.baseDaoImpl = baseDaoImpl2;
        this.dataClass = tableConfig.getDataClass();
        this.tableName = tableConfig.getTableName();
        this.fieldTypes = tableConfig.getFieldTypes(databaseType);
        FieldType findIdFieldType = null;
        boolean foreignAutoCreate2 = false;
        int foreignCollectionCount = 0;
        for (FieldType fieldType : this.fieldTypes) {
            if (fieldType.isId() || fieldType.isGeneratedId() || fieldType.isGeneratedIdSequence()) {
                if (findIdFieldType != null) {
                    throw new SQLException("More than 1 idField configured for class " + this.dataClass + " (" + findIdFieldType + "," + fieldType + ")");
                }
                findIdFieldType = fieldType;
            }
            foreignAutoCreate2 = fieldType.isForeignAutoCreate() ? true : foreignAutoCreate2;
            if (fieldType.isForeignCollection()) {
                foreignCollectionCount++;
            }
        }
        this.idField = findIdFieldType;
        this.constructor = tableConfig.getConstructor();
        this.foreignAutoCreate = foreignAutoCreate2;
        if (foreignCollectionCount == 0) {
            this.foreignCollections = NO_FOREIGN_COLLECTIONS;
            return;
        }
        this.foreignCollections = new FieldType[foreignCollectionCount];
        int foreignCollectionCount2 = 0;
        for (FieldType fieldType2 : this.fieldTypes) {
            if (fieldType2.isForeignCollection()) {
                this.foreignCollections[foreignCollectionCount2] = fieldType2;
                foreignCollectionCount2++;
            }
        }
    }

    public Class<T> getDataClass() {
        return this.dataClass;
    }

    public String getTableName() {
        return this.tableName;
    }

    public FieldType[] getFieldTypes() {
        return this.fieldTypes;
    }

    public FieldType getFieldTypeByColumnName(String columnName) {
        FieldType[] fieldTypeArr;
        FieldType[] fieldTypeArr2;
        if (this.fieldNameMap == null) {
            Map map = new HashMap();
            for (FieldType fieldType : this.fieldTypes) {
                map.put(fieldType.getColumnName().toLowerCase(), fieldType);
            }
            this.fieldNameMap = map;
        }
        FieldType fieldType2 = this.fieldNameMap.get(columnName.toLowerCase());
        if (fieldType2 != null) {
            return fieldType2;
        }
        for (FieldType fieldType22 : this.fieldTypes) {
            if (fieldType22.getFieldName().equals(columnName)) {
                throw new IllegalArgumentException("You should use columnName '" + fieldType22.getColumnName() + "' for table " + this.tableName + " instead of fieldName '" + fieldType22.getFieldName() + "'");
            }
        }
        throw new IllegalArgumentException("Unknown column name '" + columnName + "' in table " + this.tableName);
    }

    public FieldType getIdField() {
        return this.idField;
    }

    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    public String objectToString(T object) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(object.getClass().getSimpleName());
        FieldType[] fieldTypeArr = this.fieldTypes;
        int length = fieldTypeArr.length;
        int i = 0;
        while (i < length) {
            FieldType fieldType = fieldTypeArr[i];
            sb.append(' ').append(fieldType.getColumnName()).append("=");
            try {
                sb.append(fieldType.extractJavaFieldValue(object));
                i++;
            } catch (Exception e) {
                throw new IllegalStateException("Could not generate toString of field " + fieldType, e);
            }
        }
        return sb.toString();
    }

    public T createObject() {
        Object instance;
        ObjectFactory factory = null;
        try {
            if (this.baseDaoImpl != null) {
                factory = this.baseDaoImpl.getObjectFactory();
            }
            if (factory == null) {
                instance = this.constructor.newInstance(new Object[0]);
            } else {
                instance = factory.createObject(this.constructor, this.baseDaoImpl.getDataClass());
            }
            wireNewInstance(this.baseDaoImpl, instance);
            return instance;
        } catch (Exception e) {
            throw SqlExceptionUtil.create("Could not create object for " + this.constructor.getDeclaringClass(), e);
        }
    }

    public boolean isUpdatable() {
        return this.idField != null && this.fieldTypes.length > 1;
    }

    public boolean isForeignAutoCreate() {
        return this.foreignAutoCreate;
    }

    public FieldType[] getForeignCollections() {
        return this.foreignCollections;
    }

    public boolean hasColumnName(String columnName) {
        for (FieldType columnName2 : this.fieldTypes) {
            if (columnName2.getColumnName().equals(columnName)) {
                return true;
            }
        }
        return false;
    }

    private static <T, ID> void wireNewInstance(BaseDaoImpl<T, ID> baseDaoImpl2, T instance) {
        if (instance instanceof BaseDaoEnabled) {
            ((BaseDaoEnabled) instance).setDao(baseDaoImpl2);
        }
    }
}
