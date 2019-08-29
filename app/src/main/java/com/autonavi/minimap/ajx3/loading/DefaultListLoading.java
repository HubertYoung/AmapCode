package com.autonavi.minimap.ajx3.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class DefaultListLoading extends View {
    private int mBackgroundColor = -1;
    private int mForegroundColor = -592138;
    private float mListCellHeight = 95.0f;
    private float mListCellImgHeight = 65.0f;
    private float mListCellImgWidth = 91.5f;
    private float mListCellMargin = 15.0f;
    private float mListTitleHeight = 17.5f;
    private Paint mPaint = new Paint();

    public DefaultListLoading(Context context) {
        super(context);
        init(context, null);
    }

    public DefaultListLoading(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public DefaultListLoading(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mListCellMargin = (float) agn.a(displayMetrics, 15.0f);
        this.mListCellHeight = (float) agn.a(displayMetrics, 95.0f);
        this.mListCellImgWidth = (float) agn.a(displayMetrics, 91.5f);
        this.mListCellImgHeight = (float) agn.a(displayMetrics, 65.0f);
        this.mListTitleHeight = (float) agn.a(displayMetrics, 17.5f);
        this.mPaint.setAntiAlias(true);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        this.mPaint.setColor(this.mBackgroundColor);
        float f = (float) width;
        float f2 = (float) height;
        canvas.drawRect(0.0f, 0.0f, f, f2, this.mPaint);
        this.mPaint.setColor(this.mForegroundColor);
        for (float f3 = 0.0f; f3 < f2; f3 += this.mListCellHeight) {
            float f4 = this.mListCellMargin;
            float f5 = f3 + ((this.mListCellHeight - this.mListCellImgHeight) * 0.5f);
            canvas.drawRect(f4, f5, f4 + this.mListCellImgWidth, f5 + this.mListCellImgHeight, this.mPaint);
            float f6 = (((this.mListCellHeight - this.mListCellMargin) - (this.mListTitleHeight * 2.0f)) * 0.5f) + f3;
            Canvas canvas2 = canvas;
            float f7 = f4 + this.mListCellImgWidth + this.mListCellMargin;
            canvas2.drawRect(f7, f6, f - this.mListCellMargin, f6 + this.mListTitleHeight, this.mPaint);
            float f8 = f6 + this.mListTitleHeight + this.mListCellMargin;
            canvas2.drawRect(f7, f8, f - this.mListCellMargin, f8 + this.mListTitleHeight, this.mPaint);
        }
    }
}
