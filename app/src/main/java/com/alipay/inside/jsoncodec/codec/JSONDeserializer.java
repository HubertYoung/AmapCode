package com.alipay.inside.jsoncodec.codec;

import com.alipay.inside.jsoncodec.util.ClassUtil;
import com.alipay.sdk.util.h;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.json.alipay.inside.JSONArray;
import org.json.alipay.inside.JSONObject;

public class JSONDeserializer {
    static List<ObjectDeserializer> deserializers;

    static {
        ArrayList arrayList = new ArrayList();
        deserializers = arrayList;
        arrayList.add(new SimpleClassCodec());
        deserializers.add(new EnumCodec());
        deserializers.add(new DateCodec());
        deserializers.add(new MapCodec());
        deserializers.add(new SetDeserializer());
        deserializers.add(new CollectionCodec());
        deserializers.add(new ArrayCodec());
        deserializers.add(new JavaBeanCodec());
    }

    public static final Object deserialize(String str, Type type) throws Exception {
        if (str == null || str.length() == 0) {
            return null;
        }
        String trim = str.trim();
        if (trim.startsWith("[") && trim.endsWith("]")) {
            return deserialize0(new JSONArray(trim), type);
        }
        if (!trim.startsWith("{") || !trim.endsWith(h.d)) {
            return deserialize0(trim, type);
        }
        return deserialize0(new JSONObject(trim), type);
    }

    public static final <T> T deserialize0(Object obj, Type type) throws Exception {
        for (ObjectDeserializer next : deserializers) {
            if (next.match(ClassUtil.getRawClass(type))) {
                T deserialize = next.deserialize(obj, type);
                if (deserialize != null) {
                    return deserialize;
                }
            }
        }
        return null;
    }
}
