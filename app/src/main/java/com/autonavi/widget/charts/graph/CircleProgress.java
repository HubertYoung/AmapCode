package com.autonavi.widget.charts.graph;

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
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.widget.R;

public class CircleProgress extends View {
    private static final int DEFAULT_STROKE_WIDTH = 5;
    private static final int DEFAULT_WIDTH = 80;
    private Integer mBgColor;
    private PointF mCenter;
    private int mCircleBgColor;
    private int mCircleColor;
    private final int mDefaultBgColor;
    private final int mDefaultCircleColor;
    private final int mDefaultTextColor;
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
        this.mDefaultTextColor = -12303292;
        this.mRectF = new RectF();
        this.mCenter = new PointF();
        this.mPaint = new Paint();
        this.mDefaultTextSize = (float) eqp.a(context, 14);
        this.mStrokeWidth = eqp.a(context, 5);
        this.mDefaultWidth = eqp.a(context, 80);
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
        if (this.mTextSize != f) {
            this.mTextSize = f;
            if (this.mTextPaint != null) {
                this.mTextPaint.setTextSize(this.mTextSize);
                this.mLayout = null;
                requestLayout();
            }
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
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002b, code lost:
        if (r1 != 1073741824) goto L_0x001e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r8, int r9) {
        /*
            r7 = this;
            int r0 = android.view.View.MeasureSpec.getMode(r8)
            int r8 = android.view.View.MeasureSpec.getSize(r8)
            int r1 = android.view.View.MeasureSpec.getMode(r9)
            int r9 = android.view.View.MeasureSpec.getSize(r9)
            r2 = 1073741824(0x40000000, float:2.0)
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 == r3) goto L_0x0033
            if (r0 == r2) goto L_0x0029
            if (r1 == r3) goto L_0x0022
            if (r1 == r2) goto L_0x0020
            int r8 = r7.mDefaultWidth
        L_0x001e:
            r9 = r8
            goto L_0x004e
        L_0x0020:
            r8 = r9
            goto L_0x004e
        L_0x0022:
            int r8 = r7.mDefaultWidth
            int r8 = java.lang.Math.min(r8, r9)
            goto L_0x001e
        L_0x0029:
            if (r1 == r3) goto L_0x002e
            if (r1 == r2) goto L_0x004e
            goto L_0x001e
        L_0x002e:
            int r9 = java.lang.Math.min(r8, r9)
            goto L_0x004e
        L_0x0033:
            if (r1 == r3) goto L_0x0043
            if (r1 == r2) goto L_0x003e
            int r9 = r7.mDefaultWidth
            int r8 = java.lang.Math.min(r9, r8)
            goto L_0x001e
        L_0x003e:
            int r8 = java.lang.Math.min(r8, r9)
            goto L_0x004e
        L_0x0043:
            int r8 = java.lang.Math.min(r8, r9)
            int r9 = r7.mDefaultWidth
            int r8 = java.lang.Math.min(r9, r8)
            goto L_0x001e
        L_0x004e:
            int r0 = java.lang.Math.min(r8, r9)
            float r0 = (float) r0
            boolean r1 = r7.mHasStartPoint
            r2 = 1073741824(0x40000000, float:2.0)
            if (r1 == 0) goto L_0x0071
            float r1 = r7.getStartPointRadius()
            float r1 = r1 * r2
            int r3 = r7.mStrokeWidth
            float r3 = (float) r3
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x0071
            float r1 = r7.getStartPointRadius()
            float r1 = r1 * r2
            int r3 = r7.mStrokeWidth
            float r3 = (float) r3
            float r1 = r1 - r3
            float r0 = r0 - r1
        L_0x0071:
            r7.setMeasuredDimension(r8, r9)
            r1 = 0
            r3 = 4606101554889448489(0x3fec28f5c28f5c29, double:0.88)
            int r5 = r7.mStrokeWidth
            int r5 = r5 * 2
            float r5 = (float) r5
            float r5 = r0 - r5
            double r5 = (double) r5
            double r5 = r5 * r3
            int r3 = (int) r5
            int r1 = java.lang.Math.max(r1, r3)
            r7.ensureLayout(r1)
            android.graphics.RectF r1 = r7.mRectF
            float r8 = (float) r8
            float r3 = r8 - r0
            float r3 = r3 / r2
            int r4 = r7.mStrokeWidth
            int r4 = r4 / 2
            float r4 = (float) r4
            float r4 = r4 + r3
            float r9 = (float) r9
            float r0 = r9 - r0
            float r0 = r0 / r2
            int r5 = r7.mStrokeWidth
            int r5 = r5 / 2
            float r5 = (float) r5
            float r5 = r5 + r0
            float r8 = r8 - r3
            int r3 = r7.mStrokeWidth
            int r3 = r3 / 2
            float r3 = (float) r3
            float r8 = r8 - r3
            float r9 = r9 - r0
            int r0 = r7.mStrokeWidth
            int r0 = r0 / 2
            float r0 = (float) r0
            float r9 = r9 - r0
            r1.set(r4, r5, r8, r9)
            android.graphics.PointF r8 = r7.mCenter
            android.graphics.RectF r9 = r7.mRectF
            float r9 = r9.left
            android.graphics.RectF r0 = r7.mRectF
            float r0 = r0.right
            float r9 = r9 + r0
            float r9 = r9 / r2
            android.graphics.RectF r0 = r7.mRectF
            float r0 = r0.top
            android.graphics.RectF r1 = r7.mRectF
            float r1 = r1.bottom
            float r0 = r0 + r1
            float r0 = r0 / r2
            r8.set(r9, r0)
            android.graphics.RectF r8 = r7.mRectF
            float r8 = r8.right
            android.graphics.RectF r9 = r7.mRectF
            float r9 = r9.left
            float r8 = r8 - r9
            float r8 = r8 / r2
            r7.mRadius = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.widget.charts.graph.CircleProgress.onMeasure(int, int):void");
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
}
