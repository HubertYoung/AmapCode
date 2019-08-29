package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.Arrays;

public class EnumDeserializer implements ObjectDeserializer {
    private final Class<?> enumClass;
    protected long[] enumNameHashCodes = new long[this.ordinalEnums.length];
    protected final Enum[] enums;
    protected final Enum[] ordinalEnums;

    public EnumDeserializer(Class<?> cls) {
        this.enumClass = cls;
        this.ordinalEnums = (Enum[]) cls.getEnumConstants();
        long[] jArr = new long[this.ordinalEnums.length];
        for (int i = 0; i < this.ordinalEnums.length; i++) {
            String name = this.ordinalEnums[i].name();
            long j = -3750763034362895579L;
            for (int i2 = 0; i2 < name.length(); i2++) {
                j = (j ^ ((long) name.charAt(i2))) * 1099511628211L;
            }
            jArr[i] = j;
            this.enumNameHashCodes[i] = j;
        }
        Arrays.sort(this.enumNameHashCodes);
        this.enums = new Enum[this.ordinalEnums.length];
        for (int i3 = 0; i3 < this.enumNameHashCodes.length; i3++) {
            int i4 = 0;
            while (true) {
                if (i4 >= jArr.length) {
                    break;
                } else if (this.enumNameHashCodes[i3] == jArr[i4]) {
                    this.enums[i3] = this.ordinalEnums[i4];
                    break;
                } else {
                    i4++;
                }
            }
        }
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            JSONLexer jSONLexer = defaultJSONParser.lexer;
            int i = jSONLexer.token;
            if (i == 2) {
                int intValue = jSONLexer.intValue();
                jSONLexer.nextToken(16);
                if (intValue >= 0) {
                    if (intValue <= this.ordinalEnums.length) {
                        return this.ordinalEnums[intValue];
                    }
                }
                StringBuilder sb = new StringBuilder("parse enum ");
                sb.append(this.enumClass.getName());
                sb.append(" error, value : ");
                sb.append(intValue);
                throw new JSONException(sb.toString());
            } else if (i == 4) {
                String stringVal = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                if (stringVal.length() == 0) {
                    return null;
                }
                long j = -3750763034362895579L;
                for (int i2 = 0; i2 < stringVal.length(); i2++) {
                    j = (j ^ ((long) stringVal.charAt(i2))) * 1099511628211L;
                }
                int binarySearch = Arrays.binarySearch(this.enumNameHashCodes, j);
                if (binarySearch < 0) {
                    return null;
                }
                return this.enums[binarySearch];
            } else if (i == 8) {
                jSONLexer.nextToken(16);
                return null;
            } else {
                Object parse = defaultJSONParser.parse();
                StringBuilder sb2 = new StringBuilder("parse enum ");
                sb2.append(this.enumClass.getName());
                sb2.append(" error, value : ");
                sb2.append(parse);
                throw new JSONException(sb2.toString());
            }
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }
}
