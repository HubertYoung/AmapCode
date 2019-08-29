package com.alipay.deviceid.module.x;

import android.content.Context;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

public final class au {
    public static av a(Context context) {
        if (context == null) {
            return null;
        }
        String a = az.a(context, "device_feature_prefs_name", "device_feature_prefs_key");
        if (e.a(a)) {
            a = az.a("device_feature_file_name", "device_feature_file_key");
        }
        if (e.a(a)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(a);
            av avVar = new av();
            avVar.a(jSONObject.getString(Constants.KEY_IMEI));
            avVar.b(jSONObject.getString(Constants.KEY_IMSI));
            avVar.c(jSONObject.getString("mac"));
            avVar.d(jSONObject.getString("bluetoothmac"));
            avVar.e(jSONObject.getString("gsi"));
            return avVar;
        } catch (Exception e) {
            aa.a((Throwable) e);
            return null;
        }
    }

    public static void a(Context context, av avVar) {
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Constants.KEY_IMEI, avVar.a());
                jSONObject.put(Constants.KEY_IMSI, avVar.b());
                jSONObject.put("mac", avVar.c());
                jSONObject.put("bluetoothmac", avVar.d());
                jSONObject.put("gsi", avVar.e());
                String jSONObject2 = jSONObject.toString();
                if (!e.a((String) "device_feature_file_name") && !e.a((String) "device_feature_file_key")) {
                    try {
                        String a = i.a(i.a(), jSONObject2);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("device_feature_file_key", a);
                        t.a("device_feature_file_name", jSONObject3.toString());
                    } catch (Exception unused) {
                    }
                }
                az.a(context, "device_feature_prefs_name", "device_feature_prefs_key", jSONObject2);
            } catch (Exception e) {
                aa.a((Throwable) e);
            }
        }
    }
}
