package com.autonavi.miniapp.plugin.lbs;

import android.text.TextUtils;
import android.util.SparseArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SimpleLocationCache {
    private SparseArray<LocCache> caches = new SparseArray<>();

    static class LocCache {
        public String locStr;
        public long time;

        public LocCache(long j, String str) {
            this.time = j;
            this.locStr = str;
        }
    }

    public static boolean isValid(JSONObject jSONObject) {
        return jSONObject != null && jSONObject.getDouble("latitude").doubleValue() >= 0.0d && jSONObject.getDouble("longitude").doubleValue() >= 0.0d;
    }

    private String getCache(int i, long j) {
        LocCache locCache = this.caches.get(i);
        if (locCache != null && !TextUtils.isEmpty(locCache.locStr) && (System.currentTimeMillis() / 1000) - j <= locCache.time) {
            return locCache.locStr;
        }
        return null;
    }

    public JSONObject getCacheJsonObj(int i, long j) {
        try {
            return (JSONObject) JSON.parse(getCache(i, j));
        } catch (Throwable unused) {
            return null;
        }
    }

    public void saveCache(int i, JSONObject jSONObject) {
        if (i >= 0 && jSONObject != null) {
            saveCache(i, jSONObject.toJSONString());
        }
    }

    public void saveCache(int i, String str) {
        if (i >= 0 && !TextUtils.isEmpty(str)) {
            this.caches.put(i, new LocCache(System.currentTimeMillis() / 1000, str));
        }
    }
}
