package com.alipay.android.phone.mobilecommon.multimediabiz.biz.common;

import android.os.Looper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.AccumulationExecutor.OnExecuteListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class AccumulationSetExecutor<T> extends AccumulationExecutor<Set, T> {
    public AccumulationSetExecutor(int thresholdCount, long thresholdTime, OnExecuteListener<T> listener, Looper looper) {
        super(thresholdCount, thresholdTime, listener, looper);
    }

    public AccumulationSetExecutor(int thresholdCount, long thresholdTime, OnExecuteListener<T> listener) {
        super(thresholdCount, thresholdTime, listener);
    }

    /* access modifiers changed from: protected */
    public Set createCacheData() {
        return new CopyOnWriteArraySet();
    }
}
