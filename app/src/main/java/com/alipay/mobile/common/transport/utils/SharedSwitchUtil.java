package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import java.util.Observer;

public class SharedSwitchUtil {
    public static final String KEY_NET_SWITCH_ONE = "netsdk_normal_switch";
    public static final String KEY_NET_SWITCH_TWO = "android_network_core";
    public static final String SHARED_FILE = "sdkSharedSwitch.xml";
    public static final String SHARED_FILE_NAME = "sdkSharedSwitch";

    public static final void init() {
        NwSharedSwitchUtil.regSwitchChangedListener();
    }

    public static synchronized String getSharedSwitch(Context context, String key) {
        String sharedSwitch;
        synchronized (SharedSwitchUtil.class) {
            sharedSwitch = NwSharedSwitchUtil.getSharedSwitch(context, key);
        }
        return sharedSwitch;
    }

    public static void refreshSharedSwitch(Context context, String sharedPrefName, String key, String value) {
        NwSharedSwitchUtil.refreshSharedSwitch(context, sharedPrefName, key, value);
    }

    public static synchronized String getSharedSwitch(Context context, String sharedPrefName, String key) {
        String sharedSwitch;
        synchronized (SharedSwitchUtil.class) {
            sharedSwitch = NwSharedSwitchUtil.getSharedSwitch(context, sharedPrefName, key);
        }
        return sharedSwitch;
    }

    public static final void addSwitchChangedListener(Observer observer) {
        NwSharedSwitchUtil.addSwitchChangedListener(observer);
    }

    public static void notifySwitchUpdate() {
        NwSharedSwitchUtil.notifySwitchUpdate();
    }

    public static synchronized void removeSwitch(Context context) {
        synchronized (SharedSwitchUtil.class) {
            NwSharedSwitchUtil.removeSwitch(context);
        }
    }
}
