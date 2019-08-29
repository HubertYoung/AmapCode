package defpackage;

import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.guide.model.NaviPointInfo;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: rl reason: default package */
/* compiled from: NaviPointInfoUtil */
public final class rl {
    public static String a() {
        NaviPointInfo a = a(DriveUtil.getPOIHome());
        NaviPointInfo a2 = a(DriveUtil.getPOICompany());
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            jSONObject.put("homeInfo", jSONObject2);
            jSONObject.put("companyInfo", jSONObject3);
            jSONObject2.put("poiID", a.poiID);
            jSONObject2.put("name", a.name);
            jSONObject2.put("realPosLon", a.realPosLon);
            jSONObject2.put("realPosLat", a.realPosLat);
            jSONObject2.put("naviPosLon", a.naviPosLon);
            jSONObject2.put("naviPosLat", a.naviPosLat);
            jSONObject3.put("poiID", a2.poiID);
            jSONObject3.put("name", a2.name);
            jSONObject3.put("realPosLon", a2.realPosLon);
            jSONObject3.put("realPosLat", a2.realPosLat);
            jSONObject3.put("naviPosLon", a2.naviPosLon);
            jSONObject3.put("naviPosLat", a2.naviPosLat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private static NaviPointInfo a(POI poi) {
        NaviPointInfo naviPointInfo = new NaviPointInfo();
        if (poi == null) {
            naviPointInfo.name = "";
            naviPointInfo.poiID = "";
            naviPointInfo.naviPosLon = 0.0d;
            naviPointInfo.naviPosLat = 0.0d;
            naviPointInfo.realPosLon = 0.0d;
            naviPointInfo.realPosLat = 0.0d;
        } else {
            naviPointInfo.name = poi.getName();
            naviPointInfo.poiID = poi.getPid();
            naviPointInfo.naviPosLon = 0.0d;
            naviPointInfo.naviPosLat = 0.0d;
            naviPointInfo.realPosLon = poi.getPoint().getLongitude();
            naviPointInfo.realPosLat = poi.getPoint().getLatitude();
            ArrayList<GeoPoint> entranceList = poi.getEntranceList();
            if (entranceList != null && entranceList.size() > 0) {
                GeoPoint geoPoint = entranceList.get(0);
                if (geoPoint != null) {
                    naviPointInfo.naviPosLon = geoPoint.getLongitude();
                    naviPointInfo.naviPosLat = geoPoint.getLatitude();
                }
            }
        }
        return naviPointInfo;
    }
}
