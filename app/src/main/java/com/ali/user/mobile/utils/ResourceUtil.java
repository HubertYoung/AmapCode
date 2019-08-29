package com.ali.user.mobile.utils;

import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.mobile.common.share.widget.ResUtils;

public class ResourceUtil {
    public static String a(int i) {
        try {
            return AliUserInit.b().getString(i);
        } catch (Throwable th) {
            AliUserLog.b((String) "ResourceUtil", th);
            r2 = "";
            return "";
        }
    }

    public static String a(String str) {
        return a(AliUserInit.b().getResources().getIdentifier(str, ResUtils.STRING, AliUserInit.b().getPackageName()));
    }
}
