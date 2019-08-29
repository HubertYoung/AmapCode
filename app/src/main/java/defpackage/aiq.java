package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aiq reason: default package */
/* compiled from: ServiceLogUtil */
public final class aiq {
    public static void a(int i, String str) {
        StringBuilder sb = new StringBuilder("token=");
        sb.append(i);
        sb.append(" json=");
        sb.append(str);
        String sb2 = sb.toString();
        AMapLog.d("AMapService", sb2);
        a((String) "logResult", sb2);
    }

    public static void b(int i, String str) {
        StringBuilder sb = new StringBuilder("token=");
        sb.append(i);
        sb.append(" desc=");
        sb.append(str);
        String sb2 = sb.toString();
        AMapLog.d("AMapService", sb2);
        a((String) "logProtocolProcess", sb2);
    }

    public static void c(int i, String str) {
        StringBuilder sb = new StringBuilder("token=");
        sb.append(i);
        sb.append(" desc=");
        sb.append(str);
        String sb2 = sb.toString();
        AMapLog.d("AMapService", sb2);
        a((String) "logScene", sb2);
    }

    public static void a(String str) {
        AMapLog.d("AMapService", str);
        a((String) "logSendMessage", str);
    }

    public static void b(String str) {
        AMapLog.d("AMapService", "logCloudMessage:".concat(String.valueOf(str)));
        a((String) "logCloudMessage", str);
    }

    public static void a(String str, String str2) {
        ku a = ku.a();
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("]");
        sb.append(str2);
        a.c("amap_voice", sb.toString());
    }

    public static void a(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", str);
            jSONObject.put(TrafficUtil.KEYWORD, str2);
            a(jSONObject, "type", str3);
            a(jSONObject, "status", str4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00085", "B006", jSONObject);
    }

    private static void a(JSONObject jSONObject, String str, String str2) throws JSONException {
        JSONObject jSONObject2 = new JSONObject(str2);
        Iterator<String> keys = jSONObject2.keys();
        JSONArray jSONArray = new JSONArray();
        while (keys.hasNext()) {
            String valueOf = String.valueOf(keys.next());
            String valueOf2 = String.valueOf(jSONObject2.get(valueOf));
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(valueOf, valueOf2);
            jSONArray.put(jSONObject3);
        }
        jSONObject.put(str, jSONArray);
    }
}
