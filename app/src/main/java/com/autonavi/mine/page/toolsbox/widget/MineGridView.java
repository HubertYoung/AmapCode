package com.autonavi.mine.page.toolsbox.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class MineGridView extends GridView {
    public MineGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MineGridView(Context context) {
        super(context);
    }

    public MineGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }
}
