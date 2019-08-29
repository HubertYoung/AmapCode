package defpackage;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IInterface;
import android.text.TextUtils;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: fcx reason: default package */
/* compiled from: AsyncServiceBinder */
public abstract class fcx<T extends IInterface> {
    protected volatile T a = null;
    Class<? extends IInterface> b;
    Class<? extends Service> c;
    String d;
    final byte[] e = new byte[0];
    volatile boolean f = false;
    volatile boolean g = false;
    private ServiceConnection h = new ServiceConnection() {
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x003d */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onServiceDisconnected(android.content.ComponentName r4) {
            /*
                r3 = this;
                fcx r4 = defpackage.fcx.this
                byte[] r4 = r4.e
                monitor-enter(r4)
                mtopsdk.common.util.TBSdkLog$LogEnable r0 = mtopsdk.common.util.TBSdkLog.LogEnable.WarnEnable     // Catch:{ Exception -> 0x003d }
                boolean r0 = mtopsdk.common.util.TBSdkLog.a(r0)     // Catch:{ Exception -> 0x003d }
                if (r0 == 0) goto L_0x003d
                fcx r0 = defpackage.fcx.this     // Catch:{ Exception -> 0x003d }
                java.lang.String r0 = r0.d     // Catch:{ Exception -> 0x003d }
                boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x003d }
                if (r0 == 0) goto L_0x0023
                fcx r0 = defpackage.fcx.this     // Catch:{ Exception -> 0x003d }
                fcx r1 = defpackage.fcx.this     // Catch:{ Exception -> 0x003d }
                java.lang.Class<? extends android.os.IInterface> r1 = r1.b     // Catch:{ Exception -> 0x003d }
                java.lang.String r1 = r1.getSimpleName()     // Catch:{ Exception -> 0x003d }
                r0.d = r1     // Catch:{ Exception -> 0x003d }
            L_0x0023:
                java.lang.String r0 = "mtopsdk.AsyncServiceBinder"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003d }
                java.lang.String r2 = "[onServiceDisconnected] Service disconnected called,interfaceName="
                r1.<init>(r2)     // Catch:{ Exception -> 0x003d }
                fcx r2 = defpackage.fcx.this     // Catch:{ Exception -> 0x003d }
                java.lang.String r2 = r2.d     // Catch:{ Exception -> 0x003d }
                r1.append(r2)     // Catch:{ Exception -> 0x003d }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x003d }
                mtopsdk.common.util.TBSdkLog.c(r0, r1)     // Catch:{ Exception -> 0x003d }
                goto L_0x003d
            L_0x003b:
                r0 = move-exception
                goto L_0x0049
            L_0x003d:
                fcx r0 = defpackage.fcx.this     // Catch:{ all -> 0x003b }
                r1 = 0
                r0.a = r1     // Catch:{ all -> 0x003b }
                fcx r0 = defpackage.fcx.this     // Catch:{ all -> 0x003b }
                r1 = 0
                r0.g = r1     // Catch:{ all -> 0x003b }
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
                return
            L_0x0049:
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.fcx.AnonymousClass1.onServiceDisconnected(android.content.ComponentName):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            r9.a.f = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x007f, code lost:
            if (mtopsdk.common.util.TBSdkLog.a(mtopsdk.common.util.TBSdkLog.LogEnable.WarnEnable) != false) goto L_0x0081;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0081, code lost:
            r0 = new java.lang.StringBuilder("[onServiceConnected] Service bind failed. mBindFailed=");
            r0.append(r9.a.f);
            r0.append(",interfaceName=");
            r0.append(r9.a.d);
            mtopsdk.common.util.TBSdkLog.c("mtopsdk.AsyncServiceBinder", r0.toString());
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0075 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onServiceConnected(android.content.ComponentName r10, android.os.IBinder r11) {
            /*
                r9 = this;
                fcx r10 = defpackage.fcx.this
                byte[] r10 = r10.e
                monitor-enter(r10)
                r0 = 1
                r1 = 0
                fcx r2 = defpackage.fcx.this     // Catch:{ Exception -> 0x0075 }
                java.lang.String r2 = r2.d     // Catch:{ Exception -> 0x0075 }
                boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0075 }
                if (r2 == 0) goto L_0x001d
                fcx r2 = defpackage.fcx.this     // Catch:{ Exception -> 0x0075 }
                fcx r3 = defpackage.fcx.this     // Catch:{ Exception -> 0x0075 }
                java.lang.Class<? extends android.os.IInterface> r3 = r3.b     // Catch:{ Exception -> 0x0075 }
                java.lang.String r3 = r3.getSimpleName()     // Catch:{ Exception -> 0x0075 }
                r2.d = r3     // Catch:{ Exception -> 0x0075 }
            L_0x001d:
                mtopsdk.common.util.TBSdkLog$LogEnable r2 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable     // Catch:{ Exception -> 0x0075 }
                boolean r2 = mtopsdk.common.util.TBSdkLog.a(r2)     // Catch:{ Exception -> 0x0075 }
                if (r2 == 0) goto L_0x003c
                java.lang.String r2 = "mtopsdk.AsyncServiceBinder"
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0075 }
                java.lang.String r4 = "[onServiceConnected] Service connected called. interfaceName ="
                r3.<init>(r4)     // Catch:{ Exception -> 0x0075 }
                fcx r4 = defpackage.fcx.this     // Catch:{ Exception -> 0x0075 }
                java.lang.String r4 = r4.d     // Catch:{ Exception -> 0x0075 }
                r3.append(r4)     // Catch:{ Exception -> 0x0075 }
                java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0075 }
                mtopsdk.common.util.TBSdkLog.b(r2, r3)     // Catch:{ Exception -> 0x0075 }
            L_0x003c:
                fcx r2 = defpackage.fcx.this     // Catch:{ Exception -> 0x0075 }
                java.lang.Class<? extends android.os.IInterface> r2 = r2.b     // Catch:{ Exception -> 0x0075 }
                java.lang.Class[] r2 = r2.getDeclaredClasses()     // Catch:{ Exception -> 0x0075 }
                int r3 = r2.length     // Catch:{ Exception -> 0x0075 }
                r4 = 0
            L_0x0046:
                if (r4 >= r3) goto L_0x00a4
                r5 = r2[r4]     // Catch:{ Exception -> 0x0075 }
                java.lang.String r6 = r5.getSimpleName()     // Catch:{ Exception -> 0x0075 }
                java.lang.String r7 = "Stub"
                boolean r6 = r6.equals(r7)     // Catch:{ Exception -> 0x0075 }
                if (r6 == 0) goto L_0x0070
                java.lang.String r6 = "asInterface"
                java.lang.Class[] r7 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0075 }
                java.lang.Class<android.os.IBinder> r8 = android.os.IBinder.class
                r7[r1] = r8     // Catch:{ Exception -> 0x0075 }
                java.lang.reflect.Method r6 = r5.getDeclaredMethod(r6, r7)     // Catch:{ Exception -> 0x0075 }
                fcx r7 = defpackage.fcx.this     // Catch:{ Exception -> 0x0075 }
                java.lang.Object[] r8 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0075 }
                r8[r1] = r11     // Catch:{ Exception -> 0x0075 }
                java.lang.Object r5 = r6.invoke(r5, r8)     // Catch:{ Exception -> 0x0075 }
                android.os.IInterface r5 = (android.os.IInterface) r5     // Catch:{ Exception -> 0x0075 }
                r7.a = r5     // Catch:{ Exception -> 0x0075 }
            L_0x0070:
                int r4 = r4 + 1
                goto L_0x0046
            L_0x0073:
                r11 = move-exception
                goto L_0x00b9
            L_0x0075:
                fcx r11 = defpackage.fcx.this     // Catch:{ all -> 0x0073 }
                r11.f = r0     // Catch:{ all -> 0x0073 }
                mtopsdk.common.util.TBSdkLog$LogEnable r11 = mtopsdk.common.util.TBSdkLog.LogEnable.WarnEnable     // Catch:{ all -> 0x0073 }
                boolean r11 = mtopsdk.common.util.TBSdkLog.a(r11)     // Catch:{ all -> 0x0073 }
                if (r11 == 0) goto L_0x00a4
                java.lang.String r11 = "mtopsdk.AsyncServiceBinder"
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = "[onServiceConnected] Service bind failed. mBindFailed="
                r0.<init>(r2)     // Catch:{ all -> 0x0073 }
                fcx r2 = defpackage.fcx.this     // Catch:{ all -> 0x0073 }
                boolean r2 = r2.f     // Catch:{ all -> 0x0073 }
                r0.append(r2)     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = ",interfaceName="
                r0.append(r2)     // Catch:{ all -> 0x0073 }
                fcx r2 = defpackage.fcx.this     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = r2.d     // Catch:{ all -> 0x0073 }
                r0.append(r2)     // Catch:{ all -> 0x0073 }
                java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0073 }
                mtopsdk.common.util.TBSdkLog.c(r11, r0)     // Catch:{ all -> 0x0073 }
            L_0x00a4:
                fcx r11 = defpackage.fcx.this     // Catch:{ all -> 0x0073 }
                T r11 = r11.a     // Catch:{ all -> 0x0073 }
                if (r11 == 0) goto L_0x00b3
                fcx r11 = defpackage.fcx.this     // Catch:{ all -> 0x0073 }
                r11.f = r1     // Catch:{ all -> 0x0073 }
                fcx r11 = defpackage.fcx.this     // Catch:{ all -> 0x0073 }
                r11.a()     // Catch:{ all -> 0x0073 }
            L_0x00b3:
                fcx r11 = defpackage.fcx.this     // Catch:{ all -> 0x0073 }
                r11.g = r1     // Catch:{ all -> 0x0073 }
                monitor-exit(r10)     // Catch:{ all -> 0x0073 }
                return
            L_0x00b9:
                monitor-exit(r10)     // Catch:{ all -> 0x0073 }
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.fcx.AnonymousClass1.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
        }
    };

    /* access modifiers changed from: protected */
    public abstract void a();

    public fcx(Class<? extends IInterface> cls, Class<? extends Service> cls2) {
        this.b = cls;
        this.c = cls2;
    }

    @TargetApi(4)
    public final void a(Context context) {
        if (this.a == null && context != null && !this.f && !this.g) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder("[asyncBind] mContext=");
                sb.append(context);
                sb.append(",mBindFailed= ");
                sb.append(this.f);
                sb.append(",mBinding=");
                sb.append(this.g);
                TBSdkLog.b("mtopsdk.AsyncServiceBinder", sb.toString());
            }
            this.g = true;
            try {
                if (TextUtils.isEmpty(this.d)) {
                    this.d = this.b.getSimpleName();
                }
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb2 = new StringBuilder("[asyncBind]try to bind service for ");
                    sb2.append(this.d);
                    TBSdkLog.b("mtopsdk.AsyncServiceBinder", sb2.toString());
                }
                Intent intent = new Intent(context.getApplicationContext(), this.c);
                intent.setAction(this.b.getName());
                intent.setPackage(context.getPackageName());
                intent.addCategory("android.intent.category.DEFAULT");
                boolean bindService = context.bindService(intent, this.h, 1);
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb3 = new StringBuilder("[asyncBind]use intent bind service ret=");
                    sb3.append(bindService);
                    sb3.append(" for interfaceName=");
                    sb3.append(this.d);
                    TBSdkLog.b("mtopsdk.AsyncServiceBinder", sb3.toString());
                }
                this.f = !bindService;
            } catch (Throwable th) {
                this.f = true;
                StringBuilder sb4 = new StringBuilder("[asyncBind] use intent bind service failed. mBindFailed=");
                sb4.append(this.f);
                sb4.append(",interfaceName = ");
                sb4.append(this.d);
                TBSdkLog.b((String) "mtopsdk.AsyncServiceBinder", sb4.toString(), th);
            }
            if (this.f) {
                this.g = false;
            }
        }
    }

    public final T b() {
        return this.a;
    }
}
