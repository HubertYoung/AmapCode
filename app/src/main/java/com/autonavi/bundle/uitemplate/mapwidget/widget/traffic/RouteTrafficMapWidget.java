package com.autonavi.bundle.uitemplate.mapwidget.widget.traffic;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.view.View;
import com.airbnb.lottie.LottieAnimationView;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class RouteTrafficMapWidget extends AbstractMapWidget<RouteTrafficWidgetPresenter> {
    /* access modifiers changed from: private */
    public LottieAnimationView mTrafficIcon;

    public RouteTrafficMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, R.layout.map_widget_route_traffic_layout);
        this.mTrafficIcon = (LottieAnimationView) loadLayoutRes.findViewById(R.id.traffic_icon);
        return loadLayoutRes;
    }

    public void startLottie(boolean z) {
        ValueAnimator valueAnimator;
        if (!z) {
            valueAnimator = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RouteTrafficMapWidget.this.mTrafficIcon.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        } else {
            valueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RouteTrafficMapWidget.this.mTrafficIcon.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        valueAnimator.start();
    }
}
