package com.alipay.security.mobile.module.localstorage;

import com.alipay.security.mobile.module.commonutils.CommonUtils;

public class SystemPropertyStorage {
    public static String readDataFromSettings(String str) {
        if (CommonUtils.isNotBlank(str)) {
            System.clearProperty(str);
        }
        return "";
    }

    public static void writeDataToSettings(String str, String str2) {
        if (CommonUtils.isNotBlank(str)) {
            System.clearProperty(str);
        }
    }
}
