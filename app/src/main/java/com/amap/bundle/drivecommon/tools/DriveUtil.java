package com.amap.bundle.drivecommon.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewConfiguration;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.setting.quicknaviwidget.main.QuickAutonNaviSettingFragment;
import com.amap.bundle.drivecommon.map.db.NaviHistoryDao.Properties;
import com.amap.bundle.drivecommon.map.db.helper.RdCameraDBHelper;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.RouteCarResultData;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.jni.ae.guide.model.ExitDirectionInfo;
import com.autonavi.jni.ae.guide.model.NaviInfo;
import com.autonavi.jni.ae.guide.model.NaviInfoPanel;
import com.autonavi.jni.ae.guide.model.PathTrafficEventInfo;
import com.autonavi.jni.ae.guide.model.TrafficEventInfo;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.pos.LocMapPoint;
import com.autonavi.jni.ae.pos.LocMatchInfo;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.jni.ae.route.model.POIForRequest;
import com.autonavi.jni.ae.route.model.POIInfo;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.db.model.Vehicles;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import com.autonavi.sync.beans.JsonDatasWithType;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.tencent.open.SocialConstants;
import com.ut.device.UTDevice;
import de.greenrobot.dao.query.QueryBuilder;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriveUtil {
    public static final String ADDCAR_AMAPURI = "amapuri://carownerservice/addcar?sourcePage=selectCarPage&addType=";
    public static final String BUNDLE_KEY_BOOL_SAVECOOKIE = "key_savehistory";
    public static final String BUNDLE_KEY_OBJ_TITLEICONS = "key_titleicons";
    public static final int CAR_PLATE_OPEN_AVOID_LIMITED_MAX_COUNT = 3;
    public static final int CAR_PLATE_SETTING_MAX_COUNT = 3;
    public static final String CAR_SELECT_AJXPAGE = "path://amap_bundle_drive/src/car/select_page/CarSelectViewController.page.js";
    public static final int CAR_TYPE = 0;
    public static final int COMMON_CAR_TYPE = 0;
    public static final int COMMON_MOTOR_TYPE = 11;
    public static final int COMMON_TRUCK_TYPE = 1;
    public static final String DEFAULT_EXTENSION = "0";
    public static final int DEV_NO_NEED_DEVIATE = 0;
    public static final String EDITCAR_AMAPURI = "amapuri://carownerservice/editcar?sourcePage=selectCarPage&carNumber=";
    public static final int HEAVY_TRUCK = 4;
    public static final int LIGHT_TRUCK = 2;
    public static final String MAP_PLACE_DES = "地图指定位置";
    public static final String MAP_PLACE_DES_2 = "地图选定位置";
    public static final String MAP_PLACE_DES_3 = "地图选点";
    public static final int MAX_COUNT = 20;
    public static final int MEDIUM_TRUCK = 3;
    public static final int MINI_TRUCK = 1;
    public static final int MOTOR_PLATE_OPEN_AVOID_LIMITED_MAX_COUNT = 3;
    public static final int MOTOR_PLATE_SETTING_MAX_COUNT = 3;
    public static final int MOTOR_TYPE = 11;
    public static final String MY_LOCATION_DES = "我的位置";
    public static final String MY_LOCATION_LOADING = "获取中...";
    public static final String NAVI_TYPE = "navi_type";
    public static final String NAVI_TYPE_CAR = "car";
    public static final String NAVI_TYPE_ENTRANCE_CALLBACK = "callback";
    public static final String NAVI_TYPE_MOTORBIKE = "motorbike";
    public static final String NAVI_TYPE_TRUCK = "truck";
    public static final int PARKING_LIMIT_DISTENCE = 100;
    public static final int PLUGGING_IN_ELECTRIC_CAR_TYPE = 4;
    public static final int PLUGGING_IN_ELECTRIC_TRUCK_TYPE = 5;
    public static final int POI_ENCRYPTED = 1;
    public static final String POI_EXTRA_KEY_INT_PARKING = "recommendParking";
    public static final int POI_ROUTE_RECOMMEND_COMPANY = 2;
    public static final int POI_ROUTE_RECOMMEND_HOME = 1;
    public static final int POI_ROUTE_RECOMMEND_OILSTATION = 5;
    public static final int POI_ROUTE_RECOMMEND_OTHER = 4;
    public static final int POI_ROUTE_RECOMMEND_PARKING = 3;
    public static final String POI_TYPE_AIRPORT = "150104";
    @Deprecated
    public static final String POI_TYPE_AIRPORT_OLD = "150100";
    public static final String POI_TYPE_RAILWAY = "150200";
    public static final int PURE_ELECTRIC_CAR_TYPE = 2;
    public static final int PURE_ELECTRIC_TRUCK_TYPE = 3;
    public static final String SCHEME_PARAM_ENCRYPT = "encrypt";
    public static final String SOURCE_APPLICATION = "sourceApplication";
    public static final String SUBPOI_TYPE_AIRPORT = "303,105,106,305";
    public static final String SUBPOI_TYPE_RAILWAY = "103,104,305";
    public static final int TRUCK_TYPE = 1;
    private static Pattern a = Pattern.compile("[0-9]*");

    public static void addCarRouteLog(String str) {
    }

    public static void addSnrLog(String str) {
    }

    public static int getCarTypeByVtype(int i) {
        if (i == 11) {
            return 11;
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
                return 1;
            default:
                return 0;
        }
    }

    public static POI getPOIHome() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.c();
            }
        }
        return null;
    }

    public static POI getPOICompany() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.d();
            }
        }
        return null;
    }

    public static String getLastRoutingChoice() {
        String stringValue = new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("car_method", "1");
        return TextUtils.isEmpty(stringValue) ? "1" : stringValue;
    }

    public static String getTruckRoutingChoice() {
        String stringValue = new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue(DriveSpUtil.TRUCK_METHOD, "1");
        return TextUtils.isEmpty(stringValue) ? "1" : stringValue;
    }

    public static String getMotorRoutingChoice() {
        String stringValue = new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue(DriveSpUtil.MOTOR_PATH_PREFERENCE, "1");
        return TextUtils.isEmpty(stringValue) ? "1" : stringValue;
    }

    public static void delNaviHistoryList() {
        AMapAppGlobal.getApplication();
        si.a().b();
    }

    public static boolean isShowCarInfoDialog() {
        Car carTruckInfo = getCarTruckInfo();
        if (carTruckInfo == null) {
            return false;
        }
        boolean z = TextUtils.isEmpty(carTruckInfo.height) || "0".equals(carTruckInfo.height);
        boolean z2 = TextUtils.isEmpty(carTruckInfo.weight) || "0".equals(carTruckInfo.weight);
        boolean z3 = TextUtils.isEmpty(carTruckInfo.width) || "0".equals(carTruckInfo.width);
        boolean isEmpty = TextUtils.isEmpty(getTruckType(carTruckInfo.truckType));
        boolean z4 = TextUtils.isEmpty(carTruckInfo.capacity) || "0".equals(carTruckInfo.capacity);
        boolean z5 = TextUtils.isEmpty(carTruckInfo.length) || "0".equals(carTruckInfo.length);
        if ((z3 || isEmpty || z4 || z5) && (!z || !z2)) {
            return true;
        }
        return false;
    }

    public static boolean truckHasEmptyProperty() {
        Car carTruckInfo = getCarTruckInfo();
        if (carTruckInfo == null) {
            return true;
        }
        boolean z = TextUtils.isEmpty(carTruckInfo.height) || "0".equals(carTruckInfo.height);
        boolean z2 = TextUtils.isEmpty(carTruckInfo.weight) || "0".equals(carTruckInfo.weight);
        boolean z3 = TextUtils.isEmpty(carTruckInfo.width) || "0".equals(carTruckInfo.width);
        boolean isEmpty = TextUtils.isEmpty(getTruckType(carTruckInfo.truckType));
        boolean z4 = TextUtils.isEmpty(carTruckInfo.capacity) || "0".equals(carTruckInfo.capacity);
        boolean z5 = TextUtils.isEmpty(carTruckInfo.length) || "0".equals(carTruckInfo.length);
        if (z3 || isEmpty || z4 || z5 || z || z2) {
            return true;
        }
        return false;
    }

    public static void showNotAllDailog(final bid bid, int i, final String str, final tm<String, Boolean> tmVar) {
        a aVar = new a(bid.getContext());
        aVar.a(R.string.dialog_mend_truck_info_title);
        aVar.b(R.string.dialog_finish_msg);
        aVar.a(R.string.dialog_go_finish, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                if (bid != null) {
                    bid.dismissViewLayer(alertView);
                }
                if (tmVar != null) {
                    tmVar.a(false);
                    tmVar.a(Boolean.TRUE);
                }
                if (bid != null) {
                    String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
                    if (!TextUtils.isEmpty(truckCarPlateNumber)) {
                        bid.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(DriveUtil.getCarEditPath(truckCarPlateNumber))));
                    }
                }
            }
        });
        aVar.b(i, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                bid.dismissViewLayer(alertView);
                if (tmVar != null) {
                    tmVar.a(false);
                }
            }
        });
        aVar.c = new a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.a(false);
        AlertView a2 = aVar.a();
        bid.showViewLayer(a2);
        if (tmVar != null) {
            tmVar.a(a2.isShown());
        }
        a2.startAnimation();
    }

    public static String getCarEditPath(String str) {
        return EDITCAR_AMAPURI.concat(String.valueOf(str));
    }

    public static String getTruckType(int i) {
        Context appContext = AMapPageUtil.getAppContext();
        switch (i) {
            case 1:
                return appContext.getString(R.string.mini_truck);
            case 2:
                return appContext.getString(R.string.light_truck);
            case 3:
                return appContext.getString(R.string.medium_truck);
            case 4:
                return appContext.getString(R.string.heavy_truck);
            default:
                return "";
        }
    }

    public static void startCarList(int i, bid bid) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_drive/src/car/select_page/CarSelectViewController.page.js");
        pageBundle.putString("jsData", getCarTypeJson(i));
        bid.startPage(Ajx3Page.class, pageBundle);
    }

    public static void startAddCarPage(int i, bid bid, int i2) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_carowner/src/car_owner/CarAddViewController.page.js");
        pageBundle.putString("jsData", getCarTypeJson(i));
        bid.startPageForResult(Ajx3Page.class, pageBundle, i2);
    }

    public static void startAddCarPage(int i, bid bid) {
        bid.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(ADDCAR_AMAPURI.concat(String.valueOf(i)))));
    }

    public static String getCarTypeJson(int i) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("vehicleType", String.valueOf(i));
            jSONObject2.put("data", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject2.toString();
    }

    public static boolean isCarTruckInfoEmpty() {
        return getCarTruckInfo() == null;
    }

    public static float getCarTruckFloat(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0.0f;
            }
            return Float.parseFloat(str);
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    public static void showedETDEntranceTip(POI poi) {
        if (isNeedSearchCarScene(poi) && !DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_ENTRANCE_TIP_SHOW, true)) {
            DriveSpUtil.setLong(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_LAST_SHOW_TIME, System.currentTimeMillis());
        }
        DriveSpUtil.setBool(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_ENTRANCE_TIP_SHOW, false);
    }

    public static boolean shouldShowEDTEntranceTip(POI poi) {
        if (DriveSpUtil.getInt(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_ENTRANCE_CLICKED, 0) >= 3) {
            return false;
        }
        if (DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_ENTRANCE_TIP_SHOW, true)) {
            return true;
        }
        if (isNeedSearchCarScene(poi)) {
            if (System.currentTimeMillis() - DriveSpUtil.getLong(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_LAST_SHOW_TIME, 0) > 86400000) {
                return true;
            }
        }
        return false;
    }

    public static boolean isClickedETDEntrance() {
        return DriveSpUtil.getInt(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_ENTRANCE_CLICKED, 0) > 0;
    }

    public static void clickedETDEntrance() {
        DriveSpUtil.setInt(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_ENTRANCE_CLICKED, DriveSpUtil.getInt(AMapAppGlobal.getApplication(), DriveSpUtil.ETD_ENTRANCE_CLICKED, 0) + 1);
    }

    public static boolean isSpCarNumberEmpty() {
        return TextUtils.isEmpty(new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("car_no", ""));
    }

    public static boolean isDBCarNumberEmpty() {
        if (AMapAppGlobal.getApplication() == null) {
            return true;
        }
        List<Vehicles> list = null;
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            list = ato.b().a();
        }
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNeedSearchCarScene(POI poi) {
        return isNeedSearchCarScene(poi, false);
    }

    public static boolean isNeedSearchCarScene(POI poi, boolean z) {
        if (poi == null || TextUtils.isEmpty(poi.getId())) {
            return false;
        }
        if (!z && poi.getPoiExtra().containsKey("main_poi")) {
            return false;
        }
        String type = poi.getType();
        if (type == null || (!type.startsWith(POI_TYPE_RAILWAY) && !type.startsWith(POI_TYPE_AIRPORT))) {
            return false;
        }
        return true;
    }

    public static String getSearchCarSceneParam(POI poi) {
        String type = poi.getType();
        if (type != null) {
            if (type.startsWith(POI_TYPE_RAILWAY)) {
                return SUBPOI_TYPE_RAILWAY;
            }
            if (type.startsWith(POI_TYPE_AIRPORT)) {
                return SUBPOI_TYPE_AIRPORT;
            }
        }
        return null;
    }

    public static boolean isNeedArroundParkingSearch(POI poi) {
        String type = poi.getType();
        if (type != null && (type.startsWith("1509") || type.startsWith("0101"))) {
            return false;
        }
        ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
        if ((iSearchPoiData.getPoiChildrenInfo() != null ? iSearchPoiData.getPoiChildrenInfo().childType : 0) == 41) {
            return false;
        }
        Serializable serializable = poi.getPoiExtra().get(POI_EXTRA_KEY_INT_PARKING);
        if (serializable == null) {
            return true;
        }
        switch (((Integer) serializable).intValue()) {
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return false;
            case 4:
                return true;
            case 5:
                return false;
            default:
                return true;
        }
    }

    public static void putLastNavittsVersion(String str) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putStringValue("NAVIGATION_TTS_VERSION", str);
    }

    public static String getLastNavittsVersion() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("NAVIGATION_TTS_VERSION", "");
    }

    public static String getVehicleVersion() {
        return new MapSharePreference((String) "CAR_OWNER").getStringValue("VEHICLE_VERSION", "0.0");
    }

    public static void setVehicleVersion(String str) {
        new MapSharePreference((String) "CAR_OWNER").putStringValue("VEHICLE_VERSION", str);
    }

    public static void saveNaviHitory(final POI poi) {
        if (poi != null) {
            ahn.b().execute(new Runnable() {
                /* JADX WARNING: Removed duplicated region for block: B:19:0x006e  */
                /* JADX WARNING: Removed duplicated region for block: B:20:0x0078  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r3 = this;
                        com.autonavi.common.model.POI r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getMyLocationPoi()
                        if (r0 == 0) goto L_0x00a0
                        com.autonavi.common.model.POI r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getMyLocationPoi()
                        if (r0 != 0) goto L_0x001a
                        android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
                        int r1 = com.autonavi.minimap.R.string.location_fail
                        java.lang.String r0 = r0.getString(r1)
                        com.amap.bundle.utils.ui.ToastHelper.showToast(r0)
                        return
                    L_0x001a:
                        android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
                        int r2 = com.autonavi.minimap.R.string.LocationMe
                        java.lang.String r1 = defpackage.lh.a(r1, r2)
                        java.lang.String r0 = r0.getName()
                        boolean r0 = r1.equals(r0)
                        if (r0 == 0) goto L_0x003b
                        com.autonavi.common.model.POI r0 = r2
                        java.lang.String r0 = r0.getName()
                        boolean r0 = r1.equals(r0)
                        if (r0 == 0) goto L_0x003b
                        return
                    L_0x003b:
                        sj r0 = new sj
                        r0.<init>()
                        r1 = 1
                        java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                        r0.b = r1
                        com.autonavi.common.model.POI r1 = r2
                        if (r1 == 0) goto L_0x0056
                        org.json.JSONObject r1 = defpackage.chk.a(r1)
                        if (r1 == 0) goto L_0x0056
                        java.lang.String r1 = r1.toString()
                        goto L_0x0057
                    L_0x0056:
                        r1 = 0
                    L_0x0057:
                        r0.d = r1
                        com.autonavi.common.model.POI r1 = r2
                        r0.f = r1
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        com.autonavi.common.model.POI r2 = r2
                        java.lang.String r2 = r2.getId()
                        boolean r2 = android.text.TextUtils.isEmpty(r2)
                        if (r2 != 0) goto L_0x0078
                        com.autonavi.common.model.POI r2 = r2
                        java.lang.String r2 = r2.getId()
                        r1.append(r2)
                        goto L_0x0093
                    L_0x0078:
                        com.autonavi.common.model.POI r2 = r2
                        com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
                        int r2 = r2.x
                        r1.append(r2)
                        java.lang.String r2 = "+"
                        r1.append(r2)
                        com.autonavi.common.model.POI r2 = r2
                        com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
                        int r2 = r2.y
                        r1.append(r2)
                    L_0x0093:
                        java.lang.String r1 = r1.toString()
                        java.lang.String r1 = defpackage.agy.a(r1)
                        r0.a = r1
                        com.amap.bundle.drivecommon.tools.DriveUtil.a(r0)
                    L_0x00a0:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drivecommon.tools.DriveUtil.AnonymousClass4.run():void");
                }
            });
        }
    }

    public static void setResetSchoolBusMapTrafficFlag(int i) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue("reset_school_bus_traffic_state", i);
    }

    public static int getResetSchoolBusMapTrafficFlag() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("reset_school_bus_traffic_state", 1);
    }

    public static int getResetSchoolBusMapTrafficFlag(int i) {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("reset_school_bus_traffic_state", i);
    }

    public static List<sj> getNaviHistoryList() {
        AMapAppGlobal.getApplication();
        QueryBuilder queryBuilder = si.a().a.queryBuilder();
        queryBuilder.orderDesc(Properties.e);
        return queryBuilder.list();
    }

    public static void putLastRoutingChoice(String str) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putStringValue("car_method", str);
    }

    public static void putTruckRoutingChoice(String str) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putStringValue(DriveSpUtil.TRUCK_METHOD, str);
    }

    public static void putMotorRoutingChoice(String str) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putStringValue(DriveSpUtil.MOTOR_PATH_PREFERENCE, str);
    }

    public static boolean needShowCarPlateSetting() {
        ku.a().c("Restriction", "needShowCarPlateSetting");
        int carPlateSettingShowCount = getCarPlateSettingShowCount();
        if (carPlateSettingShowCount >= 3) {
            ku.a().c("Restriction", "count >= 3 :".concat(String.valueOf(carPlateSettingShowCount)));
            return false;
        }
        long carPlateLastSettingTime = getCarPlateLastSettingTime();
        if (isTodayANewDay(carPlateLastSettingTime)) {
            return true;
        }
        ku.a().c("Restriction", "!isTodayANewDay(lastMilli):".concat(String.valueOf(carPlateLastSettingTime)));
        return false;
    }

    public static boolean needShowCarPlateOpenAvoidLimitedNotice() {
        ku.a().c("Restriction", "needShowCarPlateSetting");
        int carPlateOpenAvoidLimitedNoticeCount = getCarPlateOpenAvoidLimitedNoticeCount();
        if (carPlateOpenAvoidLimitedNoticeCount >= 3) {
            ku.a().c("Restriction", "count >= 3 :".concat(String.valueOf(carPlateOpenAvoidLimitedNoticeCount)));
            return false;
        }
        long carPlateOpenAvoidLimitedLastNoticeTime = getCarPlateOpenAvoidLimitedLastNoticeTime();
        if (isTodayANewDay(carPlateOpenAvoidLimitedLastNoticeTime)) {
            return true;
        }
        ku.a().c("Restriction", "!isTodayANewDay(lastMilli):".concat(String.valueOf(carPlateOpenAvoidLimitedLastNoticeTime)));
        return false;
    }

    public static boolean needShowMotorPlateSetting() {
        ku.a().c("Restriction", "needShowMotorPlateSetting");
        int motorPlateSettingShowCount = getMotorPlateSettingShowCount();
        if (motorPlateSettingShowCount >= 3) {
            ku.a().c("Restriction", "count >= 3 :".concat(String.valueOf(motorPlateSettingShowCount)));
            return false;
        }
        long motorPlateLastSettingTime = getMotorPlateLastSettingTime();
        if (isTodayANewDay(motorPlateLastSettingTime)) {
            return true;
        }
        ku.a().c("Restriction", "!isTodayANewDay(lastMilli):".concat(String.valueOf(motorPlateLastSettingTime)));
        return false;
    }

    public static boolean needShowMotorPlateOpenAvoidLimitedNotice() {
        ku.a().c("Restriction", "needShowMotorPlateOpenAvoidLimitedNotice");
        int motorPlateOpenAvoidLimitedNoticeCount = getMotorPlateOpenAvoidLimitedNoticeCount();
        if (motorPlateOpenAvoidLimitedNoticeCount >= 3) {
            ku.a().c("Restriction", "count >= 3 :".concat(String.valueOf(motorPlateOpenAvoidLimitedNoticeCount)));
            return false;
        }
        long motorPlateOpenAvoidLimitedLastNoticeTime = getMotorPlateOpenAvoidLimitedLastNoticeTime();
        if (isTodayANewDay(motorPlateOpenAvoidLimitedLastNoticeTime)) {
            return true;
        }
        ku.a().c("Restriction", "!isTodayANewDay(lastMilli):".concat(String.valueOf(motorPlateOpenAvoidLimitedLastNoticeTime)));
        return false;
    }

    public static boolean isTodayANewDay(long j) {
        return formatDate(j).before(formatDate(System.currentTimeMillis()));
    }

    public static Date formatDate(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(Long.valueOf(j)));
        } catch (ParseException unused) {
            return null;
        }
    }

    public static int getCarPlateSettingShowCount() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getIntValue("CAR_PLATE_SETTING_SHOW_COUNT", 0);
    }

    public static void setCarPlateSettingShowCount(int i) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putIntValue("CAR_PLATE_SETTING_SHOW_COUNT", i);
    }

    public static int getMotorPlateSettingShowCount() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getIntValue("MOTOR_PATH_PLATE_SETTING_SHOW_COUNT", 0);
    }

    public static void setMotorPlateSettingShowCount(int i) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putIntValue("MOTOR_PATH_PLATE_SETTING_SHOW_COUNT", i);
    }

    public static int getCarPlateOpenAvoidLimitedNoticeCount() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getIntValue("CAR_PLATE_OPEN_AVOID_LIMITED_NOTICE_COUNT", 0);
    }

    public static void setCarPlateOpenAvoidLimitedNoticeCount(int i) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putIntValue("CAR_PLATE_OPEN_AVOID_LIMITED_NOTICE_COUNT", i);
    }

    public static int getMotorPlateOpenAvoidLimitedNoticeCount() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getIntValue("MOTOR_PATH_PLATE_LIMITED_COUNT", 0);
    }

    public static void setMotorPlateOpenAvoidLimitedNoticeCount(int i) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putIntValue("MOTOR_PATH_PLATE_LIMITED_COUNT", i);
    }

    public static long getMotorPlateLastSettingTime() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getLongValue("MOTOR_PATH_PLATE_LAST_SETTING_TIME", 0);
    }

    public static void setMotorPlateLastSettingTime(long j) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putLongValue("MOTOR_PATH_PLATE_LAST_SETTING_TIME", j);
    }

    public static long getMotorPlateOpenAvoidLimitedLastNoticeTime() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getLongValue("MOTOR_PATH_CAR_PLATE_LIMITED_TIME", 0);
    }

    public static void setMotorPlateOpenAvoidLimitedLastNoticeTime(long j) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putLongValue("MOTOR_PATH_CAR_PLATE_LIMITED_TIME", j);
    }

    public static long getCarPlateLastSettingTime() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getLongValue("CAR_PLATE_LAST_SETTING_TIME", 0);
    }

    public static void setCarPlateLastSettingTime(long j) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putLongValue("CAR_PLATE_LAST_SETTING_TIME", j);
    }

    public static long getCarPlateOpenAvoidLimitedLastNoticeTime() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getLongValue("CAR_PLATE_OPEN_AVOID_LIMITED_LAST_NOTICE_TIME", 0);
    }

    public static String getRouteBoardRedPointTip() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("route_board_red_point_tip", true) ? "1" : "0";
    }

    public static void setRouteBoardRedPointTip(String str) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("route_board_red_point_tip", TextUtils.equals("1", str));
    }

    public static String getVoiceGuideIsShown() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("vocie_guide_is_shown", false) ? "1" : "0";
    }

    public static void setVoiceGuideIsShown(float f) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("vocie_guide_is_shown", ((double) f) == 1.0d);
    }

    public static String getVUISwitchToastCount() {
        return String.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("vui_switch_toast_count", 0));
    }

    public static void setVUISwitchToastCount(int i) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue("vui_switch_toast_count", i);
    }

    public static String getVUISwitchToastTime() {
        return String.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getLongValue("vui_switch_toast_time", 0));
    }

    public static void setVUISwitchToastTime(long j) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putLongValue("vui_switch_toast_time", j);
    }

    public static String getVUIAudioPermissionDlgCount() {
        return String.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("vui_audio_permission_dlg_count", 0));
    }

    public static void setVUIAudioPermissionDlgCount(int i) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue("vui_audio_permission_dlg_count", i);
    }

    public static String getVUIAudioPermissionDlgTime() {
        return String.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getLongValue("vui_audio_permission_dlg_time", 0));
    }

    public static void setVUIAudioPermissionDlgTime(long j) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putLongValue("vui_audio_permission_dlg_time", j);
    }

    public static void setCarPlateOpenAvoidLimitedLastNoticeTime(long j) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putLongValue("CAR_PLATE_OPEN_AVOID_LIMITED_LAST_NOTICE_TIME", j);
    }

    public static void setAvoidLimitedPath(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue("CAR_AVOID_LIMITED_PATHS_SWITCH", z);
    }

    public static boolean isAvoidLimitedPath() {
        if (isEmptyAllCarList()) {
            return false;
        }
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.user_route_method_info);
        boolean booleanValue = mapSharePreference.getBooleanValue("CAR_AVOID_LIMITED_PATHS_SWITCH", false);
        SharedPreferences sharedPrefs = mapSharePreference.sharedPrefs();
        if (sharedPrefs.contains("CAR_AVOID_LIMITED_PATHS_SWITCH")) {
            return booleanValue;
        }
        if (sharedPrefs.contains("open_car_no")) {
            boolean booleanValue2 = mapSharePreference.getBooleanValue("open_car_no", false);
            sharedPrefs.edit().remove("open_car_no").apply();
            mapSharePreference.putBooleanValue("CAR_AVOID_LIMITED_PATHS_SWITCH", booleanValue2);
            return booleanValue2;
        }
        mapSharePreference.putBooleanValue("CAR_AVOID_LIMITED_PATHS_SWITCH", false);
        return booleanValue;
    }

    public static boolean isMotorAvoidLimitedPath() {
        if (TextUtils.isEmpty(getMotorPlateNum())) {
            return false;
        }
        String motorConfigValue = getMotorConfigValue(DriveSpUtil.MOTOR_PATH_LIMIT_KEY);
        if (TextUtils.isEmpty(motorConfigValue) || 1 != Float.valueOf(motorConfigValue).intValue()) {
            return false;
        }
        return true;
    }

    @Deprecated
    public static void setTruckAvoidLimitedPath(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue("truck_open_car_no", z);
    }

    public static void setLastCarsCount(int i) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putIntValue("last_cars_count", i);
    }

    public static void setLastTrucksCount(int i) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putIntValue("last_trucks_count", i);
    }

    public static int getLastCarsCount() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getIntValue("last_cars_count", 0);
    }

    public static int getLastTrucksCount() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getIntValue("last_trucks_count", 0);
    }

    public static void initLastCarsCount() {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.user_route_method_info);
        if (!mapSharePreference.contains("last_cars_count")) {
            List<Car> list = null;
            ato ato = (ato) a.a.a(ato.class);
            if (ato != null) {
                list = ato.a().a(1);
            }
            if (list == null || list.size() <= 0) {
                mapSharePreference.putIntValue("last_cars_count", 0);
            } else {
                mapSharePreference.putIntValue("last_cars_count", list.size());
            }
        }
    }

    public static void setTruckAvoidSwitch(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue(DriveSpUtil.TRUCK_AVOID_SWITCH, z);
    }

    public static boolean getTruckAvoidSwitch() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue(DriveSpUtil.TRUCK_AVOID_SWITCH, true);
    }

    public static boolean getMotorAvoidSwitch() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue(DriveSpUtil.MOTOR_AVOID_SWITCH, true);
    }

    public static void setMotorAvoidSwitch(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue(DriveSpUtil.MOTOR_AVOID_SWITCH, z);
    }

    public static void setTruckAvoidLimitedLoad(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue(DriveSpUtil.TRUCK_AVOID_WEIGHT, z);
    }

    public static boolean getTruckAvoidLimitedLoad() {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.user_route_method_info);
        boolean z = true;
        if (mapSharePreference.contains(DriveSpUtil.TRUCK_AVOID_WEIGHT)) {
            return mapSharePreference.getBooleanValue(DriveSpUtil.TRUCK_AVOID_WEIGHT, true);
        }
        Car carTruckInfo = getCarTruckInfo();
        if (carTruckInfo == null) {
            return true;
        }
        if (carTruckInfo.truckAvoidWeightLimit == 0) {
            z = false;
        }
        setTruckAvoidLimitedLoad(z);
        mapSharePreference.remove("truck_avoid_load");
        String valueOf = String.valueOf("truck_avoid_load");
        Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
        if (applicationContext == null) {
            return z;
        }
        Editor edit = applicationContext.getSharedPreferences(String.valueOf(SharePreferenceName.user_route_method_info), 0).edit();
        if (edit == null) {
            return z;
        }
        edit.remove(valueOf);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
            return z;
        }
        edit.commit();
        return z;
    }

    public static void removalTruckChoice() {
        if (!new MapSharePreference(SharePreferenceName.user_route_method_info).contains(DriveSpUtil.TRUCK_METHOD)) {
            putTruckRoutingChoice(getLastRoutingChoice());
        }
    }

    @Deprecated
    public static boolean isTruckAvoidLimitedLoad() {
        Car carTruckInfo = getCarTruckInfo();
        if (carTruckInfo == null || carTruckInfo.truckAvoidWeightLimit == 0) {
            return false;
        }
        return true;
    }

    public static boolean hasTruckPlateDesShown() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue("truck_car_no_des_shown", false);
    }

    public static void setTruckPlateDesShown(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue("truck_car_no_des_shown", z);
    }

    public static int putCarPlateNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        int i = 8;
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            i = ato.a().a(1, str);
        }
        return i;
    }

    public static int putTruckCarPlateNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        int i = 8;
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            i = ato.a().a(2, str);
        }
        return i;
    }

    public static boolean isNeedGuideTruck() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue(DriveSpUtil.need_guide_truck, true);
    }

    public static void setNeedGuideTruck(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue(DriveSpUtil.need_guide_truck, z);
    }

    public static boolean isNeedGuideMotor() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue(DriveSpUtil.need_guide_motor, true);
    }

    public static void setNeedGuideMotor(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue(DriveSpUtil.need_guide_motor, z);
    }

    public static void SetIsPlaySafeHomeResponseInfo(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue("safehome_play_response_info", z);
    }

    public static boolean isPlaySafeHomeResponseInfo() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue("safehome_play_response_info", true);
    }

    public static void setSafeHomeActivityShownTime(long j) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putLongValue("safehome_activity_show_time", j);
    }

    public static long getSafeHomeActivityShownTime() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getLongValue("safehome_activity_show_time", -1);
    }

    public static void putCarOutsideLimitShow() {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue("show_car_limit_flag", true);
    }

    public static boolean hasCarOutsideLimitShow() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue("show_car_limit_flag", false);
    }

    public static void setLightnessControltInfoShow(boolean z) {
        new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue("lightness_control_info", z);
    }

    public static boolean isLightnessControlInfoShow() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue("lightness_control_info", true);
    }

    public static double getDecimal(double d) {
        return new BigDecimal(d).setScale(4, 4).doubleValue();
    }

    public static String getLonLatKey(double d, double d2) {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat.format(d));
        sb.append(decimalFormat.format(d2));
        return sb.toString();
    }

    public static String getTrafficName(int i) {
        if (i == 11040) {
            return TrafficTopic.TAG_SHIGONG;
        }
        if (i == 11100) {
            return TrafficTopic.TAG_WATER;
        }
        switch (i) {
            case TrafficTopic.ACCIDENT_VEHICLE /*11010*/:
                return TrafficTopic.TAG_ACCIDENT_VEHICLE;
            case TrafficTopic.ACCIDENT_CRASH /*11011*/:
                return TrafficTopic.TAG_ACCIDENT_CRASH;
            case TrafficTopic.ACCIDENT_BARRIER /*11012*/:
                return TrafficTopic.TAG_ACCIDENT_BARRIER;
            default:
                switch (i) {
                    case TrafficTopic.JAM_SLOW /*11020*/:
                        return TrafficTopic.TAG_JAM_SLOW;
                    case TrafficTopic.JAM_CROWDED /*11021*/:
                        return TrafficTopic.TAG_JAM_CROWDED;
                    case TrafficTopic.JAM_STILL /*11022*/:
                        return TrafficTopic.TAG_JAM_STILL;
                    case TrafficTopic.JAM_UNBLOCKED /*11023*/:
                        return TrafficTopic.TAG_JAM_UNBLOCK;
                    default:
                        switch (i) {
                            case TrafficTopic.POLICE_CONTROL /*11030*/:
                                return TrafficTopic.TAG_POLICE_CONTROL;
                            case TrafficTopic.CONTROL_CONTROL /*11031*/:
                                return TrafficTopic.TAG_CONTROL_CONTROL;
                            case TrafficTopic.POLICE_DRUNK /*11032*/:
                                return TrafficTopic.TAG_POLICE_DRUNK;
                            case TrafficTopic.POLICE_LAW_ENFORCE /*11033*/:
                                return TrafficTopic.TAG_POLICE_LAW_ENFORCE;
                            default:
                                switch (i) {
                                    case TrafficTopic.CONTROL_CLOSE /*11050*/:
                                    case TrafficTopic.CONTROL_CLOSE_ROAD /*11051*/:
                                    case TrafficTopic.CONTROL_CLOSE_EXIT /*11052*/:
                                    case TrafficTopic.CONTROL_CLOSE_ENTRY /*11053*/:
                                    case TrafficTopic.CONTROL_CLOSE_ORDINARY_ACCIDENT /*11054*/:
                                    case TrafficTopic.CONTROL_CLOSE_MAJOR_ACCIDENT /*11055*/:
                                    case TrafficTopic.CONTROL_CLOSE_CONSTRUCTION /*11056*/:
                                    case TrafficTopic.CONTROL_CLOSE_FOG /*11057*/:
                                    case TrafficTopic.CONTROL_CLOSE_RAIN /*11058*/:
                                    case TrafficTopic.CONTROL_CLOSE_SNOW /*11059*/:
                                    case TrafficTopic.CONTROL_CLOSE_HAIL /*11061*/:
                                    case TrafficTopic.CONTROL_CLOSE_PONDING /*11062*/:
                                    case TrafficTopic.CONTROL_CLOSE_SNOWS /*11063*/:
                                    case TrafficTopic.CONTROL_CLOSE_ICE /*11064*/:
                                    case TrafficTopic.CONTROL_CLOSE_SUBSIDENCE /*11065*/:
                                        return TrafficTopic.TAG_CONTROL_CLOSE;
                                    case TrafficTopic.DANGER_CHILD /*11060*/:
                                        return TrafficTopic.TAG_DANGER_CHILD;
                                    default:
                                        switch (i) {
                                            case TrafficTopic.ANNOUNCEMENT /*11070*/:
                                                return TrafficTopic.TAG_ANNOUNCEMENT;
                                            case TrafficTopic.CAMERA_TAG /*11071*/:
                                                return TrafficTopic.TAG_CAMERA_CONTENT;
                                            case TrafficTopic.EMERGENCY_EVENT_TAG /*11072*/:
                                                return TrafficTopic.TAG_EMERGENCY_EVENT_CONTENT;
                                            default:
                                                return "";
                                        }
                                }
                        }
                }
        }
    }

    public static String getDiv() {
        return NetworkParam.getDiv();
    }

    public static String getDeviceToken() {
        return NetworkParam.getDeviceToken(AMapAppGlobal.getApplication());
    }

    public static String getTaobaoID() {
        return NetworkParam.getTaobaoID();
    }

    public static String getTaobaoDeviceId() {
        return UTDevice.getUtdid(AMapAppGlobal.getApplication());
    }

    public static String getTbtRouteVersion() {
        return RouteService.getRouteVersion();
    }

    public static ICarRouteResult parseBase64CarRouteResult(POI poi, POI poi2, String str, String str2) throws Exception {
        byte[] a2 = agv.a(str2);
        RouteCarResultData routeCarResultData = new RouteCarResultData(CalcRouteScene.SCENE_TRAFIC_REMIND_TMC);
        routeCarResultData.setFromPOI(poi);
        routeCarResultData.setToPOI(poi2);
        routeCarResultData.setMethod(str);
        routeCarResultData.parseTBTData(a2);
        tn.a().a(CalcRouteScene.SCENE_TRAFIC_REMIND_TMC, routeCarResultData.getCalcRouteResult());
        return routeCarResultData;
    }

    public static PageBundle buildSearchBundle(String str, String str2, POI poi, POI poi2, boolean z) {
        PageBundle pageBundle = new PageBundle();
        if (z) {
            pageBundle.putObject("selectedfor", SelectFor.FROM_POI);
        } else {
            pageBundle.putObject("selectedfor", SelectFor.TO_POI);
        }
        pageBundle.putInt("search_for", 1);
        pageBundle.putObject("route_type", RouteType.CAR);
        pageBundle.putString("hint", str2);
        pageBundle.putString(TrafficUtil.KEYWORD, str);
        pageBundle.putBoolean("isHideMyPosition", false);
        pageBundle.putObject("SelectPoiFromMapBean", new SelectPoiFromMapBean(poi, poi2, null));
        return pageBundle;
    }

    public static boolean isSamePoi(POI poi, POI poi2) {
        if (poi == null || poi2 == null) {
            return false;
        }
        String id = poi.getId();
        String id2 = poi2.getId();
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(id2) && id.equalsIgnoreCase(id2)) {
            return true;
        }
        if ("我的位置".equals(poi.getName()) && "我的位置".equals(poi2.getName())) {
            return true;
        }
        if (poi.getPoint() == null || poi2.getPoint() == null || poi.getPoint().x != poi2.getPoint().x || poi.getPoint().y != poi2.getPoint().y || !TextUtils.equals(poi.getName(), poi2.getName())) {
            return false;
        }
        return true;
    }

    public static String getTranslatedNetworkType() {
        int b = aaw.b(AMapAppGlobal.getApplication().getApplicationContext());
        ku.a().c("NaviMonitor", "[DriveUtil]getTranslatedNetworkType NetworkUtil.getNetWorkType = ".concat(String.valueOf(b)));
        switch (b) {
            case 0:
                return "0";
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";
            case 4:
                return "1";
            default:
                return "0";
        }
    }

    public static boolean isLegalPoiId(String str) {
        if (!TextUtils.isEmpty(str) && str.length() >= 10 && !a.matcher(str).matches()) {
            return true;
        }
        return false;
    }

    public static int genPointType(POI poi) {
        if (poi == null) {
            return -1;
        }
        int i = isLegalPoiId(poi.getId()) ? 2 : TextUtils.equals(AMapAppGlobal.getApplication().getString(R.string.my_location), poi.getName()) ? 0 : 1;
        return i;
    }

    public static Bitmap decodeResource(Resources resources, int i, int i2, int i3) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inSampleSize = a(options.outWidth, options.outHeight, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, i, options);
    }

    private static int a(int i, int i2, int i3, int i4) {
        int i5;
        if (i == 0 || i3 == 0) {
            return 1;
        }
        if (i2 > i4 || i > i3) {
            int i6 = i2 / 2;
            int i7 = i / 2;
            i5 = 1;
            while (i6 / i5 > i4 && i7 / i5 > i3) {
                i5 *= 2;
            }
        } else {
            i5 = 1;
        }
        if (i5 > 1) {
            return i5;
        }
        return 1;
    }

    public static String generateNaviIDString(LinkedHashSet<String> linkedHashSet) {
        StringBuilder sb = new StringBuilder();
        if (linkedHashSet == null || linkedHashSet.size() == 0) {
            return null;
        }
        int i = 0;
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (i != 0) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            i++;
            sb.append(str);
        }
        return sb.toString();
    }

    public static ICarRouteResult parseBase64NaviData(String str, POI poi, POI poi2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        RouteCarResultData routeCarResultData = new RouteCarResultData(CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN);
        routeCarResultData.setFromPOI(poi);
        routeCarResultData.setToPOI(poi2);
        routeCarResultData.setMethod(getLastRoutingChoice());
        routeCarResultData.parseTBTData(agv.a(str));
        tn.a().a(CalcRouteScene.SCENE_VOICE, routeCarResultData.getCalcRouteResult());
        return routeCarResultData;
    }

    public static void doOpenFeatureShowRouteResult(Context context, Uri uri) {
        GeoPoint geoPoint;
        GeoPoint geoPoint2;
        try {
            POI createPOI = POIFactory.createPOI();
            POI createPOI2 = POIFactory.createPOI();
            int a2 = a(uri, "dev", 0);
            String queryParameter = uri.getQueryParameter("sname");
            String queryParameter2 = uri.getQueryParameter("slat");
            String queryParameter3 = uri.getQueryParameter("slon");
            if (TextUtils.isEmpty(queryParameter) || TextUtils.isEmpty(queryParameter2) || TextUtils.isEmpty(queryParameter3) || !isLatLonValid(queryParameter2, queryParameter3)) {
                createPOI.setName("我的位置");
                createPOI.setPoint(LocationInstrument.getInstance().getLatestPosition());
            } else {
                Point a3 = cfg.a(Double.valueOf(uri.getQueryParameter("slat")).doubleValue(), Double.valueOf(uri.getQueryParameter("slon")).doubleValue());
                if (a2 == 1) {
                    geoPoint2 = cff.a(a3.x, a3.y);
                } else {
                    geoPoint2 = new GeoPoint(a3.x, a3.y);
                }
                createPOI.setName(queryParameter);
                createPOI.setPoint(geoPoint2);
            }
            String queryParameter4 = uri.getQueryParameter("dname");
            String queryParameter5 = uri.getQueryParameter("dlat");
            String queryParameter6 = uri.getQueryParameter("dlon");
            if (!TextUtils.isEmpty(queryParameter4) && !TextUtils.isEmpty(queryParameter5) && !TextUtils.isEmpty(queryParameter6)) {
                Point a4 = cfg.a(Double.valueOf(uri.getQueryParameter("dlat")).doubleValue(), Double.valueOf(uri.getQueryParameter("dlon")).doubleValue());
                if (a2 == 1) {
                    geoPoint = cff.a(a4.x, a4.y);
                } else {
                    geoPoint = new GeoPoint(a4.x, a4.y);
                }
                createPOI2.setName(queryParameter4);
                createPOI2.setPoint(geoPoint);
            }
            String a5 = dhw.a(Integer.parseInt(uri.getQueryParameter("m")));
            String queryParameter7 = uri.getQueryParameter("workType");
            uri.getQueryParameter("suggestType");
            if (TextUtils.isEmpty(queryParameter7) || !(Integer.parseInt(queryParameter7) == 1 || Integer.parseInt(queryParameter7) == 2)) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("bundle_key_route_type", RouteType.CAR);
                pageBundle.putObject("bundle_key_poi_start", createPOI);
                pageBundle.putObject("bundle_key_poi_end", createPOI2);
                pageBundle.putBoolean(BUNDLE_KEY_BOOL_SAVECOOKIE, false);
                pageBundle.putString("bundle_key_method", a5);
                pageBundle.putBoolean("bundle_key_from_scheme", true);
                startRoutePage(pageBundle);
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public static POIForRequest convertLocationToPoiForRequest(Location location, POIInfo[] pOIInfoArr, POIInfo[] pOIInfoArr2, POIInfo[] pOIInfoArr3, int i, int i2) {
        POIInfo[] pOIInfoArr4 = pOIInfoArr;
        POIForRequest pOIForRequest = new POIForRequest();
        if (location != null) {
            Bundle extras = location.getExtras();
            if (extras != null) {
                LocMapPoint locMapPoint = (LocMapPoint) extras.getSerializable(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_POS);
                if (locMapPoint != null) {
                    POIInfo pOIInfo = new POIInfo();
                    pOIInfo.latitude = (double) ((float) ((((double) locMapPoint.lat) / 1000000.0d) / 3.6d));
                    pOIInfo.longitude = (double) ((float) ((((double) locMapPoint.lon) / 1000000.0d) / 3.6d));
                    pOIInfo.name = AMapAppGlobal.getApplication().getString(R.string.my_location);
                    pOIInfo.type = 0;
                    if (pOIInfoArr4 != null && pOIInfoArr4.length > 0) {
                        pOIInfo.roadId = pOIInfoArr4[0].roadId;
                    }
                    pOIInfoArr4 = new POIInfo[]{pOIInfo};
                }
                pOIForRequest.speed = location.getSpeed();
                pOIForRequest.linkType = i;
                pOIForRequest.formWay = i2;
                pOIForRequest.direction = (float) extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_COURSE, -1.0d);
                pOIForRequest.reliability = (float) extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_COURSE_ACC, -1.0d);
                pOIForRequest.angleType = extras.getInt(LocationInstrument.LOCATION_EXTRAS_KEY_COURSE_TYPE, -1);
                pOIForRequest.angleGps = (float) extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURSE, -1.0d);
                pOIForRequest.angleComp = (float) extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_COMPASS_COURSE, -1.0d);
                pOIForRequest.radius = extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ERROR_DIST, -1.0f);
                pOIForRequest.sigType = extras.getInt(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_POS_TYPE, 0);
                pOIForRequest.gpsCredit = extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURE_ACC, 0.0f);
                pOIForRequest.fittingDir = extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE, -1.0f);
                pOIForRequest.fittingCredit = extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE_ACC, 0.0f);
                pOIForRequest.matchingDir = extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ROAD_COURSE, -1.0f);
                pOIForRequest.precision = location.getAccuracy();
            }
        } else {
            pOIForRequest.speed = 0.0f;
            pOIForRequest.linkType = 0;
            pOIForRequest.formWay = 1;
            pOIForRequest.direction = -1.0f;
            pOIForRequest.reliability = -1.0f;
            pOIForRequest.angleType = -1;
            pOIForRequest.angleGps = -1.0f;
            pOIForRequest.angleComp = -1.0f;
            pOIForRequest.radius = -1.0f;
            pOIForRequest.sigType = 0;
            pOIForRequest.gpsCredit = 0.0f;
            pOIForRequest.fittingDir = -1.0f;
            pOIForRequest.fittingCredit = 0.0f;
            pOIForRequest.matchingDir = -1.0f;
            pOIForRequest.precision = 0.0f;
        }
        pOIForRequest.start = pOIInfoArr4;
        pOIForRequest.via = pOIInfoArr2;
        pOIForRequest.end = pOIInfoArr3;
        return pOIForRequest;
    }

    public static void doOpenFeatureShowRouteResultNew(RouteType routeType, Uri uri) {
        String str;
        String str2;
        int i;
        POI createPOI = POIFactory.createPOI();
        POI createPOI2 = POIFactory.createPOI();
        try {
            int a2 = a(uri, LogItem.MM_C15_K4_TIME, -1);
            int a3 = a(uri, "dev", 0);
            int a4 = a(uri, SCHEME_PARAM_ENCRYPT, 0);
            String queryParameter = uri.getQueryParameter(SOURCE_APPLICATION);
            String queryParameter2 = uri.getQueryParameter("sname");
            String queryParameter3 = uri.getQueryParameter("slat");
            String queryParameter4 = uri.getQueryParameter("slon");
            if (queryParameter2 == null) {
                str = "";
            } else {
                str = queryParameter2.trim();
            }
            if (a4 == 1) {
                queryParameter3 = serverkey.amapDecodeV2(queryParameter3);
                queryParameter4 = serverkey.amapDecodeV2(queryParameter4);
            }
            if ((TextUtils.isEmpty(str) && !isLatLonValid(queryParameter3, queryParameter4)) || "我的位置".equals(str) || a(queryParameter3, queryParameter4)) {
                POI myLocationPoi = getMyLocationPoi();
                if (myLocationPoi != null) {
                    str = myLocationPoi.getName();
                    queryParameter3 = String.valueOf(myLocationPoi.getPoint().getLatitude());
                    queryParameter4 = String.valueOf(myLocationPoi.getPoint().getLongitude());
                }
            }
            boolean fromPOIParams = setFromPOIParams(createPOI, str, queryParameter3, queryParameter4, a3);
            String queryParameter5 = uri.getQueryParameter("dname");
            String queryParameter6 = uri.getQueryParameter("dlat");
            String queryParameter7 = uri.getQueryParameter("dlon");
            if (queryParameter5 == null) {
                str2 = "";
            } else {
                str2 = queryParameter5.trim();
            }
            String str3 = "0";
            if (a2 == RouteType.CAR.getValue()) {
                str3 = dhw.a(a(uri, "m", 0));
                i = a(uri, "mflag", 0);
            } else {
                i = 0;
            }
            if (a4 == 1) {
                queryParameter6 = serverkey.amapDecodeV2(queryParameter6);
                queryParameter7 = serverkey.amapDecodeV2(queryParameter7);
            }
            String queryParameter8 = uri.getQueryParameter(LocationParams.PARA_COMMON_DID);
            if (!TextUtils.isEmpty(queryParameter8)) {
                createPOI2.setId(queryParameter8);
            }
            setToPOIParams(createPOI2, str2, queryParameter6, queryParameter7, a3);
            if (fromPOIParams) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("bundle_key_route_type", routeType);
                pageBundle.putObject("bundle_key_poi_start", createPOI);
                pageBundle.putObject("bundle_key_poi_end", createPOI2);
                pageBundle.putString("bundle_key_keyword", createPOI.getName());
                pageBundle.putString("bundle_key_method", str3);
                if (!TextUtils.isEmpty(queryParameter)) {
                    pageBundle.putString(SOURCE_APPLICATION, queryParameter);
                }
                pageBundle.putInt("bundle_key_method_flag", i);
                pageBundle.putInt(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE, 1001);
                pageBundle.putBoolean("bundle_key_from_scheme", true);
                if (!TextUtils.isEmpty(str2) && "我的位置".equals(str2)) {
                    createPOI2.setPoint(LocationInstrument.getInstance().getLatestPosition());
                    pageBundle.putObject("bundle_key_poi_end", createPOI2);
                } else if (!TextUtils.isEmpty(queryParameter6) && !TextUtils.isEmpty(queryParameter7)) {
                    pageBundle.putObject("bundle_key_poi_end", createPOI2);
                } else if (!TextUtils.isEmpty(str2) && !"我的位置".equals(str2)) {
                    pageBundle.putString("bundle_key_end_poi_name_passed_in", str2);
                }
                bax bax = (bax) a.a.a(bax.class);
                if (bax != null) {
                    bax.b(pageBundle);
                }
                return;
            }
            if (TextUtils.isEmpty(queryParameter6) && TextUtils.isEmpty(queryParameter7)) {
                if (TextUtils.isEmpty(str2)) {
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putObject("bundle_key_route_type", routeType);
                    pageBundle2.putObject("bundle_key_poi_start", createPOI);
                    pageBundle2.putString("bundle_key_method", str3);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        pageBundle2.putString(SOURCE_APPLICATION, queryParameter);
                    }
                    pageBundle2.putInt("bundle_key_method_flag", i);
                    pageBundle2.putBoolean("bundle_key_from_scheme", true);
                    startRoutePage(pageBundle2);
                    return;
                } else if (!"我的位置".equals(str2)) {
                    PageBundle pageBundle3 = new PageBundle();
                    pageBundle3.putObject("bundle_key_route_type", routeType);
                    pageBundle3.putObject("bundle_key_poi_start", createPOI);
                    pageBundle3.putObject("bundle_key_poi_end", createPOI2);
                    pageBundle3.putString("bundle_key_keyword", createPOI2.getName());
                    pageBundle3.putString("bundle_key_method", str3);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        pageBundle3.putString(SOURCE_APPLICATION, queryParameter);
                    }
                    pageBundle3.putInt("bundle_key_method_flag", i);
                    pageBundle3.putInt(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE, 1002);
                    pageBundle3.putBoolean("bundle_key_from_scheme", true);
                    startRoutePage(pageBundle3);
                    return;
                }
            }
            PageBundle pageBundle4 = new PageBundle();
            pageBundle4.putObject("bundle_key_route_type", routeType);
            pageBundle4.putObject("bundle_key_poi_start", createPOI);
            pageBundle4.putObject("bundle_key_poi_end", createPOI2);
            pageBundle4.putString("bundle_key_method", str3);
            if (!TextUtils.isEmpty(queryParameter)) {
                pageBundle4.putString(SOURCE_APPLICATION, queryParameter);
            }
            pageBundle4.putInt("bundle_key_method_flag", i);
            pageBundle4.putBoolean("bundle_key_from_scheme", true);
            pageBundle4.putBoolean(BUNDLE_KEY_BOOL_SAVECOOKIE, false);
            startRoutePage(pageBundle4);
        } catch (Exception unused) {
        }
    }

    public static boolean isLatLonValid(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        try {
            double parseDouble = Double.parseDouble(str);
            double parseDouble2 = Double.parseDouble(str2);
            if (parseDouble < 0.1d || parseDouble > 180.0d || parseDouble2 < 0.1d || parseDouble2 > 180.0d) {
                return false;
            }
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private static boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        try {
            double parseDouble = Double.parseDouble(str);
            double parseDouble2 = Double.parseDouble(str2);
            if (parseDouble == 0.0d && parseDouble2 == 0.0d) {
                return true;
            }
        } catch (NumberFormatException unused) {
        }
        return false;
    }

    public static boolean setFromPOIParams(POI poi, String str, String str2, String str3, int i) {
        GeoPoint geoPoint;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            if (!TextUtils.isEmpty(str)) {
                poi.setName(str);
            } else {
                poi.setName("地图选定位置");
            }
            Point a2 = cfg.a(Double.valueOf(str2).doubleValue(), Double.valueOf(str3).doubleValue());
            if (i == 1) {
                geoPoint = cff.a(a2.x, a2.y);
            } else {
                geoPoint = new GeoPoint(a2.x, a2.y);
            }
            poi.setPoint(geoPoint);
        } else if (TextUtils.isEmpty(str) || "我的位置".equals(str)) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            poi.setName("我的位置");
            poi.setPoint(latestPosition);
        } else {
            poi.setName(str);
            return true;
        }
        return false;
    }

    public static void setToPOIParams(POI poi, String str, String str2, String str3, int i) {
        GeoPoint geoPoint;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            Point a2 = cfg.a(Double.valueOf(str2).doubleValue(), Double.valueOf(str3).doubleValue());
            if (i == 1) {
                geoPoint = cff.a(a2.x, a2.y);
            } else {
                geoPoint = new GeoPoint(a2.x, a2.y);
            }
            poi.setPoint(geoPoint);
            if (TextUtils.isEmpty(str)) {
                poi.setName("地图选定位置");
            }
        }
        if (!TextUtils.isEmpty(str)) {
            poi.setName(str);
            if ("我的位置".equals(str)) {
                poi.setPoint(LocationInstrument.getInstance().getLatestPosition());
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x024d A[Catch:{ Exception -> 0x02f9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x02b2 A[Catch:{ Exception -> 0x02f9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01d1 A[Catch:{ Exception -> 0x02f9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0228 A[Catch:{ Exception -> 0x02f9 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void startRoute(android.content.Context r22, com.autonavi.bundle.routecommon.model.RouteType r23, android.net.Uri r24) {
        /*
            r1 = r24
            com.autonavi.common.model.POI r2 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.model.POI r3 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            com.autonavi.common.PageBundle r4 = new com.autonavi.common.PageBundle
            r4.<init>()
            java.lang.String r5 = "encrypt"
            r6 = 0
            int r5 = a(r1, r5, r6)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r7 = "dev"
            int r7 = a(r1, r7, r6)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r8 = "sname"
            java.lang.String r8 = r1.getQueryParameter(r8)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r9 = "slat"
            java.lang.String r9 = r1.getQueryParameter(r9)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r10 = "slon"
            java.lang.String r10 = r1.getQueryParameter(r10)     // Catch:{ Exception -> 0x02f9 }
            if (r8 != 0) goto L_0x0036
            java.lang.String r8 = ""
            goto L_0x003a
        L_0x0036:
            java.lang.String r8 = r8.trim()     // Catch:{ Exception -> 0x02f9 }
        L_0x003a:
            r11 = 1
            if (r5 != r11) goto L_0x0045
            java.lang.String r9 = com.autonavi.server.aos.serverkey.amapDecodeV2(r9)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r10 = com.autonavi.server.aos.serverkey.amapDecodeV2(r10)     // Catch:{ Exception -> 0x02f9 }
        L_0x0045:
            java.lang.String r12 = "sid"
            java.lang.String r12 = r1.getQueryParameter(r12)     // Catch:{ Exception -> 0x02f9 }
            boolean r13 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x02f9 }
            if (r13 != 0) goto L_0x0055
            r2.setId(r12)     // Catch:{ Exception -> 0x02f9 }
        L_0x0055:
            boolean r12 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x02f9 }
            if (r12 == 0) goto L_0x0061
            boolean r12 = isLatLonValid(r9, r10)     // Catch:{ Exception -> 0x02f9 }
            if (r12 == 0) goto L_0x0070
        L_0x0061:
            java.lang.String r12 = "我的位置"
            boolean r12 = r12.equals(r8)     // Catch:{ Exception -> 0x02f9 }
            if (r12 != 0) goto L_0x0070
            boolean r12 = a(r9, r10)     // Catch:{ Exception -> 0x02f9 }
            if (r12 == 0) goto L_0x0092
        L_0x0070:
            com.autonavi.common.model.POI r12 = getMyLocationPoi()     // Catch:{ Exception -> 0x02f9 }
            if (r12 == 0) goto L_0x0092
            java.lang.String r8 = r12.getName()     // Catch:{ Exception -> 0x02f9 }
            com.autonavi.common.model.GeoPoint r9 = r12.getPoint()     // Catch:{ Exception -> 0x02f9 }
            double r9 = r9.getLatitude()     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x02f9 }
            com.autonavi.common.model.GeoPoint r10 = r12.getPoint()     // Catch:{ Exception -> 0x02f9 }
            double r12 = r10.getLongitude()     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r10 = java.lang.String.valueOf(r12)     // Catch:{ Exception -> 0x02f9 }
        L_0x0092:
            boolean r8 = setFromPOIParams(r2, r8, r9, r10, r7)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r9 = "dname"
            java.lang.String r9 = r1.getQueryParameter(r9)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r10 = "dlat"
            java.lang.String r10 = r1.getQueryParameter(r10)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r12 = "dlon"
            java.lang.String r12 = r1.getQueryParameter(r12)     // Catch:{ Exception -> 0x02f9 }
            if (r9 != 0) goto L_0x00ad
            java.lang.String r9 = ""
            goto L_0x00b1
        L_0x00ad:
            java.lang.String r9 = r9.trim()     // Catch:{ Exception -> 0x02f9 }
        L_0x00b1:
            if (r5 != r11) goto L_0x00bb
            java.lang.String r10 = com.autonavi.server.aos.serverkey.amapDecodeV2(r10)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r12 = com.autonavi.server.aos.serverkey.amapDecodeV2(r12)     // Catch:{ Exception -> 0x02f9 }
        L_0x00bb:
            java.lang.String r5 = "did"
            java.lang.String r5 = r1.getQueryParameter(r5)     // Catch:{ Exception -> 0x02f9 }
            boolean r13 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x02f9 }
            if (r13 != 0) goto L_0x00ca
            r3.setId(r5)     // Catch:{ Exception -> 0x02f9 }
        L_0x00ca:
            setToPOIParams(r3, r9, r10, r12, r7)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r5 = getLastRoutingChoice()     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r13 = "vialons"
            java.lang.String r13 = r1.getQueryParameter(r13)     // Catch:{ Exception -> 0x01b5 }
            java.lang.String r14 = "vialats"
            java.lang.String r14 = r1.getQueryParameter(r14)     // Catch:{ Exception -> 0x01b5 }
            java.lang.String r15 = "vianames"
            java.lang.String r15 = r1.getQueryParameter(r15)     // Catch:{ Exception -> 0x01b5 }
            java.lang.String r6 = "vian"
            r1.getQueryParameter(r6)     // Catch:{ Exception -> 0x01b5 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Exception -> 0x01b5 }
            r6.<init>()     // Catch:{ Exception -> 0x01b5 }
            boolean r16 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Exception -> 0x01b5 }
            if (r16 != 0) goto L_0x01ac
            boolean r16 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Exception -> 0x01b5 }
            if (r16 != 0) goto L_0x01ac
            java.lang.String r11 = "\\|"
            java.lang.String[] r11 = r13.split(r11)     // Catch:{ Exception -> 0x01b5 }
            java.lang.String r13 = "\\|"
            java.lang.String[] r13 = r14.split(r13)     // Catch:{ Exception -> 0x01b5 }
            java.lang.String r14 = "\\|"
            java.lang.String[] r14 = r15.split(r14)     // Catch:{ Exception -> 0x01b5 }
            int r15 = r11.length     // Catch:{ Exception -> 0x01b5 }
            r17 = r12
            int r12 = r13.length     // Catch:{ Exception -> 0x01a4 }
            if (r15 != r12) goto L_0x019d
            int r12 = r11.length     // Catch:{ Exception -> 0x01a4 }
            if (r12 <= 0) goto L_0x019d
            r12 = 0
        L_0x0119:
            int r15 = r11.length     // Catch:{ Exception -> 0x01a4 }
            if (r12 >= r15) goto L_0x0166
            r15 = r11[r12]     // Catch:{ Exception -> 0x01a4 }
            java.lang.Double r15 = java.lang.Double.valueOf(r15)     // Catch:{ Exception -> 0x01a4 }
            r18 = r10
            r19 = r11
            double r10 = r15.doubleValue()     // Catch:{ Exception -> 0x0160 }
            r15 = r13[r12]     // Catch:{ Exception -> 0x0160 }
            java.lang.Double r15 = java.lang.Double.valueOf(r15)     // Catch:{ Exception -> 0x0160 }
            r20 = r8
            r21 = r9
            double r8 = r15.doubleValue()     // Catch:{ Exception -> 0x019b }
            android.graphics.Point r8 = defpackage.cfg.a(r8, r10)     // Catch:{ Exception -> 0x019b }
            r9 = 1
            if (r7 != r9) goto L_0x0148
            int r9 = r8.x     // Catch:{ Exception -> 0x019b }
            int r8 = r8.y     // Catch:{ Exception -> 0x019b }
            com.autonavi.common.model.GeoPoint r8 = defpackage.cff.a(r9, r8)     // Catch:{ Exception -> 0x019b }
            goto L_0x0152
        L_0x0148:
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x019b }
            int r10 = r8.x     // Catch:{ Exception -> 0x019b }
            int r8 = r8.y     // Catch:{ Exception -> 0x019b }
            r9.<init>(r10, r8)     // Catch:{ Exception -> 0x019b }
            r8 = r9
        L_0x0152:
            r6.add(r8)     // Catch:{ Exception -> 0x019b }
            int r12 = r12 + 1
            r10 = r18
            r11 = r19
            r8 = r20
            r9 = r21
            goto L_0x0119
        L_0x0160:
            r0 = move-exception
            r20 = r8
            r21 = r9
            goto L_0x01be
        L_0x0166:
            r20 = r8
            r21 = r9
            r18 = r10
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ Exception -> 0x019b }
            r7.<init>()     // Catch:{ Exception -> 0x019b }
            r8 = 0
        L_0x0172:
            int r9 = r6.size()     // Catch:{ Exception -> 0x019b }
            if (r8 >= r9) goto L_0x0195
            int r9 = com.autonavi.minimap.R.string.autonavi_util_pass_name     // Catch:{ Exception -> 0x019b }
            r10 = r22
            java.lang.String r9 = r10.getString(r9)     // Catch:{ Exception -> 0x019b }
            int r11 = r14.length     // Catch:{ Exception -> 0x019b }
            if (r8 > r11) goto L_0x0185
            r9 = r14[r8]     // Catch:{ Exception -> 0x019b }
        L_0x0185:
            java.lang.Object r11 = r6.get(r8)     // Catch:{ Exception -> 0x019b }
            com.autonavi.common.model.GeoPoint r11 = (com.autonavi.common.model.GeoPoint) r11     // Catch:{ Exception -> 0x019b }
            com.autonavi.common.model.POI r9 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r9, r11)     // Catch:{ Exception -> 0x019b }
            r7.add(r9)     // Catch:{ Exception -> 0x019b }
            int r8 = r8 + 1
            goto L_0x0172
        L_0x0195:
            java.lang.String r6 = "bundle_key_poi_mids"
            r4.putObject(r6, r7)     // Catch:{ Exception -> 0x019b }
            goto L_0x01c2
        L_0x019b:
            r0 = move-exception
            goto L_0x01be
        L_0x019d:
            r20 = r8
            r21 = r9
            r18 = r10
            goto L_0x01c2
        L_0x01a4:
            r0 = move-exception
            r20 = r8
            r21 = r9
            r18 = r10
            goto L_0x01be
        L_0x01ac:
            r20 = r8
            r21 = r9
            r18 = r10
            r17 = r12
            goto L_0x01c2
        L_0x01b5:
            r0 = move-exception
            r20 = r8
            r21 = r9
            r18 = r10
            r17 = r12
        L_0x01be:
            r6 = r0
            r6.printStackTrace()     // Catch:{ Exception -> 0x02f9 }
        L_0x01c2:
            java.lang.String r6 = "sourceApplication"
            java.lang.String r6 = r1.getQueryParameter(r6)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r7 = "bundle_key_source_app"
            r4.putString(r7, r6)     // Catch:{ Exception -> 0x02f9 }
            r6 = 0
            if (r1 == 0) goto L_0x0226
            java.lang.String r7 = "backScheme"
            java.lang.String r7 = r1.getQueryParameter(r7)     // Catch:{ Exception -> 0x02f9 }
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x02f9 }
            if (r8 != 0) goto L_0x01e2
            android.net.Uri r7 = android.net.Uri.parse(r7)     // Catch:{ Exception -> 0x02f9 }
            goto L_0x01e3
        L_0x01e2:
            r7 = r6
        L_0x01e3:
            java.lang.String r8 = "sourceApplication"
            java.lang.String r8 = r1.getQueryParameter(r8)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r9 = "backCategory"
            java.lang.String r9 = r1.getQueryParameter(r9)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r10 = "backAction"
            java.lang.String r1 = r1.getQueryParameter(r10)     // Catch:{ Exception -> 0x02f9 }
            boolean r10 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x02f9 }
            if (r10 == 0) goto L_0x0206
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x02f9 }
            int r10 = com.autonavi.minimap.R.string.third_part     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r8 = r8.getString(r10)     // Catch:{ Exception -> 0x02f9 }
        L_0x0206:
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r10 == 0) goto L_0x020e
            java.lang.String r9 = "android.intent.category.DEFAULT"
        L_0x020e:
            if (r7 == 0) goto L_0x0226
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r10 != 0) goto L_0x0226
            dlg r6 = new dlg     // Catch:{ Exception -> 0x02f9 }
            r6.<init>()     // Catch:{ Exception -> 0x02f9 }
            r10 = 1
            r6.a = r10     // Catch:{ Exception -> 0x02f9 }
            r6.b = r7     // Catch:{ Exception -> 0x02f9 }
            r6.c = r8     // Catch:{ Exception -> 0x02f9 }
            r6.d = r9     // Catch:{ Exception -> 0x02f9 }
            r6.e = r1     // Catch:{ Exception -> 0x02f9 }
        L_0x0226:
            if (r6 == 0) goto L_0x0234
            java.lang.String r1 = "key_action"
            java.lang.String r7 = "actiono_back_scheme"
            r4.putObject(r1, r7)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "key_back_scheme_param"
            r4.putObject(r1, r6)     // Catch:{ Exception -> 0x02f9 }
        L_0x0234:
            java.lang.String r1 = "bundle_key_route_type"
            r6 = r23
            r4.putObject(r1, r6)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_poi_start"
            r4.putObject(r1, r2)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_method"
            r4.putString(r1, r5)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_from_scheme"
            r5 = 1
            r4.putBoolean(r1, r5)     // Catch:{ Exception -> 0x02f9 }
            if (r20 == 0) goto L_0x02b2
            java.lang.String r1 = "bundle_key_poi_end"
            r4.putObject(r1, r3)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_keyword"
            java.lang.String r2 = r2.getName()     // Catch:{ Exception -> 0x02f9 }
            r4.putString(r1, r2)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_request_code"
            r2 = 1001(0x3e9, float:1.403E-42)
            r4.putInt(r1, r2)     // Catch:{ Exception -> 0x02f9 }
            r9 = r21
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r1 != 0) goto L_0x0284
            java.lang.String r1 = "我的位置"
            boolean r1 = r1.equals(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r1 == 0) goto L_0x0284
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x02f9 }
            com.autonavi.common.model.GeoPoint r1 = r1.getLatestPosition()     // Catch:{ Exception -> 0x02f9 }
            r3.setPoint(r1)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_poi_end"
            r4.putObject(r1, r3)     // Catch:{ Exception -> 0x02f9 }
            goto L_0x02ae
        L_0x0284:
            r10 = r18
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x02f9 }
            if (r1 != 0) goto L_0x029a
            r12 = r17
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x02f9 }
            if (r1 != 0) goto L_0x029a
            java.lang.String r1 = "bundle_key_poi_end"
            r4.putObject(r1, r3)     // Catch:{ Exception -> 0x02f9 }
            goto L_0x02ae
        L_0x029a:
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r1 != 0) goto L_0x02ae
            java.lang.String r1 = "我的位置"
            boolean r1 = r1.equals(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r1 != 0) goto L_0x02ae
            java.lang.String r1 = "bundle_key_end_poi_name_passed_in"
            r4.putString(r1, r9)     // Catch:{ Exception -> 0x02f9 }
        L_0x02ae:
            startRoutePage(r4)     // Catch:{ Exception -> 0x02f9 }
            return
        L_0x02b2:
            r12 = r17
            r10 = r18
            r9 = r21
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x02f9 }
            if (r1 == 0) goto L_0x02f0
            boolean r1 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Exception -> 0x02f9 }
            if (r1 == 0) goto L_0x02f0
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r1 == 0) goto L_0x02ce
            startRoutePage(r4)     // Catch:{ Exception -> 0x02f9 }
            return
        L_0x02ce:
            java.lang.String r1 = "我的位置"
            boolean r1 = r1.equals(r9)     // Catch:{ Exception -> 0x02f9 }
            if (r1 != 0) goto L_0x02f0
            java.lang.String r1 = "bundle_key_poi_end"
            r4.putObject(r1, r3)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_keyword"
            java.lang.String r2 = r3.getName()     // Catch:{ Exception -> 0x02f9 }
            r4.putString(r1, r2)     // Catch:{ Exception -> 0x02f9 }
            java.lang.String r1 = "bundle_key_request_code"
            r2 = 1002(0x3ea, float:1.404E-42)
            r4.putInt(r1, r2)     // Catch:{ Exception -> 0x02f9 }
            startRoutePage(r4)     // Catch:{ Exception -> 0x02f9 }
            return
        L_0x02f0:
            java.lang.String r1 = "bundle_key_poi_end"
            r4.putObject(r1, r3)     // Catch:{ Exception -> 0x02f9 }
            startRoutePage(r4)     // Catch:{ Exception -> 0x02f9 }
            return
        L_0x02f9:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drivecommon.tools.DriveUtil.startRoute(android.content.Context, com.autonavi.bundle.routecommon.model.RouteType, android.net.Uri):void");
    }

    private static int a(Uri uri, String str, int i) {
        try {
            return Integer.parseInt(uri.getQueryParameter(str));
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static void refreshTraffic(bty bty) {
        if (bty != null && bty.s()) {
            bty.t();
        }
    }

    public static void doOpenFeatureFromTo(RouteType routeType, Uri uri) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("bundle_key_route_type", routeType);
        pageBundle.putString("bundle_key_source_app", uri.getQueryParameter(SOURCE_APPLICATION));
        pageBundle.putBoolean("bundle_key_auto_route", false);
        pageBundle.putBoolean("bundle_key_from_scheme", true);
        startRoutePage(pageBundle);
    }

    public static int getContentOptions(int i) {
        int i2 = 32;
        int i3 = 65536;
        if (getCarTypeByVtype(i) == 1) {
            if (!getTruckAvoidSwitch()) {
                i2 = 65536;
            }
            i3 = 65536 | i2;
            if (!getTruckAvoidLimitedLoad()) {
                i2 = 131072;
            }
            AMapLog.d("DriveUtil", "getContentOptions---contentOptions=".concat(String.valueOf(i3)));
            return i3;
        } else if (!isAvoidLimitedPath()) {
            i2 = 65536;
        }
        i3 |= i2;
        AMapLog.d("DriveUtil", "getContentOptions---contentOptions=".concat(String.valueOf(i3)));
        return i3;
    }

    @NonNull
    public static GeoPoint getGeoPointFromPOI(@Nullable POI poi) {
        if (poi == null) {
            return new GeoPoint();
        }
        ArrayList<GeoPoint> entranceList = poi.getEntranceList();
        if (entranceList == null || entranceList.size() <= 0 || entranceList.get(0) == null) {
            return poi.getPoint();
        }
        return entranceList.get(0);
    }

    public static boolean isNeedFilterBluetoothSpeaker() {
        return 19 < VERSION.SDK_INT && VERSION.SDK_INT <= 22;
    }

    public static int checkHasGps(Context context) {
        if (context == null) {
            return -1;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        boolean z = false;
        if (locationManager == null) {
            try {
                return R.string.drive_gps_close_title;
            } catch (Exception unused) {
            }
        } else {
            List<String> allProviders = locationManager.getAllProviders();
            boolean contains = allProviders != null ? allProviders.contains(WidgetType.GPS) : false;
            if (contains && VERSION.SDK_INT >= 19) {
                int i = Secure.getInt(context.getContentResolver(), "location_mode", 0);
                if (i == 0) {
                    return R.string.drive_gps_close_title;
                }
                if (!(i == 3 || i == 1)) {
                    return R.string.drive_gps_close_title;
                }
            }
            if (!locationManager.isProviderEnabled(WidgetType.GPS)) {
                return R.string.drive_gps_close_title;
            }
            z = contains;
            if (!z) {
                return R.string.drive_gps_close_title;
            }
            return 100;
        }
    }

    public static int getNavigationBarHeight(Context context) {
        if (hasNavBar(context)) {
            Resources resources = context.getResources();
            int identifier = resources.getIdentifier("navigation_bar_height", ResUtils.DIMEN, "android");
            if (identifier > 0) {
                return resources.getDimensionPixelSize(identifier);
            }
        }
        return 0;
    }

    public static boolean hasNavBar(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (identifier == 0) {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
        boolean z = resources.getBoolean(identifier);
        String a2 = a();
        if ("1".equals(a2)) {
            z = false;
        } else if ("0".equals(a2)) {
            z = true;
        }
        return z;
    }

    private static String a() {
        if (VERSION.SDK_INT >= 19) {
            try {
                Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
                declaredMethod.setAccessible(true);
                return (String) declaredMethod.invoke(null, new Object[]{"qemu.hw.mainkeys"});
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    public static int getLinkType(Route route, LocInfo locInfo) {
        byte b = 0;
        if (route == null || locInfo == null) {
            return 0;
        }
        LocMatchInfo[] locMatchInfoArr = locInfo.MatchInfos;
        if (locMatchInfoArr == null || locMatchInfoArr.length == 0) {
            return 0;
        }
        int length = locMatchInfoArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            LocMatchInfo locMatchInfo = locMatchInfoArr[i];
            if (locMatchInfo.pathId == route.getPathId()) {
                b = locMatchInfo.linkType;
                break;
            }
            i++;
        }
        return b;
    }

    public static int getFormway(Route route, LocInfo locInfo) {
        byte b = 1;
        if (route == null || locInfo == null) {
            return 1;
        }
        LocMatchInfo[] locMatchInfoArr = locInfo.MatchInfos;
        if (locMatchInfoArr == null || locMatchInfoArr.length == 0) {
            return 1;
        }
        int length = locMatchInfoArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            LocMatchInfo locMatchInfo = locMatchInfoArr[i];
            if (locMatchInfo.pathId == route.getPathId()) {
                b = locMatchInfo.formWay;
                break;
            }
            i++;
        }
        return b;
    }

    public static String getChoiceString(String str, int i) {
        String str2 = str.split("\\|").length > 1 ? "..." : "";
        if (str.contains("4")) {
            return "避免收费".concat(String.valueOf(str2));
        }
        if (str.contains("8")) {
            return "不走高速".concat(String.valueOf(str2));
        }
        if (str.contains("2")) {
            return "躲避拥堵".concat(String.valueOf(str2));
        }
        if (str.contains("16")) {
            return "高速优先".concat(String.valueOf(str2));
        }
        return i == 1 ? "偏好设置" : "智能推荐";
    }

    public static int getChoiceConstrainCode() {
        String[] split;
        String lastRoutingChoice = getLastRoutingChoice();
        AMapLog.d("sduaxia", "getChoiceConstrainCode--spChoice=".concat(String.valueOf(lastRoutingChoice)));
        int i = 0;
        for (String str : lastRoutingChoice.split("\\|")) {
            if (TextUtils.isDigitsOnly(str)) {
                i |= Integer.parseInt(str);
            }
        }
        AMapLog.d("sduaxia", "getChoiceConstrainCode--constrainCode=".concat(String.valueOf(i)));
        return i;
    }

    public static int getCameraMaxLimitSpeed(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return 0;
        }
        int i = 0;
        for (int i2 : iArr) {
            if (i2 < 255 && i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    public static NaviInfo parseNaviInfo(JSONObject jSONObject) {
        NaviInfo naviInfo = new NaviInfo();
        naviInfo.pathID = jSONObject.optLong("pathID");
        naviInfo.type = jSONObject.optInt("type");
        naviInfo.naviInfoFlag = jSONObject.optInt("naviInfoFlag", 0);
        JSONArray optJSONArray = jSONObject.optJSONArray("naviInfoData");
        if (optJSONArray == null || optJSONArray.length() <= 0) {
            naviInfo.naviInfoData = new NaviInfoPanel[1];
            NaviInfoPanel naviInfoPanel = new NaviInfoPanel();
            naviInfoPanel.maneuverID = jSONObject.optInt("maneuverID");
            naviInfoPanel.segmentRemainDist = jSONObject.optInt("segmentRemainDist");
            naviInfoPanel.segmentRemainTime = jSONObject.optInt("segmentRemainTime");
            naviInfoPanel.nextRouteName = jSONObject.optString("nextRouteName");
            naviInfoPanel.nextRoadNameSegIdx = jSONObject.optInt("nextRoadNameSegIdx");
            naviInfoPanel.nextRoadNameLinkIdx = jSONObject.optInt("nextRoadNameLinkIdx");
            naviInfo.naviInfoData[0] = naviInfoPanel;
        } else {
            int length = optJSONArray.length();
            naviInfo.naviInfoData = new NaviInfoPanel[length];
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                NaviInfoPanel naviInfoPanel2 = new NaviInfoPanel();
                naviInfoPanel2.maneuverID = optJSONObject.optInt("maneuverID");
                naviInfoPanel2.segmentRemainDist = optJSONObject.optInt("segmentRemainDist");
                naviInfoPanel2.segmentRemainTime = optJSONObject.optInt("segmentRemainTime");
                naviInfoPanel2.nextRouteName = optJSONObject.optString("nextRouteName");
                naviInfoPanel2.nextRoadNameSegIdx = optJSONObject.optInt("nextRoadNameSegIdx");
                naviInfoPanel2.nextRoadNameLinkIdx = optJSONObject.optInt("nextRoadNameLinkIdx");
                naviInfo.naviInfoData[i] = naviInfoPanel2;
            }
        }
        naviInfo.routeRemainDist = jSONObject.optInt("routeRemainDist");
        naviInfo.routeRemainTime = jSONObject.optInt("routeRemainTime");
        naviInfo.routeRemainLightCount = jSONObject.optInt("routeRemainLightCount");
        naviInfo.curSegIdx = jSONObject.optInt("curSegIdx");
        naviInfo.curLinkIdx = jSONObject.optInt("curLinkIdx");
        naviInfo.curPointIdx = jSONObject.optInt("curPointIdx");
        naviInfo.curRoadClass = jSONObject.optInt("curRoadClass");
        naviInfo.curRouteName = jSONObject.optString("curRouteName");
        naviInfo.ringOutCnt = jSONObject.optInt("ringOutCnt");
        naviInfo.driveTime = jSONObject.optInt("driveTime");
        naviInfo.driveDist = jSONObject.optInt("driveDist");
        naviInfo.cityCode = jSONObject.optInt("cityCode");
        naviInfo.curLinkSpeed = jSONObject.optInt("curLinkSpeed");
        naviInfo.segTipsDis = jSONObject.optInt("segTipsDis");
        naviInfo.crossManeuverID = jSONObject.optInt("crossManeuverID");
        naviInfo.nextCrossCnt = jSONObject.optInt("nextCrossCnt");
        return naviInfo;
    }

    public static PathTrafficEventInfo[] parsePathTrafficEventInfo(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.has("infos") && jSONObject.has(NewHtcHomeBadger.COUNT)) {
            JSONArray optJSONArray = jSONObject.optJSONArray("infos");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                PathTrafficEventInfo[] pathTrafficEventInfoArr = new PathTrafficEventInfo[length];
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    PathTrafficEventInfo pathTrafficEventInfo = new PathTrafficEventInfo();
                    pathTrafficEventInfo.pathID = (long) optJSONObject.optInt("pathID");
                    pathTrafficEventInfo.action = optJSONObject.optInt(SocialConstants.PARAM_ACT);
                    pathTrafficEventInfo.eventCount = optJSONObject.optInt("eventCount");
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("eventInfos");
                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                        int length2 = optJSONArray2.length();
                        TrafficEventInfo[] trafficEventInfoArr = new TrafficEventInfo[length2];
                        for (int i2 = 0; i2 < length2; i2++) {
                            JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                            TrafficEventInfo trafficEventInfo = new TrafficEventInfo();
                            trafficEventInfo.type = optJSONObject2.optInt("type");
                            trafficEventInfo.id = optJSONObject2.optInt("id");
                            trafficEventInfo.layer = optJSONObject2.optInt(WidgetType.LAYER);
                            trafficEventInfo.layerTag = optJSONObject2.optInt("layerTag");
                            trafficEventInfo.official = optJSONObject2.optBoolean("official");
                            trafficEventInfo.detail = optJSONObject2.optBoolean("detail");
                            JSONArray optJSONArray3 = optJSONObject2.optJSONArray("coord2D");
                            if (optJSONArray3 != null && optJSONArray3.length() > 1) {
                                trafficEventInfo.lon = optJSONArray3.optDouble(0);
                                trafficEventInfo.lat = optJSONArray3.optDouble(1);
                            }
                            JSONArray optJSONArray4 = optJSONObject2.optJSONArray("coord3D");
                            if (optJSONArray4 != null && optJSONArray4.length() > 1) {
                                trafficEventInfo.lon3D = optJSONArray4.optDouble(0);
                                trafficEventInfo.lat3D = optJSONArray4.optDouble(1);
                            }
                            trafficEventInfo.z3D = optJSONObject2.optDouble("z3D");
                            trafficEventInfo.lane = optJSONObject2.optString("lane");
                            trafficEventInfoArr[i2] = trafficEventInfo;
                        }
                        pathTrafficEventInfo.eventInfoArray = trafficEventInfoArr;
                    }
                    pathTrafficEventInfoArr[i] = pathTrafficEventInfo;
                }
                return pathTrafficEventInfoArr;
            }
        }
        return null;
    }

    public static ExitDirectionInfo parseExitDirectionInfo(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        ExitDirectionInfo exitDirectionInfo = new ExitDirectionInfo();
        JSONArray optJSONArray = jSONObject.optJSONArray("directionInfo");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            exitDirectionInfo.directionNum = optJSONArray.length();
            exitDirectionInfo.directionInfo = new String[optJSONArray.length()];
            for (int i = 0; i < optJSONArray.length(); i++) {
                exitDirectionInfo.directionInfo[i] = optJSONArray.optString(i);
            }
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("");
        if (optJSONArray2 != null && optJSONArray2.length() > 0) {
            exitDirectionInfo.exitNameNum = optJSONArray2.length();
            exitDirectionInfo.exitNameInfo = new String[optJSONArray2.length()];
            for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                exitDirectionInfo.exitNameInfo[i2] = optJSONArray2.optString(i2);
            }
        }
        return exitDirectionInfo;
    }

    public static boolean isOpenDriveJointDebuggingTools() {
        if (!bno.a) {
            return false;
        }
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("drive_joint_debugging_tools", false);
    }

    public static void setOpenDriveJointDebuggingTools(boolean z) {
        if (bno.a) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("drive_joint_debugging_tools", z);
            if (z) {
                ToastHelper.showLongToast("开启驾车联调测试环境");
            } else {
                ToastHelper.showLongToast("关闭驾车联调测试环境");
            }
        }
    }

    public static int getVtype(@Nullable Car car, int i) {
        int i2 = car == null ? 0 : car.vehiclePowerType;
        if (i == 0) {
            switch (i2) {
                case 1:
                    return 2;
                case 2:
                    return 4;
                default:
                    return 0;
            }
        } else if (i == 11) {
            return 11;
        } else {
            switch (i2) {
                case 1:
                    return 3;
                case 2:
                    return 5;
                default:
                    return 1;
            }
        }
    }

    public static void resetRgeoPOI(POI poi, te teVar) {
        if (!(poi == null || teVar == null || !"我的位置".equals(poi.getName()))) {
            poi.setName(teVar.a);
            poi.setAdCode(teVar.b);
        }
    }

    public static List<RdCameraPaymentItem> getLocalRdCameraPaymentData() {
        List<RdCameraPaymentItem> all = RdCameraDBHelper.getInstance(null).getAll();
        if (all.size() > 0) {
            long longValue = all.get(0).navi_timestamp.longValue() - 2592000;
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).navi_timestamp.longValue() < longValue) {
                    all.remove(i);
                }
            }
            RdCameraDBHelper.getInstance(null).deleteDataBefore(Long.valueOf(longValue));
        }
        return all;
    }

    public static void showSaveRouteFragment(cor cor) {
        if (cor.g() == 1) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_route_type", RouteType.CAR);
            pageBundle.putObject("bundle_key_poi_start", cor.a());
            pageBundle.putObject("bundle_key_poi_end", cor.b());
            pageBundle.putObject("bundle_key_poi_mids", cor.c());
            pageBundle.putBoolean("bundle_key_auto_route", true);
            pageBundle.putBoolean("key_favorites", true);
            pageBundle.putObject("original_route", cor);
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                bax.b(pageBundle);
            }
        }
    }

    public static String getMotorPlateNum() {
        return getMotorPlateNum(getMotorInfo());
    }

    public static String getMotorConfigValue(String str) {
        String str2 = DriveSpUtil.NAMESPACE_MOTOR_SETTING;
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return "";
        }
        return new MapSharePreference(str2).getStringValue(str, "");
    }

    public static String getMotorPlateNum(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            str2 = new JSONObject(str).getString("motorPlateNum");
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = null;
        }
        return str2;
    }

    public static String getMotorInfo() {
        String str = bny.c;
        if (bim.aa().a((String) "201", (String) "601") == null) {
            return str;
        }
        JsonDatasWithType a2 = bim.aa().a((String) "201", (String) "601");
        if (a2 != null) {
            try {
                if (!(a2.jsonDataWithId == null || a2.jsonDataWithId.get(0) == null || TextUtils.isEmpty(a2.jsonDataWithId.get(0).data))) {
                    str = new JSONObject(a2.jsonDataWithId.get(0).data).optString("value");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    private static Rect a(double[] dArr) {
        if (dArr == null || dArr.length != 4) {
            return new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        GeoPoint geoPoint = new GeoPoint(dArr[0], dArr[1]);
        GeoPoint geoPoint2 = new GeoPoint(dArr[2], dArr[3]);
        return new Rect(geoPoint.x, geoPoint.y, geoPoint2.x, geoPoint2.y);
    }

    public static Rect getRouteResultBound(@NonNull CalcRouteResult calcRouteResult) {
        Rect rect = null;
        for (int i = 0; i < calcRouteResult.getPathCount(); i++) {
            Route route = calcRouteResult.getRoute(i);
            if (route != null) {
                Rect a2 = a(route.getRouteBound(0, 0, 0));
                if (rect == null) {
                    rect = a2;
                } else {
                    rect.union(a2);
                }
            }
        }
        return rect;
    }

    public static Rect getRouteBound(@NonNull Route route) {
        return a(route.getRouteBound(0, 0, 0));
    }

    public static boolean getTrafficMode(Context context) {
        return DriveSpUtil.getBool(context, "traffic", false);
    }

    public static String getMotorCC(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            str2 = new JSONObject(str).getString("sweptVolume");
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = null;
        }
        return str2;
    }

    public static boolean isLand(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    public static int getOrientation(Context context) {
        int i = context.getResources().getConfiguration().orientation;
        Rect a2 = ags.a(context);
        if (i == 0 || ((i == 1 && a2.right > a2.bottom) || (i == 2 && a2.right <= a2.bottom))) {
            i = 0;
        }
        if (i == 0) {
            return a2.right <= a2.bottom ? 1 : 2;
        }
        return i;
    }

    public static String getCarPlateNumber() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(1) : null;
        if (b == null || TextUtils.isEmpty(b.plateNum)) {
            return "";
        }
        return b.plateNum;
    }

    @Nullable
    public static Car getCarInfo() {
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            return ato.a().b(1);
        }
        return null;
    }

    public static String getTruckCarPlateNumber() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        if (b == null || TextUtils.isEmpty(b.plateNum)) {
            return "";
        }
        return b.plateNum;
    }

    @Nullable
    public static Car getCarTruckInfo() {
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            return ato.a().b(2);
        }
        return null;
    }

    public static boolean isEmptyAllCarList() {
        ato ato = (ato) a.a.a(ato.class);
        List<Car> a2 = ato != null ? ato.a().a(1) : null;
        if ((a2 == null || a2.size() == 0) && isSpCarNumberEmpty() && isDBCarNumberEmpty()) {
            return true;
        }
        return false;
    }

    public static float getTruckHeight() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        if (b == null || TextUtils.isEmpty(b.height)) {
            return 0.0f;
        }
        return Float.parseFloat(b.height);
    }

    public static String getTruckWidth() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        return (b == null || TextUtils.isEmpty(b.width)) ? "" : b.width;
    }

    public static String getTruckLength() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        return (b == null || TextUtils.isEmpty(b.height)) ? "" : b.height;
    }

    public static String getTruckAxles() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        return (b == null || TextUtils.isEmpty(b.axleNum)) ? "" : b.axleNum;
    }

    public static int getTruckType() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        if (b != null) {
            return b.truckType;
        }
        return 0;
    }

    public static float getTruckLoad() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        if (b == null || TextUtils.isEmpty(b.capacity)) {
            return 0.0f;
        }
        return Float.parseFloat(b.weight);
    }

    public static String getTruckWeight() {
        ato ato = (ato) a.a.a(ato.class);
        Car b = ato != null ? ato.a().b(2) : null;
        return (b == null || TextUtils.isEmpty(b.weight)) ? "" : b.weight;
    }

    public static POI getMyLocationPoi() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(1);
        if (latestPosition == null) {
            return null;
        }
        String a2 = lh.a(AMapAppGlobal.getApplication(), R.string.LocationMe);
        POI createPOI = POIFactory.createPOI(a2, latestPosition);
        createPOI.setAddr(a2);
        return createPOI;
    }

    @Deprecated
    public static boolean isTruckAvoidLimitedPath() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue("truck_open_car_no", false);
    }

    public static boolean getIsToWork() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        POI pOIHome = getPOIHome();
        POI pOICompany = getPOICompany();
        int i = Calendar.getInstance().get(11);
        if (latestPosition == null || pOIHome == null || pOICompany == null) {
            return i >= 3 && i < 15;
        }
        float a2 = cfe.a(latestPosition, pOIHome.getPoint());
        float a3 = cfe.a(latestPosition, pOICompany.getPoint());
        if (a2 > 500.0f || a3 <= 500.0f) {
            return (a3 > 500.0f || a2 <= 500.0f) && i >= 3 && i < 15;
        }
        return true;
    }

    public static void startRoutePage(PageBundle pageBundle) {
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            bax.b(pageBundle);
        }
    }

    static /* synthetic */ void a(sj sjVar) {
        AMapAppGlobal.getApplication();
        si a2 = si.a();
        List<sj> naviHistoryList = getNaviHistoryList();
        if (naviHistoryList != null) {
            if (naviHistoryList.size() >= 20) {
                for (int i = 20; i < naviHistoryList.size(); i++) {
                    sj sjVar2 = naviHistoryList.get(i);
                    if (sjVar2 != null) {
                        a2.a.delete(sjVar2);
                    }
                }
            }
        }
        sjVar.e = Long.valueOf(System.currentTimeMillis());
        a2.a.delete(sjVar);
        a2.a.insertOrReplace(sjVar);
    }
}
