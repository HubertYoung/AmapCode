package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.System;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.List;

/* renamed from: chx reason: default package */
/* compiled from: AdiuStorageModel */
public final class chx {
    private static chx f;
    public List<String> a;
    public String b;
    private final Context c;
    private Handler d;
    private HandlerThread e;

    /* renamed from: chx$a */
    /* compiled from: AdiuStorageModel */
    static final class a extends Handler {
        private final WeakReference<chx> a;

        a(Looper looper, chx chx) {
            super(looper);
            this.a = new WeakReference<>(chx);
        }

        public final void handleMessage(Message message) {
            chx chx = (chx) this.a.get();
            if (chx != null && message != null) {
                if (message.what == -1) {
                    chx.a(chx);
                    return;
                }
                if (message.obj != null) {
                    chx.a((String) message.obj, message.what);
                }
            }
        }
    }

    public static chx a(Context context) {
        if (f == null) {
            synchronized (chx.class) {
                try {
                    if (f == null) {
                        f = new chx(context);
                    }
                }
            }
        }
        return f;
    }

    private chx(Context context) {
        this.c = context.getApplicationContext();
    }

    public final List<String> a() {
        if (this.a != null && this.a.size() > 0 && !TextUtils.isEmpty(this.a.get(0))) {
            return this.a;
        }
        this.a = c();
        return this.a;
    }

