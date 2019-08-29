package defpackage;

import android.content.res.Configuration;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;

/* renamed from: dgk reason: default package */
/* compiled from: OfflineNaviTtsPresenter */
public final class dgk extends sw<OfflineNaviTtsFragment, dgj> {
    public dgk(OfflineNaviTtsFragment offlineNaviTtsFragment) {
        super(offlineNaviTtsFragment);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mPage != null && configuration != null) {
            ((OfflineNaviTtsFragment) this.mPage).onConfigurationChanged(configuration.orientation);
        }
    }

    public final void onPageCreated() {
        ((OfflineNaviTtsFragment) this.mPage).onPageViewCreated();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        ((OfflineNaviTtsFragment) this.mPage).onPageResult(i, resultType, pageBundle);
    }

    public final void onStart() {
        ((OfflineNaviTtsFragment) this.mPage).onPageResume();
    }

    public final void onStop() {
        ((OfflineNaviTtsFragment) this.mPage).onPagePause();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        ((OfflineNaviTtsFragment) this.mPage).onPageNewNodeFragmentBundle(pageBundle);
    }

    public final void onDestroy() {
        ((OfflineNaviTtsFragment) this.mPage).onPageDestroy();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((OfflineNaviTtsFragment) this.mPage).onPageBackPressed()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final /* synthetic */ su a() {
        return new dgj(this);
    }
}
