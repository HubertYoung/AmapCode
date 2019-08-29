package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CaptureV2MaskView extends View {
    private static final float DEFAULT_CORNER_SIDE_LEN_DP = 20.0f;
    private static final float DEFAULT_CORNER_SIDE_SIZE_DP = 3.0f;
    private int cornerSideLen;
    private int cornerSideSize;
    private boolean mEnableCorner = true;
    private boolean mEnableRectBound = true;
    private boolean mEnableShadowBg = true;
    private MaskOptions mOptions;
    private Paint mPaint;

    public static class MaskOptions {
        public int maskColor = -936234446;
        public Rect rect;
        public boolean showCorner = true;
        public int strokeColor = Integer.MAX_VALUE;
        public int strokeWidth = 4;

        public MaskOptions(Rect rect2) {
            this.rect = rect2;
        }

        public String toString() {
            return "MaskOptions{rect=" + this.rect + ", maskColor=" + this.maskColor + ", strokeColor=" + this.strokeColor + ", strokeWidth=" + this.strokeWidth + ", showCorner=" + this.showCorner + '}';
        }
    }

    public CaptureV2MaskView(Context context) {
        super(context);
    }

    public CaptureV2MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CaptureV2MaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CaptureV2MaskView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void updateStyle(boolean corner, boolean shadowBg, boolean rectBound) {
        this.mEnableCorner = corner;
        this.mEnableShadowBg = shadowBg;
        this.mEnableRectBound = rectBound;
        invalidate();
    }

    public void setMaskOptions(MaskOptions options) {
        this.mOptions = options;
        calcCornerSide();
        postInvalidate();
    }

    private void calcCornerSide() {
        this.cornerSideLen = (int) (20.5f * getResources().getDisplayMetrics().density);
        this.cornerSideSize = (int) (3.5f * getResources().getDisplayMetrics().density);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        initPaint();
        drawMask(canvas);
        super.onDraw(canvas);
    }

    private void initPaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
    }

    private void drawMask(Canvas canvas) {
        if (this.mOptions != null) {
            if (this.mEnableShadowBg) {
                drawOutsideMask(canvas);
            }
            if (this.mEnableRectBound) {
                drawStrokeRect(canvas);
            }
            if (this.mEnableCorner) {
                drawRectCorner(canvas);
            }
        }
    }

    private void drawRectCorner(Canvas canvas) {
        if (this.mOptions.showCorner && this.cornerSideLen > 0) {
            Rect rect = this.mOptions.rect;
            this.mPaint.setColor(-1);
            canvas.drawRect((float) rect.left, (float) rect.top, (float) (rect.left + this.cornerSideSize), (float) (rect.top + this.cornerSideLen), this.mPaint);
            canvas.drawRect((float) rect.left, (float) rect.top, (float) (rect.left + this.cornerSideLen), (float) (rect.top + this.cornerSideSize), this.mPaint);
            canvas.drawRect((float) rect.left, (float) (rect.bottom - this.cornerSideLen), (float) (rect.left + this.cornerSideSize), (float) rect.bottom, this.mPaint);
            canvas.drawRect((float) rect.left, (float) (rect.bottom - this.cornerSideSize), (float) (rect.left + this.cornerSideLen), (float) rect.bottom, this.mPaint);
            canvas.drawRect((float) (rect.right - this.cornerSideSize), (float) rect.top, (float) rect.right, (float) (rect.top + this.cornerSideLen), this.mPaint);
            canvas.drawRect((float) (rect.right - this.cornerSideLen), (float) rect.top, (float) rect.right, (float) (rect.top + this.cornerSideSize), this.mPaint);
            canvas.drawRect((float) (rect.right - this.cornerSideSize), (float) (rect.bottom - this.cornerSideLen), (float) rect.right, (float) rect.bottom, this.mPaint);
            canvas.drawRect((float) (rect.right - this.cornerSideLen), (float) (rect.bottom - this.cornerSideSize), (float) rect.right, (float) rect.bottom, this.mPaint);
        }
    }

    private void drawStrokeRect(Canvas canvas) {
        Rect rect = this.mOptions.rect;
        this.mPaint.setColor(this.mOptions.maskColor);
        canvas.drawRect(0.0f, 0.0f, (float) rect.left, (float) getHeight(), this.mPaint);
        canvas.drawRect((float) rect.left, 0.0f, (float) rect.right, (float) rect.top, this.mPaint);
        canvas.drawRect((float) rect.right, 0.0f, (float) getWidth(), (float) getHeight(), this.mPaint);
        canvas.drawRect((float) rect.left, (float) rect.bottom, (float) rect.right, (float) getHeight(), this.mPaint);
    }

    private void drawOutsideMask(Canvas canvas) {
        Rect rect = this.mOptions.rect;
        this.mPaint.setColor(this.mOptions.strokeColor);
        int width = this.mOptions.strokeWidth;
        this.mPaint.setStrokeWidth((float) width);
        canvas.drawLine((float) rect.left, (float) rect.top, (float) rect.left, (float) rect.bottom, this.mPaint);
        canvas.drawLine((float) (rect.left + (width / 2)), (float) rect.top, (float) (rect.right - (width / 2)), (float) rect.top, this.mPaint);
        canvas.drawLine((float) rect.right, (float) rect.top, (float) rect.right, (float) rect.bottom, this.mPaint);
        canvas.drawLine((float) (rect.left + (width / 2)), (float) rect.bottom, (float) (rect.right - (width / 2)), (float) rect.bottom, this.mPaint);
    }
}
