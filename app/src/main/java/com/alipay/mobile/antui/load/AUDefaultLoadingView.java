package com.alipay.mobile.antui.load;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUDefaultLoadingView extends AbsLoadingView {
    private LoadingAnimationView smilenceView;
    private AUTextView titleView;

    public AUDefaultLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AUDefaultLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AUDefaultLoadingView(Context context) {
        super(context);
        init();
    }

    public void setLoadingText(String text) {
        this.titleView.setText(text);
        if (TextUtils.isEmpty(text)) {
            this.titleView.setVisibility(8);
        } else {
            this.titleView.setVisibility(0);
        }
    }

    public void onPullOver(int distance, int maxDistance) {
    }

    public void startLoading() {
        this.smilenceView.setMode(1);
        this.smilenceView.setAlpha(1.0f);
    }

    public void finishLoading() {
        this.smilenceView.setMode(0);
    }

    public void pause() {
        this.smilenceView.pause();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.smilence_refresh, this, true);
        this.titleView = (AUTextView) findViewById(R.id.title);
        this.smilenceView = (LoadingAnimationView) findViewById(R.id.smile);
        this.smilenceView.setMode(0);
        this.smilenceView.setLoadingAppearedListener(new a(this));
    }
}
