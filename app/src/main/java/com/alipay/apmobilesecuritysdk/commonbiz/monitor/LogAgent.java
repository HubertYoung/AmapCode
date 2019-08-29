package com.alipay.apmobilesecuritysdk.commonbiz.monitor;

import com.ali.auth.third.core.model.Constants;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.security.mobile.module.commonutils.CommonUtils;

public class LogAgent {
    public static synchronized void a(String str) {
        Behavior behavior;
        synchronized (LogAgent.class) {
            try {
                BehaviorLogger d = LoggerFactory.d();
                if (str != null && str.length() == 24 && "000000000000000000000000".equals(str)) {
                    behavior = a(SeBehaviorType.UC_UTK_24_ZEROS);
                } else if (str != null && str.length() == 24) {
                    behavior = a(SeBehaviorType.UC_UTK_UTDID);
                } else if (str == null || str.length() != 32) {
                    behavior = a(SeBehaviorType.UC_UTK_ILLEGAL, str);
                } else {
                    behavior = a(SeBehaviorType.UC_UTK_NORMAL);
                }
                d.a(behavior);
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        Behavior behavior;
        synchronized (LogAgent.class) {
            try {
                String str4 = CommonUtils.isNotBlank(str2) ? "Y" : "N";
                if (str3 != null && str3.length() == 24 && "000000000000000000000000".equals(str3)) {
                    behavior = a(SeBehaviorType.UC_APDID_LOCAL, str, (String) "utk_24_zeros", str4);
                } else if (str3 != null && str3.length() == 24) {
                    behavior = a(SeBehaviorType.UC_APDID_LOCAL, str, (String) "utk_utdid", str4);
                } else if (str3 == null || str3.length() != 32) {
                    behavior = a(SeBehaviorType.UC_APDID_LOCAL, str, (String) "utk_illegal", str4);
                } else {
                    behavior = a(SeBehaviorType.UC_APDID_LOCAL, str, (String) "utk_normal", str4);
                }
                LoggerFactory.d().a(behavior);
            } catch (Throwable unused) {
            }
        }
    }

    public static void a(String str, String str2) {
        try {
            LoggerFactory.d().a(a(SeBehaviorType.UC_SC_WARNS, str, str2, (String) null));
        } catch (Throwable unused) {
        }
    }

    public static void b(String str, String str2) {
        try {
            LoggerFactory.d().a(a(SeBehaviorType.UC_SC_ERRORS, str, str2, (String) null));
        } catch (Throwable unused) {
        }
    }

    public static synchronized void c(String str, String str2) {
        synchronized (LogAgent.class) {
            try {
                LoggerFactory.d().a(a(SeBehaviorType.UC_APDID_LOCAL, str, (String) "", CommonUtils.isNotBlank(str2) ? "Y" : "N"));
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized void a(String str, long j, long j2, int i) {
        synchronized (LogAgent.class) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder();
            sb.append(SeBehaviorType.UC_EDGE_INIT_OK);
            sb.append(" : errorCode = ");
            sb.append(str);
            sb.append(", costTime = ");
            sb.append(j);
            sb.append(", versionCode = ");
            sb.append(j2);
            f.b((String) "t0dbg", sb.toString());
            try {
                Behavior a = a(SeBehaviorType.UC_EDGE_INIT_OK, String.valueOf(str), String.valueOf(j), String.valueOf(j2));
                a.j.put("errDetail", String.valueOf(i));
                LoggerFactory.d().a(a);
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized void a(boolean z, boolean z2) {
        synchronized (LogAgent.class) {
            try {
                LoggerFactory.d().a(a(SeBehaviorType.UC_EDGE_INIT_FAIL, z ? "Y" : "N", z2 ? "Y" : "N", (String) null));
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized void a(String str, long j, long j2, String str2, String str3, int i, int i2, String str4) {
        synchronized (LogAgent.class) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder();
            sb.append(SeBehaviorType.UC_EDGE_ASK_RISK);
            sb.append(" : ua = ");
            sb.append(str3);
            sb.append(", errorCode = ");
            sb.append(str);
            sb.append(", costTime = ");
            sb.append(j2);
            f.b((String) "t0dbg", sb.toString());
            try {
                Behavior a = a(SeBehaviorType.UC_EDGE_ASK_RISK, String.valueOf(str), String.valueOf(j), String.valueOf(j2));
                a.j.put(Constants.UA, str3);
                a.j.put("stgyVer", String.valueOf(i));
                a.j.put("sealedData", str2);
                a.j.put("location", str4);
                a.j.put("errDetail", String.valueOf(i2));
                LoggerFactory.d().a(a);
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized void b(String str) {
        synchronized (LogAgent.class) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder();
            sb.append(SeBehaviorType.UC_EDGE_ASK_RISK);
            sb.append(" : logicModel ");
            sb.append(str);
            f.b((String) "t0dbg", sb.toString());
            try {
                LoggerFactory.d().a(a(SeBehaviorType.UC_EDGE_ASK_RISK, (String) "logicModel", String.valueOf(System.currentTimeMillis()), str));
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized void a() {
        synchronized (LogAgent.class) {
        }
    }

    public static synchronized void d(String str, String str2) {
        synchronized (LogAgent.class) {
            try {
                LoggerFactory.d().a(a(SeBehaviorType.UC_EDGE_INJECT_LIST, str, str2, (String) null));
            } catch (Throwable unused) {
            }
        }
    }

    private static Behavior a(SeBehaviorType seBehaviorType) {
        Behavior behavior = new Behavior();
        behavior.a = seBehaviorType.getUseCaseID();
        behavior.b = BehaviorType.EVENT;
        return behavior;
    }

    private static Behavior a(SeBehaviorType seBehaviorType, String str) {
        Behavior behavior = new Behavior();
        behavior.a = seBehaviorType.getUseCaseID();
        behavior.b = BehaviorType.EVENT;
        if (str != null) {
            behavior.g = str;
        }
        return behavior;
    }

    private static Behavior a(SeBehaviorType seBehaviorType, String str, String str2, String str3) {
        Behavior a = a(seBehaviorType, str);
        if (str2 != null) {
            a.h = str2;
        }
        if (str3 != null) {
            a.i = str3;
        }
        return a;
    }
}
