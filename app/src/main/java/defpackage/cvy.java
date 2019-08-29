package defpackage;

import android.app.Application;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* renamed from: cvy reason: default package */
/* compiled from: SmoothPlugin */
public class cvy extends Plugin implements FrameCallback {
    private cuu c;
    private long d;
    private long e;
    private boolean f = false;
    private String g;
    private String h;

    public final void a(Application application, cuu cuu, JSONObject jSONObject) {
        this.c = cuu;
        this.c.a(4, this.a);
    }

    public void doFrame(long j) {
        this.e++;
        if (this.d == 0) {
            this.d = TimeUnit.NANOSECONDS.toMillis(j);
        }
        if (this.f) {
            cvx cvx = new cvx();
            cvx.a = this.g;
            cvx.b = this.h;
            cvx.d = System.currentTimeMillis();
            cvx.c = (int) (((double) (this.e * 1000)) / ((double) (TimeUnit.NANOSECONDS.toMillis(j) - this.d)));
            this.c.b().send(cvx);
            return;
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    public final void a(int i, cur cur) {
        if (i == 4) {
            cus cus = (cus) cur;
            if (cus.c != null && !cus.c.toString().contains("com.amap.bundle.planhome.page.PlanHomePage")) {
                switch (cus.a) {
                    case 3:
                        this.f = false;
                        this.e = 0;
                        this.d = 0;
                        Choreographer.getInstance().postFrameCallback(this);
                        return;
                    case 4:
                        this.g = cus.toString();
                        this.h = Integer.toHexString(cus.getClass().hashCode());
                        this.f = true;
                        break;
                }
            }
        }
    }
}
