package defpackage;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.route.health.HelLineOverlay;
import com.autonavi.jni.route.health.PathLineParser;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.common.view.HelPointOverlay;
import com.autonavi.minimap.route.run.overlay.HelRunNaviOverlay;

/* renamed from: efk reason: default package */
/* compiled from: RouteHelRunOverlay */
public final class efk {
    public HelRunNaviOverlay a = null;
    public HelLineOverlay b = null;
    public HelLineOverlay c = null;
    public HelPointOverlay d = null;
    public Context e;
    private HelLineOverlay f = null;
    private HelPointOverlay g = null;
    private int h = 0;
    private bty i;
    private ean j;

    public efk(AbstractBaseMapPage abstractBaseMapPage) {
        this.i = abstractBaseMapPage.getMapManager().getMapView();
        if (this.i != null) {
            this.c = new HelLineOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.c);
            this.a = new HelRunNaviOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.a);
            this.f = new HelLineOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.f);
            this.b = new HelLineOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.b);
            this.g = new HelPointOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.g);
            this.d = new HelPointOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.d);
            this.e = abstractBaseMapPage.getContext();
        }
    }

    public final void a(boolean z) {
        if (z) {
            this.a.clear();
        }
        this.b.clear();
        this.f.clear();
        this.g.clear();
        this.h = 0;
        this.c.clear();
        this.d.clear();
    }

    public final void a(GeoPoint geoPoint, int i2) {
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        new MapViewLayoutParams(-2, -2, geoPoint, 3).mode = 0;
        View inflate = ((LayoutInflater) this.e.getSystemService("layout_inflater")).inflate(R.layout.route_run_milestone_view, null);
        TextView textView = (TextView) inflate.findViewById(R.id.stone_miles);
        textView.setText(String.valueOf(i2 / 1000));
        StringBuilder sb = new StringBuilder("HelRunMileStone: mile :mils gettext ");
        sb.append(i2);
        sb.append(",");
        sb.append(textView.getText().toString());
        sb.append(", ");
        eft.a("ENGINE_OUT", sb.toString());
        pointOverlayItem.mDefaultMarker = this.g.createMarker(this.h, inflate, 4, 0.0f, 0.0f, false);
        this.g.addItem(pointOverlayItem);
        this.h++;
    }

    public final void b(GeoPoint geoPoint, int i2) {
        this.a.drawNaviLine_v2(geoPoint, i2);
    }

    public final void a(GeoPoint geoPoint, int i2, int i3) {
        this.a.drawNaviLine_v3(geoPoint, i2, i3);
    }

    public final void a(int i2, int i3, int i4) {
        this.a.updateCarPosition(i2, i3, i4);
    }

    public final void a(final PathLineParser pathLineParser) {
        if (this.i != null) {
            this.i.c((Runnable) new Runnable() {
                public final void run() {
                    efk.this.b.clear();
                    efk.this.b.drawPathSegments(pathLineParser);
                }
            });
        }
    }

    public final void b(PathLineParser pathLineParser) {
        this.f.clear();
        this.f.drawPathSegments(pathLineParser);
    }

    public final void a(GeoPoint geoPoint) {
        if (geoPoint != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mDefaultMarker = this.d.createMarker(R.drawable.run_point_start, 4);
            this.d.addItem(pointOverlayItem);
        }
    }

    public final void a() {
        if (this.j == null) {
            this.j = new ean(this.i, this.c, null);
            this.j.a(50, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 50, 170);
        }
        Rect bound = this.c.getBound();
        if (bound != null) {
            float a2 = this.j.a(bound);
            GeoPoint a3 = this.j.a(bound, a2);
            this.i.W();
            this.i.b(this.i.al() / 2, this.i.am() / 2);
            this.i.g(0.0f);
            this.i.e(0.0f);
            this.i.a(a3.x, a3.y);
            this.i.d(a2);
        }
    }
}
