package com.alibaba.wireless.security.framework;

import java.io.File;
import org.json.JSONObject;

public class b {
    private static String b = "version";
    private static String c = "lib_dep_version";
    private static String d = "lib_dep_arch";
    private static String e = "target_plugin";
    private JSONObject a;
    private boolean f = true;
    private int g = 0;
    private boolean h = true;
    private String i = "";
    private boolean j = true;
    private String k = "";

    private b(JSONObject jSONObject) {
        this.a = jSONObject;
    }

    public static b a(File file) {
        b bVar = null;
        if (file != null) {
            if (!file.exists()) {
                return null;
            }
            try {
                String a2 = com.alibaba.wireless.security.framework.utils.b.a(file);
                if (a2 != null && a2.length() > 0) {
                    JSONObject jSONObject = new JSONObject(a2);
                    if ("1.0".equals(jSONObject.getString(b))) {
                        bVar = new b(jSONObject);
                    }
                }
            } catch (Exception unused) {
            }
        }
        return bVar;
    }

    public String a(String str) {
        try {
            return a().getString(str);
        } catch (Exception unused) {
            return "";
        }
    }

    public JSONObject a() {
        return this.a;
    }

    public int b() {
        int i2;
        if (this.f) {
            try {
                i2 = Integer.parseInt(a().getString(c));
            } catch (Exception unused) {
                i2 = 0;
            }
            this.g = i2;
            this.f = false;
        }
        return this.g;
    }

    public String c() {
        String str;
        if (this.h) {
            try {
                str = a().getString(d);
            } catch (Exception unused) {
                str = "";
            }
            this.i = str;
            this.h = false;
        }
        return this.i;
    }

    public String d() {
        String str;
        if (this.j) {
            try {
                str = a().getString(e);
            } catch (Exception unused) {
                str = "";
            }
            this.k = str;
            this.j = false;
        }
        return this.k;
    }
}
