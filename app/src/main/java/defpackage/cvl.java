package defpackage;

import android.app.Application;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import org.json.JSONObject;

/* renamed from: cvl reason: default package */
/* compiled from: CpuPlugin */
public class cvl extends Plugin {
    Application c;
    cuu d;
    int e = 3000;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;
    private Ajx3Page i;
    /* access modifiers changed from: private */
    public Runnable j = new Runnable() {
        public final void run() {
            cvl.a(cvl.this);
            b.b.postDelayed(cvl.this.j, (long) cvl.this.e);
        }
    };

    public final void a(Application application, cuu cuu, JSONObject jSONObject) {
        super.a(application, cuu, jSONObject);
        this.c = application;
        this.d = cuu;
        if (jSONObject != null) {
            int optInt = jSONObject.optInt("timeInterval");
            if (optInt >= 1000) {
                this.e = optInt;
            }
        }
        this.d.a(2, this.a);
        this.d.a(4, this.a);
        b.b.post(this.j);
    }

    public final void a(int i2, cur cur) {
        super.a(i2, cur);
        if (!this.f) {
            if (i2 == 2) {
                cuq cuq = (cuq) cur;
                if (cuq.a == 1) {
                    b.b.removeCallbacks(this.j);
                    return;
                }
                if (cuq.a == 2) {
                    b.b.post(this.j);
                }
                return;
            }
            if (i2 == 4) {
                cus cus = (cus) cur;
                if (cus != null && cus.a == 2) {
                    if (cus.c instanceof Ajx3Page) {
                        this.i = (Ajx3Page) cus.c;
                        return;
                    }
                    this.i = null;
                }
            }
        }
    }

    static /* synthetic */ void a(cvl cvl) {
        if (!cvl.f && !cvl.g) {
            cvl.h = true;
            cvk cvk = new cvk(cwm.a(), cwn.a(), cvl.i == null ? "" : cvl.i.getBundleInfo());
            if (cvk.b != null) {
                cvl.d.b().send(cvk);
            }
            cvl.h = false;
        }
    }
}
