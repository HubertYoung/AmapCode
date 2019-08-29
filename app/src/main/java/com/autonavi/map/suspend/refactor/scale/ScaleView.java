package com.autonavi.map.suspend.refactor.scale;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.R;

public class ScaleView extends FrameLayout implements e, f, a {
    private static int LOGO_ID = R.drawable.default_main_autonavi_logo;
    public static final int SCALESTATUS_LOGO = 2;
    public static final int SCALESTATUS_NORMAL = 0;
    public static final int SCALESTATUS_SCALE = 1;
    private static final long SCALE_DELAY = 1000;
    private static final long VIEW_ANIM_DURATION = 1000;
    private boolean isAttached = false;
    private boolean isShowLogoAnimation = true;
    private MapCustomizeManager mCustomizeManager;
    /* access modifiers changed from: private */
    public ImageView mImgLogo;
    private ValueAnimator mLogoShowingAnimator;
    /* access modifiers changed from: private */
    public ScaleLineView mScaleLineView;
    private int mScaleStatus = 0;
    private lg mTimeOutWatcher;
    Runnable r = new Runnable() {
        public final void run() {
            ScaleView.this.refresh();
        }
    };

    public ScaleView(Context context, MapCustomizeManager mapCustomizeManager) {
        super(context);
        this.mCustomizeManager = mapCustomizeManager;
        init(context, null);
    }

    public ScaleView(Context context) {
        super(context);
        init(context, null);
    }

    public ScaleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public ScaleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public void setMapManager(MapManager mapManager) {
        if (this.mScaleLineView != null) {
            this.mScaleLineView.setMapContainer(mapManager);
        }
    }

    public int getScaleStatus() {
        return this.mScaleStatus;
    }

    public void setLogo(int i) {
        setLogo(i, false);
    }

    public void setLogo(int i, boolean z) {
        if (this.mImgLogo != null) {
            this.mImgLogo.setImageResource(i);
        }
        if (z) {
            LOGO_ID = i;
        }
    }

