package defpackage;

import android.graphics.Point;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import org.json.JSONObject;

/* renamed from: byo reason: default package */
/* compiled from: MapControlAction */
public class byo extends vz {
    public final void a(final JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            PageBundle bundle = a.getBundle();
            if (bundle != null) {
                new ekz().a((POI) bundle.getObject("POI"), bundle.getString("fromSource"), (InfoliteResult) bundle.getObject("mSearchResultData"), (IRouteBusLineResult) bundle.getObject("mBuslineResultData"), (SuperId) bundle.getObject("superID"));
                final bty mapView = DoNotUseTool.getMapManager().getMapView();
                if (mapView != null) {
                    mapView.a((Runnable) new Runnable() {
                        public final void run() {
                            try {
                                if (jSONObject.has("mapInfo")) {
                                    JSONObject jSONObject = jSONObject.getJSONObject("mapInfo");
                                    double optDouble = jSONObject.optDouble(DictionaryKeys.CTRLXY_X, -1.0d);
                                    double optDouble2 = jSONObject.optDouble(DictionaryKeys.CTRLXY_Y, -1.0d);
                                    int optInt = jSONObject.optInt(H5PermissionManager.level);
                                    Point a2 = cfg.a(optDouble2, optDouble);
                                    if (mapView != null) {
                                        mapView.a((GLGeoPoint) new GeoPoint(a2.x, a2.y));
                                        mapView.c((float) optInt);
                                    }
                                }
                            } catch (Exception e) {
                                kf.a((Throwable) e);
                            }
                        }
                    }, 2000);
                }
            }
        }
    }
}
