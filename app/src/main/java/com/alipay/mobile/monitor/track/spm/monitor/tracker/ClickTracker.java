package com.alipay.mobile.monitor.track.spm.monitor.tracker;

import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.monitor.track.spm.SpmUtils;

public class ClickTracker extends BaseTracker {
    public ClickTracker(Builder behavorBuilder) {
        super(behavorBuilder);
    }

    public void commit() {
        this.mBehavorBuilder.click();
        SpmUtils.printBehaviour(TAG, this.mBehavorBuilder, "clicked");
    }
}
