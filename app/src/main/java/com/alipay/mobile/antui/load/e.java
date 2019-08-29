package com.alipay.mobile.antui.load;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AURefreshListView */
final class e implements AnimationListener {
    final /* synthetic */ AURefreshListView a;

    private e(AURefreshListView aURefreshListView) {
        this.a = aURefreshListView;
    }

    /* synthetic */ e(AURefreshListView x0, byte b) {
        this(x0);
    }

    public final void onAnimationStart(Animation animation) {
        AuiLogger.debug("AURefreshListView", "RefreshFinishAnimationListener onAnimationStart");
        if (animation instanceof f) {
            ((f) animation).a(false);
        }
        if (this.a.mOnPullRefreshListener != null) {
            AuiLogger.debug("AURefreshListView", "mOnPullRefreshListener onRefreshFinished");
            this.a.mOnPullRefreshListener.onRefreshFinished();
            return;
        }
        AuiLogger.debug("AURefreshListView", "mOnPullRefreshListener is null");
    }

    public final void onAnimationEnd(Animation animation) {
        AuiLogger.debug("AURefreshListView", "RefreshFinishAnimationListener onAnimationEnd");
        if (animation instanceof f) {
            ((f) animation).a(true);
        }
        this.a.refreshFinishLayoutAction();
    }

    public final void onAnimationRepeat(Animation animation) {
    }
}
