package com.alipay.mobile.antui.picker;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/* compiled from: AUWheelView */
final class r extends Drawable {
    final /* synthetic */ AUWheelView a;

    r(AUWheelView this$0) {
        this.a = this$0;
    }

    public final void draw(Canvas canvas) {
        int[] areaBorder = this.a.obtainSelectedAreaBorder();
        canvas.drawLine((float) (this.a.viewWidth / 6), (float) areaBorder[0], (float) ((this.a.viewWidth * 5) / 6), (float) areaBorder[0], this.a.paint);
        canvas.drawLine((float) (this.a.viewWidth / 6), (float) areaBorder[1], (float) ((this.a.viewWidth * 5) / 6), (float) areaBorder[1], this.a.paint);
    }

    public final void setAlpha(int alpha) {
    }

    public final void setColorFilter(ColorFilter cf) {
    }

    public final int getOpacity() {
        return 0;
    }
}
