package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.minimap.R;

/* renamed from: azz reason: default package */
/* compiled from: DriveCommuteTipsSimLayer */
public final class azz extends BaseLayer {
    private final String a = "DriveCommuteTipsSimLayer";
    private final String b = "redesign://basemap/RouteCommute/sim/";
    private PointOverlay<bac> c = new PointOverlay<>(this, "Commute");
    private bac d = new bac();
    private View e = LayoutInflater.from(AMapAppGlobal.getApplication()).inflate(R.layout.drive_commute_tips_sim_layout, null);
    private ImageView f = ((ImageView) this.e.findViewById(R.id.commute_tips_sim_icon));

    public azz(IVPageContext iVPageContext) {
        super(iVPageContext);
        this.c.addItem(this.d);
    }

    public final TextureWrapper loadTexture(OverlayTextureParam overlayTextureParam) {
        AMapLog.d("DriveCommuteTipsSimLayer", "loadTexture   param:".concat(String.valueOf(overlayTextureParam)));
        if (!overlayTextureParam.uri.startsWith("redesign://basemap/RouteCommute/sim/") || !(overlayTextureParam.data instanceof bac)) {
            return null;
        }
        this.f.setImageResource(((bac) overlayTextureParam.data).a);
        if (this.e == null) {
            return null;
        }
        return makeTextureWrapper(this.e);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r6, com.autonavi.common.model.GeoPoint r7) {
        /*
            r5 = this;
            java.lang.String r0 = "DriveCommuteTipsSimLayer"
            java.lang.String r1 = "updateItem   commuteType:"
            java.lang.String r2 = java.lang.String.valueOf(r6)
            java.lang.String r1 = r1.concat(r2)
            com.amap.bundle.logs.AMapLog.d(r0, r1)
            if (r7 != 0) goto L_0x0012
            return
        L_0x0012:
            r0 = 0
            r1 = 1
            if (r6 == r1) goto L_0x002a
            r1 = 3
            if (r6 == r1) goto L_0x0027
            r1 = 5
            if (r6 == r1) goto L_0x002a
            r1 = 7
            if (r6 == r1) goto L_0x0027
            r1 = 9
            if (r6 == r1) goto L_0x0024
            goto L_0x002c
        L_0x0024:
            int r0 = com.autonavi.minimap.R.drawable.drive_commute_tips_work_home_sim
            goto L_0x002c
        L_0x0027:
            int r0 = com.autonavi.minimap.R.drawable.drive_commute_tips_work_sim
            goto L_0x002c
        L_0x002a:
            int r0 = com.autonavi.minimap.R.drawable.drive_commute_tips_home_sim
        L_0x002c:
            if (r0 == 0) goto L_0x0062
            bac r6 = r5.d
            r6.a = r0
            bac r6 = r5.d
            java.lang.String r1 = "redesign://basemap/RouteCommute/sim/"
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r1.concat(r0)
            r1 = 1056964608(0x3f000000, float:0.5)
            r2 = 1065353216(0x3f800000, float:1.0)
            bac r3 = r5.d
            com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam r0 = r5.makeCustomTextureParam(r0, r1, r2, r3)
            r6.defaultTexture = r0
            bac r6 = r5.d
            com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord r0 = new com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord
            double r1 = r7.getLongitude()
            double r3 = r7.getLatitude()
            r0.<init>(r1, r3)
            r6.coord = r0
            com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay<bac> r6 = r5.c
            bac r7 = r5.d
            r6.updateItem(r7)
        L_0x0062:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.azz.a(int, com.autonavi.common.model.GeoPoint):void");
    }

    public final void a(IClickListener iClickListener) {
        this.c.setClickListener(iClickListener);
    }
}
