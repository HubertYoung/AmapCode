package com.iflytek.tts.TtsService.alc;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.alc.model.ALCLogLevel;

public final class ALCTtsLog {
    public static void p1(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P1, (String) AMapLog.GROUP_DRIVE, (String) "D1", str, str2, str3);
    }

    public static void p2(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P2, (String) AMapLog.GROUP_DRIVE, (String) "D1", str, str2, str3);
    }

    public static void p3(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P3, (String) AMapLog.GROUP_DRIVE, (String) "D1", str, str2, str3);
    }

    public static void p4(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P4, (String) AMapLog.GROUP_DRIVE, (String) "D1", str, str2, str3);
    }

    public static void p5(String str, String str2, String str3) {
        cjy.a(ALCLogLevel.P5, (String) AMapLog.GROUP_DRIVE, (String) "D1", str, str2, str3);
    }
}
