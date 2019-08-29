package com.autonavi.minimap.route.foot.net;

import com.autonavi.common.Callback;
import com.autonavi.minimap.route.export.model.IRouteResultData;

public class FootRequestHelper$1 implements Callback<ecp> {
    final /* synthetic */ axa a;
    final /* synthetic */ eag b;

    public FootRequestHelper$1(axa axa, eag eag) {
        this.a = axa;
        this.b = eag;
    }

    public final void callback(ecp ecp) {
        if (!(this.a == null || ecp == null)) {
            if (ecp.result && ecp.errorCode == 0) {
                this.a.a((IRouteResultData) ecp.a, this.b.a);
            } else if (ecp.result) {
                this.a.a(this.b.a, 3, ecp.a());
            } else {
                this.a.a(this.b.a, 2, ecp.a());
            }
        }
    }

    public final void error(Throwable th, boolean z) {
        if (this.a != null) {
            this.a.a(th, z);
        }
    }
}
