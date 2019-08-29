package com.autonavi.minimap.route.bundle;

import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.jni.eyrie.amap.tracker.TrackPoster;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

@VirtualApp(priority = 100)
public class RouteVApp extends esh {
    private boolean a = false;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        if (hasPermission()) {
            this.a = true;
        }
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        ahm.a(new Runnable() {
            public final void run() {
                RouteVApp.a(RouteVApp.this);
            }
        });
    }

    public void vAppDestroy() {
        super.vAppDestroy();
    }

    static /* synthetic */ void a(RouteVApp routeVApp) {
        eao.d("wbsww", "onapp start");
        StringBuilder sb = new StringBuilder();
        File file = new File(FileUtil.getFilesDir().getAbsolutePath(), "trackPost");
        if (!file.exists()) {
            file.mkdirs();
        }
        String absolutePath = file.getAbsolutePath();
        String keyValue = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_SNS_URL_KEY);
        LinkedHashMap<String, String> networkParamMap = NetworkParam.getNetworkParamMap(null);
        if (networkParamMap != null && !networkParamMap.isEmpty()) {
            for (Entry next : networkParamMap.entrySet()) {
                if (sb.length() > 0) {
                    sb.append('&');
                }
                sb.append((String) next.getKey());
                sb.append('=');
                sb.append((String) next.getValue());
            }
        }
        StringBuilder sb2 = new StringBuilder("trackPostAppInit() init, trackDir:");
        sb2.append(absolutePath);
        sb2.append(", aosUrl:");
        sb2.append(keyValue);
        sb2.append(", params:");
        sb2.append(sb.toString());
        TrackPostUtils.a(sb2.toString());
        TrackPoster.init(absolutePath, "https://page.amap.com/ws/page/upload/", keyValue, sb.toString());
        boolean parseBoolean = Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"));
        boolean parseBoolean2 = Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id"));
        if (parseBoolean || parseBoolean2) {
            String b = ehs.b("share_bike_order_id");
            TrackPostUtils.a("trackPostAppInit() sharebike orderid:".concat(String.valueOf(b)));
            if (!TextUtils.isEmpty(b)) {
                TrackPoster.set(TrackType.SHAREBIKE, b);
            }
        }
        ebr.a(false).postDelayed(new Runnable() {
            public final void run() {
                TrackPoster.uploadAll();
            }
        }, 10000);
        ebt.a();
    }
}
