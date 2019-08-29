package com.alipay.mobile.monitor.track.spm.monitor.tracker;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.monitor.track.spm.SpmUtils;

public class MergeTracker extends BaseTracker {
    private String a;

    public MergeTracker(String behavorId, Builder behavorBuilder) {
        super(behavorBuilder);
        this.a = behavorId;
    }

    public void commit() {
        if (!TextUtils.isEmpty(this.a) && this.mBehavorBuilder != null) {
            LoggerFactory.getBehavorLogger().event(this.a, this.mBehavorBuilder.build());
            SpmUtils.printBehaviour(TAG, this.mBehavorBuilder, this.a);
        }
    }

    public String getBehavorId() {
        return this.a;
    }
}
