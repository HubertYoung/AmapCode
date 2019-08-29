package com.amap.bundle.drivecommon.tools;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.b;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMemberNames
@KeepName
@KeepImplementations
public class DriveSpUtil {
    public static final String AVOID_FEE = "4";
    public static final String AVOID_HIGHT_WAY = "8";
    public static final String AVOID_JAM = "2";
    public static final String BROADCAST_MODE = "ReportMode";
    public static int BT_CHANNEL_MEDIA = 0;
    public static final String BT_CHANNEL_OPTION = "bt_channel_option";
    public static int BT_CHANNEL_TELEPHONY = 1;
    public static final String CALLING_SPEAK_TTS = "CallingSpeakTTS";
    public static final String CAR_DIRECTION = "NaviMapMode";
    public static final boolean CAR_HEAD_UP = true;
    public static final int CLASSIC_MODE = 1;
    public static final String COMMUTE_TAB_OFF_WORK_INDEX = "commute_tab_off_work_index";
    public static final String COMMUTE_TAB_TO_WORK_INDEX = "commute_tab_to_work_index";
    public static final String DAY_NIGHT_MODE = "NaviModeSet";
    public static final String DOWNLOAD_INTERSECTION_OF_REAL_MAP = "DownloadIntersectionOfRealMap";
    public static final String DRIVE_RADDER_CAMERA_PLAY = "drive_radder_camera_play";
    public static final String ETD_ENTRANCE_CLICKED = "etd_entrance_clicked";
    public static final String ETD_ENTRANCE_TIP_SHOW = "etd_entrance_tip_show";
    public static final String ETD_LAST_SHOW_TIME = "etd_last_show_time";
    public static final int GREENHAND_MODE = 2;
    public static final String HIGHWAY_FIRST = "16";
    public static final String HUD_RED_POINT = "RedPoint";
    public static final String KEY_CONFGI_SAFETY_SHARE_FUNCTION = "KEY_CONFGI_SAFETY_SHARE_FUNCTION";
    public static final String KEY_CONFGI_SAFETY_SHARE_POPUP_TIMEINTERVAL = "KEY_CONFGI_SAFETY_SHARE_POPUP_TIMEINTERVAL";
    public static final String KEY_CONFIG_AIR_PRESSURE_GAUG = "KEY_CONFIG_AIR_PRESSURE_GAUG";
    public static final String KEY_CONFIG_CAR_NAVI_TRAFFIC = "KEY_CONFIG_CAR_NAVI_TRAFFIC";
    public static final String KEY_CONFIG_CAR_PLATE_LAST_SETTING_TIME = "KEY_CONFIG_CAR_PLATE_LAST_SETTING_TIME";
    public static final String KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_LAST_NOTICE_TIME = "KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_LAST_NOTICE_TIME";
    public static final String KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_NOTICE_COUNT = "KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_NOTICE_COUNT";
    public static final String KEY_CONFIG_CAR_PLATE_SETTING_SHOW_COUNT = "KEY_CONFIG_CAR_PLATE_SETTING_SHOW_COUNT";
    public static final String KEY_CONFIG_DIALECT_VOICE_OPEN = "KEY_CONFIG_DIALECT_VOICE_OPEN";
    public static final String KEY_CONFIG_DRIVE_RADDAR_CAMERA_PLAY = "KEY_CONFIG_DRIVE_RADDAR_CAMERA_PLAY";
    public static final String KEY_CONFIG_IS_SUPPORT_3D = "KEY_CONFIG_IS_SUPPORT_3D";
    public static final String KEY_CONFIG_MAP_TRAFFIC = "KEY_CONFIG_MAP_TRAFFIC";
    public static final String KEY_CONFIG_NORESPONSIBILITY_SHOWN = "KEY_CONFIG_NORESPONSIBILITY_SHOWN";
    public static final String KEY_CONFIG_SAFETY_DESTINATION = "KEY_CONFIG_SAFETY_DESTINATION";
    public static final String KEY_CONFIG_SAFETY_REPORTID = "KEY_CONFIG_SAFETY_REPORTID";
    public static final String KEY_CONFIG_SAFETY_SHARE_POPUP_ALL = "KEY_CONFIG_SAFETY_SHARE_POPUP_ALL";
    public static final String KEY_CONFIG_SAFETY_SHARE_POPUP_OVER_100KM = "KEY_CONFIG_SAFETY_SHARE_POPUP_OVER_100KM";
    public static final String KEY_CONFIG_SAFETY_SHARE_STATE = "KEY_CONFIG_SAFETY_SHARE_STATE";
    public static final String KEY_CONFIG_SAFETY_SILENCE = "KEY_CONFIG_SAFETY_SILENCE";
    public static final String KEY_CONFIG_SAFETY_TIMEINTERVAL = "KEY_CONFIG_SAFETY_TIMEINTERVAL";
    public static final String KEY_CONFIG_USER_INFO_CIFA = "KEY_CONFIG_USER_INFO_CIFA";
    public static final String KEY_CRUISE_CONFIG_ENABLE_SILENCE = "KEY_CRUISE_CONFIG_ENABLE_SILENCE";
    public static final String KEY_CRUISE_CONFIG_SHOW_CAMERA_LAYER = "KEY_CRUISE_CONFIG_SHOW_CAMERA_LAYER";
    public static final String KEY_CRUISE_CONFIG_SHOW_TRAFFIC = "KEY_CRUISE_CONFIG_SHOW_TRAFFIC";
    public static final String KEY_CRUISE_SETUP_CAMERA_BROADCAST = "KEY_CRUISE_SETUP_CAMERA_BROADCAST";
    public static final String KEY_CRUISE_SETUP_SAFE_REMIND = "KEY_CRUISE_SETUP_SAFE_REMIND";
    public static final String KEY_CRUISE_SETUP_TRAFFIC_BROADCAST = "KEY_CRUISE_SETUP_TRAFFIC_BROADCAST";
    public static final String KEY_REAL_DAY_NIGHT_MODE = "KEY_REAL_DAY_NIGHT_MODE";
    public static final String KEY_ROUTE_BOARD_RED_POINT_TIP = "KEY_ROUTE_BOARD_RED_POINT_TIP";
    public static final String KEY_SETUP_ACCEPT_BOARD_CALLING = "KEY_SETUP_ACCEPT_BOARD_CALLING";
    public static final String KEY_SETUP_AROUND_SEARCH_ATM = "KEY_SETUP_AROUND_SEARCH_ATM";
    public static final String KEY_SETUP_AROUND_SEARCH_GAS = "KEY_SETUP_AROUND_SEARCH_GAS";
    public static final String KEY_SETUP_AUXILIARY_TRAFFICT_BROADCAST = "KEY_SETUP_AUXILIARY_TRAFFICT_BROADCAST";
    public static final String KEY_SETUP_BT_SOUND_CHANNEL = "KEY_SETUP_BT_SOUND_CHANNEL";
    public static final String KEY_SETUP_CAR_HEADER_UP = "KEY_SETUP_CAR_HEADER_UP";
    public static final String KEY_SETUP_CAR_PATH_METHOD = "KEY_SETUP_CAR_PATH_METHOD";
    public static final String KEY_SETUP_CAR_RESTRICT_STATE = "KEY_SETUP_CAR_RESTRICT_STATE";
    public static final String KEY_SETUP_CROSS_REAL_DOWNLOAD = "KEY_SETUP_CROSS_REAL_DOWNLOAD";
    public static final String KEY_SETUP_DAY_NIGHT_CHOICE = "KEY_SETUP_DAY_NIGHT_CHOICE";
    public static final String KEY_SETUP_DIALECT_PLAY_NAME = "KEY_SETUP_DIALECT_PLAY_NAME";
    public static final String KEY_SETUP_DIALECT_SRC_CODE = "KEY_SETUP_DIALECT_SRC_CODE";
    public static final String KEY_SETUP_ENABLE_CAMERA_BROADCAST = "KEY_SETUP_ENABLE_CAMERA_BROADCAST";
    public static final String KEY_SETUP_INCREASE_TTS_VOLUME = "KEY_SETUP_INCREASE_TTS_VOLUME";
    public static final String KEY_SETUP_INTELLIGENT_ZOOM_LEVEL = "KEY_SETUP_INTELLIGENT_ZOOM_LEVEL";
    public static final String KEY_SETUP_IS_EAGLEYE_MODE = "KEY_SETUP_IS_EAGLEYE_MODE";
    public static final String KEY_SETUP_LOW_LIGHT_NAVI = "KEY_SETUP_LOW_LIGHT_NAVI";
    public static final String KEY_SETUP_MAP_DIRECT_MODE = "KEY_SETUP_MAP_DIRECT_MODE";
    public static final String KEY_SETUP_OFFLINE_PRIORITY = "KEY_SETUP_OFFLINE_PRIORITY";
    public static final String KEY_SETUP_REAL_3D_NAVI = "KEY_SETUP_REAL_3D_NAVI";
    public static final String KEY_SETUP_RESTRICT_CAR_INFO = "KEY_SETUP_RESTRICT_CAR_INFO";
    public static final String KEY_SETUP_RESTRICT_TRUCK_INFO = "KEY_SETUP_RESTRICT_TRUCK_INFO";
    public static final String KEY_SETUP_SHOW_TMC_GUIDE = "KEY_SETUP_SHOW_TMC_GUIDE";
    public static final String KEY_SETUP_TRUCK_PATH_METHOD = "KEY_SETUP_TRUCK_PATH_METHOD";
    public static final String KEY_SETUP_TRUCK_RESTRICT_STATE = "KEY_SETUP_TRUCK_RESTRICT_STATE";
    public static final String KEY_SETUP_TRUCK_WEIGHT_RESTRICT_STATE = "KEY_SETUP_TRUCK_WEIGHT_RESTRICT_STATE";
    public static final String KEY_SETUP_TTS_MIXD_MUSIC = "KEY_SETUP_TTS_MIXD_MUSIC";
    public static final String KEY_SETUP_VOICE_BOARD_TYPE = "KEY_SETUP_VOICE_BOARD_TYPE";
    public static final String KEY_SETUP_VOICE_CONTROL_SWITCH = "KEY_SETUP_VOICE_CONTROL_SWITCH";
    public static final String KEY_VOICE_GUIDE_IS_SHOWN = "KEY_VOICE_GUIDE_IS_SHOWN";
    public static final String KEY_VUI_AUDIO_PERMISSION_DLG_COUNT = "KEY_VUI_AUDIO_PERMISSION_DLG_COUNT";
    public static final String KEY_VUI_AUDIO_PERMISSION_DLG_TIME = "KEY_VUI_AUDIO_PERMISSION_DLG_TIME";
    public static final String KEY_VUI_SWITCH_TOAST_COUNT = "KEY_VUI_SWITCH_TOAST_COUNT";
    public static final String KEY_VUI_SWITCH_TOAST_TIME = "KEY_VUI_SWITCH_TOAST_TIME";
    public static final String LIGHT_INTENSITY = "LightnessControl";
    public static final String MOTOR_AVOID_SWITCH = "motor_avoid_switch";
    public static final String MOTOR_PATH_AUXILIARY_3DREALMAP_KEY = "MOTOR_PATH_AUXILIARY_3DREALMAP_KEY";
    public static final String MOTOR_PATH_AUXILIARY_LOWBRIGHT_KEY = "MOTOR_PATH_AUXILIARY_LOWBRIGHT_KEY";
    public static final String MOTOR_PATH_AUXILIARY_REALMAP_DOWNLOAD_KEY = "MOTOR_PATH_AUXILIARY_REALMAP_DOWNLOAD_KEY";
    public static final String MOTOR_PATH_BOARDCAST_CAMERA_KEY = "MOTOR_PATH_BOARDCAST_CAMERA_KEY";
    public static final String MOTOR_PATH_BOARDCAST_IMPROVE_VOICE_KEY = "MOTOR_PATH_BOARDCAST_IMPROVE_VOICE_KEY";
    public static final String MOTOR_PATH_BOARDCAST_INCALLING_KEY = "MOTOR_PATH_BOARDCAST_INCALLING_KEY";
    public static final String MOTOR_PATH_BOARDCAST_ROADINFO_KEY = "MOTOR_PATH_BOARDCAST_ROADINFO_KEY";
    public static final String MOTOR_PATH_BOARDCAST_TYPE_KEY = "MOTOR_PATH_BOARDCAST_TYPE_KEY";
    public static final String MOTOR_PATH_DIALECT_PLAY_NAME_KEY = "MOTOR_PATH_DIALECT_PLAY_NAME";
    public static final String MOTOR_PATH_HEADER_UP = "MOTOR_PATH_HEADER_UP";
    public static final String MOTOR_PATH_LIMIT_KEY = "MOTOR_PATH_LIMIT_KEY";
    public static final String MOTOR_PATH_MAPVIEW_DAYNIGHT_KEY = "MOTOR_PATH_MAPVIEW_DAYNIGHT_KEY";
    public static final String MOTOR_PATH_MAPVIEW_NAVIVIEW_KEY = "MOTOR_PATH_MAPVIEW_NAVIVIEW_KEY";
    public static final String MOTOR_PATH_MAPVIEW_SCALEZOOM_KEY = "MOTOR_PATH_MAPVIEW_SCALEZOOM_KEY";
    public static final String MOTOR_PATH_NAVI_TRAFFIC = "MOTOR_PATH_NAVI_TRAFFIC";
    public static final String MOTOR_PATH_NUM = "MOTOR_PATH_NUM";
    public static final String MOTOR_PATH_PREFERENCE = "MOTOR_PATH_PREFERENCE";
    public static final String MOTOR_PATH_RESTRICT_CAR_INFO = "MOTOR_PATH_RESTRICT_CAR_INFO";
    public static final String MOTOR_PATH_SAFETY_SILENCE = "MOTOR_PATH_SAFETY_SILENCE";
    public static final String MOTOR_PATH_SHOW_TMC_GUIDE = "MOTOR_PATH_SHOW_TMC_GUIDE";
    public static final String MOTOR_PATH_TTS_MIXED_MUSIC_KEY = "MOTOR_PATH_TTS_MIXED_MUSIC_KEY";
    public static final String MOTOR_SETUP_INFO = "MOTOR_SETUP_INFO";
    public static final String NAMESPACE_CAR_ADAPTER = "NAMESPACE_CAR_ADAPTER";
    public static final String NAMESPACE_MOTOR_SETTING = SharePreferenceName.user_route_method_info.toString();
    public static final String NAVIGATION_DESTINATION_AT_EXCEPTION = "navigation_destination_at_exception";
    public static final String NAVIGATION_POINTS_PASSBY_AT_EXCEPTION = "navigation_points_passby_at_exception";
    public static final String NAVIGATION_TIME_AT_EXCEPTION = "navigation_time_at_exception";
    public static final String NAVIGATION_VOICE_CONTROL = "navigation_voice_control";
    public static final String NAVIGATION_VOLUME_GAIN_SWITCH = "navigation_volume_gain_switch";
    public static final String NAVIMODE = "3Dperspective";
    public static final String NAVI_MAP_MODE = "NaviMapMode";
    public static final boolean NORTH_HEAD_UP = false;
    public static final boolean OFFLINE_FIRST = false;
    public static final boolean ONLINE_FIRST = true;
    public static final String PARKING_RECOMMEND = "RecommendPark";
    public static final boolean PER2D = false;
    public static final boolean PER3D = true;
    public static final String PLAY_ELE_EYE = "PlayEleEye";
    public static final String PLAY_ROUTE_TRAFFIC = "play_route_traffic";
    public static final String RDCAMERA_VIOLATION_CONDITIONS_TVMD5 = "rdcamera_violation_conditions_tvmd5";
    public static final String REAL_3D_NAVIGATION = "real_3d_navigation";
    public static final String ROAD_GO_STRAIGHT = "RoadGoAlong";
    public static final String ROAD_STATUS = "RoadStatus";
    public static final String SAFE_HOME_END_P20_X = "safe_home_end_p20_x";
    public static final String SAFE_HOME_END_P20_Y = "safe_home_end_p20_y";
    public static final String SAFE_HOME_SHARE_END = "safe_home_share_end";
    public static final String SAFE_HOME_SHARE_ID = "safe_home_share_id";
    public static final String SAFE_HOME_SHARE_ID_782 = "safe_home_share_id_782";
    public static final String SAFE_HOME_SHOW_DEMO_PICTURE = "safe_home_show_demo_picture";
    public static final String SAFE_HOME_UPLOAD_TIME = "safe_home_upload_time";
    public static final String SCALE_AUTO_CHANGE = "ScaleAutoChange";
    public static final String SEARCH_ROUTE_IN_NET = "navi_config_online";
    public static final String SHOW_SCENE_RESULT_FIRST = "show_scene_result_first";
    public static final String SHOW_TMC_CHART = "showtmcchart";
    public static final String SP_ONLINE_OFFLINE = "Trip_Config";
    public static final String SP_ROUTE_PREFER = "user_route_method_info";
    public static final String TMC_MODE = "TmcMode";
    public static final String TRAFFIC_MODE = "traffic";
    public static final String TRUCK_AVOID_SWITCH = "truck_avoid_switch";
    public static final String TRUCK_AVOID_WEIGHT = "truck_avoid_weight";
    public static final String TRUCK_GUIDE = "truck_guide";
    public static final String TRUCK_METHOD = "truck_method";
    public static final String TTS_MIXED_MUSIC_MODE = "TTSMixedMusicMode";
    public static final float VALUE_DAY_NIGHT_MODE_AUTO = 0.0f;
    public static final float VALUE_DAY_NIGHT_MODE_DAY = 1.0f;
    public static final float VALUE_DAY_NIGHT_MODE_NIGHT = 2.0f;
    public static final String VALUE_REAL_DAY_NIGHT_MODE_DAY = "1";
    public static final String VALUE_REAL_DAY_NIGHT_MODE_NIGHT = "0";
    public static final String VALUE_UNDIFINED = "";
    public static final String VOICE_CONTROL_SWITCH = "vcs_switch";
    public static final String need_guide_motor = "sp_motor_guide";
    public static final String need_guide_truck = "need_guide_truck";

