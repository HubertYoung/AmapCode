package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class DefaultFieldDeserializer extends FieldDeserializer {
    protected ObjectDeserializer fieldValueDeserilizer;

    public DefaultFieldDeserializer(ParserConfig parserConfig, Class<?> cls, FieldInfo fieldInfo) {
        super(cls, fieldInfo, 2);
    }

    public ObjectDeserializer getFieldValueDeserilizer(ParserConfig parserConfig) {
        if (this.fieldValueDeserilizer == null) {
            this.fieldValueDeserilizer = parserConfig.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
        }
        return this.fieldValueDeserilizer;
    }

    public void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map) {
        Object obj2;
        if (this.fieldValueDeserilizer == null) {
            this.fieldValueDeserilizer = defaultJSONParser.config.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
        }
        Type type2 = this.fieldInfo.fieldType;
        boolean z = type instanceof ParameterizedType;
        if (z) {
            ParseContext parseContext = defaultJSONParser.contex;
            if (parseContext != null) {
                parseContext.type = type;
            }
            type2 = FieldInfo.getFieldType(this.clazz, type, type2);
            this.fieldValueDeserilizer = defaultJSONParser.config.getDeserializer(type2);
        }
        if ((type2 instanceof ParameterizedType) && z) {
            ParameterizedType parameterizedType = (ParameterizedType) type2;
            ParameterizedType parameterizedType2 = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Type rawType = parameterizedType2.getRawType();
            if ((rawType instanceof Class) && TypeUtils.getArgument(actualTypeArguments, ((Class) rawType).getTypeParameters(), parameterizedType2.getActualTypeArguments())) {
                type2 = new ParameterizedTypeImpl(actualTypeArguments, parameterizedType.getOwnerType(), parameterizedType.getRawType());
            }
        }
        String str = this.fieldInfo.format;
        if (str == null || !(this.fieldValueDeserilizer instanceof DateCodec)) {
            obj2 = this.fieldValueDeserilizer.deserialze(defaultJSONParser, type2, this.fieldInfo.name);
        } else {
            obj2 = ((DateCodec) this.fieldValueDeserilizer).deserialze(defaultJSONParser, type2, this.fieldInfo.name, str);
        }
        if (defaultJSONParser.resolveStatus == 1) {
            ResolveTask lastResolveTask = defaultJSONParser.getLastResolveTask();
            lastResolveTask.fieldDeserializer = this;
            lastResolveTask.ownerContext = defaultJSONParser.contex;
            defaultJSONParser.resolveStatus = 0;
        } else if (obj == null) {
            map.put(this.fieldInfo.name, obj2);
        } else {
            if (obj2 == null) {
                Class<?> cls = this.fieldInfo.fieldClass;
                if (cls == Byte.TYPE || cls == Short.TYPE || cls == Float.TYPE || cls == Double.TYPE) {
                    return;
                }
            }
            setValue(obj, obj2);
        }
    }
}
