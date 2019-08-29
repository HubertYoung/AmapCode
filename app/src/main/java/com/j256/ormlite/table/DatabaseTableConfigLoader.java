package com.j256.ormlite.table;

import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.misc.SqlExceptionUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTableConfigLoader {
    private static final String CONFIG_FILE_END_MARKER = "# --table-end--";
    private static final String CONFIG_FILE_FIELDS_END = "# --table-fields-end--";
    private static final String CONFIG_FILE_FIELDS_START = "# --table-fields-start--";
    private static final String CONFIG_FILE_START_MARKER = "# --table-start--";
    private static final String FIELD_NAME_DATA_CLASS = "dataClass";
    private static final String FIELD_NAME_TABLE_NAME = "tableName";

    public static List<DatabaseTableConfig<?>> loadDatabaseConfigFromReader(BufferedReader reader) {
        List list = new ArrayList();
        while (true) {
            DatabaseTableConfig config = fromReader(reader);
            if (config == null) {
                return list;
            }
            list.add(config);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> com.j256.ormlite.table.DatabaseTableConfig<T> fromReader(java.io.BufferedReader r8) {
        /*
            com.j256.ormlite.table.DatabaseTableConfig r1 = new com.j256.ormlite.table.DatabaseTableConfig
            r1.<init>()
            r0 = 0
        L_0x0006:
            java.lang.String r3 = r8.readLine()     // Catch:{ IOException -> 0x0020 }
            if (r3 == 0) goto L_0x0069
            java.lang.String r5 = "# --table-end--"
            boolean r5 = r3.equals(r5)
            if (r5 != 0) goto L_0x0069
            java.lang.String r5 = "# --table-fields-start--"
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x0028
            readFields(r8, r1)
            goto L_0x0006
        L_0x0020:
            r2 = move-exception
            java.lang.String r5 = "Could not read DatabaseTableConfig from stream"
            java.sql.SQLException r5 = com.j256.ormlite.misc.SqlExceptionUtil.create(r5, r2)
            throw r5
        L_0x0028:
            int r5 = r3.length()
            if (r5 == 0) goto L_0x0006
            java.lang.String r5 = "#"
            boolean r5 = r3.startsWith(r5)
            if (r5 != 0) goto L_0x0006
            java.lang.String r5 = "# --table-start--"
            boolean r5 = r3.equals(r5)
            if (r5 != 0) goto L_0x0006
            java.lang.String r5 = "="
            r6 = -2
            java.lang.String[] r4 = r3.split(r5, r6)
            int r5 = r4.length
            r6 = 2
            if (r5 == r6) goto L_0x005e
            java.sql.SQLException r5 = new java.sql.SQLException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "DatabaseTableConfig reading from stream cannot parse line: "
            r6.<init>(r7)
            java.lang.StringBuilder r6 = r6.append(r3)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x005e:
            r5 = 0
            r5 = r4[r5]
            r6 = 1
            r6 = r4[r6]
            readTableField(r1, r5, r6)
            r0 = 1
            goto L_0x0006
        L_0x0069:
            if (r0 == 0) goto L_0x006c
        L_0x006b:
            return r1
        L_0x006c:
            r1 = 0
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.table.DatabaseTableConfigLoader.fromReader(java.io.BufferedReader):com.j256.ormlite.table.DatabaseTableConfig");
    }

    public static <T> void write(BufferedWriter writer, DatabaseTableConfig<T> config) {
        try {
            writeConfig(writer, config);
        } catch (IOException e) {
            throw SqlExceptionUtil.create("Could not write config to writer", e);
        }
    }

    private static <T> void writeConfig(BufferedWriter writer, DatabaseTableConfig<T> config) {
        writer.append(CONFIG_FILE_START_MARKER);
        writer.newLine();
        if (config.getDataClass() != null) {
            writer.append(FIELD_NAME_DATA_CLASS).append('=').append(config.getDataClass().getName());
            writer.newLine();
        }
        if (config.getTableName() != null) {
            writer.append(FIELD_NAME_TABLE_NAME).append('=').append(config.getTableName());
            writer.newLine();
        }
        writer.append(CONFIG_FILE_FIELDS_START);
        writer.newLine();
        if (config.getFieldConfigs() != null) {
            for (DatabaseFieldConfig field : config.getFieldConfigs()) {
                DatabaseFieldConfigLoader.write(writer, field, config.getTableName());
            }
        }
        writer.append(CONFIG_FILE_FIELDS_END);
        writer.newLine();
        writer.append(CONFIG_FILE_END_MARKER);
        writer.newLine();
    }

    private static <T> void readTableField(DatabaseTableConfig<T> config, String field, String value) {
        if (field.equals(FIELD_NAME_DATA_CLASS)) {
            try {
                config.setDataClass(Class.forName(value));
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Unknown class specified for dataClass: " + value);
            }
        } else if (field.equals(FIELD_NAME_TABLE_NAME)) {
            config.setTableName(value);
        }
    }

    private static <T> void readFields(BufferedReader reader, DatabaseTableConfig<T> config) {
        List fields = new ArrayList();
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null || line.equals(CONFIG_FILE_FIELDS_END)) {
                    break;
                }
                DatabaseFieldConfig fieldConfig = DatabaseFieldConfigLoader.fromReader(reader);
                if (fieldConfig == null) {
                    break;
                }
                fields.add(fieldConfig);
            } catch (IOException e) {
                throw SqlExceptionUtil.create("Could not read next field from config file", e);
            }
        }
        config.setFieldConfigs(fields);
    }
}
