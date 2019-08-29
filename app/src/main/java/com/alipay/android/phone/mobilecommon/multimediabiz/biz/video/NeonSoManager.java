package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.os.Build;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.SoItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.concurrent.atomic.AtomicBoolean;

public class NeonSoManager {
    private static NeonSoManager a = new NeonSoManager();
    private AtomicBoolean b = null;

    public static NeonSoManager get() {
        return a;
    }

    private NeonSoManager() {
    }

    public synchronized boolean isNeedUpgradeFFmpegSo() {
        boolean z;
        try {
            if (this.b == null) {
                SoItem ffmpegItem = ConfigManager.getInstance().soConfigs().ffmpegItem;
                if (ffmpegItem == null) {
                    z = false;
                } else {
                    this.b = new AtomicBoolean(CompareUtils.containsIgnoreCase((Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL).toLowerCase(), ffmpegItem.models));
                }
            }
            z = this.b.get();
        }
        return z;
    }
}
