package defpackage;

import com.amap.bundle.drive.setting.quicknaviwidget.vcs.QuickAutoNaviVCSPage;

/* renamed from: qy reason: default package */
/* compiled from: QuickAutoNaviVCSPresenter */
public final class qy extends sw<QuickAutoNaviVCSPage, qx> {
    public qy(QuickAutoNaviVCSPage quickAutoNaviVCSPage) {
        super(quickAutoNaviVCSPage);
    }

    public final void onPageCreated() {
        ((QuickAutoNaviVCSPage) this.mPage).initView();
        ((QuickAutoNaviVCSPage) this.mPage).setData();
        ((QuickAutoNaviVCSPage) this.mPage).setListeners();
    }

    public final void onResume() {
        ((QuickAutoNaviVCSPage) this.mPage).setData();
    }

    public final /* synthetic */ su a() {
        return new qx(this);
    }
}
