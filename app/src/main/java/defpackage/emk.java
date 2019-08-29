package defpackage;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug.MemoryInfo;
import android.os.Process;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

/* renamed from: emk reason: default package */
/* compiled from: MemSamplerAction */
public final class emk extends emf {
    private emq a;
    private Context b;

    public emk(emq emq, Context context) {
        this.b = context;
        this.a = emq;
    }

    public final void a() {
        if (this.a != null) {
            long[] jArr = new long[3];
            int myPid = Process.myPid();
            if (myPid >= 0) {
                MemoryInfo memoryInfo = ((ActivityManager) this.b.getSystemService(WidgetType.ACTIVITY)).getProcessMemoryInfo(new int[]{myPid})[0];
                jArr[0] = (long) memoryInfo.nativePss;
                jArr[1] = (long) memoryInfo.dalvikPss;
                jArr[2] = (long) memoryInfo.getTotalPss();
            } else {
                jArr[0] = 0;
                jArr[1] = 0;
                jArr[2] = 0;
            }
            emq emq = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append(jArr[0]);
            sb.append("KB");
            emq.a("nativePss", sb.toString(), true);
            emq emq2 = this.a;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(jArr[1]);
            sb2.append("KB");
            emq2.a("dalvikPss", sb2.toString(), true);
            emq emq3 = this.a;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(jArr[2]);
            sb3.append("KB");
            emq3.a("TotalPss", sb3.toString(), true);
        }
    }
}
