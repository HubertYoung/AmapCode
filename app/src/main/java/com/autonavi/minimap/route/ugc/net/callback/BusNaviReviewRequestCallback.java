package com.autonavi.minimap.route.ugc.net.callback;

import android.content.Context;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;

public class BusNaviReviewRequestCallback implements AosResponseCallback<AosByteResponse> {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public ejz b;

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        final ekf ekf = new ekf();
        try {
            ekf.parser((byte[]) aosByteResponse.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        aho.a(new Runnable() {
            public final void run() {
                if (ekf.errorCode != 1) {
                    new StringBuilder("result.errorCode = ").append(ekf.errorCode);
                } else if (BusNaviReviewRequestCallback.this.b == null) {
                    eke.a(BusNaviReviewRequestCallback.this.a).a((String) "ugc_cache_bus");
                }
            }
        });
    }

    public BusNaviReviewRequestCallback(Context context, ejz ejz) {
        this.a = context;
        this.b = ejz;
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                if (BusNaviReviewRequestCallback.this.b != null) {
                    eke a2 = eke.a(BusNaviReviewRequestCallback.this.a);
                    ejz a3 = BusNaviReviewRequestCallback.this.b;
                    if (a3 != null) {
                        ekd.a((String) "ugc_cache_bus", new ekd(a2.a).a(ekd.a((String) "ugc_cache_bus"), a3.a()));
                    }
                }
            }
        });
    }
}
