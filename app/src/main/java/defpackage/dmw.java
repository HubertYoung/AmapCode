package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dmw reason: default package */
/* compiled from: OpenBusLineAction */
public class dmw extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String optString = jSONObject2.optString("busLineid");
                String optString2 = jSONObject2.optString("stationId");
                if (!TextUtils.isEmpty(optString)) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    StringBuilder sb = new StringBuilder("amapuri://realtimeBus/detail?param={");
                    if (!TextUtils.isEmpty(optString)) {
                        sb.append("lineId:");
                        sb.append(optString);
                    }
                    if (!TextUtils.isEmpty(optString2)) {
                        sb.append(",stationId:");
                        sb.append(optString2);
                    }
                    sb.append(",from:station_detail}");
                    intent.setData(Uri.parse(sb.toString()));
                    esf.a().a(new ese(intent));
                }
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
