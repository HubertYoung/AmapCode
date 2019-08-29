package defpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cve reason: default package */
/* compiled from: PluginDataManager */
public final class cve {
    public static String a = null;
    private static String b = null;
    private static ArrayList<String> c = new ArrayList<>();
    private static Map<String, cvi> d = new HashMap();
    private static String e = "";

    public static void a(String str, JSONObject jSONObject) {
        b = str;
        HashMap hashMap = new HashMap();
        ArrayList<String> arrayList = new ArrayList<>();
        boolean z = false;
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("plugins");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                String a2 = cvg.a(jSONObject2.getInt("type"));
                boolean z2 = jSONObject2.getBoolean("enable");
                JSONObject jSONObject3 = null;
                if (jSONObject2.has("params")) {
                    jSONObject3 = jSONObject2.getJSONObject("params");
                }
                if (z2) {
                    hashMap.put(a2, new cvi(a2, z2, jSONObject3));
                }
            }
            z = true;
        } catch (JSONException e2) {
            cwi.a("PluginDataManager", "localConfig file json error", e2);
        } catch (Exception e3) {
            cwi.a("PluginDataManager", "localConfig file error", e3);
        }
        if (z) {
            d.clear();
            c.clear();
            d = hashMap;
            c = arrayList;
            a = "0";
            e = jSONObject.toString();
        }
        d.put("AppEventDetectPlugin", new cvi("AppEventDetectPlugin"));
        d.put("UploadPlugin", new cvi("UploadPlugin"));
    }

    public static Map<String, cvi> a() {
        return d;
    }
}
