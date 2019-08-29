package com.autonavi.minimap.ajx3.widget.animator.evaluator;

import android.animation.TypeEvaluator;

public class StringEvaluator implements TypeEvaluator {
    public Object evaluate(float f, Object obj, Object obj2) {
        return f > 0.0f ? obj2 : obj;
    }
}
