package com.autonavi.minimap.route.foot.net;

import android.content.Context;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.foot.RouteFootResultData;

public class FootRequestHelper$2 implements Callback<byte[]> {
    final /* synthetic */ Context a;
    final /* synthetic */ POI b;
    final /* synthetic */ POI c;
    final /* synthetic */ Callback d;

    public FootRequestHelper$2(Context context, POI poi, POI poi2, Callback callback) {
        this.a = context;
        this.b = poi;
        this.c = poi2;
        this.d = callback;
    }

    public final void callback(byte[] bArr) {
        RouteFootResultData routeFootResultData = new RouteFootResultData(this.a);
        routeFootResultData.setFromPOI(this.b);
        routeFootResultData.setToPOI(this.c);
        ecp ecp = new ecp(routeFootResultData);
        ecp.parser(bArr);
        if (this.d != null) {
            this.d.callback(ecp);
        }
    }

    public final void error(Throwable th, boolean z) {
        if (this.d != null) {
            this.d.error(th, z);
        }
    }
}