    private void b() {
        if (this.e == null) {
            this.e = new HandlerThread("AdiuSyncThread");
            this.e.start();
            this.d = new a(this.e.getLooper(), this);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0072 A[Catch:{ all -> 0x0115 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0092 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00d0 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String> c() {
        /*
            r9 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = ""
            android.content.Context r2 = r9.c     // Catch:{ Exception -> 0x001b }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Exception -> 0x001b }
            java.lang.String r3 = r9.b     // Catch:{ Exception -> 0x001b }
            java.lang.String r2 = android.provider.Settings.System.getString(r2, r3)     // Catch:{ Exception -> 0x001b }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x001b }
            if (r3 != 0) goto L_0x001b
            java.lang.String r2 = defpackage.cib.c(r2)     // Catch:{ Exception -> 0x001b }
            r1 = r2
        L_0x001b:
            java.lang.String r2 = ""
            android.content.Context r3 = r9.c
            java.lang.String r4 = r9.b
            java.lang.String r3 = defpackage.chy.a(r3, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x002f
            java.lang.String r2 = defpackage.cib.c(r3)
        L_0x002f:
            java.lang.String r3 = ""
            android.content.Context r4 = r9.c
            java.lang.String r5 = "SharedPreferenceAdiu"
            r6 = 0
            android.content.SharedPreferences r4 = r4.getSharedPreferences(r5, r6)
            java.lang.String r5 = r9.b
            r7 = 0
            java.lang.String r4 = r4.getString(r5, r7)
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x004b
            java.lang.String r3 = defpackage.cib.c(r4)
        L_0x004b:
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 3
            r4.<init>(r5)
            r5 = -1
            boolean r8 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0115 }
            if (r8 != 0) goto L_0x00ab
            r4.add(r1)     // Catch:{ all -> 0x0115 }
            boolean r7 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0115 }
            if (r7 != 0) goto L_0x006a
            boolean r7 = android.text.TextUtils.equals(r2, r1)     // Catch:{ all -> 0x0115 }
            if (r7 != 0) goto L_0x006c
            r4.add(r2)     // Catch:{ all -> 0x0115 }
        L_0x006a:
            r6 = 16
        L_0x006c:
            boolean r7 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0115 }
            if (r7 != 0) goto L_0x0084
            boolean r7 = android.text.TextUtils.equals(r3, r1)     // Catch:{ all -> 0x0115 }
            if (r7 != 0) goto L_0x0086
            boolean r2 = android.text.TextUtils.equals(r3, r2)     // Catch:{ all -> 0x0115 }
            if (r2 != 0) goto L_0x0081
            r4.add(r3)     // Catch:{ all -> 0x0115 }
        L_0x0081:
            r6 = r6 | 256(0x100, float:3.59E-43)
            goto L_0x0086
        L_0x0084:
            r6 = r6 | 256(0x100, float:3.59E-43)
        L_0x0086:
            if (r6 <= 0) goto L_0x0089
            r0 = r1
        L_0x0089:
            if (r6 <= 0) goto L_0x00aa
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x00aa
            monitor-enter(r9)
            r9.b()     // Catch:{ all -> 0x00a7 }
            android.os.Handler r1 = r9.d     // Catch:{ all -> 0x00a7 }
            android.os.Handler r2 = r9.d     // Catch:{ all -> 0x00a7 }
            android.os.Message r0 = r2.obtainMessage(r6, r0)     // Catch:{ all -> 0x00a7 }
            r1.sendMessage(r0)     // Catch:{ all -> 0x00a7 }
            android.os.Handler r0 = r9.d     // Catch:{ all -> 0x00a7 }
            r0.sendEmptyMessage(r5)     // Catch:{ all -> 0x00a7 }
            monitor-exit(r9)     // Catch:{ all -> 0x00a7 }
            goto L_0x00aa
        L_0x00a7:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00a7 }
            throw r0
        L_0x00aa:
            return r4
        L_0x00ab:
            boolean r1 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0115 }
            if (r1 != 0) goto L_0x00e9
            r4.add(r2)     // Catch:{ all -> 0x0115 }
            boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0115 }
            if (r1 != 0) goto L_0x00c3
            boolean r1 = android.text.TextUtils.equals(r3, r2)     // Catch:{ all -> 0x0115 }
            if (r1 != 0) goto L_0x00c5
            r4.add(r3)     // Catch:{ all -> 0x0115 }
        L_0x00c3:
            r6 = 256(0x100, float:3.59E-43)
        L_0x00c5:
            r0 = r6 | 1
            if (r0 <= 0) goto L_0x00e8
            boolean r1 = android.text.TextUtils.isEmpty(r2)
            if (r1 != 0) goto L_0x00e8
            monitor-enter(r9)
            r9.b()     // Catch:{ all -> 0x00e5 }
            android.os.Handler r1 = r9.d     // Catch:{ all -> 0x00e5 }
            android.os.Handler r3 = r9.d     // Catch:{ all -> 0x00e5 }
            android.os.Message r0 = r3.obtainMessage(r0, r2)     // Catch:{ all -> 0x00e5 }
            r1.sendMessage(r0)     // Catch:{ all -> 0x00e5 }
            android.os.Handler r0 = r9.d     // Catch:{ all -> 0x00e5 }
            r0.sendEmptyMessage(r5)     // Catch:{ all -> 0x00e5 }
            monitor-exit(r9)     // Catch:{ all -> 0x00e5 }
            goto L_0x00e8
        L_0x00e5:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00e5 }
            throw r0
        L_0x00e8:
            return r4
        L_0x00e9:
            boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0115 }
            if (r1 != 0) goto L_0x0114
            r4.add(r3)     // Catch:{ all -> 0x0115 }
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x0113
            monitor-enter(r9)
            r9.b()     // Catch:{ all -> 0x0110 }
            android.os.Handler r0 = r9.d     // Catch:{ all -> 0x0110 }
            android.os.Handler r1 = r9.d     // Catch:{ all -> 0x0110 }
            r2 = 17
            android.os.Message r1 = r1.obtainMessage(r2, r3)     // Catch:{ all -> 0x0110 }
            r0.sendMessage(r1)     // Catch:{ all -> 0x0110 }
            android.os.Handler r0 = r9.d     // Catch:{ all -> 0x0110 }
            r0.sendEmptyMessage(r5)     // Catch:{ all -> 0x0110 }
            monitor-exit(r9)     // Catch:{ all -> 0x0110 }
            goto L_0x0113
        L_0x0110:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0110 }
            throw r0
        L_0x0113:
            return r4
        L_0x0114:
            return r7
        L_0x0115:
            r1 = move-exception
            if (r6 <= 0) goto L_0x0137
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0137
            monitor-enter(r9)
            r9.b()     // Catch:{ all -> 0x0134 }
            android.os.Handler r2 = r9.d     // Catch:{ all -> 0x0134 }
            android.os.Handler r3 = r9.d     // Catch:{ all -> 0x0134 }
            android.os.Message r0 = r3.obtainMessage(r6, r0)     // Catch:{ all -> 0x0134 }
            r2.sendMessage(r0)     // Catch:{ all -> 0x0134 }
            android.os.Handler r0 = r9.d     // Catch:{ all -> 0x0134 }
            r0.sendEmptyMessage(r5)     // Catch:{ all -> 0x0134 }
            monitor-exit(r9)     // Catch:{ all -> 0x0134 }
            goto L_0x0137
        L_0x0134:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0134 }
            throw r0
        L_0x0137:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.chx.c():java.util.List");
    }

    public final void a(String str, int i) {
        String b2 = cib.b(str);
        if (!TextUtils.isEmpty(b2)) {
            if ((i & 1) > 0) {
                try {
                    if (VERSION.SDK_INT >= 23) {
                        System.putString(this.c.getContentResolver(), this.b, b2);
                    } else {
                        System.putString(this.c.getContentResolver(), this.b, b2);
                    }
                } catch (Exception unused) {
                }
            }
            if ((i & 16) > 0) {
                chy.a(this.c, this.b, b2);
            }
            if ((i & 256) > 0) {
                Editor edit = this.c.getSharedPreferences("SharedPreferenceAdiu", 0).edit();
                edit.putString(this.b, b2);
                if (VERSION.SDK_INT >= 9) {
                    edit.apply();
                    return;
                }
                edit.commit();
            }
        }
    }

    static /* synthetic */ void a(chx chx) {
        synchronized (chx) {
            if (chx.e != null) {
                if (VERSION.SDK_INT >= 18) {
                    chx.e.quitSafely();
                } else {
                    chx.e.quit();
                }
                chx.d = null;
                chx.e = null;
            }
        }
    }
}
