package com.alipay.mobile.antui.segement;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.utils.AuiLogger;

public class AUScrollLayout extends AULinearLayout {
    private static final String TAG = AUScrollLayout.class.getSimpleName();
    private boolean divideAutoSize = true;
    private int maxWidth = 0;
    private boolean noScroll;

    public AUScrollLayout(Context context) {
        super(context);
        initScreenWidth();
    }

    public AUScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScreenWidth();
    }

    public void setDivideAutoSize(boolean autoSize) {
        this.divideAutoSize = autoSize;
    }

    private void initScreenWidth() {
        Resources res = getResources();
        if (res != null) {
            DisplayMetrics metrics = res.getDisplayMetrics();
            if (metrics != null) {
                this.maxWidth = metrics.widthPixels;
            }
        }
        AuiLogger.debug(TAG, "1111, maxWidth:" + this.maxWidth);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft = getPaddingLeft();
        AuiLogger.debug(TAG, "2222, maxWidth:" + this.maxWidth + " ,widthMeasureSpec:" + widthMeasureSpec);
        int paddingRight = getPaddingRight();
        int childLeft = paddingLeft;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int childWidth = child.getMeasuredWidth();
                AuiLogger.debug(TAG, "3333, childWidth:" + childWidth);
                if (childLeft + childWidth + paddingRight > this.maxWidth) {
                    this.noScroll = false;
                } else {
                    childLeft += childWidth;
                    this.noScroll = true;
                }
            }
        }
        if (this.noScroll) {
            setMeasuredDimension(this.maxWidth, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        AuiLogger.debug(TAG, "divideAutoSize:" + this.divideAutoSize + ", noScroll:" + this.noScroll + ", maxWidth:" + this.maxWidth);
        if (!this.noScroll || !this.divideAutoSize) {
            super.onLayout(changed, l, t, r, b);
            return;
        }
        int size = getChildCount();
        int childTop = getPaddingTop();
        int childWidth = this.maxWidth / size;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != 8) {
                LayoutParams params = new LayoutParams(-1, -1);
                int paddingLeft = childView.getPaddingLeft() + ((childWidth - childView.getMeasuredWidth()) / 2);
                childView.setPadding(paddingLeft, childView.getPaddingTop(), paddingLeft, childView.getPaddingBottom());
                childView.setLayoutParams(params);
                int childLeft = i * childWidth;
                childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childView.getMeasuredHeight());
            }
        }
    }
}
