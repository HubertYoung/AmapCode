package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MobileNumberUtil {
    public static final String CHINA_MAINLAND_MOBILE_REGEX = "^((\\+86)|(86))?(1)\\d{10}$";
    public static final String HONGKONG_MOBILE_REGEX = "^((\\+852)|(852))?[5,6,9]\\d{7}$";
    public static final String TAIWAN_MOBILE_REGEX = "^((\\+886)|(886))?0?9\\d{8}$";

    public MobileNumberUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static final boolean isCnHkTwMobile(String mobileNo) {
        if (mobileNo == null) {
            return false;
        }
        if (isChinaMainlandMobile(mobileNo) || isHongKongMobile(mobileNo) || isTaiwanMobile(mobileNo)) {
            return true;
        }
        return false;
    }

    public static final boolean isChinaMainlandMobile(String mobileNo) {
        if (mobileNo == null) {
            return false;
        }
        return mobileNo.matches(CHINA_MAINLAND_MOBILE_REGEX);
    }

    public static String trimChinaMainlandMobile(String mobileNo) {
        if (mobileNo == null) {
            return null;
        }
        Matcher mobileMatcher = Pattern.compile(CHINA_MAINLAND_MOBILE_REGEX).matcher(mobileNo.replaceAll(Token.SEPARATOR, "").replaceAll("-", ""));
        if (mobileMatcher.matches()) {
            return mobileMatcher.group(0).replaceFirst("^(\\+{0,1}86)", "");
        }
        return null;
    }

    public static String formatChinaMainlandMobile(String mobileNo) {
        if (mobileNo == null || mobileNo.length() < 11) {
            return mobileNo;
        }
        StringBuffer result = new StringBuffer(mobileNo);
        int length = mobileNo.length();
        result.insert(length - 4, Token.SEPARATOR);
        result.insert(length - 8, Token.SEPARATOR);
        return result.toString();
    }

    public static String obfuscateChinaMainlandMobile(String mobileNo) {
        String beforeString = mobileNo.substring(0, 3);
        return beforeString + "****" + mobileNo.substring(7, mobileNo.length());
    }

    public static final boolean isHongKongMobile(String mobileNo) {
        if (mobileNo == null) {
            return false;
        }
        return mobileNo.matches(HONGKONG_MOBILE_REGEX);
    }

    public static final boolean isTaiwanMobile(String mobileNo) {
        if (mobileNo == null) {
            return false;
        }
        return mobileNo.matches(TAIWAN_MOBILE_REGEX);
    }
}
