package com.autonavi.minimap.route.bus.localbus.net;

import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.uc.webview.export.internal.SDKFactory;

public class BusRequestHelper$1 implements Callback<dvz> {
    final /* synthetic */ axa a;
    final /* synthetic */ eag b;

    public BusRequestHelper$1(axa axa, eag eag) {
        this.a = axa;
        this.b = eag;
    }

    public final void callback(dvz dvz) {
        boolean z;
        String str;
        if (this.a != null) {
            if (dvz.a == null) {
                z = false;
            } else {
                z = dvz.a.hasData();
            }
            if (z) {
                this.a.a((IRouteResultData) dvz.a, this.b.a);
                return;
            }
            axa axa = this.a;
            RouteType routeType = this.b.a;
            int i = dvz.errorCode;
            if (TextUtils.isEmpty(dvz.errorMessage)) {
                eko.a((int) SDKFactory.getCoreType);
                str = AMapAppGlobal.getApplication().getString(R.string.route_not_query_result);
            } else {
                str = dvz.errorMessage;
            }
            axa.a(routeType, i, str);
        }
    }

    public final void error(Throwable th, boolean z) {
        if (this.a != null) {
            this.a.a(th, z);
        }
    }
}
