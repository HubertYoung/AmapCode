package com.j256.ormlite.table;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.support.ConnectionSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTableConfig<T> {
    private Constructor<T> constructor;
    private Class<T> dataClass;
    private List<DatabaseFieldConfig> fieldConfigs;
    private FieldType[] fieldTypes;
    private String tableName;

    public DatabaseTableConfig() {
    }

    public DatabaseTableConfig(Class<T> dataClass2, List<DatabaseFieldConfig> fieldConfigs2) {
        this(dataClass2, extractTableName(dataClass2), fieldConfigs2);
    }

    public DatabaseTableConfig(Class<T> dataClass2, String tableName2, List<DatabaseFieldConfig> fieldConfigs2) {
        this.dataClass = dataClass2;
        this.tableName = tableName2;
        this.fieldConfigs = fieldConfigs2;
    }

    private DatabaseTableConfig(Class<T> dataClass2, String tableName2, FieldType[] fieldTypes2) {
        this.dataClass = dataClass2;
        this.tableName = tableName2;
        this.fieldTypes = fieldTypes2;
    }

    public void initialize() {
        if (this.dataClass == null) {
            throw new IllegalStateException("dataClass was never set on " + getClass().getSimpleName());
        } else if (this.tableName == null) {
            this.tableName = extractTableName(this.dataClass);
        }
    }

    public Class<T> getDataClass() {
        return this.dataClass;
    }

    public void setDataClass(Class<T> dataClass2) {
        this.dataClass = dataClass2;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public void setFieldConfigs(List<DatabaseFieldConfig> fieldConfigs2) {
        this.fieldConfigs = fieldConfigs2;
    }

    public void extractFieldTypes(ConnectionSource connectionSource) {
        if (this.fieldTypes != null) {
            return;
        }
        if (this.fieldConfigs == null) {
            this.fieldTypes = extractFieldTypes(connectionSource, this.dataClass, this.tableName);
        } else {
            this.fieldTypes = convertFieldConfigs(connectionSource, this.tableName, this.fieldConfigs);
        }
    }

    public FieldType[] getFieldTypes(DatabaseType databaseType) {
        if (this.fieldTypes != null) {
            return this.fieldTypes;
        }
        throw new SQLException("Field types have not been extracted in table config");
    }

    public List<DatabaseFieldConfig> getFieldConfigs() {
        return this.fieldConfigs;
    }

    public Constructor<T> getConstructor() {
        if (this.constructor == null) {
            this.constructor = findNoArgConstructor(this.dataClass);
        }
        return this.constructor;
    }

    public void setConstructor(Constructor<T> constructor2) {
        this.constructor = constructor2;
    }

    public static <T> DatabaseTableConfig<T> fromClass(ConnectionSource connectionSource, Class<T> clazz) {
        String tableName2 = extractTableName(clazz);
        if (connectionSource.getDatabaseType().isEntityNamesMustBeUpCase()) {
            tableName2 = tableName2.toUpperCase();
        }
        return new DatabaseTableConfig<>(clazz, tableName2, extractFieldTypes(connectionSource, clazz, tableName2));
    }

    public static <T> String extractTableName(Class<T> clazz) {
        DatabaseTable databaseTable = (DatabaseTable) clazz.getAnnotation(DatabaseTable.class);
        if (databaseTable != null && databaseTable.tableName() != null && databaseTable.tableName().length() > 0) {
            return databaseTable.tableName();
        }
        String name = JavaxPersistence.getEntityName(clazz);
        if (name == null) {
            return clazz.getSimpleName().toLowerCase();
        }
        return name;
    }

    public static <T> Constructor<T> findNoArgConstructor(Class<T> dataClass2) {
        Constructor[] constructors;
        try {
            for (Constructor con : dataClass2.getDeclaredConstructors()) {
                if (con.getParameterTypes().length == 0) {
                    if (!con.isAccessible()) {
                        try {
                            con.setAccessible(true);
                        } catch (SecurityException e) {
                            throw new IllegalArgumentException("Could not open access to constructor for " + dataClass2);
                        }
                    }
                    return con;
                }
            }
            if (dataClass2.getEnclosingClass() == null) {
                throw new IllegalArgumentException("Can't find a no-arg constructor for " + dataClass2);
            }
            throw new IllegalArgumentException("Can't find a no-arg constructor for " + dataClass2 + ".  Missing static on inner class?");
        } catch (Exception e2) {
            throw new IllegalArgumentException("Can't lookup declared constructors for " + dataClass2, e2);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<T>, code=java.lang.Class, for r8v0, types: [java.lang.Class, java.lang.Object, java.lang.Class<T>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> com.j256.ormlite.field.FieldType[] extractFieldTypes(com.j256.ormlite.support.ConnectionSource r7, java.lang.Class r8, java.lang.String r9) {
        /*
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r0 = r8
        L_0x0006:
            if (r0 == 0) goto L_0x0023
            java.lang.reflect.Field[] r5 = r0.getDeclaredFields()
            int r6 = r5.length
            r4 = 0
        L_0x000e:
            if (r4 >= r6) goto L_0x001e
            r1 = r5[r4]
            com.j256.ormlite.field.FieldType r2 = com.j256.ormlite.field.FieldType.createFieldType(r7, r9, r1, r8)
            if (r2 == 0) goto L_0x001b
            r3.add(r2)
        L_0x001b:
            int r4 = r4 + 1
            goto L_0x000e
        L_0x001e:
            java.lang.Class r0 = r0.getSuperclass()
            goto L_0x0006
        L_0x0023:
            boolean r4 = r3.isEmpty()
            if (r4 == 0) goto L_0x004e
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "No fields have a "
            r5.<init>(r6)
            java.lang.Class<com.j256.ormlite.field.DatabaseField> r6 = com.j256.ormlite.field.DatabaseField.class
            java.lang.String r6 = r6.getSimpleName()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = " annotation in "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x004e:
            int r4 = r3.size()
            com.j256.ormlite.field.FieldType[] r4 = new com.j256.ormlite.field.FieldType[r4]
            java.lang.Object[] r4 = r3.toArray(r4)
            com.j256.ormlite.field.FieldType[] r4 = (com.j256.ormlite.field.FieldType[]) r4
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.table.DatabaseTableConfig.extractFieldTypes(com.j256.ormlite.support.ConnectionSource, java.lang.Class, java.lang.String):com.j256.ormlite.field.FieldType[]");
    }

    private FieldType[] convertFieldConfigs(ConnectionSource connectionSource, String tableName2, List<DatabaseFieldConfig> fieldConfigs2) {
        List fieldTypes2 = new ArrayList();
        for (DatabaseFieldConfig fieldConfig : fieldConfigs2) {
            FieldType fieldType = null;
            Class classWalk = this.dataClass;
            while (true) {
                if (classWalk == null) {
                    break;
                }
                try {
                    Field field = classWalk.getDeclaredField(fieldConfig.getFieldName());
                    if (field != null) {
                        fieldType = new FieldType(connectionSource, tableName2, field, fieldConfig, this.dataClass);
                        break;
                    }
                    classWalk = classWalk.getSuperclass();
                } catch (NoSuchFieldException e) {
                }
            }
            if (fieldType == null) {
                throw new SQLException("Could not find declared field with name '" + fieldConfig.getFieldName() + "' for " + this.dataClass);
            }
            fieldTypes2.add(fieldType);
        }
        if (!fieldTypes2.isEmpty()) {
            return (FieldType[]) fieldTypes2.toArray(new FieldType[fieldTypes2.size()]);
        }
        throw new SQLException("No fields were configured for class " + this.dataClass);
    }
}
