package defpackage;

import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.train.controller.ExtTrainUIStatusController.RequestStatus;
import com.autonavi.minimap.route.train.inter.ITrainRouteResult;
import com.autonavi.minimap.route.train.page.ExtTrainPlanListPage;

/* renamed from: eai reason: default package */
/* compiled from: ExtTrainRouteResultCallback */
public final class eai implements axa {
    private ExtTrainPlanListPage a;
    private POI b;
    private POI c;
    private String d;

    public eai(ExtTrainPlanListPage extTrainPlanListPage, POI poi, POI poi2, String str) {
        this.a = extTrainPlanListPage;
        this.b = poi;
        this.c = poi2;
        this.d = str;
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        if (this.a.isAlive()) {
            this.a.d();
            this.a.c();
            ITrainRouteResult iTrainRouteResult = (ITrainRouteResult) iRouteResultData;
            if (iTrainRouteResult.getTrainPlanInfoResult().size() == 0) {
                this.a.a(RequestStatus.FAILED_NO_RESULT);
                return;
            }
            if (iRouteResultData.getFromPOI() == null) {
                iRouteResultData.setFromPOI(this.b);
            }
            if (iRouteResultData.getToPOI() == null) {
                iRouteResultData.setToPOI(this.c);
            }
            acg acg = (acg) a.a.a(acg.class);
            if (acg != null) {
                acg.a(iRouteResultData, RouteType.TRAIN);
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_start_city_key", this.b);
            pageBundle.putObject("bundle_end_city_key", this.c);
            pageBundle.putObject("bundle_selected_date_key", this.d);
            pageBundle.putObject("bundle_train_plan_key", iTrainRouteResult.getTrainPlanInfoResult());
            pageBundle.putObject("bundle_train_plan_service_switch", Boolean.valueOf(iTrainRouteResult.isNeedServiceSwitch()));
            pageBundle.putBoolean("bundle_high_train_flag", false);
            pageBundle.putBoolean("bundle_student_flag", false);
            pageBundle.putInt("bundle_is_from_which_page", 1);
            this.a.startPageForResult(ExtTrainPlanListPage.class, pageBundle, 1);
        }
    }

    public final void a(RouteType routeType, int i, String str) {
        if (this.a.isAlive()) {
            this.a.c();
            if (routeType == RouteType.TRAIN) {
                this.a.startPageForResult(ExtTrainPlanListPage.class, (PageBundle) null, 1);
                if (i / 10 == 4) {
                    int i2 = i % 10;
                    if (i2 == 1) {
                        this.a.a(RequestStatus.FAILED_NO_RESULT);
                    } else if (i2 == 2) {
                        this.a.a(RequestStatus.FAILED_NO_RESULT);
                    } else if (i2 == 3) {
                        this.a.a(RequestStatus.FAILED_SERVER_ERROR);
                    } else {
                        this.a.a(RequestStatus.FAILED_NO_RESULT);
                    }
                } else {
                    this.a.a(RequestStatus.FAILED_NET_ERROR);
                }
            }
        }
    }

    public final void a(Throwable th, boolean z) {
        if (this.a.isAlive()) {
            this.a.c();
            this.a.d();
            this.a.startPageForResult(ExtTrainPlanListPage.class, (PageBundle) null, 1);
            this.a.a(RequestStatus.FAILED_NET_ERROR);
        }
    }
}
