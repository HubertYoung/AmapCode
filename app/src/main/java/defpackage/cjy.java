package defpackage;

import com.autonavi.jni.alc.ALCManager;
import com.autonavi.minimap.alc.model.ALCLogLevel;

/* renamed from: cjy reason: default package */
/* compiled from: ALCLog */
public final class cjy {
    public static cjz a;

    public static void a(ALCLogLevel aLCLogLevel, long j, String str, String str2, int i, String str3) {
        ALCManager.getInstance().record(aLCLogLevel, j, str, str2, i, str3);
        if (a != null) {
            a.onRecord(aLCLogLevel, j, str, str2, i, str3);
        }
    }

    public static void a(ALCLogLevel aLCLogLevel, long j, String str, String str2, String str3, int i, String str4) {
        ALCManager.getInstance().recordWithSubTag(aLCLogLevel, 0, j, str, str2, str3, i, str4);
        if (a != null) {
            a.onRecord(aLCLogLevel, j, str, str3, i, str4);
        }
    }

    public static void a(ALCLogLevel aLCLogLevel, String str, String str2, String str3, String str4, String str5) {
        ALCManager.getInstance().log(aLCLogLevel, str, str2, str3, str4, str5);
        if (a != null) {
            a.onLog(aLCLogLevel, str, str2, str3, str4, str5);
        }
    }

    public static void a(String str) {
        a(ALCLogLevel.LOG_DEBUG, 32768, (String) "amap", (String) "", 0, str);
    }

    public static void b(String str) {
        a(ALCLogLevel.LOG_WARN, 32768, (String) "amap", (String) "", 0, str);
    }

    public static void c(String str) {
        a(ALCLogLevel.LOG_ERROR, 32768, (String) "amap", (String) "", 0, str);
    }
}
