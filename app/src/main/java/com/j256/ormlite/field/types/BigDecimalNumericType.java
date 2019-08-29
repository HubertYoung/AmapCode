package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.DatabaseResults;
import java.math.BigDecimal;

public class BigDecimalNumericType extends BaseDataType {
    private static final BigDecimalNumericType singleTon = new BigDecimalNumericType();

    public static BigDecimalNumericType getSingleton() {
        return singleTon;
    }

    private BigDecimalNumericType() {
        super(SqlType.BIG_DECIMAL, new Class[0]);
    }

    protected BigDecimalNumericType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        try {
            return new BigDecimal(defaultStr);
        } catch (IllegalArgumentException e) {
            throw SqlExceptionUtil.create("Problems with field " + fieldType + " parsing default BigDecimal string '" + defaultStr + "'", e);
        }
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
        return results.getBigDecimal(columnPos);
    }

    public boolean isAppropriateId() {
        return false;
    }

    public boolean isEscapedValue() {
        return false;
    }

    public Class<?> getPrimaryClass() {
        return BigDecimal.class;
    }
}
