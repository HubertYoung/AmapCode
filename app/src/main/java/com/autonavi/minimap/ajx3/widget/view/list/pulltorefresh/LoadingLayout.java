package com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode;

@SuppressLint({"ViewConstructor"})
public abstract class LoadingLayout extends FrameLayout {
    protected final Mode mMode;
    protected String mPullLabel;
    protected String mRefreshingLabel;
    protected String mReleaseLabel;

    /* access modifiers changed from: protected */
    public abstract int getContentSize();

    public abstract boolean isHidden();

    /* access modifiers changed from: protected */
    public abstract void pullToRefresh();

    public abstract void refreshing();

    /* access modifiers changed from: protected */
    public abstract void releaseToRefresh();

    public abstract void reset();

    public abstract void setArrowImage(String str);

    public abstract void setBgColor(int i);

    public abstract void setBgImage(String str);

    public abstract void setHidden(boolean z);

    public abstract void setTextColor(int i);

    public LoadingLayout(Context context, Mode mode) {
        super(context);
        this.mMode = mode;
    }

    /* access modifiers changed from: protected */
    public final void setHeight(int i) {
        getLayoutParams().height = i;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public final void setWidth(int i) {
        getLayoutParams().width = i;
        requestLayout();
    }

    public void setPullText(String str) {
        this.mPullLabel = str;
    }

    public void setRefreshingText(String str) {
        this.mRefreshingLabel = str;
    }

    public void setReleaseText(String str) {
        this.mReleaseLabel = str;
    }
}
