package defpackage;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.autonavi.bundle.uitemplate.container.shimmer.Shimmer;

/* renamed from: bdt reason: default package */
/* compiled from: ShimmerDrawable */
public final class bdt extends Drawable {
    public final AnimatorUpdateListener a = new AnimatorUpdateListener() {
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            bdt.this.invalidateSelf();
        }
    };
    public final Paint b = new Paint();
    @Nullable
    public ValueAnimator c;
    @Nullable
    public Shimmer d;
    private final Rect e = new Rect();
    private final Matrix f = new Matrix();

    public final void setAlpha(int i) {
    }

    public final void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public bdt() {
        this.b.setAntiAlias(true);
    }

    public final boolean a() {
        return this.c != null && this.c.isStarted();
    }

    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.e.set(0, 0, rect.width(), rect.height());
        c();
        b();
    }

    public final void draw(@NonNull Canvas canvas) {
        float f2;
        float f3;
        if (this.d != null && this.b.getShader() != null) {
            float tan = (float) Math.tan(Math.toRadians((double) this.d.n));
            float height = ((float) this.e.height()) + (((float) this.e.width()) * tan);
            float width = ((float) this.e.width()) + (tan * ((float) this.e.height()));
            float f4 = 0.0f;
            float animatedFraction = this.c != null ? this.c.getAnimatedFraction() : 0.0f;
            switch (this.d.d) {
                case 1:
                    float f5 = -height;
                    f3 = f5 + ((height - f5) * animatedFraction);
                    break;
                case 2:
                    f2 = width + (((-width) - width) * animatedFraction);
                    break;
                case 3:
                    f3 = height + (((-height) - height) * animatedFraction);
                    break;
                default:
                    float f6 = -width;
                    f2 = f6 + ((width - f6) * animatedFraction);
                    break;
            }
            f4 = f3;
            f2 = 0.0f;
            this.f.reset();
            this.f.setRotate(this.d.n, ((float) this.e.width()) / 2.0f, ((float) this.e.height()) / 2.0f);
            this.f.postTranslate(f2, f4);
            this.b.getShader().setLocalMatrix(this.f);
            canvas.drawRect(this.e, this.b);
        }
    }

    public final int getOpacity() {
        return (this.d == null || (!this.d.o && !this.d.q)) ? -1 : -3;
    }

    public final void b() {
        if (this.c != null && !this.c.isStarted() && this.d != null && this.d.p && getCallback() != null) {
            this.c.start();
        }
    }

    public final void c() {
        RadialGradient radialGradient;
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (width != 0 && height != 0 && this.d != null) {
            int a2 = this.d.a(width);
            int b2 = this.d.b(height);
            boolean z = true;
            if (this.d.g != 1) {
                if (!(this.d.d == 1 || this.d.d == 3)) {
                    z = false;
                }
                if (z) {
                    a2 = 0;
                }
                if (!z) {
                    b2 = 0;
                }
                LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, (float) a2, (float) b2, this.d.b, this.d.a, TileMode.CLAMP);
                radialGradient = linearGradient;
            } else {
                float f2 = ((float) b2) / 2.0f;
                float max = (float) (((double) Math.max(a2, b2)) / Math.sqrt(2.0d));
                int[] iArr = this.d.b;
                RadialGradient radialGradient2 = new RadialGradient(((float) a2) / 2.0f, f2, max, iArr, this.d.a, TileMode.CLAMP);
                radialGradient = radialGradient2;
            }
            this.b.setShader(radialGradient);
        }
    }
}
