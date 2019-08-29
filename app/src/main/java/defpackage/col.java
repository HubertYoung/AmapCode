package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.basemap.errorback.model.ReportErrorBean;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.location.LocationInstrument;
import com.uc.webview.export.extension.UCCore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: col reason: default package */
/* compiled from: NewFeedbackStarter */
public final class col {
    private static int a;

    public static String a(int i) {
        switch (i) {
            case 11:
                return "poi_station";
            case 13:
                return "poi_road";
            case 14:
                return "poi_poi";
            case 15:
                return "poi_add";
            default:
                return "";
        }
    }

    public static boolean a() {
        return true;
    }

    public static int a(PageBundle pageBundle, String str) {
        return b(pageBundle, str);
    }

    public static int b(PageBundle pageBundle, String str) {
        if (pageBundle == null || TextUtils.isEmpty(str)) {
            return -1;
        }
        String string = pageBundle.getString(str);
        if (TextUtils.isEmpty(string)) {
            return pageBundle.getInt(str, -1);
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static PageBundle c(PageBundle pageBundle, String str) {
        String str2;
        PageBundle pageBundle2 = new PageBundle();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("pageKey", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("feedbackParam", jSONObject2);
            if (pageBundle != null) {
                int b = b(pageBundle, (String) "sourcepage");
                if (b >= 0) {
                    jSONObject2.put("sourcePage", String.valueOf(b));
                }
                String a2 = a(b(pageBundle, (String) "page_id"));
                if (!TextUtils.isEmpty(a2)) {
                    jSONObject2.put("entryListId", a2);
                }
                String string = pageBundle.getString("error_pic_path");
                if (!TextUtils.isEmpty(string)) {
                    jSONObject2.put("picPath", string);
                }
                int i = pageBundle.getInt("auto_screenshot", -1);
                if (i >= 0) {
                    jSONObject2.put("autoScreenshot", String.valueOf(i));
                }
                int i2 = pageBundle.getInt("delete_screenshot_file", -1);
                if (i2 >= 0) {
                    jSONObject2.put("picDelete", String.valueOf(i2));
                }
                String string2 = pageBundle.getString("location_log", "");
                if (!TextUtils.isEmpty(string2)) {
                    jSONObject2.put("locationLogFile", string2);
                }
                String string3 = pageBundle.getString(AutoJsonUtils.JSON_ADCODE);
                if (!TextUtils.isEmpty(string3)) {
                    jSONObject2.put(AutoJsonUtils.JSON_ADCODE, String.valueOf(string3));
                }
                String string4 = pageBundle.getString("Ad1");
                if (!TextUtils.isEmpty(string4)) {
                    jSONObject2.put("startAdcode", String.valueOf(string4));
                }
                String string5 = pageBundle.getString("Ad2");
                if (!TextUtils.isEmpty(string5)) {
                    jSONObject2.put("endAdcode", String.valueOf(string5));
                }
                POI poi = (POI) pageBundle.getSerializable("points");
                if (poi != null) {
                    JSONObject b2 = bnx.b(poi);
                    if (b2 != null) {
                        jSONObject2.put("poi", b2.toString());
                    }
                }
                POI poi2 = (POI) pageBundle.getObject("startpoint");
                if (poi2 != null) {
                    JSONObject b3 = bnx.b(poi2);
                    if (b3 != null) {
                        jSONObject2.put("startPoi", b3.toString());
                    }
                }
                POI poi3 = (POI) pageBundle.getObject("endpoint");
                if (poi3 != null) {
                    JSONObject b4 = bnx.b(poi3);
                    if (b4 != null) {
                        jSONObject2.put("endPoi", b4.toString());
                    }
                }
                String string6 = pageBundle.getString("car_used", "");
                if (!TextUtils.isEmpty(string6)) {
                    jSONObject2.put("carUsed", string6);
                }
                String string7 = pageBundle.getString("naviid", "");
                if (!TextUtils.isEmpty(string7)) {
                    jSONObject2.put("naviId", string7);
                }
                cgk cgk = (cgk) pageBundle.getObject("drive_issue_content_key");
                if (cgk != null) {
                    JSONObject a3 = a(cgk);
                    if (a3 != null) {
                        jSONObject2.put("naviHistory", a3.toString());
                    }
                }
                String string8 = pageBundle.getString("bsid", "");
                if (!TextUtils.isEmpty(string8)) {
                    jSONObject2.put("bsid", string8);
                }
                String string9 = pageBundle.getString("name", "");
                if (!TextUtils.isEmpty(string9)) {
                    jSONObject2.put("name", string9);
                }
                String string10 = pageBundle.getString("category", "");
                if (!TextUtils.isEmpty(string10)) {
                    jSONObject2.put("category", string10);
                }
                String string11 = pageBundle.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, "");
                if (!TextUtils.isEmpty(string11)) {
                    jSONObject2.put(TrafficUtil.POIID, string11);
                }
                String string12 = pageBundle.getString("sonPOIID", "");
                if (!TextUtils.isEmpty(string12)) {
                    jSONObject2.put("sonPoiId", string12);
                }
                int b5 = b(pageBundle, (String) "route_line_type");
                if (b5 >= 0) {
                    jSONObject2.put("routeLineType", String.valueOf(b5));
                }
                int b6 = b(pageBundle, (String) "from_route_type");
                if (b6 >= 0) {
                    jSONObject2.put("fromRouteType", String.valueOf(b6));
                }
                Object object = pageBundle.getObject("realtime_bus_param");
                if (object != null) {
                    jSONObject2.put("busPlanData", object);
                }
                Object object2 = pageBundle.getObject("bus_line_param");
                if (object2 != null) {
                    jSONObject2.put("busLineData", object2);
                }
                String string13 = pageBundle.getString("navi_type");
                if (!TextUtils.isEmpty(string13)) {
                    jSONObject2.put("naviType", string13);
                    if (DriveUtil.NAVI_TYPE_TRUCK.equals(string13)) {
                        JSONObject b7 = b();
                        if (b7 != null) {
                            jSONObject2.put("truckInfo", b7.toString());
                        }
                    }
                }
                JSONObject c = c();
                if (c != null) {
                    jSONObject2.put("carInfo", c.toString());
                }
                String string14 = pageBundle.getString("lines", "");
                if (!TextUtils.isEmpty(string14)) {
                    jSONObject2.put("stationLines", string14);
                }
                List list = (List) pageBundle.getObject("foot_path");
                if (list != null) {
                    JSONArray a4 = a(list);
                    if (a4 != null) {
                        jSONObject2.put("footPath", a4.toString());
                    }
                }
                ReportErrorBean reportErrorBean = (ReportErrorBean) pageBundle.getObject("ReportErrorDescFragment.ReportErrorBean");
                if (reportErrorBean != null) {
                    a(jSONObject2, reportErrorBean);
                }
                if (b == 22) {
                    IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                    if (iOfflineManager != null) {
                        String offlineDBFilePath = iOfflineManager.getOfflineDBFilePath();
                        if (!TextUtils.isEmpty(offlineDBFilePath) && new File(offlineDBFilePath).exists()) {
                            jSONObject2.put("offlineDBFile", offlineDBFilePath);
                        }
                        String offlineLogFilePath = iOfflineManager.getOfflineLogFilePath();
                        if (!TextUtils.isEmpty(offlineLogFilePath) && new File(offlineLogFilePath).exists()) {
                            jSONObject2.put("offlineLogFile", offlineLogFilePath);
                        }
                    }
                }
                jSONObject2.put("locationType", ave.a());
                String a5 = kn.a(AMapPageUtil.getAppContext());
                if (TextUtils.isEmpty(a5)) {
                    a5 = "";
                }
                jSONObject2.put("deviceId", a5);
                Object object3 = pageBundle.getObject("old_poi_param");
                if (object3 != null) {
                    jSONObject2.put("oldPoiParam", object3);
                }
                Object object4 = pageBundle.getObject("old_bus_plan_param");
                if (object4 != null) {
                    jSONObject2.put("oldBusPlanParam", object4);
                }
                Object object5 = pageBundle.getObject("old_bus_line_param");
                if (object5 != null) {
                    jSONObject2.put("oldBusLineParam", object5);
                }
                ArrayList arrayList = new ArrayList();
                if (pageBundle.getBoolean("has_ride_for_bus_route", false)) {
                    arrayList.add("hasRide");
                }
                if (arrayList.size() > 0) {
                    jSONObject2.put("tags", b(arrayList));
                }
                Object object6 = pageBundle.getObject("twice_report_param");
                if (object6 != null) {
                    jSONObject2.put("twiceReportInfo", object6);
                }
            }
            str2 = jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            str2 = null;
        }
        if (TextUtils.isEmpty(str) || !str.equals("entrylist")) {
            pageBundle2.putString("url", "path://amap_bundle_common_feedback/src/pages/FeedbackRouter.page.js");
        } else {
            pageBundle2.putString("url", "path://amap_bundle_common_feedback/src/pages/EntryList.page.js");
        }
        pageBundle2.putObject("jsData", str2);
        return pageBundle2;
    }

    public static PageBundle a(String str, String str2) {
        PageBundle pageBundle = new PageBundle();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("pageKey", str);
            jSONObject.put("feedbackParam", str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(str) || !str.equals("entrylist")) {
            pageBundle.putString("url", "path://amap_bundle_common_feedback/src/pages/FeedbackRouter.page.js");
        } else {
            pageBundle.putString("url", "path://amap_bundle_common_feedback/src/pages/EntryList.page.js");
        }
        pageBundle.putObject("jsData", jSONObject.toString());
        return pageBundle;
    }

    private static JSONArray a(List<axp> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (axp next : list) {
            if (next != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("startName", next.c);
                    jSONObject.put("endName", next.d);
                } catch (JSONException e) {
                    e.printStackTrace();
                    jSONObject = null;
                }
                if (jSONObject != null) {
                    jSONArray.put(jSONObject);
                }
            }
        }
        return jSONArray;
    }

