package com.autonavi.bundle.amaphome.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TripToolSelectView extends RelativeLayout {
    public TripToolSelectView(Context context) {
        super(context);
        adjustSelf();
    }

    public TripToolSelectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        adjustSelf();
    }

    public TripToolSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        adjustSelf();
    }

    public TripToolSelectView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        adjustSelf();
    }

    private void adjustSelf() {
        setPadding(getPaddingLeft(), getPaddingTop() + ags.d(getContext()), getPaddingRight(), getPaddingBottom());
    }
}
