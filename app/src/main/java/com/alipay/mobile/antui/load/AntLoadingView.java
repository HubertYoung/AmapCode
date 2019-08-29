package com.alipay.mobile.antui.load;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUEmptyGoneTextView;
import com.alipay.mobile.antui.lottie.AULottieLayout;
import com.alipay.mobile.antui.utils.AULottieFileUtils;
import com.alipay.mobile.antui.utils.AuiLogger;

public class AntLoadingView extends AbsLoadingView {
    private static final float REFRESH_END_PROGRESS = 0.8f;
    private static final float REFRESH_START_PROGRESS = 0.17f;
    public static final String STYLE_BLUE = "_BLUE";
    public static final String STYLE_WHITE = "_WHITE";
    private AULottieLayout antAnimationView;
    private String currentStyle = "";
    private AUEmptyGoneTextView titleView;

    public AntLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AntLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AntLoadingView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.au_loading_layout, this, true);
        this.titleView = (AUEmptyGoneTextView) findViewById(R.id.title);
        this.antAnimationView = (AULottieLayout) findViewById(R.id.animation);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isLoading) {
            AuiLogger.debug("AntLoadingView ", "onAttachedToWindow isLoading = " + this.isLoading);
            this.antAnimationView.playAnimation();
        }
    }

    public void initAnimation(String key) {
        setAntColor(key);
    }

    public void setAntColor(String style) {
        if (!TextUtils.equals(this.currentStyle, style)) {
            this.currentStyle = style;
            if (TextUtils.equals(style, "_BLUE")) {
                this.antAnimationView.setAnimationSource(AULottieFileUtils.getRefreshAnimation(getContext()));
                this.titleView.setTextColor(getResources().getColor(R.color.AU_COLOR_SUB_CONTENT));
            } else if (TextUtils.equals(style, "_WHITE")) {
                this.antAnimationView.setAnimationSource(AULottieFileUtils.getRefreshAnimation(getContext(), 40, -1, 22, Color.parseColor("#070707")));
                this.titleView.setTextColor(Color.parseColor("#9AFFFFFF"));
            }
            this.antAnimationView.addAnimatorUpdateListener(new h(this));
        }
    }

    public void setLoadingText(String text) {
        this.titleView.setText(text);
    }

    public void onPullOver(int distance, int maxDistance) {
        float positionPercent = ((float) distance) / ((float) maxDistance);
        if (positionPercent < 0.3f) {
            this.antAnimationView.setProgress(0.0f);
        } else if (positionPercent < 1.0f) {
            this.antAnimationView.setProgress(REFRESH_START_PROGRESS * positionPercent);
        } else {
            this.antAnimationView.setProgress(REFRESH_START_PROGRESS);
            this.antAnimationView.pauseAnimation();
        }
    }

    public void startLoading() {
        this.antAnimationView.setMinAndMaxProgress(REFRESH_START_PROGRESS, REFRESH_END_PROGRESS);
        this.antAnimationView.setProgress(REFRESH_START_PROGRESS);
        this.antAnimationView.loop(true);
        this.antAnimationView.playAnimation();
        this.isLoading = true;
    }

    public void finishLoading() {
        this.antAnimationView.setMinAndMaxProgress(0.0f, 1.0f);
        this.antAnimationView.loop(false);
        this.antAnimationView.cancelAnimation();
        setFirstLoadingAppeared(false);
        this.isLoading = false;
    }

    public void pause() {
        this.antAnimationView.pauseAnimation();
        this.isLoading = false;
    }
}
