package com.alipay.mobile.tinyappcommon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundCornerImageView extends ImageView {
    private float a;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = 0.0f;
    }

    public void setRoundSize(float roundSize) {
        this.a = roundSize;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        clipPath.addRoundRect(new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight()), this.a, this.a, Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
