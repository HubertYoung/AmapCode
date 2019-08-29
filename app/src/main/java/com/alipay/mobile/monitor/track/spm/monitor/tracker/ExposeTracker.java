package com.alipay.mobile.monitor.track.spm.monitor.tracker;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.monitor.track.spm.SpmUtils;

public class ExposeTracker extends BaseTracker {
    public ExposeTracker(Builder behavorBuilder) {
        super(behavorBuilder);
    }

    public void commit() {
        LoggerFactory.getBehavorLogger().event(BehavorID.EXPOSURE, this.mBehavorBuilder.build());
        SpmUtils.printBehaviour(TAG, this.mBehavorBuilder, BehavorID.EXPOSURE);
    }
}
