package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;

public class SecurityUtil {
    public static final String HIDEACCOUNT = "hideaccount";
    public static final String HIDEIDCARD = "hideidcard";
    public static final String HIDENAME = "hidename";

    public SecurityUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static String accountHide(String account, String tag) {
        String hideResult = account;
        if (account == null) {
            return "";
        }
        if (HIDEACCOUNT.equals(tag)) {
            if (account.contains(AUScreenAdaptTool.PREFIX_ID)) {
                int index = account.indexOf(AUScreenAdaptTool.PREFIX_ID);
                String beforeString = account.substring(0, index);
                String hehindString = account.substring(index, account.length());
                hideResult = beforeString.length() >= 3 ? beforeString.substring(0, 3) + "***" + hehindString : beforeString + "***" + hehindString;
            } else if (MobileNumberUtil.isChinaMainlandMobile(account)) {
                hideResult = MobileNumberUtil.obfuscateChinaMainlandMobile(account);
            }
        } else if (HIDENAME.equals(tag)) {
            int nameLength = account.length();
            if (nameLength > 1) {
                hideResult = "*" + account.substring(1, nameLength);
            }
        } else if (HIDEIDCARD.equals(tag)) {
            hideResult = account.substring(0, 1) + "****************" + account.substring(account.length() - 1);
        }
        return hideResult;
    }
}
