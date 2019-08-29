package mtopsdk.mtop.global;

import android.content.Context;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.intf.Mtop;

public class SDKUtils {
    public static long getCorrectionTime() {
        return getTimeOffset() + (System.currentTimeMillis() / 1000);
    }

    public static long getCorrectionTimeMillis() {
        return getCorrectionTime() * 1000;
    }

    public static long getTimeOffset() {
        String a = fgy.a();
        if (fdd.a(a)) {
            try {
                return Long.parseLong(a);
            } catch (NumberFormatException unused) {
                TBSdkLog.d("mtopsdk.SDKUtils", "[getTimeOffset]parse t_offset failed");
            }
        } else {
            fgy.c("t_offset", "0");
            return 0;
        }
    }

    @Deprecated
    public static void registerSessionInfo(String str, String str2) {
        Mtop.a((String) "INNER", (Context) null).a((String) null, str, str2);
    }

    @Deprecated
    public static void registerSessionInfo(String str, String str2, String str3) {
        Mtop.a((String) "INNER", (Context) null).a((String) null, str, str3);
    }

    @Deprecated
    public static void logOut() {
        Mtop.a((String) "INNER", (Context) null).c();
    }

    @Deprecated
    public static void registerTtid(String str) {
        Mtop.a((String) "INNER", (Context) null).a(str);
    }

    @Deprecated
    public static void registerMtopSdkProperty(String str, String str2) {
        ffd ffd = Mtop.a((String) "INNER", (Context) null).c;
        if (fdd.a(str) && fdd.a(str2)) {
            ffd.a().put(str, str2);
            if (TBSdkLog.a(LogEnable.DebugEnable)) {
                StringBuilder sb = new StringBuilder("[registerMtopSdkProperty]register MtopSdk Property succeed,key=");
                sb.append(str);
                sb.append(",value=");
                sb.append(str2);
                TBSdkLog.a((String) "mtopsdk.MtopConfig", sb.toString());
            }
        }
    }

    @Deprecated
    public static boolean removeCacheBlock(String str) {
        ep epVar = Mtop.a((String) "INNER", (Context) null).c.u;
        return epVar != null && epVar.e();
    }

    @Deprecated
    public static boolean unintallCacheBlock(String str) {
        ep epVar = Mtop.a((String) "INNER", (Context) null).c.u;
        return epVar != null && epVar.d();
    }

    @Deprecated
    public static boolean removeCacheItem(String str, String str2) {
        Mtop a = Mtop.a((String) "INNER", (Context) null);
        if (fdd.b(str2)) {
            TBSdkLog.d("mtopsdk.Mtop", "[removeCacheItem] remove CacheItem failed,invalid cacheKey=".concat(String.valueOf(str2)));
        } else {
            ep epVar = a.c.u;
            if (epVar != null && epVar.c()) {
                return true;
            }
        }
        return false;
    }
}
