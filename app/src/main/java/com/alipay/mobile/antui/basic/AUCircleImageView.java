package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class AUCircleImageView extends AUMaskImage {
    public AUCircleImageView(Context paramContext) {
        super(paramContext);
    }

    public AUCircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public AUCircleImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public Bitmap createMask(int width, int height) {
        Bitmap localBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint(1);
        localPaint.setColor(-7829368);
        localCanvas.drawCircle((float) (width / 2), (float) (width / 2), (float) (width / 2), localPaint);
        return localBitmap;
    }
}
