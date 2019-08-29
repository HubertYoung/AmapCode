package defpackage;

import com.amap.bundle.drive.setting.quicknaviwidget.display.QuickAutoNaviDisplaySettings;

/* renamed from: qs reason: default package */
/* compiled from: QuickAutoNaviDisplaySettingsPresenter */
public final class qs extends sw<QuickAutoNaviDisplaySettings, qr> {
    public qs(QuickAutoNaviDisplaySettings quickAutoNaviDisplaySettings) {
        super(quickAutoNaviDisplaySettings);
    }

    public final void onPageCreated() {
        ((QuickAutoNaviDisplaySettings) this.mPage).onPageViewCreated();
    }

    public final /* synthetic */ su a() {
        return new qr(this);
    }
}