    private static JSONObject a(cgk cgk) {
        if (cgk == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("startName", cgk.c);
            jSONObject.put("endName", cgk.d);
            jSONObject.put("startTime", cgk.g);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    private static void a(JSONObject jSONObject, ReportErrorBean reportErrorBean) {
        if (reportErrorBean != null) {
            try {
                if (!TextUtils.isEmpty(reportErrorBean.errorImgUrl)) {
                    jSONObject.put("picPath", reportErrorBean.errorImgUrl);
                }
                POI poi = reportErrorBean.fromPoi;
                if (poi != null) {
                    JSONObject b = bnx.b(poi);
                    if (b != null) {
                        jSONObject.put("startPoi", b.toString());
                    }
                }
                POI poi2 = reportErrorBean.endPoi;
                if (poi2 != null) {
                    JSONObject b2 = bnx.b(poi2);
                    if (b2 != null) {
                        jSONObject.put("endPoi", b2.toString());
                    }
                }
                List<POI> list = reportErrorBean.throughPois;
                if (list != null && list.size() > 0) {
                    JSONArray jSONArray = new JSONArray();
                    for (POI next : list) {
                        if (next != null) {
                            jSONArray.put(bnx.b(next));
                        }
                    }
                    jSONObject.put("passingPoi", jSONArray.toString());
                }
                String str = reportErrorBean.expand;
                String str2 = "";
                if (!TextUtils.isEmpty(str)) {
                    Context appContext = AMapPageUtil.getAppContext();
                    if (appContext != null) {
                        String mapBaseStorage = FileUtil.getMapBaseStorage(appContext);
                        if (!TextUtils.isEmpty(mapBaseStorage)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(mapBaseStorage);
                            sb.append("/autonavi/navi_report.zcfk");
                            str2 = sb.toString();
                            FileUtil.writeTextFile(new File(str2), str);
                        }
                    }
                }
                if (!TextUtils.isEmpty(str2)) {
                    jSONObject.put("driveReportFile", str2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static JSONObject b() {
        djk djk = (djk) ank.a(djk.class);
        ato ato = (ato) a.a.a(ato.class);
        JSONObject jSONObject = null;
        Car b = ato != null ? ato.a().b(2) : null;
        if (djk != null) {
            jSONObject = new JSONObject();
            if (b != null) {
                try {
                    jSONObject.put("plate", djk.h());
                    if (djk.c()) {
                        jSONObject.put("truck_navi_option", "1");
                    } else {
                        jSONObject.put("truck_navi_option", "0");
                    }
                    if (djk.d()) {
                        jSONObject.put("truck_load_option", "1");
                    } else {
                        jSONObject.put("truck_load_option", "0");
                    }
                    jSONObject.put("max_height", djk.e() == 0.0f ? "" : String.valueOf(djk.e()));
                    jSONObject.put(UCCore.OPTION_LOAD_KERNEL_TYPE, djk.f() == 0.0f ? "" : String.valueOf(djk.f()));
                    jSONObject.put("truck_type", b.truckType);
                    jSONObject.put("truck_length", b.length);
                    jSONObject.put("truck_width", b.width);
                    jSONObject.put("truck_weight", b.weight);
                    jSONObject.put("truck_axles", b.axleNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONObject;
    }

    private static JSONObject c() {
        djk djk = (djk) ank.a(djk.class);
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            ato.a().b(1);
        }
        if (djk == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("plate", djk.g());
            jSONObject.put("contentOptions", djk.b() ? "1" : "0");
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public static JSONObject b(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("record_id", str);
            jSONObject.put("type", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static String b(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append(list.get(i));
        }
        return stringBuffer.toString();
    }
}
