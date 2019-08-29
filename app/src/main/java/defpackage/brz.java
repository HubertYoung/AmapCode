package defpackage;

import android.os.Handler;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

/* renamed from: brz reason: default package */
/* compiled from: TMCSwitchManager */
public final class brz {
    private static volatile brz d;
    private static final Object e = new Object();
    public Handler a = new Handler();
    public Runnable b = new Runnable() {
        public final void run() {
            brz.this.b();
        }
    };
    public a c = new a() {
        public final void a() {
            if (brz.this.f != null && brz.this.f.booleanValue()) {
                brz.this.b();
            }
        }

        public final void b() {
            if (brz.this.f != null && brz.this.f.booleanValue()) {
                brz.this.b();
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile Boolean f = null;

    public static brz a() {
        if (d == null) {
            synchronized (e) {
                try {
                    if (d == null) {
                        d = new brz();
                    }
                }
            }
        }
        return d;
    }

    protected brz() {
    }

    public final void a(boolean z, boolean z2) {
        if (this.f == null || z != this.f.booleanValue()) {
            this.f = Boolean.valueOf(z);
            if (!z2) {
                this.a.removeCallbacks(this.b);
                b();
                return;
            }
            this.a.removeCallbacks(this.b);
            this.a.postDelayed(this.b, 1000);
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        try {
            bty mapView = DoNotUseTool.getMapView();
            if (mapView != null) {
                boolean u = mapView.u();
                boolean z = false;
                int p = mapView.p(false);
                if (this.f.booleanValue() && p == 0) {
                    z = true;
                }
                if (u != z) {
                    mapView.c(z);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
