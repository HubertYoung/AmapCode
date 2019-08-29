package com.alipay.security.mobile.module.localstorage;

import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.io.File;

public class PublicStorage {
    private static final String SDCARD_FORDER_PATH = ".SystemConfig";

    public static void writeDataToPublicArea(String str, String str2) {
        try {
            SystemPropertyStorage.writeDataToSettings(str, str2);
            if (SDCardStorage.isSdCardAvailable()) {
                StringBuilder sb = new StringBuilder(SDCARD_FORDER_PATH);
                sb.append(File.separator);
                sb.append(str);
                SDCardStorage.writeDataToSDCard(sb.toString(), str2);
            }
        } catch (Throwable unused) {
        }
    }

    public static String readDataFromPublicArea(String str) {
        try {
            String readDataFromSettings = SystemPropertyStorage.readDataFromSettings(str);
            try {
                if (CommonUtils.isBlank(readDataFromSettings)) {
                    StringBuilder sb = new StringBuilder(SDCARD_FORDER_PATH);
                    sb.append(File.separator);
                    sb.append(str);
                    return SDCardStorage.readDataFromSDCard(sb.toString());
                }
            } catch (Throwable unused) {
            }
            return readDataFromSettings;
        } catch (Throwable unused2) {
            r3 = "";
            return "";
        }
    }
}
