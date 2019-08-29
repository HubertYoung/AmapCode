package com.ant.phone.xmedia.api.utils;

import android.os.Build;
import android.os.Environment;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NativeSupportHelper {
    private static Map<String, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("samsung", "GT-I9103,GT-P7310,GT-P7300,GT-P7500,GT-P7510,GT-P5210,GT-P5200".toLowerCase());
        a.put("hisense", "HS-T96".toLowerCase());
        a.put(DeviceProperty.ALIAS_ZTE, "ZTE U930,ZTE U880F1,ZTE U970".toLowerCase());
        a.put(LeakCanaryInternals.MOTOROLA, "Xoom,Xoom Wifi,MB860,MB855".toLowerCase());
        a.put("asus", "Transformer TF101".toLowerCase());
        a.put("lge", "LG-P990".toLowerCase());
        a.put(DeviceProperty.ALIAS_HUAWEI, "HUAWEI P6-T00".toLowerCase());
    }

    public static boolean a() {
        try {
            return !b() && !c();
        } catch (Throwable t) {
            Log.e("NativeSupportHelper", "isSupportNativeProcess error", t);
            return true;
        }
    }

    private static boolean b() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return new File(Environment.getExternalStorageDirectory(), "alipay/multimedia/.disableNative").exists();
        }
        return false;
    }

    private static boolean c() {
        String key = Build.MANUFACTURER.toLowerCase();
        boolean in = a.containsKey(key) && a.get(key).contains(Build.MODEL.toLowerCase());
        Log.d("NativeSupportHelper", "inBlackList: " + in + ", [" + Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + "]");
        return in;
    }
}
