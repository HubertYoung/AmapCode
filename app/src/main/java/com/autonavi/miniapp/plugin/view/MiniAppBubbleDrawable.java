package com.autonavi.miniapp.plugin.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class MiniAppBubbleDrawable extends Drawable {
    private int mArrowDirection;
    private float mArrowHeight;
    private float mArrowPosition;
    private float mArrowWidth;
    private int mBubbleColor;
    private float mCornersRadius;
    private Paint mPaint = new Paint(1);
    private Path mPath = new Path();
    private RectF mRect;
    private int mStrokeColor;
    private Paint mStrokePaint;
    private Path mStrokePath;
    private float mStrokeWidth;

    public int getOpacity() {
        return -3;
    }

    public MiniAppBubbleDrawable(float f, float f2, float f3, float f4, int i, int i2, int i3) {
        this.mArrowWidth = f;
        this.mCornersRadius = f2;
        this.mArrowHeight = f3;
        this.mStrokeWidth = f4;
        this.mStrokeColor = i;
        this.mBubbleColor = i2;
        this.mArrowDirection = i3;
    }

    private void prepare() {
        if (this.mCornersRadius > this.mRect.width() - ((float) (this.mStrokeColor * 2))) {
            this.mCornersRadius = this.mRect.width() - ((float) (this.mStrokeColor * 2));
        }
        if (this.mCornersRadius > this.mRect.height() - (this.mStrokeWidth * 2.0f)) {
            this.mCornersRadius = this.mRect.height() - (this.mStrokeWidth * 2.0f);
        }
        this.mArrowPosition = (this.mRect.width() / 2.0f) - this.mArrowHeight;
        this.mPaint.setColor(this.mBubbleColor);
        if (this.mStrokeWidth > 0.0f) {
            this.mStrokePaint = new Paint(1);
            this.mStrokePaint.setColor(this.mStrokeColor);
            this.mStrokePath = new Path();
            initPath(this.mArrowDirection, this.mPath, this.mStrokeWidth);
            initPath(this.mArrowDirection, this.mStrokePath, 0.0f);
            return;
        }
        initPath(this.mArrowDirection, this.mPath, 0.0f);
    }

    public void draw(@NonNull Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        if (!rectF.equals(this.mRect)) {
            this.mRect = rectF;
            prepare();
        }
        if (this.mStrokeWidth > 0.0f) {
            canvas.drawPath(this.mStrokePath, this.mStrokePaint);
        }
        canvas.drawPath(this.mPath, this.mPaint);
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    private void initPath(int i, Path path, float f) {
        switch (i) {
            case 0:
                if (this.mCornersRadius <= 0.0f) {
                    initLeftSquarePath(this.mRect, path, f);
                    return;
                } else if (f <= 0.0f || f <= this.mCornersRadius) {
                    initLeftRoundedPath(this.mRect, path, f);
                    return;
                } else {
                    initLeftSquarePath(this.mRect, path, f);
                    return;
                }
            case 1:
                if (this.mCornersRadius <= 0.0f) {
                    initTopSquarePath(this.mRect, path, f);
                    return;
                } else if (f <= 0.0f || f <= this.mCornersRadius) {
                    initTopRoundedPath(this.mRect, path, f);
                    return;
                } else {
                    initTopSquarePath(this.mRect, path, f);
                    return;
                }
            case 2:
                if (this.mCornersRadius <= 0.0f) {
                    initRightSquarePath(this.mRect, path, f);
                    return;
                } else if (f <= 0.0f || f <= this.mCornersRadius) {
                    initRightRoundedPath(this.mRect, path, f);
                    return;
                } else {
                    initRightSquarePath(this.mRect, path, f);
                    return;
                }
            case 3:
                if (this.mCornersRadius <= 0.0f) {
                    initBottomSquarePath(this.mRect, path, f);
                    return;
                } else if (f <= 0.0f || f <= this.mCornersRadius) {
                    initBottomRoundedPath(this.mRect, path, f);
                    break;
                } else {
                    initBottomSquarePath(this.mRect, path, f);
                    return;
                }
        }
    }

    private void initLeftRoundedPath(RectF rectF, Path path, float f) {
        path.moveTo(this.mArrowWidth + rectF.left + this.mCornersRadius + f, rectF.top + f);
        path.lineTo((rectF.width() - this.mCornersRadius) - f, rectF.top + f);
        path.arcTo(new RectF(rectF.right - this.mCornersRadius, rectF.top + f, rectF.right - f, this.mCornersRadius + rectF.top), 270.0f, 90.0f);
        path.lineTo(rectF.right - f, (rectF.bottom - this.mCornersRadius) - f);
        path.arcTo(new RectF(rectF.right - this.mCornersRadius, rectF.bottom - this.mCornersRadius, rectF.right - f, rectF.bottom - f), 0.0f, 90.0f);
        path.lineTo(rectF.left + this.mArrowWidth + this.mCornersRadius + f, rectF.bottom - f);
        path.arcTo(new RectF(rectF.left + this.mArrowWidth + f, rectF.bottom - this.mCornersRadius, this.mCornersRadius + rectF.left + this.mArrowWidth, rectF.bottom - f), 90.0f, 90.0f);
        float f2 = f / 2.0f;
        path.lineTo(rectF.left + this.mArrowWidth + f, (this.mArrowHeight + this.mArrowPosition) - f2);
        path.lineTo(rectF.left + f + f, this.mArrowPosition + (this.mArrowHeight / 2.0f));
        path.lineTo(rectF.left + this.mArrowWidth + f, this.mArrowPosition + f2);
        path.lineTo(rectF.left + this.mArrowWidth + f, rectF.top + this.mCornersRadius + f);
        path.arcTo(new RectF(rectF.left + this.mArrowWidth + f, rectF.top + f, this.mCornersRadius + rectF.left + this.mArrowWidth, this.mCornersRadius + rectF.top), 180.0f, 90.0f);
        path.close();
    }

    private void initLeftSquarePath(RectF rectF, Path path, float f) {
        path.moveTo(this.mArrowWidth + rectF.left + f, rectF.top + f);
        path.lineTo(rectF.width() - f, rectF.top + f);
        path.lineTo(rectF.right - f, rectF.bottom - f);
        path.lineTo(rectF.left + this.mArrowWidth + f, rectF.bottom - f);
        float f2 = f / 2.0f;
        path.lineTo(rectF.left + this.mArrowWidth + f, (this.mArrowHeight + this.mArrowPosition) - f2);
        path.lineTo(rectF.left + f + f, this.mArrowPosition + (this.mArrowHeight / 2.0f));
        path.lineTo(rectF.left + this.mArrowWidth + f, this.mArrowPosition + f2);
        path.lineTo(rectF.left + this.mArrowWidth + f, rectF.top + f);
        path.close();
    }

    private void initTopRoundedPath(RectF rectF, Path path, float f) {
        path.moveTo(rectF.left + Math.min(this.mArrowPosition, this.mCornersRadius) + f, rectF.top + this.mArrowHeight + f);
        float f2 = f / 2.0f;
        path.lineTo(rectF.left + this.mArrowPosition + f2, rectF.top + this.mArrowHeight + f);
        path.lineTo(rectF.left + (this.mArrowWidth / 2.0f) + this.mArrowPosition, rectF.top + f + f);
        path.lineTo(((rectF.left + this.mArrowWidth) + this.mArrowPosition) - f2, rectF.top + this.mArrowHeight + f);
        path.lineTo((rectF.right - this.mCornersRadius) - f, rectF.top + this.mArrowHeight + f);
        path.arcTo(new RectF(rectF.right - this.mCornersRadius, rectF.top + this.mArrowHeight + f, rectF.right - f, this.mCornersRadius + rectF.top + this.mArrowHeight), 270.0f, 90.0f);
        path.lineTo(rectF.right - f, (rectF.bottom - this.mCornersRadius) - f);
        path.arcTo(new RectF(rectF.right - this.mCornersRadius, rectF.bottom - this.mCornersRadius, rectF.right - f, rectF.bottom - f), 0.0f, 90.0f);
        path.lineTo(rectF.left + this.mCornersRadius + f, rectF.bottom - f);
        path.arcTo(new RectF(rectF.left + f, rectF.bottom - this.mCornersRadius, this.mCornersRadius + rectF.left, rectF.bottom - f), 90.0f, 90.0f);
        path.lineTo(rectF.left + f, rectF.top + this.mArrowHeight + this.mCornersRadius + f);
        path.arcTo(new RectF(rectF.left + f, rectF.top + this.mArrowHeight + f, this.mCornersRadius + rectF.left, this.mCornersRadius + rectF.top + this.mArrowHeight), 180.0f, 90.0f);
        path.close();
    }

    private void initTopSquarePath(RectF rectF, Path path, float f) {
        path.moveTo(rectF.left + this.mArrowPosition + f, rectF.top + this.mArrowHeight + f);
        float f2 = f / 2.0f;
        path.lineTo(rectF.left + this.mArrowPosition + f2, rectF.top + this.mArrowHeight + f);
        path.lineTo(rectF.left + (this.mArrowWidth / 2.0f) + this.mArrowPosition, rectF.top + f + f);
        path.lineTo(((rectF.left + this.mArrowWidth) + this.mArrowPosition) - f2, rectF.top + this.mArrowHeight + f);
        path.lineTo(rectF.right - f, rectF.top + this.mArrowHeight + f);
        path.lineTo(rectF.right - f, rectF.bottom - f);
        path.lineTo(rectF.left + f, rectF.bottom - f);
        path.lineTo(rectF.left + f, rectF.top + this.mArrowHeight + f);
        path.lineTo(rectF.left + this.mArrowPosition + f, rectF.top + this.mArrowHeight + f);
        path.close();
    }

    private void initRightRoundedPath(RectF rectF, Path path, float f) {
        path.moveTo(rectF.left + this.mCornersRadius + f, rectF.top + f);
        path.lineTo(((rectF.width() - this.mCornersRadius) - this.mArrowWidth) - f, rectF.top + f);
        path.arcTo(new RectF((rectF.right - this.mCornersRadius) - this.mArrowWidth, rectF.top + f, (rectF.right - this.mArrowWidth) - f, this.mCornersRadius + rectF.top), 270.0f, 90.0f);
        float f2 = f / 2.0f;
        path.lineTo((rectF.right - this.mArrowWidth) - f, this.mArrowPosition + f2);
        path.lineTo((rectF.right - f) - f, this.mArrowPosition + (this.mArrowHeight / 2.0f));
        path.lineTo((rectF.right - this.mArrowWidth) - f, (this.mArrowPosition + this.mArrowHeight) - f2);
        path.lineTo((rectF.right - this.mArrowWidth) - f, (rectF.bottom - this.mCornersRadius) - f);
        path.arcTo(new RectF((rectF.right - this.mCornersRadius) - this.mArrowWidth, rectF.bottom - this.mCornersRadius, (rectF.right - this.mArrowWidth) - f, rectF.bottom - f), 0.0f, 90.0f);
        path.lineTo(rectF.left + this.mArrowWidth + f, rectF.bottom - f);
        path.arcTo(new RectF(rectF.left + f, rectF.bottom - this.mCornersRadius, this.mCornersRadius + rectF.left, rectF.bottom - f), 90.0f, 90.0f);
        path.arcTo(new RectF(rectF.left + f, rectF.top + f, this.mCornersRadius + rectF.left, this.mCornersRadius + rectF.top), 180.0f, 90.0f);
        path.close();
    }

    private void initRightSquarePath(RectF rectF, Path path, float f) {
        path.moveTo(rectF.left + f, rectF.top + f);
        path.lineTo((rectF.width() - this.mArrowWidth) - f, rectF.top + f);
        float f2 = f / 2.0f;
        path.lineTo((rectF.right - this.mArrowWidth) - f, this.mArrowPosition + f2);
        path.lineTo((rectF.right - f) - f, this.mArrowPosition + (this.mArrowHeight / 2.0f));
        path.lineTo((rectF.right - this.mArrowWidth) - f, (this.mArrowPosition + this.mArrowHeight) - f2);
        path.lineTo((rectF.right - this.mArrowWidth) - f, rectF.bottom - f);
        path.lineTo(rectF.left + f, rectF.bottom - f);
        path.lineTo(rectF.left + f, rectF.top + f);
        path.close();
    }

    private void initBottomRoundedPath(RectF rectF, Path path, float f) {
        path.moveTo(rectF.left + this.mCornersRadius + f, rectF.top + f);
        path.lineTo((rectF.width() - this.mCornersRadius) - f, rectF.top + f);
        path.arcTo(new RectF(rectF.right - this.mCornersRadius, rectF.top + f, rectF.right - f, this.mCornersRadius + rectF.top), 270.0f, 90.0f);
        path.lineTo(rectF.right - f, ((rectF.bottom - this.mArrowHeight) - this.mCornersRadius) - f);
        path.arcTo(new RectF(rectF.right - this.mCornersRadius, (rectF.bottom - this.mCornersRadius) - this.mArrowHeight, rectF.right - f, (rectF.bottom - this.mArrowHeight) - f), 0.0f, 90.0f);
        float f2 = f / 2.0f;
        path.lineTo(((rectF.left + this.mArrowWidth) + this.mArrowPosition) - f2, (rectF.bottom - this.mArrowHeight) - f);
        path.lineTo(rectF.left + this.mArrowPosition + (this.mArrowWidth / 2.0f), (rectF.bottom - f) - f);
        path.lineTo(rectF.left + this.mArrowPosition + f2, (rectF.bottom - this.mArrowHeight) - f);
        path.lineTo(rectF.left + Math.min(this.mCornersRadius, this.mArrowPosition) + f, (rectF.bottom - this.mArrowHeight) - f);
        path.arcTo(new RectF(rectF.left + f, (rectF.bottom - this.mCornersRadius) - this.mArrowHeight, this.mCornersRadius + rectF.left, (rectF.bottom - this.mArrowHeight) - f), 90.0f, 90.0f);
        path.lineTo(rectF.left + f, rectF.top + this.mCornersRadius + f);
        path.arcTo(new RectF(rectF.left + f, rectF.top + f, this.mCornersRadius + rectF.left, this.mCornersRadius + rectF.top), 180.0f, 90.0f);
        path.close();
    }

    private void initBottomSquarePath(RectF rectF, Path path, float f) {
        path.moveTo(rectF.left + f, rectF.top + f);
        path.lineTo(rectF.right - f, rectF.top + f);
        path.lineTo(rectF.right - f, (rectF.bottom - this.mArrowHeight) - f);
        float f2 = f / 2.0f;
        path.lineTo(((rectF.left + this.mArrowWidth) + this.mArrowPosition) - f2, (rectF.bottom - this.mArrowHeight) - f);
        path.lineTo(rectF.left + this.mArrowPosition + (this.mArrowWidth / 2.0f), (rectF.bottom - f) - f);
        path.lineTo(rectF.left + this.mArrowPosition + f2, (rectF.bottom - this.mArrowHeight) - f);
        path.lineTo(rectF.left + this.mArrowPosition + f, (rectF.bottom - this.mArrowHeight) - f);
        path.lineTo(rectF.left + f, (rectF.bottom - this.mArrowHeight) - f);
        path.lineTo(rectF.left + f, rectF.top + f);
        path.close();
    }
}
