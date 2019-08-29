package defpackage;

import android.os.Build;
import com.alipay.sdk.packet.d;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.sdk.location.LocationInstrument;
import com.taobao.accs.common.Constants;
import com.ut.mini.UTAnalytics;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: kc reason: default package */
/* compiled from: GDBehaviorTrackerSingleton */
public class kc {
    private static volatile kc c;
    final Pattern a = Pattern.compile("([0-9a-zA-Z_]+)\\.([0-9a-zA-Z_]+)\\.([0-9a-zA-Z_]+)\\.([0-9a-zA-Z_]+)");
    /* access modifiers changed from: 0000 */
    public volatile int b = 1;
    private final AtomicLong d = new AtomicLong(0);
    private final String e = "ut_config";

    public final int a(String str, Object obj, Map<String, String> map) {
        if (this.b == 0) {
            return -4;
        }
        if (str == null || obj == null) {
            return -3;
        }
        if (bno.a && !this.a.matcher(str).find()) {
            return -1;
        }
        String[] split = str.split("\\.");
        if (split.length != 4) {
            return -1;
        }
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("spm-cnt", str);
        map.putAll(b());
        UTAnalytics.getInstance().getDefaultTracker().updatePageName(obj, split[1]);
        UTAnalytics.getInstance().getDefaultTracker().updatePageProperties(obj, map);
        UTAnalytics.getInstance().getDefaultTracker().pageAppearDonotSkip(obj);
        return 0;
    }

    public final int a(Object obj) {
        if (this.b == 0) {
            return -4;
        }
        if (obj == null) {
            return -3;
        }
        UTAnalytics.getInstance().getDefaultTracker().pageDisAppear(obj);
        return 0;
    }

    public static kc a() {
        if (c == null) {
            synchronized (kc.class) {
                try {
                    if (c == null) {
                        c = new kc();
                    }
                }
            }
        }
        return c;
    }

    private kc() {
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(LocationParams.PARA_COMMON_DIU, NetworkParam.getDiu());
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(LocationParams.PARA_COMMON_DIU2, NetworkParam.getMac());
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(LocationParams.PARA_COMMON_DIU3, NetworkParam.getIsn());
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(LocationParams.PARA_COMMON_DIV, NetworkParam.getDiv());
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(LocationParams.PARA_COMMON_DIBV, NetworkParam.getDibv());
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(LocationParams.PARA_COMMON_DIC, NetworkParam.getDic());
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(Constants.KEY_MODEL, Build.MODEL);
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(d.n, Build.DEVICE);
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty("manufacture", Build.MANUFACTURER);
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(Constants.KEY_IMSI, agm.b(AMapAppGlobal.getApplication()));
        afk.b();
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty(ConfigerHelper.AETRAFFIC_KEY, afk.b().a());
        UTAnalytics.getInstance().getDefaultTracker().setGlobalProperty("sessionid", String.valueOf(NetworkParam.getSession()));
        String a2 = lo.a().a((String) "ut_config");
        if (a2 != null) {
            try {
                JSONObject jSONObject = new JSONObject(a2);
                if (jSONObject.has("ut_config")) {
                    this.b = jSONObject.getInt("ut_config");
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        lo.a().a((String) "ut_config", (lp) new lp() {
            public final void onConfigCallBack(int i) {
            }

            public final void onConfigResultCallBack(int i, String str) {
                if (str != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has("ut_config")) {
                            kc.this.b = jSONObject.getInt("ut_config");
                        }
                    } catch (JSONException e) {
                        if (bno.a) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public final Map<String, String> b() {
        HashMap hashMap = new HashMap();
        try {
            hashMap.put(LocationParams.PARA_COMMON_ADIU, NetworkParam.getAdiu());
            hashMap.put("stepid", String.valueOf(this.d.getAndIncrement()));
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                hashMap.put(CameraControllerManager.MY_POILOCATION_LNG, String.valueOf(latestPosition.getLongitude()));
                hashMap.put("lat", String.valueOf(latestPosition.getLatitude()));
            }
        } catch (Exception e2) {
            if (bno.a) {
                e2.printStackTrace();
            }
        }
        return hashMap;
    }
}
