package com.autonavi.bundle.uitemplate.container.shimmer;

import android.content.res.TypedArray;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Shimmer {
    public final float[] a = new float[4];
    public final int[] b = new int[4];
    final RectF c = new RectF();
    public int d = 0;
    @ColorInt
    int e = -1;
    @ColorInt
    int f = 1291845631;
    public int g = 0;
    int h = 0;
    int i = 0;
    float j = 1.0f;
    float k = 1.0f;
    float l = 0.0f;
    float m = 0.5f;
    public float n = 20.0f;
    public boolean o = true;
    public boolean p = true;
    public boolean q = true;
    int r = -1;
    int s = 1;
    long t = 1000;
    long u;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
        public static final int BOTTOM_TO_TOP = 3;
        public static final int LEFT_TO_RIGHT = 0;
        public static final int RIGHT_TO_LEFT = 2;
        public static final int TOP_TO_BOTTOM = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape {
        public static final int LINEAR = 0;
        public static final int RADIAL = 1;
    }

    public static class a extends b<a> {
        /* access modifiers changed from: protected */
        public final /* bridge */ /* synthetic */ b a() {
            return this;
        }
    }

    public static abstract class b<T extends b<T>> {
        final Shimmer a = new Shimmer();

        /* access modifiers changed from: protected */
        public abstract T a();

        /* access modifiers changed from: 0000 */
        public T a(TypedArray typedArray) {
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_clip_to_children)) {
                this.a.o = typedArray.getBoolean(R.styleable.ShimmerFrameLayoutXX_shimmer_clip_to_children, this.a.o);
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_auto_start)) {
                this.a.p = typedArray.getBoolean(R.styleable.ShimmerFrameLayoutXX_shimmer_auto_start, this.a.p);
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_base_alpha)) {
                this.a.f = (((int) (a(typedArray.getFloat(R.styleable.ShimmerFrameLayoutXX_shimmer_base_alpha, 0.3f)) * 255.0f)) << 24) | (this.a.f & ViewCompat.MEASURED_SIZE_MASK);
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_highlight_alpha)) {
                this.a.e = (((int) (a(typedArray.getFloat(R.styleable.ShimmerFrameLayoutXX_shimmer_highlight_alpha, 1.0f)) * 255.0f)) << 24) | (16777215 & this.a.e);
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_duration)) {
                long j = (long) typedArray.getInt(R.styleable.ShimmerFrameLayoutXX_shimmer_duration, (int) this.a.t);
                if (j < 0) {
                    throw new IllegalArgumentException("Given a negative duration: ".concat(String.valueOf(j)));
                }
                this.a.t = j;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_repeat_count)) {
                this.a.r = typedArray.getInt(R.styleable.ShimmerFrameLayoutXX_shimmer_repeat_count, this.a.r);
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_repeat_delay)) {
                long j2 = (long) typedArray.getInt(R.styleable.ShimmerFrameLayoutXX_shimmer_repeat_delay, (int) this.a.u);
                if (j2 < 0) {
                    throw new IllegalArgumentException("Given a negative repeat delay: ".concat(String.valueOf(j2)));
                }
                this.a.u = j2;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_repeat_mode)) {
                this.a.s = typedArray.getInt(R.styleable.ShimmerFrameLayoutXX_shimmer_repeat_mode, this.a.s);
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_direction)) {
                switch (typedArray.getInt(R.styleable.ShimmerFrameLayoutXX_shimmer_direction, this.a.d)) {
                    case 1:
                        a(1);
                        break;
                    case 2:
                        a(2);
                        break;
                    case 3:
                        a(3);
                        break;
                    default:
                        a(0);
                        break;
                }
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_shape)) {
                if (typedArray.getInt(R.styleable.ShimmerFrameLayoutXX_shimmer_shape, this.a.g) != 1) {
                    b(0);
                } else {
                    b(1);
                }
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_dropoff)) {
                float f = typedArray.getFloat(R.styleable.ShimmerFrameLayoutXX_shimmer_dropoff, this.a.m);
                if (f < 0.0f) {
                    throw new IllegalArgumentException("Given invalid dropoff value: ".concat(String.valueOf(f)));
                }
                this.a.m = f;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_fixed_width)) {
                int dimensionPixelSize = typedArray.getDimensionPixelSize(R.styleable.ShimmerFrameLayoutXX_shimmer_fixed_width, this.a.h);
                if (dimensionPixelSize < 0) {
                    throw new IllegalArgumentException("Given invalid width: ".concat(String.valueOf(dimensionPixelSize)));
                }
                this.a.h = dimensionPixelSize;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_fixed_height)) {
                int dimensionPixelSize2 = typedArray.getDimensionPixelSize(R.styleable.ShimmerFrameLayoutXX_shimmer_fixed_height, this.a.i);
                if (dimensionPixelSize2 < 0) {
                    throw new IllegalArgumentException("Given invalid height: ".concat(String.valueOf(dimensionPixelSize2)));
                }
                this.a.i = dimensionPixelSize2;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_intensity)) {
                float f2 = typedArray.getFloat(R.styleable.ShimmerFrameLayoutXX_shimmer_intensity, this.a.l);
                if (f2 < 0.0f) {
                    throw new IllegalArgumentException("Given invalid intensity value: ".concat(String.valueOf(f2)));
                }
                this.a.l = f2;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_width_ratio)) {
                float f3 = typedArray.getFloat(R.styleable.ShimmerFrameLayoutXX_shimmer_width_ratio, this.a.j);
                if (f3 < 0.0f) {
                    throw new IllegalArgumentException("Given invalid width ratio: ".concat(String.valueOf(f3)));
                }
                this.a.j = f3;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_height_ratio)) {
                float f4 = typedArray.getFloat(R.styleable.ShimmerFrameLayoutXX_shimmer_height_ratio, this.a.k);
                if (f4 < 0.0f) {
                    throw new IllegalArgumentException("Given invalid height ratio: ".concat(String.valueOf(f4)));
                }
                this.a.k = f4;
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_tilt)) {
                this.a.n = typedArray.getFloat(R.styleable.ShimmerFrameLayoutXX_shimmer_tilt, this.a.n);
            }
            return a();
        }

        private T a(int i) {
            this.a.d = i;
            return a();
        }

        private T b(int i) {
            this.a.g = i;
            return a();
        }

        public final Shimmer b() {
            Shimmer shimmer = this.a;
            if (shimmer.g != 1) {
                shimmer.b[0] = shimmer.f;
                shimmer.b[1] = shimmer.e;
                shimmer.b[2] = shimmer.e;
                shimmer.b[3] = shimmer.f;
            } else {
                shimmer.b[0] = shimmer.e;
                shimmer.b[1] = shimmer.e;
                shimmer.b[2] = shimmer.f;
                shimmer.b[3] = shimmer.f;
            }
            Shimmer shimmer2 = this.a;
            if (shimmer2.g != 1) {
                shimmer2.a[0] = Math.max(((1.0f - shimmer2.l) - shimmer2.m) / 2.0f, 0.0f);
                shimmer2.a[1] = Math.max(((1.0f - shimmer2.l) - 0.001f) / 2.0f, 0.0f);
                shimmer2.a[2] = Math.min(((shimmer2.l + 1.0f) + 0.001f) / 2.0f, 1.0f);
                shimmer2.a[3] = Math.min(((shimmer2.l + 1.0f) + shimmer2.m) / 2.0f, 1.0f);
            } else {
                shimmer2.a[0] = 0.0f;
                shimmer2.a[1] = Math.min(shimmer2.l, 1.0f);
                shimmer2.a[2] = Math.min(shimmer2.l + shimmer2.m, 1.0f);
                shimmer2.a[3] = 1.0f;
            }
            return this.a;
        }

        private static float a(float f) {
            return Math.min(1.0f, Math.max(0.0f, f));
        }
    }

    public static class c extends b<c> {
        /* access modifiers changed from: protected */
        public final /* bridge */ /* synthetic */ b a() {
            return this;
        }

        /* access modifiers changed from: 0000 */
        public final /* synthetic */ b a(TypedArray typedArray) {
            super.a(typedArray);
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_base_color)) {
                int color = typedArray.getColor(R.styleable.ShimmerFrameLayoutXX_shimmer_base_color, this.a.f);
                this.a.f = (color & ViewCompat.MEASURED_SIZE_MASK) | (this.a.f & -16777216);
            }
            if (typedArray.hasValue(R.styleable.ShimmerFrameLayoutXX_shimmer_highlight_color)) {
                this.a.e = typedArray.getColor(R.styleable.ShimmerFrameLayoutXX_shimmer_highlight_color, this.a.e);
            }
            return this;
        }
    }

    Shimmer() {
    }

    public final int a(int i2) {
        return this.h > 0 ? this.h : Math.round(this.j * ((float) i2));
    }

    public final int b(int i2) {
        return this.i > 0 ? this.i : Math.round(this.k * ((float) i2));
    }
}
