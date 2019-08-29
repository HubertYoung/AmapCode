package com.ta.audid.utils;

import android.os.Build;
import android.text.TextUtils;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.android.utils.SystemProperties;
import java.lang.reflect.Field;

public class YunOSDeviceUtils {
    public static boolean isYunOSPhoneSystem() {
        return (System.getProperty("java.vm.name") != null && System.getProperty("java.vm.name").toLowerCase().contains("lemur")) || System.getProperty("ro.yunos.version") != null || !TextUtils.isEmpty(SystemProperties.get("ro.yunos.build.version"));
    }

    public static boolean isYunOSTvSystem() {
        return !TextUtils.isEmpty(SystemProperties.get("ro.yunos.product.chip")) || !TextUtils.isEmpty(SystemProperties.get("ro.yunos.hardware"));
    }

    public static String getYunOSUuid() {
        String str = SystemProperties.get("ro.aliyun.clouduuid", "");
        if (TextUtils.isEmpty(str)) {
            str = SystemProperties.get("ro.sys.aliyun.clouduuid", "");
        }
        return TextUtils.isEmpty(str) ? getYunOSTVUuid() : str;
    }

    private static String getYunOSTVUuid() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getYunOSVersion() {
        String property = System.getProperty("ro.yunos.version", "");
        String buildVersion = getBuildVersion();
        return !StringUtils.isEmpty(buildVersion) ? buildVersion : property;
    }

    private static String getBuildVersion() {
        try {
            Field declaredField = Build.class.getDeclaredField("YUNOS_BUILD_VERSION");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                return (String) declaredField.get(new String());
            }
        } catch (Exception unused) {
        }
        return "";
    }
}
