package com.alipay.mobile.securitycommon.aliauth.util;

import com.alipay.mobile.account.adapter.LogAdapter;

public class LogUtil {
    public static void log(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Thread, id:");
        sb.append(Thread.currentThread().getId());
        sb.append(",name:");
        sb.append(Thread.currentThread().getName());
        sb.append("] ");
        sb.append(str2);
        LogAdapter.a(str, sb.toString());
    }
}
