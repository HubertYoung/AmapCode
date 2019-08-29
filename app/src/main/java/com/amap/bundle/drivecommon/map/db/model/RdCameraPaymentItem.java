package com.amap.bundle.drivecommon.map.db.model;

import android.text.TextUtils;
import com.autonavi.common.model.GeoPoint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RdCameraPaymentItem {
    public Float cost_time;
    public String date;
    public String dateFormat = null;
    public Float distance;
    public String end;
    public GeoPoint endGeoPoint;
    public String end_point;
    public Long navi_timestamp;
    public List<GeoPoint> pathGeoPoints;
    public String path_points;
    public final int promotion = 4;
    public String report_time;
    public Float speed;
    public GeoPoint stGeoPoint;
    public String st_point;
    public String start;
    public String status;
    public String time;
    public String type;
    public GeoPoint violationGeoPoint;
    public String violation_point;
    public String weekday;

    enum Status {
        ;

        /* access modifiers changed from: 0000 */
        public abstract String a();

        /* access modifiers changed from: 0000 */
        public abstract int b();
    }

    public String getDateDesc() {
        if (!TextUtils.isEmpty(this.dateFormat)) {
            return this.dateFormat;
        }
        if (TextUtils.isEmpty(this.date) || TextUtils.isEmpty(this.weekday) || TextUtils.isEmpty(this.time)) {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis((this.navi_timestamp.longValue() * 1000) - ((long) ((this.cost_time.intValue() * 60) * 1000)));
            this.date = new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
            boolean z = true;
            if (instance.getFirstDayOfWeek() != 1) {
                z = false;
            }
            int i = 7;
            int i2 = instance.get(7);
            if (z) {
                int i3 = i2 - 1;
                if (i3 != 0) {
                    i = i3;
                }
            } else {
                i = i2;
            }
            switch (i) {
                case 1:
                    this.weekday = "星期一";
                    break;
                case 2:
                    this.weekday = "星期二";
                    break;
                case 3:
                    this.weekday = "星期三";
                    break;
                case 4:
                    this.weekday = "星期四";
                    break;
                case 5:
                    this.weekday = "星期五";
                    break;
                case 6:
                    this.weekday = "星期六";
                    break;
                case 7:
                    this.weekday = "星期日";
                    break;
                default:
                    this.weekday = "";
                    break;
            }
            this.time = new SimpleDateFormat("HH:mm:ss").format(instance.getTime());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.date);
        sb.append("(");
        sb.append(this.weekday);
        sb.append(") ");
        sb.append(this.time);
        this.dateFormat = sb.toString();
        return this.dateFormat;
    }

    public long getNaviStarttimestamp() {
        return this.navi_timestamp.longValue() - ((long) (this.cost_time.intValue() * 60));
    }

    public String getDistanceDescr() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        StringBuilder sb = new StringBuilder("里程");
        sb.append(decimalFormat.format(this.distance));
        sb.append("km");
        return sb.toString();
    }

    public String getTimeCostDescr() {
        if (this.cost_time.floatValue() <= 0.0f) {
            return "用时<1分钟";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        if (this.cost_time.floatValue() > 60.0f) {
            StringBuilder sb = new StringBuilder("用时");
            sb.append(decimalFormat.format((double) (this.cost_time.floatValue() / 60.0f)));
            sb.append("小时");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder("用时");
        sb2.append(decimalFormat.format(this.cost_time));
        sb2.append("分钟");
        return sb2.toString();
    }

    public String getSpeedDescr() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        StringBuilder sb = new StringBuilder("车速");
        sb.append(decimalFormat.format(this.speed));
        sb.append("km/h");
        return sb.toString();
    }

    public String getType() {
        return this.type;
    }

    public String getReportTime() {
        return this.report_time;
    }

    public GeoPoint getStpoint() {
        if (this.stGeoPoint == null) {
            this.stGeoPoint = new GeoPoint();
        }
        if (this.st_point != null) {
            this.stGeoPoint = StringToGeoPoint(this.st_point);
        }
        return this.stGeoPoint;
    }

    public GeoPoint getEndpoint() {
        if (this.endGeoPoint == null) {
            this.endGeoPoint = new GeoPoint();
        }
        if (this.end_point != null) {
            this.endGeoPoint = StringToGeoPoint(this.end_point);
        }
        return this.endGeoPoint;
    }

    public GeoPoint getViolationpoint() {
        return this.violationGeoPoint;
    }

    public List<GeoPoint> getPathpoints() {
        if (this.pathGeoPoints == null) {
            this.pathGeoPoints = new ArrayList();
        }
        if (this.path_points != null) {
            this.pathGeoPoints = StringToGeoPoints(this.path_points);
        }
        return this.pathGeoPoints;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatusDescr() {
        Status[] values;
        for (Status status2 : Status.values()) {
            if (status2.name().equals(this.status)) {
                return status2.a();
            }
        }
        return Status.d.a();
    }

    public int getStatusColor() {
        Status[] values;
        for (Status status2 : Status.values()) {
            if (status2.name().equals(this.status)) {
                return status2.b();
            }
        }
        return Status.d.b();
    }

    public static String GeoPointToString(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(geoPoint.getLongitude());
        sb.append(",");
        sb.append(geoPoint.getLatitude());
        return sb.toString();
    }

    public static GeoPoint StringToGeoPoint(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(",");
        return new GeoPoint(Double.valueOf(split[0]).doubleValue(), Double.valueOf(split[1]).doubleValue());
    }

    public static String GeoPointsToString(List<GeoPoint> list) {
        if (list == null || list.size() <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            GeoPoint geoPoint = list.get(i);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(geoPoint.getLongitude());
            sb2.append(",");
            sb2.append(geoPoint.getLatitude());
            sb2.append(",");
            sb.append(sb2.toString());
        }
        GeoPoint geoPoint2 = list.get(list.size() - 1);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(geoPoint2.getLongitude());
        sb3.append(",");
        sb3.append(geoPoint2.getLatitude());
        sb.append(sb3.toString());
        return sb.toString();
    }

    public static List<GeoPoint> StringToGeoPoints(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        String[] split = str.replace("[", "").replace("]", "").split(",");
        int i = 0;
        while (i < split.length) {
            int i2 = i + 1;
            double doubleValue = Double.valueOf(split[i]).doubleValue();
            i = i2 + 1;
            arrayList.add(new GeoPoint(doubleValue, Double.valueOf(split[i2]).doubleValue()));
        }
        return arrayList;
    }

    public static List<RdCameraPaymentItem> parsePaymentItem(JSONObject jSONObject) {
        List<RdCameraPaymentItem> list = null;
        if (!jSONObject.has("records")) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("records");
                for (int i = 0; i < jSONArray.length(); i++) {
                    RdCameraPaymentItem rdCameraPaymentItem = new RdCameraPaymentItem();
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    rdCameraPaymentItem.date = jSONObject2.getString("date");
                    rdCameraPaymentItem.weekday = jSONObject2.getString("weekday");
                    rdCameraPaymentItem.time = jSONObject2.getString("time");
                    rdCameraPaymentItem.distance = Float.valueOf(jSONObject2.getString("distance"));
                    rdCameraPaymentItem.cost_time = Float.valueOf(jSONObject2.getString("cost_time"));
                    rdCameraPaymentItem.speed = Float.valueOf(jSONObject2.getString("speed"));
                    rdCameraPaymentItem.start = jSONObject2.getString("st_point");
                    rdCameraPaymentItem.end = jSONObject2.getString("end_point");
                    rdCameraPaymentItem.status = jSONObject2.getString("status");
                    rdCameraPaymentItem.type = jSONObject2.getString("type");
                    rdCameraPaymentItem.report_time = jSONObject2.getString("report_time");
                    arrayList.add(rdCameraPaymentItem);
                }
                return arrayList;
            } catch (JSONException e) {
                e = e;
                list = arrayList;
                e.printStackTrace();
                return list;
            }
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            return list;
        }
    }
}
