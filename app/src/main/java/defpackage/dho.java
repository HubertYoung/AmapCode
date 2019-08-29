package defpackage;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.base.overlay.PointOverlay;

/* renamed from: dho reason: default package */
/* compiled from: RouteCarResultMidPopItem */
public final class dho extends dhp {
    private int h;
    private View k;
    private bty l;

    public dho(POI poi, int i, View view, bty bty) {
        super(poi);
        this.h = i;
        this.k = view;
        this.l = bty;
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, this.g.getPoint(), 81);
        mapViewLayoutParams.mode = 0;
        this.l.a(this.k, (LayoutParams) mapViewLayoutParams);
        this.mDefaultMarker = pointOverlay.createMarker(this.h + 360, this.k, 5, 0.5f, 1.0f, false);
        this.l.a(this.k);
        pointOverlay.setAnimatorType(2);
    }

    public final Rect[] a() {
        return new Rect[]{b()};
    }
}
