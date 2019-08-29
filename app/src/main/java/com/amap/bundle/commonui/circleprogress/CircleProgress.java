package com.amap.bundle.commonui.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;

public class CircleProgress extends View {
    private static final int DEFAULT_STROKE_WIDTH = 5;
    private static final int DEFAULT_WIDTH = 80;
    private Integer mBgColor;
    private PointF mCenter;
    private int mCircleBgColor;
    private int mCircleColor;
    private final int mDefaultBgColor;
    private final int mDefaultCircleColor;
    private final float mDefaultTextSize;
    private int mDefaultWidth;
    private Paint mFillPaint;
    private boolean mHasStartPoint;
    private StaticLayout mLayout;
    private Paint mPaint;
    private float mPercent;
    private float mRadius;
    private RectF mRectF;
    private Integer mStartPointColor;
    private Integer mStartPointRadius;
    private int mStrokeWidth;
    private String mText;
    private int mTextColor;
    private TextPaint mTextPaint;
    private float mTextSize;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleProgress(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDefaultCircleColor = Color.rgb(66, 145, FavoritesPointFragment.REQUEST_HOME);
        this.mDefaultBgColor = Color.rgb(204, 204, 204);
        this.mRectF = new RectF();
        this.mCenter = new PointF();
        this.mPaint = new Paint();
        this.mDefaultTextSize = (float) dip2px(context, 14);
        this.mStrokeWidth = dip2px(context, 5);
        this.mDefaultWidth = dip2px(context, 80);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CircleProgress, i, 0);
        initByAttributes(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        initPainters();
    }

