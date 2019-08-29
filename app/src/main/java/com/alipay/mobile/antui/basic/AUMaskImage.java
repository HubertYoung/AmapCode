package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import com.alipay.mobile.antui.R;

public abstract class AUMaskImage extends AUImageView {
    private static final Xfermode MASK_XFERMODE = new PorterDuffXfermode(Mode.SRC_IN);
    private static final Matrix defaut_matrix = new Matrix();
    private static PaintFlagsDrawFilter drawFilter = new PaintFlagsDrawFilter(0, 3);
    protected boolean isCreateMask = true;
    private Bitmap mask;
    private int originalHeight = 0;
    private int originalWidth = 0;
    private Paint paint;

    public abstract Bitmap createMask(int i, int i2);

    public AUMaskImage(Context paramContext) {
        super(paramContext);
        initStyle(paramContext, null);
    }

    public AUMaskImage(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initStyle(paramContext, paramAttributeSet);
    }

    public AUMaskImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initStyle(paramContext, paramAttributeSet);
    }

    public void setNeedMask(boolean needMask) {
        this.isCreateMask = needMask;
    }

    private void initStyle(Context paramContext, AttributeSet paramAttributeSet) {
        if (paramAttributeSet != null) {
            TypedArray a = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MaskImage);
            this.isCreateMask = a.getBoolean(0, true);
            a.recycle();
        }
        this.paint = new Paint();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas paramCanvas) {
        if (!this.isCreateMask) {
            super.onDraw(paramCanvas);
            return;
        }
        Drawable localDrawable = getDrawable();
        if (localDrawable == null) {
            Log.e("AntUI-build", "MaskImage onDraw Exception: localDrawable is null");
            super.onDraw(paramCanvas);
            return;
        }
        try {
            int width = getWidth();
            int height = getHeight();
            int layerId = paramCanvas.saveLayer(0.0f, 0.0f, (float) width, (float) height, null, 31);
            if (this.mask == null || this.mask.isRecycled() || this.originalWidth != width || this.originalHeight != height) {
                this.mask = createMask(width, height);
                this.originalHeight = height;
                this.originalWidth = width;
            }
            paramCanvas.drawBitmap(this.mask, defaut_matrix, this.paint);
            this.paint.setXfermode(MASK_XFERMODE);
            DrawFilter drawFilter2 = paramCanvas.getDrawFilter();
            paramCanvas.setDrawFilter(drawFilter);
            paramCanvas.drawBitmap(drawableToBitmap(localDrawable), getImageMatrix(), this.paint);
            paramCanvas.setDrawFilter(drawFilter2);
            this.paint.setXfermode(null);
            paramCanvas.restoreToCount(layerId);
        } catch (Exception localException) {
            super.onDraw(paramCanvas);
            Log.e("AntUI-build", "MaskImage onDraw Exception: ", localException);
        }
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int w = getWidth();
        int h = getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
