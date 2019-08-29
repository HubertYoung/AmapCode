package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.amap.app.AMapAppGlobal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blh reason: default package */
/* compiled from: NativeStorageAction */
public class blh extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a = a();
        if (a != null && a.mPageContext != null) {
            boolean z = false;
            SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("NativeStorageAction", 0);
            String optString = jSONObject.optString("key");
            String str = "";
            if (jSONObject.has("value")) {
                String optString2 = jSONObject.optString("value");
                Editor edit = sharedPreferences.edit();
                edit.putString(optString, optString2);
                edit.apply();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                a.callJs(waVar.a, jSONObject2.toString());
            } else if ("hotelDate".equals(optString)) {
                String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String string = AMapAppGlobal.getApplication().getSharedPreferences("NativeStorageAction", 0).getString("hotelDate", "");
                String str2 = "";
                if (!TextUtils.isEmpty(string)) {
                    int indexOf = string.indexOf(MergeUtil.SEPARATOR_KV);
                    int indexOf2 = indexOf > 0 ? string.indexOf(MergeUtil.SEPARATOR_KV, indexOf + 1) : -1;
                    int length = string.length();
                    if (indexOf > 0 && indexOf2 > 0 && length > indexOf && length > indexOf2) {
                        str2 = string.substring(indexOf2 + 1);
                    }
                    if (str2.equals(format)) {
                        z = true;
                    }
                }
                if (z) {
                    str = sharedPreferences.getString("hotelDate", "");
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("_action", waVar.b);
                jSONObject3.put("value", str);
                a.callJs(waVar.a, jSONObject3.toString());
            } else if ("TRCCompensateIsOnline".equals(optString)) {
                String string2 = sharedPreferences.getString("TRCCompensateIsOnline", "0");
                if (TextUtils.isEmpty(string2) || "null".equals(string2)) {
                    string2 = "0";
                }
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("_action", waVar.b);
                jSONObject4.put("value", string2);
                a.callJs(waVar.a, jSONObject4.toString());
            } else if ("TRCCompensateNavTimes".equals(optString)) {
                JSONArray jSONArray = new JSONArray();
                djk djk = (djk) ank.a(djk.class);
                if (djk != null) {
                    jSONArray = djk.j();
                }
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("_action", waVar.b);
                jSONObject5.put("value", jSONArray);
                a.callJs(waVar.a, jSONObject5.toString());
            } else if ("indoorGuide".equals(optString)) {
                String string3 = sharedPreferences.getString("indoorGuide", "");
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("_action", waVar.b);
                jSONObject6.put("value", string3);
                a.callJs(waVar.a, jSONObject6.toString());
            } else {
                JSONObject jSONObject7 = new JSONObject();
                jSONObject7.put("_action", waVar.b);
                jSONObject7.put("value", sharedPreferences.getString(optString, ""));
                a.callJs(waVar.a, jSONObject7.toString());
            }
        }
    }

    public static void a(String str, String str2) {
        Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("NativeStorageAction", 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }
}
