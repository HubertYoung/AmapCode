package com.xiaomi.smack.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import com.xiaomi.channel.commonutils.misc.k;
import com.xiaomi.channel.commonutils.misc.k.b;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class g {
    private static k a = new k(true);
    private static int b = -1;
    /* access modifiers changed from: private */
    public static final Object c = new Object();
    /* access modifiers changed from: private */
    public static List<a> d = Collections.synchronizedList(new ArrayList());
    private static String e = "";
    private static com.xiaomi.push.providers.a f;

    static class a {
        public String a = "";
        public long b = 0;
        public int c = -1;
        public int d = -1;
        public String e = "";
        public long f = 0;

        public a(String str, long j, int i, int i2, String str2, long j2) {
            this.a = str;
            this.b = j;
            this.c = i;
            this.d = i2;
            this.e = str2;
            this.f = j2;
        }

        public boolean a(a aVar) {
            return TextUtils.equals(aVar.a, this.a) && TextUtils.equals(aVar.e, this.e) && aVar.c == this.c && aVar.d == this.d && Math.abs(aVar.b - this.b) <= 5000;
        }
    }

    public static int a(String str) {
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes().length;
        }
    }

    private static long a(int i, long j) {
        return (j * ((long) (i == 0 ? 13 : 11))) / 10;
    }

    public static void a(Context context) {
        b = c(context);
    }

    /* JADX INFO: finally extract failed */
    public static void a(Context context, String str, long j, boolean z, long j2) {
        boolean isEmpty;
        Context context2 = context;
        if (context2 != null && !TextUtils.isEmpty(str) && "com.xiaomi.xmsf".equals(context2.getPackageName())) {
            String str2 = str;
            if (!"com.xiaomi.xmsf".equals(str2)) {
                int b2 = b(context2);
                if (-1 != b2) {
                    synchronized (c) {
                        try {
                            isEmpty = d.isEmpty();
                            String d2 = b2 == 0 ? d(context2) : "";
                            a aVar = new a(str2, j2, b2, z ? 1 : 0, d2, a(b2, j));
                            a(aVar);
                        } catch (Throwable th) {
                            while (true) {
                                throw th;
                            }
                        }
                    }
                    if (isEmpty) {
                        a.a((b) new h(context2), 5000);
                    }
                }
            }
        }
    }

    private static void a(a aVar) {
        for (a next : d) {
            if (next.a(aVar)) {
                next.f += aVar.f;
                return;
            }
        }
        d.add(aVar);
    }

    private static int b(Context context) {
        if (b == -1) {
            b = c(context);
        }
        return b;
    }

    /* access modifiers changed from: private */
    public static void b(Context context, List<a> list) {
        try {
            synchronized (com.xiaomi.push.providers.a.a) {
                SQLiteDatabase writableDatabase = e(context).getWritableDatabase();
                writableDatabase.beginTransaction();
                try {
                    for (a next : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("package_name", next.a);
                        contentValues.put("message_ts", Long.valueOf(next.b));
                        contentValues.put("network_type", Integer.valueOf(next.c));
                        contentValues.put("bytes", Long.valueOf(next.f));
                        contentValues.put("rcv", Integer.valueOf(next.d));
                        contentValues.put(Constants.KEY_IMSI, next.e);
                        writableDatabase.insert("traffic", null, contentValues);
                    }
                    writableDatabase.setTransactionSuccessful();
                } finally {
                    writableDatabase.endTransaction();
                }
            }
        } catch (SQLiteException e2) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int c(android.content.Context r2) {
        /*
            r0 = -1
            java.lang.String r1 = "connectivity"
            java.lang.Object r2 = r2.getSystemService(r1)     // Catch:{ Exception -> 0x0018 }
            android.net.ConnectivityManager r2 = (android.net.ConnectivityManager) r2     // Catch:{ Exception -> 0x0018 }
            if (r2 != 0) goto L_0x000c
            return r0
        L_0x000c:
            android.net.NetworkInfo r2 = r2.getActiveNetworkInfo()     // Catch:{  }
            if (r2 != 0) goto L_0x0013
            return r0
        L_0x0013:
            int r2 = r2.getType()
            return r2
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smack.util.g.c(android.content.Context):int");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:14|15|16|(1:18)|19|20|21|22) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0029 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized java.lang.String d(android.content.Context r2) {
        /*
            java.lang.Class<com.xiaomi.smack.util.g> r0 = com.xiaomi.smack.util.g.class
            monitor-enter(r0)
            boolean r1 = com.xiaomi.channel.commonutils.android.f.e()     // Catch:{ all -> 0x002d }
            if (r1 != 0) goto L_0x000d
            java.lang.String r2 = ""
            monitor-exit(r0)
            return r2
        L_0x000d:
            java.lang.String r1 = e     // Catch:{ all -> 0x002d }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x002d }
            if (r1 != 0) goto L_0x0019
            java.lang.String r2 = e     // Catch:{ all -> 0x002d }
            monitor-exit(r0)
            return r2
        L_0x0019:
            java.lang.String r1 = "phone"
            java.lang.Object r2 = r2.getSystemService(r1)     // Catch:{ Exception -> 0x0029 }
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch:{ Exception -> 0x0029 }
            if (r2 == 0) goto L_0x0029
            java.lang.String r2 = r2.getSubscriberId()     // Catch:{ Exception -> 0x0029 }
            e = r2     // Catch:{ Exception -> 0x0029 }
        L_0x0029:
            java.lang.String r2 = e     // Catch:{ all -> 0x002d }
            monitor-exit(r0)
            return r2
        L_0x002d:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smack.util.g.d(android.content.Context):java.lang.String");
    }

    private static com.xiaomi.push.providers.a e(Context context) {
        if (f != null) {
            return f;
        }
        com.xiaomi.push.providers.a aVar = new com.xiaomi.push.providers.a(context);
        f = aVar;
        return aVar;
    }
}
