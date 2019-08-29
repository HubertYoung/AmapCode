package com.amap.bundle.drivecommon.payfor;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import com.tencent.open.SocialConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@Keep
public class PayforNaviData implements Serializable, Comparable<PayforNaviData> {
    public static final int CHECK_STATE_CHECKED_FAIL = 2;
    public static final int CHECK_STATE_CHECK_SUCCESS = 1;
    public static final int CHECK_STATE_UNCHECKED = 0;
    private static final String KEY_AVER_SPEED = "speed";
    private static final String KEY_DATE = "date";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_DONE_DAYS = "done_days";
    private static final String KEY_END_X = "endx";
    private static final String KEY_END_Y = "endy";
    private static final String KEY_FROM_ADDRESS = "start";
    private static final String KEY_HAVE_CHECKED = "status";
    private static final String KEY_HAVE_REPORTED = "reported";
    private static final String KEY_KEY = "key";
    private static final String KEY_LEN_URL = "share_url";
    private static final String KEY_NAVIGATION_TIME = "navi_time";
    private static final String KEY_PAYED_MONEY = "money";
    private static final String KEY_POIID = "poiid";
    private static final String KEY_RECORD_ID = "record_id";
    public static final String KEY_STORAGE = "apply_pay_for_data.v2";
    private static final String KEY_TIME = "time";
    private static final String KEY_TIME_USED = "last_time";
    private static final String KEY_TO_ADDRESS = "end";
    public static final long ONE_MONTH = 2592000;
    private static final long serialVersionUID = 1;
    public final int averageSpeed;
    public final int checkState;
    public final String date;
    public final int distance;
    public int doneDays;
    public double endX;
    public double endY;
    public final String fromAddress;
    public final boolean haveReported;
    public final String key;
    public double moneyMaypayed;
    public String naviTime;
    public final double payedMoney;
    public final String poiid;
    public String recordId;
    public final String shareUrl;
    public final String time;
    public final int timeUsed;
    public String toAddress;
    public POI toPoi;

    public interface a {
        POI a();

        POI b();

        int c();

        int d();

        int e();

        String f();
    }

    public static boolean isNeedShowMoney(double d) {
        return d >= 2.0d;
    }

    public void log() {
    }

    public PayforNaviData(String str) throws JSONException {
        this(new JSONObject(str));
    }

