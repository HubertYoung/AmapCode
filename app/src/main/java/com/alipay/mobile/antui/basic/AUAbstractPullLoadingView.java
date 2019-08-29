package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;

public abstract class AUAbstractPullLoadingView extends AURelativeLayout {
    public static final byte STATE_CLOSE = 0;
    public static final byte STATE_FINISH = 4;
    public static final byte STATE_LOAD = 3;
    public static final byte STATE_OPEN = 1;
    public static final byte STATE_OVER = 2;
    protected byte mState = 0;

    public abstract int getOverViewHeight();

    public abstract void init();

    public abstract void onFinish();

    public abstract void onLoad();

    public abstract void onOpen();

    public abstract void onOver();

    public AUAbstractPullLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AUAbstractPullLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AUAbstractPullLoadingView(Context context) {
        super(context);
        init();
    }

    public void onPullto(double pos, byte pullRefreshState) {
    }

    public void setState(byte state) {
        this.mState = state;
    }

    public byte getState() {
        return this.mState;
    }

    public AUView getLoadingShadowView() {
        return null;
    }

    public AUView getNormalShadowView() {
        return null;
    }

    public AUImageView getLoadingLogo() {
        return null;
    }

    public AUImageView getNormalLogo() {
        return null;
    }

    public long getRemainedLoadingDuration() {
        return 0;
    }
}
