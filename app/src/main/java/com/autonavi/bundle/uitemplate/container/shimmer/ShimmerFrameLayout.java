package com.autonavi.bundle.uitemplate.container.shimmer;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.container.shimmer.Shimmer.a;
import com.autonavi.bundle.uitemplate.container.shimmer.Shimmer.c;
import com.autonavi.minimap.R;

public class ShimmerFrameLayout extends RelativeLayout {
    private final Paint mContentPaint = new Paint();
    private final bdt mShimmerDrawable = new bdt();

    public ShimmerFrameLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ShimmerFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public ShimmerFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    @TargetApi(21)
    public ShimmerFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context, attributeSet);
    }

    private void init(Context context, @Nullable AttributeSet attributeSet) {
        setWillNotDraw(false);
        this.mShimmerDrawable.setCallback(this);
        if (attributeSet == null) {
            setShimmer(new a().b());
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ShimmerFrameLayoutXX, 0, 0);
        try {
            setShimmer(((!obtainStyledAttributes.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_colored) || !obtainStyledAttributes.getBoolean(R.styleable.ShimmerFrameLayoutXX_shimmer_colored, false)) ? new a() : new c()).a(obtainStyledAttributes).b());
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public ShimmerFrameLayout setShimmer(@Nullable Shimmer shimmer) {
        boolean z;
        bdt bdt = this.mShimmerDrawable;
        bdt.d = shimmer;
        if (bdt.d != null) {
            bdt.b.setXfermode(new PorterDuffXfermode(bdt.d.q ? Mode.DST_IN : Mode.SRC_IN));
        }
        bdt.c();
        if (bdt.d != null) {
            if (bdt.c != null) {
                z = bdt.c.isStarted();
                bdt.c.cancel();
                bdt.c.removeAllUpdateListeners();
            } else {
                z = false;
            }
            bdt.c = ValueAnimator.ofFloat(new float[]{0.0f, ((float) (bdt.d.u / bdt.d.t)) + 1.0f});
            bdt.c.setRepeatMode(bdt.d.s);
            bdt.c.setRepeatCount(bdt.d.r);
            bdt.c.setDuration(bdt.d.t + bdt.d.u);
            bdt.c.addUpdateListener(bdt.a);
            if (z) {
                bdt.c.start();
            }
        }
        bdt.invalidateSelf();
        if (shimmer == null || !shimmer.o) {
            setLayerType(0, null);
        } else {
            setLayerType(2, this.mContentPaint);
        }
        return this;
    }

    public void startShimmer() {
        bdt bdt = this.mShimmerDrawable;
        if (bdt.c != null && !bdt.a() && bdt.getCallback() != null) {
            bdt.c.start();
        }
    }

    public void stopShimmer() {
        bdt bdt = this.mShimmerDrawable;
        if (bdt.c != null && bdt.a()) {
            bdt.c.cancel();
        }
    }

    public boolean isShimmerStarted() {
        return this.mShimmerDrawable.a();
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mShimmerDrawable.setBounds(0, 0, getWidth(), getHeight());
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mShimmerDrawable.b();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopShimmer();
    }

    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mShimmerDrawable.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(@NonNull Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mShimmerDrawable;
    }
}
