package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.d;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class c {
    private static c b;
    String a;
    private Context c;
    private a d;
    private Map<String, a> e;

    public static class a {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public boolean h = true;
        public boolean i = false;
        public int j = 1;
        private Context k;

        public a(Context context) {
            this.k = context;
        }

        public static a a(Context context, String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                a aVar = new a(context);
                aVar.a = jSONObject.getString("appId");
                aVar.b = jSONObject.getString("appToken");
                aVar.c = jSONObject.getString("regId");
                aVar.d = jSONObject.getString("regSec");
                aVar.f = jSONObject.getString("devId");
                aVar.e = jSONObject.getString("vName");
                aVar.h = jSONObject.getBoolean("valid");
                aVar.i = jSONObject.getBoolean("paused");
                aVar.j = jSONObject.getInt("envType");
                aVar.g = jSONObject.getString("regResource");
                return aVar;
            } catch (Throwable th) {
                b.a(th);
                return null;
            }
        }

        public static String a(a aVar) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("appId", aVar.a);
                jSONObject.put("appToken", aVar.b);
                jSONObject.put("regId", aVar.c);
                jSONObject.put("regSec", aVar.d);
                jSONObject.put("devId", aVar.f);
                jSONObject.put("vName", aVar.e);
                jSONObject.put("valid", aVar.h);
                jSONObject.put("paused", aVar.i);
                jSONObject.put("envType", aVar.j);
                jSONObject.put("regResource", aVar.g);
                return jSONObject.toString();
            } catch (Throwable th) {
                b.a(th);
                return null;
            }
        }

        private String d() {
            return com.xiaomi.channel.commonutils.android.a.a(this.k, this.k.getPackageName());
        }

        public void a(int i2) {
            this.j = i2;
        }

        public void a(String str, String str2) {
            this.c = str;
            this.d = str2;
            this.f = d.h(this.k);
            this.e = d();
            this.h = true;
            Editor edit = c.b(this.k).edit();
            edit.putString("regId", str);
            edit.putString("regSec", str2);
            edit.putString("devId", this.f);
            edit.putString("vName", d());
            edit.putBoolean("valid", true);
            edit.commit();
        }

        public void a(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.g = str3;
            Editor edit = c.b(this.k).edit();
            edit.putString("appId", this.a);
            edit.putString("appToken", str2);
            edit.putString("regResource", str3);
            edit.commit();
        }

        public void a(boolean z) {
            this.i = z;
        }

        public boolean a() {
            return c(this.a, this.b);
        }

        public void b() {
            c.b(this.k).edit().clear().commit();
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.f = null;
            this.e = null;
            this.h = false;
            this.i = false;
            this.j = 1;
        }

        public void b(String str, String str2) {
            this.c = str;
            this.d = str2;
            this.f = d.h(this.k);
            this.e = d();
            this.h = true;
        }

        public void b(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.g = str3;
        }

        public void c() {
            this.h = false;
            c.b(this.k).edit().putBoolean("valid", this.h).commit();
        }

        public boolean c(String str, String str2) {
            return TextUtils.equals(this.a, str) && TextUtils.equals(this.b, str2) && !TextUtils.isEmpty(this.c) && !TextUtils.isEmpty(this.d) && TextUtils.equals(this.f, d.h(this.k));
        }
    }

    private c(Context context) {
        this.c = context;
        n();
    }

    public static c a(Context context) {
        if (b == null) {
            b = new c(context);
        }
        return b;
    }

    public static SharedPreferences b(Context context) {
        return context.getSharedPreferences("mipush", 0);
    }

    private void n() {
        this.d = new a(this.c);
        this.e = new HashMap();
        SharedPreferences b2 = b(this.c);
        this.d.a = b2.getString("appId", null);
        this.d.b = b2.getString("appToken", null);
        this.d.c = b2.getString("regId", null);
        this.d.d = b2.getString("regSec", null);
        this.d.f = b2.getString("devId", null);
        if (!TextUtils.isEmpty(this.d.f) && this.d.f.startsWith("a-")) {
            this.d.f = d.h(this.c);
            b2.edit().putString("devId", this.d.f).commit();
        }
        this.d.e = b2.getString("vName", null);
        this.d.h = b2.getBoolean("valid", true);
        this.d.i = b2.getBoolean("paused", false);
        this.d.j = b2.getInt("envType", 1);
        this.d.g = b2.getString("regResource", null);
    }

    public void a(int i) {
        this.d.a(i);
        b(this.c).edit().putInt("envType", i).commit();
    }

    public void a(String str) {
        Editor edit = b(this.c).edit();
        edit.putString("vName", str);
        edit.commit();
        this.d.e = str;
    }

    public void a(String str, a aVar) {
        this.e.put(str, aVar);
        String a2 = a.a(aVar);
        b(this.c).edit().putString("hybrid_app_info_".concat(String.valueOf(str)), a2).commit();
    }

    public void a(String str, String str2, String str3) {
        this.d.a(str, str2, str3);
    }

    public void a(boolean z) {
        this.d.a(z);
        b(this.c).edit().putBoolean("paused", z).commit();
    }

    public boolean a() {
        return !TextUtils.equals(com.xiaomi.channel.commonutils.android.a.a(this.c, this.c.getPackageName()), this.d.e);
    }

    public boolean a(String str, String str2) {
        return this.d.c(str, str2);
    }

    public a b(String str) {
        if (this.e.containsKey(str)) {
            return this.e.get(str);
        }
        String concat = "hybrid_app_info_".concat(String.valueOf(str));
        SharedPreferences b2 = b(this.c);
        if (!b2.contains(concat)) {
            return null;
        }
        a a2 = a.a(this.c, b2.getString(concat, ""));
        this.e.put(concat, a2);
        return a2;
    }

    public void b(String str, String str2) {
        this.d.a(str, str2);
    }

    public boolean b() {
        if (this.d.a()) {
            return true;
        }
        b.a((String) "Don't send message before initialization succeeded!");
        return false;
    }

    public boolean b(String str, String str2, String str3) {
        a b2 = b(str3);
        return b2 != null && TextUtils.equals(str, b2.a) && TextUtils.equals(str2, b2.b);
    }

    public String c() {
        return this.d.a;
    }

    public void c(String str) {
        this.e.remove(str);
        b(this.c).edit().remove("hybrid_app_info_".concat(String.valueOf(str))).commit();
    }

    public String d() {
        return this.d.b;
    }

    public String e() {
        return this.d.c;
    }

    public String f() {
        return this.d.d;
    }

    public String g() {
        return this.d.g;
    }

    public void h() {
        this.d.b();
    }

    public boolean i() {
        return this.d.a();
    }

    public void j() {
        this.d.c();
    }

    public boolean k() {
        return this.d.i;
    }

    public int l() {
        return this.d.j;
    }

    public boolean m() {
        return !this.d.h;
    }
}
