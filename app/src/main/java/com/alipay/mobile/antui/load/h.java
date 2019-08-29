package com.alipay.mobile.antui.load;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.net.ParseException;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AntLoadingView */
final class h implements AnimatorUpdateListener {
    final /* synthetic */ AntLoadingView a;

    h(AntLoadingView this$0) {
        this.a = this$0;
    }

    public final void onAnimationUpdate(ValueAnimator animation) {
        try {
            if (((double) ((Float) animation.getAnimatedValue()).floatValue()) >= 0.7500000119209289d && this.a.loadingListener != null) {
                AuiLogger.error("AntLoadingView", "firstLoadingAppeared");
                this.a.setFirstLoadingAppeared(true);
                this.a.loadingListener.onLoadingAppeared();
            }
        } catch (ParseException ex) {
            AuiLogger.error("AntLoadingView", "parseALabel error :" + ex);
        }
    }
}