    /* access modifiers changed from: protected */
    public void initByAttributes(TypedArray typedArray) {
        this.mCircleColor = typedArray.getColor(R.styleable.CircleProgress_circle_color, this.mDefaultCircleColor);
        this.mCircleBgColor = typedArray.getColor(R.styleable.CircleProgress_circle_bg_color, this.mDefaultBgColor);
        this.mTextColor = typedArray.getColor(R.styleable.CircleProgress_circle_text_color, -12303292);
        this.mTextSize = typedArray.getDimension(R.styleable.CircleProgress_circle_text_size, this.mDefaultTextSize);
        this.mStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.CircleProgress_circle_stroke_width, this.mStrokeWidth);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        setPercent(typedArray.getFloat(R.styleable.CircleProgress_circle_percent, 0.0f));
    }

    /* access modifiers changed from: protected */
    public void initPainters() {
        if (this.mTextPaint == null) {
            this.mTextPaint = new TextPaint();
            this.mTextPaint.setAntiAlias(true);
            this.mTextPaint.setDither(true);
        }
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextSize(this.mTextSize);
    }

    public void invalidate() {
        super.invalidate();
    }

    public float getPercent() {
        return this.mPercent;
    }

    public void setPercent(float f) {
        if (this.mPercent != f) {
            this.mPercent = f;
            if (this.mPercent < 0.0f) {
                this.mPercent = 0.0f;
            }
            if (this.mPercent > 100.0f) {
                this.mPercent = 100.0f;
            }
            invalidate();
        }
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setStrokeWidth(int i) {
        if (this.mStrokeWidth != i && i >= 0) {
            this.mStrokeWidth = i;
            invalidate();
        }
    }

    public void setBgColor(int i) {
        this.mBgColor = Integer.valueOf(i);
        invalidate();
    }

    public void setHasStartPoint(boolean z) {
        if (this.mHasStartPoint != z) {
            this.mHasStartPoint = z;
            requestLayout();
        }
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String str) {
        this.mText = str;
        this.mLayout = null;
        invalidate();
        requestLayout();
    }

    private void ensureLayout(int i) {
        if (TextUtils.isEmpty(this.mText) || i <= 0) {
            this.mLayout = null;
            return;
        }
        if (this.mLayout == null) {
            initPainters();
            StaticLayout staticLayout = new StaticLayout(this.mText, this.mTextPaint, i, Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            this.mLayout = staticLayout;
            int textSize = (int) this.mTextPaint.getTextSize();
            while (this.mLayout.getHeight() > i && textSize > 1) {
                textSize -= 2;
                this.mTextPaint.setTextSize((float) textSize);
                StaticLayout staticLayout2 = new StaticLayout(this.mText, this.mTextPaint, i, Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
                this.mLayout = staticLayout2;
            }
        }
    }

    public int getCircleColor() {
        return this.mCircleColor;
    }

    public void setCircleColor(int i) {
        this.mCircleColor = i;
        invalidate();
    }

    public int getCircleBgColor() {
        return this.mCircleBgColor;
    }

    public void setCircleBgColor(int i) {
        this.mCircleBgColor = i;
        invalidate();
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public void setTextSize(float f) {
        if (this.mTextSize != f && this.mTextPaint != null) {
            this.mTextSize = f;
            this.mTextPaint.setTextSize(this.mTextSize);
            this.mLayout = null;
            requestLayout();
        }
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setTextColor(int i) {
        this.mTextColor = i;
        this.mTextPaint.setColor(i);
        invalidate();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0032, code lost:
        if (r1 != 1073741824) goto L_0x0023;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r9, int r10) {
        /*
            r8 = this;
            int r0 = android.view.View.MeasureSpec.getMode(r9)
            int r9 = android.view.View.MeasureSpec.getSize(r9)
            int r1 = android.view.View.MeasureSpec.getMode(r10)
            int r10 = android.view.View.MeasureSpec.getSize(r10)
            r2 = 0
            r3 = 1073741824(0x40000000, float:2.0)
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 == r4) goto L_0x003a
            if (r0 == 0) goto L_0x004c
            if (r0 == r3) goto L_0x002e
            if (r1 == r4) goto L_0x0027
            if (r1 == 0) goto L_0x004c
            if (r1 == r3) goto L_0x0025
            int r9 = r8.mDefaultWidth
        L_0x0023:
            r10 = r9
            goto L_0x005a
        L_0x0025:
            r9 = r10
            goto L_0x005a
        L_0x0027:
            int r9 = r8.mDefaultWidth
            int r9 = java.lang.Math.min(r9, r10)
            goto L_0x0023
        L_0x002e:
            if (r1 == r4) goto L_0x0035
            if (r1 == 0) goto L_0x004d
            if (r1 == r3) goto L_0x005a
            goto L_0x0023
        L_0x0035:
            int r10 = java.lang.Math.min(r9, r10)
            goto L_0x005a
        L_0x003a:
            if (r1 == r4) goto L_0x004f
            if (r1 == 0) goto L_0x004c
            if (r1 == r3) goto L_0x0047
            int r10 = r8.mDefaultWidth
            int r9 = java.lang.Math.min(r10, r9)
            goto L_0x0023
        L_0x0047:
            int r9 = java.lang.Math.min(r9, r10)
            goto L_0x005a
        L_0x004c:
            r9 = 0
        L_0x004d:
            r10 = 0
            goto L_0x005a
        L_0x004f:
            int r9 = java.lang.Math.min(r9, r10)
            int r10 = r8.mDefaultWidth
            int r9 = java.lang.Math.min(r10, r9)
            goto L_0x0023
        L_0x005a:
            int r0 = java.lang.Math.min(r9, r10)
            float r0 = (float) r0
            boolean r1 = r8.mHasStartPoint
            r3 = 1073741824(0x40000000, float:2.0)
            if (r1 == 0) goto L_0x007d
            float r1 = r8.getStartPointRadius()
            float r1 = r1 * r3
            int r4 = r8.mStrokeWidth
            float r4 = (float) r4
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 <= 0) goto L_0x007d
            float r1 = r8.getStartPointRadius()
            float r1 = r1 * r3
            int r4 = r8.mStrokeWidth
            float r4 = (float) r4
            float r1 = r1 - r4
            float r0 = r0 - r1
        L_0x007d:
            r8.setMeasuredDimension(r9, r10)
            r4 = 4606101554889448489(0x3fec28f5c28f5c29, double:0.88)
            int r1 = r8.mStrokeWidth
            int r1 = r1 * 2
            float r1 = (float) r1
            float r1 = r0 - r1
            double r6 = (double) r1
            double r6 = r6 * r4
            int r1 = (int) r6
            int r1 = java.lang.Math.max(r2, r1)
            r8.ensureLayout(r1)
            android.graphics.RectF r1 = r8.mRectF
            float r9 = (float) r9
            float r2 = r9 - r0
            float r2 = r2 / r3
            int r4 = r8.mStrokeWidth
            int r4 = r4 / 2
            float r4 = (float) r4
            float r4 = r4 + r2
            float r10 = (float) r10
            float r0 = r10 - r0
            float r0 = r0 / r3
            int r5 = r8.mStrokeWidth
            int r5 = r5 / 2
            float r5 = (float) r5
            float r5 = r5 + r0
            float r9 = r9 - r2
            int r2 = r8.mStrokeWidth
            int r2 = r2 / 2
            float r2 = (float) r2
            float r9 = r9 - r2
            float r10 = r10 - r0
            int r0 = r8.mStrokeWidth
            int r0 = r0 / 2
            float r0 = (float) r0
            float r10 = r10 - r0
            r1.set(r4, r5, r9, r10)
            android.graphics.PointF r9 = r8.mCenter
            android.graphics.RectF r10 = r8.mRectF
            float r10 = r10.left
            android.graphics.RectF r0 = r8.mRectF
            float r0 = r0.right
            float r10 = r10 + r0
            float r10 = r10 / r3
            android.graphics.RectF r0 = r8.mRectF
            float r0 = r0.top
            android.graphics.RectF r1 = r8.mRectF
            float r1 = r1.bottom
            float r0 = r0 + r1
            float r0 = r0 / r3
            r9.set(r10, r0)
            android.graphics.RectF r9 = r8.mRectF
            float r9 = r9.right
            android.graphics.RectF r10 = r8.mRectF
            float r10 = r10.left
            float r9 = r9 - r10
            float r9 = r9 / r3
            r8.mRadius = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.commonui.circleprogress.CircleProgress.onMeasure(int, int):void");
    }

    private float getStartPointRadius() {
        return (float) (this.mStartPointRadius != null ? this.mStartPointRadius.intValue() : this.mStrokeWidth);
    }

    public void setStartPointRadius(int i) {
        this.mStartPointRadius = Integer.valueOf(i);
        if (this.mHasStartPoint) {
            requestLayout();
        }
    }

    private int getStartPointColor() {
        return this.mStartPointColor == null ? getCircleColor() : this.mStartPointColor.intValue();
    }

    public void setStartPointColor(int i) {
        this.mStartPointColor = Integer.valueOf(i);
        if (this.mHasStartPoint) {
            invalidate();
        }
    }

    private void setupFillPaint() {
        if (this.mFillPaint == null) {
            this.mFillPaint = new Paint();
            this.mFillPaint.setAntiAlias(true);
            this.mFillPaint.setDither(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawBackground(canvas);
        this.mPaint.setColor(getCircleBgColor());
        this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
        canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, this.mPaint);
        this.mPaint.setColor(getCircleColor());
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.mRectF, 270.0f, this.mPercent * 3.6f, false, this.mPaint);
        drawStartPoint(canvas);
        drawText(canvas);
    }

    private void drawStartPoint(Canvas canvas) {
        if (this.mHasStartPoint) {
            double d = ((((double) this.mPercent) * 3.141592653589793d) / 50.0d) + 4.71238898038469d;
            float cos = this.mCenter.x + (((float) Math.cos(d)) * this.mRadius);
            float sin = this.mCenter.y + (((float) Math.sin(d)) * this.mRadius);
            float startPointRadius = getStartPointRadius();
            setupFillPaint();
            this.mFillPaint.setColor(getStartPointColor());
            canvas.drawCircle(cos, sin, startPointRadius, this.mFillPaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        if (this.mBgColor != null) {
            setupFillPaint();
            this.mFillPaint.setColor(this.mBgColor.intValue());
            canvas.drawCircle(this.mCenter.x, this.mCenter.y, Math.max(1.0f, this.mRadius), this.mFillPaint);
        }
    }

    private void drawText(Canvas canvas) {
        if (this.mLayout != null) {
            canvas.save();
            canvas.translate((float) ((getMeasuredWidth() - this.mLayout.getWidth()) / 2), (float) ((getMeasuredHeight() - this.mLayout.getHeight()) / 2));
            this.mLayout.draw(canvas);
            canvas.restore();
        }
    }

    private int dip2px(Context context, int i) {
        return (int) ((((float) i) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
