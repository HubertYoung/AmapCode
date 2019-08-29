package com.alipay.mobile.antui.load;

import android.content.Context;
import android.util.AttributeSet;
import com.alipay.mobile.antui.basic.AUFrameLayout;

public abstract class AbsLoadingView extends AUFrameLayout {
    private boolean firstLoadingAppeared;
    protected boolean isLoading = false;
    protected LoadingListener loadingListener;

    public interface LoadingListener {
        void onLoadingAppeared();
    }

    public abstract void finishLoading();

    public abstract void onPullOver(int i, int i2);

    public abstract void pause();

    public abstract void startLoading();

    public AbsLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AbsLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsLoadingView(Context context) {
        super(context);
    }

    public void setLoadingText(String text) {
    }

    public void setLoadingListener(LoadingListener loadingListener2) {
        this.loadingListener = loadingListener2;
    }

    public void setFirstLoadingAppeared(boolean firstLoadingAppeared2) {
        this.firstLoadingAppeared = firstLoadingAppeared2;
    }

    public boolean isFirstLoadingAppeared() {
        return this.firstLoadingAppeared;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void initAnimation(String key) {
    }
}
