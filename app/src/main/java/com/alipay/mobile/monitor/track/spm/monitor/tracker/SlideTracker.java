package com.alipay.mobile.monitor.track.spm.monitor.tracker;

import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.monitor.track.spm.SpmUtils;

public class SlideTracker extends BaseTracker {
    public SlideTracker(Builder behavorBuilder) {
        super(behavorBuilder);
    }

    public void commit() {
        this.mBehavorBuilder.slide();
        SpmUtils.printBehaviour("SlideTracker", this.mBehavorBuilder, BehavorID.SLIDE);
    }
}
