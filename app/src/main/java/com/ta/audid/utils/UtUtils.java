package com.ta.audid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.ta.audid.Variables;
import com.ta.utdid2.android.utils.Base64;
import com.ta.utdid2.android.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class UtUtils {
    private static final String UTDID_MODULE = "UtdidMonitor";

    public static void sendUtdidMonitorEvent(String str, Map<String, String> map) {
    }

    public static String getUserNick() {
        Context context = Variables.getInstance().getContext();
        if (context == null) {
            return "";
        }
        String str = "";
        SharedPreferences sharedPreferences = context.getSharedPreferences("UTCommon", 0);
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString("_lun", "");
            if (!StringUtils.isEmpty(string)) {
                try {
                    str = new String(Base64.decode(string.getBytes(), 2), "UTF-8");
                } catch (Exception e) {
                    UtdidLogger.d((String) "", e);
                }
            }
        }
        return str;
    }

    public static String getUserId() {
        Context context = Variables.getInstance().getContext();
        if (context == null) {
            return "";
        }
        String str = "";
        SharedPreferences sharedPreferences = context.getSharedPreferences("UTCommon", 0);
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString("_luid", "");
            if (!StringUtils.isEmpty(string)) {
                try {
                    str = new String(Base64.decode(string.getBytes(), 2), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    UtdidLogger.d((String) "", e);
                }
            }
        }
        return str;
    }
}
