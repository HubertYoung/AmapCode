package com.autonavi.minimap.route.coach.net;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.model.CoachRouteResult;
import com.autonavi.minimap.route.export.model.IRouteResultData;

public class CoachRequestHelper$1 implements Callback<dzo> {
    final /* synthetic */ axa a;

    public CoachRequestHelper$1(axa axa) {
        this.a = axa;
    }

    public final void callback(dzo dzo) {
        if (this.a != null) {
            if (dzo.result) {
                if (dzo.a == 0) {
                    this.a.a((IRouteResultData) new CoachRouteResult(dzo), RouteType.COACH);
                    return;
                }
                this.a.a(RouteType.COACH, dzo.a, dzo.errorMessage);
            } else if (dzo.errorCode == 25) {
                this.a.a(RouteType.COACH, 63, "");
            } else {
                Exception exc = new Exception("parse failed");
                axa axa = this.a;
                RouteType routeType = RouteType.COACH;
                axa.a((Throwable) exc, false);
            }
        }
    }

    public final void error(Throwable th, boolean z) {
        if (this.a != null) {
            if (z) {
                this.a.a(RouteType.COACH, -1, AMapAppGlobal.getApplication().getString(R.string.train_plan_network_status_error_callback));
                return;
            }
            axa axa = this.a;
            RouteType routeType = RouteType.COACH;
            axa.a(th, false);
        }
    }
}
