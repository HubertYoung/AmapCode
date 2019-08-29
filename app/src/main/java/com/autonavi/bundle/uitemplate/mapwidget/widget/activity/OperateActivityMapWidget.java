package com.autonavi.bundle.uitemplate.mapwidget.widget.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.minimap.R;

public class OperateActivityMapWidget extends AbstractMapWidget<OperateActivityWidgetPresenter> {
    private MvpGifImageView mActivityGif;
    private LottieAnimationView mActivityLottie;
    private ImageView mActivityPng;

    public OperateActivityMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, R.layout.map_widget_operate_activity);
        loadLayoutRes.setVisibility(8);
        return loadLayoutRes;
    }

    public void onInit(Context context) {
        if (this.mContentView != null) {
            this.mActivityPng = (ImageView) this.mContentView.findViewById(R.id.activity_png);
            this.mActivityGif = (MvpGifImageView) this.mContentView.findViewById(R.id.activity_gif);
            this.mActivityLottie = (LottieAnimationView) this.mContentView.findViewById(R.id.activity_lottie);
            this.mActivityLottie.loop(true);
        }
    }

    public void setLottieViewVisibility(int i) {
        if (this.mActivityLottie != null && this.mActivityLottie.getVisibility() != i) {
            this.mActivityLottie.setVisibility(i);
        }
    }

    public void setGifViewVisibility(int i) {
        if (this.mActivityGif != null && this.mActivityGif.getVisibility() != i) {
            this.mActivityGif.setVisibility(i);
        }
    }

    public void setPngViewVisibility(int i) {
        if (this.mActivityPng != null && this.mActivityPng.getVisibility() != i) {
            this.mActivityPng.setVisibility(i);
        }
    }

    public void setContentViewVisibility(int i) {
        if (this.mContentView != null && this.mContentView.getVisibility() != i) {
            Stub.getMapWidgetManager().setWidgetVisibleForType(WidgetType.ACTIVITY, i);
        }
    }

    public ImageView showPNGView() {
        setLottieViewVisibility(8);
        setGifViewVisibility(8);
        setPngViewVisibility(0);
        return this.mActivityPng;
    }

    public MvpGifImageView showGIFView() {
        setLottieViewVisibility(8);
        setPngViewVisibility(8);
        return this.mActivityGif;
    }

    public LottieAnimationView showLOTTIEView() {
        setGifViewVisibility(8);
        setPngViewVisibility(8);
        setLottieViewVisibility(0);
        return this.mActivityLottie;
    }
}
