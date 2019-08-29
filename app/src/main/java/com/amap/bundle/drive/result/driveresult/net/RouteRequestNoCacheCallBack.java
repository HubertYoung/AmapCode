package com.amap.bundle.drive.result.driveresult.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import java.util.List;

public abstract class RouteRequestNoCacheCallBack<T> implements AosResponseCallback<AosByteResponse> {
    protected Callback<T> b;
    protected POI c;
    protected POI d;
    protected List<POI> e;
    protected String f;
    protected long g;

    public abstract T a(byte[] bArr);

    public abstract void a(T t);

    public abstract void a(Throwable th);

    public RouteRequestNoCacheCallBack(Callback<T> callback, POI poi, List<POI> list, POI poi2, String str) {
        this.b = callback;
        this.c = poi;
        this.e = list;
        this.d = poi2;
        this.f = str;
        this.g = 0;
    }

    protected RouteRequestNoCacheCallBack() {
    }

    /* renamed from: a */
    public void onSuccess(AosByteResponse aosByteResponse) {
        if (aosByteResponse == null) {
            aho.a(new Runnable() {
                public final void run() {
                    RouteRequestNoCacheCallBack.this.a((Throwable) new Exception("response is empty."));
                }
            });
            return;
        }
        final Object a = a((byte[]) aosByteResponse.getResult());
        aho.a(new Runnable() {
            public final void run() {
                RouteRequestNoCacheCallBack.this.a(a);
            }
        });
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                RouteRequestNoCacheCallBack.this.a((Throwable) aosResponseException);
            }
        });
    }
}
