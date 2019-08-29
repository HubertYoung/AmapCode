package defpackage;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.IPoiDetailPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ceb reason: default package */
/* compiled from: GpsPresenter */
public final class ceb {
    cdv a;
    MapManager b;
    public cee c;
    public String d;
    public a e = null;
    defpackage.cec.a f = new defpackage.cec.a() {
        public final void a() {
            if (ceb.this.c != null) {
                ceb.this.c.onClickBefore();
            }
            ceb ceb = ceb.this;
            int i = 0;
            switch (ceb.a.h()) {
                case 1:
                case 4:
                    i = 1;
                    break;
                case 2:
                case 5:
                    i = 2;
                    break;
                case 6:
                case 7:
                    i = 3;
                    break;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", String.valueOf(i));
                jSONObject.put(TrafficUtil.KEYWORD, ceb.b.getMapView().w());
                if (!TextUtils.isEmpty(ceb.d)) {
                    jSONObject.put("status", ceb.d);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AMapLog.d(ReportManager.LOG_PATH, "gps:".concat(String.valueOf(jSONObject)));
            LogManager.actionLogV2("P00001", "B028", jSONObject);
            HashMap hashMap = new HashMap();
            hashMap.put("type", String.valueOf(i));
            StringBuilder sb = new StringBuilder();
            sb.append(ceb.b.getMapView().w());
            hashMap.put(TrafficUtil.KEYWORD, sb.toString());
            if (!TextUtils.isEmpty(ceb.d)) {
                hashMap.put("status", ceb.d);
            }
            kd.a((String) "amap.P00001.0.B028", (Map<String, String>) hashMap);
            ceb.a(ceb.this);
            if (ceb.this.e != null) {
                ceb.this.e.a();
            }
            if (ceb.this.c != null) {
                ceb.this.c.onClickEnd();
            }
        }
    };
    private Context g;
    private ceg h;

    /* renamed from: ceb$a */
    /* compiled from: GpsPresenter */
    public interface a {
        void a();
    }

    public ceb(Context context, cdv cdv, ceg ceg, MapManager mapManager) {
        this.g = context;
        this.a = cdv;
        this.h = ceg;
        this.b = mapManager;
        this.a.a(this.f);
    }

    static /* synthetic */ void a(ceb ceb) {
        boolean z;
        ceb.b.onGpsBtnClick();
        ceb.b.getOverlayManager().clearScenicSelectMapPois();
        ceb.b.getOverlayManager().getGpsLayer();
        if (cdx.b().a > 500) {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            if (!cei.a && !mapSharePreference.getBooleanValue("showAccurate", false)) {
                cei.a = true;
                new cei().a((Activity) ceb.g);
            }
        }
        ceb.h.a();
        if (ceb.c != null) {
            ceb.c.onClickDoing();
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof IPoiDetailPage) {
            IPoiDetailPage iPoiDetailPage = (IPoiDetailPage) pageContext;
            z = iPoiDetailPage.isGpsTipDisable();
            iPoiDetailPage.onPageGpsBtnClicked();
        } else {
            z = false;
        }
        if (ceb.a.c() && !z) {
            int a2 = ceb.b.getMapView().F().a();
            for (int i = 0; i < a2; i++) {
                BaseMapOverlay a3 = ceb.b.getMapView().F().a(i);
                if (PointOverlay.class.isInstance(a3)) {
                    PointOverlay pointOverlay = (PointOverlay) a3;
                    pointOverlay.clearFocus();
                    if (pointOverlay.isClearWhenLoseFocus()) {
                        pointOverlay.clear();
                    }
                }
            }
            ceb.b.getOverlayManager().showGpsFooter();
            ceb.b.getMapView().E();
        }
    }
}
