package com.autonavi.minimap.route.bus.realtimebus;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;

public class RealTimeBusTask$1 extends FalconAosPrepareResponseCallback<ResultType> {
    final /* synthetic */ boolean a;
    final /* synthetic */ dxw b;

    public RealTimeBusTask$1(dxw dxw, boolean z) {
        this.b = dxw;
        this.a = z;
    }

    public final ResultType a(AosByteResponse aosByteResponse) {
        return this.b.a((byte[]) aosByteResponse.getResult());
    }

    public final void a(ResultType resulttype) {
        if (this.b.a != null) {
            this.b.a.callback(resulttype);
        }
        if (!this.b.d && this.b.c && this.b.b >= 0) {
            this.b.e.removeCallbacks(this.b.f);
            this.b.e.postDelayed(this.b.f, (long) this.b.b);
        }
        if (this.a) {
            ebj.b(1);
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a) {
            if (aosResponseException.isCallbackError) {
                ebj.b(2);
            } else {
                ebj.b(3);
            }
        }
        if (!this.b.d && this.b.c && this.b.b >= 0) {
            this.b.e.removeCallbacks(this.b.f);
            this.b.e.postDelayed(this.b.f, (long) this.b.b);
        }
        if (this.b.a != null) {
            this.b.a.error(aosResponseException, aosResponseException.isCallbackError);
        }
    }
}
