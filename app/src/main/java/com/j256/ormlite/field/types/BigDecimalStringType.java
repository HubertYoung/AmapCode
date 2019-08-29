package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionProcessor;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.math.BigDecimal;

public class BigDecimalStringType extends BaseDataType {
    public static int DEFAULT_WIDTH = 255;
    private static final BigDecimalStringType singleTon = new BigDecimalStringType();

    public static BigDecimalStringType getSingleton() {
        return singleTon;
    }

    private BigDecimalStringType() {
        super(SqlType.STRING, new Class[]{BigDecimal.class});
    }

    protected BigDecimalStringType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        if (fieldType != null) {
            try {
                if (fieldType.isEncryption()) {
                    return OrmLiteEncryptionProcessor.encrypt(new BigDecimal(defaultStr).toString());
                }
            } catch (IllegalArgumentException e) {
                throw SqlExceptionUtil.create("Problems with field " + fieldType + " parsing default BigDecimal string '" + defaultStr + "'", e);
            }
        }
        return new BigDecimal(defaultStr);
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getString(columnPos);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        if (fieldType != null) {
            try {
                if (fieldType.isEncryption()) {
                    return new BigDecimal(OrmLiteEncryptionProcessor.decrypt((String) sqlArg));
                }
            } catch (IllegalArgumentException e) {
                throw SqlExceptionUtil.create("Problems with column " + columnPos + " parsing BigDecimal string '" + sqlArg + "'", e);
            }
        }
        return new BigDecimal((String) sqlArg);
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        BigDecimal bigInteger = (BigDecimal) obj;
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
