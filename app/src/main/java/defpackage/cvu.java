package defpackage;

import android.app.Application;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import org.json.JSONObject;

/* renamed from: cvu reason: default package */
/* compiled from: MemoryPlugin */
public class cvu extends Plugin {
    private Application c;
    private cuu d;
    /* access modifiers changed from: private */
    public int e = 3000;
    /* access modifiers changed from: private */
    public boolean f = false;
    private boolean g = false;
    private boolean h = false;
    private Ajx3Page i;
    /* access modifiers changed from: private */
    public Runnable j = new Runnable() {
        public final void run() {
            if (!cvu.this.f) {
                cvu.b(cvu.this);
                b.b.postDelayed(cvu.this.j, (long) cvu.this.e);
            }
        }
    };

    public final void a(Application application, cuu cuu, JSONObject jSONObject) {
        super.a(application, cuu, jSONObject);
        this.c = application;
        this.d = cuu;
        if (jSONObject != null) {
            int optInt = jSONObject.optInt("timeInterval", 3000);
            if (optInt >= 1000) {
                this.e = optInt;
            }
        }
        this.d.a(4, this.a);
        this.d.a(2, this.a);
        b.b.post(this.j);
    }

    public final void a(int i2, cur cur) {
        super.a(i2, cur);
        if (!this.f && i2 != 1) {
            if (i2 == 2) {
                cuq cuq = (cuq) cur;
                if (cuq.a == 1) {
                    b.b.removeCallbacks(this.j);
                    return;
                }
                if (cuq.a == 2) {
                    b.b.post(this.j);
                }
            } else if (i2 == 4) {
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

    static /* synthetic */ void b(cvu cvu) {
        if (!cvu.f && !cvu.g) {
            cvu.h = true;
            cvv a = cvw.a(cvu.c);
            cvu.h = false;
            if (a != null) {
                cvu.d.b().send(new cvt(cwm.a(), a, cvu.i == null ? "" : cvu.i.getBundleInfo()));
            }
        }
    }
}
