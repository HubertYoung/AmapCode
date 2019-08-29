package com.alibaba.baichuan.android.auth;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.utils.cache.CacheUtils;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import org.json.JSONObject;

public class d {
    private String a;
    private long b;
    private long c;

    static class a {
        public static d a = new d();
    }

    private d() {
        this.a = "";
        this.b = -1;
        this.c = -1;
        f();
    }

    public static d a() {
        return a.a;
    }

    private void f() {
        JSONObject jsonObject = JSONUtils.getJsonObject(CacheUtils.getDecryptedCache("BC_AuthToken"));
        if (jsonObject != null) {
            this.a = JSONUtils.optString(jsonObject, "AuthToken");
            this.b = JSONUtils.optLong(jsonObject, "TokenExpires").longValue();
            this.c = JSONUtils.optLong(jsonObject, "RefreshTime").longValue();
        }
    }

    private String g() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("AuthToken", this.a);
            jSONObject.put("TokenExpires", this.b);
            jSONObject.put("RefreshTime", this.c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public synchronized void a(String str, long j) {
        AlibcLogger.e("alibc", "setToken ".concat(String.valueOf(str)));
        this.a = str;
        this.b = System.currentTimeMillis() + (j * 1000);
        this.c = System.currentTimeMillis();
        CacheUtils.asyncPutEncryptedCache("BC_AuthToken", a().g());
    }

    public String b() {
        if (d() && this.c + 86400000 < System.currentTimeMillis()) {
            AlibcAuth.authRefresh();
            AlibcLogger.e("alibc", "authRefresh");
        }
        return this.a;
    }

    public String c() {
        return this.a;
    }

    public boolean d() {
        return !TextUtils.isEmpty(this.a) && this.b >= System.currentTimeMillis();
    }

    public synchronized boolean e() {
        this.a = null;
        this.b = -1;
        this.c = -1;
        CacheUtils.asyncPutEncryptedCache("BC_AuthToken", a().g());
        return true;
    }
}
