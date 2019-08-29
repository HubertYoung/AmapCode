package com.alipay.mobile.antui.picker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUAddImageView extends AUTextView {
    private final Paint mPaint = new Paint();
    private float midLineWidth = 2.0f;
    private int strokeWidth = 1;

    public AUAddImageView(Context context) {
        super(context);
        setDensity(context);
    }

    private void setDensity(Context context) {
        this.strokeWidth = DensityUtil.dip2px(context, (float) this.strokeWidth);
        this.midLineWidth = (float) DensityUtil.dip2px(context, this.midLineWidth);
        AuiLogger.info("setDensity", "setDensity strokeWidth:" + this.strokeWidth + "   midLineWidth:" + this.midLineWidth);
    }

    public AUAddImageView(Context context, AttributeSet set) {
        super(context, set);
        setDensity(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setColor(Color.parseColor("#cccccc"));
        this.mPaint.setStrokeWidth((float) this.strokeWidth);
        canvas.drawRect(new Rect(this.strokeWidth, this.strokeWidth, getWidth() - this.strokeWidth, getHeight() - this.strokeWidth), this.mPaint);
        this.mPaint.setStrokeWidth(this.midLineWidth);
        int innerHeight = getWidth();
        canvas.drawLine((float) ((innerHeight * 54) / 228), (float) (innerHeight / 2), (float) ((innerHeight * 174) / 228), (float) (innerHeight / 2), this.mPaint);
        canvas.drawLine((float) (innerHeight / 2), (float) ((innerHeight * 54) / 228), (float) (innerHeight / 2), (float) ((innerHeight * 174) / 228), this.mPaint);
    }
}
