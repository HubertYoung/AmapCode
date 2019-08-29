package defpackage;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* renamed from: eah reason: default package */
/* compiled from: RouteRequestParamUrlBuilder */
public final class eah {
    public static String a(POI poi, POI poi2) {
        int i;
        if (poi.getName().equals("我的位置")) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation == null || !latestLocation.getProvider().equals(IndoorLocationProvider.NAME)) {
                if (latestPosition != null) {
                    poi.setPoint(latestPosition);
                }
                poi.setPid("");
                poi.setInoorFloorNoName("");
            } else {
                Bundle extras = latestLocation.getExtras();
                if (extras != null) {
                    String string = extras.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                    String string2 = extras.getString("floor");
                    if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                        if (latestPosition != null) {
                            poi.setPoint(latestPosition);
                        }
                        poi.setPid(string);
                        poi.setInoorFloorNoName(string2);
                    }
                }
            }
        }
        if (poi2.getName().equals("我的位置")) {
            GeoPoint latestPosition2 = LocationInstrument.getInstance().getLatestPosition();
            Location latestLocation2 = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation2 == null || !latestLocation2.getProvider().equals(IndoorLocationProvider.NAME)) {
                if (latestPosition2 != null) {
                    poi2.setPoint(latestPosition2);
                }
                poi2.setPid("");
                poi2.setInoorFloorNoName("");
            } else {
                Bundle extras2 = latestLocation2.getExtras();
                if (extras2 != null) {
                    String string3 = extras2.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                    String string4 = extras2.getString("floor");
                    if (!TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string4)) {
                        if (latestPosition2 != null) {
                            poi2.setPoint(latestPosition2);
                        }
                        poi2.setPid(string3);
                        poi2.setInoorFloorNoName(string4);
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        try {
            int i2 = 2;
            if (ebm.b(poi.getId())) {
                sb.append(poi.getId());
                sb.append(",");
                i = 3;
            } else {
                sb.append(poi.getPoint().getLongitude());
                sb.append(",");
                sb.append(poi.getPoint().getLatitude());
                sb.append(",");
                if ("我的位置".equals(poi.getName())) {
                    i = 1;
                } else {
                    if (!DriveUtil.MAP_PLACE_DES.equals(poi.getName())) {
                        if (!"地图选定位置".equals(poi.getName())) {
                            i = 4;
                        }
                    }
                    i = 2;
                }
            }
            if (ebm.b(poi2.getId())) {
                sb.append(poi2.getId());
                sb.append(",");
                i2 = 3;
            } else {
                sb.append(poi2.getPoint().getLongitude());
                sb.append(",");
                sb.append(poi2.getPoint().getLatitude());
                sb.append(",");
                if ("我的位置".equals(poi2.getName())) {
                    i2 = 1;
                } else if (!DriveUtil.MAP_PLACE_DES.equals(poi2.getName())) {
                    if (!"地图选定位置".equals(poi2.getName())) {
                        i2 = 4;
                    }
                }
            }
            sb.append(i);
            sb.append(",");
            sb.append(i2);
            sb.append(",0,0,100000");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("lv", "3.1");
            jSONObject.put("isindoor", "1");
            jSONObject.put("maxLength", "100000");
            jSONObject.put(FunctionSupportConfiger.TAXI_TAG, "0");
            jSONObject.put("req_num", "2");
            JSONObject jSONObject2 = new JSONObject();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(poi.getPoint().getLongitude());
            jSONObject2.put(DictionaryKeys.CTRLXY_X, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(poi.getPoint().getLatitude());
            jSONObject2.put(DictionaryKeys.CTRLXY_Y, sb3.toString());
            jSONObject2.put("id", poi.getId());
            jSONObject2.put("precision", i);
            jSONObject2.put("pid", poi.getPid());
            jSONObject2.put("floor", poi.getIndoorFloorNoName());
            jSONObject.put(H5PageData.KEY_UC_START, jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(poi2.getPoint().getLongitude());
            jSONObject3.put(DictionaryKeys.CTRLXY_X, sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(poi2.getPoint().getLatitude());
            jSONObject3.put(DictionaryKeys.CTRLXY_Y, sb5.toString());
            jSONObject3.put("id", poi2.getId());
            jSONObject3.put("precision", i2);
            jSONObject3.put("pid", poi2.getPid());
            jSONObject3.put("floor", poi2.getIndoorFloorNoName());
            jSONObject.put("end", jSONObject3);
            return jSONObject.toString();
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }

    public static String a(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("lv", "2.9");
            jSONObject.put(DictionaryKeys.CTRLXY_X, str);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, str2);
            jSONObject.put("length", str3);
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
