package com.autonavi.minimap.auidebugger.boommenu;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;

public class Bar extends View {
    public Bar(Context context) {
        this(context, null);
    }

    public Bar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackgroundResource(R.drawable.bar);
    }

    public void setColor(int i) {
        ((GradientDrawable) getBackground()).setColor(i);
    }
}
