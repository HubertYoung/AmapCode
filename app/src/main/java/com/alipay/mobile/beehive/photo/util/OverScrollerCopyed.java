package com.alipay.mobile.beehive.photo.util;

import android.content.Context;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class OverScrollerCopyed {
    private static final int DEFAULT_DURATION = 250;
    private static final int FLING_MODE = 1;
    private static final int SCROLL_MODE = 0;
    static float sViscousFluidNormalize;
    static float sViscousFluidScale = 8.0f;
    private final boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMode;
    private final a mScrollerX;
    private final a mScrollerY;

    static class a {
        private static float p = ((float) (Math.log(0.78d) / Math.log(0.9d)));
        private static final float[] q = new float[101];
        private static final float[] r = new float[101];
        /* access modifiers changed from: private */
        public int a;
        /* access modifiers changed from: private */
        public int b;
        /* access modifiers changed from: private */
        public int c;
        private int d;
        /* access modifiers changed from: private */
        public float e;
        private float f;
        /* access modifiers changed from: private */
        public long g;
        /* access modifiers changed from: private */
        public int h;
        private int i;
        private int j;
        /* access modifiers changed from: private */
        public boolean k = true;
        private int l;
        private float m = ViewConfiguration.getScrollFriction();
        /* access modifiers changed from: private */
        public int n = 0;
        private float o;

        static {
            float x;
            float coef;
            float y;
            float coef2;
            float x_min = 0.0f;
            float y_min = 0.0f;
            for (int i2 = 0; i2 < 100; i2++) {
                float alpha = ((float) i2) / 100.0f;
                float x_max = 1.0f;
                while (true) {
                    x = x_min + ((x_max - x_min) / 2.0f);
                    coef = 3.0f * x * (1.0f - x);
                    float tx = ((((1.0f - x) * 0.175f) + (0.35000002f * x)) * coef) + (x * x * x);
                    if (((double) Math.abs(tx - alpha)) < 1.0E-5d) {
                        break;
                    } else if (tx > alpha) {
                        x_max = x;
                    } else {
                        x_min = x;
                    }
                }
                q[i2] = ((((1.0f - x) * 0.5f) + x) * coef) + (x * x * x);
                float y_max = 1.0f;
                while (true) {
                    y = y_min + ((y_max - y_min) / 2.0f);
                    coef2 = 3.0f * y * (1.0f - y);
                    float dy = ((((1.0f - y) * 0.5f) + y) * coef2) + (y * y * y);
                    if (((double) Math.abs(dy - alpha)) < 1.0E-5d) {
                        break;
                    } else if (dy > alpha) {
                        y_max = y;
                    } else {
                        y_min = y;
                    }
                }
                r[i2] = ((((1.0f - y) * 0.175f) + (0.35000002f * y)) * coef2) + (y * y * y);
            }
            float[] fArr = q;
            r[100] = 1.0f;
            fArr[100] = 1.0f;
        }

        /* access modifiers changed from: 0000 */
        public final void a(float friction) {
            this.m = friction;
        }

        a(Context context) {
            this.o = 386.0878f * context.getResources().getDisplayMetrics().density * 160.0f * 0.84f;
        }

        /* access modifiers changed from: 0000 */
        public final void b(float q2) {
            this.b = this.a + Math.round(((float) (this.c - this.a)) * q2);
        }

        private static float c(int velocity) {
            return velocity > 0 ? -2000.0f : 2000.0f;
        }

        private void d(int start, int oldFinal, int newFinal) {
            float x = Math.abs(((float) (newFinal - start)) / ((float) (oldFinal - start)));
            int index = (int) (100.0f * x);
            if (index < 100) {
                float x_inf = ((float) index) / 100.0f;
                float t_inf = r[index];
                this.h = (int) (((float) this.h) * (t_inf + (((x - x_inf) / ((((float) (index + 1)) / 100.0f) - x_inf)) * (r[index + 1] - t_inf))));
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(int start, int distance, int duration) {
            this.k = false;
            this.a = start;
            this.c = start + distance;
            this.g = AnimationUtils.currentAnimationTimeMillis();
            this.h = duration;
            this.f = 0.0f;
            this.d = 0;
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            this.b = this.c;
            this.k = true;
        }

        /* access modifiers changed from: 0000 */
        public final void a(int position) {
            this.c = position;
            this.k = false;
        }

        /* access modifiers changed from: 0000 */
        public final void b(int extend) {
            this.h = ((int) (AnimationUtils.currentAnimationTimeMillis() - this.g)) + extend;
            this.k = false;
        }

        /* access modifiers changed from: 0000 */
        public final boolean b(int start, int min, int max) {
            this.k = true;
            this.c = start;
            this.a = start;
            this.d = 0;
            this.g = AnimationUtils.currentAnimationTimeMillis();
            this.h = 0;
            if (start < min) {
                a(start, min);
            } else if (start > max) {
                a(start, max);
            }
            if (!this.k) {
                return true;
            }
            return false;
        }

        private void a(int start, int end) {
            this.k = false;
            this.n = 1;
            this.a = start;
            this.c = end;
            int delta = start - end;
            this.f = c(delta);
            this.d = -delta;
            this.l = Math.abs(delta);
            this.h = (int) (1000.0d * Math.sqrt((-2.0d * ((double) delta)) / ((double) this.f)));
        }

        /* access modifiers changed from: 0000 */
        public final void a(int start, int velocity, int min, int max, int over) {
            this.l = over;
            this.k = false;
            this.d = velocity;
            this.e = (float) velocity;
            this.i = 0;
            this.h = 0;
            this.g = AnimationUtils.currentAnimationTimeMillis();
            this.a = start;
            this.b = start;
            if (start > max || start < min) {
                a(start, min, max, velocity);
                return;
            }
            this.n = 0;
            double totalDistance = 0.0d;
            if (velocity != 0) {
                int f2 = f(velocity);
                this.i = f2;
                this.h = f2;
                totalDistance = e(velocity);
            }
            this.j = (int) (((double) Math.signum((float) velocity)) * totalDistance);
            this.c = this.j + start;
            if (this.c < min) {
                d(this.a, this.c, min);
                this.c = min;
            }
            if (this.c > max) {
                d(this.a, this.c, max);
                this.c = max;
            }
        }

        private double d(int velocity) {
            return Math.log((double) ((0.35f * ((float) Math.abs(velocity))) / (this.m * this.o)));
        }

        private double e(int velocity) {
            return ((double) (this.m * this.o)) * Math.exp((((double) p) / (((double) p) - 1.0d)) * d(velocity));
        }

        private int f(int velocity) {
            return (int) (1000.0d * Math.exp(d(velocity) / (((double) p) - 1.0d)));
        }

        private void e(int start, int end, int velocity) {
            float totalDuration = (float) Math.sqrt((2.0d * ((double) (((((float) (velocity * velocity)) / 2.0f) / Math.abs(this.f)) + ((float) Math.abs(end - start))))) / ((double) Math.abs(this.f)));
            this.g -= (long) ((int) (1000.0f * (totalDuration - (((float) (-velocity)) / this.f))));
            this.a = end;
            this.d = (int) ((-this.f) * totalDuration);
        }

        private void f(int start, int end, int velocity) {
            int i2;
            if (velocity == 0) {
                i2 = start - end;
            } else {
                i2 = velocity;
            }
            this.f = c(i2);
            e(start, end, velocity);
            d();
        }

        private void a(int start, int min, int max, int velocity) {
            boolean positive;
            int edge;
            boolean z = true;
            if (start <= min || start >= max) {
                if (start > max) {
                    positive = true;
                } else {
                    positive = false;
                }
                if (positive) {
                    edge = max;
                } else {
                    edge = min;
                }
                int overDistance = start - edge;
                if (overDistance * velocity < 0) {
                    z = false;
                }
                if (z) {
                    f(start, edge, velocity);
                } else if (e(velocity) > ((double) Math.abs(overDistance))) {
                    a(start, velocity, positive ? min : start, positive ? start : max, this.l);
                } else {
                    a(start, edge);
                }
            } else {
                Log.e("OverScroller", "startAfterEdge called from a valid position");
                this.k = true;
            }
        }

        /* access modifiers changed from: 0000 */
        public final void c(int start, int end, int over) {
            if (this.n == 0) {
                this.l = over;
                this.g = AnimationUtils.currentAnimationTimeMillis();
                a(start, end, end, (int) this.e);
            }
        }

        private void d() {
            float distance = ((float) (this.d * this.d)) / (Math.abs(this.f) * 2.0f);
            float sign = Math.signum((float) this.d);
            if (distance > ((float) this.l)) {
                this.f = (((-sign) * ((float) this.d)) * ((float) this.d)) / (((float) this.l) * 2.0f);
                distance = (float) this.l;
            }
            this.l = (int) distance;
            this.n = 2;
            int i2 = this.a;
            if (this.d <= 0) {
                distance = -distance;
            }
            this.c = i2 + ((int) distance);
            this.h = -((int) ((1000.0f * ((float) this.d)) / this.f));
        }

        /* access modifiers changed from: 0000 */
        public final boolean b() {
            switch (this.n) {
                case 0:
                    if (this.h < this.i) {
                        this.a = this.c;
                        this.d = (int) this.e;
                        this.f = c(this.d);
                        this.g += (long) this.h;
                        d();
                        break;
                    } else {
                        return false;
                    }
                case 1:
                    return false;
                case 2:
                    this.g += (long) this.h;
                    a(this.c, this.a);
                    break;
            }
            c();
            return true;
        }

        /* access modifiers changed from: 0000 */
        public final boolean c() {
            long currentTime = AnimationUtils.currentAnimationTimeMillis() - this.g;
            if (currentTime > ((long) this.h)) {
                return false;
            }
            double distance = 0.0d;
            switch (this.n) {
                case 0:
                    float t = ((float) currentTime) / ((float) this.i);
                    int index = (int) (100.0f * t);
                    float distanceCoef = 1.0f;
                    float velocityCoef = 0.0f;
                    if (index < 100) {
                        float t_inf = ((float) index) / 100.0f;
                        float d_inf = q[index];
                        velocityCoef = (q[index + 1] - d_inf) / ((((float) (index + 1)) / 100.0f) - t_inf);
                        distanceCoef = d_inf + ((t - t_inf) * velocityCoef);
                    }
                    distance = (double) (((float) this.j) * distanceCoef);
                    this.e = ((((float) this.j) * velocityCoef) / ((float) this.i)) * 1000.0f;
                    break;
                case 1:
                    float t2 = ((float) currentTime) / ((float) this.h);
                    float t22 = t2 * t2;
                    float sign = Math.signum((float) this.d);
                    distance = (double) (((float) this.l) * sign * ((3.0f * t22) - ((2.0f * t2) * t22)));
                    this.e = ((float) this.l) * sign * 6.0f * ((-t2) + t22);
                    break;
                case 2:
                    float t3 = ((float) currentTime) / 1000.0f;
                    this.e = ((float) this.d) + (this.f * t3);
                    distance = (double) ((((float) this.d) * t3) + (((this.f * t3) * t3) / 2.0f));
                    break;
            }
            this.b = this.a + ((int) Math.round(distance));
            return true;
        }
    }

    public OverScrollerCopyed(Context context) {
        this(context, null);
    }

    public OverScrollerCopyed(Context context, Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public OverScrollerCopyed(Context context, Interpolator interpolator, boolean flywheel) {
        this.mInterpolator = interpolator;
        this.mFlywheel = flywheel;
        this.mScrollerX = new a(context);
        this.mScrollerY = new a(context);
    }

    /* access modifiers changed from: 0000 */
    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public final void setFriction(float friction) {
        this.mScrollerX.a(friction);
        this.mScrollerY.a(friction);
    }

    public final boolean isFinished() {
        return this.mScrollerX.k && this.mScrollerY.k;
    }

    public final void forceFinished(boolean finished) {
        this.mScrollerX.k = this.mScrollerY.k = finished;
    }

    public final int getCurrX() {
        return this.mScrollerX.b;
    }

    public final int getCurrY() {
        return this.mScrollerY.b;
    }

    public float getCurrVelocity() {
        return (float) Math.sqrt((double) ((this.mScrollerX.e * this.mScrollerX.e) + (this.mScrollerY.e * this.mScrollerY.e)));
    }

    public final int getStartX() {
        return this.mScrollerX.a;
    }

    public final int getStartY() {
        return this.mScrollerY.a;
    }

    public final int getFinalX() {
        return this.mScrollerX.c;
    }

    public final int getFinalY() {
        return this.mScrollerY.c;
    }

    @Deprecated
    public final int getDuration() {
        return Math.max(this.mScrollerX.h, this.mScrollerY.h);
    }

    @Deprecated
    public void extendDuration(int extend) {
        this.mScrollerX.b(extend);
        this.mScrollerY.b(extend);
    }

    @Deprecated
    public void setFinalX(int newX) {
        this.mScrollerX.a(newX);
    }

    @Deprecated
    public void setFinalY(int newY) {
        this.mScrollerY.a(newY);
    }

    public boolean computeScrollOffset() {
        float q;
        if (isFinished()) {
            return false;
        }
        switch (this.mMode) {
            case 0:
                long elapsedTime = AnimationUtils.currentAnimationTimeMillis() - this.mScrollerX.g;
                int duration = this.mScrollerX.h;
                if (elapsedTime >= ((long) duration)) {
                    abortAnimation();
                    break;
                } else {
                    float q2 = ((float) elapsedTime) / ((float) duration);
                    if (this.mInterpolator == null) {
                        q = viscousFluid(q2);
                    } else {
                        q = this.mInterpolator.getInterpolation(q2);
                    }
                    this.mScrollerX.b(q);
                    this.mScrollerY.b(q);
                    break;
                }
            case 1:
                if (!this.mScrollerX.k && !this.mScrollerX.c() && !this.mScrollerX.b()) {
                    this.mScrollerX.a();
                }
                if (!this.mScrollerY.k && !this.mScrollerY.c() && !this.mScrollerY.b()) {
                    this.mScrollerY.a();
                    break;
                }
        }
        return true;
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX, startY, dx, dy, 250);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mMode = 0;
        this.mScrollerX.a(startX, dx, duration);
        this.mScrollerY.a(startY, dy, duration);
    }

    public boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY) {
        this.mMode = 1;
        boolean spingbackX = this.mScrollerX.b(startX, minX, maxX);
        boolean spingbackY = this.mScrollerY.b(startY, minY, maxY);
        if (spingbackX || spingbackY) {
            return true;
        }
        return false;
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        if (this.mFlywheel && !isFinished()) {
            float oldVelocityX = this.mScrollerX.e;
            float oldVelocityY = this.mScrollerY.e;
            if (Math.signum((float) velocityX) == Math.signum(oldVelocityX) && Math.signum((float) velocityY) == Math.signum(oldVelocityY)) {
                velocityX = (int) (((float) velocityX) + oldVelocityX);
                velocityY = (int) (((float) velocityY) + oldVelocityY);
            }
        }
        this.mMode = 1;
        this.mScrollerX.a(startX, velocityX, minX, maxX, overX);
        this.mScrollerY.a(startY, velocityY, minY, maxY, overY);
    }

    public void notifyHorizontalEdgeReached(int startX, int finalX, int overX) {
        this.mScrollerX.c(startX, finalX, overX);
    }

    public void notifyVerticalEdgeReached(int startY, int finalY, int overY) {
        this.mScrollerY.c(startY, finalY, overY);
    }

    public boolean isOverScrolled() {
        return (!this.mScrollerX.k && this.mScrollerX.n != 0) || (!this.mScrollerY.k && this.mScrollerY.n != 0);
    }

    public void abortAnimation() {
        this.mScrollerX.a();
        this.mScrollerY.a();
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - Math.min(this.mScrollerX.g, this.mScrollerY.g));
    }

    public boolean isScrollingInDirection(float xvel, float yvel) {
        return !isFinished() && Math.signum(xvel) == Math.signum((float) (this.mScrollerX.c - this.mScrollerX.a)) && Math.signum(yvel) == Math.signum((float) (this.mScrollerY.c - this.mScrollerY.a));
    }

    static float viscousFluid(float x) {
        float x2;
        float x3 = x * sViscousFluidScale;
        if (x3 < 1.0f) {
            x2 = x3 - (1.0f - ((float) Math.exp((double) (-x3))));
        } else {
            x2 = 0.36787945f + (0.63212055f * (1.0f - ((float) Math.exp((double) (1.0f - x3)))));
        }
        return sViscousFluidNormalize * x2;
    }

    static {
        sViscousFluidNormalize = 1.0f;
        sViscousFluidNormalize = 1.0f / viscousFluid(1.0f);
    }
}
