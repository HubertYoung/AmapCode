package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;

public class DeviceConfig {
    public static final String LEVEL_DEFAULT_VAL = "0";
    public static final String LEVEL_UID = "4";
    public static final String LEVEL_VER = "3";
    public static final String LEVEL_VER_MANUE = "2";
    public static final String LEVEL_VER_MANUE_MODEL = "1";
    private static final long UPDATE_INTERVEL = 43200000;
    private String configKey = "";
    public String content = "";
    public String defaultVal = "";
    private long lastUpdateTime = 0;
    public String level = "";

    public DeviceConfig(String key) {
        this.configKey = key;
    }

    public boolean compareKey(String key) {
        if (TextUtils.isEmpty(this.configKey) || TextUtils.isEmpty(key)) {
            return false;
        }
        return this.configKey.equals(key);
    }

    public boolean needUpdate() {
        return Math.abs(System.currentTimeMillis() - this.lastUpdateTime) > UPDATE_INTERVEL;
    }

    public void updateTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void setNeedUpdate() {
        this.lastUpdateTime = 0;
    }

    public String toString() {
        return "DeviceConfig{configKey=" + this.configKey + ", lastUpdateTime=" + this.lastUpdateTime + ", level=" + this.level + ", defaultVal=" + this.defaultVal + ", content=" + this.content + '}';
    }
}
