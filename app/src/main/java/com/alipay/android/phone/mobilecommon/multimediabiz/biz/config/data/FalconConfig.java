package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DeviceConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class FalconConfig {
    private static final String TAG = "FalconConfig";
    private int encodeSwitch = 1;
    private int falconSwitch = 1;
    private long lastUpdateTime = 0;
    private SoftConfig softConfig = null;

    public static class SoftConfig {
        private int cpuLevel = 4;
        private String crf = "";
        private String preset = "";

        public String getPreset() {
            return this.preset;
        }

        public void setPreset(String preset2) {
            this.preset = preset2;
        }

        public String getCrf() {
            return this.crf;
        }

        public void setCrf(String crf2) {
            this.crf = crf2;
        }

        public int getCpuLevel() {
            return this.cpuLevel;
        }

        public void setCpuLevel(int cpuLevel2) {
            this.cpuLevel = cpuLevel2;
        }

        public String toString() {
            return "SoftConfig{cpuLevel=" + this.cpuLevel + ", crf='" + this.crf + '\'' + ", preset='" + this.preset + '\'' + '}';
        }
    }

    public SoftConfig getSoftConfig() {
        return this.softConfig == null ? new SoftConfig() : this.softConfig;
    }

    public void setSoftConfig(SoftConfig softConfig2) {
        this.softConfig = softConfig2;
    }

    public boolean isHardEncode() {
        return this.encodeSwitch == 1;
    }

    public void setEncodeSwitch(int encodeSwitch2) {
        this.encodeSwitch = encodeSwitch2;
    }

    public boolean isFalconSwitchOn() {
        return this.falconSwitch == 1;
    }

    public void setFalconSwitch(int falconSwitch2) {
        this.falconSwitch = falconSwitch2;
    }

    public void updateLastTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public boolean isNeedUpdate() {
        return Math.abs(System.currentTimeMillis() - this.lastUpdateTime) > TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL;
    }

    public static void parseFalconDeviceConfig(FalconConfig falconConfig, DeviceConfig config) {
        if (falconConfig != null && config != null && falconConfig.isNeedUpdate()) {
            falconConfig.updateLastTime();
            if (!TextUtils.isEmpty(config.content) && config.content.contains(MergeUtil.SEPARATOR_KV)) {
                String[] items = config.content.split("\\|");
                try {
                    if (items.length > 1) {
                        falconConfig.setFalconSwitch(Integer.parseInt(items[1]));
                    }
                    if (items.length > 2) {
                        falconConfig.setEncodeSwitch(Integer.parseInt(items[2]));
                    }
                    SoftConfig softConfig2 = null;
                    if (items.length > 3) {
                        softConfig2 = new SoftConfig();
                        softConfig2.setCrf(items[3]);
                    }
                    if (items.length > 4) {
                        softConfig2.setPreset(items[4]);
                    }
                    if (items.length > 5) {
                        softConfig2.setCpuLevel(Integer.parseInt(items[5]));
                    }
                    if (softConfig2 != null) {
                        falconConfig.setSoftConfig(softConfig2);
                    }
                } catch (Exception e) {
                    Logger.D(TAG, "parseFalconDeviceConfig exp", new Object[0]);
                }
            }
        }
    }

    public String toString() {
        return "FalconConfig{falconSwitch=" + this.falconSwitch + ", encodeSwitch=" + this.encodeSwitch + ", softConfig=" + this.softConfig + '}';
    }
}
