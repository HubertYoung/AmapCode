package defpackage;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import com.autonavi.widget.ui.BalloonLayout;
import org.json.JSONObject;

/* renamed from: cvr reason: default package */
/* compiled from: NewMainThreadBlockPlugin */
public class cvr extends Plugin implements Runnable {
    private Handler c = null;
    private String d = null;
    private cvq e;
    private String f = "";
    private boolean g = true;
    private cuu h;
    private String i = null;
    private long j = BalloonLayout.DEFAULT_DISPLAY_DURATION;

    public final void a(Application application, cuu cuu, JSONObject jSONObject) {
        super.a(application, cuu, jSONObject);
        this.h = cuu;
        cuu.a(4, this.a);
        cuu.a(2, this.a);
        this.e = new cvq();
        this.d = a();
        try {
            long j2 = (long) jSONObject.getInt("timeInterval");
            if (j2 >= 500) {
                this.j = j2;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.c = b.b;
        this.c.postDelayed(this, this.j);
    }

    public final void a(int i2, cur cur) {
        String str;
        super.a(i2, cur);
        if (i2 == 4) {
            cus cus = (cus) cur;
            if (cus.a == 3) {
                AbstractBasePage abstractBasePage = cus.c;
                if (abstractBasePage == null) {
                    str = "unknown";
                } else {
                    str = abstractBasePage.toString();
                }
                this.f = str;
                if (this.g) {
                    this.c.removeCallbacks(this);
                    this.c.postDelayed(this, this.j);
                    this.g = false;
                }
            }
            return;
        }
        if (i2 == 2) {
            int i3 = ((cuq) cur).a;
            boolean z = true;
            if (i3 != 1) {
                z = false;
            }
            this.g = z;
            if (!this.g) {
                this.c.removeCallbacks(this);
                this.c.postDelayed(this, this.j);
            }
        }
    }

    private String a() {
        Message a = this.e.a();
        if (a == null || SystemClock.uptimeMillis() <= a.getWhen()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a.hashCode());
        sb.append(a.getWhen());
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0099, code lost:
        if (r7.g == false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009b, code lost:
        r7.c.postDelayed(r7, r7.j);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a2, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b2, code lost:
        if (r7.g != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b5, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r7 = this;
            java.lang.String r0 = r7.a()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r0 == 0) goto L_0x0097
            java.lang.String r1 = r7.d     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            boolean r1 = r0.equals(r1)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r1 == 0) goto L_0x0095
            java.lang.String r1 = r7.i     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r0 != 0) goto L_0x0097
            android.os.Looper r0 = android.os.Looper.getMainLooper()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.Thread r0 = r0.getThread()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.StackTraceElement[] r0 = r0.getStackTrace()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r0 == 0) goto L_0x0090
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r1.<init>()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            int r2 = r0.length     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r3 = 0
            r4 = 0
        L_0x002c:
            if (r3 >= r2) goto L_0x004c
            r5 = r0[r3]     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r4 != 0) goto L_0x003d
            java.lang.String r6 = r5.getClassName()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            boolean r6 = defpackage.cvp.a(r6)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r6 != 0) goto L_0x003d
            r4 = 1
        L_0x003d:
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r1.append(r5)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.String r5 = "\n"
            r1.append(r5)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            int r3 = r3 + 1
            goto L_0x002c
        L_0x004c:
            if (r4 == 0) goto L_0x0090
            boolean r0 = r7.g     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r0 == 0) goto L_0x0055
            java.lang.String r0 = "background"
            goto L_0x0057
        L_0x0055:
            java.lang.String r0 = r7.f     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
        L_0x0057:
            cvs r2 = new cvs     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r6.<init>()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r6.append(r0)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.String r0 = "_NEW_BLOCKED"
            r6.append(r0)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.String r0 = r6.toString()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r2.<init>(r3, r5, r0)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            cuu r0 = r7.h     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            cuv r0 = r0.b()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r0.send(r2)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            boolean r0 = defpackage.bno.a     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            if (r0 == 0) goto L_0x0090
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.String r2 = "doSendMainBlockInfo: "
            r0.<init>(r2)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r0.append(r1)     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
        L_0x0090:
            java.lang.String r0 = r7.d     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            r7.i = r0     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
            goto L_0x0097
        L_0x0095:
            r7.d = r0     // Catch:{ Exception -> 0x00b0, all -> 0x00a3 }
        L_0x0097:
            boolean r0 = r7.g
            if (r0 != 0) goto L_0x00b5
        L_0x009b:
            android.os.Handler r0 = r7.c
            long r1 = r7.j
            r0.postDelayed(r7, r1)
            return
        L_0x00a3:
            r0 = move-exception
            boolean r1 = r7.g
            if (r1 != 0) goto L_0x00af
            android.os.Handler r1 = r7.c
            long r2 = r7.j
            r1.postDelayed(r7, r2)
        L_0x00af:
            throw r0
        L_0x00b0:
            boolean r0 = r7.g
            if (r0 != 0) goto L_0x00b5
            goto L_0x009b
        L_0x00b5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cvr.run():void");
    }
}
