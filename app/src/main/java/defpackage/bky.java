package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bky reason: default package */
/* compiled from: GetInfoForShareBike */
public class bky extends vz {
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0098 A[Catch:{ JSONException -> 0x00b2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r11, defpackage.wa r12) throws org.json.JSONException {
        /*
            r10 = this;
            com.amap.bundle.jsadapter.JsAdapter r0 = r10.a()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.String r1 = ""
            java.lang.String r2 = ""
            if (r11 == 0) goto L_0x001d
            java.lang.String r1 = "_action"
            java.lang.String r2 = ""
            java.lang.String r1 = r11.optString(r1, r2)
            java.lang.String r2 = "appkey"
            java.lang.String r3 = ""
            java.lang.String r2 = r11.optString(r2, r3)
        L_0x001d:
            boolean r11 = android.text.TextUtils.isEmpty(r2)
            if (r11 == 0) goto L_0x0027
            java.lang.String r2 = c()
        L_0x0027:
            com.autonavi.common.js.action.GetInfoForShareBike$1 r11 = new com.autonavi.common.js.action.GetInfoForShareBike$1
            r11.<init>(r10, r0, r12)
            org.json.JSONObject r12 = new org.json.JSONObject
            r12.<init>()
            org.json.JSONObject r12 = new org.json.JSONObject
            r12.<init>()
            java.lang.String r0 = "_action"
            r12.put(r0, r1)     // Catch:{ JSONException -> 0x00b2 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00b2 }
            r0.<init>()     // Catch:{ JSONException -> 0x00b2 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00b2 }
            r1.<init>()     // Catch:{ JSONException -> 0x00b2 }
            com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x00b2 }
            r4 = 5
            com.autonavi.common.model.GeoPoint r3 = r3.getLatestPosition(r4)     // Catch:{ JSONException -> 0x00b2 }
            r4 = 0
            if (r3 != 0) goto L_0x0054
            r6 = r4
            goto L_0x0058
        L_0x0054:
            double r6 = r3.getLongitude()     // Catch:{ JSONException -> 0x00b2 }
        L_0x0058:
            if (r3 != 0) goto L_0x005b
            goto L_0x005f
        L_0x005b:
            double r4 = r3.getLatitude()     // Catch:{ JSONException -> 0x00b2 }
        L_0x005f:
            if (r3 == 0) goto L_0x0078
            li r8 = defpackage.li.a()     // Catch:{ JSONException -> 0x00b2 }
            if (r8 == 0) goto L_0x0078
            int r9 = r3.x     // Catch:{ JSONException -> 0x00b2 }
            int r3 = r3.y     // Catch:{ JSONException -> 0x00b2 }
            lj r3 = r8.b(r9, r3)     // Catch:{ JSONException -> 0x00b2 }
            if (r3 == 0) goto L_0x0078
            java.lang.String r3 = r3.i     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ JSONException -> 0x00b2 }
            goto L_0x007a
        L_0x0078:
            java.lang.String r3 = ""
        L_0x007a:
            java.lang.String r8 = "lng"
            r1.put(r8, r6)     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r6 = "lat"
            r1.put(r6, r4)     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r4 = "lnglat"
            r0.put(r4, r1)     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r1 = "citycode"
            r0.put(r1, r3)     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r1 = b(r2)     // Catch:{ JSONException -> 0x00b2 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x00b2 }
            if (r2 != 0) goto L_0x00a9
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00b2 }
            r2.<init>(r1)     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r1 = "platform"
            java.lang.String r3 = "amap"
            r2.put(r1, r3)     // Catch:{ JSONException -> 0x00b2 }
            java.lang.String r1 = "extData"
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x00b2 }
        L_0x00a9:
            java.lang.String r1 = "content"
            r12.put(r1, r0)     // Catch:{ JSONException -> 0x00b2 }
            r11.callback(r12)     // Catch:{ JSONException -> 0x00b2 }
            return
        L_0x00b2:
            r11 = move-exception
            r11.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bky.a(org.json.JSONObject, wa):void");
    }

    public static synchronized boolean a(String str, String str2) {
        synchronized (bky.class) {
            if (AMapPageUtil.getAppContext() == null) {
                return false;
            }
            new MapSharePreference((String) "share_bike_user_info_sp_data").putStringValue(str, str2);
            return true;
        }
    }

    public static synchronized boolean a(String str, String str2, String str3) {
        synchronized (bky.class) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("appkey", str);
                jSONObject.put("userid", str2);
                jSONObject.put("token", str3);
                a(str, jSONObject.toString());
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static synchronized boolean a(String str) {
        synchronized (bky.class) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (AMapPageUtil.getAppContext() == null) {
                return false;
            }
            new MapSharePreference((String) "share_bike_user_info_sp_data").putStringValue("tag_ofo_appkey_action", str);
            return true;
        }
    }

    private static synchronized String c() {
        String str;
        synchronized (bky.class) {
            str = "";
            if (AMapPageUtil.getAppContext() != null) {
                str = new MapSharePreference((String) "share_bike_user_info_sp_data").getStringValue("tag_ofo_appkey_action", "");
            }
        }
        return str;
    }

    private static synchronized String b(String str) {
        synchronized (bky.class) {
            if (TextUtils.isEmpty(str)) {
                String d = d();
                return d;
            } else if (AMapPageUtil.getAppContext() == null) {
                return "";
            } else {
                String stringValue = new MapSharePreference((String) "share_bike_user_info_sp_data").getStringValue(str, "");
                return stringValue;
            }
        }
    }

    private static synchronized String d() {
        String jSONObject;
        synchronized (bky.class) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("token", c("share_bike_token_id"));
                jSONObject2.put("userid", c("share_bike_user_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jSONObject = jSONObject2.toString();
        }
        return jSONObject;
    }

    private static synchronized String c(String str) {
        String str2;
        synchronized (bky.class) {
            str2 = "";
            if (AMapPageUtil.getAppContext() != null) {
                str2 = new MapSharePreference((String) "share_bike_sp_data").sharedPrefs().getString(str, "");
            }
        }
        return str2;
    }

    public static synchronized boolean b() {
        synchronized (bky.class) {
            if (AMapPageUtil.getAppContext() == null) {
                return false;
            }
            new MapSharePreference((String) "share_bike_user_info_sp_data").edit().clear().apply();
            return true;
        }
    }
}