    public static String getString(Context context, String str, String str2) {
        return new MapSharePreference((String) "SharedPreferences").getStringValue(str, str2);
    }

    public static String setString(Context context, String str, String str2) {
        new MapSharePreference((String) "SharedPreferences").edit().putString(str, str2).apply();
        return str2;
    }

    public static int getInt(Context context, String str, int i) {
        return new MapSharePreference((String) "SharedPreferences").getIntValue(str, i);
    }

    public static int setInt(Context context, String str, int i) {
        new MapSharePreference((String) "SharedPreferences").edit().putInt(str, i).apply();
        return i;
    }

    public static long getLong(Context context, String str, long j) {
        return new MapSharePreference((String) "SharedPreferences").getLongValue(str, j);
    }

    public static long setLong(Context context, String str, long j) {
        new MapSharePreference((String) "SharedPreferences").edit().putLong(str, j).apply();
        return j;
    }

    public static boolean getBool(Context context, String str, boolean z) {
        return new MapSharePreference((String) "SharedPreferences").getBooleanValue(str, z);
    }

    public static boolean setBool(Context context, String str, boolean z) {
        new MapSharePreference((String) "SharedPreferences").edit().putBoolean(str, z).apply();
        return z;
    }

    private static void putPOIToJson(JSONObject jSONObject, String str, ArrayList<POI> arrayList) {
        JSONObject jSONObject2 = jSONObject;
        ArrayList<POI> arrayList2 = arrayList;
        if (arrayList2 != null && arrayList.size() > 0 && jSONObject2 != null) {
            try {
                int size = arrayList.size();
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < size; i++) {
                    POI poi = arrayList2.get(i);
                    JSONObject jSONObject3 = new JSONObject();
                    agd.a(jSONObject3, (String) "mId", poi.getId());
                    agd.a(jSONObject3, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName());
                    agd.a(jSONObject3, (String) "mAddr", poi.getAddr());
                    agd.a(jSONObject3, (String) "mx", poi.getPoint().x);
                    agd.a(jSONObject3, (String) "my", poi.getPoint().y);
                    agd.a(jSONObject3, (String) "mEndPoiExtension", poi.getEndPoiExtension());
                    agd.a(jSONObject3, (String) "mTransparent", poi.getTransparent());
                    ArrayList<GeoPoint> entranceList = poi.getEntranceList();
                    ArrayList<GeoPoint> exitList = poi.getExitList();
                    if (entranceList != null && entranceList.size() > 0) {
                        JSONArray jSONArray2 = new JSONArray();
                        int size2 = entranceList.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            JSONObject jSONObject4 = new JSONObject();
                            GeoPoint geoPoint = entranceList.get(i2);
                            jSONObject4.put("mEntranceX", geoPoint.x);
                            jSONObject4.put("mEntranceY", geoPoint.y);
                            jSONArray2.put(i2, jSONObject4);
                        }
                        jSONObject3.put("mEntranceList", jSONArray2);
                    }
                    if (exitList != null && exitList.size() > 0) {
                        JSONArray jSONArray3 = new JSONArray();
                        int size3 = exitList.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            JSONObject jSONObject5 = new JSONObject();
                            GeoPoint geoPoint2 = exitList.get(i3);
                            jSONObject5.put("mExitX", geoPoint2.x);
                            jSONObject5.put("mExitY", geoPoint2.y);
                            jSONArray3.put(i3, jSONObject5);
                        }
                        jSONObject3.put("mExitList", jSONArray3);
                    }
                    jSONArray.put(i, jSONObject3);
                }
                agd.a(jSONObject2, str, jSONArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static POI buildPoiFromJson(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null) {
            return null;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            if (jSONObject2 == null) {
                return null;
            }
            POI createPOI = POIFactory.createPOI();
            ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
            iSearchPoiData.setId(agd.e(jSONObject2, "mId"));
            iSearchPoiData.setName(agd.e(jSONObject2, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
            iSearchPoiData.setAddr(agd.e(jSONObject2, "mAddr"));
            iSearchPoiData.setPoint(new GeoPoint());
            if (!jSONObject2.has("mx") || !jSONObject2.has("my")) {
                iSearchPoiData.getPoint().setLonLat(agd.b(jSONObject2, "mLongitude"), agd.b(jSONObject2, "mLatitude"));
            } else {
                iSearchPoiData.getPoint().x = agd.a(jSONObject2, "mx");
                iSearchPoiData.getPoint().y = agd.a(jSONObject2, "my");
            }
            iSearchPoiData.setEndPoiExtension(agd.e(jSONObject2, "mEndPoiExtension"));
            iSearchPoiData.setTransparent(agd.e(jSONObject2, "mTransparent"));
            String e = agd.e(jSONObject2, "mEntranceList");
            if (!TextUtils.isEmpty(e)) {
                JSONArray jSONArray = new JSONArray(e);
                ArrayList arrayList = new ArrayList();
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    arrayList.add(new GeoPoint(jSONObject3.getInt("mEntranceX"), jSONObject3.getInt("mEntranceY")));
                }
                iSearchPoiData.setEntranceList(arrayList);
            }
            String e2 = agd.e(jSONObject2, "mExitList");
            if (!TextUtils.isEmpty(e2)) {
                JSONArray jSONArray2 = new JSONArray(e2);
                ArrayList arrayList2 = new ArrayList();
                int length2 = jSONArray2.length();
                for (int i2 = 0; i2 < length2; i2++) {
                    JSONObject jSONObject4 = jSONArray2.getJSONObject(i2);
                    arrayList2.add(new GeoPoint(jSONObject4.getInt("mExitX"), jSONObject4.getInt("mExitY")));
                }
                iSearchPoiData.setExitList(arrayList2);
            }
            iSearchPoiData.setParent(agd.e(jSONObject2, "mParent"));
            iSearchPoiData.setChildType(agd.e(jSONObject2, "mChildType"));
            iSearchPoiData.setFnona(agd.e(jSONObject2, "mFnona"));
            iSearchPoiData.setTowardsAngle(agd.e(jSONObject2, "mTowardsAngle"));
            iSearchPoiData.setType(agd.e(jSONObject2, "mTypeCode"));
            return createPOI;
        } catch (JSONException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private static void putPOIToJson(JSONObject jSONObject, String str, POI poi) {
        if (poi != null && jSONObject != null && str != null) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                agd.a(jSONObject2, (String) "mId", poi.getId());
                agd.a(jSONObject2, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName());
                agd.a(jSONObject2, (String) "mAddr", poi.getAddr());
                agd.a(jSONObject2, (String) "mx", poi.getPoint().x);
                agd.a(jSONObject2, (String) "my", poi.getPoint().y);
                agd.a(jSONObject2, (String) "mEndPoiExtension", poi.getEndPoiExtension());
                agd.a(jSONObject2, (String) "mTransparent", poi.getTransparent());
                ArrayList<GeoPoint> entranceList = poi.getEntranceList();
                ArrayList<GeoPoint> exitList = poi.getExitList();
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
                    jSONObject2.put("mEntranceList", jSONArray);
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
                    jSONObject2.put("mExitList", jSONArray2);
                }
                jSONObject.put(str, jSONObject2);
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }

    private static ArrayList<POI> buildMidPois(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null) {
            return null;
        }
        try {
            String e = agd.e(jSONObject, str);
            ArrayList<POI> arrayList = new ArrayList<>();
            if (!TextUtils.isEmpty(e)) {
                JSONArray jSONArray = new JSONArray(e);
                if (jSONArray.length() > 0) {
                    int length = jSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        if (jSONObject2 != null) {
                            POI createPOI = POIFactory.createPOI();
                            createPOI.setId(agd.e(jSONObject2, "mId"));
                            createPOI.setName(agd.e(jSONObject2, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
                            createPOI.setAddr(agd.e(jSONObject2, "mAddr"));
                            createPOI.setPoint(new GeoPoint());
                            createPOI.getPoint().x = agd.a(jSONObject2, "mx");
                            createPOI.getPoint().y = agd.a(jSONObject2, "my");
                            createPOI.setEndPoiExtension(agd.e(jSONObject2, "mEndPoiExtension"));
                            createPOI.setTransparent(agd.e(jSONObject2, "mTransparent"));
                            String e2 = agd.e(jSONObject2, "mEntranceList");
                            if (!TextUtils.isEmpty(e2)) {
                                JSONArray jSONArray2 = new JSONArray(e2);
                                ArrayList arrayList2 = new ArrayList();
                                int length2 = jSONArray2.length();
                                for (int i2 = 0; i2 < length2; i2++) {
                                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                                    arrayList2.add(new GeoPoint(jSONObject3.getInt("mEntranceX"), jSONObject3.getInt("mEntranceY")));
                                }
                                createPOI.setEntranceList(arrayList2);
                            }
                            String e3 = agd.e(jSONObject2, "mExitList");
                            if (!TextUtils.isEmpty(e3)) {
                                JSONArray jSONArray3 = new JSONArray(e3);
                                ArrayList arrayList3 = new ArrayList();
                                int length3 = jSONArray3.length();
                                for (int i3 = 0; i3 < length3; i3++) {
                                    JSONObject jSONObject4 = jSONArray3.getJSONObject(i3);
                                    arrayList3.add(new GeoPoint(jSONObject4.getInt("mExitX"), jSONObject4.getInt("mExitY")));
                                }
                                createPOI.setExitList(arrayList3);
                            }
                            arrayList.add(createPOI);
                        }
                    }
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getNavigationTimeAtException(Context context) {
        return getString(context, NAVIGATION_TIME_AT_EXCEPTION, "");
    }

    public static void saveCurrentNavigation(Context context, long j, int i, POI poi, ArrayList<POI> arrayList) {
        try {
            Editor edit = new MapSharePreference((String) "SharedPreferences").edit();
            if (poi == null || poi.getPoint() == null || poi.getPoint().x == 0 || poi.getPoint().y == 0) {
                edit.putString(NAVIGATION_DESTINATION_AT_EXCEPTION, "");
            } else {
                JSONObject jSONObject = new JSONObject();
                putPOIToJson(jSONObject, b.c, poi);
                edit.putString(NAVIGATION_DESTINATION_AT_EXCEPTION, jSONObject.toString());
            }
            if (arrayList == null || arrayList.size() <= 0) {
                edit.putString(NAVIGATION_POINTS_PASSBY_AT_EXCEPTION, "");
            } else {
                JSONObject jSONObject2 = new JSONObject();
                putPOIToJson(jSONObject2, b.d, arrayList);
                edit.putString(NAVIGATION_POINTS_PASSBY_AT_EXCEPTION, jSONObject2.toString());
            }
            if (j == -1) {
                edit.putString(NAVIGATION_TIME_AT_EXCEPTION, "");
                edit.apply();
                return;
            }
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(b.a, j);
            jSONObject3.put(b.b, i);
            edit.putString(NAVIGATION_TIME_AT_EXCEPTION, jSONObject3.toString());
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNavigationPointsPassbyAtException(Context context) {
        return getString(context, NAVIGATION_POINTS_PASSBY_AT_EXCEPTION, "");
    }

    public static String getNavigationDestinationAtException(Context context) {
        return getString(context, NAVIGATION_DESTINATION_AT_EXCEPTION, "");
    }

    public static POI getDestinationAtException(Context context) {
        try {
            String navigationDestinationAtException = getNavigationDestinationAtException(context);
            if (TextUtils.isEmpty(navigationDestinationAtException)) {
                return null;
            }
            return buildPoiFromJson(new JSONObject(navigationDestinationAtException), b.c);
        } catch (Exception unused) {
            return null;
        }
    }

    public static ArrayList<POI> getPointsPassbyAtException(Context context) {
        try {
            String navigationPointsPassbyAtException = getNavigationPointsPassbyAtException(context);
            if (TextUtils.isEmpty(navigationPointsPassbyAtException)) {
                return null;
            }
            return buildMidPois(new JSONObject(navigationPointsPassbyAtException), b.d);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getSafeHomeShareId(Context context) {
        return getString(context, SAFE_HOME_SHARE_ID, "");
    }

    public static void setSafeHomeShareId(Context context, String str) {
        setString(context, SAFE_HOME_SHARE_ID, str);
    }

    public static String getSafeHomeShareIdIn782(Context context) {
        return getString(context, SAFE_HOME_SHARE_ID_782, "");
    }

    public static void setSafeHomeShareIdIn782(Context context, String str) {
        setString(context, SAFE_HOME_SHARE_ID_782, str);
    }

    public static int getSafeHomeEndp20x(Context context) {
        return getInt(context, SAFE_HOME_END_P20_X, 0);
    }

    public static void setSafeHomeEndp20x(Context context, int i) {
        setInt(context, SAFE_HOME_END_P20_X, i);
    }

    public static int getSafeHomeEndp20y(Context context) {
        return getInt(context, SAFE_HOME_END_P20_Y, 0);
    }

    public static void setSafeHomeEndp20y(Context context, int i) {
        setInt(context, SAFE_HOME_END_P20_Y, i);
    }

    public static long getSafeHomeUploadTime(Context context) {
        return getLong(context, SAFE_HOME_UPLOAD_TIME, 0);
    }

    public static void setSafeHomeUploadTime(Context context, long j) {
        setLong(context, SAFE_HOME_UPLOAD_TIME, j);
    }

    public static boolean getSafeHomeShareEnd(Context context) {
        return getBool(context, SAFE_HOME_SHARE_END, false);
    }

    public static void setSafeHomeShareEnd(Context context, boolean z) {
        setBool(context, SAFE_HOME_SHARE_END, z);
    }

    public static boolean shouldRouteOffline() {
        return !NetworkReachability.a() && !getSearchRouteInNetMode(AMapAppGlobal.getApplication());
    }

    public static boolean getSearchRouteInNetMode(Context context) {
        return context.getSharedPreferences(SP_ONLINE_OFFLINE, 0).getBoolean("navi_config_online", true);
    }

    public static void setSearchRouteInNeMode(Context context, boolean z) {
        context.getSharedPreferences(SP_ONLINE_OFFLINE, 0).edit().putBoolean("navi_config_online", z).apply();
    }

    public static boolean getFirstShowSceneResultToast(Context context) {
        return getBool(context, SHOW_SCENE_RESULT_FIRST, false);
    }

    public static void setShowSceneResultFirst(Context context, boolean z) {
        setBool(context, SHOW_SCENE_RESULT_FIRST, z);
    }

    public static int getCommuteTabToWorkIndex(Context context) {
        return getInt(context, COMMUTE_TAB_TO_WORK_INDEX, 0);
    }

    public static void setCommuteTabToWorkIndex(Context context, int i) {
        setInt(context, COMMUTE_TAB_TO_WORK_INDEX, i);
    }

    public static int getCommuteTabOffWorkIndex(Context context) {
        return getInt(context, COMMUTE_TAB_OFF_WORK_INDEX, 0);
    }

    public static void setCommuteTabOffWorkIndex(Context context, int i) {
        setInt(context, COMMUTE_TAB_OFF_WORK_INDEX, i);
    }

    public static boolean getDriveRadderCameraPlay(Context context) {
        return getBool(context, DRIVE_RADDER_CAMERA_PLAY, false);
    }

    public static void setDriveRadderCameraPlay(Context context, boolean z) {
        setBool(context, DRIVE_RADDER_CAMERA_PLAY, z);
    }

    public static void removeKey(Context context, String str) {
        Editor edit = new MapSharePreference((String) "SharedPreferences").edit();
        edit.remove(str);
        edit.apply();
    }
}
