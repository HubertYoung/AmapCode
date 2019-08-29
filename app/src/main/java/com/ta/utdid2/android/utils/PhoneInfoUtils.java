package com.ta.utdid2.android.utils;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.Random;

public class PhoneInfoUtils {
    public static String getUniqueID() {
        int nextInt = new Random().nextInt();
        int nextInt2 = new Random().nextInt();
        byte[] bytes = IntUtils.getBytes((int) (System.currentTimeMillis() / 1000));
        byte[] bytes2 = IntUtils.getBytes((int) System.nanoTime());
        byte[] bytes3 = IntUtils.getBytes(nextInt);
        byte[] bytes4 = IntUtils.getBytes(nextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return Base64.encodeToString(bArr, 2);
    }

    public static String getImei(Context context) {
        String str = null;
        if (!BuildCompatUtils.isAtLeastQ() && context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getDeviceId();
                }
            } catch (Exception unused) {
            }
        }
        if (StringUtils.isEmpty(str)) {
            str = getYunOSUuid();
        }
        if (StringUtils.isEmpty(str)) {
            str = getCheckAndroidID(context);
        }
        return StringUtils.isEmpty(str) ? getUniqueID() : str;
    }

    private static String getCheckAndroidID(Context context) {
        String str;
        try {
            str = Secure.getString(context.getContentResolver(), "android_id");
            try {
                if (TextUtils.isEmpty(str) || str.equalsIgnoreCase("a5f5faddde9e9f02") || str.equalsIgnoreCase("8e17f7422b35fbea") || str.equalsIgnoreCase("0000000000000000")) {
                    return "";
                }
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            str = "";
        }
        return str;
    }

    private static String getYunOSUuid() {
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

    public static String getImsi(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getSubscriberId();
                }
            } catch (Exception unused) {
            }
        }
        return StringUtils.isEmpty(str) ? getUniqueID() : str;
    }
}
