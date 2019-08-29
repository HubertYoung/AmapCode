package com.amap.bundle.drive.ajx.module;

import com.amap.bundle.drive.ajx.inter.INaviEnd;
import com.amap.bundle.drivecommon.map.db.helper.RdCameraDBHelper;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import org.json.JSONArray;
import org.json.JSONObject;

public class ModuleDriveEndImpl {
    private static final String TAG = "ModuleDriveEndImpl";

    public void requestDriveEndOperationActivities(String str, INaviEnd iNaviEnd) {
        log("requestDriveEndOperationActivities  driveEnd:".concat(String.valueOf(iNaviEnd)));
        if (iNaviEnd != null) {
            iNaviEnd.requestDriveEndOperationActivities(str);
        }
    }

    public int getErrorReportNum(INaviEnd iNaviEnd) {
        if (iNaviEnd != null) {
            return iNaviEnd.getErrorReportNum();
        }
        return 0;
    }

    public void reportDriveEndError(String str, INaviEnd iNaviEnd) {
        if (iNaviEnd != null) {
            iNaviEnd.reportDriveEndError(str);
        }
    }

    public void reportDestinationError(String str, INaviEnd iNaviEnd) {
        if (iNaviEnd != null) {
            iNaviEnd.reportDestinationError(str);
        }
    }

    public void saveDriveEndPayforData(final String str) {
        ahn.b().execute(new Runnable() {
            public void run() {
                try {
                    new PayforNaviData(str, (String) "").save();
                } catch (Exception unused) {
                }
            }
        });
    }

    public void saveDriveEndTrcComensateInfo(final String str) {
        ahn.b().execute(new Runnable() {
            public void run() {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    RdCameraPaymentItem rdCameraPaymentItem = new RdCameraPaymentItem();
                    rdCameraPaymentItem.navi_timestamp = Long.valueOf(jSONObject.optLong("timeStamp") / 1000);
                    rdCameraPaymentItem.start = jSONObject.optString("startName");
                    rdCameraPaymentItem.end = jSONObject.optString("endName");
                    rdCameraPaymentItem.distance = Float.valueOf(((float) jSONObject.optDouble("travelDist")) / 1000.0f);
                    rdCameraPaymentItem.cost_time = Float.valueOf((float) (((int) (jSONObject.optDouble("travelTime") + 30.0d)) / 60));
                    rdCameraPaymentItem.speed = Float.valueOf((float) jSONObject.optDouble("speed"));
                    StringBuilder sb = new StringBuilder();
                    sb.append(jSONObject.optString("startLon"));
                    sb.append(",");
                    sb.append(jSONObject.optString("startLat"));
                    rdCameraPaymentItem.st_point = sb.toString();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(jSONObject.optString("endLon"));
                    sb2.append(",");
                    sb2.append(jSONObject.optString("endLat"));
                    rdCameraPaymentItem.end_point = sb2.toString();
                    rdCameraPaymentItem.violation_point = null;
                    JSONArray optJSONArray = jSONObject.optJSONArray("coords");
                    if (optJSONArray == null || optJSONArray.length() <= 0) {
                        rdCameraPaymentItem.path_points = "";
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            sb3.append(optJSONArray.opt(i));
                            if (i < optJSONArray.length() - 1) {
                                sb3.append(",");
                            }
                        }
                        rdCameraPaymentItem.path_points = sb3.toString();
                    }
                    RdCameraDBHelper.getInstance(AMapAppGlobal.getApplication()).save(rdCameraPaymentItem);
                } catch (Exception unused) {
                }
            }
        });
    }

    private void log(String str) {
        AMapLog.i(TAG, "module_opt ".concat(String.valueOf(str)));
    }
}
