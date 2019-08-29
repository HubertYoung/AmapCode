package defpackage;

import com.amap.bundle.drive.setting.quicknaviwidget.broadcast.QuickAutoNaviBroadcastSettings;

/* renamed from: qo reason: default package */
/* compiled from: QuickAutoNaviBroadcastSettingsPresenter */
public final class qo extends sw<QuickAutoNaviBroadcastSettings, qn> {
    public qo(QuickAutoNaviBroadcastSettings quickAutoNaviBroadcastSettings) {
        super(quickAutoNaviBroadcastSettings);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        ((QuickAutoNaviBroadcastSettings) this.mPage).init();
    }

    public final /* synthetic */ su a() {
        return new qn(this);
    }
}
