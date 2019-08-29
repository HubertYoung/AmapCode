package defpackage;

import android.media.AudioManager;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dys reason: default package */
/* compiled from: BigTripLogUtil */
public final class dys {
    public static void a(String str, String str2, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        a(str, str2, jSONObject);
    }

    public static void a(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        a(str, str2, jSONObject);
    }

    public static void a(String str, String str2, JSONObject jSONObject) {
        if (jSONObject == null) {
            LogManager.actionLogV2(str, str2);
        } else {
            LogManager.actionLogV2(str, str2, jSONObject);
        }
    }

    public static void a(String str, String str2) {
        String str3 = "";
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            str3 = iVoicePackageManager.getCurrentTtsName2();
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(TrafficUtil.KEYWORD, str3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        a(str, str2, jSONObject);
    }

    public static void b(String str, String str2) {
        AudioManager audioManager = (AudioManager) AMapAppGlobal.getApplication().getSystemService("audio");
        if (audioManager != null) {
            String str3 = "";
            float streamVolume = ((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3));
            if (streamVolume == 0.0f) {
                str3 = "0";
            } else if (0.0f < streamVolume && ((double) streamVolume) <= 0.5d) {
                str3 = "(0,50%]";
            } else if (0.5d < ((double) streamVolume) && streamVolume < 1.0f) {
                str3 = "(50%,100%)";
            } else if (streamVolume >= 1.0f) {
                str3 = "[100%,∞)";
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("text", str3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            a(str, str2, jSONObject);
        }
    }
}
