package com.autonavi.minimap.falcon.base;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback.WorkThread;
import defpackage.dkm;
import defpackage.dko;
import org.json.JSONObject;

public abstract class FalconAosHttpResponseCallBack<T extends dkm, C extends dko> implements AosResponseCallback<AosByteResponse> {
    protected C b;
    protected boolean c = true;

    public abstract T a();

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (this.b != null) {
            if (aosByteResponse == null || aosByteResponse.getResult() == null || ((byte[]) aosByteResponse.getResult()).length == 0) {
                a(new Exception("null == callback or data is empty"));
                return;
            }
            try {
                String str = new String((byte[]) aosByteResponse.getResult(), "utf-8");
                if (TextUtils.isEmpty(str)) {
                    this.b.a(new Exception("data is empty"));
                    return;
                }
                JSONObject jSONObject = new JSONObject(str);
                final dkm a = a();
                a.fromJson(jSONObject);
                if (this.c) {
                    aho.a(new Runnable() {
                        public final void run() {
                            FalconAosHttpResponseCallBack.this.b.a(a);
                        }
                    });
                } else {
                    this.b.a(a);
                }
            } catch (Exception e) {
                a(e);
            }
        }
    }

    public FalconAosHttpResponseCallBack(C c2) {
        this.b = c2;
    }

    public FalconAosHttpResponseCallBack(C c2, WorkThread workThread) {
        boolean z = true;
        this.c = workThread != WorkThread.UI ? false : z;
        this.b = c2;
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        a(aosResponseException);
    }

    private void a(final Exception exc) {
        if (this.c) {
            aho.a(new Runnable() {
                public final void run() {
                    FalconAosHttpResponseCallBack.this.b.a(exc);
                }
            });
        } else {
            this.b.a(exc);
        }
    }
}
