package com.autonavi.minimap.route.bus.realtimebus.net;

import com.autonavi.common.Callback;

public class MultiThreadCallbackWrapper<ResultType> implements Callback<ResultType> {
    /* access modifiers changed from: private */
    public IResultProcessor<ResultType> mOriginalCallback;
    /* access modifiers changed from: private */
    public ResultType mResult;

    public interface IResultProcessor<ResultType> extends Callback<ResultType> {
        void onSafeMainHandler(ResultType resulttype);

        void onSafeSubHandler(ResultType resulttype);
    }

    public void callback(ResultType resulttype) {
    }

    public void error(Throwable th, boolean z) {
    }

    public void callback(ResultType resulttype, IResultProcessor<ResultType> iResultProcessor) {
        this.mResult = resulttype;
        this.mOriginalCallback = iResultProcessor;
        runOnSubThread();
    }

    private void runOnSubThread() {
        ebr.a(false).post(new Runnable() {
            public final void run() {
                try {
                    MultiThreadCallbackWrapper.this.mOriginalCallback.onSafeSubHandler(MultiThreadCallbackWrapper.this.mResult);
                    MultiThreadCallbackWrapper.this.runOnMainThread(null);
                } catch (Exception e) {
                    MultiThreadCallbackWrapper.this.runOnMainThread(e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void runOnMainThread(final Exception exc) {
        ebr.a(true).post(new Runnable() {
            public final void run() {
                if (exc == null) {
                    MultiThreadCallbackWrapper.this.mOriginalCallback.onSafeMainHandler(MultiThreadCallbackWrapper.this.mResult);
                } else {
                    MultiThreadCallbackWrapper.this.mOriginalCallback.error(exc, true);
                }
            }
        });
    }
}
