package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import com.alipay.mobile.antui.api.AUScrollViewListener;

public class AUScrollView extends ScrollView implements AUViewInterface {
    private Boolean isAP;
    private AUScrollViewListener mScrollViewListener;

    public AUScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AUScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUScrollView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mScrollViewListener != null) {
            this.mScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setScrollViewListener(AUScrollViewListener scrollViewListener) {
        this.mScrollViewListener = scrollViewListener;
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
