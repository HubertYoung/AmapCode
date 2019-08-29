package com.sijla.g;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.sijla.b.c;
import java.io.File;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class h {

    public interface a {
        void a(String str);

        void a(String str, JSONObject jSONObject);
    }

    public static void a(String str, JSONObject jSONObject, a aVar, boolean z) {
        JSONObject jSONObject2;
        com.sijla.g.b.a.a a2 = com.sijla.g.b.a.a().a(str, jSONObject);
        if (aVar != null) {
            int a3 = a2.a();
            if (200 != a3 && 204 != a3) {
                aVar.a(String.format(Locale.getDefault(), "[%d] %s %s", new Object[]{Integer.valueOf(a3), str, (String) a2.b()}));
            } else if (z) {
                String str2 = (String) a2.b();
                try {
                    jSONObject2 = new JSONObject(str2);
                } catch (Throwable unused) {
                    jSONObject2 = b.d(str2);
                }
                aVar.a(str, jSONObject2);
            } else {
                aVar.a(str, jSONObject);
            }
        }
    }

    public static boolean a(String str, JSONObject jSONObject) {
        c cVar = new c();
        com.sijla.g.b.a.a a2 = com.sijla.g.b.a.a().a(str, jSONObject);
        int a3 = a2.a();
        if (200 == a3 || 204 == a3) {
            cVar.a(true);
        } else {
            cVar.a(false);
            cVar.a(a2.b());
        }
        return cVar.b();
    }

    public static c a(String str, JSONObject jSONObject, Map<String, File> map) {
        c cVar = new c();
        com.sijla.g.b.a.a a2 = com.sijla.g.b.a.a().a(str, jSONObject, map);
        int a3 = a2.a();
        if (200 == a3 || 204 == a3) {
            cVar.a(true);
        } else {
            cVar.a(false);
            cVar.a(a2.b());
        }
        return cVar;
    }

    public static File a(String str, JSONObject jSONObject, String str2) {
        return a(str, jSONObject, str2, true);
    }

    public static File a(String str, JSONObject jSONObject, String str2, boolean z) {
        c cVar = new c();
        cVar.a(false);
        File file = new File(str2);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        int a2 = com.sijla.g.b.a.a().b(str, jSONObject, new com.sijla.g.b.b.a.a(file)).a();
        if (204 == a2 && z) {
            cVar.a(true);
            if (z) {
                a(str2);
            }
        }
        if (200 == a2) {
            cVar.a(true);
        }
        if (cVar.b()) {
            return file;
        }
        return null;
    }

    public static File a(String str, String str2) {
        if (b.a(str) || b.a(str2)) {
            return null;
        }
        c cVar = new c();
        cVar.a(false);
        File file = new File(str2);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        int a2 = com.sijla.g.b.a.a().a(str, (JSONObject) null, (com.sijla.g.b.b.a) new com.sijla.g.b.b.a.a(file)).a();
        if (204 == a2) {
            cVar.a(true);
            a(str2);
        }
        if (200 == a2) {
            cVar.a(true);
        }
        if (cVar.b()) {
            return file;
        }
        return null;
    }

    private static void a(String str) {
        File file = new File(str.replace(".gz", ""));
        if (file.exists() && file.isFile()) {
            com.sijla.g.a.c.a(Token.SEPARATOR, file.getAbsolutePath(), true);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" lastModified:");
            sb.append(d.b(file.lastModified()));
            g.a(sb.toString());
        }
    }

    public static boolean a(String str, JSONArray jSONArray, boolean z, JSONObject jSONObject, Map<String, File> map) {
        int i = 0;
        if (jSONArray == null || jSONArray.length() == 0 || map == null) {
            return false;
        }
        boolean z2 = false;
        while (i < jSONArray.length()) {
            try {
                String optString = jSONArray.optString(i, "");
                if ((z || !z2) && !b.a(optString) && a(optString, jSONObject, map).b() && !z2) {
                    z2 = true;
                }
                i++;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return z2;
    }
}
