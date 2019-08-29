package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.route.health.HelLineOverlay;
import com.autonavi.jni.route.health.PathLineParser;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.common.view.HelPointOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareBikeAddressTipOverlay;
import com.autonavi.minimap.route.sharebike.overlay.ShareRidingNaviOverlay;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage.TipStyle;
import org.json.JSONObject;

/* renamed from: ehb reason: default package */
/* compiled from: ShareRidingOverlay */
public final class ehb {
    public ShareRidingNaviOverlay a = null;
    public HelLineOverlay b = null;
    public HelPointOverlay c = null;
    public ShareBikeAddressTipOverlay d;
    public Context e;
    public int f = 0;
    private HelLineOverlay g = null;
    private HelPointOverlay h;
    private bty i;
    private boolean j;

    public ehb(AbstractBaseMapPage abstractBaseMapPage) {
        b();
        this.i = abstractBaseMapPage.getMapManager().getMapView();
        if (this.i != null) {
            this.a = new ShareRidingNaviOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.a);
            this.g = new HelLineOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.g);
            this.b = new HelLineOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.b);
            this.c = new HelPointOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.c);
            this.h = new HelPointOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.h);
            this.d = new ShareBikeAddressTipOverlay(this.i);
            abstractBaseMapPage.addOverlay(this.d);
            this.e = abstractBaseMapPage.getContext();
        }
    }

    public final void a() {
        this.a.clear();
        this.b.clear();
        this.g.clear();
        this.c.clear();
        this.h.clear();
        this.d.clear();
        this.f = 0;
    }

    public final void a(int i2, int i3, int i4) {
        this.a.firstSetCarPosition(i2, i3, i4);
    }

    public final void a(GeoPoint geoPoint, int i2) {
        this.a.drawNaviLineV2(geoPoint, i2);
    }

    public final void a(GeoPoint geoPoint, int i2, int i3) {
        this.a.drawNaviLineV3(geoPoint, i2, i3);
    }

    public final void a(GeoPoint geoPoint) {
        if (geoPoint != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mDefaultMarker = this.h.createMarker(R.drawable.run_point_start, 4);
            this.h.addItem(pointOverlayItem);
        }
    }

    public final void b(int i2, int i3, int i4) {
        this.a.updateCarPosition(i2, i3, i4);
    }

    public final void a(POI poi, int i2, int i3, TipStyle tipStyle) {
        String str = "";
        if (poi != null) {
            str = poi.getName();
        }
        if (TextUtils.equals("我的位置", str)) {
            str = "";
        }
        if (this.d != null && i2 != 0 && i3 != 0) {
            this.d.updataOverlay(str, new GeoPoint(i2, i3));
            if (!this.j) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", TextUtils.isEmpty(str) ? "blank" : "destination");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (tipStyle == TipStyle.RIDING) {
                    LogManager.actionLogV2("P00300", "B008", jSONObject);
                } else {
                    LogManager.actionLogV2("P00310", "B005", jSONObject);
                }
                this.j = true;
            }
        }
    }

    public final void b() {
        if (this.j) {
            this.j = false;
        }
    }

    public final void a(final PathLineParser pathLineParser) {
        if (this.i != null) {
            this.i.c((Runnable) new Runnable() {
                public final void run() {
                    ehb.this.b.clear();
                    ehb.this.b.drawPathSegments(pathLineParser);
                }
            });
        }
    }

    public final void b(PathLineParser pathLineParser) {
        this.g.clear();
        this.g.drawPathSegments(pathLineParser);
    }
}
