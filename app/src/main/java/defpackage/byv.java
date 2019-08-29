package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.FavoriteLayer;

/* renamed from: byv reason: default package */
/* compiled from: MapPointLayer */
public final class byv extends BaseLayer {
    public PointOverlay<PointOverlayItem> a = new PointOverlay<>(this, "SelectPOI");
    public bty b;
    private IVPageContext c;

    public byv(IVPageContext iVPageContext, bty bty) {
        super(iVPageContext);
        this.b = bty;
        this.c = iVPageContext;
    }

    public final void a(POI poi, boolean z) {
        GeoPointHD geoPointHD = new GeoPointHD(poi.getPoint());
        PointOverlayItem pointOverlayItem = new PointOverlayItem();
        pointOverlayItem.defaultTexture = makeTextureParam(R.drawable.b_poi_hl, 0.5f, 0.87f);
        a(poi, pointOverlayItem);
        pointOverlayItem.coord = new Coord(geoPointHD.getLongitude(), geoPointHD.getLatitude());
        this.a.addItem(pointOverlayItem);
        if (z) {
            this.b.a((GLGeoPoint) poi.getPoint());
        }
        a();
    }

    private static void a() {
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            FavoriteLayer g = brn.g();
            if (g != null) {
                g.clearFocus();
            }
        }
    }

    private void a(POI poi, PointOverlayItem pointOverlayItem) {
        if (poi.getPoiExtra() != null && poi.getPoiExtra().get("ScenicGuidePoi.IconType") != null && (poi.getPoiExtra().get("ScenicGuidePoi.IconType") instanceof String) && !TextUtils.isEmpty(poi.getPoiExtra().get("ScenicGuidePoi.IconType").toString())) {
            String obj = poi.getPoiExtra().get("ScenicGuidePoi.IconType").toString();
            StringBuilder sb = new StringBuilder("icon_");
            sb.append(obj.toLowerCase());
            sb.append("_selected");
            int a2 = bby.a(sb.toString(), this.c.getContext());
            if (a2 == 0) {
                a2 = R.drawable.icon_default_selected;
            }
            pointOverlayItem.defaultTexture = makeTextureParam(a2, 0.5f, 0.87f);
        }
    }

    public final void clear() {
        super.clear();
    }

    public final void onDestroy() {
        super.onDestroy();
    }
}
