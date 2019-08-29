package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class RealtimeStrokeTextView extends TextView {
    private int mStrokeColor = -1;
    private TextPaint mStrokePaint;
    private int mStrokeWidth = 3;

    public RealtimeStrokeTextView(Context context) {
        super(context);
    }

    public RealtimeStrokeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RealtimeStrokeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setStrokeWidth(int i) {
        this.mStrokeWidth = i;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mStrokePaint == null) {
            this.mStrokePaint = new TextPaint();
        }
        TextPaint paint = getPaint();
        this.mStrokePaint.setTextSize(paint.getTextSize());
        this.mStrokePaint.setTypeface(paint.getTypeface());
        this.mStrokePaint.setFlags(paint.getFlags());
        this.mStrokePaint.setAlpha(paint.getAlpha());
        this.mStrokePaint.setStyle(Style.STROKE);
        this.mStrokePaint.setColor(this.mStrokeColor);
        this.mStrokePaint.setStrokeWidth((float) this.mStrokeWidth);
        String charSequence = getText().toString();
        canvas.drawText(charSequence, (((float) getWidth()) - this.mStrokePaint.measureText(charSequence)) / 2.0f, (float) getBaseline(), this.mStrokePaint);
        super.onDraw(canvas);
    }
}
