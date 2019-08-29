package com.alipay.mobile.common.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class SharedSwitchUtil {
    public static final String KEY_NET_SWITCH_ONE = "netsdk_normal_switch";
    public static final String KEY_NET_SWITCH_TWO = "android_network_core";
    public static final String MDAP_UPLOAD_WHITE_CONFIG = "mdap_upload_white_config";
    public static final String POSITIVE_LOG_WHITE_CONFIG = "positive_log_white_config";
    public static final String SHARED_FILE = "sdkSharedSwitch.xml";
    public static final String SHARED_FILE_NAME = "sdkSharedSwitch";
    public static final String THREAD_POOL_CONFIG = "thread_pool_config";
    public static final String UNI_DOMAIN_CONFIG = "spdy_uniformorigin_config";
    private static Boolean a = null;
    public static SwitchChangedObserble switchChangedObserble;
    public static List<String> switchList = new ArrayList<String>() {
        {
            add("netsdk_normal_switch");
            add("android_network_core");
            add(SharedSwitchUtil.UNI_DOMAIN_CONFIG);
            add(SharedSwitchUtil.THREAD_POOL_CONFIG);
            add(SharedSwitchUtil.MDAP_UPLOAD_WHITE_CONFIG);
            add(SharedSwitchUtil.POSITIVE_LOG_WHITE_CONFIG);
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    };

    class SwitchChangedObserble extends Observable {
        SwitchChangedObserble() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void notifyObservers(Object data) {
            setChanged();
            super.notifyObservers(data);
        }
    }

    public SharedSwitchUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static synchronized String getSharedSwitch(Context context, String key) {
        String string;
        synchronized (SharedSwitchUtil.class) {
            string = context.getSharedPreferences("sdkSharedSwitch", 4).getString(key, null);
        }
        return string;
    }

    public static synchronized void refreshSharedSwitch(Context context, Map<String, String> switchMap) {
        synchronized (SharedSwitchUtil.class) {
            try {
                Editor ed = context.getSharedPreferences("sdkSharedSwitch", 4).edit();
                boolean hasSwitch = false;
                for (String key : switchList) {
                    String value = switchMap.get(key);
                    if (value == null || value.trim().length() <= 0) {
                        LogCatUtil.info("SharedSwitchUtil", "key=[" + key + "], value is null ");
                    } else {
                        ed.putString(key, value);
                        hasSwitch = true;
                        StringBuilder stringBuilder = new StringBuilder("key=[" + key + "], ");
                        if (isDebugger(context)) {
                            stringBuilder.append("value=[" + value + "]");
                        } else {
                            stringBuilder.append("value is not null");
                        }
                        LogCatUtil.info("SharedSwitchUtil", stringBuilder.toString());
                    }
                }
                if (hasSwitch) {
                    ed.commit();
                    a().notifyObservers(switchMap);
                }
            } catch (Throwable ex) {
                LogCatUtil.error("sdkSharedSwitch", "SDK层开关配置处理", ex);
            }
        }
        return;
    }

    public static void refreshSharedSwitch(Context context, String key, String value) {
        try {
            Editor ed = context.getSharedPreferences("sdkSharedSwitch", 4).edit();
            ed.putString(key, value);
            ed.commit();
        } catch (Throwable ex) {
            LogCatUtil.error("sdkSharedSwitch", "SDK层开关配置处理", ex);
        }
    }

    public static void refreshSharedSwitch(Context context, String sharedPrefName, String key, String value) {
        try {
            Editor ed = context.getSharedPreferences(sharedPrefName, 4).edit();
            ed.putString(key, value);
            if (ed.commit()) {
                LogCatUtil.info("SharedSwitchUtil", "refreshSharedSwitch commit success!");
            } else {
                LogCatUtil.info("SharedSwitchUtil", "refreshSharedSwitch commit fail!");
            }
        } catch (Throwable ex) {
            LogCatUtil.error("SharedSwitchUtil", "refreshSharedSwitch, sharedPrefName=[" + sharedPrefName + "], key=[" + key + "], value=[" + value + "]", ex);
        }
    }

    public static synchronized String getSharedSwitch(Context context, String sharedPrefName, String key) {
        String string;
        synchronized (SharedSwitchUtil.class) {
            string = context.getSharedPreferences(sharedPrefName, 4).getString(key, null);
        }
        return string;
    }

    public static final void addSwitchChangedListener(Observer observer) {
        a().addObserver(observer);
    }

    private static SwitchChangedObserble a() {
        if (switchChangedObserble == null) {
            switchChangedObserble = new SwitchChangedObserble();
        }
        return switchChangedObserble;
    }

    public static final boolean isDebugger(Context context) {
        boolean z;
        if (a != null) {
            return a.booleanValue();
        }
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0) {
                z = true;
            } else {
                z = false;
            }
            Boolean valueOf = Boolean.valueOf(z);
            a = valueOf;
            return valueOf.booleanValue();
        } catch (Throwable e) {
            LogCatUtil.error((String) "MiscUtils", e);
            return false;
        }
    }

    public static void notifySwitchUpdate() {
        a().notifyObservers();
    }
}
