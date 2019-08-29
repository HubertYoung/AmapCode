package com.alibaba.baichuan.android.trade.utils;

import com.alibaba.baichuan.android.trade.constants.UrlConstants;
import java.util.regex.Pattern;

public class k {
    protected static final String a = "com.alibaba.baichuan.android.trade.utils.k";

    public static boolean a(String str) {
        if (str == null) {
            return false;
        }
        for (String matches : UrlConstants.RE_LOGIN_URLS) {
            if (Pattern.matches(matches, str)) {
                return true;
            }
        }
        return false;
    }
}
