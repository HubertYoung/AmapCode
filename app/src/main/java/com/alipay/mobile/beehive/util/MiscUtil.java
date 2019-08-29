package com.alipay.mobile.beehive.util;

import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Random;

public class MiscUtil {
    private static final int LOG_STRING_LEN = 40;
    public static final String NULL_STR = "NULL";

    public static String safeToString(Object target, String postStr) {
        return target.getClass().getSimpleName() + AUScreenAdaptTool.PREFIX_ID + Integer.toHexString(target.hashCode()) + "(" + postStr + ")";
    }

    public static String safeToString(Object v) {
        if (v == null) {
            return NULL_STR;
        }
        if (v instanceof String) {
            return (String) v;
        }
        String s = v.toString();
        if (!TextUtils.isEmpty(s) && s.length() > 40) {
            try {
                return s.substring(0, 40);
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) EventBusManager.TAG, (Throwable) ex);
            }
        }
        return s;
    }

    public static String randomStr(int len) {
        char[] chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }
}