    public PayforNaviData(JSONObject jSONObject) {
        this.poiid = jSONObject.optString("poiid");
        this.fromAddress = jSONObject.optString("start");
        this.toAddress = jSONObject.optString(KEY_TO_ADDRESS);
        this.endX = jSONObject.optDouble(KEY_END_X);
        this.endY = jSONObject.optDouble(KEY_END_Y);
        this.distance = jSONObject.optInt(KEY_DISTANCE);
        this.timeUsed = jSONObject.optInt(KEY_TIME_USED);
        this.averageSpeed = jSONObject.optInt(KEY_AVER_SPEED);
        this.haveReported = jSONObject.optBoolean(KEY_HAVE_REPORTED, true);
        this.checkState = jSONObject.optInt("status");
        this.payedMoney = jSONObject.optDouble(KEY_PAYED_MONEY);
        this.date = jSONObject.optString(KEY_DATE);
        this.time = jSONObject.optString("time", "");
        this.key = jSONObject.optString("key");
        this.recordId = jSONObject.optString(KEY_RECORD_ID);
        this.naviTime = jSONObject.optString(KEY_NAVIGATION_TIME, "");
        this.shareUrl = jSONObject.optString(KEY_LEN_URL);
        this.doneDays = jSONObject.optInt("done_days");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x00a4, code lost:
        r9 = r9.substring(0, r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PayforNaviData(com.amap.bundle.drivecommon.payfor.PayforNaviData.a r8, java.lang.String r9) {
        /*
            r7 = this;
            r7.<init>()
            long r0 = java.lang.System.currentTimeMillis()
            com.autonavi.common.model.POI r2 = r8.b()
            java.lang.String r2 = r2.getId()
            r7.poiid = r2
            com.autonavi.common.model.POI r2 = r8.a()
            java.lang.String r2 = r2.getName()
            r7.fromAddress = r2
            com.autonavi.common.model.POI r2 = r8.b()
            java.lang.String r2 = r2.getName()
            r7.toAddress = r2
            com.autonavi.common.model.POI r2 = r8.b()
            r7.toPoi = r2
            com.autonavi.common.model.POI r2 = r8.b()
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            int r3 = r2.x
            long r3 = (long) r3
            int r2 = r2.y
            long r5 = (long) r2
            com.autonavi.minimap.map.DPoint r2 = defpackage.cfg.a(r3, r5)
            double r3 = r2.x
            r7.endX = r3
            double r2 = r2.y
            r7.endY = r2
            int r2 = r8.c()
            r7.distance = r2
            int r2 = r8.d()
            r7.timeUsed = r2
            int r2 = r8.e()
            r7.averageSpeed = r2
            java.lang.String r8 = r8.f()
            r7.shareUrl = r8
            r8 = 0
            r7.haveReported = r8
            r7.checkState = r8
            r2 = 0
            r7.payedMoney = r2
            r2 = 0
            r7.recordId = r2
            java.util.Date r2 = new java.util.Date
            r2.<init>(r0)
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat
            java.lang.String r4 = "MM-dd"
            java.util.Locale r5 = java.util.Locale.getDefault()
            r3.<init>(r4, r5)
            java.lang.String r3 = r3.format(r2)
            r7.date = r3
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat
            java.lang.String r4 = "HH:mm"
            java.util.Locale r5 = java.util.Locale.getDefault()
            r3.<init>(r4, r5)
            java.lang.String r2 = r3.format(r2)
            r7.time = r2
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r7.key = r0
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 != 0) goto L_0x00a8
            r0 = 46
            int r0 = r9.indexOf(r0)
            if (r0 <= 0) goto L_0x00a8
            java.lang.String r9 = r9.substring(r8, r0)
        L_0x00a8:
            r7.naviTime = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drivecommon.payfor.PayforNaviData.<init>(com.amap.bundle.drivecommon.payfor.PayforNaviData$a, java.lang.String):void");
    }

    public PayforNaviData(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.poiid = jSONObject.optString("poiid");
        this.fromAddress = jSONObject.optString("startName");
        this.toAddress = jSONObject.optString("endName");
        GeoPoint geoPoint = new GeoPoint(jSONObject.optDouble("endLon"), jSONObject.optDouble("endLat"));
        this.toPoi = POIFactory.createPOI(this.toAddress, geoPoint);
        this.toPoi.setPid(this.poiid);
        DPoint a2 = cfg.a((long) geoPoint.x, (long) geoPoint.y);
        this.endX = a2.x;
        this.endY = a2.y;
        this.distance = jSONObject.optInt(KEY_DISTANCE);
        this.timeUsed = jSONObject.optInt("seconds");
        this.averageSpeed = jSONObject.optInt(KEY_AVER_SPEED);
        this.naviTime = jSONObject.optString("timestamp");
        this.shareUrl = jSONObject.optString(SocialConstants.PARAM_SHARE_URL);
        this.haveReported = false;
        this.checkState = 0;
        this.payedMoney = 0.0d;
        this.recordId = null;
        Date date2 = new Date(currentTimeMillis);
        this.date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(date2);
        this.time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date2);
        this.key = String.valueOf(currentTimeMillis);
        if (!TextUtils.isEmpty(this.naviTime)) {
            int indexOf = this.naviTime.indexOf(46);
            if (indexOf > 0) {
                this.naviTime = this.naviTime.substring(0, indexOf);
            }
        }
    }

    @Deprecated
    public PayforNaviData(long j, int i) {
        this.poiid = String.valueOf(j).substring(3);
        this.fromAddress = AMapAppGlobal.getApplication().getString(R.string.my_location);
        this.toAddress = AMapAppGlobal.getApplication().getString(R.string.destination);
        this.endX = 112.3456d;
        this.endY = 36.8902d;
        this.distance = i;
        this.timeUsed = 3000;
        this.averageSpeed = 30;
        this.haveReported = false;
        this.checkState = 0;
        this.payedMoney = 0.0d;
        this.recordId = null;
        Date date2 = new Date(j);
        this.date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(date2);
        this.time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date2);
        this.key = String.valueOf(j);
        this.naviTime = String.valueOf(j / 1000);
        this.shareUrl = "";
    }

    public String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("poiid", this.poiid);
            jSONObject.put("start", this.fromAddress);
            jSONObject.put(KEY_TO_ADDRESS, this.toAddress);
            jSONObject.put(KEY_END_X, this.endX);
            jSONObject.put(KEY_END_Y, this.endY);
            jSONObject.put(KEY_DISTANCE, this.distance);
            jSONObject.put(KEY_TIME_USED, this.timeUsed);
            jSONObject.put(KEY_AVER_SPEED, this.averageSpeed);
            jSONObject.put(KEY_HAVE_REPORTED, this.haveReported);
            jSONObject.put("status", this.checkState);
            jSONObject.put(KEY_PAYED_MONEY, this.payedMoney);
            jSONObject.put(KEY_DATE, this.date);
            jSONObject.put("time", this.time);
            jSONObject.put("key", this.key);
            jSONObject.put(KEY_RECORD_ID, this.recordId);
            jSONObject.put(KEY_NAVIGATION_TIME, this.naviTime);
            jSONObject.put(KEY_LEN_URL, this.shareUrl);
            jSONObject.put("done_days", this.doneDays);
            return jSONObject.toString();
        } catch (JSONException e) {
            kf.a((Throwable) e);
            return null;
        }
    }

    public void save() {
        bic.a((String) KEY_STORAGE).set(this.key, toJson());
    }

    public void delete() {
        bic.a((String) KEY_STORAGE).remove(this.key);
    }

    @SuppressFBWarnings({"EQ_COMPARETO_USE_OBJECT_EQUALS"})
    public int compareTo(PayforNaviData payforNaviData) {
        return payforNaviData.key.compareTo(this.key);
    }

    public static List<PayforNaviData> getNativeNaviDatas() {
        LinkedList linkedList = new LinkedList();
        WebStorage<String> a2 = bic.a((String) KEY_STORAGE);
        for (String str : a2) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    long currentTimeMillis = System.currentTimeMillis() - Long.parseLong(str);
                    PayforNaviData payforNaviData = new PayforNaviData(a2.get(str));
                    if (currentTimeMillis >= 2592000) {
                        payforNaviData.delete();
                    } else {
                        linkedList.add(payforNaviData);
                    }
                }
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
        return linkedList;
    }
}
