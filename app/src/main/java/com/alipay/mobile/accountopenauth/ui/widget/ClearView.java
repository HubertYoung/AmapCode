package com.alipay.mobile.accountopenauth.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class ClearView extends View {
    private Paint a;
    private Context b;

    public ClearView(Context context) {
        this(context, null);
    }

    public ClearView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = getContext();
        this.a = new Paint();
        this.a.setAntiAlias(true);
        this.a.setStyle(Style.FILL_AND_STROKE);
        this.a.setColor(Color.parseColor("#999999"));
    }

    public void setColor(int i) {
        this.a.setColor(i);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float a2 = (float) a(this.b, 13.0f);
        float width = ((float) (getWidth() / 2)) - (a2 / 2.0f);
        float f = a2 + width;
        Canvas canvas2 = canvas;
        float f2 = width;
        float f3 = f;
        canvas2.drawLine(f2, width, f3, f, this.a);
        canvas2.drawLine(f2, f, f3, width, this.a);
        super.onDraw(canvas);
    }

    private static int a(Context context, float f) {
        int i = 0;
        if (context == null) {
            return 0;
        }
        try {
            i = (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Throwable unused) {
        }
        return i;
    }
}
