package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.IdentityHashMap;
import java.util.List;

public final class ListSerializer implements ObjectSerializer {
    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        JSONSerializer jSONSerializer2 = jSONSerializer;
        Object obj3 = obj;
        Object obj4 = obj2;
        SerializeWriter serializeWriter = jSONSerializer2.out;
        boolean z = (serializeWriter.features & SerializerFeature.WriteClassName.mask) != 0;
        Type type2 = null;
        if (z) {
            type2 = TypeUtils.getCollectionItemType(type);
        }
        if (obj3 != null) {
            List list = (List) obj3;
            int size = list.size();
            if (size == 0) {
                serializeWriter.append((CharSequence) "[]");
                return;
            }
            SerialContext serialContext = jSONSerializer2.context;
            if ((serializeWriter.features & SerializerFeature.DisableCircularReferenceDetect.mask) == 0) {
                jSONSerializer2.context = new SerialContext(serialContext, obj3, obj4, 0);
                if (jSONSerializer2.references == null) {
                    jSONSerializer2.references = new IdentityHashMap<>();
                }
                jSONSerializer2.references.put(obj3, jSONSerializer2.context);
            }
            try {
                if ((serializeWriter.features & SerializerFeature.PrettyFormat.mask) != 0) {
                    serializeWriter.write(91);
                    jSONSerializer.incrementIndent();
                    for (int i = 0; i < size; i++) {
                        Object obj5 = list.get(i);
                        if (i != 0) {
                            serializeWriter.write(44);
                        }
                        jSONSerializer.println();
                        if (obj5 == null) {
                            jSONSerializer2.out.writeNull();
                        } else if (jSONSerializer2.references == null || !jSONSerializer2.references.containsKey(obj5)) {
                            ObjectSerializer objectSerializer = jSONSerializer2.config.get(obj5.getClass());
                            jSONSerializer2.context = new SerialContext(serialContext, obj3, obj4, 0);
                            objectSerializer.write(jSONSerializer2, obj5, Integer.valueOf(i), type2);
                        } else {
                            jSONSerializer2.writeReference(obj5);
                        }
                    }
                    jSONSerializer.decrementIdent();
                    jSONSerializer.println();
                    serializeWriter.write(93);
                    jSONSerializer2.context = serialContext;
                    return;
                }
                int i2 = serializeWriter.count + 1;
                if (i2 > serializeWriter.buf.length) {
                    if (serializeWriter.writer == null) {
                        serializeWriter.expandCapacity(i2);
                    } else {
                        serializeWriter.flush();
                        i2 = 1;
                    }
                }
                serializeWriter.buf[serializeWriter.count] = '[';
                serializeWriter.count = i2;
                for (int i3 = 0; i3 < list.size(); i3++) {
                    Object obj6 = list.get(i3);
                    if (i3 != 0) {
                        int i4 = serializeWriter.count + 1;
                        if (i4 > serializeWriter.buf.length) {
                            if (serializeWriter.writer == null) {
                                serializeWriter.expandCapacity(i4);
                            } else {
                                serializeWriter.flush();
                                i4 = 1;
                            }
                        }
                        serializeWriter.buf[serializeWriter.count] = ',';
                        serializeWriter.count = i4;
                    }
                    if (obj6 == null) {
                        serializeWriter.append((CharSequence) "null");
                    } else {
                        Class<?> cls = obj6.getClass();
                        if (cls == Integer.class) {
                            serializeWriter.writeInt(((Integer) obj6).intValue());
                        } else if (cls == Long.class) {
                            long longValue = ((Long) obj6).longValue();
                            if (z) {
                                serializeWriter.writeLong(longValue);
                                serializeWriter.write(76);
                            } else {
                                serializeWriter.writeLong(longValue);
                            }
                        } else if (cls == String.class) {
                            String str = (String) obj6;
                            if ((serializeWriter.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                                serializeWriter.writeStringWithSingleQuote(str);
                            } else {
                                serializeWriter.writeStringWithDoubleQuote(str, 0, true);
                            }
                        } else {
                            if ((serializeWriter.features & SerializerFeature.DisableCircularReferenceDetect.mask) == 0) {
                                jSONSerializer2.context = new SerialContext(serialContext, obj3, obj4, 0);
                            }
                            if (jSONSerializer2.references == null || !jSONSerializer2.references.containsKey(obj6)) {
                                jSONSerializer2.config.get(obj6.getClass()).write(jSONSerializer2, obj6, Integer.valueOf(i3), type2);
                            } else {
                                jSONSerializer2.writeReference(obj6);
                            }
                        }
                    }
                }
                int i5 = serializeWriter.count + 1;
                if (i5 > serializeWriter.buf.length) {
                    if (serializeWriter.writer == null) {
                        serializeWriter.expandCapacity(i5);
                    } else {
                        serializeWriter.flush();
                        i5 = 1;
                    }
                }
                serializeWriter.buf[serializeWriter.count] = ']';
                serializeWriter.count = i5;
                jSONSerializer2.context = serialContext;
            } catch (Throwable th) {
                Throwable th2 = th;
                jSONSerializer2.context = serialContext;
                throw th2;
            }
        } else if ((serializeWriter.features & SerializerFeature.WriteNullListAsEmpty.mask) != 0) {
            serializeWriter.write((String) "[]");
        } else {
            serializeWriter.writeNull();
        }
    }
}
