package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

final class ResolveFieldDeserializer extends FieldDeserializer {
    private final Collection collection;
    private final int index;
    private final Object key;
    private final List list;
    private final Map map;
    private final DefaultJSONParser parser;

    public final void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map2) {
    }

    public ResolveFieldDeserializer(DefaultJSONParser defaultJSONParser, List list2, int i) {
        super(null, null, 0);
        this.parser = defaultJSONParser;
        this.index = i;
        this.list = list2;
        this.key = null;
        this.map = null;
        this.collection = null;
    }

    public ResolveFieldDeserializer(Map map2, Object obj) {
        super(null, null, 0);
        this.parser = null;
        this.index = -1;
        this.list = null;
        this.key = obj;
        this.map = map2;
        this.collection = null;
    }

    public ResolveFieldDeserializer(Collection collection2) {
        super(null, null, 0);
        this.parser = null;
        this.index = -1;
        this.list = null;
        this.key = null;
        this.map = null;
        this.collection = collection2;
    }

    public final void setValue(Object obj, Object obj2) {
        if (this.map != null) {
            this.map.put(this.key, obj2);
        } else if (this.collection != null) {
            this.collection.add(obj2);
        } else {
            this.list.set(this.index, obj2);
            if (this.list instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) this.list;
                Object relatedArray = jSONArray.getRelatedArray();
                if (relatedArray != null && Array.getLength(relatedArray) > this.index) {
                    if (jSONArray.getComponentType() != null) {
                        obj2 = TypeUtils.cast(obj2, jSONArray.getComponentType(), this.parser.config);
                    }
                    Array.set(relatedArray, this.index, obj2);
                }
            }
        }
    }
}
