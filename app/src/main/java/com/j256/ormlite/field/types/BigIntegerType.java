package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.math.BigInteger;

public class BigIntegerType extends BaseDataType {
    public static int DEFAULT_WIDTH = 255;
    private static final BigIntegerType singleTon = new BigIntegerType();

    public static BigIntegerType getSingleton() {
        return singleTon;
    }

    protected BigIntegerType() {
        super(SqlType.STRING, new Class[]{BigInteger.class});
    }

    protected BigIntegerType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        if (fieldType != null) {
            try {
                if (fieldType.isEncryption()) {
                    return OrmLiteEncryptionProcessor.encrypt(new BigInteger(defaultStr).toString());
                }
            } catch (IllegalArgumentException e) {
                throw SqlExceptionUtil.create("Problems with field " + fieldType + " parsing default BigInteger string '" + defaultStr + "'", e);
            }
        }
        return new BigInteger(defaultStr);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getString(columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        if (fieldType != null) {
            try {
                if (fieldType.isEncryption()) {
                    return new BigInteger(OrmLiteEncryptionProcessor.decrypt((String) sqlArg));
                }
            } catch (IllegalArgumentException e) {
                throw SqlExceptionUtil.create("Problems with column " + columnPos + " parsing BigInteger string '" + sqlArg + "'", e);
            }
        }
        return new BigInteger((String) sqlArg);
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        BigInteger bigInteger = (BigInteger) obj;
        if (fieldType == null || !fieldType.isEncryption()) {
            return bigInteger.toString();
        }
        return OrmLiteEncryptionProcessor.encrypt(bigInteger.toString());
    }

    public int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public boolean isAppropriateId() {
        return false;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        return sqlArgToJava(fieldType, stringValue, columnPos);
    }
}
