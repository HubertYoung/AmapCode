package com.alipay.mobile.antui.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.uc.webview.export.extension.UCCore;

public class SquareRelativeLayout extends AURelativeLayout {
    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int widthMeasureSpec2 = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), UCCore.VERIFY_POLICY_QUICK);
        super.onMeasure(widthMeasureSpec2, widthMeasureSpec2);
    }
}
