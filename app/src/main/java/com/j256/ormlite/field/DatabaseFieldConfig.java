package com.j256.ormlite.field;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.types.VoidType;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DatabaseFieldConfig {
    public static final boolean DEFAULT_CAN_BE_NULL = true;
    public static final DataType DEFAULT_DATA_TYPE = DataType.UNKNOWN;
    public static final boolean DEFAULT_FOREIGN_COLLECTION_ORDER_ASCENDING = true;
    private static final int DEFAULT_MAX_EAGER_FOREIGN_COLLECTION_LEVEL = 1;
    public static final Class<? extends DataPersister> DEFAULT_PERSISTER_CLASS = VoidType.class;
    private boolean allowGeneratedIdInsert;
    private boolean canBeNull = true;
    private String columnDefinition;
    private String columnName;
    private DataPersister dataPersister;
    private DataType dataType = DEFAULT_DATA_TYPE;
    private String defaultValue;
    private boolean encryption;
    private String fieldName;
    private boolean foreign;
    private boolean foreignAutoCreate;
    private boolean foreignAutoRefresh;
    private boolean foreignCollection;
    private String foreignCollectionColumnName;
    private boolean foreignCollectionEager;
    private String foreignCollectionForeignFieldName;
    private int foreignCollectionMaxEagerLevel = 1;
    private boolean foreignCollectionOrderAscending = true;
    private String foreignCollectionOrderColumnName;
    private String foreignColumnName;
    private DatabaseTableConfig<?> foreignTableConfig;
    private String format;
    private boolean generatedId;
    private String generatedIdSequence;
    private boolean id;
    private boolean index;
    private String indexName;
    private int maxForeignAutoRefreshLevel = -1;
    private boolean persisted = true;
    private Class<? extends DataPersister> persisterClass = DEFAULT_PERSISTER_CLASS;
    private boolean readOnly;
    private boolean throwIfNull;
    private boolean unique;
    private boolean uniqueCombo;
    private boolean uniqueIndex;
    private String uniqueIndexName;
    private Enum<?> unknownEnumValue;
    private boolean useGetSet;
    private boolean version;
    private int width;

    public DatabaseFieldConfig() {
    }

    public DatabaseFieldConfig(String fieldName2) {
        this.fieldName = fieldName2;
    }

    public DatabaseFieldConfig(String fieldName2, String columnName2, DataType dataType2, String defaultValue2, int width2, boolean canBeNull2, boolean id2, boolean generatedId2, String generatedIdSequence2, boolean foreign2, DatabaseTableConfig<?> foreignTableConfig2, boolean useGetSet2, Enum<?> unknownEnumValue2, boolean throwIfNull2, String format2, boolean unique2, String indexName2, String uniqueIndexName2, boolean autoRefresh, int maxForeignAutoRefreshLevel2, int maxForeignCollectionLevel, boolean encryption2) {
        this.fieldName = fieldName2;
        this.columnName = columnName2;
        this.dataType = DataType.UNKNOWN;
        this.defaultValue = defaultValue2;
        this.width = width2;
        this.canBeNull = canBeNull2;
        this.id = id2;
        this.generatedId = generatedId2;
        this.generatedIdSequence = generatedIdSequence2;
        this.foreign = foreign2;
        this.foreignTableConfig = foreignTableConfig2;
        this.useGetSet = useGetSet2;
        this.unknownEnumValue = unknownEnumValue2;
        this.throwIfNull = throwIfNull2;
        this.format = format2;
        this.unique = unique2;
        this.indexName = indexName2;
        this.uniqueIndexName = uniqueIndexName2;
        this.foreignAutoRefresh = autoRefresh;
        this.maxForeignAutoRefreshLevel = maxForeignAutoRefreshLevel2;
        this.foreignCollectionMaxEagerLevel = maxForeignCollectionLevel;
        this.encryption = encryption2;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName2) {
        this.fieldName = fieldName2;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName2) {
        this.columnName = columnName2;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType2) {
        this.dataType = dataType2;
    }

    public DataPersister getDataPersister() {
        if (this.dataPersister == null) {
            return this.dataType.getDataPersister();
        }
        return this.dataPersister;
    }

    public void setDataPersister(DataPersister dataPersister2) {
        this.dataPersister = dataPersister2;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue2) {
        this.defaultValue = defaultValue2;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public boolean isCanBeNull() {
        return this.canBeNull;
    }

    public void setCanBeNull(boolean canBeNull2) {
        this.canBeNull = canBeNull2;
    }

    public boolean isId() {
        return this.id;
    }

    public void setId(boolean id2) {
        this.id = id2;
    }

    public boolean isGeneratedId() {
        return this.generatedId;
    }

    public void setGeneratedId(boolean generatedId2) {
        this.generatedId = generatedId2;
    }

    public String getGeneratedIdSequence() {
        return this.generatedIdSequence;
    }

    public void setGeneratedIdSequence(String generatedIdSequence2) {
        this.generatedIdSequence = generatedIdSequence2;
    }

    public boolean isForeign() {
        return this.foreign;
    }

    public void setForeign(boolean foreign2) {
        this.foreign = foreign2;
    }

    public DatabaseTableConfig<?> getForeignTableConfig() {
        return this.foreignTableConfig;
    }

    public void setForeignTableConfig(DatabaseTableConfig<?> foreignTableConfig2) {
        this.foreignTableConfig = foreignTableConfig2;
    }

    public boolean isUseGetSet() {
        return this.useGetSet;
    }

    public void setUseGetSet(boolean useGetSet2) {
        this.useGetSet = useGetSet2;
    }

    public Enum<?> getUnknownEnumValue() {
        return this.unknownEnumValue;
    }

    public void setUnknownEnumValue(Enum<?> unknownEnumValue2) {
        this.unknownEnumValue = unknownEnumValue2;
    }

    public boolean isThrowIfNull() {
        return this.throwIfNull;
    }

    public void setThrowIfNull(boolean throwIfNull2) {
        this.throwIfNull = throwIfNull2;
    }

    public boolean isPersisted() {
        return this.persisted;
    }

    public void setPersisted(boolean persisted2) {
        this.persisted = persisted2;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format2) {
        this.format = format2;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public void setUnique(boolean unique2) {
        this.unique = unique2;
    }

    public boolean isUniqueCombo() {
        return this.uniqueCombo;
    }

    public void setUniqueCombo(boolean uniqueCombo2) {
        this.uniqueCombo = uniqueCombo2;
    }

    public boolean isIndex() {
        return this.index;
    }

    public void setIndex(boolean index2) {
        this.index = index2;
    }

    public String getIndexName(String tableName) {
        if (this.index && this.indexName == null) {
            this.indexName = findIndexName(tableName);
        }
        return this.indexName;
    }

    public void setIndexName(String indexName2) {
        this.indexName = indexName2;
    }

    public boolean isUniqueIndex() {
        return this.uniqueIndex;
    }

    public void setUniqueIndex(boolean uniqueIndex2) {
        this.uniqueIndex = uniqueIndex2;
    }

    public String getUniqueIndexName(String tableName) {
        if (this.uniqueIndex && this.uniqueIndexName == null) {
            this.uniqueIndexName = findIndexName(tableName);
        }
        return this.uniqueIndexName;
    }

    public void setUniqueIndexName(String uniqueIndexName2) {
        this.uniqueIndexName = uniqueIndexName2;
    }

    public void setForeignAutoRefresh(boolean foreignAutoRefresh2) {
        this.foreignAutoRefresh = foreignAutoRefresh2;
    }

    public boolean isForeignAutoRefresh() {
        return this.foreignAutoRefresh;
    }

    public int getMaxForeignAutoRefreshLevel() {
        return this.maxForeignAutoRefreshLevel;
    }

    public void setMaxForeignAutoRefreshLevel(int maxForeignLevel) {
        this.maxForeignAutoRefreshLevel = maxForeignLevel;
    }

    public boolean isForeignCollection() {
        return this.foreignCollection;
    }

    public void setForeignCollection(boolean foreignCollection2) {
        this.foreignCollection = foreignCollection2;
    }

    public boolean isForeignCollectionEager() {
        return this.foreignCollectionEager;
    }

    public void setForeignCollectionEager(boolean foreignCollectionEager2) {
        this.foreignCollectionEager = foreignCollectionEager2;
    }

    public int getForeignCollectionMaxEagerLevel() {
        return this.foreignCollectionMaxEagerLevel;
    }

    public void setForeignCollectionMaxEagerLevel(int foreignCollectionMaxEagerLevel2) {
        this.foreignCollectionMaxEagerLevel = foreignCollectionMaxEagerLevel2;
    }

    @Deprecated
    public void setMaxEagerForeignCollectionLevel(int maxEagerForeignCollectionLevel) {
        this.foreignCollectionMaxEagerLevel = maxEagerForeignCollectionLevel;
    }

    @Deprecated
    public void setForeignCollectionMaxEagerForeignCollectionLevel(int maxEagerForeignCollectionLevel) {
        this.foreignCollectionMaxEagerLevel = maxEagerForeignCollectionLevel;
    }

    public String getForeignCollectionColumnName() {
        return this.foreignCollectionColumnName;
    }

    public void setForeignCollectionColumnName(String foreignCollectionColumn) {
        this.foreignCollectionColumnName = foreignCollectionColumn;
    }

    public String getForeignCollectionOrderColumnName() {
        return this.foreignCollectionOrderColumnName;
    }

    @Deprecated
    public void setForeignCollectionOrderColumn(String foreignCollectionOrderColumn) {
        this.foreignCollectionOrderColumnName = foreignCollectionOrderColumn;
    }

    public void setForeignCollectionOrderColumnName(String foreignCollectionOrderColumn) {
        this.foreignCollectionOrderColumnName = foreignCollectionOrderColumn;
    }

    public boolean isForeignCollectionOrderAscending() {
        return this.foreignCollectionOrderAscending;
    }

    public void setForeignCollectionOrderAscending(boolean foreignCollectionOrderAscending2) {
        this.foreignCollectionOrderAscending = foreignCollectionOrderAscending2;
    }

    public String getForeignCollectionForeignFieldName() {
        return this.foreignCollectionForeignFieldName;
    }

    @Deprecated
    public void setForeignCollectionForeignColumnName(String foreignCollectionForeignColumnName) {
        this.foreignCollectionForeignFieldName = foreignCollectionForeignColumnName;
    }

    public void setForeignCollectionForeignFieldName(String foreignCollectionForeignFieldName2) {
        this.foreignCollectionForeignFieldName = foreignCollectionForeignFieldName2;
    }

    public Class<? extends DataPersister> getPersisterClass() {
        return this.persisterClass;
    }

    public void setPersisterClass(Class<? extends DataPersister> persisterClass2) {
        this.persisterClass = persisterClass2;
    }

    public boolean isAllowGeneratedIdInsert() {
        return this.allowGeneratedIdInsert;
    }

    public void setAllowGeneratedIdInsert(boolean allowGeneratedIdInsert2) {
        this.allowGeneratedIdInsert = allowGeneratedIdInsert2;
    }

    public String getColumnDefinition() {
        return this.columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition2) {
        this.columnDefinition = columnDefinition2;
    }

    public boolean isForeignAutoCreate() {
        return this.foreignAutoCreate;
    }

    public void setForeignAutoCreate(boolean foreignAutoCreate2) {
        this.foreignAutoCreate = foreignAutoCreate2;
    }

    public boolean isVersion() {
        return this.version;
    }

    public void setVersion(boolean version2) {
        this.version = version2;
    }

    public String getForeignColumnName() {
        return this.foreignColumnName;
    }

    public void setForeignColumnName(String foreignColumnName2) {
        this.foreignColumnName = foreignColumnName2;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly2) {
        this.readOnly = readOnly2;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setEncryption(boolean encryption2) {
        this.encryption = encryption2;
    }

    public static DatabaseFieldConfig fromField(DatabaseType databaseType, String tableName, Field field) {
        DatabaseField databaseField = (DatabaseField) field.getAnnotation(DatabaseField.class);
        if (databaseField == null) {
            ForeignCollectionField foreignCollection2 = (ForeignCollectionField) field.getAnnotation(ForeignCollectionField.class);
            if (foreignCollection2 != null) {
                return fromForeignCollection(databaseType, field, foreignCollection2);
            }
            return JavaxPersistence.createFieldConfig(databaseType, field);
        } else if (databaseField.persisted()) {
            return fromDatabaseField(databaseType, tableName, field, databaseField);
        } else {
            return null;
        }
    }

    public static Method findGetMethod(Field field, boolean throwExceptions) {
        String methodName = methodFromField(field, "get");
        try {
            Method fieldGetMethod = field.getDeclaringClass().getMethod(methodName, new Class[0]);
            if (fieldGetMethod.getReturnType() == field.getType()) {
                return fieldGetMethod;
            }
            if (!throwExceptions) {
                return null;
            }
            throw new IllegalArgumentException("Return type of get method " + methodName + " does not return " + field.getType());
        } catch (Exception e) {
            if (!throwExceptions) {
                return null;
            }
            throw new IllegalArgumentException("Could not find appropriate get method for " + field);
        }
    }

    public static Method findSetMethod(Field field, boolean throwExceptions) {
        String methodName = methodFromField(field, "set");
        try {
            Method fieldSetMethod = field.getDeclaringClass().getMethod(methodName, new Class[]{field.getType()});
            if (fieldSetMethod.getReturnType() == Void.TYPE) {
                return fieldSetMethod;
            }
            if (!throwExceptions) {
                return null;
            }
            throw new IllegalArgumentException("Return type of set method " + methodName + " returns " + fieldSetMethod.getReturnType() + " instead of void");
        } catch (Exception e) {
            if (!throwExceptions) {
                return null;
            }
            throw new IllegalArgumentException("Could not find appropriate set method for " + field);
        }
    }

    public static DatabaseFieldConfig fromDatabaseField(DatabaseType databaseType, String tableName, Field field, DatabaseField databaseField) {
        DatabaseFieldConfig config = new DatabaseFieldConfig();
        config.fieldName = field.getName();
        if (databaseType.isEntityNamesMustBeUpCase()) {
            config.fieldName = config.fieldName.toUpperCase();
        }
        config.columnName = valueIfNotBlank(databaseField.columnName());
        config.dataType = databaseField.dataType();
        String defaultValue2 = databaseField.defaultValue();
        if (!defaultValue2.equals(DatabaseField.DEFAULT_STRING)) {
            config.defaultValue = defaultValue2;
        }
        config.width = databaseField.width();
        config.canBeNull = databaseField.canBeNull();
        config.id = databaseField.id();
        config.generatedId = databaseField.generatedId();
        config.generatedIdSequence = valueIfNotBlank(databaseField.generatedIdSequence());
        config.foreign = databaseField.foreign();
        config.useGetSet = databaseField.useGetSet();
        config.unknownEnumValue = findMatchingEnumVal(field, databaseField.unknownEnumName());
        config.throwIfNull = databaseField.throwIfNull();
        config.format = valueIfNotBlank(databaseField.format());
        config.unique = databaseField.unique();
        config.uniqueCombo = databaseField.uniqueCombo();
        config.index = databaseField.index();
        config.indexName = valueIfNotBlank(databaseField.indexName());
        config.uniqueIndex = databaseField.uniqueIndex();
        config.uniqueIndexName = valueIfNotBlank(databaseField.uniqueIndexName());
        config.foreignAutoRefresh = databaseField.foreignAutoRefresh();
        config.maxForeignAutoRefreshLevel = databaseField.maxForeignAutoRefreshLevel();
        config.persisterClass = databaseField.persisterClass();
        config.allowGeneratedIdInsert = databaseField.allowGeneratedIdInsert();
        config.columnDefinition = valueIfNotBlank(databaseField.columnDefinition());
        config.foreignAutoCreate = databaseField.foreignAutoCreate();
        config.version = databaseField.version();
        config.foreignColumnName = valueIfNotBlank(databaseField.foreignColumnName());
        config.readOnly = databaseField.readOnly();
        config.encryption = databaseField.encryption();
        return config;
    }

    public void postProcess() {
        if (this.foreignColumnName != null) {
            this.foreignAutoRefresh = true;
        }
        if (this.foreignAutoRefresh && this.maxForeignAutoRefreshLevel == -1) {
            this.maxForeignAutoRefreshLevel = 2;
        }
    }

    public static Enum<?> findMatchingEnumVal(Field field, String unknownEnumName) {
        Enum[] enumArr;
        if (unknownEnumName == null || unknownEnumName.length() == 0) {
            return null;
        }
        for (Enum enumVal : (Enum[]) field.getType().getEnumConstants()) {
            if (enumVal.name().equals(unknownEnumName)) {
                return enumVal;
            }
        }
        throw new IllegalArgumentException("Unknwown enum unknown name " + unknownEnumName + " for field " + field);
    }

    private static DatabaseFieldConfig fromForeignCollection(DatabaseType databaseType, Field field, ForeignCollectionField foreignCollection2) {
        DatabaseFieldConfig config = new DatabaseFieldConfig();
        config.fieldName = field.getName();
        if (foreignCollection2.columnName().length() > 0) {
            config.columnName = foreignCollection2.columnName();
        }
        config.foreignCollection = true;
        config.foreignCollectionEager = foreignCollection2.eager();
        int maxEagerLevel = foreignCollection2.maxEagerForeignCollectionLevel();
        if (maxEagerLevel != 1) {
            config.foreignCollectionMaxEagerLevel = maxEagerLevel;
        } else {
            config.foreignCollectionMaxEagerLevel = foreignCollection2.maxEagerLevel();
        }
        config.foreignCollectionOrderColumnName = valueIfNotBlank(foreignCollection2.orderColumnName());
        config.foreignCollectionOrderAscending = foreignCollection2.orderAscending();
        config.foreignCollectionColumnName = valueIfNotBlank(foreignCollection2.columnName());
        String foreignFieldName = valueIfNotBlank(foreignCollection2.foreignFieldName());
        if (foreignFieldName == null) {
            config.foreignCollectionForeignFieldName = valueIfNotBlank(valueIfNotBlank(foreignCollection2.foreignColumnName()));
        } else {
            config.foreignCollectionForeignFieldName = foreignFieldName;
        }
        return config;
    }

    private String findIndexName(String tableName) {
        if (this.columnName == null) {
            return tableName + "_" + this.fieldName + "_idx";
        }
        return tableName + "_" + this.columnName + "_idx";
    }

    private static String valueIfNotBlank(String newValue) {
        if (newValue == null || newValue.length() == 0) {
            return null;
        }
        return newValue;
    }

    private static String methodFromField(Field field, String prefix) {
        return prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }
}
