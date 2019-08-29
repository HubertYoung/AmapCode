package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class SerializableType extends BaseDataType {
    private static final SerializableType singleTon = new SerializableType();

    public static SerializableType getSingleton() {
        return singleTon;
    }

    private SerializableType() {
        super(SqlType.SERIALIZABLE, new Class[0]);
    }

    protected SerializableType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        throw new SQLException("Default values for serializable types are not supported");
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getBytes(columnPos);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0051 A[SYNTHETIC, Splitter:B:19:0x0051] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object sqlArgToJava(com.j256.ormlite.field.FieldType r7, java.lang.Object r8, int r9) {
        /*
            r6 = this;
            byte[] r8 = (byte[]) r8
            r0 = r8
            byte[] r0 = (byte[]) r0
            if (r7 == 0) goto L_0x0011
            boolean r4 = r7.isEncryption()
            if (r4 == 0) goto L_0x0011
            byte[] r0 = com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor.decrypt(r0)
        L_0x0011:
            r2 = 0
            java.io.ObjectInputStream r3 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x0024 }
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x0024 }
            r4.<init>(r0)     // Catch:{ Exception -> 0x0024 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0024 }
            java.lang.Object r4 = r3.readObject()     // Catch:{ Exception -> 0x005c, all -> 0x0059 }
            r3.close()     // Catch:{ IOException -> 0x0055 }
        L_0x0023:
            return r4
        L_0x0024:
            r1 = move-exception
        L_0x0025:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x004e }
            java.lang.String r5 = "Could not read serialized object from byte array: "
            r4.<init>(r5)     // Catch:{ all -> 0x004e }
            java.lang.String r5 = java.util.Arrays.toString(r0)     // Catch:{ all -> 0x004e }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x004e }
            java.lang.String r5 = "(len "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x004e }
            int r5 = r0.length     // Catch:{ all -> 0x004e }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x004e }
            java.lang.String r5 = ")"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x004e }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x004e }
            java.sql.SQLException r4 = com.j256.ormlite.misc.SqlExceptionUtil.create(r4, r1)     // Catch:{ all -> 0x004e }
            throw r4     // Catch:{ all -> 0x004e }
        L_0x004e:
            r4 = move-exception
        L_0x004f:
            if (r2 == 0) goto L_0x0054
            r2.close()     // Catch:{ IOException -> 0x0057 }
        L_0x0054:
            throw r4
        L_0x0055:
            r5 = move-exception
            goto L_0x0023
        L_0x0057:
            r5 = move-exception
            goto L_0x0054
        L_0x0059:
            r4 = move-exception
            r2 = r3
            goto L_0x004f
        L_0x005c:
            r1 = move-exception
            r2 = r3
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.field.types.SerializableType.sqlArgToJava(com.j256.ormlite.field.FieldType, java.lang.Object, int):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0040 A[SYNTHETIC, Splitter:B:18:0x0040] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object javaToSqlArg(com.j256.ormlite.field.FieldType r7, java.lang.Object r8) {
        /*
            r6 = this;
            r1 = 0
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0028 }
            r3.<init>()     // Catch:{ Exception -> 0x0028 }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Exception -> 0x0028 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0028 }
            r2.writeObject(r8)     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            r2.close()     // Catch:{ Exception -> 0x0049, all -> 0x0046 }
            r1 = 0
            if (r7 == 0) goto L_0x0023
            boolean r4 = r7.isEncryption()     // Catch:{ Exception -> 0x0028 }
            if (r4 == 0) goto L_0x0023
            byte[] r4 = r3.toByteArray()     // Catch:{ Exception -> 0x0028 }
            byte[] r4 = com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor.encrypt(r4)     // Catch:{ Exception -> 0x0028 }
        L_0x0022:
            return r4
        L_0x0023:
            byte[] r4 = r3.toByteArray()     // Catch:{ Exception -> 0x0028 }
            goto L_0x0022
        L_0x0028:
            r0 = move-exception
        L_0x0029:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x003d }
            java.lang.String r5 = "Could not write serialized object to byte array: "
            r4.<init>(r5)     // Catch:{ all -> 0x003d }
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ all -> 0x003d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x003d }
            java.sql.SQLException r4 = com.j256.ormlite.misc.SqlExceptionUtil.create(r4, r0)     // Catch:{ all -> 0x003d }
            throw r4     // Catch:{ all -> 0x003d }
        L_0x003d:
            r4 = move-exception
        L_0x003e:
            if (r1 == 0) goto L_0x0043
            r1.close()     // Catch:{ IOException -> 0x0044 }
        L_0x0043:
            throw r4
        L_0x0044:
            r5 = move-exception
            goto L_0x0043
        L_0x0046:
            r4 = move-exception
            r1 = r2
            goto L_0x003e
        L_0x0049:
            r0 = move-exception
            r1 = r2
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.field.types.SerializableType.javaToSqlArg(com.j256.ormlite.field.FieldType, java.lang.Object):java.lang.Object");
    }

    public boolean isValidForField(Field field) {
        return Serializable.class.isAssignableFrom(field.getType());
    }

    public boolean isStreamType() {
        return true;
    }

    public boolean isComparable() {
        return false;
    }

    public boolean isAppropriateId() {
        return false;
    }

    public boolean isArgumentHolderRequired() {
        return true;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        throw new SQLException("Serializable type cannot be converted from string to Java");
    }

    public Class<?> getPrimaryClass() {
        return Serializable.class;
    }
}
