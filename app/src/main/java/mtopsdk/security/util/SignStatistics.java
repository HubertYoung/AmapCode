package mtopsdk.security.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.util.TBSdkLog;

public final class SignStatistics {
    private static volatile ffv a;
    private static volatile AtomicBoolean b = new AtomicBoolean(false);

    public interface SignStatsType {

        @Retention(RetentionPolicy.SOURCE)
        public @interface Definition {
        }
    }

    public static void a(ffv ffv) {
        a = ffv;
        TBSdkLog.b("mtopsdk.SignStatistics", "set IUploadStats =".concat(String.valueOf(ffv)));
    }

    public static void a(String str, String str2, String str3) {
        if (a != null) {
            if (b.compareAndSet(false, true)) {
                HashSet hashSet = new HashSet();
                hashSet.add("type");
                hashSet.add("errorcode");
                hashSet.add("flag");
                if (a != null) {
                    a.a((String) "mtopsdk", (String) "signException", (Set<String>) hashSet, (Set<String>) null);
                }
            }
            HashMap hashMap = new HashMap();
            hashMap.put("type", str);
            hashMap.put("errorcode", str2);
            hashMap.put("flag", str3);
            if (a != null) {
                a.a((String) "mtopsdk", (String) "signException", (Map<String, String>) hashMap, (Map<String, Double>) null);
            }
        }
    }
}
