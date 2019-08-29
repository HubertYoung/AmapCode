package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.antui.utils.DensityUtil;

public class NumberProgressBar extends View {
    private static final int CORNER_SIZE = 8;
    private static final int FONT_SIZE_DP = 14;
    private static int FONT_SIZE_PX = 0;
    private static final float MAX_PROGRESS = 100.0f;
    private static final int PROGRESS_COLOR = Color.parseColor("#80108EE9");
    private static final int TEXT_COLOR = Color.parseColor("#FFFFFF");
    private float baseline;
    private String mDispString;
    private Paint mPaint;
    private RectF mProgressRect;
    private RectF mRect;

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDispString = "";
        this.baseline = -1.0f;
        this.mPaint = new Paint();
        this.mPaint.setDither(true);
        FONT_SIZE_PX = DensityUtil.dip2px(context, 14.0f);
    }

    private void paintProgress(Canvas canvas) {
        this.mPaint.setColor(PROGRESS_COLOR);
        this.mPaint.setStyle(Style.FILL);
        if (this.mProgressRect != null) {
            canvas.drawRoundRect(this.mProgressRect, 8.0f, 8.0f, this.mPaint);
        }
    }

    private void paintProgressText(Canvas canvas) {
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setTextSize((float) FONT_SIZE_PX);
        FontMetricsInt fontMetrics = this.mPaint.getFontMetricsInt();
        if (this.baseline < 0.0f) {
            this.baseline = (this.mRect.top + ((((this.mRect.bottom - this.mRect.top) - ((float) fontMetrics.bottom)) + ((float) fontMetrics.top)) / 2.0f)) - ((float) fontMetrics.top);
        }
        this.mPaint.setTextAlign(Align.CENTER);
        this.mPaint.setColor(TEXT_COLOR);
        canvas.drawText(this.mDispString, ((float) getWidth()) / 2.0f, this.baseline, this.mPaint);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mRect == null) {
            this.mRect = new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        }
        paintProgress(canvas);
        paintProgressText(canvas);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar(Context context) {
        this(context, null, 0);
    }

    public NumberProgressBar setProgress(int progress) {
        if (!(getWidth() == 0 || getHeight() == 0)) {
            if (((float) progress) > MAX_PROGRESS) {
                progress = 100;
            }
            if (this.mProgressRect == null) {
                this.mProgressRect = new RectF(0.0f, 0.0f, 0.0f, (float) getHeight());
            }
            this.mProgressRect.right = (((float) progress) / MAX_PROGRESS) * ((float) getWidth());
            invalidate();
        }
        return this;
    }

    public NumberProgressBar setText(String text) {
        this.mDispString = text;
        invalidate();
        return this;
    }
}
