package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.route.coach.controller.CoachUIStatusController.ResultStatus;
import com.autonavi.minimap.route.coach.inter.ICoachRouteResult;
import com.autonavi.minimap.route.coach.model.CoachPlanData;
import com.autonavi.minimap.route.coach.page.CoachResultListPage;
import com.autonavi.minimap.route.export.model.IRouteResultData;

/* renamed from: dzm reason: default package */
/* compiled from: CoachRouteResultCallback */
public final class dzm implements axa {
    private final RouteType a = RouteType.COACH;
    private CoachResultListPage b;
    private IRouteUI c;
    private dzr d;

    public dzm(CoachResultListPage coachResultListPage, dzr dzr) {
        this.b = coachResultListPage;
        this.d = dzr;
        this.c = dzr.a;
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        this.b.o = true;
        this.b.p = true;
        if (!eal.a(this.c, this.a)) {
            this.b.n = true;
        } else if (!this.b.isAlive()) {
            this.b.n = true;
        } else {
            this.b.j = false;
            this.b.b();
            if (iRouteResultData instanceof ICoachRouteResult) {
                this.b.a(ResultStatus.SUCCESS);
                this.b.a(true);
                CoachResultListPage coachResultListPage = this.b;
                ICoachRouteResult iCoachRouteResult = (ICoachRouteResult) iRouteResultData;
                coachResultListPage.h = iCoachRouteResult;
                if (iCoachRouteResult != null) {
                    CoachResultListPage.a(iCoachRouteResult);
                    if (coachResultListPage.h.isParseOK()) {
                        coachResultListPage.i = iCoachRouteResult.getCoachPlanResult();
                        CoachPlanData coachPlanData = coachResultListPage.i;
                        if (coachResultListPage.g != null) {
                            coachResultListPage.g.setData(coachPlanData);
                        }
                        if (coachPlanData != null) {
                            coachResultListPage.a(coachPlanData);
                            coachResultListPage.f.setStationList(CoachResultListPage.a(coachPlanData.depNameList), CoachResultListPage.a(coachPlanData.arrNameList));
                        }
                        coachResultListPage.d.setSelection(0);
                    }
                }
                dzj dzj = this.d.e;
                if (dzj != null) {
                    dzj.c = true;
                    dzj.a();
                }
            }
        }
    }

    public final void a(RouteType routeType, int i, String str) {
        this.b.p = false;
        this.b.o = true;
        if (!eal.a(this.c, this.a)) {
            this.b.n = true;
        } else if (!this.b.isAlive()) {
            this.b.n = true;
        } else {
            this.b.j = false;
            this.b.b();
            if (i / 10 == 6) {
                int i2 = i % 10;
                if (i2 == 1) {
                    this.b.a(ResultStatus.FAILED_NO_RESULT);
                } else if (i2 == 2) {
                    if (TextUtils.isEmpty(str) || !str.contains("距离太近")) {
                        this.b.a(ResultStatus.FAILED_NO_RESULT);
                    } else {
                        this.b.a(ResultStatus.FAILED_NO_RESULT_FOR_SHORT_DISTANCE);
                    }
                } else if (i2 == 3) {
                    this.b.a(ResultStatus.FAILED_SERVER_ERROR);
                } else {
                    this.b.a(ResultStatus.FAILED_NO_RESULT);
                }
            } else {
                this.b.a(ResultStatus.FAILED_NET_ERROR);
            }
            this.b.a(false);
        }
    }

    public final void a(Throwable th, boolean z) {
        if (!eal.a(this.c, this.a)) {
            this.b.n = true;
        } else if (!this.b.isAlive()) {
            this.b.n = true;
        } else {
            this.b.j = false;
            this.b.b();
            this.b.a(ResultStatus.FAILED_NET_ERROR);
        }
    }
}
