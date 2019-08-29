package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.route.train.page.ExtTrainPlanListPage;
import com.autonavi.minimap.route.train.presenter.ExtTrainPlanListPresenter$1;
import com.autonavi.minimap.route.train.stationlist.StationRequestManger;

/* renamed from: ejj reason: default package */
/* compiled from: ExtTrainPlanListPresenter */
public final class ejj extends eaf<ExtTrainPlanListPage> {
    /* access modifiers changed from: private */
    public ctl a;

    public final ON_BACK_TYPE onBackPressed() {
        ExtTrainPlanListPage extTrainPlanListPage = (ExtTrainPlanListPage) this.mPage;
        if (!(extTrainPlanListPage.c != null && extTrainPlanListPage.c.isShown())) {
            return super.onBackPressed();
        }
        ((ExtTrainPlanListPage) this.mPage).a();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public ejj(ExtTrainPlanListPage extTrainPlanListPage) {
        super(extTrainPlanListPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        ((ExtTrainPlanListPage) this.mPage).a(((ExtTrainPlanListPage) this.mPage).getArguments());
        ExtTrainPlanListPage extTrainPlanListPage = (ExtTrainPlanListPage) this.mPage;
        if (!extTrainPlanListPage.b && extTrainPlanListPage.c != null) {
            extTrainPlanListPage.a.addView(extTrainPlanListPage.c);
            extTrainPlanListPage.b = true;
        }
        ((ExtTrainPlanListPage) this.mPage).a(false);
        if (((ExtTrainPlanListPage) this.mPage).getArguments() != null) {
            ((ExtTrainPlanListPage) this.mPage).f();
        }
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onAppear() {
            }

            public final void onCover() {
                if (ejj.this.a != null) {
                    ctl b = ejj.this.a;
                    ejj.this.mPage;
                    b.a("18");
                }
            }
        });
    }

    public final void onStop() {
        ((ExtTrainPlanListPage) this.mPage).b();
        super.onStop();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (i != -1) {
            ((ExtTrainPlanListPage) this.mPage).f();
        }
    }

    public final void onStart() {
        StationRequestManger.a().a(0);
        if (((ExtTrainPlanListPage) this.mPage).g) {
            this.a = (ctl) a.a.a(ctl.class);
            ((ExtTrainPlanListPage) this.mPage).g = false;
            if (this.a != null) {
                this.a.a("15", new ExtTrainPlanListPresenter$1(this));
            }
        }
        ((ExtTrainPlanListPage) this.mPage).a(false);
        super.onStart();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        ((ExtTrainPlanListPage) this.mPage).d = false;
        super.onNewIntent(pageBundle);
        ((ExtTrainPlanListPage) this.mPage).a(pageBundle);
        ((ExtTrainPlanListPage) this.mPage).a(true);
    }
}
