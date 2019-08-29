package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback.b;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.train.controller.TrainUIStatusController.RequestStatus;
import com.autonavi.minimap.route.train.inter.ITrainRouteResult;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;

/* renamed from: eaj reason: default package */
/* compiled from: TrainRouteResultCallback */
public final class eaj implements axa, b {
    private TrainPlanListPage a;
    private ejm b;
    private IRouteUI c;
    private String d;
    private final RouteType e = RouteType.TRAIN;

    public final void onCancelled() {
    }

    public eaj(TrainPlanListPage trainPlanListPage, IRouteUI iRouteUI, String str, ejm ejm) {
        this.a = trainPlanListPage;
        this.c = iRouteUI;
        this.d = str;
        this.b = ejm;
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        this.a.d();
        this.a.n = true;
        if (!eal.a(this.c, this.e)) {
            this.a.m = true;
        } else if (!this.a.isAlive()) {
            this.a.m = true;
        } else {
            this.a.e = false;
            this.a.a();
            ITrainRouteResult iTrainRouteResult = (ITrainRouteResult) iRouteResultData;
            if (iTrainRouteResult.getTrainPlanInfoResult().size() != 0 || !this.a.isAlive()) {
                this.a.o = true;
                POI d2 = this.c.d();
                POI f = this.c.f();
                if (iRouteResultData.getFromPOI() == null) {
                    iRouteResultData.setFromPOI(d2);
                }
                if (iRouteResultData.getToPOI() == null) {
                    iRouteResultData.setToPOI(f);
                }
                acg acg = (acg) a.a.a(acg.class);
                if (acg != null) {
                    POI fromPOI = iRouteResultData.getFromPOI();
                    POI toPOI = iRouteResultData.getToPOI();
                    if (fromPOI != null) {
                        fromPOI = fromPOI.clone();
                        fromPOI.setName(this.b.b());
                    }
                    if (toPOI != null) {
                        toPOI = toPOI.clone();
                        toPOI.setName(this.b.c());
                    }
                    if (!(fromPOI == null || toPOI == null || TextUtils.equals(fromPOI.getName(), toPOI.getName()))) {
                        acg.a(fromPOI, toPOI, RouteType.TRAIN);
                    }
                }
                PageBundle pageBundle = new PageBundle();
                if (this.c != null) {
                    pageBundle.putObject("bundle_start_city_key", this.c.d());
                    pageBundle.putObject("bundle_end_city_key", this.c.f());
                    pageBundle.putObject("bundle_selected_date_key", this.d);
                    pageBundle.putObject("bundle_train_plan_key", iTrainRouteResult.getTrainPlanInfoResult());
                    pageBundle.putObject("bundle_train_plan_service_switch", Boolean.valueOf(iTrainRouteResult.isNeedServiceSwitch()));
                    pageBundle.putBoolean("bundle_high_train_flag", false);
                    pageBundle.putBoolean("bundle_student_flag", false);
                    pageBundle.putInt("bundle_is_from_which_page", 1);
                    this.c.a(TrainPlanListPage.class, pageBundle);
                }
                this.a.b(true);
                return;
            }
            this.a.a(RequestStatus.FAILED_NO_RESULT);
            this.a.b(false);
        }
    }

    public final void a(RouteType routeType, int i, String str) {
        this.a.n = true;
        this.a.d();
        if (!eal.a(this.c, this.e)) {
            this.a.m = true;
        } else if (!this.a.isAlive()) {
            this.a.m = true;
        } else {
            this.a.e = false;
            this.a.a();
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
            this.a.b(false);
        }
    }

    public final void a(Throwable th, boolean z) {
        this.a.n = true;
        this.a.d();
        if (!eal.a(this.c, this.e)) {
            this.a.m = true;
        } else if (!this.a.isAlive()) {
            this.a.m = true;
        } else {
            this.a.e = false;
            this.a.a();
            this.a.a(RequestStatus.FAILED_NET_ERROR);
            this.a.b(false);
        }
    }
}
