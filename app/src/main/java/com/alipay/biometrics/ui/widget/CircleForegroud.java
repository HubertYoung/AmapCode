package com.alipay.biometrics.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.lang.ref.WeakReference;

public class CircleForegroud extends ImageView {
    private static final Xfermode SXFERMODE = new PorterDuffXfermode(Mode.XOR);
    protected Context mContext;
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    private WeakReference<Bitmap> mWeakBitmap;

    public CircleForegroud(Context context) {
        super(context);
        sharedConstructor();
    }

    public CircleForegroud(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        sharedConstructor();
    }

    public CircleForegroud(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        sharedConstructor();
    }

    private void sharedConstructor() {
        this.mPaint = new Paint(1);
    }

    public void invalidate() {
        this.mWeakBitmap = null;
        if (this.mMaskBitmap != null) {
            this.mMaskBitmap.recycle();
        }
        super.invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
            try {
                Bitmap bitmap = this.mWeakBitmap != null ? (Bitmap) this.mWeakBitmap.get() : null;
                if (bitmap == null || bitmap.isRecycled()) {
                    Drawable drawable = getDrawable();
                    if (drawable != null) {
                        try {
                            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
                            Canvas canvas2 = new Canvas(bitmap);
                            drawable.setBounds(0, 0, getWidth(), getHeight());
                            drawable.draw(canvas2);
                            if (this.mMaskBitmap == null || this.mMaskBitmap.isRecycled()) {
                                this.mMaskBitmap = createMask1();
                            }
                            this.mPaint.reset();
                            this.mPaint.setFilterBitmap(false);
                            this.mPaint.setXfermode(SXFERMODE);
                            canvas2.drawBitmap(this.mMaskBitmap, 0.0f, 0.0f, this.mPaint);
                            this.mWeakBitmap = new WeakReference<>(bitmap);
                        } catch (OutOfMemoryError e) {
                            BioLog.e((Throwable) e);
                            System.gc();
                            canvas.restoreToCount(saveLayer);
                            return;
                        }
                    }
                }
                if (bitmap != null) {
                    this.mPaint.setXfermode(null);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mPaint);
                    return;
                }
                canvas.restoreToCount(saveLayer);
            } catch (Exception e2) {
            } finally {
                canvas.restoreToCount(saveLayer);
            }
        } else {
            super.onDraw(canvas);
        }
    }

    public Bitmap createMask1() {
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setAntiAlias(true);
        paint.setColor(-1);
        float width = (float) getWidth();
        float height = (((float) getHeight()) - width) / 2.0f;
        canvas.drawOval(new RectF(0.0f, height, width, height + width), paint);
        return createBitmap;
    }
}
