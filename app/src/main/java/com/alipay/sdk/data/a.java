package com.alipay.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.i;
import org.json.JSONObject;

public final class a {
    public static final int a = 3500;
    public static final String b = "http://h5.m.taobao.com/trade/paySuccess.html?bizOrderId=$OrderId$&";
    public static final int c = 1000;
    public static final int d = 20000;
    public static final String e = "alipay_cashier_dynamic_config";
    public static final String f = "timeout";
    public static final String g = "st_sdk_config";
    public static final String h = "tbreturl";
    private static a k;
    int i = 3500;
    public String j = b;

    public final int a() {
        if (this.i < 1000 || this.i > 20000) {
            return 3500;
        }
        new StringBuilder("DynamicConfig::getJumpTimeout >").append(this.i);
        return this.i;
    }

    private String c() {
        return this.j;
    }

    public static a b() {
        if (k == null) {
            a aVar = new a();
            k = aVar;
            String b2 = i.b(b.a().a, e, null);
            if (!TextUtils.isEmpty(b2)) {
                try {
                    JSONObject jSONObject = new JSONObject(b2);
                    aVar.i = jSONObject.optInt("timeout", 3500);
                    aVar.j = jSONObject.optString(h, b).trim();
                } catch (Throwable unused) {
                }
            }
        }
        return k;
    }

    private void d() {
        String b2 = i.b(b.a().a, e, null);
        if (!TextUtils.isEmpty(b2)) {
            try {
                JSONObject jSONObject = new JSONObject(b2);
                this.i = jSONObject.optInt("timeout", 3500);
                this.j = jSONObject.optString(h, b).trim();
            } catch (Throwable unused) {
            }
        }
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                this.i = jSONObject.optInt("timeout", 3500);
                this.j = jSONObject.optString(h, b).trim();
            } catch (Throwable unused) {
            }
        }
    }

    private void e() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("timeout", a());
            jSONObject.put(h, this.j);
            i.a(b.a().a, e, jSONObject.toString());
        } catch (Exception unused) {
        }
    }

    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject optJSONObject = new JSONObject(str).optJSONObject(g);
                this.i = optJSONObject.optInt("timeout", 3500);
                this.j = optJSONObject.optString(h, b).trim();
            } catch (Throwable unused) {
            }
        }
    }

    public final void a(Context context) {
        new Thread(new b(this, context)).start();
    }

    private static /* synthetic */ void a(a aVar, String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject optJSONObject = new JSONObject(str).optJSONObject(g);
                aVar.i = optJSONObject.optInt("timeout", 3500);
                aVar.j = optJSONObject.optString(h, b).trim();
            } catch (Throwable unused) {
            }
        }
    }

    private static /* synthetic */ void a(a aVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("timeout", aVar.a());
            jSONObject.put(h, aVar.j);
            i.a(b.a().a, e, jSONObject.toString());
        } catch (Exception unused) {
        }
    }
}
