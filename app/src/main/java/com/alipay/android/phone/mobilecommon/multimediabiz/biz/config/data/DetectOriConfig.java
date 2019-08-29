package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.BaseConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DeviceConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class DetectOriConfig extends BaseConfig {
    private static final String TAG = "DetectOriConfig";
    private int inPadBlackList = 0;

    public void parseDetectOriConfig(DeviceConfig config) {
        if (config != null && needUpdate()) {
            updateTime();
            if (!TextUtils.isEmpty(config.content) && config.content.contains(MergeUtil.SEPARATOR_KV)) {
                String[] items = config.content.split("\\|");
                try {
                    if (items.length > 1) {
                        this.inPadBlackList = Integer.parseInt(items[1]);
                    }
                } catch (Exception e) {
                    Logger.D(TAG, "parseDetectOriConfig exp", new Object[0]);
                }
            }
        }
    }

    public boolean isInPadBlackList() {
        return this.inPadBlackList == 1;
    }

    public String toString() {
        return "DetectOriConfig=[inPadBlackList=" + this.inPadBlackList + "]";
    }
}
