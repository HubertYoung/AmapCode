package com.autonavi.map.search.comment.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import com.uc.webview.export.extension.UCCore;

public class SpuareLayout extends RelativeLayout {
    public SpuareLayout(Context context) {
        super(context);
    }

    public SpuareLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SpuareLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        getMeasuredHeight();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, UCCore.VERIFY_POLICY_QUICK);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }
}
