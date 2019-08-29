package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

class MaterialProgressDrawable extends Drawable implements Animatable {
    private static final Interpolator c = new LinearInterpolator();
    /* access modifiers changed from: private */
    public static final Interpolator d = new FastOutSlowInInterpolator();
    final Ring a;
    boolean b;
    private final int[] e = {-16777216};
    private final ArrayList<Animation> f = new ArrayList<>();
    private float g;
    private Resources h;
    private View i;
    private Animation j;
    /* access modifiers changed from: private */
    public float k;
    private double l;
    private double m;
    private final Callback n = new Callback() {
        public void invalidateDrawable(Drawable drawable) {
            MaterialProgressDrawable.this.invalidateSelf();
        }

        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            MaterialProgressDrawable.this.scheduleSelf(runnable, j);
        }

        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            MaterialProgressDrawable.this.unscheduleSelf(runnable);
        }
    };

    @Retention(RetentionPolicy.CLASS)
    public @interface ProgressDrawableSize {
    }

    static class Ring {
        final RectF a = new RectF();
        final Paint b = new Paint();
        final Paint c = new Paint();
        float d = 0.0f;
        float e = 0.0f;
        float f = 0.0f;
        float g = 5.0f;
        float h = 2.5f;
        int[] i;
        int j;
        float k;
        float l;
        float m;
        boolean n;
        Path o;
        float p;
        double q;
        int r;
        int s;
        int t;
        final Paint u = new Paint(1);
        int v;
        int w;
        private final Callback x;

        public Ring(Callback callback) {
            this.x = callback;
            this.b.setStrokeCap(Cap.SQUARE);
            this.b.setAntiAlias(true);
            this.b.setStyle(Style.STROKE);
            this.c.setStyle(Style.FILL);
            this.c.setAntiAlias(true);
        }

        public final void a(@NonNull int[] iArr) {
            this.i = iArr;
            a(0);
        }

        public final void a(int i2) {
            this.j = i2;
            this.w = this.i[this.j];
        }

        /* access modifiers changed from: 0000 */
        public final int a() {
            return (this.j + 1) % this.i.length;
        }

        public final void a(float f2) {
            this.d = f2;
            d();
        }

        public final void b(float f2) {
            this.e = f2;
            d();
        }

        public final void c(float f2) {
            this.f = f2;
            d();
        }

        public final void a(boolean z) {
            if (this.n != z) {
                this.n = z;
                d();
            }
        }

        public final void b() {
            this.k = this.d;
            this.l = this.e;
            this.m = this.f;
        }

        public final void c() {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            a(0.0f);
            b(0.0f);
            c(0.0f);
        }

        /* access modifiers changed from: 0000 */
        public final void d() {
            this.x.invalidateDrawable(null);
        }
    }

    public int getOpacity() {
        return -3;
    }

    public MaterialProgressDrawable(Context context, View view) {
        this.i = view;
        this.h = context.getResources();
        this.a = new Ring(this.n);
        this.a.a(this.e);
        a(1);
        final Ring ring = this.a;
        AnonymousClass1 r6 = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                if (MaterialProgressDrawable.this.b) {
                    MaterialProgressDrawable.a(f, ring);
                    return;
                }
                float a2 = MaterialProgressDrawable.b(ring);
                float f2 = ring.l;
                float f3 = ring.k;
                float f4 = ring.m;
                MaterialProgressDrawable.c(f, ring);
                if (f <= 0.5f) {
                    Interpolator a3 = MaterialProgressDrawable.d;
                    ring.a(f3 + ((0.8f - a2) * a3.getInterpolation(f / 0.5f)));
                }
                if (f > 0.5f) {
                    Interpolator a4 = MaterialProgressDrawable.d;
                    ring.b(f2 + ((0.8f - a2) * a4.getInterpolation((f - 0.5f) / 0.5f)));
                }
                ring.c(f4 + (0.25f * f));
                MaterialProgressDrawable.this.c((f * 216.0f) + ((MaterialProgressDrawable.this.k / 5.0f) * 1080.0f));
            }
        };
        r6.setRepeatCount(-1);
        r6.setRepeatMode(1);
        r6.setInterpolator(c);
        r6.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MaterialProgressDrawable.this.k = 0.0f;
            }

            public void onAnimationRepeat(Animation animation) {
                ring.b();
                Ring ring = ring;
                ring.a(ring.a());
                ring.a(ring.e);
                if (MaterialProgressDrawable.this.b) {
                    MaterialProgressDrawable.this.b = false;
                    animation.setDuration(1332);
                    ring.a(false);
                    return;
                }
                MaterialProgressDrawable.this.k = (MaterialProgressDrawable.this.k + 1.0f) % 5.0f;
            }
        });
        this.j = r6;
    }

    private void a(double d2, double d3, double d4, double d5, float f2, float f3) {
        float f4;
        Ring ring = this.a;
        float f5 = this.h.getDisplayMetrics().density;
        double d6 = (double) f5;
        this.l = d2 * d6;
        this.m = d3 * d6;
        float f6 = ((float) d5) * f5;
        ring.g = f6;
        ring.b.setStrokeWidth(f6);
        ring.d();
        ring.q = d4 * d6;
        ring.a(0);
        ring.r = (int) (f2 * f5);
        ring.s = (int) (f3 * f5);
        float min = (float) Math.min((int) this.l, (int) this.m);
        if (ring.q <= 0.0d || min < 0.0f) {
            f4 = (float) Math.ceil((double) (ring.g / 2.0f));
        } else {
            f4 = (float) (((double) (min / 2.0f)) - ring.q);
        }
        ring.h = f4;
    }

    public final void a(@ProgressDrawableSize int i2) {
        if (i2 == 0) {
            a(56.0d, 56.0d, 12.5d, 3.0d, 12.0f, 6.0f);
        } else {
            a(40.0d, 40.0d, 8.75d, 2.5d, 10.0f, 5.0f);
        }
    }

    public final void a(boolean z) {
        this.a.a(z);
    }

    public final void a(float f2) {
        Ring ring = this.a;
        if (f2 != ring.p) {
            ring.p = f2;
            ring.d();
        }
    }

    public final void b(float f2) {
        this.a.a(0.0f);
        this.a.b(f2);
    }

    public final void b(int i2) {
        this.a.v = i2;
    }

    public int getIntrinsicHeight() {
        return (int) this.m;
    }

    public int getIntrinsicWidth() {
        return (int) this.l;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int save = canvas.save();
        canvas.rotate(this.g, bounds.exactCenterX(), bounds.exactCenterY());
        Ring ring = this.a;
        RectF rectF = ring.a;
        rectF.set(bounds);
        rectF.inset(ring.h, ring.h);
        float f2 = (ring.d + ring.f) * 360.0f;
        float f3 = ((ring.e + ring.f) * 360.0f) - f2;
        ring.b.setColor(ring.w);
        canvas.drawArc(rectF, f2, f3, false, ring.b);
        if (ring.n) {
            if (ring.o == null) {
                ring.o = new Path();
                ring.o.setFillType(FillType.EVEN_ODD);
            } else {
                ring.o.reset();
            }
            float sin = (float) ((ring.q * Math.sin(0.0d)) + ((double) bounds.exactCenterY()));
            ring.o.moveTo(0.0f, 0.0f);
            ring.o.lineTo(((float) ring.r) * ring.p, 0.0f);
            ring.o.lineTo((((float) ring.r) * ring.p) / 2.0f, ((float) ring.s) * ring.p);
            ring.o.offset(((float) ((ring.q * Math.cos(0.0d)) + ((double) bounds.exactCenterX()))) - (((float) (((int) ring.h) / 2)) * ring.p), sin);
            ring.o.close();
            ring.c.setColor(ring.w);
            canvas.rotate((f2 + f3) - 5.0f, bounds.exactCenterX(), bounds.exactCenterY());
            canvas.drawPath(ring.o, ring.c);
        }
        if (ring.t < 255) {
            ring.u.setColor(ring.v);
            ring.u.setAlpha(255 - ring.t);
            canvas.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), (float) (bounds.width() / 2), ring.u);
        }
        canvas.restoreToCount(save);
    }

    public void setAlpha(int i2) {
        this.a.t = i2;
    }

    public int getAlpha() {
        return this.a.t;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Ring ring = this.a;
        ring.b.setColorFilter(colorFilter);
        ring.d();
    }

    /* access modifiers changed from: 0000 */
    public final void c(float f2) {
        this.g = f2;
        invalidateSelf();
    }

    public boolean isRunning() {
        ArrayList<Animation> arrayList = this.f;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Animation animation = arrayList.get(i2);
            if (animation.hasStarted() && !animation.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        this.j.reset();
        this.a.b();
        if (this.a.e != this.a.d) {
            this.b = true;
            this.j.setDuration(666);
            this.i.startAnimation(this.j);
            return;
        }
        this.a.a(0);
        this.a.c();
        this.j.setDuration(1332);
        this.i.startAnimation(this.j);
    }

    public void stop() {
        this.i.clearAnimation();
        c(0.0f);
        this.a.a(false);
        this.a.a(0);
        this.a.c();
    }

    /* access modifiers changed from: private */
    public static float b(Ring ring) {
        return (float) Math.toRadians(((double) ring.g) / (ring.q * 6.283185307179586d));
    }

    /* access modifiers changed from: private */
    public static void c(float f2, Ring ring) {
        if (f2 > 0.75f) {
            float f3 = (f2 - 0.75f) / 0.25f;
            int i2 = ring.i[ring.j];
            int i3 = ring.i[ring.a()];
            int i4 = (i2 >> 24) & 255;
            int i5 = (i2 >> 16) & 255;
            int i6 = (i2 >> 8) & 255;
            int i7 = i2 & 255;
            ring.w = ((i4 + ((int) (((float) (((i3 >> 24) & 255) - i4)) * f3))) << 24) | ((i5 + ((int) (((float) (((i3 >> 16) & 255) - i5)) * f3))) << 16) | ((i6 + ((int) (((float) (((i3 >> 8) & 255) - i6)) * f3))) << 8) | (i7 + ((int) (f3 * ((float) ((i3 & 255) - i7)))));
        }
    }

    static /* synthetic */ void a(float f2, Ring ring) {
        c(f2, ring);
        ring.a(ring.k + (((ring.l - b(ring)) - ring.k) * f2));
        ring.b(ring.l);
        ring.c(ring.m + ((((float) (Math.floor((double) (ring.m / 0.8f)) + 1.0d)) - ring.m) * f2));
    }
}
