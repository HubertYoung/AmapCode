package defpackage;

import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: cxy reason: default package */
/* compiled from: FeedPageOpenerImpl */
public final class cxy implements cxw {
    public final void a(bid bid) {
        if (bid != null) {
            b(bid, null);
        }
    }

    public final void a(bid bid, Map<String, String> map) {
        b(bid, map);
    }

    private static void b(bid bid, Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        try {
            jSONObject.put("user_loc", AppManager.getInstance().getUserLocInfo());
            if (mapView != null) {
                jSONObject.put("geoobj", bbw.a(mapView.H()));
                jSONObject.put(H5PermissionManager.level, String.valueOf(mapView.w()));
                GeoPoint geoPoint = new GeoPoint(mapView.n());
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                jSONObject.put("is_show_switchloc", bbu.a(geoPoint.getLatitude(), geoPoint.getLongitude(), latestPosition.getLatitude(), latestPosition.getLongitude()) > 1000.0d);
            }
            if (map != null) {
                for (String next : map.keySet()) {
                    jSONObject.put(next, map.get(next));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", Ajx3Path.FEED_PATH);
        pageBundle.putString("jsData", jSONObject.toString());
        apr apr = (apr) a.a.a(apr.class);
        if (apr != null) {
            apr.c(bid, pageBundle);
        }
    }
}
