package defpackage;

import android.content.Context;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.minimap.R;

/* renamed from: csh reason: default package */
/* compiled from: TrafficPointLayer */
public final class csh extends BaseLayer {
    private PointOverlay<PointOverlayItem> a = new PointOverlay<>(this, "TrafficPoint");
    /* access modifiers changed from: private */
    public bty b;
    /* access modifiers changed from: private */
    public Context c;
    private String d = null;

    public csh(IVPageContext iVPageContext, bty bty, Context context) {
        super(iVPageContext);
        this.b = bty;
        this.c = context;
    }

    public final void a(final als als) {
        if (this.d != null) {
            this.b.a(this.d);
        }
        this.d = als.b;
        final GeoPointHD geoPointHD = new GeoPointHD(als.e, als.f);
        aho.a(new Runnable() {
            public final void run() {
                csh.this.b.a(als.e, als.f, 2, (float) csh.this.c.getResources().getDrawable(R.drawable.tmc_poi_hl).getIntrinsicWidth(), (float) csh.this.c.getResources().getDrawable(R.drawable.tmc_poi_hl).getIntrinsicHeight(), als.b);
                csh.this.b.a((GLGeoPoint) geoPointHD);
            }
        });
        PointOverlayItem pointOverlayItem = new PointOverlayItem();
        pointOverlayItem.defaultTexture = makeTextureParam(R.drawable.tmc_poi_hl, 0.5f, 1.0f);
        pointOverlayItem.coord = new Coord(geoPointHD.getLongitude(), geoPointHD.getLatitude());
        this.a.addItem(pointOverlayItem);
    }

    public final void clear() {
        super.clear();
        if (this.d != null) {
            this.b.a(this.d);
        }
        this.d = "";
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.d != null) {
            this.b.a(this.d);
        }
        this.d = "";
    }
}
