package com.ali.user.mobile.log;

import android.text.TextUtils;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.info.AppInfo;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.ex.ExceptionEnum;
import com.alipay.mobileapp.biz.rpc.mdap.UnifyCustomMdapFacade;
import com.amap.bundle.drivecommon.inter.NetConstant;
import java.util.Map;

public class LogAgent {
    private static long a = -1;
    private static StringBuilder b = new StringBuilder();

    public static void a(String str, String str2, String str3, String str4, String str5) {
        a(str, str2, str3, str4, str5, BehaviorType.OPENPAGE, null, null, null);
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        a(str, str2, str3, str4, str5, BehaviorType.OPENPAGE, str6, str7, str8);
    }

    public static void b(String str, String str2, String str3, String str4, String str5) {
        a(str, str2, str3, str4, str5, BehaviorType.CLICK, null, null, null);
    }

    public static void b(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        a(str, str2, str3, str4, str5, BehaviorType.CLICK, str6, str7, str8);
    }

    public static void a(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        LoggerFactory.d().a(b(str, str2, str3, str4, str5, map));
    }

    public static void c(String str, String str2, String str3, String str4, String str5) {
        LoggerFactory.d().a(b(str2, str3, LoginMonitor.a(str), str4, str5, null));
    }

    private static void a(String str, String str2, String str3, String str4, String str5, BehaviorType behaviorType, String str6, String str7, String str8) {
        Behavior b2 = b(str, str2, str6, str7, str8, null);
        b2.e = str3;
        b2.a(NetConstant.KEY_MONEY_ACCOUNT, str4);
        b2.a("token", str5);
        b2.b = behaviorType;
        LoggerFactory.d().a(b2);
    }

    public static void a(String str, String str2, String str3, String str4) {
        LoggerFactory.d().a(b(str, str2, str3, str4, null, null));
    }

    public static void a(String str, String str2) {
        a(str, str2, (String) null, (String) null, (String) null, BehaviorType.CLICK);
    }

    public static void d(String str, String str2, String str3, String str4, String str5) {
        a(str, str2, str3, str4, str5, BehaviorType.CLICK);
    }

    public static void e(String str, String str2, String str3, String str4, String str5) {
        a(str, str2, str3, str4, str5, BehaviorType.OPENPAGE);
    }

    public static void f(String str, String str2, String str3, String str4, String str5) {
        a(str, str2, str3, str4, str5, BehaviorType.EVENT);
    }

    private static void a(String str, String str2, String str3, String str4, String str5, BehaviorType behaviorType) {
        Behavior b2 = b(str, str2, str3, str4, str5, null);
        b2.b = behaviorType;
        LoggerFactory.d().a(b2);
    }

    public static void a() {
        a = -1;
        b.delete(0, b.length());
    }

    public static void a(String str, String str2, String str3, String str4, String str5, Map<String, String> map, final String str6) {
        final Behavior b2 = b(str, str2, str3, str4, str5, map);
        SecurityUtil.a((Runnable) new Runnable() {
            public final void run() {
                try {
                    ((UnifyCustomMdapFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(UnifyCustomMdapFacade.class)).mobileUnifyRegister(RpcLogUtil.a(Behavior.this, str6));
                } catch (Throwable th) {
                    AliUserLog.b((String) "LogAgent", th);
                }
            }
        });
    }

    public static void a(Throwable th) {
        LoggerFactory.e().a(ExceptionEnum.EXCEPTION, "", "", th, "");
    }

    public static void a(String str, String str2, String str3, Map<String, String> map) {
        String[] strArr = null;
        if (map != null) {
            try {
                if (!map.isEmpty()) {
                    strArr = new String[]{str3, map.toString()};
                }
            } catch (Throwable th) {
                LoggerFactory.f().b("mtBizReport", "error", th);
                return;
            }
        }
        LoggerFactory.e().a(str, str2, strArr);
    }

    private static Behavior b(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        if (-1 == a) {
            a = System.currentTimeMillis();
        }
        Behavior behavior = new Behavior();
        behavior.a = str;
        behavior.c = str2;
        if (!TextUtils.isEmpty(str3)) {
            behavior.g = str3;
        }
        if (!TextUtils.isEmpty(str4)) {
            behavior.h = str4;
        }
        if (!TextUtils.isEmpty(str5)) {
            behavior.i = str5;
        }
        if (map != null) {
            for (String next : map.keySet()) {
                behavior.a(next, map.get(next));
            }
        }
        behavior.a("session", String.valueOf(a));
        behavior.a("appId", AppInfo.getInstance().getSdkId());
        behavior.a("appVersion", AppInfo.getInstance().getSdkVersion());
        behavior.a("tests", b.substring(0));
        return behavior;
    }
}
