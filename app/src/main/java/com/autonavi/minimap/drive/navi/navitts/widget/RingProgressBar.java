package com.autonavi.minimap.drive.navi.navitts.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"IS2_INCONSISTENT_SYNC"})
public class RingProgressBar extends View {
    private static final int FILL = 1;
    private static final int STROKE = 0;
    private float mCurrentLength;
    private float mMaxLength;
    private RectF mOval;
    private Paint mPaint;
    private int mRingColor;
    private int mRingProgressColor;
    private float mRingWidth;
    private int mStyle;
    private int mTextProgressColor;
    private float mTextProgressSize;
    private boolean textIsDisplayable;

    public RingProgressBar(Context context) {
        this(context, null);
    }

    public RingProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RingProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RingProgressBar);
        this.mRingColor = obtainStyledAttributes.getColor(R.styleable.RingProgressBar_ringColor, SupportMenu.CATEGORY_MASK);
        this.mRingProgressColor = obtainStyledAttributes.getColor(R.styleable.RingProgressBar_ringProgressColor, -16711936);
        this.mTextProgressColor = obtainStyledAttributes.getColor(R.styleable.RingProgressBar_textProgressColor, -16711936);
        this.mTextProgressSize = obtainStyledAttributes.getDimension(R.styleable.RingProgressBar_textProgressSize, 15.0f);
        this.mRingWidth = obtainStyledAttributes.getDimension(R.styleable.RingProgressBar_ringWidth, 5.0f);
        this.mMaxLength = (float) obtainStyledAttributes.getInteger(R.styleable.RingProgressBar_maxLength, 100);
        this.textIsDisplayable = obtainStyledAttributes.getBoolean(R.styleable.RingProgressBar_ringTextIsDisplayable, true);
        this.mStyle = obtainStyledAttributes.getInt(R.styleable.RingProgressBar_ringStyle, 0);
        obtainStyledAttributes.recycle();
        this.mOval = new RectF();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        float f = (float) width;
        int i = (int) (f - (this.mRingWidth / 2.0f));
        this.mPaint.setColor(this.mRingColor);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(this.mRingWidth);
        this.mPaint.setAntiAlias(true);
        canvas.drawCircle(f, f, (float) i, this.mPaint);
        this.mPaint.setStrokeWidth(0.0f);
        this.mPaint.setColor(this.mTextProgressColor);
        this.mPaint.setTextSize(this.mTextProgressSize);
        this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        int i2 = (int) ((this.mCurrentLength / this.mMaxLength) * 100.0f);
        Paint paint = this.mPaint;
        StringBuilder sb = new StringBuilder();
        sb.append(i2);
        sb.append("%");
        float measureText = paint.measureText(sb.toString());
        if (this.textIsDisplayable && i2 != 0 && this.mStyle == 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i2);
            sb2.append("%");
            canvas.drawText(sb2.toString(), f - (measureText / 2.0f), f + (this.mTextProgressSize / 2.0f), this.mPaint);
        }
        this.mPaint.setStrokeWidth(this.mRingWidth);
        this.mPaint.setColor(this.mRingProgressColor);
        float f2 = (float) (width - i);
        float f3 = (float) (width + i);
        this.mOval.set(f2, f2, f3, f3);
        switch (this.mStyle) {
            case 0:
                this.mPaint.setStyle(Style.STROKE);
                canvas.drawArc(this.mOval, -90.0f, (this.mCurrentLength * 360.0f) / this.mMaxLength, false, this.mPaint);
                return;
            case 1:
                this.mPaint.setStyle(Style.FILL_AND_STROKE);
                if (this.mCurrentLength != 0.0f) {
                    canvas.drawArc(this.mOval, -90.0f, (this.mCurrentLength * 360.0f) / this.mMaxLength, true, this.mPaint);
                    break;
                }
                break;
        }
    }

    public synchronized float getMaxLength() {
        return this.mMaxLength;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0031, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setMaxLength(int r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 >= 0) goto L_0x0034
            boolean r0 = com.autonavi.ae.AEUtil.IS_DEBUG     // Catch:{ all -> 0x0032 }
            if (r0 == 0) goto L_0x0030
            ku r0 = defpackage.ku.a()     // Catch:{ all -> 0x0032 }
            java.lang.String r1 = "RingProgressBar"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0032 }
            java.lang.String r3 = "setCurrentLength   maxLength= "
            r2.<init>(r3)     // Catch:{ all -> 0x0032 }
            r2.append(r5)     // Catch:{ all -> 0x0032 }
            java.lang.String r5 = " Thread = "
            r2.append(r5)     // Catch:{ all -> 0x0032 }
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0032 }
            r2.append(r5)     // Catch:{ all -> 0x0032 }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x0032 }
            r0.c(r1, r5)     // Catch:{ all -> 0x0032 }
            java.lang.String r5 = "mCurrentLength not less than 0"
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)     // Catch:{ all -> 0x0032 }
        L_0x0030:
            monitor-exit(r4)
            return
        L_0x0032:
            r5 = move-exception
            goto L_0x0039
        L_0x0034:
            float r5 = (float) r5
            r4.mMaxLength = r5     // Catch:{ all -> 0x0032 }
            monitor-exit(r4)
            return
        L_0x0039:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.navi.navitts.widget.RingProgressBar.setMaxLength(int):void");
    }

    public synchronized float getCurrentLength() {
        return this.mCurrentLength;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0034, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setCurrentLength(float r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x0037
            boolean r0 = com.autonavi.ae.AEUtil.IS_DEBUG     // Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x0033
            ku r0 = defpackage.ku.a()     // Catch:{ all -> 0x0035 }
            java.lang.String r1 = "RingProgressBar"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0035 }
            java.lang.String r3 = "setCurrentLength   currentLength= "
            r2.<init>(r3)     // Catch:{ all -> 0x0035 }
            r2.append(r5)     // Catch:{ all -> 0x0035 }
            java.lang.String r5 = " Thread = "
            r2.append(r5)     // Catch:{ all -> 0x0035 }
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0035 }
            r2.append(r5)     // Catch:{ all -> 0x0035 }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x0035 }
            r0.c(r1, r5)     // Catch:{ all -> 0x0035 }
            java.lang.String r5 = "mCurrentLength not less than 0"
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)     // Catch:{ all -> 0x0035 }
        L_0x0033:
            monitor-exit(r4)
            return
        L_0x0035:
            r5 = move-exception
            goto L_0x004c
        L_0x0037:
            float r0 = r4.mMaxLength     // Catch:{ all -> 0x0035 }
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x003f
            float r5 = r4.mMaxLength     // Catch:{ all -> 0x0035 }
        L_0x003f:
            float r0 = r4.mMaxLength     // Catch:{ all -> 0x0035 }
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x004a
            r4.mCurrentLength = r5     // Catch:{ all -> 0x0035 }
            r4.postInvalidate()     // Catch:{ all -> 0x0035 }
        L_0x004a:
            monitor-exit(r4)
            return
        L_0x004c:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.navi.navitts.widget.RingProgressBar.setCurrentLength(float):void");
    }

    public int getTextProgressColor() {
        return this.mTextProgressColor;
    }

    public void setTextProgressColor(int i) {
        this.mTextProgressColor = i;
    }

    public float getTextProgressSize() {
        return this.mTextProgressSize;
    }

    public void setTextProgressSize(float f) {
        this.mTextProgressSize = f;
    }

    public float getRingWidth() {
        return this.mRingWidth;
    }

    public void setRingWidth(float f) {
        this.mRingWidth = f;
    }
}
