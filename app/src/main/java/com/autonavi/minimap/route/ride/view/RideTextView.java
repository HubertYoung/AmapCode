package com.autonavi.minimap.route.ride.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

public class RideTextView extends View {
    private Typeface mType;
    private float strWidth = 0.0f;
    private String text = "";
    private Paint textPaint;
    private float textSize;

    public RideTextView(Context context) {
        super(context);
        init();
    }

    public RideTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public RideTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        if (MeasureSpec.getSize(i) == -1) {
            i3 = getResources().getDisplayMetrics().widthPixels;
        } else {
            i3 = (int) Math.ceil((double) (this.strWidth + (getResources().getDisplayMetrics().density * 6.0f)));
        }
        setMeasuredDimension(i3, (int) Math.ceil((double) (this.textSize + (getResources().getDisplayMetrics().density * 5.0f))));
    }

    @SuppressLint({"NewApi"})
    @TargetApi(21)
    public RideTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
        if (this.textPaint == null) {
            this.textPaint = new Paint();
            this.textSize = getResources().getDisplayMetrics().density * 12.0f;
        }
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1));
        if (this.mType == null) {
            this.mType = erp.a(AMapPageUtil.getAppContext()).a((String) "regular.ttf");
        }
        this.textPaint.setTypeface(this.mType);
        this.strWidth = this.textPaint.measureText(this.text);
    }

    public void setTextSize(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("argument is not right!!!");
        }
        this.textSize = f * getResources().getDisplayMetrics().density;
        init();
        requestLayout();
        invalidate();
    }

    public void setText(String str) {
        if (str == null) {
            throw new IllegalArgumentException("argument is not right!!!");
        }
        this.text = str;
        init();
        requestLayout();
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.mType = typeface;
        init();
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(this.text, (((float) getMeasuredWidth()) - this.strWidth) / 2.0f, this.textSize + (getResources().getDisplayMetrics().density * 2.0f), this.textPaint);
    }
}
