package defpackage;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;

/* renamed from: cqf reason: default package */
/* compiled from: MultiPointOverlayItem */
public final class cqf extends POIOverlayItem {
    private static final int[] a = {R.drawable.b_poi_1, R.drawable.b_poi_2, R.drawable.b_poi_3, R.drawable.b_poi_4, R.drawable.b_poi_5, R.drawable.b_poi_6, R.drawable.b_poi_7, R.drawable.b_poi_8, R.drawable.b_poi_9, R.drawable.b_poi_10, R.drawable.b_poi_geo_hl};
    private static final int[] b = {R.drawable.b_poi_1_hl, R.drawable.b_poi_2_hl, R.drawable.b_poi_3_hl, R.drawable.b_poi_4_hl, R.drawable.b_poi_5_hl, R.drawable.b_poi_6_hl, R.drawable.b_poi_7_hl, R.drawable.b_poi_8_hl, R.drawable.b_poi_9_hl, R.drawable.b_poi_10_hl, R.drawable.b_poi_hl};
    private int c;

    public cqf(POI poi, int i) {
        super(poi);
        this.c = i;
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        if (this.c < a.length) {
            this.mDefaultMarker = pointOverlay.createMarker(a[this.c], 5);
        }
    }

    public final void onPrepareSetFocus(PointOverlay pointOverlay) {
        super.onPrepareSetFocus(pointOverlay);
        if (this.c < b.length) {
            this.mFocusMarker = pointOverlay.createMarker(b[this.c], 5);
        }
    }
}
