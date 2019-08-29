package org.json.alipay.inside;

import com.uc.crashsdk.export.LogType;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class JSONObject {
    public static final Object NULL = new Null();
    private Map map;

    static final class Null {
        /* access modifiers changed from: protected */
        public final Object clone() {
            return this;
        }

        public final boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public final String toString() {
            return "null";
        }

        private Null() {
        }
    }

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(JSONObject jSONObject, String[] strArr) throws JSONException {
        this();
        for (int i = 0; i < strArr.length; i++) {
            putOpt(strArr[i], jSONObject.opt(strArr[i]));
        }
    }

    public JSONObject(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '{') {
            throw jSONTokener.syntaxError("A JSONObject text must begin with '{'");
        }
        while (true) {
            char nextClean = jSONTokener.nextClean();
            if (nextClean == 0) {
                throw jSONTokener.syntaxError("A JSONObject text must end with '}'");
            } else if (nextClean != '}') {
                jSONTokener.back();
                String obj = jSONTokener.nextValue().toString();
                char nextClean2 = jSONTokener.nextClean();
                if (nextClean2 == '=') {
                    if (jSONTokener.next() != '>') {
                        jSONTokener.back();
                    }
                } else if (nextClean2 != ':') {
                    throw jSONTokener.syntaxError("Expected a ':' after a key");
                }
                put(obj, jSONTokener.nextValue());
                char nextClean3 = jSONTokener.nextClean();
                if (nextClean3 == ',' || nextClean3 == ';') {
                    if (jSONTokener.nextClean() != '}') {
                        jSONTokener.back();
                    } else {
                        return;
                    }
                } else if (nextClean3 != '}') {
                    throw jSONTokener.syntaxError("Expected a ',' or '}'");
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public JSONObject(Map map2) {
        this.map = map2 == null ? new HashMap() : map2;
    }

    public JSONObject(Map map2, boolean z) {
        this.map = new HashMap();
        if (map2 != null) {
            for (Entry entry : map2.entrySet()) {
                this.map.put(entry.getKey(), new JSONObject(entry.getValue(), z));
            }
        }
    }

    public JSONObject(Object obj) {
        this();
        populateInternalMap(obj, false);
    }

    public JSONObject(Object obj, boolean z) {
        this();
        populateInternalMap(obj, z);
    }

    private void populateInternalMap(Object obj, boolean z) {
        Class<?> cls = obj.getClass();
        if (cls.getClassLoader() == null) {
            z = false;
        }
        Method[] methods = z ? cls.getMethods() : cls.getDeclaredMethods();
        int i = 0;
        while (i < methods.length) {
            try {
                Method method = methods[i];
                String name = method.getName();
                String str = "";
                if (name.startsWith("get")) {
                    str = name.substring(3);
                } else if (name.startsWith("is")) {
                    str = name.substring(2);
                }
                if (str.length() > 0 && Character.isUpperCase(str.charAt(0)) && method.getParameterTypes().length == 0) {
                    if (str.length() == 1) {
                        str = str.toLowerCase();
                    } else if (!Character.isUpperCase(str.charAt(1))) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str.substring(0, 1).toLowerCase());
                        sb.append(str.substring(1));
                        str = sb.toString();
                    }
                    Object invoke = method.invoke(obj, null);
                    if (invoke == null) {
                        this.map.put(str, NULL);
                    } else if (invoke.getClass().isArray()) {
                        this.map.put(str, new JSONArray(invoke, z));
                    } else if (invoke instanceof Collection) {
                        this.map.put(str, new JSONArray((Collection) invoke, z));
                    } else if (invoke instanceof Map) {
                        this.map.put(str, new JSONObject((Map) invoke, z));
                    } else if (isStandardProperty(invoke.getClass())) {
                        this.map.put(str, invoke);
                    } else {
                        if (!invoke.getClass().getPackage().getName().startsWith(LogType.JAVA_TYPE)) {
                            if (invoke.getClass().getClassLoader() != null) {
                                this.map.put(str, new JSONObject(invoke, z));
                            }
                        }
                        this.map.put(str, invoke.toString());
                    }
                }
                i++;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isStandardProperty(Class cls) {
        return cls.isPrimitive() || cls.isAssignableFrom(Byte.class) || cls.isAssignableFrom(Short.class) || cls.isAssignableFrom(Integer.class) || cls.isAssignableFrom(Long.class) || cls.isAssignableFrom(Float.class) || cls.isAssignableFrom(Double.class) || cls.isAssignableFrom(Character.class) || cls.isAssignableFrom(String.class) || cls.isAssignableFrom(Boolean.class);
    }

    public JSONObject(Object obj, String[] strArr) {
        this();
        Class<?> cls = obj.getClass();
        for (String str : strArr) {
            try {
                put(str, cls.getField(str).get(obj));
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject(String str) throws JSONException {
        this(new JSONTokener(str));
    }

    public JSONObject accumulate(String str, Object obj) throws JSONException {
        testValidity(obj);
        Object opt = opt(str);
        if (opt == null) {
            if (obj instanceof JSONArray) {
                obj = new JSONArray().put(obj);
            }
            put(str, obj);
        } else if (opt instanceof JSONArray) {
            ((JSONArray) opt).put(obj);
        } else {
            put(str, (Object) new JSONArray().put(opt).put(obj));
        }
        return this;
    }

    public JSONObject append(String str, Object obj) throws JSONException {
        testValidity(obj);
        Object opt = opt(str);
        if (opt == null) {
            put(str, (Object) new JSONArray().put(obj));
        } else if (opt instanceof JSONArray) {
            put(str, (Object) ((JSONArray) opt).put(obj));
        } else {
            StringBuilder sb = new StringBuilder("JSONObject[");
            sb.append(str);
            sb.append("] is not a JSONArray.");
            throw new JSONException(sb.toString());
        }
        return this;
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return "null";
        }
        String d2 = Double.toString(d);
        if (d2.indexOf(46) > 0 && d2.indexOf(101) < 0 && d2.indexOf(69) < 0) {
            while (d2.endsWith("0")) {
                d2 = d2.substring(0, d2.length() - 1);
            }
            if (d2.endsWith(".")) {
                d2 = d2.substring(0, d2.length() - 1);
            }
        }
        return d2;
    }

    public Object get(String str) throws JSONException {
        Object opt = opt(str);
        if (opt != null) {
            return opt;
        }
        StringBuilder sb = new StringBuilder("JSONObject[");
        sb.append(quote(str));
        sb.append("] not found.");
        throw new JSONException(sb.toString());
    }

    public boolean getBoolean(String str) throws JSONException {
        Object obj = get(str);
        if (!obj.equals(Boolean.FALSE)) {
            boolean z = obj instanceof String;
            if (!z || !((String) obj).equalsIgnoreCase("false")) {
                if (obj.equals(Boolean.TRUE) || (z && ((String) obj).equalsIgnoreCase("true"))) {
                    return true;
                }
                StringBuilder sb = new StringBuilder("JSONObject[");
                sb.append(quote(str));
                sb.append("] is not a Boolean.");
                throw new JSONException(sb.toString());
            }
        }
        return false;
    }

    public double getDouble(String str) throws JSONException {
        Object obj = get(str);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).doubleValue();
            }
            return Double.valueOf((String) obj).doubleValue();
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder("JSONObject[");
            sb.append(quote(str));
            sb.append("] is not a number.");
            throw new JSONException(sb.toString());
        }
    }

    public int getInt(String str) throws JSONException {
        Object obj = get(str);
        return obj instanceof Number ? ((Number) obj).intValue() : (int) getDouble(str);
    }

    public JSONArray getJSONArray(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        StringBuilder sb = new StringBuilder("JSONObject[");
        sb.append(quote(str));
        sb.append("] is not a JSONArray.");
        throw new JSONException(sb.toString());
    }

    public JSONObject getJSONObject(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        StringBuilder sb = new StringBuilder("JSONObject[");
        sb.append(quote(str));
        sb.append("] is not a JSONObject.");
        throw new JSONException(sb.toString());
    }

    public long getLong(String str) throws JSONException {
        Object obj = get(str);
        return obj instanceof Number ? ((Number) obj).longValue() : (long) getDouble(str);
    }

    public static String[] getNames(JSONObject jSONObject) {
        int length = jSONObject.length();
        if (length == 0) {
            return null;
        }
        Iterator keys = jSONObject.keys();
        String[] strArr = new String[length];
        int i = 0;
        while (keys.hasNext()) {
            strArr[i] = (String) keys.next();
            i++;
        }
        return strArr;
    }

    public static String[] getNames(Object obj) {
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getFields();
        int length = fields.length;
        if (length == 0) {
            return null;
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = fields[i].getName();
        }
        return strArr;
    }

    public String getString(String str) throws JSONException {
        return get(str).toString();
    }

    public boolean has(String str) {
        return this.map.containsKey(str);
    }

    public boolean isNull(String str) {
        return NULL.equals(opt(str));
    }

    public Iterator keys() {
        return this.map.keySet().iterator();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        JSONArray jSONArray = new JSONArray();
        Iterator keys = keys();
        while (keys.hasNext()) {
            jSONArray.put(keys.next());
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        return jSONArray;
    }

    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException((String) "Null pointer");
        }
        testValidity(number);
        String obj = number.toString();
        if (obj.indexOf(46) <= 0 || obj.indexOf(101) >= 0 || obj.indexOf(69) >= 0) {
            return obj;
        }
        while (obj.endsWith("0")) {
            obj = obj.substring(0, obj.length() - 1);
        }
        return obj.endsWith(".") ? obj.substring(0, obj.length() - 1) : obj;
    }

    public Object opt(String str) {
        if (str == null) {
            return null;
        }
        return this.map.get(str);
    }

    public boolean optBoolean(String str) {
        return optBoolean(str, false);
    }

    public boolean optBoolean(String str, boolean z) {
        try {
            return getBoolean(str);
        } catch (Exception unused) {
            return z;
        }
    }

    public JSONObject put(String str, Collection collection) throws JSONException {
        put(str, (Object) new JSONArray(collection));
        return this;
    }

    public double optDouble(String str) {
        return optDouble(str, Double.NaN);
    }

    public double optDouble(String str, double d) {
        try {
            Object opt = opt(str);
            if (opt instanceof Number) {
                return ((Number) opt).doubleValue();
            }
            return new Double((String) opt).doubleValue();
        } catch (Exception unused) {
            return d;
        }
    }

    public int optInt(String str) {
        return optInt(str, 0);
    }

    public int optInt(String str, int i) {
        try {
            return getInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public JSONArray optJSONArray(String str) {
        Object opt = opt(str);
        if (opt instanceof JSONArray) {
            return (JSONArray) opt;
        }
        return null;
    }

    public JSONObject optJSONObject(String str) {
        Object opt = opt(str);
        if (opt instanceof JSONObject) {
            return (JSONObject) opt;
        }
        return null;
    }

    public long optLong(String str) {
        return optLong(str, 0);
    }

    public long optLong(String str, long j) {
        try {
            return getLong(str);
        } catch (Exception unused) {
            return j;
        }
    }

    public String optString(String str) {
        return optString(str, "");
    }

    public String optString(String str, String str2) {
        Object opt = opt(str);
        return opt != null ? opt.toString() : str2;
    }

    public JSONObject put(String str, boolean z) throws JSONException {
        put(str, (Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject put(String str, double d) throws JSONException {
        put(str, (Object) Double.valueOf(d));
        return this;
    }

    public JSONObject put(String str, int i) throws JSONException {
        put(str, (Object) Integer.valueOf(i));
        return this;
    }

    public JSONObject put(String str, long j) throws JSONException {
        put(str, (Object) new Long(j));
        return this;
    }

    public JSONObject put(String str, Map map2) throws JSONException {
        put(str, (Object) new JSONObject(map2));
        return this;
    }

    public JSONObject put(String str, Object obj) throws JSONException {
        if (str == null) {
            throw new JSONException((String) "Null key.");
        }
        if (obj != null) {
            testValidity(obj);
            this.map.put(str, obj);
        } else {
            remove(str);
        }
        return this;
    }

    public JSONObject putOpt(String str, Object obj) throws JSONException {
        if (!(str == null || obj == null)) {
            put(str, obj);
        }
        return this;
    }

    public static String quote(String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 4);
        stringBuffer.append('\"');
        int i = 0;
        char c = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt != '\"') {
                if (charAt != '/') {
                    if (charAt != '\\') {
                        switch (charAt) {
                            case 8:
                                stringBuffer.append("\\b");
                                break;
                            case 9:
                                stringBuffer.append("\\t");
                                break;
                            case 10:
                                stringBuffer.append("\\n");
                                break;
                            default:
                                switch (charAt) {
                                    case 12:
                                        stringBuffer.append("\\f");
                                        break;
                                    case 13:
                                        stringBuffer.append("\\r");
                                        break;
                                    default:
                                        if (charAt >= ' ' && ((charAt < 128 || charAt >= 160) && (charAt < 8192 || charAt >= 8448))) {
                                            stringBuffer.append(charAt);
                                            break;
                                        } else {
                                            StringBuilder sb = new StringBuilder("000");
                                            sb.append(Integer.toHexString(charAt));
                                            String sb2 = sb.toString();
                                            StringBuilder sb3 = new StringBuilder("\\u");
                                            sb3.append(sb2.substring(sb2.length() - 4));
                                            stringBuffer.append(sb3.toString());
                                            break;
                                        }
                                        break;
                                }
                        }
                    }
                } else {
                    if (c == '<') {
                        stringBuffer.append('\\');
                    }
                    stringBuffer.append(charAt);
                }
                i++;
                c = charAt;
            }
            stringBuffer.append('\\');
            stringBuffer.append(charAt);
            i++;
            c = charAt;
        }
        stringBuffer.append('\"');
        return stringBuffer.toString();
    }

    public Object remove(String str) {
        return this.map.remove(str);
    }

    public Iterator sortedKeys() {
        return new TreeSet(this.map.keySet()).iterator();
    }

    static void testValidity(Object obj) throws JSONException {
        if (obj == null) {
            return;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.isInfinite() || d.isNaN()) {
                throw new JSONException((String) "JSON does not allow non-finite numbers.");
            }
        } else if (obj instanceof Float) {
            Float f = (Float) obj;
            if (f.isInfinite() || f.isNaN()) {
                throw new JSONException((String) "JSON does not allow non-finite numbers.");
            }
        }
    }

    public JSONArray toJSONArray(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONArray2.put(opt(jSONArray.getString(i)));
        }
        return jSONArray2;
    }

    public String toString() {
        try {
            Iterator keys = keys();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (keys.hasNext()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(',');
                }
                Object next = keys.next();
                stringBuffer.append(quote(next.toString()));
                stringBuffer.append(':');
                stringBuffer.append(valueToString(this.map.get(next)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString(int i) throws JSONException {
        return toString(i, 0);
    }

    /* access modifiers changed from: 0000 */
    public String toString(int i, int i2) throws JSONException {
        int i3;
        int length = length();
        if (length == 0) {
            return bny.c;
        }
        Iterator sortedKeys = sortedKeys();
        StringBuffer stringBuffer = new StringBuffer("{");
        int i4 = i2 + i;
        if (length == 1) {
            Object next = sortedKeys.next();
            stringBuffer.append(quote(next.toString()));
            stringBuffer.append(": ");
            stringBuffer.append(valueToString(this.map.get(next), i, i2));
        } else {
            while (true) {
                i3 = 0;
                if (!sortedKeys.hasNext()) {
                    break;
                }
                Object next2 = sortedKeys.next();
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(",\n");
                } else {
                    stringBuffer.append(10);
                }
                while (i3 < i4) {
                    stringBuffer.append(' ');
                    i3++;
                }
                stringBuffer.append(quote(next2.toString()));
                stringBuffer.append(": ");
                stringBuffer.append(valueToString(this.map.get(next2), i, i4));
            }
            if (stringBuffer.length() > 1) {
                stringBuffer.append(10);
                while (i3 < i2) {
                    stringBuffer.append(' ');
                    i3++;
                }
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    static String valueToString(Object obj) throws JSONException {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof Number) {
            return numberToString((Number) obj);
        }
        if ((obj instanceof Boolean) || (obj instanceof JSONObject) || (obj instanceof JSONArray)) {
            return obj.toString();
        }
        if (obj instanceof Map) {
            return new JSONObject((Map) obj).toString();
        }
        if (obj instanceof Collection) {
            return new JSONArray((Collection) obj).toString();
        }
        if (obj.getClass().isArray()) {
            return new JSONArray(obj).toString();
        }
        return quote(obj.toString());
    }

    static String valueToString(Object obj, int i, int i2) throws JSONException {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof Number) {
            return numberToString((Number) obj);
        }
        if (obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).toString(i, i2);
        }
        if (obj instanceof JSONArray) {
            return ((JSONArray) obj).toString(i, i2);
        }
        if (obj instanceof Map) {
            return new JSONObject((Map) obj).toString(i, i2);
        }
        if (obj instanceof Collection) {
            return new JSONArray((Collection) obj).toString(i, i2);
        }
        if (obj.getClass().isArray()) {
            return new JSONArray(obj).toString(i, i2);
        }
        return quote(obj.toString());
    }

    public Writer write(Writer writer) throws JSONException {
        boolean z = false;
        try {
            Iterator keys = keys();
            writer.write(123);
            while (keys.hasNext()) {
                if (z) {
                    writer.write(44);
                }
                Object next = keys.next();
                writer.write(quote(next.toString()));
                writer.write(58);
                Object obj = this.map.get(next);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(valueToString(obj));
                }
                z = true;
            }
            writer.write(125);
            return writer;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }
}
