package defpackage;

import com.amap.bundle.drive.setting.quicknaviwidget.btchannel.QuickAutoNaviBTChannelPage;

/* renamed from: qq reason: default package */
/* compiled from: QuickAutoNaviBTChannelPresenter */
public final class qq extends sw<QuickAutoNaviBTChannelPage, qp> {
    public qq(QuickAutoNaviBTChannelPage quickAutoNaviBTChannelPage) {
        super(quickAutoNaviBTChannelPage);
    }

    public final void onPageCreated() {
        ((QuickAutoNaviBTChannelPage) this.mPage).checkBundle();
        ((QuickAutoNaviBTChannelPage) this.mPage).initView();
        ((QuickAutoNaviBTChannelPage) this.mPage).setData();
        ((QuickAutoNaviBTChannelPage) this.mPage).setListeners();
    }

    public final void onDestroy() {
        super.onDestroy();
        ((QuickAutoNaviBTChannelPage) this.mPage).releaseSpeakerPlayManager();
    }

    public final /* synthetic */ su a() {
        return new qp(this);
    }
}
