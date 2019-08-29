package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.drivecommon.tools.TripSpUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.b;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: re reason: default package */
/* compiled from: DrivingNavigationSPUtilImpl */
public final class re {
    public static boolean a() {
        return AMapAppGlobal.getApplication().getSharedPreferences(DriveSpUtil.SP_ONLINE_OFFLINE, 0).getBoolean("navi_config_online", true);
    }

    public static String a(Context context) {
        if (!TripSpUtil.getTripBroadCastState(context)) {
            return "";
        }
        if (DriveSpUtil.getInt(context, DriveSpUtil.BROADCAST_MODE, 2) == 2) {
            return context.getString(R.string.quickautonavi_car_setting_broadcast_mode_greenhand);
        }
        return context.getString(R.string.quickautonavi_car_setting_braodcast_mode_classic);
    }

    public static int b() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_BOARDCAST_TYPE_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 2;
    }

    public static void a(Context context, int i) {
        DriveSpUtil.setInt(context, DriveSpUtil.BROADCAST_MODE, i);
    }

    public static void a(String str) {
        ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_DIALECT_PLAY_NAME_KEY, str);
    }

    public static int b(Context context) {
        return DriveSpUtil.getInt(context, DriveSpUtil.DAY_NIGHT_MODE, 16);
    }

    public static int c() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_MAPVIEW_DAYNIGHT_KEY);
        if (TextUtils.isEmpty(motorConfigValue)) {
            return 0;
        }
        return (int) Float.parseFloat(motorConfigValue);
    }

    public static void b(Context context, int i) {
        DriveSpUtil.setInt(context, DriveSpUtil.DAY_NIGHT_MODE, i);
    }

    public static void a(int i) {
        ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_MAPVIEW_DAYNIGHT_KEY, String.valueOf(i));
    }

    public static int d() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_MAPVIEW_NAVIVIEW_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 1;
    }

    public static void a(Context context, boolean z) {
        DriveSpUtil.setBool(context, "NaviMapMode", z);
        tf.a(z);
    }

    public static void b(int i) {
        ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_MAPVIEW_NAVIVIEW_KEY, String.valueOf(i));
    }

    public static int e() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_BOARDCAST_CAMERA_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 1;
    }

    public static void b(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.PLAY_ELE_EYE, z);
    }

    public static int f() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_BOARDCAST_ROADINFO_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 1;
    }

    public static void c(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.PLAY_ROUTE_TRAFFIC, z);
    }

    public static int g() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_AUXILIARY_LOWBRIGHT_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 0;
    }

    public static void d(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.LIGHT_INTENSITY, z);
    }

    public static int h() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_MAPVIEW_SCALEZOOM_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 1;
    }

    public static void e(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.SCALE_AUTO_CHANGE, z);
    }

    public static void f(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.DOWNLOAD_INTERSECTION_OF_REAL_MAP, z);
    }

    public static int i() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_AUXILIARY_REALMAP_DOWNLOAD_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 1;
    }

    public static int j() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_AUXILIARY_3DREALMAP_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 1;
    }

    public static int k() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_TTS_MIXED_MUSIC_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 1;
    }

    public static void a(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("TTSMixedMusicMode", z);
    }

    public static boolean l() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("TTSMixedMusicMode", true);
    }

    public static void g(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.REAL_3D_NAVIGATION, z);
    }

    public static void h(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.CALLING_SPEAK_TTS, z);
    }

    public static int m() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_BOARDCAST_INCALLING_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 0;
    }

    public static void i(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.NAVIGATION_VOLUME_GAIN_SWITCH, z);
    }

    public static int n() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_BOARDCAST_IMPROVE_VOICE_KEY);
        if (!TextUtils.isEmpty(motorConfigValue)) {
            return Float.valueOf(motorConfigValue).intValue();
        }
        return 0;
    }

    public static void j(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.ROAD_GO_STRAIGHT, z);
    }

    public static void k(Context context, boolean z) {
        DriveSpUtil.setBool(context, DriveSpUtil.VOICE_CONTROL_SWITCH, z);
    }

    public static void a(long j, int i) {
        if (j == -1) {
            try {
                b((String) DriveSpUtil.NAVIGATION_TIME_AT_EXCEPTION, (String) "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(b.a, j);
            jSONObject.put(b.b, i);
            b((String) DriveSpUtil.NAVIGATION_TIME_AT_EXCEPTION, jSONObject.toString());
        }
    }

    public static void a(POI poi) {
        if (poi != null) {
            try {
                if (!(poi.getPoint() == null || poi.getPoint().getLatitude() == 0.0d || poi.getPoint().getLongitude() == 0.0d)) {
                    JSONObject jSONObject = new JSONObject();
                    a(jSONObject, b.c, poi);
                    b((String) DriveSpUtil.NAVIGATION_DESTINATION_AT_EXCEPTION, jSONObject.toString());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        b((String) DriveSpUtil.NAVIGATION_DESTINATION_AT_EXCEPTION, (String) "");
    }

    public static void a(List<POI> list) {
        if (list != null) {
            try {
                if (list.size() > 0) {
                    JSONObject jSONObject = new JSONObject();
                    a(jSONObject, b.d, list);
                    b((String) DriveSpUtil.NAVIGATION_POINTS_PASSBY_AT_EXCEPTION, jSONObject.toString());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        b((String) DriveSpUtil.NAVIGATION_POINTS_PASSBY_AT_EXCEPTION, (String) "");
    }

    public static String a(String str, String str2) {
        return new MapSharePreference((String) "SharedPreferences").getStringValue(str, str2);
    }

    public static String b(String str, String str2) {
        new MapSharePreference((String) "SharedPreferences").edit().putString(str, str2).apply();
        return str2;
    }

    public static boolean a(String str, boolean z) {
        return new MapSharePreference((String) "SharedPreferences").getBooleanValue(str, z);
    }

    public static boolean b(String str, boolean z) {
        new MapSharePreference((String) "SharedPreferences").edit().putBoolean(str, z).apply();
        return z;
    }

    private static void a(JSONObject jSONObject, String str, List<POI> list) {
        List<POI> list2 = list;
        if (list2 != null && list.size() > 0) {
            try {
                int size = list.size();
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < size; i++) {
                    POI poi = list2.get(i);
                    if (poi != null) {
                        JSONObject jSONObject2 = new JSONObject();
                        agd.a(jSONObject2, (String) "mId", poi.getId());
                        agd.a(jSONObject2, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName());
                        agd.a(jSONObject2, (String) "mAddr", poi.getAddr());
                        agd.a(jSONObject2, (String) "mx", poi.getPoint().x);
                        agd.a(jSONObject2, (String) "my", poi.getPoint().y);
                        ArrayList<GeoPoint> entranceList = poi.getEntranceList();
                        ArrayList<GeoPoint> exitList = poi.getExitList();
                        if (entranceList != null && entranceList.size() > 0) {
                            JSONArray jSONArray2 = new JSONArray();
                            int size2 = entranceList.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                JSONObject jSONObject3 = new JSONObject();
                                GeoPoint geoPoint = entranceList.get(i2);
                                jSONObject3.put("mEntranceX", geoPoint.x);
                                jSONObject3.put("mEntranceY", geoPoint.y);
                                jSONArray2.put(i2, jSONObject3);
                            }
                            agd.a(jSONObject2, (String) "mEntranceList", jSONArray2.toString());
                        }
                        if (exitList != null && exitList.size() > 0) {
                            JSONArray jSONArray3 = new JSONArray();
                            int size3 = exitList.size();
                            for (int i3 = 0; i3 < size3; i3++) {
                                JSONObject jSONObject4 = new JSONObject();
                                GeoPoint geoPoint2 = exitList.get(i3);
                                jSONObject4.put("mExitX", geoPoint2.x);
                                jSONObject4.put("mExitY", geoPoint2.y);
                                jSONArray3.put(i3, jSONObject4);
                            }
                            agd.a(jSONObject2, (String) "mExitList", jSONArray3.toString());
                        }
                        jSONArray.put(i, jSONObject2);
                    }
                }
                agd.a(jSONObject, str, jSONArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void a(JSONObject jSONObject, String str, POI poi) {
        if (poi != null && str != null) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                agd.a(jSONObject2, (String) "mId", iSearchPoiData.getId());
                agd.a(jSONObject2, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, iSearchPoiData.getName());
                agd.a(jSONObject2, (String) "mAddr", iSearchPoiData.getAddr());
                agd.a(jSONObject2, (String) "mLatitude", String.valueOf(iSearchPoiData.getPoint().getLatitude()));
                agd.a(jSONObject2, (String) "mLongitude", String.valueOf(iSearchPoiData.getPoint().getLongitude()));
                agd.a(jSONObject2, (String) "mEndPoiExtension", iSearchPoiData.getEndPoiExtension());
                agd.a(jSONObject2, (String) "mTransparent", iSearchPoiData.getTransparent());
                ArrayList<GeoPoint> entranceList = iSearchPoiData.getEntranceList();
                ArrayList<GeoPoint> exitList = iSearchPoiData.getExitList();
                if (entranceList != null && entranceList.size() > 0) {
                    JSONArray jSONArray = new JSONArray();
                    int size = entranceList.size();
                    for (int i = 0; i < size; i++) {
                        JSONObject jSONObject3 = new JSONObject();
                        GeoPoint geoPoint = entranceList.get(i);
                        jSONObject3.put("mEntranceX", geoPoint.x);
                        jSONObject3.put("mEntranceY", geoPoint.y);
                        jSONArray.put(i, jSONObject3);
                    }
                    agd.a(jSONObject2, (String) "mEntranceList", jSONArray.toString());
                }
                if (exitList != null && exitList.size() > 0) {
                    JSONArray jSONArray2 = new JSONArray();
                    int size2 = exitList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        JSONObject jSONObject4 = new JSONObject();
                        GeoPoint geoPoint2 = exitList.get(i2);
                        jSONObject4.put("mExitX", geoPoint2.x);
                        jSONObject4.put("mExitY", geoPoint2.y);
                        jSONArray2.put(i2, jSONObject4);
                    }
                    agd.a(jSONObject2, (String) "mExitList", jSONArray2.toString());
                }
                String parent = iSearchPoiData.getParent();
                String childType = iSearchPoiData.getChildType();
                if (!TextUtils.isEmpty(parent) && !TextUtils.isEmpty(childType)) {
                    agd.a(jSONObject2, (String) "mParent", parent);
                    agd.a(jSONObject2, (String) "mChildType", childType);
                }
                String fnona = iSearchPoiData.getFnona();
                if (!TextUtils.isEmpty(fnona)) {
                    agd.a(jSONObject2, (String) "mFnona", fnona);
                }
                String towardsAngle = iSearchPoiData.getTowardsAngle();
                if (!TextUtils.isEmpty(towardsAngle)) {
                    agd.a(jSONObject2, (String) "mTowardsAngle", towardsAngle);
                }
                String type = iSearchPoiData.getType();
                if (!TextUtils.isEmpty(type)) {
                    agd.a(jSONObject2, (String) "mTypeCode", type);
                }
                jSONObject.put(str, jSONObject2);
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
