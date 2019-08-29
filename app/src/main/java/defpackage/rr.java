package defpackage;

import android.hardware.SensorManager;
import android.os.Build.VERSION;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;

@BundleInterface(mt.class)
/* renamed from: rr reason: default package */
/* compiled from: PressureServiceImpl */
public class rr implements mt {
    private rq a;

    public final boolean a() {
        return rq.b();
    }

    public final boolean b() {
        if (this.a == null) {
            this.a = new rq();
        }
        return this.a.c();
    }

    public final void c() {
        if (this.a == null) {
            this.a = new rq();
        }
        rq rqVar = this.a;
        if (rqVar.a) {
            try {
                ((SensorManager) AMapAppGlobal.getApplication().getSystemService("sensor")).unregisterListener(rqVar);
                rqVar.a = false;
                if (rqVar.b != null) {
                    if (VERSION.SDK_INT >= 18) {
                        rqVar.b.quitSafely();
                        return;
                    }
                    rqVar.b.quit();
                }
            } catch (Exception unused) {
            }
        }
    }
}
