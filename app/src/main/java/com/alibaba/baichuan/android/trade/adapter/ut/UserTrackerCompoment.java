package com.alibaba.baichuan.android.trade.adapter.ut;

import com.alibaba.baichuan.android.trade.adapter.ut.performance.PerformancePoint;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

public class UserTrackerCompoment {
    private static final String a = "UserTrackerCompoment";
    private static long b;

    private static String a(String str) {
        if (str.contains(UserTrackerConstants.U_PAGE_NATIVE)) {
            return "Page_Native";
        }
        if (str.contains(UserTrackerConstants.U_PAGE_H5)) {
            return "Page_H5";
        }
        if (str.contains("Taoke_Trace_")) {
            str = "Taoke_Trace";
        }
        return str;
    }

    public static void endInitTimeRecord() {
        b = System.currentTimeMillis() - b;
    }

    public static long getUTInitTime() {
        return b;
    }

    public static void sendPerfomancePoint(PerformancePoint performancePoint) {
        if (performancePoint == null) {
            a.a(a, "sendPerfomancePoint", "pagePerformancePoint is null");
            return;
        }
        String str = a;
        StringBuilder sb = new StringBuilder("sendPerfomancePoint:");
        sb.append(performancePoint.toString());
        AlibcLogger.d(str, sb.toString());
        if (!performancePoint.checkData()) {
            AlibcLogger.e(a, "sendPerfomancePoint: time is too large");
        }
        AlibcUserTracker.getInstance().sendPerfomancePoint("BCTradeSDK", performancePoint.getMonitorPoint(), performancePoint.getDimensionValues(), performancePoint.getMeasureValues());
    }

    public static void sendUseabilityFailure(String str, String str2) {
        sendUseabilityFailure(str, str2, null);
    }

    public static void sendUseabilityFailure(String str, String str2, String str3) {
        if (str == null || str2 == null) {
            a.a(a, "sendUseabilityFailure", "label/errMsg is null!");
            return;
        }
        UsabilityErrorMsg usabilityMsg = ErrorMsgBeanGererator.getUsabilityMsg(str, str2, str3);
        if (usabilityMsg == null) {
            a.a(a, "sendUseabilityFailure", "usabilityErrorMsg is null !");
            return;
        }
        AlibcUserTracker.getInstance().sendUseabilityFailure("BCTradeSDK", a(str), usabilityMsg.getErrorCodeString(), usabilityMsg.getErrMsg());
    }

    public static void sendUseabilityFailureForOtherErrmsg(String str, String str2, String str3) {
        if (str == null || str3 == null) {
            a.a(a, "sendUseabilityFailure", "monitorPoint/errCode is null!");
            return;
        }
        String a2 = a(str);
        AlibcUserTracker instance = AlibcUserTracker.getInstance();
        if (str2 == null) {
            str2 = "";
        }
        instance.sendUseabilityFailure("BCTradeSDK", a2, str3, str2);
    }

    public static void sendUseabilitySuccess(String str) {
        if (str == null) {
            a.a(a, "sendUseabilityFailure", "label/errMsg is null!");
            return;
        }
        AlibcUserTracker.getInstance().sendUseabilitySuccess("BCTradeSDK", a(str));
    }

    public static void startInitTimeRecord() {
        b = System.currentTimeMillis();
    }
}
