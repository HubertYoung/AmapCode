package com.autonavi.minimap.route.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class RouteStrokeTextView extends TextView {
    private TextPaint strokePaint;

    public RouteStrokeTextView(Context context) {
        super(context);
    }

    public RouteStrokeTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RouteStrokeTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public RouteStrokeTextView(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.strokePaint == null) {
            this.strokePaint = new TextPaint();
        }
        TextPaint paint = getPaint();
        this.strokePaint.setTextSize(paint.getTextSize());
        this.strokePaint.setTypeface(paint.getTypeface());
        this.strokePaint.setFlags(paint.getFlags());
        this.strokePaint.setAlpha(paint.getAlpha());
        this.strokePaint.setStyle(Style.STROKE);
        this.strokePaint.setColor(getStrokeColor());
        this.strokePaint.setStrokeWidth(getStrokeWidth());
        String charSequence = getText().toString();
        canvas.drawText(charSequence, ((float) getWidth()) - this.strokePaint.measureText(charSequence), (float) getBaseline(), this.strokePaint);
        super.onDraw(canvas);
    }

    public float getStrokeWidth() {
        return (float) agn.a(getContext(), 1.0f);
    }

    public int getStrokeColor() {
        return getResources().getColor(R.color.f_c_1);
    }
}
