package com.sijla.e;

import android.content.Context;
import android.text.TextUtils;
import com.sijla.g.a.a;
import com.sijla.g.a.b;
import com.sijla.g.d;
import com.sijla.g.f;
import com.sijla.g.h;
import com.sijla.g.i;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c extends b {
    private final String b;
    private String c;
    private Context d;

    public c(Context context) {
        this.d = context;
        StringBuilder sb = new StringBuilder();
        sb.append(b.a(context));
        sb.append("qfs/");
        this.b = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.b);
        sb2.append("qfs.js-");
        sb2.append(d.b());
        this.c = sb2.toString();
    }

    public void a(JSONObject jSONObject) {
        JSONObject b2 = b(jSONObject);
        JSONArray jSONArray = new JSONArray();
        File file = new File(this.c);
        if (file.exists()) {
            try {
                jSONArray = new JSONArray(com.sijla.g.b.b(com.sijla.g.b.a(file)));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (b2 != null) {
            jSONArray.put(b2);
            com.sijla.g.a.c.a(this.c, jSONArray.toString());
        }
    }

    public void b() {
        try {
            if (a.g(this.d) && com.sijla.g.b.a(this.d, (String) "qfs_time_dur", com.sijla.c.c.a.optLong("timepost", 3600))) {
                c();
                d();
                a(i());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void c() {
        String b2 = d.b();
        File[] a = com.sijla.g.a.c.a(this.b);
        if (a != null && a.length > 0) {
            for (File file : a) {
                if (!file.getName().contains(b2)) {
                    com.sijla.g.a.c.a(file);
                }
            }
        }
    }

    private void a(List<File> list) {
        if (list != null && list.size() > 0) {
            HashMap hashMap = new HashMap();
            for (File next : list) {
                if (next.exists()) {
                    hashMap.put(next.getName(), next);
                }
            }
            JSONArray optJSONArray = com.sijla.c.c.a.optJSONArray("fdurls");
            if (optJSONArray != null) {
                boolean z = true;
                if (1 != com.sijla.c.c.a.optInt("repeatReportst", 0)) {
                    z = false;
                }
                h.a("QFS", optJSONArray, z, new JSONObject(), hashMap);
            }
            b(list);
        }
    }

    private void d() {
        File file = new File(this.c);
        if (file.exists()) {
            try {
                JSONArray jSONArray = new JSONArray(com.sijla.g.b.b(com.sijla.g.b.a(file)));
                int length = jSONArray.length();
                if (length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        sb.append(jSONArray.optJSONObject(i).toString());
                        sb.append("\n");
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(com.sijla.g.b.b()[0]);
                    String sb3 = sb2.toString();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(this.b);
                    sb4.append("qfs_");
                    sb4.append(i.b(this.d));
                    sb4.append("_");
                    sb4.append(d.c());
                    sb4.append("_");
                    sb4.append(sb3);
                    String sb5 = sb4.toString();
                    com.sijla.g.a.c.a(sb.toString(), sb5, true);
                    f.a(sb5, true);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            } finally {
                file.delete();
            }
        }
    }

    private JSONObject b(JSONObject jSONObject) {
        if (jSONObject == null || jSONObject.length() <= 0) {
            return null;
        }
        String optString = jSONObject.optString("s1", "");
        if (TextUtils.isEmpty(optString)) {
            return null;
        }
        try {
            String substring = com.sijla.g.a.d.a(optString).substring(0, 8);
            JSONObject jSONObject2 = new JSONObject();
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                if (next != null && !"s1".equals(next) && !"e".equals(next)) {
                    try {
                        jSONObject2.put(next, com.sijla.d.b.b(substring, jSONObject.optString(next)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return jSONObject2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private List<File> i() {
        File[] a = com.sijla.g.a.c.a(this.b, (FileFilter) new FileFilter() {
            public boolean accept(File file) {
                String name = file.getName();
                return name.startsWith("qfs") && name.endsWith("gz");
            }
        });
        if (a == null || a.length <= 0) {
            return null;
        }
        return Arrays.asList(a);
    }

    private void b(List<File> list) {
        com.sijla.g.a.c.a(list);
    }

    public void h() {
        b();
    }

    public void g() {
        b();
    }

    public void a() {
        super.a();
        b();
    }
}
