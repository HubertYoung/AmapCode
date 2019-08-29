package com.autonavi.minimap.life.common.net.opt;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import defpackage.dog;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public abstract class LifeNetRequestCallback<T extends dog> implements AosResponseCallback<AosByteResponse> {
    protected Callback<T> mCallback;
    protected T mResponser = newInstance();

    public void finish() {
    }

    public abstract T newInstance();

    public LifeNetRequestCallback(Callback<T> callback) {
        this.mCallback = callback;
    }

    public void onSuccess(AosByteResponse aosByteResponse) {
        if (aosByteResponse != null) {
            byte[] bArr = (byte[]) aosByteResponse.getResult();
            if (bArr != null) {
                prepare(bArr);
                if (this.mCallback != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            LifeNetRequestCallback.this.mCallback.callback(LifeNetRequestCallback.this.mResponser);
                        }
                    });
                }
            }
        }
        finishOnUi();
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        if (this.mCallback != null) {
            aho.a(new Runnable() {
                public final void run() {
                    LifeNetRequestCallback.this.mCallback.error(aosResponseException, false);
                }
            });
        }
        finishOnUi();
    }

    public void prepare(byte[] bArr) {
        if (this.mResponser != null) {
            this.mResponser.a(bArr);
        }
    }

    public void finishOnUi() {
        aho.a(new Runnable() {
            public final void run() {
                LifeNetRequestCallback.this.finish();
            }
        });
    }
}
