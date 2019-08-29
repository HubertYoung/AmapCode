package com.autonavi.minimap.auidebugger.boommenu;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;

public class Dot extends View {
    public Dot(Context context) {
        this(context, null);
    }

    public Dot(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackgroundResource(R.drawable.dot);
    }

    public void setColor(int i) {
        ((GradientDrawable) getBackground()).setColor(i);
    }
}
