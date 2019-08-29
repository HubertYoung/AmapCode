package com.autonavi.minimap.route.train.net;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.train.model.RouteTrainPlanResult;

public class TrainRequestHelper$1 implements Callback<ejh> {
    final /* synthetic */ axa a;

    public TrainRequestHelper$1(axa axa) {
        this.a = axa;
    }

    public final void callback(ejh ejh) {
        if (this.a != null) {
            if (ejh.result) {
                if (ejh.f == 0) {
                    this.a.a((IRouteResultData) new RouteTrainPlanResult(ejh), RouteType.TRAIN);
                    return;
                }
                this.a.a(RouteType.TRAIN, ejh.f, ejh.errorMessage);
            } else if (ejh.errorCode == 25) {
                this.a.a(RouteType.TRAIN, 43, "");
            } else {
                Exception exc = new Exception("parse failed");
                axa axa = this.a;
                RouteType routeType = RouteType.TRAIN;
                axa.a((Throwable) exc, false);
            }
        }
    }

    public final void error(Throwable th, boolean z) {
        if (this.a != null) {
            if (z) {
                this.a.a(RouteType.TRAIN, -1, AMapAppGlobal.getApplication().getString(R.string.train_plan_network_status_error_callback));
                return;
            }
            axa axa = this.a;
            RouteType routeType = RouteType.TRAIN;
            axa.a(th, false);
        }
    }
}
