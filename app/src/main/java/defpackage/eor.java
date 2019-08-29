package defpackage;

import android.os.Handler;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: eor reason: default package */
/* compiled from: GPSStatusChecker */
public final class eor extends Thread {
    public volatile AtomicInteger a = new AtomicInteger(0);
    public int b = 0;
    public Object c = new Object();
    private LocationInstrument d;
    private boolean e = false;
    private Handler f;

    public eor(LocationInstrument locationInstrument, Handler handler) {
        this.d = locationInstrument;
        this.f = handler;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0064 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            super.run()
            r0 = 1
            r4.e = r0
        L_0x0006:
            boolean r0 = r4.e
            if (r0 == 0) goto L_0x0068
            int r0 = r4.b
            switch(r0) {
                case 0: goto L_0x0059;
                case 1: goto L_0x0010;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0006
        L_0x0010:
            com.autonavi.sdk.location.LocationInstrument r0 = r4.d     // Catch:{ Exception -> 0x0006 }
            com.amap.location.sdk.fusion.LocationManagerProxy r0 = r0.getProxy()     // Catch:{ Exception -> 0x0006 }
            if (r0 == 0) goto L_0x004a
            boolean r0 = r4.e     // Catch:{ Exception -> 0x0006 }
            if (r0 == 0) goto L_0x004a
            com.autonavi.sdk.location.LocationInstrument r0 = r4.d     // Catch:{ Exception -> 0x0006 }
            boolean r0 = r0.isLocating()     // Catch:{ Exception -> 0x0006 }
            if (r0 == 0) goto L_0x004a
            java.util.concurrent.atomic.AtomicInteger r0 = r4.a     // Catch:{ Exception -> 0x0006 }
            int r0 = r0.incrementAndGet()     // Catch:{ Exception -> 0x0006 }
            r1 = 10
            if (r0 <= r1) goto L_0x004a
            android.os.Handler r0 = r4.f     // Catch:{ Exception -> 0x0006 }
            r1 = 241(0xf1, float:3.38E-43)
            android.os.Message r0 = r0.obtainMessage(r1)     // Catch:{ Exception -> 0x0006 }
            r0.sendToTarget()     // Catch:{ Exception -> 0x0006 }
            ku r0 = defpackage.ku.a()     // Catch:{ Exception -> 0x0006 }
            java.lang.String r1 = "GpsStatusChecker"
            java.lang.String r2 = "10s timeout"
            r0.a(r1, r2)     // Catch:{ Exception -> 0x0006 }
            java.util.concurrent.atomic.AtomicInteger r0 = r4.a     // Catch:{ Exception -> 0x0006 }
            r1 = 0
            r0.set(r1)     // Catch:{ Exception -> 0x0006 }
        L_0x004a:
            java.lang.Object r0 = r4.c     // Catch:{ Exception -> 0x0006 }
            monitor-enter(r0)     // Catch:{ Exception -> 0x0006 }
            java.lang.Object r1 = r4.c     // Catch:{ all -> 0x0056 }
            r2 = 1000(0x3e8, double:4.94E-321)
            r1.wait(r2)     // Catch:{ all -> 0x0056 }
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            goto L_0x0006
        L_0x0056:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            throw r1     // Catch:{ Exception -> 0x0006 }
        L_0x0059:
            java.lang.Object r0 = r4.c
            monitor-enter(r0)
            java.lang.Object r1 = r4.c     // Catch:{ InterruptedException -> 0x0064 }
            r1.wait()     // Catch:{ InterruptedException -> 0x0064 }
            goto L_0x0064
        L_0x0062:
            r1 = move-exception
            goto L_0x0066
        L_0x0064:
            monitor-exit(r0)     // Catch:{ all -> 0x0062 }
            goto L_0x0006
        L_0x0066:
            monitor-exit(r0)     // Catch:{ all -> 0x0062 }
            throw r1
        L_0x0068:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eor.run():void");
    }

    public final void a() {
        this.a.set(0);
    }

    public final void b() {
        String str;
        this.e = false;
        synchronized (this.c) {
            this.c.notify();
        }
        ku a2 = ku.a();
        if (!bno.a) {
            str = "";
        } else {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("MSG=");
            stringBuffer.append("GpsStatusChecker");
            stringBuffer.append(" | ");
            stringBuffer.append("cancel");
            stringBuffer.append("\n");
            stringBuffer.append("THREAD=");
            stringBuffer.append(Thread.currentThread().getName());
            for (int i = 4; i < stackTrace.length; i++) {
                stringBuffer.append("\n\t\t\t");
                stringBuffer.append(stackTrace[i]);
            }
            str = stringBuffer.toString();
        }
        a2.a((String) "GpsStatusChecker", str);
    }
}