    public void setLogoSize(int i, int i2) {
        LayoutParams layoutParams = this.mImgLogo.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
            layoutParams.height = i2;
            this.mImgLogo.setLayoutParams(layoutParams);
        }
    }

    public void setTextSize(int i) {
        if (this.mScaleLineView != null) {
            this.mScaleLineView.setTextSize(i);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttached = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttached = false;
    }

    private void init(Context context, AttributeSet attributeSet) {
        cdd.n().a((f) this);
        cdd.n().a((e) this);
        this.mTimeOutWatcher = new lg(1000);
        this.mTimeOutWatcher.a(this);
        this.mScaleLineView = new ScaleLineView(context, attributeSet);
        this.mImgLogo = new ImageView(context);
        this.mImgLogo.setImageResource(LOGO_ID);
        this.mImgLogo.setBackgroundResource(0);
        this.mImgLogo.setAlpha(1.0f);
        this.mScaleLineView.setAlpha(0.0f);
        this.mImgLogo.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 83));
        addAll();
        initLogoAnimator();
    }

    private void initLogoAnimator() {
        this.mLogoShowingAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mLogoShowingAnimator.setDuration(1000);
        this.mLogoShowingAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float f = (Float) valueAnimator.getAnimatedValue();
                ScaleView.this.mImgLogo.setAlpha(f.floatValue());
                ScaleView.this.mScaleLineView.setAlpha(1.0f - f.floatValue());
            }
        });
        this.mLogoShowingAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
            }

            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
            }
        });
    }

    private void resetIfNecessary() {
        if (isLogoShowing()) {
            cancelLogoShowingAnimator();
        }
        hideLogo();
    }

    private void animatedShowLogo() {
        if (this.mLogoShowingAnimator != null && !this.mLogoShowingAnimator.isStarted()) {
            this.mLogoShowingAnimator.start();
        }
    }

    public void setScaleStatus(int i) {
        this.mScaleStatus = i;
        if (this.mScaleStatus == 1) {
            this.isShowLogoAnimation = false;
        } else {
            this.isShowLogoAnimation = true;
        }
    }

    private void cancelLogoShowingAnimator() {
        if (this.mLogoShowingAnimator != null) {
            this.mLogoShowingAnimator.reverse();
        }
    }

    private boolean isLogoShowing() {
        return this.mLogoShowingAnimator != null && this.mLogoShowingAnimator.isRunning();
    }

    public boolean isLogoShown() {
        return this.mImgLogo.isShown() && this.mImgLogo.getAlpha() > 0.0f;
    }

    private void hideLogo() {
        this.mImgLogo.setAlpha(0.0f);
        this.mScaleLineView.setAlpha(1.0f);
        this.mScaleLineView.setVisibility(0);
    }

    private void showLogo() {
        this.mImgLogo.setAlpha(1.0f);
        this.mScaleLineView.setAlpha(0.0f);
    }

    public ScaleLineView getScaleLineView() {
        return this.mScaleLineView;
    }

    private void removeAll() {
        removeAllViews();
    }

    private void addAll() {
        ViewParent parent = this.mImgLogo.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.mImgLogo);
        }
        addView(this.mImgLogo);
        ViewParent parent2 = this.mScaleLineView.getParent();
        if (parent2 instanceof ViewGroup) {
            ((ViewGroup) parent2).removeView(this.mScaleLineView);
        }
        addView(this.mScaleLineView);
    }

    private void refreshScaleLine() {
        if (this.mScaleLineView != null) {
            this.mScaleLineView.refreshScaleLineView();
        }
    }

    /* access modifiers changed from: private */
    public void refresh() {
        if (this.mTimeOutWatcher.c) {
            resetIfNecessary();
            this.mTimeOutWatcher.b();
        }
        this.mTimeOutWatcher.c();
    }

    private void postRefresh() {
        if (this.isAttached) {
            post(this.r);
        } else {
            this.r.run();
        }
    }

    public void setAmapLogoVisibility(boolean z) {
        this.isShowLogoAnimation = z;
        updateLogo();
    }

    private void updateLogo() {
        if (this.isShowLogoAnimation) {
            showLogo();
        } else {
            hideLogo();
        }
    }

    public void onTimeReset() {
        refreshScaleLine();
    }

    public void onTimeOut() {
        if (this.isShowLogoAnimation) {
            animatedShowLogo();
        }
    }

    public void refreshScaleLineView() {
        refreshScaleLine();
    }

    @Deprecated
    public void setScaleColor(int i, int i2) {
        if (this.mScaleLineView != null) {
            this.mScaleLineView.setScaleColor(i, i2);
        }
    }

    public void setScaleLineColor(int i, int i2) {
        ScaleLineView scaleLineView = getScaleLineView();
        if (scaleLineView != null) {
            scaleLineView.setScaleLineColor(i, i2);
            scaleLineView.postInvalidate();
        }
    }

    public void refreshScaleLogo() {
        switch (this.mScaleStatus) {
            case 0:
                if (this.isShowLogoAnimation) {
                    if (this.mScaleLineView.needrefreshLogo()) {
                        postRefresh();
                        break;
                    }
                } else {
                    hideLogo();
                    return;
                }
                break;
            case 1:
                hideLogo();
                return;
            case 2:
                showLogo();
                return;
        }
    }

    public void changeLogoStatus(boolean z) {
        switch (this.mScaleStatus) {
            case 0:
                if (z) {
                    if (this.isShowLogoAnimation) {
                        postRefresh();
                        break;
                    }
                } else {
                    hideLogo();
                    return;
                }
                break;
            case 1:
                hideLogo();
                return;
            case 2:
                showLogo();
                return;
        }
    }

    public void onResetViewState() {
        if (this.mCustomizeManager != null && this.mCustomizeManager.getNaviMode() <= 0 && !cdd.n().l()) {
            ViewParent parent = getParent();
            ViewGroup viewGroup = null;
            if (ViewGroup.class.isInstance(parent)) {
                viewGroup = (ViewGroup) parent;
            }
            if (!(this.mCustomizeManager == null || this.mCustomizeManager.isViewEnable(8192) || viewGroup == null)) {
                viewGroup.removeView(this);
            }
        }
    }
}
