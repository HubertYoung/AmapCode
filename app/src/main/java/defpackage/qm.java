package defpackage;

import com.amap.bundle.drive.setting.quicknaviwidget.accessibility.QuickAutoNaviAccessibilitySettings;

/* renamed from: qm reason: default package */
/* compiled from: QuickAutoNaviAccessibilitySettingsPresenter */
public final class qm extends sw<QuickAutoNaviAccessibilitySettings, ql> {
    public qm(QuickAutoNaviAccessibilitySettings quickAutoNaviAccessibilitySettings) {
        super(quickAutoNaviAccessibilitySettings);
    }

    public final void onPageCreated() {
        ((QuickAutoNaviAccessibilitySettings) this.mPage).init();
        ((QuickAutoNaviAccessibilitySettings) this.mPage).setViewData();
    }

    public final /* synthetic */ su a() {
        return new ql(this);
    }
}
