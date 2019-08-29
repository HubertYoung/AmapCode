package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bfh reason: default package */
/* compiled from: VUILog */
public final class bfh {
    public static void a(String str, String str2) {
        if (bno.a && (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2))) {
            AMapLog.debug("route.vui", str, str2);
        }
    }

    public static void b(String str, String str2) {
        if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
            cjy.a(ALCLogLevel.P1, (String) AMapLog.GROUP_COMMON, (String) "D1", (String) "P0050", str, str2);
            a(str, str2);
        }
    }

    public static void a(String str, String str2, String str3) {
        try {
            StringBuilder sb = new StringBuilder("type:");
            sb.append(str);
            sb.append(",subType:");
            sb.append(str2);
            sb.append(",status:");
            sb.append(str3);
            a("vuiUnavailableLog", sb.toString());
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("type", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                jSONObject.put(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                jSONObject.put("status", str3);
            }
            LogUtil.actionLogV2("P00462", "B012", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", str);
            LogUtil.actionLogV2("P00462", "B013", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void a(boolean z, int i, int i2, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("status", z ? "1" : "0");
            if (z) {
                jSONObject.put("text", String.format("localDNSTimeout:%d,httpDNSTimeout:%d,IP:%s", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), str}));
            }
            LogUtil.actionLogV2("P00462", "B017", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
