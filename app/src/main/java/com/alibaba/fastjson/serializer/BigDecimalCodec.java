package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalCodec implements ObjectDeserializer, ObjectSerializer {
    public static final BigDecimalCodec instance = new BigDecimalCodec();

    private BigDecimalCodec() {
    }

    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            if ((serializeWriter.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0) {
                serializeWriter.write(48);
            } else {
                serializeWriter.writeNull();
            }
        } else if (obj instanceof BigInteger) {
            serializeWriter.write(((BigInteger) obj).toString());
        } else {
            BigDecimal bigDecimal = (BigDecimal) obj;
            serializeWriter.write(bigDecimal.toString());
            if (!((serializeWriter.features & SerializerFeature.WriteClassName.mask) == 0 || type == BigDecimal.class || bigDecimal.scale() != 0)) {
                serializeWriter.write(46);
            }
        }
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        if (i == 2) {
            if (type == BigInteger.class) {
                String numberString = jSONLexer.numberString();
                jSONLexer.nextToken(16);
                return new BigInteger(numberString, 10);
            }
            T decimalValue = jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            return decimalValue;
        } else if (i == 3) {
            T decimalValue2 = jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            if (type != BigInteger.class) {
                return decimalValue2;
            }
            int scale = decimalValue2.scale();
            if (scale >= -100 && scale <= 100) {
                return decimalValue2.toBigInteger();
            }
            throw new NumberFormatException();
        } else {
            Object parse = defaultJSONParser.parse();
            if (parse == null) {
                return null;
            }
            if (type == BigInteger.class) {
                return TypeUtils.castToBigInteger(parse);
            }
            return TypeUtils.castToBigDecimal(parse);
        }
    }
}
