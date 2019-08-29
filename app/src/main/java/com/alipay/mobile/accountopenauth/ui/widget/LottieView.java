package com.alipay.mobile.accountopenauth.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LottieView extends View {
    private Paint a;
    private Path b;

    public LottieView(Context context) {
        super(context);
        a();
    }

    public LottieView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public LottieView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        this.a = new Paint();
        this.a.setAntiAlias(true);
        this.a.setStyle(Style.FILL);
        this.a.setColor(Color.parseColor("#108ee9"));
        this.b = new Path();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float f = (float) (width / 2);
        this.b.moveTo(f, 0.0f);
        this.b.lineTo((float) width, 0.0f);
        float f2 = (float) height;
        this.b.lineTo(f, f2);
        this.b.lineTo(0.0f, f2);
        this.b.close();
        canvas.drawPath(this.b, this.a);
    }
}
