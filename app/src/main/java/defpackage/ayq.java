package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommute.common.CommuteHelper;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ayq reason: default package */
/* compiled from: CommuteBusRulesUtils */
public final class ayq {
    public static boolean a(String str, POI poi, POI poi2, GeoPoint geoPoint, boolean z) {
        azb.a(CommuteHelper.a, "isWeekRule jsonData = ".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str) && azk.b(str) && azk.a(str) && b(str, poi, poi2, geoPoint, z)) {
            return true;
        }
        return false;
    }

    private static boolean b(String str, POI poi, POI poi2, GeoPoint geoPoint, boolean z) {
        if (poi == null || poi2 == null) {
            return true;
        }
        if (!bnx.a(poi) || !bnx.a(poi2)) {
            return false;
        }
        try {
            double optDouble = new JSONObject(str).optDouble("maxDistance");
            if (optDouble > 0.0d) {
                if (!Double.isNaN(optDouble)) {
                    double d = optDouble * 1000.0d;
                    if (geoPoint == null) {
                        geoPoint = LocationInstrument.getInstance().getLatestPosition(5);
                    }
                    if (geoPoint == null) {
                        return false;
                    }
                    POI createPOI = POIFactory.createPOI();
                    createPOI.setName("我的位置");
                    createPOI.setPoint(geoPoint);
                    float a = cfe.a(createPOI.getPoint(), poi.getPoint());
                    float a2 = cfe.a(createPOI.getPoint(), poi2.getPoint());
                    azb.a(CommuteHelper.a, "BUS---isDistanceRule 距离家直线距离 homeDistance = ".concat(String.valueOf(a)));
                    azb.a(CommuteHelper.a, "BUS---isDistanceRule 距离公司直线距离 companyDistance = ".concat(String.valueOf(a2)));
                    azb.a(CommuteHelper.a, "BUS---isDistanceRule 规则最大距离 maxM = ".concat(String.valueOf(d)));
                    if (z && (((double) a) >= d || ((double) a2) >= d)) {
                        ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.bus_commute_distance_long_toast));
                    }
                    return ((double) a) < d && ((double) a2) < d;
                }
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int a(GeoPoint geoPoint, POI poi, POI poi2) {
        if (geoPoint == null) {
            geoPoint = LocationInstrument.getInstance().getLatestPosition(5);
        }
        if (geoPoint == null || !bnx.a(poi) || !bnx.a(poi2)) {
            return -1;
        }
        float a = cfe.a(geoPoint, poi.getPoint());
        float a2 = cfe.a(geoPoint, poi2.getPoint());
        double a3 = a.a.a();
        if (a < a2) {
            if (((double) a) - a3 <= 0.0d) {
                return 0;
            }
        } else if (((double) a2) - a3 <= 0.0d) {
            return 1;
        }
        return -1;
    }
}
