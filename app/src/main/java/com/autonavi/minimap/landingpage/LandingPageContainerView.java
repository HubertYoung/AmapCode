package com.autonavi.minimap.landingpage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class LandingPageContainerView extends RelativeLayout {
    public LandingPageContainerView(Context context) {
        super(context);
        adjustSelf();
    }

    public LandingPageContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        adjustSelf();
    }

    public LandingPageContainerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        adjustSelf();
    }

    public LandingPageContainerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        adjustSelf();
    }

    private void adjustSelf() {
        setPadding(getPaddingLeft(), getPaddingTop() + ags.d(getContext()), getPaddingRight(), getPaddingBottom());
    }
}
