package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;

public final class BooleanCodec implements ObjectDeserializer, ObjectSerializer {
    public static final BooleanCodec instance = new BooleanCodec();

    private BooleanCodec() {
    }

    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        Boolean bool = (Boolean) obj;
        if (bool == null) {
            if ((serializeWriter.features & SerializerFeature.WriteNullBooleanAsFalse.mask) != 0) {
                serializeWriter.write((String) "false");
            } else {
                serializeWriter.writeNull();
            }
        } else if (bool.booleanValue()) {
            serializeWriter.write((String) "true");
        } else {
            serializeWriter.write((String) "false");
        }
    }

    public final <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        T t;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        if (i == 6) {
            jSONLexer.nextToken(16);
            t = Boolean.TRUE;
        } else if (i == 7) {
            jSONLexer.nextToken(16);
            t = Boolean.FALSE;
        } else if (i == 2) {
            int intValue = jSONLexer.intValue();
            jSONLexer.nextToken(16);
            if (intValue == 1) {
                t = Boolean.TRUE;
            } else {
                t = Boolean.FALSE;
            }
        } else {
            Object parse = defaultJSONParser.parse();
            if (parse == null) {
                return null;
            }
            t = TypeUtils.castToBoolean(parse);
        }
        return t;
    }
}
