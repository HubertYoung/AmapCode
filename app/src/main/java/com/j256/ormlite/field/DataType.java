package com.j256.ormlite.field;

import com.j256.ormlite.field.types.BigDecimalNumericType;
import com.j256.ormlite.field.types.BigDecimalStringType;
import com.j256.ormlite.field.types.BigIntegerType;
import com.j256.ormlite.field.types.BooleanObjectType;
import com.j256.ormlite.field.types.BooleanType;
import com.j256.ormlite.field.types.ByteArrayType;
import com.j256.ormlite.field.types.ByteObjectType;
import com.j256.ormlite.field.types.ByteType;
import com.j256.ormlite.field.types.CharType;
import com.j256.ormlite.field.types.CharacterObjectType;
import com.j256.ormlite.field.types.DateLongType;
import com.j256.ormlite.field.types.DateStringType;
import com.j256.ormlite.field.types.DateTimeType;
import com.j256.ormlite.field.types.DateType;
import com.j256.ormlite.field.types.DoubleObjectType;
import com.j256.ormlite.field.types.DoubleType;
import com.j256.ormlite.field.types.EnumIntegerType;
import com.j256.ormlite.field.types.EnumStringType;
import com.j256.ormlite.field.types.FloatObjectType;
import com.j256.ormlite.field.types.FloatType;
import com.j256.ormlite.field.types.IntType;
import com.j256.ormlite.field.types.IntegerObjectType;
import com.j256.ormlite.field.types.LongObjectType;
import com.j256.ormlite.field.types.LongStringType;
import com.j256.ormlite.field.types.LongType;
import com.j256.ormlite.field.types.SerializableType;
import com.j256.ormlite.field.types.ShortObjectType;
import com.j256.ormlite.field.types.ShortType;
import com.j256.ormlite.field.types.SqlDateType;
import com.j256.ormlite.field.types.StringBytesType;
import com.j256.ormlite.field.types.StringType;
import com.j256.ormlite.field.types.TimeStampType;
import com.j256.ormlite.field.types.UuidType;

public enum DataType {
    STRING(StringType.getSingleton()),
    LONG_STRING(LongStringType.getSingleton()),
    STRING_BYTES(StringBytesType.getSingleton()),
    BOOLEAN(BooleanType.getSingleton()),
    BOOLEAN_OBJ(BooleanObjectType.getSingleton()),
    DATE(DateType.getSingleton()),
    DATE_LONG(DateLongType.getSingleton()),
    DATE_STRING(DateStringType.getSingleton()),
    CHAR(CharType.getSingleton()),
    CHAR_OBJ(CharacterObjectType.getSingleton()),
    BYTE(ByteType.getSingleton()),
    BYTE_ARRAY(ByteArrayType.getSingleton()),
    BYTE_OBJ(ByteObjectType.getSingleton()),
    SHORT(ShortType.getSingleton()),
    SHORT_OBJ(ShortObjectType.getSingleton()),
    INTEGER(IntType.getSingleton()),
    INTEGER_OBJ(IntegerObjectType.getSingleton()),
    LONG(LongType.getSingleton()),
    LONG_OBJ(LongObjectType.getSingleton()),
    FLOAT(FloatType.getSingleton()),
    FLOAT_OBJ(FloatObjectType.getSingleton()),
    DOUBLE(DoubleType.getSingleton()),
    DOUBLE_OBJ(DoubleObjectType.getSingleton()),
    SERIALIZABLE(SerializableType.getSingleton()),
    ENUM_STRING(EnumStringType.getSingleton()),
    ENUM_INTEGER(EnumIntegerType.getSingleton()),
    UUID(UuidType.getSingleton()),
    BIG_INTEGER(BigIntegerType.getSingleton()),
    BIG_DECIMAL(BigDecimalStringType.getSingleton()),
    BIG_DECIMAL_NUMERIC(BigDecimalNumericType.getSingleton()),
    DATE_TIME(DateTimeType.getSingleton()),
    SQL_DATE(SqlDateType.getSingleton()),
    TIME_STAMP(TimeStampType.getSingleton()),
    UNKNOWN(null);
    
    private final DataPersister dataPersister;

    private DataType(DataPersister dataPersister2) {
        this.dataPersister = dataPersister2;
    }

    public final DataPersister getDataPersister() {
        return this.dataPersister;
    }
}
