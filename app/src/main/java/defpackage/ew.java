package defpackage;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* renamed from: ew reason: default package */
/* compiled from: LottieDrawable */
public class ew extends Drawable implements Callback {
    private static final String o = "ew";
    public ev a;
    public final ig b = new ig();
    public float c = 1.0f;
    public final Set<a> d = new HashSet();
    public final ArrayList<b> e = new ArrayList<>();
    @Nullable
    public gl f;
    @Nullable
    public String g;
    @Nullable
    public et h;
    @Nullable
    public gk i;
    @Nullable
    public es j;
    @Nullable
    public fb k;
    public boolean l;
    @Nullable
    public hy m;
    public boolean n;
    private final Matrix p = new Matrix();
    private int q = 255;

    /* renamed from: ew$a */
    /* compiled from: LottieDrawable */
    public static class a {
        public final String a;
        @Nullable
        public final String b;
        @Nullable
        public final ColorFilter c;

        a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
            this.a = str;
            this.b = str2;
            this.c = colorFilter;
        }

        public final int hashCode() {
            int hashCode = this.a != null ? this.a.hashCode() * 527 : 17;
            return this.b != null ? hashCode * 31 * this.b.hashCode() : hashCode;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return hashCode() == aVar.hashCode() && this.c == aVar.c;
        }
    }

    /* renamed from: ew$b */
    /* compiled from: LottieDrawable */
    public interface b {
        void a();
    }

    public int getOpacity() {
        return -3;
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public ew() {
        this.b.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (ew.this.m != null) {
                    ew.this.m.a(ew.this.b.d);
                }
            }
        });
    }

    public final void a() {
        if (this.f != null) {
            this.f.a();
        }
    }

    public final void b() {
        this.m = new hy(this, com.airbnb.lottie.model.layer.Layer.a.a(this.a), this.a.f, this.a);
    }

    public void invalidateSelf() {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int i2) {
        this.q = i2;
    }

    public int getAlpha() {
        return this.q;
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        a aVar = new a(str, str2, colorFilter);
        if (colorFilter != null || !this.d.contains(aVar)) {
            this.d.add(new a(str, str2, colorFilter));
        } else {
            this.d.remove(aVar);
        }
        if (this.m != null) {
            this.m.a(str, str2, colorFilter);
        }
    }

    public void draw(@NonNull Canvas canvas) {
        float f2;
        eu.a("Drawable#draw");
        if (this.m != null) {
            float f3 = this.c;
            float min = Math.min(((float) canvas.getWidth()) / ((float) this.a.h.width()), ((float) canvas.getHeight()) / ((float) this.a.h.height()));
            if (f3 > min) {
                f2 = this.c / min;
            } else {
                min = f3;
                f2 = 1.0f;
            }
            int i2 = (f2 > 1.0f ? 1 : (f2 == 1.0f ? 0 : -1));
            if (i2 > 0) {
                canvas.save();
                float width = ((float) this.a.h.width()) / 2.0f;
                float height = ((float) this.a.h.height()) / 2.0f;
                float f4 = width * min;
                float f5 = height * min;
                canvas.translate((this.c * width) - f4, (this.c * height) - f5);
                canvas.scale(f2, f2, f4, f5);
            }
            this.p.reset();
            this.p.preScale(min, min);
            this.m.a(canvas, this.p, this.q);
            eu.b("Drawable#draw");
            if (i2 > 0) {
                canvas.restore();
            }
        }
    }

    public final void c() {
        if (this.m == null) {
            this.e.add(new b() {
                public final void a() {
                    ew.this.c();
                }
            });
        } else {
            this.b.a();
        }
    }

    public final void d() {
        if (this.m == null) {
            this.e.add(new b() {
                public final void a() {
                    ew.this.d();
                }
            });
        } else {
            this.b.b();
        }
    }

    public final void a(final int i2) {
        if (this.a == null) {
            this.e.add(new b() {
                public final void a() {
                    ew.this.a(i2);
                }
            });
        } else {
            a(((float) i2) / this.a.b());
        }
    }

    public final void a(float f2) {
        this.b.b(f2);
    }

    public final void b(final int i2) {
        if (this.a == null) {
            this.e.add(new b() {
                public final void a() {
                    ew.this.b(i2);
                }
            });
        } else {
            b(((float) i2) / this.a.b());
        }
    }

    public final void b(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.b.c(f2);
    }

    public final void a(final int i2, final int i3) {
        if (this.a == null) {
            this.e.add(new b() {
                public final void a() {
                    ew.this.a(i2, i3);
                }
            });
        } else {
            this.b.a(((float) i2) / this.a.b(), ((float) i3) / this.a.b());
        }
    }

    public final void c(final int i2) {
        if (this.a == null) {
            this.e.add(new b() {
                public final void a() {
                    ew.this.c(i2);
                }
            });
        } else {
            c(((float) i2) / this.a.b());
        }
    }

    public final void c(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.b.a(f2);
        if (this.m != null) {
            this.m.a(f2);
        }
    }

    public final void a(boolean z) {
        this.b.setRepeatCount(z ? -1 : 0);
    }

    public final void d(float f2) {
        this.c = f2;
        f();
    }

    public final boolean e() {
        return this.k == null && this.a.d.size() > 0;
    }

    public final void f() {
        if (this.a != null) {
            float f2 = this.c;
            setBounds(0, 0, (int) (((float) this.a.h.width()) * f2), (int) (((float) this.a.h.height()) * f2));
        }
    }

    public final void g() {
        this.e.clear();
        this.b.cancel();
    }

    public int getIntrinsicWidth() {
        if (this.a == null) {
            return -1;
        }
        return (int) (((float) this.a.h.width()) * this.c);
    }

    public int getIntrinsicHeight() {
        if (this.a == null) {
            return -1;
        }
        return (int) (((float) this.a.h.height()) * this.c);
    }

    @Nullable
    public final Bitmap a(String str) {
        gl h2 = h();
        if (h2 != null) {
            return h2.a(str);
        }
        return null;
    }

    public final gl h() {
        if (getCallback() == null) {
            return null;
        }
        if (this.f != null && !this.f.a(i())) {
            this.f.a();
            this.f = null;
        }
        if (this.f == null) {
            this.f = new gl(getCallback(), this.g, this.h, this.a.b);
        }
        return this.f;
    }

    @Nullable
    private Context i() {
        Callback callback = getCallback();
        if (callback != null && (callback instanceof View)) {
            return ((View) callback).getContext();
        }
        return null;
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j2);
        }
    }

    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }
}
