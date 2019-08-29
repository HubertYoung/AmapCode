package com.autonavi.minimap.route.ugc.net.callback;

import android.content.Context;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;

public class FootNaviReviewRequestCallback extends FalconAosPrepareResponseCallback<ekg> {
    private Context a;
    private eka b;

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        ekg ekg = (ekg) obj;
        if (ekg.errorCode != 1) {
            new StringBuilder("result.errorCode = ").append(ekg.errorCode);
        } else if (this.b == null) {
            eke.a(this.a).a((String) "ugc_cache_foot");
        }
    }

    public FootNaviReviewRequestCallback(Context context, eka eka) {
        this.a = context;
        this.b = eka;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.b != null) {
            eke a2 = eke.a(this.a);
            eka eka = this.b;
            if (eka != null) {
                ekd.a((String) "ugc_cache_foot", new ekd(a2.a).a(ekd.a((String) "ugc_cache_foot"), eka.a()));
            }
        }
    }

    private static ekg b(AosByteResponse aosByteResponse) {
        ekg ekg = new ekg();
        try {
            ekg.parser((byte[]) aosByteResponse.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ekg;
    }
}
