package com.ali.user.mobile.biz;

import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;

public class ReportLocationServiceWrapper {
    public static void a(String str) {
        try {
            AliUserLog.c("ReportLocationServiceWrapper", "report begin");
            PluginManager.a("report_device_location_plugin").getServiceMap().get("REPORT_DEVICE_LOCATION_SERVICE").start(str);
            AliUserLog.c("ReportLocationServiceWrapper", "report end");
        } catch (Throwable th) {
            AliUserLog.a("ReportLocationServiceWrapper", "report location error", th);
        }
    }
}
