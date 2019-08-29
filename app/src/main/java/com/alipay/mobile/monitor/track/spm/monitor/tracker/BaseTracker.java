package com.alipay.mobile.monitor.track.spm.monitor.tracker;

import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;

public abstract class BaseTracker implements Comparable<BaseTracker> {
    protected static String TAG = BaseTracker.class.getSimpleName();
    protected Builder mBehavorBuilder;

    public abstract void commit();

    public BaseTracker(Builder behavorBuilder) {
        this.mBehavorBuilder = behavorBuilder;
    }

    public Builder getBehavorBuilder() {
        return this.mBehavorBuilder;
    }

    public int compareTo(BaseTracker another) {
        return 0;
    }
}
