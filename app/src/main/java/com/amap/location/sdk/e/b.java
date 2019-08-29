package com.amap.location.sdk.e;

import com.amap.location.sdk.fusion.LocationParams;
import org.json.JSONObject;

/* compiled from: AmapExtraUtil */
public class b {
    public static void a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIP)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIP, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIV)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIV, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIBV)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIBV, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIE)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIE, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DID)) {
                jSONObject.put(LocationParams.PARA_COMMON_DID, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIC)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIC, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIU)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIU, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIU2)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIU2, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_DIU3)) {
                jSONObject.put(LocationParams.PARA_COMMON_DIU3, "Unknown");
            }
            if (!jSONObject.has(LocationParams.PARA_COMMON_CIFA)) {
                jSONObject.put(LocationParams.PARA_COMMON_CIFA, "Unknown");
            }
            if (!jSONObject.has("channel")) {
                jSONObject.put("channel", "Unknown");
            }
            if (!jSONObject.has("from")) {
                jSONObject.put("from", "Unknown");
            }
            jSONObject.put("netloc", "0");
            jSONObject.put("gpsstatus", "0");
            jSONObject.put("nbssid", "0");
            if (!jSONObject.has("reversegeo")) {
                jSONObject.put("reversegeo", "0");
            }
            if (!jSONObject.has("wait1stwifi")) {
                jSONObject.put("wait1stwifi", "0");
            }
            if (!jSONObject.has("autoup")) {
                jSONObject.put("autoup", "1");
            }
            if (!jSONObject.has("fetchoffdatamobile")) {
                jSONObject.put("fetchoffdatamobile", "1");
            }
            if (!jSONObject.has("enableofflineloc")) {
                jSONObject.put("enableofflineloc", "1");
            }
            jSONObject.put("upcolmobile", 1);
            jSONObject.put("enablegetreq", 1);
            if (!jSONObject.has(LocationParams.PARA_COMMON_LOC_SCENE)) {
                jSONObject.put(LocationParams.PARA_COMMON_LOC_SCENE, "0");
            }
        } catch (Exception unused) {
        }
    }

    public static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        a(jSONObject);
        return jSONObject;
    }
}
