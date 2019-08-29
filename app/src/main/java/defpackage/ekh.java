package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.ugc.page.BusNaviReviewPage;

/* renamed from: ekh reason: default package */
/* compiled from: BusNaviReviewPresenter */
public final class ekh extends eaf<BusNaviReviewPage> {
    public ekh(BusNaviReviewPage busNaviReviewPage) {
        super(busNaviReviewPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        ((BusNaviReviewPage) this.mPage).f();
        LogManager.actionLogV2("P00266", "B001");
    }

    public final void onStart() {
        super.onStart();
        ((BusNaviReviewPage) this.mPage).b();
        ((BusNaviReviewPage) this.mPage).setSoftInputMode(16);
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((BusNaviReviewPage) this.mPage).setResult(ResultType.CANCEL, (PageBundle) null);
        return super.onBackPressed();
    }
}
