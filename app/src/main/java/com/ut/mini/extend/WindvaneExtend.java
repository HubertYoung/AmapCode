package com.ut.mini.extend;

import android.taobao.windvane.jsbridge.WVPluginManager;
import com.alibaba.analytics.utils.Logger;
import com.ut.mini.core.WVUserTrack;

public class WindvaneExtend {
    public static void registerWindvane(boolean z) {
        if (!UTExtendSwitch.bWindvaneExtend) {
            Logger.w((String) "UTAnalytics", "user disable WVTBUserTrack ");
        } else if (z) {
            Logger.w((String) "UTAnalytics", "Has registered WVTBUserTrack plugin,not need to register again! ");
        } else {
            try {
                WVPluginManager.registerPlugin("WVTBUserTrack", WVUserTrack.class, true);
                Logger.d((String) "UTAnalytics", "register WVTBUserTrack Success");
            } catch (Throwable th) {
                Logger.e((String) "UTAnalytics", "Exception", th.toString());
            }
        }
    }
}
