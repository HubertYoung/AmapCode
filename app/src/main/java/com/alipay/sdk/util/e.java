package com.alipay.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.app.statistic.c;

public class e {
    public static final String b = "failed";
    public Activity a;
    /* access modifiers changed from: private */
    public IAlixPay c;
    /* access modifiers changed from: private */
    public final Object d = IAlixPay.class;
    private boolean e;
    private a f;
    private ServiceConnection g = new f(this);
    private IRemoteServiceCallback h = new g(this);

    public interface a {
        void a();
    }

    public e(Activity activity, a aVar) {
        this.a = activity;
        this.f = aVar;
    }

    public final String a(String str) {
        try {
            com.alipay.sdk.util.l.a a2 = l.a((Context) this.a);
            if (a2.a()) {
                return b;
            }
            if (a2 != null) {
                if (a2.b > 78) {
                    String a3 = l.a();
                    Intent intent = new Intent();
                    intent.setClassName(a3, "com.alipay.android.app.TransProcessPayActivity");
                    this.a.startActivity(intent);
                    Thread.sleep(200);
                }
            }
            return b(str);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a((String) c.b, (String) c.D, th);
        }
    }

    private void a(com.alipay.sdk.util.l.a aVar) throws InterruptedException {
        if (aVar != null && aVar.b > 78) {
            String a2 = l.a();
            Intent intent = new Intent();
            intent.setClassName(a2, "com.alipay.android.app.TransProcessPayActivity");
            this.a.startActivity(intent);
            Thread.sleep(200);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(13:34|35|(1:37)|38|(1:40)|41|42|43|44|45|46|(1:49)|66) */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0104, code lost:
        if (r7.a != null) goto L_0x0106;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0106, code lost:
        r7.a.setRequestedOrientation(0);
        r7.e = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x013c, code lost:
        if (r7.a != null) goto L_0x0106;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x009c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x00eb */
    /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x0123 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:69:0x0147 */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0102  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String b(java.lang.String r8) {
        /*
            r7 = this;
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            java.lang.String r1 = com.alipay.sdk.util.l.a()
            r0.setPackage(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            java.lang.String r1 = ".IAlixPay"
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.setAction(r1)
            android.app.Activity r1 = r7.a
            java.lang.String r1 = com.alipay.sdk.util.l.h(r1)
            android.app.Activity r2 = r7.a     // Catch:{ Throwable -> 0x016d }
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ Throwable -> 0x016d }
            android.content.ServiceConnection r3 = r7.g     // Catch:{ Throwable -> 0x016d }
            r4 = 1
            boolean r0 = r2.bindService(r0, r3, r4)     // Catch:{ Throwable -> 0x016d }
            if (r0 != 0) goto L_0x003d
            java.lang.Throwable r8 = new java.lang.Throwable     // Catch:{ Throwable -> 0x016d }
            java.lang.String r0 = "bindService fail"
            r8.<init>(r0)     // Catch:{ Throwable -> 0x016d }
            throw r8     // Catch:{ Throwable -> 0x016d }
        L_0x003d:
            java.lang.Object r0 = r7.d
            monitor-enter(r0)
            com.alipay.android.app.IAlixPay r2 = r7.c     // Catch:{ all -> 0x016a }
            if (r2 != 0) goto L_0x005b
            java.lang.Object r2 = r7.d     // Catch:{ InterruptedException -> 0x0053 }
            com.alipay.sdk.data.a r3 = com.alipay.sdk.data.a.b()     // Catch:{ InterruptedException -> 0x0053 }
            int r3 = r3.a()     // Catch:{ InterruptedException -> 0x0053 }
            long r5 = (long) r3     // Catch:{ InterruptedException -> 0x0053 }
            r2.wait(r5)     // Catch:{ InterruptedException -> 0x0053 }
            goto L_0x005b
        L_0x0053:
            r2 = move-exception
            java.lang.String r3 = "biz"
            java.lang.String r5 = "BindWaitTimeoutEx"
            com.alipay.sdk.app.statistic.a.a(r3, r5, r2)     // Catch:{ all -> 0x016a }
        L_0x005b:
            monitor-exit(r0)     // Catch:{ all -> 0x016a }
            r0 = 0
            r2 = 0
            com.alipay.android.app.IAlixPay r3 = r7.c     // Catch:{ Throwable -> 0x0110 }
            if (r3 != 0) goto L_0x00bf
            android.app.Activity r8 = r7.a     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r8 = com.alipay.sdk.util.l.h(r8)     // Catch:{ Throwable -> 0x0110 }
            android.app.Activity r3 = r7.a     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r3 = com.alipay.sdk.util.l.i(r3)     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r4 = "biz"
            java.lang.String r5 = "ClientBindFailed"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0110 }
            r6.<init>()     // Catch:{ Throwable -> 0x0110 }
            r6.append(r1)     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r1 = "|"
            r6.append(r1)     // Catch:{ Throwable -> 0x0110 }
            r6.append(r8)     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r8 = "|"
            r6.append(r8)     // Catch:{ Throwable -> 0x0110 }
            r6.append(r3)     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r8 = r6.toString()     // Catch:{ Throwable -> 0x0110 }
            com.alipay.sdk.app.statistic.a.a(r4, r5, r8)     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r8 = "failed"
            com.alipay.android.app.IAlixPay r1 = r7.c     // Catch:{ Throwable -> 0x009c }
            com.alipay.android.app.IRemoteServiceCallback r3 = r7.h     // Catch:{ Throwable -> 0x009c }
            r1.unregisterCallback(r3)     // Catch:{ Throwable -> 0x009c }
        L_0x009c:
            android.app.Activity r1 = r7.a     // Catch:{ Throwable -> 0x00a7 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x00a7 }
            android.content.ServiceConnection r3 = r7.g     // Catch:{ Throwable -> 0x00a7 }
            r1.unbindService(r3)     // Catch:{ Throwable -> 0x00a7 }
        L_0x00a7:
            r7.f = r2
            r7.h = r2
            r7.g = r2
            r7.c = r2
            boolean r1 = r7.e
            if (r1 == 0) goto L_0x00be
            android.app.Activity r1 = r7.a
            if (r1 == 0) goto L_0x00be
            android.app.Activity r1 = r7.a
            r1.setRequestedOrientation(r0)
            r7.e = r0
        L_0x00be:
            return r8
        L_0x00bf:
            com.alipay.sdk.util.e$a r1 = r7.f     // Catch:{ Throwable -> 0x0110 }
            if (r1 == 0) goto L_0x00c8
            com.alipay.sdk.util.e$a r1 = r7.f     // Catch:{ Throwable -> 0x0110 }
            r1.a()     // Catch:{ Throwable -> 0x0110 }
        L_0x00c8:
            android.app.Activity r1 = r7.a     // Catch:{ Throwable -> 0x0110 }
            int r1 = r1.getRequestedOrientation()     // Catch:{ Throwable -> 0x0110 }
            if (r1 != 0) goto L_0x00d7
            android.app.Activity r1 = r7.a     // Catch:{ Throwable -> 0x0110 }
            r1.setRequestedOrientation(r4)     // Catch:{ Throwable -> 0x0110 }
            r7.e = r4     // Catch:{ Throwable -> 0x0110 }
        L_0x00d7:
            com.alipay.android.app.IAlixPay r1 = r7.c     // Catch:{ Throwable -> 0x0110 }
            com.alipay.android.app.IRemoteServiceCallback r3 = r7.h     // Catch:{ Throwable -> 0x0110 }
            r1.registerCallback(r3)     // Catch:{ Throwable -> 0x0110 }
            com.alipay.android.app.IAlixPay r1 = r7.c     // Catch:{ Throwable -> 0x0110 }
            java.lang.String r8 = r1.Pay(r8)     // Catch:{ Throwable -> 0x0110 }
            com.alipay.android.app.IAlixPay r1 = r7.c     // Catch:{ Throwable -> 0x00eb }
            com.alipay.android.app.IRemoteServiceCallback r3 = r7.h     // Catch:{ Throwable -> 0x00eb }
            r1.unregisterCallback(r3)     // Catch:{ Throwable -> 0x00eb }
        L_0x00eb:
            android.app.Activity r1 = r7.a     // Catch:{ Throwable -> 0x00f6 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x00f6 }
            android.content.ServiceConnection r3 = r7.g     // Catch:{ Throwable -> 0x00f6 }
            r1.unbindService(r3)     // Catch:{ Throwable -> 0x00f6 }
        L_0x00f6:
            r7.f = r2
            r7.h = r2
            r7.g = r2
            r7.c = r2
            boolean r1 = r7.e
            if (r1 == 0) goto L_0x013f
            android.app.Activity r1 = r7.a
            if (r1 == 0) goto L_0x013f
        L_0x0106:
            android.app.Activity r1 = r7.a
            r1.setRequestedOrientation(r0)
            r7.e = r0
            goto L_0x013f
        L_0x010e:
            r8 = move-exception
            goto L_0x0140
        L_0x0110:
            r8 = move-exception
            java.lang.String r1 = "biz"
            java.lang.String r3 = "ClientBindException"
            com.alipay.sdk.app.statistic.a.a(r1, r3, r8)     // Catch:{ all -> 0x010e }
            java.lang.String r8 = com.alipay.sdk.app.i.a()     // Catch:{ all -> 0x010e }
            com.alipay.android.app.IAlixPay r1 = r7.c     // Catch:{ Throwable -> 0x0123 }
            com.alipay.android.app.IRemoteServiceCallback r3 = r7.h     // Catch:{ Throwable -> 0x0123 }
            r1.unregisterCallback(r3)     // Catch:{ Throwable -> 0x0123 }
        L_0x0123:
            android.app.Activity r1 = r7.a     // Catch:{ Throwable -> 0x012e }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x012e }
            android.content.ServiceConnection r3 = r7.g     // Catch:{ Throwable -> 0x012e }
            r1.unbindService(r3)     // Catch:{ Throwable -> 0x012e }
        L_0x012e:
            r7.f = r2
            r7.h = r2
            r7.g = r2
            r7.c = r2
            boolean r1 = r7.e
            if (r1 == 0) goto L_0x013f
            android.app.Activity r1 = r7.a
            if (r1 == 0) goto L_0x013f
            goto L_0x0106
        L_0x013f:
            return r8
        L_0x0140:
            com.alipay.android.app.IAlixPay r1 = r7.c     // Catch:{ Throwable -> 0x0147 }
            com.alipay.android.app.IRemoteServiceCallback r3 = r7.h     // Catch:{ Throwable -> 0x0147 }
            r1.unregisterCallback(r3)     // Catch:{ Throwable -> 0x0147 }
        L_0x0147:
            android.app.Activity r1 = r7.a     // Catch:{ Throwable -> 0x0152 }
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Throwable -> 0x0152 }
            android.content.ServiceConnection r3 = r7.g     // Catch:{ Throwable -> 0x0152 }
            r1.unbindService(r3)     // Catch:{ Throwable -> 0x0152 }
        L_0x0152:
            r7.f = r2
            r7.h = r2
            r7.g = r2
            r7.c = r2
            boolean r1 = r7.e
            if (r1 == 0) goto L_0x0169
            android.app.Activity r1 = r7.a
            if (r1 == 0) goto L_0x0169
            android.app.Activity r1 = r7.a
            r1.setRequestedOrientation(r0)
            r7.e = r0
        L_0x0169:
            throw r8
        L_0x016a:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x016a }
            throw r8
        L_0x016d:
            r8 = move-exception
            java.lang.String r0 = "biz"
            java.lang.String r1 = "ClientBindServiceFailed"
            com.alipay.sdk.app.statistic.a.a(r0, r1, r8)
            java.lang.String r8 = "failed"
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.util.e.b(java.lang.String):java.lang.String");
    }

    private void a() {
        this.a = null;
    }
}
