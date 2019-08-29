package com.autonavi.map.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.reflect.Field;

public class StrokeTextView extends TextView {
    private int mColor = -16777216;
    private int mShadowColor = 0;
    private float mShadowRadius = 0.0f;
    private int mStrokeWidth = 3;

    public StrokeTextView(Context context) {
        super(context);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setTextColor(int i) {
        super.setTextColor(i);
        this.mColor = i;
    }

    public void setShadowColor(int i) {
        this.mShadowColor = i;
        invalidate();
    }

    public void setmShadowRadius(float f) {
        this.mShadowRadius = f;
        invalidate();
    }

    public void setStrokeWidth(int i) {
        this.mStrokeWidth = i;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        setTextColorUseReflection(-1);
        getPaint().setStrokeWidth((float) this.mStrokeWidth);
        getPaint().setStyle(Style.FILL_AND_STROKE);
        getPaint().setFakeBoldText(true);
        getPaint().setShadowLayer(this.mShadowRadius, -2.0f, -1.0f, this.mShadowColor);
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTextColorUseReflection(this.mColor);
        getPaint().setStrokeWidth(0.0f);
        getPaint().setStyle(Style.FILL_AND_STROKE);
        getPaint().setFakeBoldText(false);
        getPaint().setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        super.onDraw(canvas);
    }

    private void setTextColorUseReflection(int i) {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCurTextColor");
            declaredField.setAccessible(true);
            declaredField.set(this, Integer.valueOf(i));
            declaredField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e4) {
            e4.printStackTrace();
        }
    }
}
