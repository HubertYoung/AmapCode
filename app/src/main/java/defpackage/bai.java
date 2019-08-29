package defpackage;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.routecommute.drive.tips.DriveCommuteTipsOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;

/* renamed from: bai reason: default package */
/* compiled from: DriveCommuteTipsMapController */
public class bai {
    private static final String h = "bai";
    azz a;
    DriveCommuteTipsOverlay b;
    azy c;
    a d;
    a e;
    a f;
    IClickListener g;
    private bty i;
    private AbstractBaseMapPage j;

    public bai(AbstractBaseMapPage abstractBaseMapPage, bty bty) {
        this.i = bty;
        this.j = abstractBaseMapPage;
    }

    public final void a() {
        if (this.b != null) {
            this.b.clear();
            this.i.F().c(this.b);
            this.b = null;
        }
        if (this.a != null) {
            this.a.clear();
            this.a = null;
        }
    }

    public final void b() {
        if (this.c != null) {
            this.c.clear();
            this.c = null;
        }
    }

    public final void a(boolean z) {
        if (this.b != null) {
            this.b.setVisible(z);
        }
        if (this.a != null) {
            if (z) {
                this.a.show();
            } else {
                this.a.hide();
            }
        }
        if (this.c != null) {
            if (z) {
                this.c.show();
                return;
            }
            this.c.hide();
        }
    }

    public final void a(GeoPoint geoPoint, azu azu, int i2) {
        if (this.b == null) {
            this.b = new DriveCommuteTipsOverlay(this.i);
            this.i.F().b((BaseMapOverlay) this.b);
            if (this.b.getGLOverlay() != null) {
                this.b.getGLOverlay().setOverlayPriority(150);
            }
            this.b.setOnDriveCommuteTipListener(this.d);
            this.b.setOnDriveCommuteTipListener(this.e);
            this.b.setOverlayOnTop(true);
        }
        if (this.a != null) {
            this.a.clear();
            this.a = null;
        }
        if (this.b != null) {
            this.b.updateView(i2, geoPoint, azu);
        }
    }

    public final void a(GeoPoint geoPoint, azs azs, int i2) {
        if (this.b == null) {
            this.b = new DriveCommuteTipsOverlay(this.i);
            this.i.F().b((BaseMapOverlay) this.b);
            if (this.b.getGLOverlay() != null) {
                this.b.getGLOverlay().setOverlayPriority(150);
            }
            this.b.setOnDriveCommuteTipListener(this.d);
            this.b.setOnDriveCommuteTipListener(this.e);
            this.b.setOverlayOnTop(true);
        }
        if (this.a != null) {
            this.a.clear();
            this.a = null;
        }
        if (this.b != null) {
            this.b.updateView(i2, geoPoint, azs);
        }
    }

    public final void a(GeoPoint geoPoint, int i2) {
        if (this.b != null) {
            this.b.removeAll();
        }
        if (this.a == null) {
            this.a = new azz(this.j.getVMapPage());
            this.a.a(this.g);
        }
        this.a.a(i2, geoPoint);
    }

    public final void a(int i2, GeoPoint geoPoint) {
        if (this.c == null) {
            this.c = new azy(this.j.getVMapPage());
            this.c.a(this.f);
        }
        this.c.a(i2, geoPoint);
    }

    public final boolean c() {
        return (this.b == null && this.a == null) ? false : true;
    }

    public final boolean d() {
        return this.c != null;
    }
}
