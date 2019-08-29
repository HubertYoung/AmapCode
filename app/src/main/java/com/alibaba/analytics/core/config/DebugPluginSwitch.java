package com.alibaba.analytics.core.config;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.SystemConfigMgr.IKVChangeListener;

public class DebugPluginSwitch implements IKVChangeListener {
    public static final String KEY = "sw_plugin";

    public void onChange(String str, String str2) {
        if (KEY.equalsIgnoreCase(str)) {
            boolean z = false;
            try {
                z = Boolean.parseBoolean(str2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z) {
                Variables.getInstance().turnOffDebugPlugin();
            }
        }
    }
}
