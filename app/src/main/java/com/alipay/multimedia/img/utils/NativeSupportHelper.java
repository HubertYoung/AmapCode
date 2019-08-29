package com.alipay.multimedia.img.utils;

import android.os.Build;
import android.os.Environment;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NativeSupportHelper {
    private static final String TAG = "NativeSupportHelper";
    private static boolean bForceDisableForGaode = true;
    private static Map<String, String> sBlackMap = new HashMap();

    static {
        sBlackMap.put("samsung", "GT-I9103,GT-P7310,GT-P7300,GT-P7500,GT-P7510,GT-P5210,GT-P5200".toLowerCase());
        sBlackMap.put("hisense", "HS-T96".toLowerCase());
        sBlackMap.put(DeviceProperty.ALIAS_ZTE, "ZTE U930,ZTE U880F1,ZTE U970".toLowerCase());
        sBlackMap.put(LeakCanaryInternals.MOTOROLA, "Xoom,Xoom Wifi,MB860,MB855".toLowerCase());
        sBlackMap.put("asus", "Transformer TF101,K016".toLowerCase());
        sBlackMap.put("lge", "LG-P990".toLowerCase());
        sBlackMap.put(DeviceProperty.ALIAS_HUAWEI, "HUAWEI P6-T00".toLowerCase());
    }

    public static boolean isSupportNativeProcess() {
        try {
            return !isForceDisable() && !inBlackList();
        } catch (Throwable t) {
            LogUtils.e(TAG, "isSupportNativeProcess error", t);
            return true;
        }
    }

    public static boolean isForceDisable() {
        if (bForceDisableForGaode) {
            return true;
        }
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return new File(Environment.getExternalStorageDirectory(), "alipay/multimedia/.disableNative").exists();
        }
        return false;
    }

    private static boolean inBlackList() {
        String key = Build.MANUFACTURER.toLowerCase();
        boolean in = sBlackMap.containsKey(key) && sBlackMap.get(key).contains(Build.MODEL.toLowerCase());
        LogUtils.d(TAG, "inBlackList: " + in + ", [" + Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + "]");
        return in;
    }

    public static void toggleForceDisable(boolean disable) {
        LogUtils.d(TAG, "toggleForceDisable disable: " + disable);
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File file = new File(Environment.getExternalStorageDirectory(), "alipay/multimedia/.disableNative");
            if (disable) {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    LogUtils.w(TAG, "toggleForceDisable error, " + e);
                }
            } else if (file.exists()) {
                file.delete();
            }
        }
    }
}
