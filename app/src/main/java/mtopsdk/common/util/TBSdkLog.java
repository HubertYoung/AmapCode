package mtopsdk.common.util;

import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.util.HashMap;
import java.util.Map;
import mtopsdk.common.log.LogAdapter;

public final class TBSdkLog {
    private static boolean a = true;
    private static boolean b = true;
    private static LogEnable c = LogEnable.DebugEnable;
    private static Map<String, LogEnable> d = new HashMap(5);
    private static LogAdapter e;

    public enum LogEnable {
        VerboseEnable(SecureSignatureDefine.SG_KEY_SIGN_VERSION),
        DebugEnable("D"),
        InfoEnable(LogHelper.DEFAULT_LEVEL),
        WarnEnable("W"),
        ErrorEnable("E"),
        NoneEnable("L");
        
        private String logEnable;

        public final String getLogEnable() {
            return this.logEnable;
        }

        private LogEnable(String str) {
            this.logEnable = str;
        }
    }

    static {
        LogEnable[] values;
        for (LogEnable logEnable : LogEnable.values()) {
            d.put(logEnable.getLogEnable(), logEnable);
        }
    }

    public static void a(LogAdapter logAdapter) {
        e = logAdapter;
        new StringBuilder("[setLogAdapter] logAdapter=").append(logAdapter);
    }

    public static void a(boolean z) {
        a = z;
    }

    public static void a() {
        b = false;
    }

    private static void b(LogEnable logEnable) {
        if (logEnable != null) {
            c = logEnable;
            new StringBuilder("[setLogEnable] logEnable=").append(logEnable);
        }
    }

    public static void a(String str, String str2) {
        a(str, (String) null, str2);
    }

    public static void a(String str, String str2, String str3) {
        if (a(LogEnable.DebugEnable)) {
            if (b) {
                if (e != null) {
                    e.a(2, str, a(str2, str3), null);
                }
            } else if (a) {
                a(str2, str3);
            }
        }
    }

    public static void b(String str, String str2) {
        b(str, (String) null, str2);
    }

    public static void b(String str, String str2, String str3) {
        if (a(LogEnable.InfoEnable)) {
            if (b) {
                if (e != null) {
                    e.a(4, str, a(str2, str3), null);
                }
            } else if (a) {
                a(str2, str3);
            }
        }
    }

    public static void c(String str, String str2) {
        c(str, null, str2);
    }

    public static void c(String str, String str2, String str3) {
        if (a(LogEnable.WarnEnable)) {
            if (b) {
                if (e != null) {
                    e.a(8, str, a(str2, str3), null);
                }
            } else if (a) {
                a(str2, str3);
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        a(str, null, str2, th);
    }

    public static void a(String str, String str2, String str3, Throwable th) {
        if (a(LogEnable.WarnEnable)) {
            if (b) {
                if (e != null) {
                    e.a(8, str, a(str2, str3), th);
                }
            } else if (a) {
                a(str2, str3);
            }
        }
    }

    public static void d(String str, String str2) {
        d(str, null, str2);
    }

    public static void d(String str, String str2, String str3) {
        if (a(LogEnable.ErrorEnable)) {
            if (b) {
                if (e != null) {
                    e.a(16, str, a(str2, str3), null);
                }
            } else if (a) {
                a(str2, str3);
            }
        }
    }

    public static void b(String str, String str2, Throwable th) {
        b(str, null, str2, th);
    }

    public static void b(String str, String str2, String str3, Throwable th) {
        if (a(LogEnable.ErrorEnable)) {
            if (b) {
                if (e != null) {
                    e.a(16, str, a(str2, str3), th);
                }
            } else if (a) {
                a(str2, str3);
            }
        }
    }

    private static String a(String str, String... strArr) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append("[seq:");
            sb.append(str);
            sb.append("]|");
        }
        for (int i = 0; i <= 0; i++) {
            sb.append(strArr[0]);
        }
        return sb.toString();
    }

    public static boolean a(LogEnable logEnable) {
        if (b && e != null) {
            LogEnable logEnable2 = d.get(e.a());
            if (!(logEnable2 == null || c.ordinal() == logEnable2.ordinal())) {
                b(logEnable2);
            }
        }
        return logEnable.ordinal() >= c.ordinal();
    }

    public static void e(String str, String str2) {
        try {
            if (e != null) {
                e.a(str, str2);
            }
        } catch (Throwable unused) {
        }
    }
}
