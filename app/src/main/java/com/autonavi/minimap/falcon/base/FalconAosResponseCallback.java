package com.autonavi.minimap.falcon.base;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;

public class FalconAosResponseCallback implements AosResponseCallback<AosByteResponse> {
    protected final WorkThread a;
    protected final AosResponseCallback b;

    public enum WorkThread {
        WORK,
        UI
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        final AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (this.a == WorkThread.UI || (this.b instanceof AosResponseCallbackOnUi)) {
            aho.a(new Runnable() {
                public final void run() {
                    FalconAosResponseCallback.this.b.onSuccess(aosByteResponse);
                }
            });
        } else {
            this.b.onSuccess(aosByteResponse);
        }
    }

    public FalconAosResponseCallback(AosResponseCallback aosResponseCallback) {
        this(WorkThread.WORK, aosResponseCallback);
    }

    public FalconAosResponseCallback(WorkThread workThread, AosResponseCallback aosResponseCallback) {
        this.a = workThread;
        this.b = aosResponseCallback;
    }

    public void onFailure(final AosRequest aosRequest, final AosResponseException aosResponseException) {
        if (this.a == WorkThread.UI || (this.b instanceof AosResponseCallbackOnUi)) {
            aho.a(new Runnable() {
                public final void run() {
                    FalconAosResponseCallback.this.b.onFailure(aosRequest, aosResponseException);
                }
            });
        } else {
            this.b.onFailure(aosRequest, aosResponseException);
        }
    }
}
