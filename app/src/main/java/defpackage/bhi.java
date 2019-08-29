package defpackage;

import com.autonavi.carowner.payfor.ApplyPayForListFragment;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;

/* renamed from: bhi reason: default package */
/* compiled from: ApplyPayForListPresenter */
public final class bhi extends sw<ApplyPayForListFragment, bhc> {
    public bhi(ApplyPayForListFragment applyPayForListFragment) {
        super(applyPayForListFragment);
    }

    public final void onPageCreated() {
        ((ApplyPayForListFragment) this.mPage).initView();
    }

    public final void onStart() {
        ((ApplyPayForListFragment) this.mPage).onPageResume();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        ((ApplyPayForListFragment) this.mPage).onPageResult(i, resultType, pageBundle);
    }

    public final /* synthetic */ su a() {
        return new bhc(this);
    }
}
