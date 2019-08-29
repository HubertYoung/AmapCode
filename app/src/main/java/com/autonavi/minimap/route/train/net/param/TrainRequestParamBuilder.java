package com.autonavi.minimap.route.train.net.param;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class TrainRequestParamBuilder implements ParamEntity {
    public static final int TRAIN_ALL_TYPE = 0;
    public static final int TRAIN_ALL_TYPE_TICKET = 0;
    public static final String TRAIN_DEPARTURE_DATE = "yyyy-MM-dd";
    public static final String TRAIN_DEPARTURE_TIME = "HH-MM";
    public static final int TRAIN_ONLY_G_TYPE = 1;
    public static final int TRAIN_ONLY_STUDENT_TICKET = 1;

    public static String buildTrainParam(@NonNull GeoPoint geoPoint, @NonNull GeoPoint geoPoint2, @NonNull String str, int i, int i2, int i3) {
        JSONObject jSONObject = new JSONObject();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(geoPoint2.getLongitude());
            sb2.append(",");
            sb2.append(geoPoint2.getLatitude());
            jSONObject.put("x1", geoPoint.getLongitude());
            jSONObject.put("y1", geoPoint.getLatitude());
            jSONObject.put("x2", geoPoint2.getLongitude());
            jSONObject.put("y2", geoPoint2.getLatitude());
            if (i3 != 0) {
                jSONObject.put("req_num", i3);
            }
            if (TextUtils.isEmpty(str) || !isValidDate(str, "yyyy-MM-dd")) {
                jSONObject.put("date", getCurrentTime("yyyy-MM-dd"));
            } else {
                jSONObject.put("date", str);
            }
            jSONObject.put("time", getCurrentTime(TRAIN_DEPARTURE_TIME));
            int i4 = 0;
            jSONObject.put("traintype", i == 1 ? 1 : 0);
            if (i2 == 1) {
                i4 = 1;
            }
            jSONObject.put("tickettype", i4);
            jSONObject.put("ver", 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static String buildTrainParam(@NonNull POI poi, @NonNull POI poi2, @NonNull String str, int i, int i2, int i3) {
        JSONObject jSONObject = new JSONObject();
        try {
            GeoPoint point = poi.getPoint();
            GeoPoint point2 = poi2.getPoint();
            jSONObject.put("x1", point.getLongitude());
            jSONObject.put("y1", point.getLatitude());
            jSONObject.put("x2", point2.getLongitude());
            jSONObject.put("y2", point2.getLatitude());
            jSONObject.put("poiid1", poi.getId());
            jSONObject.put("poiid2", poi2.getId());
            jSONObject.put("pn1", poi.getName());
            jSONObject.put("pn2", poi2.getName());
            if (i3 != 0) {
                jSONObject.put("req_num", i3);
            }
            if (TextUtils.isEmpty(str) || !isValidDate(str, "yyyy-MM-dd")) {
                jSONObject.put("date", getCurrentTime("yyyy-MM-dd"));
            } else {
                jSONObject.put("date", str);
            }
            jSONObject.put("time", getCurrentTime(TRAIN_DEPARTURE_TIME));
            int i4 = 0;
            jSONObject.put("traintype", i == 1 ? 1 : 0);
            if (i2 == 1) {
                i4 = 1;
            }
            jSONObject.put("tickettype", i4);
            jSONObject.put("ver", 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static boolean isValidDate(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(str);
            return true;
        } catch (ParseException unused) {
            return false;
        }
    }

    private static String getCurrentTime(String str) {
        return new SimpleDateFormat(str).format(new Date(System.currentTimeMillis()));
    }
}
