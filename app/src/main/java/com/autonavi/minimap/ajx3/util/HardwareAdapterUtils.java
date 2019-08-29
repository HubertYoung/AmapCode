package com.autonavi.minimap.ajx3.util;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.Ajx;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class HardwareAdapterUtils {
    public static final int ANDROID_VERSION_P = 28;
    private static final String BUILD_MODEL = Build.MODEL;
    private static final int BUILD_SDK_VERSION = VERSION.SDK_INT;
    private static final String CLOUD_CONFIG_KEY = "ajx_hardware_blacklist";
    private static final HashSet<String> LOCAL_BLACK_LIST;
    private static boolean sDisableHardware = (BUILD_SDK_VERSION == 28 && LOCAL_BLACK_LIST.contains(BUILD_MODEL));

    static {
        HashSet<String> hashSet = new HashSet<>(32);
        LOCAL_BLACK_LIST = hashSet;
        hashSet.add("NX563J");
        LOCAL_BLACK_LIST.add("NX616J");
        LOCAL_BLACK_LIST.add("NX619J");
        LOCAL_BLACK_LIST.add("NX609J");
        LOCAL_BLACK_LIST.add("NX629J");
        LOCAL_BLACK_LIST.add("NX595J");
        LOCAL_BLACK_LIST.add("NX531J");
        LOCAL_BLACK_LIST.add("NX569J");
        LOCAL_BLACK_LIST.add("NX569H");
        LOCAL_BLACK_LIST.add("NX606J");
        LOCAL_BLACK_LIST.add("NX549J");
        LOCAL_BLACK_LIST.add("NX513J");
        LOCAL_BLACK_LIST.add("NX510J");
        LOCAL_BLACK_LIST.add("NX511J");
        LOCAL_BLACK_LIST.add("NX541J");
        LOCAL_BLACK_LIST.add("NX529J");
        LOCAL_BLACK_LIST.add("NX589J");
    }

    public static synchronized boolean isHardwareAcceleratedForBorder() {
        boolean z;
        synchronized (HardwareAdapterUtils.class) {
            z = !sDisableHardware;
        }
        return z;
    }

    public static void updateHardwareAcceleratedForBorder() {
        if (!sDisableHardware) {
            try {
                String cloudConfig = Ajx.getInstance().getCloudConfig(CLOUD_CONFIG_KEY);
                if (!TextUtils.isEmpty(cloudConfig)) {
                    JSONArray optJSONArray = new JSONObject(cloudConfig).optJSONArray(String.valueOf(BUILD_SDK_VERSION));
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            if (BUILD_MODEL.equals(optJSONArray.get(i))) {
                                sDisableHardware = true;
                                return;
                            }
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
    }
}
