package com.autonavi.minimap.drive.restrictedarea.module;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import defpackage.dht;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public abstract class RestrictedCityListCallback<T extends dht> implements AosResponseCallback<AosByteResponse> {
    protected Callback<T> mCallback;
    protected T mResponser = newInstance();

    public void finish() {
    }

    public abstract T newInstance();

    public RestrictedCityListCallback(Callback<T> callback) {
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
                            RestrictedCityListCallback.this.mCallback.callback(RestrictedCityListCallback.this.mResponser);
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
                    RestrictedCityListCallback.this.mCallback.error(aosResponseException, false);
                }
            });
        }
        finishOnUi();
    }

    public void prepare(byte[] bArr) {
        if (this.mResponser != null) {
            T t = this.mResponser;
            if (bArr == null) {
                t.c = -1;
                return;
            }
            try {
                t.h = new JSONObject(new String(bArr, "UTF-8"));
                t.e = t.h.getString("version");
                t.g = t.h.getBoolean("result");
                t.c = t.h.getInt("code");
                t.f = t.h.getLong("timestamp");
                t.d = t.h.optString("message");
            } catch (Exception unused) {
                t.g = false;
                t.c = -2;
                t.d = dht.b;
            }
        }
    }

    public void finishOnUi() {
        aho.a(new Runnable() {
            public final void run() {
                RestrictedCityListCallback.this.finish();
            }
        });
    }
}
