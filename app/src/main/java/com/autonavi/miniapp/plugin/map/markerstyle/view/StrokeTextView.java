package com.autonavi.miniapp.plugin.map.markerstyle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.Arrays;

public class StrokeTextView extends TextView {
    private static final int HORIZENTAL = 0;
    private boolean gradientChanged;
    private LinearGradient mGradient;
    private int[] mGradientColor;
    private int mGradientOrientation;
    private TextPaint mPaint;
    private int mStrokeColor = -1;
    private int mStrokeWidth;
    private int mTextColor;

    public StrokeTextView(Context context) {
        super(context);
        init(null);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        this.mPaint = getPaint();
        if (attributeSet != null) {
            this.mStrokeColor = -1;
            this.mStrokeWidth = 0;
            this.mGradientOrientation = 0;
            setStrokeColor(this.mStrokeColor);
            setStrokeWidth(this.mStrokeWidth);
            setGradientOrientation(this.mGradientOrientation);
        }
    }

    public void setGradientOrientation(int i) {
        if (this.mGradientOrientation != i) {
            this.mGradientOrientation = i;
            this.gradientChanged = true;
            invalidate();
        }
    }

    public void setGradientColor(int[] iArr) {
        if (!Arrays.equals(iArr, this.mGradientColor)) {
            this.mGradientColor = iArr;
            this.gradientChanged = true;
            invalidate();
        }
    }

    public void setStrokeColor(int i) {
        if (this.mStrokeColor != i) {
            this.mStrokeColor = i;
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mStrokeWidth > 0) {
            this.mTextColor = getCurrentTextColor();
            this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
            this.mPaint.setFakeBoldText(true);
            this.mPaint.setShadowLayer((float) this.mStrokeWidth, 0.0f, 0.0f, 0);
            this.mPaint.setStyle(Style.FILL_AND_STROKE);
            setColor(this.mStrokeColor);
            this.mPaint.setShader(null);
            super.onDraw(canvas);
            if (this.gradientChanged) {
                if (this.mGradientColor != null) {
                    this.mGradient = getGradient();
                }
                this.gradientChanged = false;
            }
            if (this.mGradient != null) {
                this.mPaint.setShader(this.mGradient);
                this.mPaint.setColor(-1);
            } else {
                setColor(this.mTextColor);
            }
            this.mPaint.setStrokeWidth(0.0f);
            this.mPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        }
        super.onDraw(canvas);
    }

    public void setStrokeWidth(int i) {
        this.mStrokeWidth = i;
        invalidate();
    }

    private LinearGradient getGradient() {
        if (this.mGradientOrientation == 0) {
            LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, (float) getWidth(), 0.0f, this.mGradientColor, null, TileMode.CLAMP);
            return linearGradient;
        }
        LinearGradient linearGradient2 = new LinearGradient(0.0f, 0.0f, 0.0f, (float) getHeight(), this.mGradientColor, null, TileMode.CLAMP);
        return linearGradient2;
    }

    private void setColor(int i) {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCurTextColor");
            declaredField.setAccessible(true);
            declaredField.set(this, Integer.valueOf(i));
            declaredField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mPaint.setColor(i);
    }
}
