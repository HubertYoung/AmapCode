package defpackage;

import android.view.View;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.route.bus.localbus.overlay.RecommonPopOverlay;

/* renamed from: dwf reason: default package */
/* compiled from: RecommonPopOverlayManger */
public final class dwf {
    RecommonPopOverlay a = new RecommonPopOverlay(this.c);
    RecommonPopOverlay b = new RecommonPopOverlay(this.c);
    private bty c;

    public dwf(bty bty) {
        this.c = bty;
    }

    public final void a(OnItemClickListener onItemClickListener, OnItemClickListener onItemClickListener2) {
        if (this.a == null) {
            this.a = new RecommonPopOverlay(this.c);
        }
        this.a.setOnItemClickListener(onItemClickListener);
        if (this.b == null) {
            this.b = new RecommonPopOverlay(this.c);
        }
        this.b.setOnItemClickListener(onItemClickListener2);
    }

    public final void a(GeoPoint geoPoint, View view, MapViewLayoutParams mapViewLayoutParams) {
        b();
        if (view != null && this.a != null) {
            this.a.addMarker(2048, geoPoint, view, mapViewLayoutParams);
        }
    }

    public final void b(GeoPoint geoPoint, View view, MapViewLayoutParams mapViewLayoutParams) {
        c();
        if (view != null && this.b != null) {
            this.b.addMarker(2049, geoPoint, view, mapViewLayoutParams);
        }
    }

    private void b() {
        if (this.a != null) {
            this.a.clearFocus();
            this.a.clear();
        }
    }

    private void c() {
        if (this.b != null) {
            this.b.clearFocus();
            this.b.clear();
        }
    }

    public final void a() {
        c();
        b();
    }
}
