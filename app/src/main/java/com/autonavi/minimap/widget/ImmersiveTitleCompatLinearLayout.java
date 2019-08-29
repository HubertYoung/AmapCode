package com.autonavi.minimap.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ImmersiveTitleCompatLinearLayout extends LinearLayout {
    public ImmersiveTitleCompatLinearLayout(Context context) {
        super(context);
        setPadding(getLeft(), getTop() + (euk.a() ? euk.a(getContext()) : 0), getRight(), getBottom());
    }

    public ImmersiveTitleCompatLinearLayout(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setPadding(getLeft(), getTop() + (euk.a() ? euk.a(getContext()) : 0), getRight(), getBottom());
    }

    public ImmersiveTitleCompatLinearLayout(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setPadding(getLeft(), getTop() + (euk.a() ? euk.a(getContext()) : 0), getRight(), getBottom());
    }

    public ImmersiveTitleCompatLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setPadding(getLeft(), getTop() + (euk.a() ? euk.a(getContext()) : 0), getRight(), getBottom());
    }
}
