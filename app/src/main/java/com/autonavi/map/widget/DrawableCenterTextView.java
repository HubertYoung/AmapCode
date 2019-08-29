package com.autonavi.map.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class DrawableCenterTextView extends TextView {
    public DrawableCenterTextView(Context context) {
        super(context);
    }

    public DrawableCenterTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DrawableCenterTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Drawable[] compoundDrawables = getCompoundDrawables();
        if (compoundDrawables != null) {
            Drawable drawable = compoundDrawables[0];
            if (drawable != null) {
                canvas.translate((((float) getWidth()) - ((getPaint().measureText(getText().toString()) + ((float) drawable.getIntrinsicWidth())) + ((float) getCompoundDrawablePadding()))) / 2.0f, 0.0f);
            }
        }
        super.onDraw(canvas);
    }
}
