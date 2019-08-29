package com.alipay.mobile.antui.load;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.antui.R;

public class LoadingAnimationView extends View {
    public static final int MODE_REFRESH = 1;
    public static final int MODE_SILENCE = 0;
    private Paint circlePaint = new Paint();
    private boolean firstLoading = false;
    private RectF innerCircleBounds = new RectF();
    private OnLoadingAppearedListener listener;
    private int mMaxProgress = 360;
    private int mProgress = 0;
    private int mode;
    private boolean paused = false;
    private Paint processPaint = new Paint();
    private int strokeWidth = 4;

    public LoadingAnimationView(Context context) {
        super(context);
        init();
    }

    public LoadingAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setLoadingAppearedListener(OnLoadingAppearedListener listener2) {
        this.listener = listener2;
    }

    public void setMode(int mode2) {
        this.mode = mode2;
        if (mode2 == 1) {
            this.mProgress = 0;
            this.firstLoading = true;
        }
        this.paused = false;
        invalidate();
    }

    public void pause() {
        this.paused = true;
    }

    public int getMode() {
        return this.mode;
    }

    private void init() {
        this.circlePaint.setColor(getResources().getColor(R.color.AU_COLOR22));
        this.circlePaint.setStrokeWidth((float) this.strokeWidth);
        this.circlePaint.setAntiAlias(true);
        this.circlePaint.setStyle(Style.STROKE);
        this.processPaint.setColor(getResources().getColor(R.color.AU_COLOR_LINK));
        this.processPaint.setStrokeWidth((float) this.strokeWidth);
        this.processPaint.setAntiAlias(true);
        this.processPaint.setStyle(Style.STROKE);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mode == 0) {
            canvas.drawArc(this.innerCircleBounds, -90.0f, 360.0f, false, this.circlePaint);
            canvas.drawArc(this.innerCircleBounds, -90.0f, 50.0f, false, this.processPaint);
        } else if (this.mode == 1) {
            canvas.drawArc(this.innerCircleBounds, -90.0f, 360.0f, false, this.circlePaint);
            canvas.drawArc(this.innerCircleBounds, ((((float) this.mProgress) / ((float) this.mMaxProgress)) * 360.0f) - 90.0f, 50.0f, false, this.processPaint);
            if (this.mProgress >= this.mMaxProgress) {
                this.mProgress = 0;
                if (this.firstLoading && this.listener != null) {
                    this.listener.appeared();
                    this.firstLoading = false;
                }
            } else {
                this.mProgress += 5;
            }
            if (!this.paused) {
                postInvalidateDelayed(10);
            }
        } else {
            super.onDraw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupBounds();
    }

    private void setupBounds() {
        this.innerCircleBounds = new RectF((float) this.strokeWidth, (float) this.strokeWidth, (float) (getWidth() - this.strokeWidth), (float) (getHeight() - this.strokeWidth));
    }
}
