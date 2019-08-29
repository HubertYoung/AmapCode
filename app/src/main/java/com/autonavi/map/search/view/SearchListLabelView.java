package com.autonavi.map.search.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"EI_EXPOSE_REP2"})
public class SearchListLabelView extends View {
    private Paint mBorderPaint;
    private int mBorderRadius;
    private RectF mBorderRect = new RectF();
    private int mBorderWidth = 1;
    private b mItemInfo = new b();
    private int mLabelMargin;
    private int mMaxWidth;
    private int mOffset;
    private int mOutMarginLeft;
    private int mOutMarginRight;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private TextPaint mTextPaint;
    private int mTextSize;

    public static class a {
        public String a;
        public int b;
    }

    public static class b {
        a[] a = new a[0];
        int b;
    }

    public SearchListLabelView(Context context) {
        super(context);
        init(context);
    }

    public SearchListLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public SearchListLabelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mTextSize = agn.a(context, 11.0f);
        this.mLabelMargin = agn.a(context, 5.0f);
        this.mBorderRadius = agn.a(context, 2.0f);
        this.mPaddingBottom = agn.a(context, 4.0f);
        this.mPaddingTop = agn.a(context, 1.0f);
        this.mPaddingRight = agn.a(context, 5.0f);
        this.mPaddingLeft = agn.a(context, 5.0f);
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setStrokeWidth((float) this.mBorderWidth);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setStyle(Style.FILL);
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize((float) this.mTextSize);
        this.mOffset = (int) ((((float) this.mBorderWidth) / 2.0f) + 0.5f);
        this.mOutMarginRight = getResources().getDimensionPixelSize(R.dimen.default_margin_4A);
        this.mOutMarginLeft = 0;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        int i4 = this.mPaddingTop + this.mTextSize + this.mPaddingBottom + (this.mBorderWidth * 2) + this.mOffset + 1;
        int size = (MeasureSpec.getSize(i) - this.mOutMarginLeft) - this.mOutMarginRight;
        int i5 = 0;
        for (int i6 = 0; i6 < this.mItemInfo.a.length; i6++) {
            a aVar = this.mItemInfo.a[i6];
            if (i6 == 0) {
                i3 = 0;
            } else {
                i3 = this.mLabelMargin;
            }
            float measureTextWidth = measureTextWidth(aVar) + ((float) this.mPaddingLeft) + ((float) this.mPaddingRight);
            float f = (float) i5;
            float f2 = (float) i3;
            if (f + measureTextWidth + f2 + ((float) (this.mBorderWidth * 2)) < ((float) size)) {
                i5 = (int) (f + measureTextWidth + f2 + ((float) (this.mBorderWidth * 2)));
            }
        }
        this.mMaxWidth = Math.min(i5, size);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(this.mMaxWidth, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawLabels(canvas);
        super.onDraw(canvas);
    }

    private int getTextWidth(a aVar) {
        float[] fArr = new float[aVar.a.length()];
        this.mTextPaint.getTextWidths(aVar.a, fArr);
        int i = 0;
        for (float f : fArr) {
            i += (int) f;
        }
        return i;
    }

    private float measureTextWidth(a aVar) {
        if (TextUtils.isEmpty(aVar.a)) {
            return 0.0f;
        }
        return this.mTextPaint.measureText(aVar.a);
    }

    private void drawLabels(Canvas canvas) {
        int i;
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mItemInfo.a.length) {
            a aVar = this.mItemInfo.a[i2];
            if (i2 == 0) {
                i = 0;
            } else {
                i = this.mLabelMargin;
            }
            float measureTextWidth = measureTextWidth(aVar) + ((float) this.mPaddingLeft) + ((float) this.mPaddingRight);
            int i4 = this.mPaddingTop + this.mTextSize + this.mPaddingBottom;
            if (this.mItemInfo.b <= 0 || ((float) i3) + measureTextWidth + ((float) i) + ((float) (this.mBorderWidth * 2)) <= ((float) this.mItemInfo.b)) {
                float f = (float) i3;
                float f2 = (float) i;
                if (f + measureTextWidth + f2 + ((float) (this.mBorderWidth * 2)) <= ((float) this.mMaxWidth)) {
                    int i5 = i3 + i;
                    this.mBorderPaint.setColor(getResources().getColor(R.color.c_36_a));
                    this.mBorderRect.set((float) (this.mOffset + i5), (float) (this.mOffset + 1), ((float) (this.mOffset + i5)) + measureTextWidth, (float) (this.mOffset + 1 + i4));
                    canvas.drawRoundRect(this.mBorderRect, (float) this.mBorderRadius, (float) this.mBorderRadius, this.mBorderPaint);
                    this.mTextPaint.setColor(aVar.b);
                    canvas.drawText(aVar.a, (float) (this.mOffset + i5 + this.mPaddingLeft), (float) (this.mOffset + 1 + this.mTextSize + this.mPaddingTop), this.mTextPaint);
                    i3 = (int) (f + measureTextWidth + f2 + ((float) (this.mBorderWidth * 2)));
                    i2++;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void setItemInfo(b bVar) {
        this.mItemInfo = bVar;
        invalidate();
    }

    public b getItemInfo() {
        return this.mItemInfo;
    }
}
