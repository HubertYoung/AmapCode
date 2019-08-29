package com.autonavi.minimap.auidebugger.boommenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.autonavi.minimap.R;

public class ShadowLayout extends FrameLayout {
    private int OriginalShadowColor;
    private int ShadowColor;
    private float mCornerRadius;
    private float mDx;
    private float mDy;
    private boolean mForceInvalidateShadow = false;
    private boolean mInvalidateShadowOnSizeChanged = true;
    private float mShadowRadius;

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        return 0;
    }

    public ShadowLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context, attributeSet);
    }

    public ShadowLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i > 0 && i2 > 0) {
            if (getBackground() == null || this.mInvalidateShadowOnSizeChanged || this.mForceInvalidateShadow) {
                this.mForceInvalidateShadow = false;
                setBackgroundCompat(i, i2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mForceInvalidateShadow) {
            this.mForceInvalidateShadow = false;
            setBackgroundCompat(i3 - i, i4 - i2);
        }
    }

    public void setInvalidateShadowOnSizeChanged(boolean z) {
        this.mInvalidateShadowOnSizeChanged = z;
    }

    public void invalidateShadow() {
        this.mForceInvalidateShadow = true;
        requestLayout();
        invalidate();
    }

    private void initView(Context context, AttributeSet attributeSet) {
        initAttributes(context, attributeSet);
        int abs = (int) (this.mShadowRadius + Math.abs(this.mDx));
        int abs2 = (int) (this.mShadowRadius + Math.abs(this.mDy));
        setPadding(abs, abs2, abs, abs2);
    }

    private void setBackgroundCompat(int i, int i2) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), createShadowBitmap(i, i2, this.mCornerRadius, this.mShadowRadius, this.mDx, this.mDy, this.ShadowColor, 0));
        if (VERSION.SDK_INT <= 16) {
            setBackgroundDrawable(bitmapDrawable);
        } else {
            setBackground(bitmapDrawable);
        }
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = getTypedArray(context, attributeSet, R.styleable.ShadowLayout);
        if (typedArray != null) {
            try {
                this.mCornerRadius = typedArray.getDimension(R.styleable.ShadowLayout_sl_cornerRadius, getResources().getDimension(R.dimen.default_corner_radius));
                this.mShadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_sl_shadowRadius, getResources().getDimension(R.dimen.default_shadow_radius));
                this.mDx = typedArray.getDimension(R.styleable.ShadowLayout_sl_dx, 0.0f);
                this.mDy = typedArray.getDimension(R.styleable.ShadowLayout_sl_dy, 0.0f);
                int color = typedArray.getColor(R.styleable.ShadowLayout_sl_shadowColor, ContextCompat.getColor(context, R.color.default_shadow_color));
                this.ShadowColor = color;
                this.OriginalShadowColor = color;
            } finally {
                typedArray.recycle();
            }
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] iArr) {
        return context.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    private Bitmap createShadowBitmap(int i, int i2, float f, float f2, float f3, float f4, int i3, int i4) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ALPHA_8);
        Canvas canvas = new Canvas(createBitmap);
        RectF rectF = new RectF(f2, f2, ((float) i) - f2, ((float) i2) - f2);
        if (f4 > 0.0f) {
            rectF.top += f4;
            rectF.bottom -= f4;
        } else if (f4 < 0.0f) {
            rectF.top += Math.abs(f4);
            rectF.bottom -= Math.abs(f4);
        }
        if (f3 > 0.0f) {
            rectF.left += f3;
            rectF.right -= f3;
        } else if (f3 < 0.0f) {
            rectF.left += Math.abs(f3);
            rectF.right -= Math.abs(f3);
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i4);
        paint.setStyle(Style.FILL);
        if (!isInEditMode()) {
            paint.setShadowLayer(f2, f3, f4, i3);
        }
        canvas.drawRoundRect(rectF, f, f, paint);
        return createBitmap;
    }

    public void setShadowColor(int i) {
        this.ShadowColor = i;
        invalidate();
    }

    public int getOriginalShadowColor() {
        return this.OriginalShadowColor;
    }

    public int getShadowColor() {
        return this.ShadowColor;
    }

    public void setmShadowRadius(float f) {
        this.mShadowRadius = f;
    }

    public void setmCornerRadius(float f) {
        this.mCornerRadius = f;
        invalidate();
    }

    public void setmDx(float f) {
        this.mDx = f;
        invalidate();
    }

    public void setmDy(float f) {
        this.mDy = f;
        invalidate();
    }
}
