package org.json.alipay.inside;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JSONArray {
    private ArrayList myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList();
    }

    public JSONArray(JSONTokener jSONTokener) throws JSONException {
        char c;
        char nextClean;
        this();
        char nextClean2 = jSONTokener.nextClean();
        if (nextClean2 == '[') {
            c = ']';
        } else if (nextClean2 == '(') {
            c = ')';
        } else {
            throw jSONTokener.syntaxError("A JSONArray text must start with '['");
        }
        if (jSONTokener.nextClean() != ']') {
            jSONTokener.back();
            while (true) {
                if (jSONTokener.nextClean() == ',') {
                    jSONTokener.back();
                    this.myArrayList.add(null);
                } else {
                    jSONTokener.back();
                    this.myArrayList.add(jSONTokener.nextValue());
                }
                nextClean = jSONTokener.nextClean();
                if (nextClean == ')') {
                    break;
                } else if (nextClean == ',' || nextClean == ';') {
                    if (jSONTokener.nextClean() != ']') {
                        jSONTokener.back();
                    } else {
                        return;
                    }
                } else if (nextClean != ']') {
                    throw jSONTokener.syntaxError("Expected a ',' or ']'");
                }
            }
            if (c != nextClean) {
                StringBuilder sb = new StringBuilder("Expected a '");
                sb.append(Character.valueOf(c));
                sb.append("'");
                throw jSONTokener.syntaxError(sb.toString());
            }
        }
    }

    public JSONArray(String str) throws JSONException {
        this(new JSONTokener(str));
    }

    public JSONArray(Collection collection) {
        this.myArrayList = collection == null ? new ArrayList() : new ArrayList(collection);
    }

    public JSONArray(Collection<Object> collection, boolean z) {
        this.myArrayList = new ArrayList();
        if (collection != null) {
            for (Object jSONObject : collection) {
                this.myArrayList.add(new JSONObject(jSONObject, z));
            }
        }
    }

    public JSONArray(Object obj) throws JSONException {
        this();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                put(Array.get(obj, i));
            }
            return;
        }
        throw new JSONException((String) "JSONArray initial value should be a string or collection or array.");
    }

    public JSONArray(Object obj, boolean z) throws JSONException {
        this();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                put((Object) new JSONObject(Array.get(obj, i), z));
            }
            return;
        }
        throw new JSONException((String) "JSONArray initial value should be a string or collection or array.");
    }

    public Object get(int i) throws JSONException {
        Object opt = opt(i);
        if (opt != null) {
            return opt;
        }
        StringBuilder sb = new StringBuilder("JSONArray[");
        sb.append(i);
        sb.append("] not found.");
        throw new JSONException(sb.toString());
    }

    public boolean getBoolean(int i) throws JSONException {
        Object obj = get(i);
        if (!obj.equals(Boolean.FALSE)) {
            boolean z = obj instanceof String;
            if (!z || !((String) obj).equalsIgnoreCase("false")) {
                if (obj.equals(Boolean.TRUE) || (z && ((String) obj).equalsIgnoreCase("true"))) {
                    return true;
                }
                StringBuilder sb = new StringBuilder("JSONArray[");
                sb.append(i);
                sb.append("] is not a Boolean.");
                throw new JSONException(sb.toString());
            }
        }
        return false;
    }

    public double getDouble(int i) throws JSONException {
        Object obj = get(i);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).doubleValue();
            }
            return Double.valueOf((String) obj).doubleValue();
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder("JSONArray[");
            sb.append(i);
            sb.append("] is not a number.");
            throw new JSONException(sb.toString());
        }
    }

    public int getInt(int i) throws JSONException {
        Object obj = get(i);
        return obj instanceof Number ? ((Number) obj).intValue() : (int) getDouble(i);
    }

    public JSONArray getJSONArray(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        StringBuilder sb = new StringBuilder("JSONArray[");
        sb.append(i);
        sb.append("] is not a JSONArray.");
        throw new JSONException(sb.toString());
    }

    public JSONObject getJSONObject(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        StringBuilder sb = new StringBuilder("JSONArray[");
        sb.append(i);
        sb.append("] is not a JSONObject.");
        throw new JSONException(sb.toString());
    }

    public long getLong(int i) throws JSONException {
        Object obj = get(i);
        return obj instanceof Number ? ((Number) obj).longValue() : (long) getDouble(i);
    }

    public String getString(int i) throws JSONException {
        return get(i).toString();
    }

    public boolean isNull(int i) {
        return JSONObject.NULL.equals(opt(i));
    }

    public String join(String str) throws JSONException {
        int length = length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuffer.append(str);
            }
            stringBuffer.append(JSONObject.valueToString(this.myArrayList.get(i)));
        }
        return stringBuffer.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return this.myArrayList.get(i);
    }

    public boolean optBoolean(int i) {
        return optBoolean(i, false);
    }

    public boolean optBoolean(int i, boolean z) {
        try {
            return getBoolean(i);
        } catch (Exception unused) {
            return z;
        }
    }

    public double optDouble(int i) {
        return optDouble(i, Double.NaN);
    }

    public double optDouble(int i, double d) {
        try {
            return getDouble(i);
        } catch (Exception unused) {
            return d;
        }
    }

    public int optInt(int i) {
        return optInt(i, 0);
    }

    public int optInt(int i, int i2) {
        try {
            return getInt(i);
        } catch (Exception unused) {
            return i2;
        }
    }

    public JSONArray optJSONArray(int i) {
        Object opt = opt(i);
        if (opt instanceof JSONArray) {
            return (JSONArray) opt;
        }
        return null;
    }

    public JSONObject optJSONObject(int i) {
        Object opt = opt(i);
        if (opt instanceof JSONObject) {
            return (JSONObject) opt;
        }
        return null;
    }

    public long optLong(int i) {
        return optLong(i, 0);
    }

    public long optLong(int i, long j) {
        try {
            return getLong(i);
        } catch (Exception unused) {
            return j;
        }
    }

    public String optString(int i) {
        return optString(i, "");
    }

    public String optString(int i, String str) {
        Object opt = opt(i);
        return opt != null ? opt.toString() : str;
    }

    public JSONArray put(boolean z) {
        put((Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(Collection collection) {
        put((Object) new JSONArray(collection));
        return this;
    }

    public JSONArray put(double d) throws JSONException {
        Double valueOf = Double.valueOf(d);
        JSONObject.testValidity(valueOf);
        put((Object) valueOf);
        return this;
    }

    public JSONArray put(int i) {
        put((Object) Integer.valueOf(i));
        return this;
    }

    public JSONArray put(long j) {
        put((Object) new Long(j));
        return this;
    }

    public JSONArray put(Map map) {
        put((Object) new JSONObject(map));
        return this;
    }

    public JSONArray put(Object obj) {
        this.myArrayList.add(obj);
        return this;
    }

    public JSONArray put(int i, boolean z) throws JSONException {
        put(i, (Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(int i, Collection collection) throws JSONException {
        put(i, (Object) new JSONArray(collection));
        return this;
    }

    public JSONArray put(int i, double d) throws JSONException {
        put(i, (Object) Double.valueOf(d));
        return this;
    }

    public JSONArray put(int i, int i2) throws JSONException {
        put(i, (Object) Integer.valueOf(i2));
        return this;
    }

    public JSONArray put(int i, long j) throws JSONException {
        put(i, (Object) new Long(j));
        return this;
    }

    public JSONArray put(int i, Map map) throws JSONException {
        put(i, (Object) new JSONObject(map));
        return this;
    }

    public JSONArray put(int i, Object obj) throws JSONException {
        JSONObject.testValidity(obj);
        if (i < 0) {
            StringBuilder sb = new StringBuilder("JSONArray[");
            sb.append(i);
            sb.append("] not found.");
            throw new JSONException(sb.toString());
        }
        if (i < length()) {
            this.myArrayList.set(i, obj);
        } else {
            while (i != length()) {
                put(JSONObject.NULL);
            }
            put(obj);
        }
        return this;
    }

    public JSONObject toJSONObject(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0 || length() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONObject.put(jSONArray.getString(i), opt(i));
        }
        return jSONObject;
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder("[");
            sb.append(join(","));
            sb.append(']');
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString(int i) throws JSONException {
        return toString(i, 0);
    }

    /* access modifiers changed from: 0000 */
    public String toString(int i, int i2) throws JSONException {
        int length = length();
        if (length == 0) {
            return "[]";
        }
        StringBuffer stringBuffer = new StringBuffer("[");
        if (length == 1) {
            stringBuffer.append(JSONObject.valueToString(this.myArrayList.get(0), i, i2));
        } else {
            int i3 = i2 + i;
            stringBuffer.append(10);
            for (int i4 = 0; i4 < length; i4++) {
                if (i4 > 0) {
                    stringBuffer.append(",\n");
                }
                for (int i5 = 0; i5 < i3; i5++) {
                    stringBuffer.append(' ');
                }
                stringBuffer.append(JSONObject.valueToString(this.myArrayList.get(i4), i, i3));
            }
            stringBuffer.append(10);
            for (int i6 = 0; i6 < i2; i6++) {
                stringBuffer.append(' ');
            }
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    public Writer write(Writer writer) throws JSONException {
        try {
            int length = length();
            writer.write(91);
            int i = 0;
            boolean z = false;
            while (i < length) {
                if (z) {
                    writer.write(44);
                }
                Object obj = this.myArrayList.get(i);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(JSONObject.valueToString(obj));
                }
                i++;
                z = true;
            }
            writer.write(93);
            return writer;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }
}
