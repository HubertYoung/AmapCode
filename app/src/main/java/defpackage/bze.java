package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bze reason: default package */
/* compiled from: SearchResultAjxProxy */
public final class bze {
    public static final /* synthetic */ boolean i = true;
    public final bid a;
    public int b;
    public bxi c;
    public bxd d;
    public String e = "";
    public bxh f;
    public Map<String, JsFunctionCallback> g;
    defpackage.bxg.a h = new defpackage.bxg.a() {
        public final void a(int i, String str) {
            try {
                bze.a(bvd.a(bze.this.f.b, bze.this.f.m), bze.a(bze.this, str));
            } catch (JSONException e) {
                e.printStackTrace();
                bze.a(2, e, bze.a(bze.this, str));
            }
        }

        public final void a(int i) {
            switch (i) {
                case 1:
                    bze.a(3, new Exception("error!"), bze.a(bze.this, (String) ""));
                    return;
                case 2:
                    bze.a(bby.c(bze.this.f.c().city) ? 4 : 5, new Exception("error!"), bze.a(bze.this, (String) ""));
                    return;
                case 3:
                case 4:
                case 5:
                case 8:
                    bze.this.c;
                    bze.this.b;
                    bxi.a(bze.this.f);
                    return;
                case 6:
                    bze.a(bby.c(bze.this.f.c().city) ? 6 : 7, new Exception("error!"), bze.a(bze.this, (String) ""));
                    return;
                case 7:
                    bze.a(8, new Exception("error!"), bze.a(bze.this, (String) ""));
                    return;
                default:
                    bze.a(2, new Exception("请求已取消"), bze.a(bze.this, (String) ""));
                    return;
            }
        }

        public final void b_() {
            bze.a(2, new Exception("请求已取消"), bze.a(bze.this, (String) ""));
        }
    };

    /* renamed from: bze$a */
    /* compiled from: SearchResultAjxProxy */
    public static class a {
        public final String a;
        public final String b;
        public final String c;
        public final String d;
        public final String e;
        public final String f;
        public final String g;
        public final String h;
        public final String i;
        public final String j;

        public a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
            this.h = str5;
            this.g = str6;
            this.e = str7;
            this.f = str8;
            this.i = str9;
            this.j = str10;
        }
    }

    public bze(bid bid, int i2) {
        this.a = bid;
        this.b = i2;
        this.f = new bxh();
        this.g = new ConcurrentHashMap();
    }

    public final void a(bxh bxh) {
        this.f = bxh;
        this.f.b(this.h);
        this.f.a(this.h);
    }

    public static double a(String str) {
        double d2 = -1.0d;
        if (TextUtils.isEmpty(str) || "undefined".equals(str)) {
            return -1.0d;
        }
        try {
            d2 = Double.parseDouble(str);
        } catch (Exception unused) {
        }
        return d2;
    }

    public static void a(JSONObject jSONObject, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("status", 1);
                jSONObject2.put("content", jSONObject);
                jsFunctionCallback.callback(jSONObject2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void a(int i2, Exception exc, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("status", i2);
                jSONObject.put("error", exc.getMessage());
                jsFunctionCallback.callback(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ JsFunctionCallback a(bze bze, String str) {
        JsFunctionCallback jsFunctionCallback = !TextUtils.isEmpty(str) ? bze.g.get(str) : null;
        return jsFunctionCallback == null ? bze.g.get(bze.e) : jsFunctionCallback;
    }
}
