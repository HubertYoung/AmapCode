package com.autonavi.minimap.basemap.traffic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class TrafficGridView extends GridView {
    public TrafficGridView(Context context) {
        super(context);
    }

    public TrafficGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TrafficGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }
}
