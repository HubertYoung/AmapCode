package com.xiaomi.push.log;

import android.content.Context;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.service.ba;
import com.xiaomi.smack.util.e;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class b {
    private static volatile b c;
    /* access modifiers changed from: private */
    public final ConcurrentLinkedQueue<C0080b> a = new ConcurrentLinkedQueue<>();
    /* access modifiers changed from: private */
    public Context b;

    class a extends C0080b {
        a() {
            super();
        }

        public void b() {
            b.this.b();
        }
    }

    /* renamed from: com.xiaomi.push.log.b$b reason: collision with other inner class name */
    class C0080b extends com.xiaomi.channel.commonutils.misc.k.b {
        long i = System.currentTimeMillis();

        C0080b() {
        }

        public void b() {
        }

        public boolean d() {
            return true;
        }

        /* access modifiers changed from: 0000 */
        public final boolean e() {
            return System.currentTimeMillis() - this.i > 172800000;
        }
    }

    class c extends C0080b {
        String a;
        String b;
        File c;
        int d;
        boolean e;
        boolean f;

        c(String str, String str2, File file, boolean z) {
            super();
            this.a = str;
            this.b = str2;
            this.c = file;
            this.f = z;
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x0039  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x003e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean f() {
            /*
                r10 = this;
                com.xiaomi.push.log.b r0 = com.xiaomi.push.log.b.this
                android.content.Context r0 = r0.b
                java.lang.String r1 = "log.timestamp"
                r2 = 0
                android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r2)
                java.lang.String r1 = "log.requst"
                java.lang.String r3 = ""
                java.lang.String r1 = r0.getString(r1, r3)
                long r3 = java.lang.System.currentTimeMillis()
                org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x002b }
                r5.<init>(r1)     // Catch:{ JSONException -> 0x002b }
                java.lang.String r1 = "time"
                long r6 = r5.getLong(r1)     // Catch:{ JSONException -> 0x002b }
                java.lang.String r1 = "times"
                int r1 = r5.getInt(r1)     // Catch:{ JSONException -> 0x002c }
                goto L_0x002d
            L_0x002b:
                r6 = r3
            L_0x002c:
                r1 = 0
            L_0x002d:
                long r3 = java.lang.System.currentTimeMillis()
                long r3 = r3 - r6
                r8 = 86400000(0x5265c00, double:4.2687272E-316)
                int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
                if (r3 >= 0) goto L_0x003e
                r3 = 10
                if (r1 <= r3) goto L_0x0043
                return r2
            L_0x003e:
                long r6 = java.lang.System.currentTimeMillis()
                r1 = 0
            L_0x0043:
                org.json.JSONObject r2 = new org.json.JSONObject
                r2.<init>()
                r3 = 1
                java.lang.String r4 = "time"
                r2.put(r4, r6)     // Catch:{ JSONException -> 0x0066 }
                java.lang.String r4 = "times"
                int r1 = r1 + r3
                r2.put(r4, r1)     // Catch:{ JSONException -> 0x0066 }
                android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ JSONException -> 0x0066 }
                java.lang.String r1 = "log.requst"
                java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x0066 }
                android.content.SharedPreferences$Editor r0 = r0.putString(r1, r2)     // Catch:{ JSONException -> 0x0066 }
                r0.commit()     // Catch:{ JSONException -> 0x0066 }
                return r3
            L_0x0066:
                r0 = move-exception
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "JSONException on put "
                r1.<init>(r2)
                java.lang.String r0 = r0.getMessage()
                r1.append(r0)
                java.lang.String r0 = r1.toString()
                com.xiaomi.channel.commonutils.logger.b.c(r0)
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.log.b.c.f():boolean");
        }

        public void b() {
            try {
                if (f()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(Oauth2AccessToken.KEY_UID, ba.e());
                    hashMap.put("token", this.b);
                    hashMap.put(com.alipay.sdk.app.statistic.c.a, d.k(b.this.b));
                    d.a(this.a, hashMap, this.c, "file");
                }
                this.e = true;
            } catch (IOException unused) {
            }
        }

        public void c() {
            if (!this.e) {
                this.d++;
                if (this.d < 3) {
                    b.this.a.add(this);
                }
            }
            if (this.e || this.d >= 3) {
                this.c.delete();
            }
            b.this.a((long) ((1 << this.d) * 1000));
        }

        public boolean d() {
            return d.e(b.this.b) || (this.f && d.c(b.this.b));
        }
    }

    private b(Context context) {
        this.b = context;
        this.a.add(new a());
        b(0);
    }

    public static b a(Context context) {
        if (c == null) {
            synchronized (b.class) {
                if (c == null) {
                    c = new b(context);
                }
            }
        }
        c.b = context;
        return c;
    }

    /* access modifiers changed from: private */
    public void a(long j) {
        C0080b peek = this.a.peek();
        if (peek != null && peek.d()) {
            b(j);
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (!com.xiaomi.channel.commonutils.file.c.b() && !com.xiaomi.channel.commonutils.file.c.a()) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(this.b.getExternalFilesDir(null));
                sb.append("/.logcache");
                File file = new File(sb.toString());
                if (file.exists() && file.isDirectory()) {
                    for (File delete : file.listFiles()) {
                        delete.delete();
                    }
                }
            } catch (NullPointerException unused) {
            }
        }
    }

    private void b(long j) {
        if (!this.a.isEmpty()) {
            e.a(new d(this), j);
        }
    }

    private void c() {
        while (!this.a.isEmpty()) {
            C0080b peek = this.a.peek();
            if (peek != null) {
                if (peek.e() || this.a.size() > 6) {
                    com.xiaomi.channel.commonutils.logger.b.c("remove Expired task");
                    this.a.remove();
                } else {
                    return;
                }
            }
        }
    }

    public void a() {
        c();
        a(0);
    }

    public void a(String str, String str2, Date date, Date date2, int i, boolean z) {
        ConcurrentLinkedQueue<C0080b> concurrentLinkedQueue = this.a;
        c cVar = new c(this, i, date, date2, str, str2, z);
        concurrentLinkedQueue.add(cVar);
        b(0);
    }
}
