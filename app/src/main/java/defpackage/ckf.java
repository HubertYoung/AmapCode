package defpackage;

import android.text.TextUtils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: ckf reason: default package */
/* compiled from: InstallErrorHandler */
public final class ckf implements un {
    public final b a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(NetworkParam.getAosSnsUrl());
        sb.append("ws/feedback/report/");
        String sb2 = sb.toString();
        HashMap hashMap = new HashMap();
        hashMap.put("type", "1004");
        hashMap.putAll(map);
        if (!TextUtils.isEmpty(a())) {
            hashMap.put("userid", a());
        }
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        int i = latestPosition.x;
        int i2 = latestPosition.y;
        if (!(i == 0 || i2 == 0)) {
            DPoint a = cfg.a((long) i, (long) i2);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a.x);
            hashMap.put("longitude", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a.y);
            hashMap.put("latitude", sb4.toString());
            try {
                lj b = li.a().b(i, i2);
                if (b != null) {
                    hashMap.put(AutoJsonUtils.JSON_ADCODE, String.valueOf(b.j));
                }
            } catch (Throwable unused) {
            }
        }
        return new b(sb2, hashMap);
    }

    public final void a(String str, String str2, JSONObject jSONObject) {
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    private static String a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }
}
