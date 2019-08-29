package com.ali.auth.third.ut;

import android.app.Application;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.service.UserTrackerService;
import com.taobao.accs.common.Constants;
import com.ut.mini.UTAnalytics;
import com.ut.mini.UTHitBuilders.UTCustomHitBuilder;
import com.ut.mini.core.sign.UTSecuritySDKRequestAuthentication;
import java.util.HashMap;
import java.util.Map;

public class UserTrackerImpl implements UserTrackerService {
    public UserTrackerImpl() {
        try {
            UTAnalytics.getInstance().turnOnDebug();
            UTAnalytics.getInstance().setContext(KernelContext.context);
            UTAnalytics.getInstance().setAppApplicationInstance((Application) KernelContext.context.getApplicationContext());
            UTAnalytics.getInstance().setRequestAuthentication(new UTSecuritySDKRequestAuthentication(KernelContext.getAppKey(), ""));
            a();
        } catch (Throwable unused) {
        }
    }

    private void a() {
        UTCustomHitBuilder uTCustomHitBuilder = new UTCustomHitBuilder("80001");
        HashMap hashMap = new HashMap();
        hashMap.put(Constants.KEY_MODEL, "auth");
        hashMap.put("version", ConfigManager.SDK_VERSION.toString());
        uTCustomHitBuilder.setProperties(hashMap);
        UTAnalytics.getInstance().getTracker("25").send(uTCustomHitBuilder.build());
    }

    private static Map<String, String> b() {
        return new HashMap();
    }

    public void send(String str, Map<String, String> map) {
        UTCustomHitBuilder uTCustomHitBuilder = new UTCustomHitBuilder(str);
        Map<String, String> b = b();
        if (map != null) {
            b.putAll(map);
        }
        uTCustomHitBuilder.setProperties(b);
        UTAnalytics.getInstance().getTracker("onesdk").send(uTCustomHitBuilder.build());
    }
}
