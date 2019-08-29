package com.autonavi.minimap.ajx3.modules.net;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import java.util.HashMap;
import java.util.Map;

public class AjxUrlParser {
    private static Map<String, String> sAjxUrlMappingMap;

    static {
        HashMap hashMap = new HashMap();
        sAjxUrlMappingMap = hashMap;
        hashMap.put("aos.comment", "aos_ugc_comment_url");
        sAjxUrlMappingMap.put("aos.sns", ConfigerHelper.AOS_SNS_URL_KEY);
        sAjxUrlMappingMap.put("aos.traffic", "aos_traffic_url");
        sAjxUrlMappingMap.put("aos.m5", ConfigerHelper.SEARCH_AOS_URL_KEY);
        sAjxUrlMappingMap.put("aos.oss", ConfigerHelper.OPERATIONAL_URL_KEY);
        sAjxUrlMappingMap.put("aos.wb", ConfigerHelper.WB_URL_KEY);
        sAjxUrlMappingMap.put("aos.account_level", ConfigerHelper.USER_LEVEL_URL);
        sAjxUrlMappingMap.put("aos.account_checkin", ConfigerHelper.USER_CHECKIN_URL);
        sAjxUrlMappingMap.put("aos.passport", ConfigerHelper.AOS_PASSPORT_URL_KEY);
        sAjxUrlMappingMap.put("aos.awaken", ConfigerHelper.H5_LOG_URL);
        sAjxUrlMappingMap.put("aos.drive", ConfigerHelper.DRIVE_AOS_URL_KEY);
        sAjxUrlMappingMap.put("aos.url", ConfigerHelper.AOS_URL_KEY);
    }

    public static String ajxUrlToAosUrl(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        String[] split = str.split("\\?", 2);
        if (split.length <= 0) {
            return null;
        }
        String str2 = split[0];
        int indexOf = str2.indexOf("$");
        if (indexOf == -1) {
            return str2;
        }
        int i = indexOf + 1;
        int indexOf2 = str2.indexOf("$", i);
        if (indexOf2 != -1) {
            return convertUrl(str2.substring(i, indexOf2).toLowerCase(), str2.substring(indexOf2 + 1, str2.length()));
        }
        return null;
    }

    private static String convertUrl(String str, String str2) {
        boolean z;
        String str3 = sAjxUrlMappingMap.get(str);
        if (TextUtils.isEmpty(str3) && "aos.ts".equalsIgnoreCase(str)) {
            cuh cuh = (cuh) ank.a(cuh.class);
            if (cuh == null) {
                z = true;
            } else {
                z = cuh.r();
            }
            str3 = z ? "ts_polling_https_url" : ConfigerHelper.AOS_TS_POLLING_URL_KEY;
        }
        String str4 = "";
        if (!TextUtils.isEmpty(str3)) {
            if (!aaw.a(str3)) {
                str4 = aaf.b(str3);
            }
        } else if (!aaw.a(str2)) {
            str4 = aaf.b(ConfigerHelper.AOS_URL_KEY);
        }
        if (str4.endsWith("/")) {
            str4 = str4.substring(0, str4.length() - 1);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        sb.append(str2);
        String sb2 = sb.toString();
        if (sb2.contains("?")) {
            return sb2;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("?");
        return sb3.toString();
    }
}
