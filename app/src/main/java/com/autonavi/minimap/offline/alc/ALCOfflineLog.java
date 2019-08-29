package com.autonavi.minimap.offline.alc;

import com.amap.bundle.logs.AMapLog;

public class ALCOfflineLog {
    public static void p1(String str, String str2, String str3) {
        AMapLog.logFatalNative(AMapLog.GROUP_OFFLINE, str, str2, str3);
    }

    public static void p2(String str, String str2, String str3) {
        AMapLog.logErrorNative(AMapLog.GROUP_OFFLINE, str, str2, str3);
    }

    public static void p3(String str, String str2, String str3) {
        AMapLog.logNormalNative(AMapLog.GROUP_OFFLINE, str, str2, str3);
    }
}
