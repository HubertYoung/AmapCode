package com.autonavi.minimap.ajx3.widget.animator.interpolator;

import android.view.animation.Interpolator;

public class ReverseInterpolator implements Interpolator {
    public float getInterpolation(float f) {
        return Math.abs(f - 1.0f);
    }
}
