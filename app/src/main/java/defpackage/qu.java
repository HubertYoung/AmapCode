package defpackage;

import android.content.res.Configuration;
import com.amap.bundle.drive.setting.quicknaviwidget.main.QuickAutonNaviSettingFragment;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;

/* renamed from: qu reason: default package */
/* compiled from: QuickAutoNaviSettingPresenter */
public final class qu extends sw<QuickAutonNaviSettingFragment, qt> {
    public qu(QuickAutonNaviSettingFragment quickAutonNaviSettingFragment) {
        super(quickAutonNaviSettingFragment);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        ((QuickAutonNaviSettingFragment) this.mPage).init();
    }

    public final void onStart() {
        super.onStart();
        ((QuickAutonNaviSettingFragment) this.mPage).onPageResume();
    }

    public final void onDestroy() {
        super.onDestroy();
        ((QuickAutonNaviSettingFragment) this.mPage).onPageDestroyView();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        ((QuickAutonNaviSettingFragment) this.mPage).onPageResult(i, resultType, pageBundle);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((QuickAutonNaviSettingFragment) this.mPage).configurationChanged(configuration);
    }

    public final ON_BACK_TYPE onBackPressed() {
        return ((QuickAutonNaviSettingFragment) this.mPage).isMendTruckDialogShow() ? ON_BACK_TYPE.TYPE_IGNORE : super.onBackPressed();
    }

    public final /* synthetic */ su a() {
        return new qt(this);
    }
}
