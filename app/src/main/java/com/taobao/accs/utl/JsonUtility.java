package com.taobao.accs.utl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtility {

    public static class JsonObjectBuilder {
        JSONObject jObject = new JSONObject();

        public JsonObjectBuilder put(String str, String str2) {
            if (!(str2 == null || str == null)) {
                try {
                    this.jObject.put(str, str2);
                } catch (JSONException unused) {
                }
            }
            return this;
        }

        public JsonObjectBuilder put(String str, Integer num) {
            if (num == null) {
                return this;
            }
            try {
                this.jObject.put(str, num);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JsonObjectBuilder put(String str, Boolean bool) {
            if (bool == null) {
                return this;
            }
            try {
                this.jObject.put(str, bool);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JsonObjectBuilder put(String str, Long l) {
            if (l == null) {
                return this;
            }
            try {
                this.jObject.put(str, l);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JsonObjectBuilder put(String str, JSONArray jSONArray) {
            if (jSONArray == null) {
                return this;
            }
            try {
                this.jObject.put(str, jSONArray);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JSONObject build() {
            return this.jObject;
        }
    }

    public static String getString(JSONObject jSONObject, String str, String str2) throws JSONException {
        return (jSONObject != null && jSONObject.has(str)) ? jSONObject.getString(str) : str2;
    }
}
