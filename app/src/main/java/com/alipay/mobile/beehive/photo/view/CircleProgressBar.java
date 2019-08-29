package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.beehive.R;

public class CircleProgressBar extends View {
    private int max = 100;
    private int outRadius;
    private Paint paint;
    private RectF pieProgressOval;
    private int progress = -1;
    private int progressBackground = 0;
    private int progressColor = -1;
    private int radius = -1;
    private int stockWidth;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.outRadius = context.getResources().getDimensionPixelOffset(R.dimen.video_pregress_radius);
        this.radius = this.outRadius - DensityUtil.dip2px(context, 3.0f);
        this.progressBackground = 0;
        this.progressColor = -1;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.stockWidth = DensityUtil.dip2px(context, 1.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.radius != -1 && getVisibility() == 0) {
            drawInnerCircle(canvas);
            drawPieProgress(canvas);
            drawOuterCircle(canvas);
        }
    }

    private void drawOuterCircle(Canvas canvas) {
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeWidth((float) this.stockWidth);
        canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) this.outRadius, this.paint);
    }

    private void drawPieProgress(Canvas canvas) {
        float angle = 0.0f;
        if (this.max > 0 && this.progress >= 0) {
            angle = (((float) this.progress) / ((float) this.max)) * 360.0f;
        }
        this.paint.setColor(this.progressColor);
        updatePieProgressOval();
        canvas.drawArc(this.pieProgressOval, 270.0f, angle, true, this.paint);
    }

    private void drawInnerCircle(Canvas canvas) {
        this.paint.setColor(this.progressBackground);
        this.paint.setStyle(Style.FILL);
        canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) this.radius, this.paint);
    }

    private void updatePieProgressOval() {
        float l = (((float) getWidth()) - ((float) (this.radius * 2))) / 2.0f;
        float t = (((float) getHeight()) - (((float) this.radius) * 2.0f)) / 2.0f;
        float r = ((float) getWidth()) - ((((float) getWidth()) - ((float) (this.radius * 2))) / 2.0f);
        float b = ((float) getHeight()) - ((((float) getHeight()) - (((float) this.radius) * 2.0f)) / 2.0f);
        if (this.pieProgressOval == null) {
            this.pieProgressOval = new RectF(l, t, r, b);
            return;
        }
        this.pieProgressOval.left = l;
        this.pieProgressOval.top = t;
        this.pieProgressOval.right = r;
        this.pieProgressOval.bottom = b;
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0) {
            invalidate();
        }
    }

    public void setRadius(int radius2) {
        this.radius = radius2;
    }

    public void setRadius(int outRadius2, int innerRadius) {
        this.outRadius = outRadius2;
        this.radius = innerRadius;
        invalidate();
    }

    public void setProgressBackground(int progressBackground2) {
        this.progressBackground = progressBackground2;
    }

    public void setProgressColor(int progressColor2) {
        this.progressColor = progressColor2;
    }

    public void setMax(int max2) {
        this.max = max2;
    }

    public void setProgress(int progress2) {
        this.progress = progress2;
        invalidate();
    }

    public void safeSetProgress(final int progress2) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            post(new Runnable() {
                public final void run() {
                    CircleProgressBar.this.setProgress(progress2);
                }
            });
        } else {
            setProgress(progress2);
        }
    }
}
