package com.ali.user.mobile.util;

import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;

public class LoginUtil {
    public static String a(UserInfo userInfo) {
        if (userInfo == null) {
            return "(userInfo == null)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(userInfo != null, autoLogin:");
        sb.append(userInfo.isAutoLogin());
        sb.append(")");
        return sb.toString();
    }

    public static void a(String str, String str2) {
        String str3;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("-StackTrace:");
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(" ### ");
                sb.append(stackTraceElement.toString());
            }
            str3 = sb.toString();
        } else {
            str3 = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[Thread:");
        sb2.append(Thread.currentThread().getId());
        sb2.append("] ");
        sb2.append(str3);
        AliUserLog.c(str, sb2.toString());
        LogAgent.f("UC-ZHAQ-56", "loginTrace-stackTrace", str3, "", "");
    }
}
