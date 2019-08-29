package defpackage;

import android.content.res.Configuration;
import com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;

/* renamed from: or reason: default package */
/* compiled from: AjxRouteCarResultBrowserPresenter */
public final class or extends Ajx3PagePresenter {
    public or(AjxRouteCarResultBrowserPage ajxRouteCarResultBrowserPage) {
        super(ajxRouteCarResultBrowserPage);
        this.mPage = ajxRouteCarResultBrowserPage;
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
    }

    public final boolean onMapLevelChange(boolean z) {
        super.onMapLevelChange(z);
        return true;
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
    }

    public final boolean onEngineActionGesture(alg alg) {
        return super.onEngineActionGesture(alg);
    }
}
