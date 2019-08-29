package com.alipay.mobile.antui.load;

import android.content.Context;
import android.util.AttributeSet;
import com.alipay.mobile.antui.basic.AUFrameLayout;

public abstract class AbsLoadMoreView extends AUFrameLayout {
    public abstract void loadingFinished(boolean z);

    public abstract void startLoading();

    public AbsLoadMoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AbsLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsLoadMoreView(Context context) {
        super(context);
    }
}
