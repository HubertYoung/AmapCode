package com.ali.user.mobile.register;

import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.register.model.State;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.amap.bundle.drivecommon.inter.NetConstant;
import java.util.HashMap;
import java.util.Map;

public class LogUtils {
    public static void a(String str, String str2) {
        String str3;
        String str4;
        if (RegContext.a().c == null || RegContext.a().c.a() == null) {
            str4 = null;
            str3 = null;
        } else {
            State a = RegContext.a().c.a();
            str3 = a.f;
            str4 = a.a() != null ? a.a().asAccount() : null;
        }
        LogAgent.b(str, str2, null, str4, str3);
    }

    public static void a(String str, String str2, String str3, String str4) {
        String str5;
        String str6;
        String str7 = null;
        if (RegContext.a().c == null || RegContext.a().c.a() == null) {
            str6 = null;
            str5 = null;
        } else {
            State a = RegContext.a().c.a();
            String str8 = a.f;
            if (a.a() != null) {
                str7 = a.a().asAccount();
            }
            str6 = str7;
            str5 = str8;
        }
        LogAgent.b(str, str2, null, str6, str5, str3, str4, null);
    }

    public static void b(String str, String str2) {
        String str3;
        String str4;
        if (RegContext.a().c == null || RegContext.a().c.a() == null) {
            str4 = null;
            str3 = null;
        } else {
            State a = RegContext.a().c.a();
            str3 = a.f;
            str4 = a.a() != null ? a.a().asAccount() : null;
        }
        LogAgent.a(str, str2, null, str4, str3);
    }

    public static void b(String str, String str2, String str3, String str4) {
        String str5;
        String str6;
        String str7 = null;
        if (RegContext.a().c == null || RegContext.a().c.a() == null) {
            str6 = null;
            str5 = null;
        } else {
            State a = RegContext.a().c.a();
            String str8 = a.f;
            if (a.a() != null) {
                str7 = a.a().asAccount();
            }
            str6 = str7;
            str5 = str8;
        }
        LogAgent.a(str, str2, null, str6, str5, str3, str4, null);
    }

    public static void c(String str, String str2, String str3, String str4) {
        Object obj;
        String str5 = null;
        if (RegContext.a().c == null || RegContext.a().c.a() == null) {
            obj = null;
        } else {
            State a = RegContext.a().c.a();
            obj = a.f;
            if (a.a() != null) {
                str5 = a.a().asAccount();
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put(NetConstant.KEY_MONEY_ACCOUNT, str5);
        hashMap.put("token", obj);
        LogAgent.a(str, str2, str3, str4, null, hashMap, BehavorID.OPENPAGE);
    }

    public static void d(String str, String str2, String str3, String str4) {
        LogAgent.a(str, str2, str3, str4, (String) null, (Map<String, String>) null);
    }
}
