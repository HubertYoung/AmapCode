package com.alipay.mobile.antui.load;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AURefreshListView */
final class b implements AnimationListener {
    final /* synthetic */ AURefreshListView a;

    b(AURefreshListView this$0) {
        this.a = this$0;
    }

    public final void onAnimationStart(Animation animation) {
        if (animation instanceof f) {
            ((f) animation).a(false);
        }
    }

    public final void onAnimationEnd(Animation animation) {
        AuiLogger.debug("AURefreshListView", "releaseToRefreshAnimListener onAnimationEnd");
        if (animation instanceof f) {
            ((f) animation).a(true);
        }
        this.a.loadingView.startLoading();
    }

    public final void onAnimationRepeat(Animation animation) {
    }
}
