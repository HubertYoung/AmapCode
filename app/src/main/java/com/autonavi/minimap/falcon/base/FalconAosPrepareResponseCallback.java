package com.autonavi.minimap.falcon.base;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;

public abstract class FalconAosPrepareResponseCallback<T> implements AosResponseCallback<AosByteResponse> {
    public abstract T a(AosByteResponse aosByteResponse);

    public abstract void a(AosRequest aosRequest, AosResponseException aosResponseException);

    public abstract void a(T t);

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        final Object a = a((AosByteResponse) aosResponse);
        aho.a(new Runnable() {
            public final void run() {
                FalconAosPrepareResponseCallback.this.a(a);
            }
        });
    }

    public final void onFailure(final AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                FalconAosPrepareResponseCallback.this.a(aosRequest, aosResponseException);
            }
        });
    }
}
