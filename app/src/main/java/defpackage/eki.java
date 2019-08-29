package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.ugc.page.FootNaviReviewPage;

/* renamed from: eki reason: default package */
/* compiled from: FootNaviReviewPresenter */
public final class eki extends eaf<FootNaviReviewPage> {
    public eki(FootNaviReviewPage footNaviReviewPage) {
        super(footNaviReviewPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        LogManager.actionLogV2("P00267", "B002");
    }

    public final void onStart() {
        super.onStart();
        ((FootNaviReviewPage) this.mPage).b();
        ((FootNaviReviewPage) this.mPage).f();
        ((FootNaviReviewPage) this.mPage).setSoftInputMode(18);
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final void onStop() {
        super.onStop();
        FootNaviReviewPage footNaviReviewPage = (FootNaviReviewPage) this.mPage;
        footNaviReviewPage.setSoftInputMode(footNaviReviewPage.e);
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((FootNaviReviewPage) this.mPage).setResult(ResultType.CANCEL, (PageBundle) null);
        return super.onBackPressed();
    }
}
