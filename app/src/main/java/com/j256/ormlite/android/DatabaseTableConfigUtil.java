package com.j256.ormlite.android;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldConfig;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class DatabaseTableConfigUtil {
    private static final int ALLOW_GENERATED_ID_INSERT = 24;
    private static final int CAN_BE_NULL = 5;
    private static final int COLUMN_DEFINITON = 25;
    private static final int COLUMN_NAME = 1;
    private static final int DATA_TYPE = 2;
    private static final int DEFAULT_VALUE = 3;
    private static final int ENCRYPTION = 30;
    private static final int FOREIGN = 9;
    private static final int FOREIGN_AUTO_CREATE = 26;
    private static final int FOREIGN_AUTO_REFRESH = 21;
    private static final int FOREIGN_COLUMN_NAME = 28;
    private static final int FORMAT = 14;
    private static final int GENERATED_ID = 7;
    private static final int GENERATED_ID_SEQUENCE = 8;
    private static final int ID = 6;
    private static final int INDEX = 17;
    private static final int INDEX_NAME = 19;
    private static final int MAX_FOREIGN_AUTO_REFRESH_LEVEL = 22;
    private static final int PERSISTED = 13;
    private static final int PERSISTER_CLASS = 23;
    private static final int READ_ONLY = 29;
    private static final int THROW_IF_NULL = 12;
    private static final int UNIQUE = 15;
    private static final int UNIQUE_COMBO = 16;
    private static final int UNIQUE_INDEX = 18;
    private static final int UNIQUE_INDEX_NAME = 20;
    private static final int UNKNOWN_ENUM_NAME = 11;
    private static final int USE_GET_SET = 10;
    private static final int VERSION = 27;
    private static final int WIDTH = 4;
    private static Class<?> annotationFactoryClazz;
    private static Class<?> annotationMemberClazz;
    private static final int[] configFieldNums = lookupClasses();
    private static Field elementsField;
    private static Field nameField;
    private static Field valueField;
    private static int workedC = 0;

    class DatabaseFieldSample {
        @DatabaseField
        String field;

        private DatabaseFieldSample() {
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<T>, code=java.lang.Class, for r11v0, types: [java.lang.Class, java.lang.Class<T>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> com.j256.ormlite.table.DatabaseTableConfig<T> fromClass(com.j256.ormlite.support.ConnectionSource r10, java.lang.Class r11) {
        /*
            com.j256.ormlite.db.DatabaseType r2 = r10.getDatabaseType()
            java.lang.String r5 = com.j256.ormlite.table.DatabaseTableConfig.extractTableName(r11)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r0 = r11
        L_0x000e:
            if (r0 == 0) goto L_0x0031
            java.lang.reflect.Field[] r7 = r0.getDeclaredFields()
            int r8 = r7.length
            r6 = 0
        L_0x0016:
            if (r6 >= r8) goto L_0x002c
            r3 = r7[r6]
            com.j256.ormlite.field.DatabaseFieldConfig r1 = configFromField(r2, r5, r3)
            if (r1 == 0) goto L_0x0029
            boolean r9 = r1.isPersisted()
            if (r9 == 0) goto L_0x0029
            r4.add(r1)
        L_0x0029:
            int r6 = r6 + 1
            goto L_0x0016
        L_0x002c:
            java.lang.Class r0 = r0.getSuperclass()
            goto L_0x000e
        L_0x0031:
            int r6 = r4.size()
            if (r6 != 0) goto L_0x0039
            r6 = 0
        L_0x0038:
            return r6
        L_0x0039:
            com.j256.ormlite.table.DatabaseTableConfig r6 = new com.j256.ormlite.table.DatabaseTableConfig
            r6.<init>(r11, r5, r4)
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.android.DatabaseTableConfigUtil.fromClass(com.j256.ormlite.support.ConnectionSource, java.lang.Class):com.j256.ormlite.table.DatabaseTableConfig");
    }

    public static int getWorkedC() {
        return workedC;
    }

    private static int[] lookupClasses() {
        try {
            annotationFactoryClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationFactory");
            annotationMemberClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationMember");
            Class annotationMemberArrayClazz = Class.forName("[Lorg.apache.harmony.lang.annotation.AnnotationMember;");
            try {
                Field declaredField = annotationFactoryClazz.getDeclaredField("elements");
                elementsField = declaredField;
                declaredField.setAccessible(true);
                Field declaredField2 = annotationMemberClazz.getDeclaredField("name");
                nameField = declaredField2;
                declaredField2.setAccessible(true);
                Field declaredField3 = annotationMemberClazz.getDeclaredField("value");
                valueField = declaredField3;
                declaredField3.setAccessible(true);
                InvocationHandler proxy = Proxy.getInvocationHandler((DatabaseField) DatabaseFieldSample.class.getDeclaredField("field").getAnnotation(DatabaseField.class));
                if (proxy.getClass() != annotationFactoryClazz) {
                    return null;
                }
                try {
                    Object elements = elementsField.get(proxy);
                    if (elements == null || elements.getClass() != annotationMemberArrayClazz) {
                        return null;
                    }
                    Object[] elementArray = (Object[]) elements;
                    int[] configNums = new int[elementArray.length];
                    for (int i = 0; i < elementArray.length; i++) {
                        configNums[i] = configFieldNameToNum((String) nameField.get(elementArray[i]));
                    }
                    return configNums;
                } catch (IllegalAccessException e) {
                    return null;
                }
            } catch (SecurityException e2) {
                return null;
            } catch (NoSuchFieldException e3) {
                return null;
            }
        } catch (ClassNotFoundException e4) {
            return null;
        }
    }

    private static int configFieldNameToNum(String configName) {
        if (configName.equals("columnName")) {
            return 1;
        }
        if (configName.equals("dataType")) {
            return 2;
        }
        if (configName.equals("defaultValue")) {
            return 3;
        }
        if (configName.equals("width")) {
            return 4;
        }
        if (configName.equals("canBeNull")) {
            return 5;
        }
        if (configName.equals("id")) {
            return 6;
        }
        if (configName.equals("generatedId")) {
            return 7;
        }
        if (configName.equals("generatedIdSequence")) {
            return 8;
        }
        if (configName.equals("foreign")) {
            return 9;
        }
        if (configName.equals("useGetSet")) {
            return 10;
        }
        if (configName.equals("unknownEnumName")) {
            return 11;
        }
        if (configName.equals("throwIfNull")) {
            return 12;
        }
        if (configName.equals("persisted")) {
            return 13;
        }
        if (configName.equals(IjkMediaMeta.IJKM_KEY_FORMAT)) {
            return 14;
        }
        if (configName.equals("unique")) {
            return 15;
        }
        if (configName.equals("uniqueCombo")) {
            return 16;
        }
        if (configName.equals("index")) {
            return 17;
        }
        if (configName.equals("uniqueIndex")) {
            return 18;
        }
        if (configName.equals("indexName")) {
            return 19;
        }
        if (configName.equals("uniqueIndexName")) {
            return 20;
        }
        if (configName.equals("foreignAutoRefresh")) {
            return 21;
        }
        if (configName.equals("maxForeignAutoRefreshLevel")) {
            return 22;
        }
        if (configName.equals("persisterClass")) {
            return 23;
        }
        if (configName.equals("allowGeneratedIdInsert")) {
            return 24;
        }
        if (configName.equals("columnDefinition")) {
            return 25;
        }
        if (configName.equals("foreignAutoCreate")) {
            return 26;
        }
        if (configName.equals("version")) {
            return 27;
        }
        if (configName.equals("foreignColumnName")) {
            return 28;
        }
        if (configName.equals("readOnly")) {
            return 29;
        }
        if (configName.equals("encryption")) {
            return 30;
        }
        throw new IllegalStateException("Could not find support for DatabaseField " + configName);
    }

    private static DatabaseFieldConfig configFromField(DatabaseType databaseType, String tableName, Field field) {
        if (configFieldNums == null) {
            return DatabaseFieldConfig.fromField(databaseType, tableName, field);
        }
        DatabaseField databaseField = (DatabaseField) field.getAnnotation(DatabaseField.class);
        DatabaseFieldConfig config = null;
        if (databaseField != null) {
            try {
                config = buildConfig(databaseField, tableName, field);
            } catch (Exception e) {
            }
        }
        if (config == null) {
            return DatabaseFieldConfig.fromField(databaseType, tableName, field);
        }
        workedC++;
        return config;
    }

    private static DatabaseFieldConfig buildConfig(DatabaseField databaseField, String tableName, Field field) {
        DatabaseFieldConfig config = null;
        InvocationHandler proxy = Proxy.getInvocationHandler(databaseField);
        if (proxy.getClass() == annotationFactoryClazz) {
            Object elementsObject = elementsField.get(proxy);
            if (elementsObject != null) {
                config = new DatabaseFieldConfig(field.getName());
                Object[] objs = (Object[]) elementsObject;
                for (int i = 0; i < configFieldNums.length; i++) {
                    Object value = valueField.get(objs[i]);
                    if (value != null) {
                        assignConfigField(configFieldNums[i], config, field, value);
                    }
                }
            }
        }
        return config;
    }

    private static void assignConfigField(int configNum, DatabaseFieldConfig config, Field field, Object value) {
        switch (configNum) {
            case 1:
                config.setColumnName(valueIfNotBlank((String) value));
                return;
            case 2:
                config.setDataType((DataType) value);
                return;
            case 3:
                String defaultValue = (String) value;
                if (defaultValue != null && !defaultValue.equals(DatabaseField.DEFAULT_STRING)) {
                    config.setDefaultValue(defaultValue);
                    return;
                }
                return;
            case 4:
                config.setWidth(((Integer) value).intValue());
                return;
            case 5:
                config.setCanBeNull(((Boolean) value).booleanValue());
                return;
            case 6:
                config.setId(((Boolean) value).booleanValue());
                return;
            case 7:
                config.setGeneratedId(((Boolean) value).booleanValue());
                return;
            case 8:
                config.setGeneratedIdSequence(valueIfNotBlank((String) value));
                return;
            case 9:
                config.setForeign(((Boolean) value).booleanValue());
                return;
            case 10:
                config.setUseGetSet(((Boolean) value).booleanValue());
                return;
            case 11:
                config.setUnknownEnumValue(DatabaseFieldConfig.findMatchingEnumVal(field, (String) value));
                return;
            case 12:
                config.setThrowIfNull(((Boolean) value).booleanValue());
                return;
            case 13:
                config.setPersisted(((Boolean) value).booleanValue());
                return;
            case 14:
                config.setFormat(valueIfNotBlank((String) value));
                return;
            case 15:
                config.setUnique(((Boolean) value).booleanValue());
                return;
            case 16:
                config.setUniqueCombo(((Boolean) value).booleanValue());
                return;
            case 17:
                config.setIndex(((Boolean) value).booleanValue());
                return;
            case 18:
                config.setUniqueIndex(((Boolean) value).booleanValue());
                return;
            case 19:
                config.setIndexName(valueIfNotBlank((String) value));
                return;
            case 20:
                config.setUniqueIndexName(valueIfNotBlank((String) value));
                return;
            case 21:
                config.setForeignAutoRefresh(((Boolean) value).booleanValue());
                return;
            case 22:
                config.setMaxForeignAutoRefreshLevel(((Integer) value).intValue());
                return;
            case 23:
                config.setPersisterClass((Class) value);
                return;
            case 24:
                config.setAllowGeneratedIdInsert(((Boolean) value).booleanValue());
                return;
            case 25:
                config.setColumnDefinition(valueIfNotBlank((String) value));
                return;
            case 26:
                config.setForeignAutoCreate(((Boolean) value).booleanValue());
                return;
            case 27:
                config.setVersion(((Boolean) value).booleanValue());
                return;
            case 28:
                config.setForeignColumnName(valueIfNotBlank((String) value));
                return;
            case 29:
                config.setReadOnly(((Boolean) value).booleanValue());
                return;
            case 30:
                config.setEncryption(((Boolean) value).booleanValue());
                return;
            default:
                throw new IllegalStateException("Could not find support for DatabaseField number " + configNum);
        }
    }

    private static String valueIfNotBlank(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        return value;
    }
}
