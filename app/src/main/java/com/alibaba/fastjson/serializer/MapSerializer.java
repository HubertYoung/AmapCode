package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public final class MapSerializer implements ObjectSerializer {
    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        boolean z;
        JSONSerializer jSONSerializer2 = jSONSerializer;
        Object obj3 = obj;
        SerializeWriter serializeWriter = jSONSerializer2.out;
        if (obj3 == null) {
            serializeWriter.writeNull();
            return;
        }
        Map<String, Object> map = (Map) obj3;
        Class<?> cls = map.getClass();
        boolean z2 = (cls == JSONObject.class || cls == HashMap.class || cls == LinkedHashMap.class) && map.containsKey(JSON.DEFAULT_TYPE_KEY);
        if ((serializeWriter.features & SerializerFeature.SortField.mask) != 0) {
            if (map instanceof JSONObject) {
                map = ((JSONObject) map).getInnerMap();
            }
            if (!(map instanceof SortedMap) && !(map instanceof LinkedHashMap)) {
                try {
                    map = new TreeMap<>(map);
                } catch (Exception unused) {
                }
            }
        }
        if (jSONSerializer2.references == null || !jSONSerializer2.references.containsKey(obj3)) {
            SerialContext serialContext = jSONSerializer2.context;
            jSONSerializer2.setContext(serialContext, obj3, obj2, 0);
            try {
                serializeWriter.write(123);
                jSONSerializer.incrementIndent();
                if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) == 0 || z2) {
                    z = true;
                } else {
                    serializeWriter.writeFieldName(jSONSerializer2.config.typeKey, false);
                    serializeWriter.writeString(obj.getClass().getName());
                    z = false;
                }
                Class<?> cls2 = null;
                ObjectSerializer objectSerializer = null;
                for (Entry next : map.entrySet()) {
                    Object value = next.getValue();
                    Object key = next.getKey();
                    if (jSONSerializer2.applyName(obj3, key) && jSONSerializer2.apply(obj3, key, value)) {
                        Object processKey = jSONSerializer2.processKey(obj3, key, value);
                        Object processValue = JSONSerializer.processValue(jSONSerializer2, obj3, processKey, value);
                        if (processValue == null) {
                            if ((SerializerFeature.WriteMapNullValue.mask & serializeWriter.features) == 0) {
                            }
                        }
                        if (processKey instanceof String) {
                            String str = (String) processKey;
                            if (!z) {
                                serializeWriter.write(44);
                            }
                            if ((serializeWriter.features & SerializerFeature.PrettyFormat.mask) != 0) {
                                jSONSerializer.println();
                            }
                            serializeWriter.writeFieldName(str, true);
                        } else {
                            if (!z) {
                                serializeWriter.write(44);
                            }
                            if ((serializeWriter.features & SerializerFeature.WriteNonStringKeyAsString.mask) == 0 || (processKey instanceof Enum)) {
                                jSONSerializer2.write(processKey);
                            } else {
                                jSONSerializer2.write(JSON.toJSONString(processKey));
                            }
                            serializeWriter.write(58);
                        }
                        if (processValue == null) {
                            serializeWriter.writeNull();
                        } else {
                            Class<?> cls3 = processValue.getClass();
                            if (cls3 == cls2) {
                                objectSerializer.write(jSONSerializer2, processValue, processKey, null);
                            } else {
                                objectSerializer = jSONSerializer2.config.get(cls3);
                                objectSerializer.write(jSONSerializer2, processValue, processKey, null);
                                cls2 = cls3;
                            }
                        }
                        z = false;
                    }
                }
                jSONSerializer2.context = serialContext;
                jSONSerializer.decrementIdent();
                if ((serializeWriter.features & SerializerFeature.PrettyFormat.mask) != 0 && map.size() > 0) {
                    jSONSerializer.println();
                }
                serializeWriter.write(125);
            } catch (Throwable th) {
                Throwable th2 = th;
                jSONSerializer2.context = serialContext;
                throw th2;
            }
        } else {
            jSONSerializer.writeReference(obj);
        }
    }
}
