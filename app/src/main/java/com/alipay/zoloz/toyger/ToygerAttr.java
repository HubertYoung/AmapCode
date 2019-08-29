package com.alipay.zoloz.toyger;

import android.graphics.RectF;

public interface ToygerAttr {
    float brightness();

    short distance();

    float gaussian();

    boolean hasTarget();

    float integrity();

    float motion();

    float quality();

    RectF region();
}
