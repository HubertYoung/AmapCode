package com.autonavi.minimap.basemap.traffic;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import com.alipay.mobile.antui.basic.AUCardOptionView;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.ITrafficTopic;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrafficTopic extends Topic implements ITrafficTopic {
    public static final int ACCIDENT = 1050;
    public static final int ACCIDENT_BARRIER = 11012;
    public static final int ACCIDENT_CRASH = 11011;
    public static final int ACCIDENT_VEHICLE = 11010;
    public static final int ANNOUNCEMENT = 11070;
    public static final int CAMERA = 1105;
    public static final int CAMERA_TAG = 11071;
    public static final int CONSTRUCTION = 1065;
    public static final int CONTROL = 1085;
    public static final int CONTROL_CLOSE = 11050;
    public static final int CONTROL_CLOSE_CONSTRUCTION = 11056;
    public static final int CONTROL_CLOSE_ENTRY = 11053;
    public static final int CONTROL_CLOSE_EXIT = 11052;
    public static final int CONTROL_CLOSE_FOG = 11057;
    public static final int CONTROL_CLOSE_HAIL = 11061;
    public static final int CONTROL_CLOSE_ICE = 11064;
    public static final int CONTROL_CLOSE_MAJOR_ACCIDENT = 11055;
    public static final int CONTROL_CLOSE_ORDINARY_ACCIDENT = 11054;
    public static final int CONTROL_CLOSE_PONDING = 11062;
    public static final int CONTROL_CLOSE_RAIN = 11058;
    public static final int CONTROL_CLOSE_ROAD = 11051;
    public static final int CONTROL_CLOSE_SNOW = 11059;
    public static final int CONTROL_CLOSE_SNOWS = 11063;
    public static final int CONTROL_CLOSE_SUBSIDENCE = 11065;
    public static final int CONTROL_CONTROL = 11031;
    public static final int CONTROL_ICON = 1060;
    public static final int DANGER = 1075;
    public static final int DANGER_CHILD = 11060;
    public static final int EMERGENCY_EVENT = 1110;
    public static final int EMERGENCY_EVENT_TAG = 11072;
    public static final int EXCLUDE_SOURCE_TYPE_CHONGQING = 121;
    public static final List<String> GovSouceTypeList = Collections.unmodifiableList(new ArrayList<String>() {
        {
            add("1");
            add("4");
            add("7");
            add("10");
            add("12");
            for (int i = 111; i <= 200; i++) {
                if (i != 121) {
                    add(String.valueOf(i));
                }
            }
        }
    });
    public static final int JAM = 1055;
    public static final int JAM_CROWDED = 11021;
    public static final int JAM_SLOW = 11020;
    public static final int JAM_STILL = 11022;
    public static final int JAM_UNBLOCKED = 11023;
    public static final Map<Integer, Integer> LayerTag2Icon = Collections.unmodifiableMap(new HashMap<Integer, Integer>() {
        {
            put(Integer.valueOf(TrafficTopic.JAM_UNBLOCKED), Integer.valueOf(R.drawable.traffic_report_unblocked));
            put(Integer.valueOf(TrafficTopic.JAM_SLOW), Integer.valueOf(R.drawable.traffic_report_congestion));
            put(Integer.valueOf(TrafficTopic.JAM_CROWDED), Integer.valueOf(R.drawable.traffic_report_congestion));
            put(Integer.valueOf(TrafficTopic.JAM_STILL), Integer.valueOf(R.drawable.traffic_report_congestion));
            put(Integer.valueOf(TrafficTopic.POLICE_DRUNK), Integer.valueOf(R.drawable.traffic_report_police));
            put(Integer.valueOf(TrafficTopic.POLICE_CONTROL), Integer.valueOf(R.drawable.traffic_report_police));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ROAD), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_CONSTRUCTION), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ENTRY), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_EXIT), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_FOG), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_HAIL), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ICE), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_MAJOR_ACCIDENT), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ORDINARY_ACCIDENT), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_PONDING), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_RAIN), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_SNOW), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_SNOWS), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_SUBSIDENCE), Integer.valueOf(R.drawable.traffic_report_closure));
            put(Integer.valueOf(TrafficTopic.CONTROL_CONTROL), Integer.valueOf(R.drawable.traffic_report_control));
            put(Integer.valueOf(TrafficTopic.DANGER_CHILD), Integer.valueOf(R.drawable.traffic_report_danger));
            put(Integer.valueOf(TrafficTopic.SHIGONG), Integer.valueOf(R.drawable.traffic_report_process));
            put(Integer.valueOf(TrafficTopic.ANNOUNCEMENT), Integer.valueOf(R.drawable.traffic_report_announcement));
            put(Integer.valueOf(11100), Integer.valueOf(R.drawable.traffic_report_ponding));
            put(Integer.valueOf(TrafficTopic.CAMERA_TAG), Integer.valueOf(R.drawable.traffic_report_shijing));
            put(Integer.valueOf(TrafficTopic.EMERGENCY_EVENT_TAG), Integer.valueOf(R.drawable.traffic_report_danger));
            put(Integer.valueOf(TrafficTopic.ACCIDENT_VEHICLE), Integer.valueOf(R.drawable.traffic_report_trouble));
            put(Integer.valueOf(TrafficTopic.ACCIDENT_CRASH), Integer.valueOf(R.drawable.traffic_report_accident));
            put(Integer.valueOf(TrafficTopic.ACCIDENT_BARRIER), Integer.valueOf(R.drawable.report_not_well_road));
            put(Integer.valueOf(TrafficTopic.POLICE_LAW_ENFORCE), Integer.valueOf(R.drawable.traffic_report_police));
            put(Integer.valueOf(TrafficTopic.WEATHER_WIND_11080), Integer.valueOf(R.drawable.traffic_report_weather_11080));
            put(Integer.valueOf(TrafficTopic.WEATHER_WIND_11081), Integer.valueOf(R.drawable.traffic_report_weather_11081));
            put(Integer.valueOf(TrafficTopic.WEATHER_WIND_11082), Integer.valueOf(R.drawable.traffic_report_weather_11082));
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11083), Integer.valueOf(R.drawable.traffic_report_weather_11083));
            put(Integer.valueOf(TrafficTopic.WEATHER_ICE_11084), Integer.valueOf(R.drawable.traffic_report_weather_11084));
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11085), Integer.valueOf(R.drawable.traffic_report_weather_11085));
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11086), Integer.valueOf(R.drawable.traffic_report_weather_11086));
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11087), Integer.valueOf(R.drawable.traffic_report_weather_11087));
            put(Integer.valueOf(TrafficTopic.WEATHER_SNOW_11088), Integer.valueOf(R.drawable.traffic_report_weather_11088));
            put(Integer.valueOf(TrafficTopic.WEATHER_SNOW_11089), Integer.valueOf(R.drawable.traffic_report_weather_11089));
            put(Integer.valueOf(TrafficTopic.WEATHER_SNOW_11090), Integer.valueOf(R.drawable.traffic_report_weather_11090));
            put(Integer.valueOf(TrafficTopic.WEATHER_FOG_11091), Integer.valueOf(R.drawable.traffic_report_weather_11091));
            put(Integer.valueOf(TrafficTopic.WEATHER_FOG_11092), Integer.valueOf(R.drawable.traffic_report_weather_11092));
            put(Integer.valueOf(TrafficTopic.WEATHER_SAND_11093), Integer.valueOf(R.drawable.traffic_report_weather_11093));
            put(Integer.valueOf(TrafficTopic.WEATHER_LIGHT_11094), Integer.valueOf(R.drawable.traffic_report_weather_11094));
        }
    });
    public static final Map<Integer, String> LayerTag2Title = Collections.unmodifiableMap(new HashMap<Integer, String>() {
        {
            put(Integer.valueOf(TrafficTopic.JAM_UNBLOCKED), TrafficTopic.TAG_JAM_UNBLOCK);
            put(Integer.valueOf(TrafficTopic.JAM_SLOW), TrafficTopic.TAG_JAM_SLOW);
            put(Integer.valueOf(TrafficTopic.JAM_CROWDED), TrafficTopic.TAG_JAM_CROWDED);
            put(Integer.valueOf(TrafficTopic.JAM_STILL), TrafficTopic.TAG_JAM_STILL);
            put(Integer.valueOf(TrafficTopic.POLICE_DRUNK), TrafficTopic.TAG_POLICE_DRUNK);
            put(Integer.valueOf(TrafficTopic.POLICE_CONTROL), TrafficTopic.TAG_POLICE_CONTROL);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ROAD), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_CONSTRUCTION), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ENTRY), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_EXIT), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_FOG), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_HAIL), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ICE), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_MAJOR_ACCIDENT), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_ORDINARY_ACCIDENT), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_PONDING), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_RAIN), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_SNOW), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_SNOWS), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CLOSE_SUBSIDENCE), TrafficTopic.TAG_CONTROL_CLOSE);
            put(Integer.valueOf(TrafficTopic.CONTROL_CONTROL), TrafficTopic.TAG_CONTROL_CONTROL);
            put(Integer.valueOf(TrafficTopic.DANGER_CHILD), TrafficTopic.TAG_DANGER_CHILD);
            put(Integer.valueOf(TrafficTopic.SHIGONG), TrafficTopic.TAG_SHIGONG);
            put(Integer.valueOf(TrafficTopic.ANNOUNCEMENT), TrafficTopic.TAG_ANNOUNCEMENT);
            put(Integer.valueOf(11100), TrafficTopic.TAG_WATER);
            put(Integer.valueOf(TrafficTopic.CAMERA_TAG), TrafficTopic.TAG_CAMERA_CONTENT);
            put(Integer.valueOf(TrafficTopic.EMERGENCY_EVENT_TAG), TrafficTopic.TAG_EMERGENCY_EVENT_CONTENT);
            put(Integer.valueOf(TrafficTopic.ACCIDENT_VEHICLE), TrafficTopic.TAG_ACCIDENT_VEHICLE);
            put(Integer.valueOf(TrafficTopic.ACCIDENT_CRASH), TrafficTopic.TAG_ACCIDENT_CRASH);
            put(Integer.valueOf(TrafficTopic.ACCIDENT_BARRIER), TrafficTopic.TAG_ACCIDENT_BARRIER);
            put(Integer.valueOf(TrafficTopic.POLICE_LAW_ENFORCE), TrafficTopic.TAG_POLICE_LAW_ENFORCE);
            put(Integer.valueOf(TrafficTopic.WEATHER_WIND_11080), TrafficTopic.TAG_WEATHER_WIND_11080);
            put(Integer.valueOf(TrafficTopic.WEATHER_WIND_11081), TrafficTopic.TAG_WEATHER_WIND_11081);
            put(Integer.valueOf(TrafficTopic.WEATHER_WIND_11082), TrafficTopic.TAG_WEATHER_WIND_11082);
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11083), TrafficTopic.TAG_WEATHER_RAIN_11083);
            put(Integer.valueOf(TrafficTopic.WEATHER_ICE_11084), TrafficTopic.TAG_WEATHER_ICE_11084);
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11085), TrafficTopic.TAG_WEATHER_RAIN_11085);
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11086), TrafficTopic.TAG_WEATHER_RAIN_11086);
            put(Integer.valueOf(TrafficTopic.WEATHER_RAIN_11087), TrafficTopic.TAG_WEATHER_RAIN_11087);
            put(Integer.valueOf(TrafficTopic.WEATHER_SNOW_11088), TrafficTopic.TAG_WEATHER_SNOW_11088);
            put(Integer.valueOf(TrafficTopic.WEATHER_SNOW_11089), TrafficTopic.TAG_WEATHER_SNOW_11089);
            put(Integer.valueOf(TrafficTopic.WEATHER_SNOW_11090), TrafficTopic.TAG_WEATHER_SNOW_11090);
            put(Integer.valueOf(TrafficTopic.WEATHER_FOG_11091), TrafficTopic.TAG_WEATHER_FOG_11091);
            put(Integer.valueOf(TrafficTopic.WEATHER_FOG_11092), TrafficTopic.TAG_WEATHER_FOG_11092);
            put(Integer.valueOf(TrafficTopic.WEATHER_SAND_11093), TrafficTopic.TAG_WEATHER_SAND_11093);
            put(Integer.valueOf(TrafficTopic.WEATHER_LIGHT_11094), TrafficTopic.TAG_WEATHER_LIGHT_11094);
        }
    });
    public static final int MOOD = 1040;
    public static final int NOTIFY = 1080;
    public static final String OPERATION_COMPETITION = "10011:14";
    public static final String OPERATION_CONTROL = "10011:15";
    public static final String OPERATION_EMERGENCY = "10011:13";
    public static final String OPERATION_TRAFFIC_AFFECTAREA = "10011:18";
    public static final Map<String, Integer> OperationIcon = Collections.unmodifiableMap(new HashMap<String, Integer>() {
        {
            put(TrafficTopic.OPERATION_EMERGENCY, Integer.valueOf(R.drawable.traffic_operation_emergency));
            put(TrafficTopic.OPERATION_COMPETITION, Integer.valueOf(R.drawable.traffic_operation_competition));
            put(TrafficTopic.OPERATION_CONTROL, Integer.valueOf(R.drawable.traffic_operation_control));
            put(TrafficTopic.OPERATION_TRAFFIC_AFFECTAREA, Integer.valueOf(R.drawable.traffic_operation_report));
        }
    });
    public static final int POLICE = 1095;
    public static final int POLICE_CONTROL = 11030;
    public static final int POLICE_DRUNK = 11032;
    public static final int POLICE_LAW_ENFORCE = 11033;
    public static final int RESERVED_SOURCE_TYPE_MAX = 200;
    public static final int RESERVED_SOURCE_TYPE_MIN = 111;
    public static final int SHIGONG = 11040;
    public static final int SINA = 1070;
    public static final String SOURCE_TYPE_AMAP = "2";
    public static final String SOURCE_TYPE_ATI = "8";
    public static final String SOURCE_TYPE_BROADCAST = "9";
    public static final String SOURCE_TYPE_CAR = "5";
    public static final String SOURCE_TYPE_CHONGQING_EVENT = "121";
    public static final String SOURCE_TYPE_EXCAVATE = "10";
    public static final String SOURCE_TYPE_GAODE = "1";
    public static final String SOURCE_TYPE_NAVI = "3";
    public static final String SOURCE_TYPE_POLY = "0";
    public static final String SOURCE_TYPE_SHENZHEN_PL = "11";
    public static final String SOURCE_TYPE_SINA = "6";
    public static final String SOURCE_TYPE_SUIPIAN = "7";
    public static final String SOURCE_TYPE_TRAFFIC = "4";
    public static final String SOURCE_TYPE_WEATHER = "12";
    public static final String TAG_ACCIDENT_BARRIER = AMapAppGlobal.getApplication().getString(R.string.accident_barrier);
    public static final String TAG_ACCIDENT_CRASH = AMapAppGlobal.getApplication().getString(R.string.accident_crash);
    public static final String TAG_ACCIDENT_VEHICLE = AMapAppGlobal.getApplication().getString(R.string.breakdown);
    public static final String TAG_ANNOUNCEMENT = AMapAppGlobal.getApplication().getString(R.string.road_notice);
    public static final String TAG_CAMERA_CONTENT = AMapAppGlobal.getApplication().getString(R.string.road_street_view);
    public static final String TAG_CONTROL_CLOSE = AMapAppGlobal.getApplication().getString(R.string.report_cluorse);
    public static final String TAG_CONTROL_CONTROL = AMapAppGlobal.getApplication().getString(R.string.traffic_control);
    public static final String TAG_DANGER_CHILD = AMapAppGlobal.getApplication().getString(R.string.road_warning);
    public static final String TAG_EMERGENCY_EVENT_CONTENT = AMapAppGlobal.getApplication().getString(R.string.emergency_event);
    public static final String TAG_JAM_CROWDED = AMapAppGlobal.getApplication().getString(R.string.mytracks_detailfoot_trafficjam);
    public static final String TAG_JAM_SLOW = AMapAppGlobal.getApplication().getString(R.string.traffic_slow);
    public static final String TAG_JAM_STILL = AMapAppGlobal.getApplication().getString(R.string.traffic_still);
    public static final String TAG_JAM_UNBLOCK = AMapAppGlobal.getApplication().getString(R.string.traffic_unblocked);
    public static final String TAG_POLICE_CONTROL = AMapAppGlobal.getApplication().getString(R.string.police_control);
    public static final String TAG_POLICE_DRUNK = AMapAppGlobal.getApplication().getString(R.string.police_driving_under_influence);
    public static final String TAG_POLICE_LAW_ENFORCE = AMapAppGlobal.getApplication().getString(R.string.traffic_btn_police);
    public static final String TAG_SHIGONG = AMapAppGlobal.getApplication().getString(R.string.road_maintenance);
    public static final String TAG_UNBLOCKED = AMapAppGlobal.getApplication().getString(R.string.road_unblocked);
    public static final String TAG_WATER = AMapAppGlobal.getApplication().getString(R.string.road_floored);
    public static final String TAG_WEATHER_FOG_11091 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11091);
    public static final String TAG_WEATHER_FOG_11092 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11092);
    public static final String TAG_WEATHER_ICE_11084 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11084);
    public static final String TAG_WEATHER_LIGHT_11094 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11094);
    public static final String TAG_WEATHER_RAIN_11083 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11083);
    public static final String TAG_WEATHER_RAIN_11085 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11085);
    public static final String TAG_WEATHER_RAIN_11086 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11086);
    public static final String TAG_WEATHER_RAIN_11087 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11087);
    public static final String TAG_WEATHER_SAND_11093 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11093);
    public static final String TAG_WEATHER_SNOW_11088 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11088);
    public static final String TAG_WEATHER_SNOW_11089 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11089);
    public static final String TAG_WEATHER_SNOW_11090 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11090);
    public static final String TAG_WEATHER_WIND_11080 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11080);
    public static final String TAG_WEATHER_WIND_11081 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11081);
    public static final String TAG_WEATHER_WIND_11082 = AMapAppGlobal.getApplication().getString(R.string.traffic_weather_11082);
    public static final int WATER = 1100;
    public static final int WATER_TAG = 11100;
    public static final int WEATHER_FOG_11091 = 11091;
    public static final int WEATHER_FOG_11092 = 11092;
    public static final int WEATHER_ICE_11084 = 11084;
    public static final int WEATHER_LIGHT_11094 = 11094;
    public static final int WEATHER_RAIN_11083 = 11083;
    public static final int WEATHER_RAIN_11085 = 11085;
    public static final int WEATHER_RAIN_11086 = 11086;
    public static final int WEATHER_RAIN_11087 = 11087;
    public static final int WEATHER_SAND_11093 = 11093;
    public static final int WEATHER_SNOW_11088 = 11088;
    public static final int WEATHER_SNOW_11089 = 11089;
    public static final int WEATHER_SNOW_11090 = 11090;
    public static final int WEATHER_WIND_11080 = 11080;
    public static final int WEATHER_WIND_11081 = 11081;
    public static final int WEATHER_WIND_11082 = 11082;
    private static final long serialVersionUID = 885331763454657434L;
    private String audio;
    private String audiolen;
    private int criticism;
    private String desc;
    private String displayname;
    private String endTime;
    private a eventAffectOverlayModel;
    private int filterKey;
    private String head;
    private String lane;
    private long lastUpdateTime;
    private String mAddress;
    private int mButtonFlag;
    private String mCardinfoUrl;
    private String mCardinfoUrlName;
    private long mCreateTime;
    private int mDeltaSeconds;
    private int mDirection;
    private String mFaceUrl;
    private int mLayerId;
    private int mLayerTag;
    private String mNickName;
    private String mShowUrl;
    private int mWay;
    private String operationIcon;
    private String operationName;
    private int praise;
    private String source;
    private String specificTime;
    private String startTime;
    private int subDetailTopicCount;
    private ArrayList<TrafficTopic> subinfo;
    private ArrayList<csa> trafficGroupInfo;
    private int type;

    public enum Type {
        OFFICCIAL,
        AUTHORITY,
        OTHERS
    }

    public static final class a {
        public ArrayList<GeoPoint> a;
        public ArrayList<ArrayList<GeoPoint>> b;
        public int c;
        public ArrayList<GeoPoint> d;
        ArrayList<ArrayList<GeoPoint>> e;

        /* synthetic */ a(byte b2) {
            this();
        }

        private a() {
            this.a = new ArrayList<>();
            this.b = new ArrayList<>();
            this.c = -1;
            this.d = new ArrayList<>();
            this.e = new ArrayList<>();
        }

        /* access modifiers changed from: 0000 */
        public final void a(JSONArray jSONArray) {
            if (jSONArray != null) {
                AMapLog.d("affect", "TrafficTopic: ".concat(String.valueOf(jSONArray)));
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    new ArrayList();
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        try {
                            GeoPoint geoPoint = new GeoPoint(optJSONObject.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON), optJSONObject.getDouble("lat"));
                            this.a.add(geoPoint);
                            this.d.add(geoPoint);
                        } catch (Exception e2) {
                            if (bno.a) {
                                StringBuilder sb = new StringBuilder("parseAffectArea error");
                                sb.append(jSONArray.toString());
                                AMapLog.e("TrafficTipc", sb.toString());
                                throw new RuntimeException(e2);
                            }
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(JSONObject jSONObject) {
            if (jSONObject != null) {
                String optString = jSONObject.optString("color");
                if (!TextUtils.isEmpty(optString)) {
                    try {
                        this.c = Color.parseColor(optString);
                    } catch (IllegalArgumentException unused) {
                        this.c = -1;
                    }
                }
                JSONArray optJSONArray = jSONObject.optJSONArray("coords");
                if (optJSONArray != null) {
                    AMapLog.d("affect", "TrafficTopic: ".concat(String.valueOf(optJSONArray)));
                    int length = optJSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONArray optJSONArray2 = optJSONArray.optJSONArray(i);
                        if (optJSONArray2 != null) {
                            int length2 = optJSONArray2.length();
                            ArrayList arrayList = new ArrayList();
                            this.b.add(arrayList);
                            for (int i2 = 0; i2 < length2; i2++) {
                                JSONObject optJSONObject = optJSONArray2.optJSONObject(i2);
                                if (optJSONObject != null) {
                                    try {
                                        GeoPoint geoPoint = new GeoPoint(optJSONObject.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON), optJSONObject.getDouble("lat"));
                                        arrayList.add(geoPoint);
                                        this.d.add(geoPoint);
                                    } catch (Exception e2) {
                                        if (bno.a) {
                                            StringBuilder sb = new StringBuilder("parseAffectArea error");
                                            sb.append(jSONObject.toString());
                                            AMapLog.e("TrafficTipc", sb.toString());
                                            throw new RuntimeException(e2);
                                        }
                                    }
                                }
                            }
                            continue;
                        }
                    }
                }
            }
        }
    }

    public TrafficTopic() {
        this.mAddress = "";
        this.mDeltaSeconds = 0;
        this.mCreateTime = 0;
        this.subinfo = new ArrayList<>();
        this.trafficGroupInfo = new ArrayList<>();
        this.eventAffectOverlayModel = new a(0);
    }

    public TrafficTopic(JSONObject jSONObject) {
        this.mAddress = "";
        this.mDeltaSeconds = 0;
        this.mCreateTime = 0;
        this.subinfo = new ArrayList<>();
        this.trafficGroupInfo = new ArrayList<>();
        this.eventAffectOverlayModel = new a(0);
        setAddress(jSONObject.optString("address"));
        setPicUrl(jSONObject.optString(SocialConstants.PARAM_APP_ICON));
        setAudio(jSONObject.optString("audio"));
        setAudiolen(jSONObject.optString("audiolen"));
        setSource(jSONObject.optString("source"));
        setX(jSONObject.optInt(DictionaryKeys.CTRLXY_X));
        setY(jSONObject.optInt(DictionaryKeys.CTRLXY_Y));
        setDeltaSeconds(jSONObject.optInt("deltatime"));
        setCreateTime(jSONObject.optLong("createtime"));
        setLastUpdateTime(jSONObject.optLong("lastupdate"));
        setTitle(jSONObject.optString("title"));
        setLayerId(jSONObject.optInt(WidgetType.LAYER));
        setLayerTag(jSONObject.optInt("layertag"));
        setDirection(jSONObject.optInt("direct"));
        setWay(jSONObject.optInt("way"));
        setPraise(jSONObject.optInt(AUCardOptionView.TYPE_PRAISE));
        setCriticism(jSONObject.optInt("criticism"));
        setHead(jSONObject.optString("head"));
        setDesc(jSONObject.optString("desc"));
        setStartTime(jSONObject.optString("infostartdate"));
        setEndTime(jSONObject.optString("infoenddate"));
        setSpecificTime(jSONObject.optString("infotimeseg"));
        setLane(jSONObject.optString("lane"));
        setSubDetailTopicCount(jSONObject.optInt("subdetailcount"));
        setType(jSONObject.optInt("official"));
        this.operationIcon = jSONObject.optString("iconstyle");
        this.operationName = jSONObject.optString("eventname");
        this.mButtonFlag = jSONObject.optInt("buttonflag", -1);
        this.mShowUrl = jSONObject.optString("showurl", "");
        if (jSONObject.has("id")) {
            setId(jSONObject.optInt("id"));
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("relation");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    this.trafficGroupInfo.add(new csa(optJSONObject));
                }
            }
        }
        this.eventAffectOverlayModel.a(jSONObject.optJSONArray("affect"));
        this.eventAffectOverlayModel.a(jSONObject.optJSONObject("multiLines"));
        JSONArray optJSONArray2 = jSONObject.optJSONArray("uids");
        if (optJSONArray2 != null) {
            try {
                int length2 = optJSONArray2.length();
                for (int i2 = 0; i2 < length2; i2++) {
                    addUids(i2, optJSONArray2.getString(i2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setPolyCount(jSONObject.optInt("polycount", 1));
        if (getPolyCount() > 1) {
            setTagId(jSONObject.optInt("tagid"));
            setLocal(jSONObject.optInt(BehavorReporter.PROVIDE_BY_LOCAL));
            setIsPhysic(jSONObject.optInt("isphysic"));
        }
        JSONObject optJSONObject2 = jSONObject.optJSONObject("around");
        if (optJSONObject2 != null) {
            setAroundName(optJSONObject2.optString("name"));
        }
        setNickName(jSONObject.optString("nick"));
        setDisplayname(jSONObject.optString("displayname"));
        JSONArray optJSONArray3 = jSONObject.optJSONArray("subinfo");
        if (optJSONArray3 != null) {
            for (int i3 = 0; i3 < optJSONArray3.length(); i3++) {
                JSONObject optJSONObject3 = optJSONArray3.optJSONObject(i3);
                if (optJSONObject3 != null) {
                    this.subinfo.add(new TrafficTopic(optJSONObject3));
                }
            }
        }
        JSONObject optJSONObject4 = jSONObject.optJSONObject("cardinfo");
        if (optJSONObject4 != null) {
            this.mCardinfoUrl = optJSONObject4.optString("url", "");
            this.mCardinfoUrlName = optJSONObject4.optString("urlname", "");
        }
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getAudio() {
        return this.audio;
    }

    public void setAudio(String str) {
        this.audio = str;
    }

    public String getAudiolen() {
        return this.audiolen;
    }

    public void setAudiolen(String str) {
        this.audiolen = str;
    }

    public ArrayList getSubinfo() {
        return this.subinfo;
    }

    public ArrayList<csa> getTrafficGroup() {
        return this.trafficGroupInfo;
    }

    public void setSubinfo(ArrayList<TrafficTopic> arrayList) {
        this.subinfo = arrayList;
    }

    public TrafficTopic(int i) {
        this.mAddress = "";
        this.mDeltaSeconds = 0;
        this.mCreateTime = 0;
        this.subinfo = new ArrayList<>();
        this.trafficGroupInfo = new ArrayList<>();
        this.eventAffectOverlayModel = new a(0);
        this.mLayerId = i;
    }

    public void setLayerId(int i) {
        this.mLayerId = i;
    }

    public int getLayerId() {
        return this.mLayerId;
    }

    public void setLayerTag(int i) {
        this.mLayerTag = i;
    }

    public int getLayerTag() {
        return this.mLayerTag;
    }

    public int getHeadImgRes(boolean z) {
        int i = 0;
        if (isMultiReports()) {
            return this.subinfo.get(0).getHeadImgRes(z);
        }
        int intValue = (TextUtils.isEmpty(this.operationIcon) || z || OperationIcon.get(this.operationIcon) == null) ? 0 : OperationIcon.get(this.operationIcon).intValue();
        if (intValue == 0) {
            Integer num = LayerTag2Icon.get(Integer.valueOf(this.mLayerTag));
            if (num != null) {
                i = num.intValue();
            }
        } else {
            i = intValue;
        }
        return i;
    }

    public void setDirection(int i) {
        this.mDirection = i;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(long j) {
        this.lastUpdateTime = j;
    }

    public void setWay(int i) {
        this.mWay = i;
    }

    public int getWay() {
        return this.mWay;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public int getFilterKey() {
        return this.filterKey;
    }

    public void setFilterKey(int i) {
        this.filterKey = i;
    }

    public int getCriticism() {
        return this.criticism;
    }

    public void setCriticism(int i) {
        this.criticism = i;
    }

    public int getPraise() {
        return this.praise;
    }

    public void setPraise(int i) {
        this.praise = i;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setDeltaSeconds(int i) {
        this.mDeltaSeconds = i;
    }

    public int getDeltaSeconds() {
        return this.mDeltaSeconds;
    }

    public void setCreateTime(long j) {
        this.mCreateTime = j;
    }

    public long getCreateTime() {
        return this.mCreateTime;
    }

    public void setFaceUrl(String str) {
        this.mFaceUrl = str;
    }

    public String getFaceUrl() {
        return this.mFaceUrl;
    }

    public void setNickName(String str) {
        this.mNickName = str;
    }

    public String getNickName() {
        return TextUtils.isEmpty(this.mNickName) ? "匿名用户" : this.mNickName;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String str) {
        this.displayname = str;
    }

    public String getHead() {
        return this.head;
    }

    public void setHead(String str) {
        this.head = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String getSpecificTime() {
        return this.specificTime;
    }

    public void setSpecificTime(String str) {
        this.specificTime = str;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String str) {
        this.endTime = str;
    }

    public String getLane() {
        return this.lane;
    }

    public void setLane(String str) {
        this.lane = str;
    }

    public int getSubDetailTopicCount() {
        return this.subDetailTopicCount;
    }

    public void setSubDetailTopicCount(int i) {
        this.subDetailTopicCount = i;
    }

    public int getSubTopicCount() {
        if (this.subinfo == null) {
            return 0;
        }
        return this.subinfo.size();
    }

    public boolean isMultiReports() {
        return this.subinfo != null && this.subinfo.size() > 0;
    }

    public CharSequence getContent() {
        if (!isNewData()) {
            return getTitle();
        }
        if (TextUtils.isEmpty(this.lane) || TextUtils.isEmpty(this.desc)) {
            StringBuilder sb = new StringBuilder(this.lane);
            sb.append(this.desc);
            return sb;
        }
        StringBuilder sb2 = new StringBuilder(this.lane);
        sb2.append(" • ");
        sb2.append(this.desc);
        return sb2;
    }

    public boolean isMultiDetailsReports() {
        return this.subDetailTopicCount > 1;
    }

    public CharSequence getOtherReports() {
        ahy a2 = ahy.a("");
        if (isMultiReports()) {
            int i = this.subDetailTopicCount == 0 ? 1 : this.subDetailTopicCount;
            int i2 = i;
            int i3 = 0;
            while (i2 < this.subinfo.size() && i3 < 3) {
                a2.b(this.subinfo.get(i2).mNickName, new ForegroundColorSpan(-9079435));
                i2++;
                if (!(i2 == this.subinfo.size() || i3 + 1 == 3)) {
                    a2.b("、");
                }
                i3++;
            }
            if (!TextUtils.isEmpty(a2)) {
                int i4 = i2 == this.subinfo.size() ? 20 : 15;
                if (a2.length() > i4) {
                    a2 = ahy.a(a2.subSequence(0, i4)).b("...");
                }
                if (i2 == this.subinfo.size()) {
                    a2.b("也在此处上报", new ForegroundColorSpan(-9079435));
                } else {
                    StringBuilder sb = new StringBuilder("等");
                    sb.append(this.subinfo.size() - i);
                    sb.append("名用户也在此处上报");
                    a2.b(sb, new ForegroundColorSpan(-9079435));
                }
            }
        }
        return a2;
    }

    public List<TrafficTopic> getAllDetailsTopics() {
        ArrayList arrayList = new ArrayList();
        if (!isMultiReports()) {
            arrayList.add(this);
        } else if (this.subDetailTopicCount == 0) {
            arrayList.add(this.subinfo.get(0));
        } else {
            arrayList.addAll(this.subinfo.subList(0, this.subDetailTopicCount));
        }
        return arrayList;
    }

    public String getTopicHead() {
        if (isMultiReports()) {
            return this.subinfo.get(0).getHead();
        }
        return getHead();
    }

    public String getTopicLayer() {
        if (isMultiReports()) {
            return LayerTag2Title.get(Integer.valueOf(this.subinfo.get(0).getLayerTag()));
        }
        return LayerTag2Title.get(Integer.valueOf(getLayerTag()));
    }

    public String getExTopicLayer(boolean z) {
        String str = isMultiReports() ? this.subinfo.get(0).operationName : this.operationName;
        if (TextUtils.isEmpty(str) || z) {
            return getTopicLayer();
        }
        return str;
    }

    public boolean isNewData() {
        return !TextUtils.isEmpty(this.head);
    }

    public String getCardinfoUrl() {
        if (isMultiReports()) {
            return this.subinfo.get(0).getCardinfoUrl();
        }
        return this.mCardinfoUrl;
    }

    public String getCardinfoUrlName() {
        if (isMultiReports()) {
            return this.subinfo.get(0).getCardinfoUrlName();
        }
        return this.mCardinfoUrlName;
    }

    public int getButtonFlag() {
        return this.mButtonFlag;
    }

    public String getShowUrl() {
        return this.mShowUrl != null ? this.mShowUrl : "";
    }

    @NonNull
    public a getAffectOverlayData() {
        return this.eventAffectOverlayModel;
    }

    public boolean isJamReport() {
        if (!TextUtils.isEmpty(this.operationName)) {
            return "拥堵预测".equals(this.operationName);
        }
        return false;
    }

    public String getEventName() {
        return this.operationName;
    }
}
