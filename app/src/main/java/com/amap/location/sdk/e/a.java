package com.amap.location.sdk.e;

import android.content.Context;
import android.text.TextUtils;
import com.amap.location.icecream.b;
import com.amap.location.sdk.fusion.LocationParams;
import org.json.JSONObject;

/* compiled from: AmapCloudParser */
public class a {
    public static void a(Context context, JSONObject jSONObject) {
        if (!f.b(context)) {
            try {
                if (jSONObject.has(LocationParams.PARA_AMAP_CLOUD_LOCSDK_PLUGIN)) {
                    String optString = jSONObject.optString(LocationParams.PARA_AMAP_CLOUD_LOCSDK_PLUGIN);
                    if (!TextUtils.isEmpty(optString) && optString.contains("list_")) {
                        b.a().a(context, new JSONObject(optString));
                    }
                }
                if (jSONObject.has(LocationParams.PARA_AMAP_CLOUD_ALC)) {
                    String optString2 = jSONObject.optString(LocationParams.PARA_AMAP_CLOUD_ALC, "");
                    if (!TextUtils.isEmpty(optString2)) {
                        d.a(optString2);
                    }
                }
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }
    }
}
