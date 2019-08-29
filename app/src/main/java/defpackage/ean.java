package defpackage;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.ArcOverlay;
import com.autonavi.minimap.base.overlay.ArcOverlayItem;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay;

/* renamed from: ean reason: default package */
/* compiled from: RouteOperateLineStationNew */
public final class ean {
    private static int l = 19;
    private static int m = 3;
    public bty a;
    public LineOverlay b;
    public PointOverlay c;
    public Context d;
    public a e = null;
    public int f;
    public int g;
    public int h;
    public int i;
    private ArcOverlay j;
    private cdz k;
    private int n;
    private int o;

    /* renamed from: ean$a */
    /* compiled from: RouteOperateLineStationNew */
    public class a {
        public float a = -1.0f;
        public GeoPoint b = null;
        public int c = 0;
        public int d = 0;

        public a() {
        }
    }

    public ean(bty bty, LineOverlay lineOverlay, cdz cdz) {
        this.a = bty;
        this.b = lineOverlay;
        this.d = bty.d();
        this.k = cdz;
    }

    public final void a(int i2, int i3, int i4, int i5) {
        this.f = i2;
        this.g = i3;
        this.h = i4;
        this.i = i5;
        this.n = agn.a(this.d, (float) (this.g + this.i));
        this.o = agn.a(this.d, (float) (this.f + this.h));
    }

    public final void a() {
        if (this.k != null) {
            this.k.f();
        }
    }

    public final boolean b() {
        if (this.b == null) {
            return true;
        }
        Rect bound = this.b.getBound();
        b(bound);
        if (bound != null) {
            return true;
        }
        return false;
    }

    public final void a(GeoPoint geoPoint) {
        b(geoPoint);
        this.a.P();
    }

    private void b(GeoPoint geoPoint) {
        if (this.j != null) {
            if (geoPoint == null) {
                this.j.clear();
            } else if (this.j.getArcItem(0) == null) {
                ArcOverlayItem arcOverlayItem = new ArcOverlayItem(geoPoint.x, geoPoint.y, 0, 1, 0, 0);
                arcOverlayItem.mDefaultMarker = this.j.createMarker(R.drawable.marker_arc, 4);
                this.j.addItem(arcOverlayItem);
            } else {
                this.j.setItemPosition(geoPoint);
            }
        }
    }

    public final GeoPoint a(Rect rect, float f2) {
        float U = this.a.U();
        float a2 = ((float) agn.a(this.d, (float) (this.i - this.g))) * U;
        double d2 = (double) (19.0f - f2);
        return new GeoPoint(rect.centerX() + ((int) (((double) (((float) agn.a(this.d, (float) (this.h - this.f))) * U)) * Math.pow(2.0d, d2))), rect.centerY() + ((int) (((double) a2) * Math.pow(2.0d, d2))));
    }

    public final float a(Rect rect) {
        float U = this.a.U();
        return Math.min((float) l, Math.max((float) m, Math.min((float) a((double) ((((float) this.a.al()) - ((float) this.o)) * U), (double) rect.width()), (float) a((double) ((((float) this.a.am()) - ((float) this.n)) * U), (double) rect.height()))));
    }

    private static double a(double d2, double d3) {
        return 20.0d - (Math.log(d3 / d2) / Math.log(2.0d));
    }

    public final void a(int i2) {
        if (this.c != null) {
            this.c.setFocus(i2, false);
        }
    }

    public final void b(Rect rect) {
        if (rect != null) {
            DisplayMetrics displayMetrics = this.d.getResources().getDisplayMetrics();
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            defpackage.eak.a a2 = new defpackage.eak.a().a(rect, agn.a(this.d, (float) this.f), agn.a(this.d, (float) this.g), agn.a(this.d, (float) this.h), agn.a(this.d, (float) this.i));
            a2.j = 0;
            a2.k = 0;
            a2.a(this.a, i2, i3, i2 / 2, i3 / 2);
            a2.l = 0.0f;
            eak.a(a2.a().a);
        }
    }
}
