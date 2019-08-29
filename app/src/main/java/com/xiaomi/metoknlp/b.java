package com.xiaomi.metoknlp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.List;

public class b {
    private static boolean a = true;
    private static int b = 1;
    private static b c;
    private Context d;
    private SharedPreferences e = this.d.getSharedPreferences("config", 0);
    private List f = new ArrayList();

    private b(Context context) {
        this.d = context;
    }

    public static b a() {
        return c;
    }

    public static synchronized void a(Context context) {
        synchronized (b.class) {
            if (c == null) {
                c = new b(context);
            }
        }
    }

    public static boolean b() {
        return false;
    }

    public void a(g gVar) {
        if (gVar != null) {
            synchronized (this.f) {
                this.f.add(gVar);
            }
        }
    }

    public void a(String str) {
        Editor edit = this.e.edit();
        edit.putString("config_update_time", str);
        edit.commit();
    }

    public void c() {
        synchronized (this.f) {
            for (g a2 : this.f) {
                a2.a();
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d() {
        /*
            r11 = this;
            java.lang.String r0 = r11.k()
            java.lang.String r1 = com.xiaomi.metoknlp.a.e.h()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000f
            return
        L_0x000f:
            java.lang.String r0 = com.xiaomi.metoknlp.a.e.d()
            java.lang.String r2 = "collect"
            java.lang.String r3 = "t_"
            java.lang.String r4 = java.lang.String.valueOf(r0)
            java.lang.String r3 = r3.concat(r4)
            java.lang.String r2 = com.xiaomi.metoknlp.c.a(r2, r3)
            r3 = 0
            r4 = 5
            if (r2 == 0) goto L_0x002d
            boolean r5 = r2.isEmpty()
            if (r5 == 0) goto L_0x004e
        L_0x002d:
            r2 = 0
        L_0x002e:
            java.lang.String r5 = "collect"
            java.lang.String r6 = "t_"
            java.lang.String r7 = java.lang.String.valueOf(r0)
            java.lang.String r6 = r6.concat(r7)
            java.lang.String r5 = com.xiaomi.metoknlp.c.a(r5, r6)
            if (r5 == 0) goto L_0x0046
            boolean r6 = r5.isEmpty()
            if (r6 == 0) goto L_0x004a
        L_0x0046:
            int r2 = r2 + 1
            if (r2 != r4) goto L_0x002e
        L_0x004a:
            if (r2 != r4) goto L_0x004d
            return
        L_0x004d:
            r2 = r5
        L_0x004e:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x0163 }
            r5.<init>(r2)     // Catch:{ Exception -> 0x0163 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0163 }
            java.lang.String r6 = "data"
            java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x0163 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0163 }
            android.content.SharedPreferences r5 = r11.e     // Catch:{ Exception -> 0x0163 }
            java.lang.String r6 = "s_f"
            java.lang.String r7 = ""
            java.lang.String r5 = r5.getString(r6, r7)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r6 = "s_f"
            java.lang.String r7 = ""
            java.lang.String r6 = r2.optString(r6, r7)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r7 = "f_d_d"
            int r7 = r2.optInt(r7, r3)     // Catch:{ Exception -> 0x0163 }
            boolean r7 = com.xiaomi.metoknlp.a.e.a(r7)     // Catch:{ Exception -> 0x0163 }
            android.content.SharedPreferences r8 = r11.e     // Catch:{ Exception -> 0x0163 }
            android.content.SharedPreferences$Editor r8 = r8.edit()     // Catch:{ Exception -> 0x0163 }
            java.lang.String r9 = "s_f"
            r8.putString(r9, r6)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r9 = "f_d_d"
            r8.putBoolean(r9, r7)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r7 = "m_s_u"
            java.lang.String r9 = "m_s_u"
            java.lang.String r10 = "https://metok.sys.miui.com"
            java.lang.String r2 = r2.optString(r9, r10)     // Catch:{ Exception -> 0x0163 }
            r8.putString(r7, r2)     // Catch:{ Exception -> 0x0163 }
            r8.commit()     // Catch:{ Exception -> 0x0163 }
            java.util.Date r2 = new java.util.Date     // Catch:{ Exception -> 0x0163 }
            r2.<init>()     // Catch:{ Exception -> 0x0163 }
            java.util.Date r2 = new java.util.Date     // Catch:{ Exception -> 0x0163 }
            r2.<init>()     // Catch:{ Exception -> 0x0163 }
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0163 }
            java.lang.String r7 = "yyyyMMddHHmm"
            r2.<init>(r7)     // Catch:{ Exception -> 0x0163 }
            if (r5 == 0) goto L_0x00d6
            boolean r7 = r5.isEmpty()     // Catch:{ Exception -> 0x0163 }
            if (r7 != 0) goto L_0x00d6
            if (r6 == 0) goto L_0x00d6
            boolean r7 = r6.isEmpty()     // Catch:{ Exception -> 0x0163 }
            if (r7 != 0) goto L_0x00d6
            java.util.Date r5 = r2.parse(r5)     // Catch:{ Exception -> 0x0163 }
            java.util.Date r2 = r2.parse(r6)     // Catch:{ Exception -> 0x0163 }
            boolean r6 = r2.before(r5)     // Catch:{ Exception -> 0x0163 }
            if (r6 != 0) goto L_0x00cf
            boolean r2 = r2.equals(r5)     // Catch:{ Exception -> 0x0163 }
            if (r2 == 0) goto L_0x00d6
        L_0x00cf:
            r11.a(r1)     // Catch:{ Exception -> 0x0163 }
            r11.c()     // Catch:{ Exception -> 0x0163 }
            return
        L_0x00d6:
            java.lang.String r2 = "collect"
            java.lang.String r5 = "f_"
            java.lang.String r6 = java.lang.String.valueOf(r0)
            java.lang.String r5 = r5.concat(r6)
            java.lang.String r2 = com.xiaomi.metoknlp.c.a(r2, r5)
            if (r2 == 0) goto L_0x00ee
            boolean r5 = r2.isEmpty()
            if (r5 == 0) goto L_0x010d
        L_0x00ee:
            java.lang.String r2 = "collect"
            java.lang.String r5 = "f_"
            java.lang.String r6 = java.lang.String.valueOf(r0)
            java.lang.String r5 = r5.concat(r6)
            java.lang.String r2 = com.xiaomi.metoknlp.c.a(r2, r5)
            if (r2 == 0) goto L_0x0106
            boolean r5 = r2.isEmpty()
            if (r5 == 0) goto L_0x010a
        L_0x0106:
            int r3 = r3 + 1
            if (r3 != r4) goto L_0x00ee
        L_0x010a:
            if (r3 != r4) goto L_0x010d
            return
        L_0x010d:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{  }
            r0.<init>(r2)     // Catch:{  }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{  }
            java.lang.String r3 = "data"
            java.lang.String r0 = r0.getString(r3)     // Catch:{  }
            r2.<init>(r0)     // Catch:{  }
            android.content.SharedPreferences r0 = r11.e     // Catch:{  }
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{  }
            java.lang.String r3 = "d_m_i"
            java.lang.String r4 = "d_m_i"
            r5 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            long r7 = r2.optLong(r4, r5)     // Catch:{  }
            r0.putLong(r3, r7)     // Catch:{  }
            java.lang.String r3 = "d_n_s"
            java.lang.String r4 = "d_n_s"
            int r7 = b     // Catch:{  }
            int r4 = r2.optInt(r4, r7)     // Catch:{  }
            boolean r4 = com.xiaomi.metoknlp.a.e.a(r4)     // Catch:{  }
            r0.putBoolean(r3, r4)     // Catch:{  }
            java.lang.String r3 = "d_s_t"
            java.lang.String r4 = "d_s_t"
            long r7 = r2.optLong(r4, r5)     // Catch:{  }
            r0.putLong(r3, r7)     // Catch:{  }
            java.lang.String r3 = "d_s_c_t"
            java.lang.String r4 = "d_s_c_t"
            long r4 = r2.optLong(r4, r5)     // Catch:{  }
            r0.putLong(r3, r4)     // Catch:{  }
            r0.commit()     // Catch:{  }
            r11.a(r1)
            r11.c()
        L_0x0163:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.metoknlp.b.d():void");
    }

    public String e() {
        return this.e.getString("m_s_u", "https://metok.sys.miui.com");
    }

    public boolean f() {
        return this.e.getBoolean("f_d_d", true);
    }

    public long g() {
        return this.e.getLong("d_m_i", Long.MAX_VALUE);
    }

    public boolean h() {
        return this.e.getBoolean("d_n_s", a);
    }

    public long i() {
        return this.e.getLong("d_s_t", Long.MAX_VALUE);
    }

    public long j() {
        return this.e.getLong("d_s_c_t", Long.MAX_VALUE);
    }

    public String k() {
        return this.e.getString("config_update_time", "0");
    }
}
