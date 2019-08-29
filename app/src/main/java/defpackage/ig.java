package defpackage;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.annotation.FloatRange;

/* renamed from: ig reason: default package */
/* compiled from: LottieValueAnimator */
public final class ig extends ValueAnimator {
    public boolean a = false;
    public long b;
    public float c = 1.0f;
    @FloatRange(from = 0.0d, to = 1.0d)
    public float d = 0.0f;
    @FloatRange(from = 0.0d, to = 1.0d)
    private float e = 0.0f;
    @FloatRange(from = 0.0d, to = 1.0d)
    private float f = 1.0f;

    public ig() {
        setInterpolator(null);
        addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (!ig.this.a) {
                    ig.this.d = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                }
            }
        });
        c();
    }

    public final void a(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        float a2 = ii.a(f2, this.e, this.f);
        this.d = a2;
        float abs = (d() ? this.f - a2 : a2 - this.e) / Math.abs(this.f - this.e);
        if (getDuration() > 0) {
            setCurrentPlayTime((long) (((float) getDuration()) * abs));
        }
    }

    public final void a(@FloatRange(from = 0.0d, to = 1.0d) float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3) {
        this.e = f2;
        this.f = f3;
        c();
    }

    public final void b(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        if (f2 >= this.f) {
            throw new IllegalArgumentException("Min value must be smaller then max value.");
        }
        this.e = f2;
        c();
    }

    public final void c(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        if (f2 <= this.e) {
            throw new IllegalArgumentException("Max value must be greater than min value.");
        }
        this.f = f2;
        c();
    }

    public final void d(float f2) {
        this.c = f2;
        c();
    }

    public final void a() {
        start();
        a(d() ? this.f : this.e);
    }

    public final void b() {
        float f2 = this.d;
        if (d() && this.d == this.e) {
            f2 = this.f;
        } else if (!d() && this.d == this.f) {
            f2 = this.e;
        }
        start();
        a(f2);
    }

    private boolean d() {
        return this.c < 0.0f;
    }

    public final void c() {
        setDuration((long) ((((float) this.b) * (this.f - this.e)) / Math.abs(this.c)));
        float[] fArr = new float[2];
        fArr[0] = this.c < 0.0f ? this.f : this.e;
        fArr[1] = this.c < 0.0f ? this.e : this.f;
        setFloatValues(fArr);
        a(this.d);
    }
}
