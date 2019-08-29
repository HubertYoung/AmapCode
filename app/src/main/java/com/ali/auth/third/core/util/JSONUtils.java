package com.ali.auth.third.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static Boolean optBoolean(JSONObject jSONObject, String str) {
        return Boolean.valueOf(jSONObject.has(str) ? jSONObject.optBoolean(str) : false);
    }

    public static Integer optInteger(JSONObject jSONObject, String str) {
        if (jSONObject.has(str)) {
            return Integer.valueOf(jSONObject.optInt(str));
        }
        return null;
    }

    public static Long optLong(JSONObject jSONObject, String str) {
        if (jSONObject.has(str)) {
            return Long.valueOf(jSONObject.optLong(str));
        }
        return null;
    }

    public static String optString(JSONObject jSONObject, String str) {
        if (jSONObject.has(str)) {
            return jSONObject.optString(str);
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r5v6, types: [T, java.lang.Byte[]] */
    /* JADX WARNING: type inference failed for: r5v8, types: [java.lang.Character[], T] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v6, types: [T, java.lang.Byte[]]
      assigns: [java.lang.Byte[]]
      uses: [?[], T, ?[OBJECT, ARRAY][]]
      mth insns count: 157
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T parseStringValue(java.lang.String r4, java.lang.Class<T> r5) {
        /*
            r0 = 0
            if (r4 == 0) goto L_0x01a1
            if (r5 != 0) goto L_0x0006
            return r0
        L_0x0006:
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x000f
            return r4
        L_0x000f:
            java.lang.Class r1 = java.lang.Short.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L_0x019c
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x0021
            goto L_0x019c
        L_0x0021:
            java.lang.Class r1 = java.lang.Integer.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L_0x0197
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x0033
            goto L_0x0197
        L_0x0033:
            java.lang.Class r1 = java.lang.Long.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L_0x0192
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x0045
            goto L_0x0192
        L_0x0045:
            java.lang.Class r1 = java.lang.Boolean.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L_0x018d
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x0057
            goto L_0x018d
        L_0x0057:
            java.lang.Class r1 = java.lang.Float.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L_0x0188
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x0069
            goto L_0x0188
        L_0x0069:
            java.lang.Class r1 = java.lang.Double.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L_0x0183
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x007b
            goto L_0x0183
        L_0x007b:
            java.lang.Class r1 = java.lang.Byte.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L_0x017e
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x008d
            goto L_0x017e
        L_0x008d:
            java.lang.Class r1 = java.lang.Character.TYPE
            boolean r1 = r1.equals(r5)
            r2 = 0
            if (r1 != 0) goto L_0x0175
            java.lang.Class<java.lang.Character> r1 = java.lang.Character.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x00a0
            goto L_0x0175
        L_0x00a0:
            java.lang.Class<java.util.Date> r1 = java.util.Date.class
            boolean r1 = r1.isAssignableFrom(r5)
            if (r1 == 0) goto L_0x00c0
            java.text.SimpleDateFormat r5 = new java.text.SimpleDateFormat     // Catch:{ ParseException -> 0x00b7 }
            java.lang.String r0 = "yyyyMMddHHmmssSSSZ"
            java.util.Locale r1 = java.util.Locale.US     // Catch:{ ParseException -> 0x00b7 }
            r5.<init>(r0, r1)     // Catch:{ ParseException -> 0x00b7 }
            java.util.Date r4 = r5.parse(r4)     // Catch:{ ParseException -> 0x00b7 }
            return r4
        L_0x00b7:
            r4 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r0 = "Parse Date error"
            r5.<init>(r0, r4)
            throw r5
        L_0x00c0:
            char r1 = r4.charAt(r2)
            boolean r3 = r5.isArray()
            if (r3 == 0) goto L_0x0149
            java.lang.Class r5 = r5.getComponentType()
            r3 = 91
            if (r1 != r3) goto L_0x00e3
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ Exception -> 0x00dc }
            r0.<init>(r4)     // Catch:{ Exception -> 0x00dc }
            java.lang.Object[] r4 = toPOJOArray(r0, r5)     // Catch:{ Exception -> 0x00dc }
            return r4
        L_0x00dc:
            r4 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            r5.<init>(r4)
            throw r5
        L_0x00e3:
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x00f2
            java.lang.String r5 = ","
            java.lang.String[] r4 = r4.split(r5)
            return r4
        L_0x00f2:
            java.lang.Class r1 = java.lang.Character.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x00ff
            char[] r4 = r4.toCharArray()
            return r4
        L_0x00ff:
            java.lang.Class<java.lang.Character> r1 = java.lang.Character.class
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x011d
            char[] r4 = r4.toCharArray()
            int r5 = r4.length
            java.lang.Character[] r5 = new java.lang.Character[r5]
        L_0x010e:
            int r0 = r5.length
            if (r2 >= r0) goto L_0x011c
            char r0 = r4[r2]
            java.lang.Character r0 = java.lang.Character.valueOf(r0)
            r5[r2] = r0
            int r2 = r2 + 1
            goto L_0x010e
        L_0x011c:
            return r5
        L_0x011d:
            java.lang.Class r1 = java.lang.Byte.TYPE
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x012a
            byte[] r4 = android.util.Base64.decode(r4, r2)
            return r4
        L_0x012a:
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x0148
            byte[] r4 = android.util.Base64.decode(r4, r2)
            int r5 = r4.length
            java.lang.Byte[] r5 = new java.lang.Byte[r5]
        L_0x0139:
            int r0 = r5.length
            if (r2 >= r0) goto L_0x0147
            byte r0 = r4[r2]
            java.lang.Byte r0 = java.lang.Byte.valueOf(r0)
            r5[r2] = r0
            int r2 = r2 + 1
            goto L_0x0139
        L_0x0147:
            return r5
        L_0x0148:
            return r0
        L_0x0149:
            r2 = 123(0x7b, float:1.72E-43)
            if (r1 != r2) goto L_0x016b
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0164 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x0164 }
            java.lang.Class<java.util.Map> r4 = java.util.Map.class
            boolean r4 = r4.isAssignableFrom(r5)     // Catch:{ Exception -> 0x0164 }
            if (r4 == 0) goto L_0x015f
            java.util.Map r4 = toMap(r0)     // Catch:{ Exception -> 0x0164 }
            return r4
        L_0x015f:
            java.lang.Object r4 = toPOJO(r0, r5)     // Catch:{ Exception -> 0x0164 }
            return r4
        L_0x0164:
            r4 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            r5.<init>(r4)
            throw r5
        L_0x016b:
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            boolean r5 = r5.isAssignableFrom(r1)
            if (r5 == 0) goto L_0x0174
            return r4
        L_0x0174:
            return r0
        L_0x0175:
            char r4 = r4.charAt(r2)
            java.lang.Character r4 = java.lang.Character.valueOf(r4)
            return r4
        L_0x017e:
            java.lang.Byte r4 = java.lang.Byte.valueOf(r4)
            return r4
        L_0x0183:
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            return r4
        L_0x0188:
            java.lang.Float r4 = java.lang.Float.valueOf(r4)
            return r4
        L_0x018d:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            return r4
        L_0x0192:
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            return r4
        L_0x0197:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            return r4
        L_0x019c:
            java.lang.Short r4 = java.lang.Short.valueOf(r4)
            return r4
        L_0x01a1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.util.JSONUtils.parseStringValue(java.lang.String, java.lang.Class):java.lang.Object");
    }

    public static String toJson(Map<String, Object> map) {
        return toJsonObject(map).toString();
    }

    public static JSONArray toJsonArray(List<Object> list) {
        JSONArray jSONArray = new JSONArray();
        for (Object next : list) {
            if (next instanceof Map) {
                next = toJsonObject((Map) next);
            }
            jSONArray.put(next);
        }
        return jSONArray;
    }

    public static JSONArray toJsonArray(Object[] objArr) {
        JSONArray jSONArray = new JSONArray();
        for (JSONObject jSONObject : objArr) {
            if (jSONObject instanceof Map) {
                jSONObject = toJsonObject((Map) jSONObject);
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static JSONObject toJsonObject(Map<String, ? extends Object> map) {
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            for (Entry next : map.entrySet()) {
                Object value = next.getValue();
                if (value != null) {
                    if (value instanceof Map) {
                        str = (String) next.getKey();
                        value = toJsonObject((Map) value);
                    } else if (value instanceof List) {
                        str = (String) next.getKey();
                        value = toJsonArray((List) value);
                    } else if (value.getClass().isArray()) {
                        str = (String) next.getKey();
                        value = toJsonArray((Object[]) value);
                    } else {
                        str = (String) next.getKey();
                    }
                    jSONObject.put(str, value);
                }
            }
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonString(Map<String, String> map) {
        return toJsonObject(map).toString();
    }

    public static List<Object> toList(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(jSONArray.length());
        int i = 0;
        int length = jSONArray.length();
        while (i < length) {
            Object obj = jSONArray.get(i);
            Object obj2 = obj instanceof JSONObject ? toMap((JSONObject) obj) : obj instanceof JSONArray ? toList((JSONArray) obj) : jSONArray.get(i);
            arrayList.add(obj2);
            i++;
        }
        return arrayList;
    }

    public static Map<String, Object> toMap(JSONObject jSONObject) throws JSONException {
        HashMap hashMap = new HashMap();
        if (jSONObject == null) {
            return hashMap;
        }
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            Object opt = jSONObject.opt(next);
            if (opt instanceof JSONObject) {
                opt = toMap((JSONObject) opt);
            } else if (opt instanceof JSONArray) {
                opt = toList((JSONArray) opt);
            }
            hashMap.put(next, opt);
        }
        return hashMap;
    }

    public static <T> T toPOJO(JSONObject jSONObject, Class<T> cls) {
        Field[] fields;
        char c;
        Object obj;
        if (jSONObject == null || cls == null || cls == Void.TYPE) {
            return null;
        }
        try {
            T newInstance = cls.newInstance();
            for (Field field : cls.getFields()) {
                Class type = field.getType();
                String name = field.getName();
                if (jSONObject.has(name)) {
                    if (!type.isPrimitive()) {
                        if (type == String.class) {
                            obj = jSONObject.getString(name);
                        } else {
                            if (!(type == Boolean.class || type == Integer.class || type == Short.class || type == Long.class)) {
                                if (type != Double.class) {
                                    obj = type.isArray() ? toPOJOArray(jSONObject.getJSONArray(name), type.getComponentType()) : Map.class.isAssignableFrom(type) ? toMap(jSONObject.getJSONObject(name)) : toPOJO(jSONObject.getJSONObject(name), type);
                                }
                            }
                            obj = jSONObject.get(name);
                        }
                        field.set(newInstance, obj);
                    } else if (type == Boolean.TYPE) {
                        field.setBoolean(newInstance, jSONObject.getBoolean(name));
                    } else if (type == Byte.TYPE) {
                        field.setByte(newInstance, (byte) jSONObject.getInt(name));
                    } else if (type == Character.TYPE) {
                        String string = jSONObject.getString(name);
                        if (string != null) {
                            if (string.length() != 0) {
                                c = string.charAt(0);
                                field.setChar(newInstance, c);
                            }
                        }
                        c = 0;
                        field.setChar(newInstance, c);
                    } else if (type == Short.TYPE) {
                        field.setShort(newInstance, (short) jSONObject.getInt(name));
                    } else if (type == Integer.TYPE) {
                        field.setInt(newInstance, jSONObject.getInt(name));
                    } else if (type == Long.TYPE) {
                        field.setLong(newInstance, jSONObject.getLong(name));
                    } else if (type == Float.TYPE) {
                        field.setFloat(newInstance, (float) jSONObject.getDouble(name));
                    } else if (type == Double.TYPE) {
                        field.setDouble(newInstance, jSONObject.getDouble(name));
                    }
                }
            }
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<T>, code=java.lang.Class, for r6v0, types: [java.lang.Class, java.lang.Class<T>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T[] toPOJOArray(org.json.JSONArray r5, java.lang.Class r6) {
        /*
            if (r5 == 0) goto L_0x00f4
            if (r6 == 0) goto L_0x00f4
            java.lang.Class r0 = java.lang.Void.TYPE
            if (r6 != r0) goto L_0x000a
            goto L_0x00f4
        L_0x000a:
            int r0 = r5.length()
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r6, r0)
            r1 = 0
            r2 = 0
        L_0x0014:
            int r3 = r5.length()     // Catch:{ JSONException -> 0x00ed }
            if (r2 >= r3) goto L_0x00ea
            boolean r3 = r6.isPrimitive()     // Catch:{ JSONException -> 0x00ed }
            if (r3 != 0) goto L_0x0074
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            if (r6 != r3) goto L_0x0029
            java.lang.String r3 = r5.getString(r2)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x006f
        L_0x0029:
            java.lang.Class<java.lang.Boolean> r3 = java.lang.Boolean.class
            if (r6 == r3) goto L_0x006b
            java.lang.Class<java.lang.Integer> r3 = java.lang.Integer.class
            if (r6 == r3) goto L_0x006b
            java.lang.Class<java.lang.Short> r3 = java.lang.Short.class
            if (r6 == r3) goto L_0x006b
            java.lang.Class<java.lang.Long> r3 = java.lang.Long.class
            if (r6 == r3) goto L_0x006b
            java.lang.Class<java.lang.Double> r3 = java.lang.Double.class
            if (r6 != r3) goto L_0x003e
            goto L_0x006b
        L_0x003e:
            boolean r3 = r6.isArray()     // Catch:{ JSONException -> 0x00ed }
            if (r3 == 0) goto L_0x0051
            org.json.JSONArray r3 = r5.getJSONArray(r2)     // Catch:{ JSONException -> 0x00ed }
            java.lang.Class r4 = r6.getComponentType()     // Catch:{ JSONException -> 0x00ed }
            java.lang.Object[] r3 = toPOJOArray(r3, r4)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x006f
        L_0x0051:
            java.lang.Class<java.util.Map> r3 = java.util.Map.class
            boolean r3 = r3.isAssignableFrom(r6)     // Catch:{ JSONException -> 0x00ed }
            if (r3 == 0) goto L_0x0062
            org.json.JSONObject r3 = r5.getJSONObject(r2)     // Catch:{ JSONException -> 0x00ed }
            java.util.Map r3 = toMap(r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x006f
        L_0x0062:
            org.json.JSONObject r3 = r5.getJSONObject(r2)     // Catch:{ JSONException -> 0x00ed }
            java.lang.Object r3 = toPOJO(r3, r6)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x006f
        L_0x006b:
            java.lang.Object r3 = r5.get(r2)     // Catch:{ JSONException -> 0x00ed }
        L_0x006f:
            java.lang.reflect.Array.set(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x0074:
            java.lang.Class r3 = java.lang.Boolean.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x0081
            boolean r3 = r5.getBoolean(r2)     // Catch:{ JSONException -> 0x00ed }
            java.lang.reflect.Array.setBoolean(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x0081:
            java.lang.Class r3 = java.lang.Byte.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x008e
            int r3 = r5.getInt(r2)     // Catch:{ JSONException -> 0x00ed }
            byte r3 = (byte) r3     // Catch:{ JSONException -> 0x00ed }
            java.lang.reflect.Array.setByte(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x008e:
            java.lang.Class r3 = java.lang.Character.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x00a9
            java.lang.String r3 = r5.getString(r2)     // Catch:{ JSONException -> 0x00ed }
            if (r3 == 0) goto L_0x00a4
            int r4 = r3.length()     // Catch:{ JSONException -> 0x00ed }
            if (r4 != 0) goto L_0x009f
            goto L_0x00a4
        L_0x009f:
            char r3 = r3.charAt(r1)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00a5
        L_0x00a4:
            r3 = 0
        L_0x00a5:
            java.lang.reflect.Array.setChar(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x00a9:
            java.lang.Class r3 = java.lang.Short.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x00b6
            int r3 = r5.getInt(r2)     // Catch:{ JSONException -> 0x00ed }
            short r3 = (short) r3     // Catch:{ JSONException -> 0x00ed }
            java.lang.reflect.Array.setShort(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x00b6:
            java.lang.Class r3 = java.lang.Integer.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x00c2
            int r3 = r5.getInt(r2)     // Catch:{ JSONException -> 0x00ed }
            java.lang.reflect.Array.setInt(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x00c2:
            java.lang.Class r3 = java.lang.Long.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x00ce
            long r3 = r5.getLong(r2)     // Catch:{ JSONException -> 0x00ed }
            java.lang.reflect.Array.setLong(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x00ce:
            java.lang.Class r3 = java.lang.Float.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x00db
            double r3 = r5.getDouble(r2)     // Catch:{ JSONException -> 0x00ed }
            float r3 = (float) r3     // Catch:{ JSONException -> 0x00ed }
            java.lang.reflect.Array.setFloat(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
            goto L_0x00e6
        L_0x00db:
            java.lang.Class r3 = java.lang.Double.TYPE     // Catch:{ JSONException -> 0x00ed }
            if (r6 != r3) goto L_0x00e6
            double r3 = r5.getDouble(r2)     // Catch:{ JSONException -> 0x00ed }
            java.lang.reflect.Array.setDouble(r0, r2, r3)     // Catch:{ JSONException -> 0x00ed }
        L_0x00e6:
            int r2 = r2 + 1
            goto L_0x0014
        L_0x00ea:
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            return r0
        L_0x00ed:
            r5 = move-exception
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            r6.<init>(r5)
            throw r6
        L_0x00f4:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.util.JSONUtils.toPOJOArray(org.json.JSONArray, java.lang.Class):java.lang.Object[]");
    }

    public static String[] toStringArray(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return new String[0];
        }
        String[] strArr = new String[jSONArray.length()];
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArray.optString(i);
        }
        return strArr;
    }
}
