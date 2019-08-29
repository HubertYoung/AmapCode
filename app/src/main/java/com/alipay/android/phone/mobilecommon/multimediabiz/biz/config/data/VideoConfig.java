package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DeviceConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class VideoConfig {
    public static int ENCODE_HARD = 1;
    public static int ENCODE_SOFT = 0;
    private static final String TAG = "VideoConfig";
    public String crf = null;
    public int encodeType = ENCODE_SOFT;
    private long lastUpdateTime = 0;
    public int level = 4;
    public String preset = null;

    public VideoConfig() {
        if (VERSION.SDK_INT >= 18) {
            this.encodeType = ENCODE_HARD;
        }
    }

    public void updateLastTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public boolean isNeedUpdate() {
        return Math.abs(System.currentTimeMillis() - this.lastUpdateTime) > TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL;
    }

    public boolean isHardEncode() {
        return this.encodeType == ENCODE_HARD;
    }

    public static void parseVideoDeviceConfig(VideoConfig videoConfig, DeviceConfig config) {
        if (videoConfig != null && config != null && videoConfig.isNeedUpdate()) {
            videoConfig.updateLastTime();
            if (!TextUtils.isEmpty(config.content) && config.content.contains(MergeUtil.SEPARATOR_KV)) {
                String[] items = config.content.split("\\|");
                try {
                    if (items.length > 1) {
                        videoConfig.encodeType = Integer.parseInt(items[1]);
                    }
                    if (items.length > 2) {
                        videoConfig.crf = items[2];
                    }
                    if (items.length > 3) {
                        videoConfig.preset = items[3];
                    }
                    if (items.length > 4) {
                        videoConfig.level = Integer.parseInt(items[4]);
                    }
                } catch (Exception e) {
                    Logger.D(TAG, "parseFalconDeviceConfig exp", new Object[0]);
                }
            }
        }
    }

    public String toString() {
        return "VideoConfig=[level=" + this.level + ";crf=" + this.crf + ";preset=" + this.preset + ";encodeType=" + this.encodeType + "]";
    }
}
