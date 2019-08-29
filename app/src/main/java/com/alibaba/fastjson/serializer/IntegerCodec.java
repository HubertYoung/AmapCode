package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public final class IntegerCodec implements ObjectDeserializer, ObjectSerializer {
    public static IntegerCodec instance = new IntegerCodec();

    private IntegerCodec() {
    }

    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        Number number = (Number) obj;
        if (number != null) {
            if (obj instanceof Long) {
                serializeWriter.writeLong(number.longValue());
            } else {
                serializeWriter.writeInt(number.intValue());
            }
            if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) != 0) {
                Class<?> cls = number.getClass();
                if (cls == Byte.class) {
                    serializeWriter.write(66);
                } else if (cls == Short.class) {
                    serializeWriter.write(83);
                } else if (cls == Long.class) {
                    long longValue = number.longValue();
                    if (longValue <= 2147483647L && longValue >= -2147483648L && type != Long.class) {
                        serializeWriter.write(76);
                    }
                }
            }
        } else if ((serializeWriter.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0) {
            serializeWriter.write(48);
        } else {
            serializeWriter.writeNull();
        }
    }

    public final <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        T t;
        T t2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        if (i == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        if (i == 2) {
            if (type == Long.TYPE || type == Long.class) {
                t = Long.valueOf(jSONLexer.longValue());
            } else {
                try {
                    t = Integer.valueOf(jSONLexer.intValue());
                } catch (NumberFormatException e) {
                    throw new JSONException("int value overflow, field : ".concat(String.valueOf(obj)), e);
                }
            }
            jSONLexer.nextToken(16);
        } else if (i == 3) {
            BigDecimal decimalValue = jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            if (type == Long.TYPE || type == Long.class) {
                t = Long.valueOf(decimalValue.longValueExact());
            } else {
                t = Integer.valueOf(decimalValue.intValueExact());
            }
        } else {
            Object parse = defaultJSONParser.parse();
            try {
                if (type != Long.TYPE) {
                    if (type != Long.class) {
                        t2 = TypeUtils.castToInt(parse);
                        t = t2;
                    }
                }
                t2 = TypeUtils.castToLong(parse);
                t = t2;
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("cast error, field : ");
                sb.append(obj);
                sb.append(", value ");
                sb.append(parse);
                throw new JSONException(sb.toString(), e2);
            }
        }
        return t;
    }
}
