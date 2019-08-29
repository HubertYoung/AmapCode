package com.autonavi.minimap.life.common.widget.view.autosize;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public abstract class AutoSizeLayout extends RelativeLayout {
    private Bundle mLayoutParams;
    private Bundle mMeasureParams;

    /* access modifiers changed from: protected */
    public abstract don initLayoutPolicy(int i, int i2, Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract doo initMeasurePolicy(int i, int i2, Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract Bundle layoutInitParams(Context context, AttributeSet attributeSet);

    /* access modifiers changed from: protected */
    public abstract Bundle measureInitParams(Context context, AttributeSet attributeSet);

    public AutoSizeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayoutParams = layoutInitParams(context, attributeSet);
        this.mMeasureParams = measureInitParams(context, attributeSet);
    }

    public AutoSizeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutParams = layoutInitParams(context, attributeSet);
        this.mMeasureParams = measureInitParams(context, attributeSet);
    }

    public AutoSizeLayout(Context context) {
        super(context);
        this.mLayoutParams = layoutInitParams(context, null);
        this.mMeasureParams = measureInitParams(context, null);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mMeasureParams != null) {
            int measuredWidth = getMeasuredWidth();
            int i3 = 0;
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                View childAt = getChildAt(i4);
                if (!(childAt == null || childAt.getVisibility() == 8)) {
                    i3 += childAt.getMeasuredWidth();
                }
            }
            initMeasurePolicy(measuredWidth, i3, this.mMeasureParams);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mLayoutParams != null) {
            int width = getWidth();
            int i5 = 0;
            for (int i6 = 0; i6 < getChildCount(); i6++) {
                View childAt = getChildAt(i6);
                if (!(childAt == null || childAt.getVisibility() == 8)) {
                    i5 += childAt.getWidth();
                }
            }
            initLayoutPolicy(width, i5, this.mLayoutParams);
        }
    }
}
