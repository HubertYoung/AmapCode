package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteTipBean;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ayp reason: default package */
/* compiled from: BusCommuteActionLog */
public final class ayp {
    public static String a = "P00448";
    public static String b = "B004";
    public static String c = "B005";
    public static String d = "B001";
    public static String e = "B002";
    public static String f = "B003";
    public static String g = "B010";
    public static String h = "B011";
    public static String i = "B013";

    public static void a(String str, String str2, int i2) {
        int i3 = i2 + 1;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("index", i3);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    public static void a(String str, String str2, boolean z, boolean z2) {
        JSONObject jSONObject = new JSONObject();
        String str3 = z ? "setting_user" : "data_mining_user";
        String str4 = z2 ? "go_home" : "to_company";
        try {
            jSONObject.put("type", str3);
            jSONObject.put("to", str4);
            LogManager.actionLogV2(str, str2, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public static void a(String str, String str2, BusCommuteTipBean busCommuteTipBean, boolean z) {
        String str3;
        if (busCommuteTipBean != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                int i2 = busCommuteTipBean.firstSegmentBusType;
                if (i2 == 12) {
                    str3 = "first_segment_ferry";
                } else {
                    if (!(i2 == 2 || i2 == 3)) {
                        if (i2 != 10) {
                            str3 = "first_segment_bus";
                        }
                    }
                    str3 = "first_segment_subway";
                }
                String str4 = "";
                aym aym = b.a;
                if (aym.a(aym.a)) {
                    str4 = "sbtq";
                } else if (aym.a(aym.c)) {
                    str4 = "sbftq";
                } else if (aym.a(aym.b)) {
                    str4 = "xbtq";
                } else if (aym.a(aym.d)) {
                    str4 = "xbftq";
                }
                String str5 = busCommuteTipBean.isRealtime ? "realtime_true" : "realtime_false";
                String str6 = busCommuteTipBean.isSettingUser ? "setting_user" : "data_mining_user";
                String str7 = busCommuteTipBean.isGoHome ? "go_home" : "to_company";
                String str8 = busCommuteTipBean.time_tag == 0 ? "risk_false" : "risk_true";
                String str9 = "stop_event_false";
                if (busCommuteTipBean.stopEventList != null && busCommuteTipBean.stopEventList.size() > 0) {
                    str9 = "stop_event_true";
                }
                jSONObject.put("status", str5);
                jSONObject.put("result", str3);
                jSONObject.put("type", str6);
                jSONObject.put("to", str7);
                jSONObject.put("time", str4);
                jSONObject.put("content", str8);
                jSONObject.put("text", str9);
                if (z) {
                    jSONObject.put("index", busCommuteTipBean.foucsIndex + 1);
                }
                LogManager.actionLogV2(str, str2, jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
