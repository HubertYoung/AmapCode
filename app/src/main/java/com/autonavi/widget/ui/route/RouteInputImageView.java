package com.autonavi.widget.ui.route;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RouteInputImageView extends ImageView {
    public RouteInputImageView(Context context) {
        super(context);
    }

    public RouteInputImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RouteInputImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setAlpha(float f) {
        super.setAlpha(f);
        setEnabled(f > 0.8f);
    }

    public int getVisibility() {
        return getAlpha() == 1.0f ? 0 : 8;
    }

    public void setVisibility(int i) {
        setAlpha(i == 0 ? 1.0f : 0.0f);
    }
}
