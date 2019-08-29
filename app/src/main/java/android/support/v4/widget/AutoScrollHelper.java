package android.support.v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public abstract class AutoScrollHelper implements OnTouchListener {
    private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    private static final int DEFAULT_EDGE_TYPE = 1;
    private static final float DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE;
    private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
    private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
    private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
    private static final int DEFAULT_RAMP_UP_DURATION = 500;
    private static final float DEFAULT_RELATIVE_EDGE = 0.2f;
    private static final float DEFAULT_RELATIVE_VELOCITY = 1.0f;
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    private static final int HORIZONTAL = 0;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int VERTICAL = 1;
    private int mActivationDelay;
    private boolean mAlreadyDelayed;
    /* access modifiers changed from: private */
    public boolean mAnimating;
    private final Interpolator mEdgeInterpolator = new AccelerateInterpolator();
    private int mEdgeType;
    private boolean mEnabled;
    private boolean mExclusive;
    private float[] mMaximumEdges = {Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mMaximumVelocity = {Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mMinimumVelocity = {0.0f, 0.0f};
    /* access modifiers changed from: private */
    public boolean mNeedsCancel;
    /* access modifiers changed from: private */
    public boolean mNeedsReset;
    private float[] mRelativeEdges = {0.0f, 0.0f};
    private float[] mRelativeVelocity = {0.0f, 0.0f};
    private Runnable mRunnable;
    /* access modifiers changed from: private */
    public final ClampedScroller mScroller = new ClampedScroller();
    /* access modifiers changed from: private */
    public final View mTarget;

    static class ClampedScroller {
        int a;
        int b;
        float c;
        float d;
        long e = Long.MIN_VALUE;
        long f = 0;
        int g = 0;
        int h = 0;
        long i = -1;
        float j;
        int k;

        /* access modifiers changed from: 0000 */
        public final float a(long j2) {
            if (j2 < this.e) {
                return 0.0f;
            }
            if (this.i < 0 || j2 < this.i) {
                return AutoScrollHelper.constrain(((float) (j2 - this.e)) / ((float) this.a), 0.0f, 1.0f) * 0.5f;
            }
            return (1.0f - this.j) + (this.j * AutoScrollHelper.constrain(((float) (j2 - this.i)) / ((float) this.k), 0.0f, 1.0f));
        }
    }

    class ScrollAnimationRunnable implements Runnable {
        private ScrollAnimationRunnable() {
        }

        /* synthetic */ ScrollAnimationRunnable(AutoScrollHelper autoScrollHelper, byte b) {
            this();
        }

        public void run() {
            if (AutoScrollHelper.this.mAnimating) {
                if (AutoScrollHelper.this.mNeedsReset) {
                    AutoScrollHelper.this.mNeedsReset = false;
                    ClampedScroller access$300 = AutoScrollHelper.this.mScroller;
                    access$300.e = AnimationUtils.currentAnimationTimeMillis();
                    access$300.i = -1;
                    access$300.f = access$300.e;
                    access$300.j = 0.5f;
                    access$300.g = 0;
                    access$300.h = 0;
                }
                ClampedScroller access$3002 = AutoScrollHelper.this.mScroller;
                if ((access$3002.i > 0 && AnimationUtils.currentAnimationTimeMillis() > access$3002.i + ((long) access$3002.k)) || !AutoScrollHelper.this.shouldAnimate()) {
                    AutoScrollHelper.this.mAnimating = false;
                    return;
                }
                if (AutoScrollHelper.this.mNeedsCancel) {
                    AutoScrollHelper.this.mNeedsCancel = false;
                    AutoScrollHelper.this.cancelTargetTouch();
                }
                if (access$3002.f == 0) {
                    throw new RuntimeException("Cannot compute scroll delta before calling start()");
                }
                long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                float a2 = access$3002.a(currentAnimationTimeMillis);
                access$3002.f = currentAnimationTimeMillis;
                float f = ((float) (currentAnimationTimeMillis - access$3002.f)) * ((-4.0f * a2 * a2) + (a2 * 4.0f));
                access$3002.g = (int) (access$3002.c * f);
                access$3002.h = (int) (f * access$3002.d);
                AutoScrollHelper.this.scrollTargetBy(access$3002.g, access$3002.h);
                ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this);
            }
        }
    }

    /* access modifiers changed from: private */
    public static float constrain(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    /* access modifiers changed from: private */
    public static int constrain(int i, int i2, int i3) {
        return i > i3 ? i3 : i < i2 ? i2 : i;
    }

    public abstract boolean canTargetScrollHorizontally(int i);

    public abstract boolean canTargetScrollVertically(int i);

    public abstract void scrollTargetBy(int i, int i2);

    public AutoScrollHelper(View view) {
        this.mTarget = view;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float f = (float) ((int) ((displayMetrics.density * 1575.0f) + 0.5f));
        setMaximumVelocity(f, f);
        float f2 = (float) ((int) ((displayMetrics.density * 315.0f) + 0.5f));
        setMinimumVelocity(f2, f2);
        setEdgeType(1);
        setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        setRelativeEdges(0.2f, 0.2f);
        setRelativeVelocity(1.0f, 1.0f);
        setActivationDelay(DEFAULT_ACTIVATION_DELAY);
        setRampUpDuration(500);
        setRampDownDuration(500);
    }

    public AutoScrollHelper setEnabled(boolean z) {
        if (this.mEnabled && !z) {
            requestStop();
        }
        this.mEnabled = z;
        return this;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public AutoScrollHelper setExclusive(boolean z) {
        this.mExclusive = z;
        return this;
    }

    public boolean isExclusive() {
        return this.mExclusive;
    }

    public AutoScrollHelper setMaximumVelocity(float f, float f2) {
        this.mMaximumVelocity[0] = f / 1000.0f;
        this.mMaximumVelocity[1] = f2 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setMinimumVelocity(float f, float f2) {
        this.mMinimumVelocity[0] = f / 1000.0f;
        this.mMinimumVelocity[1] = f2 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setRelativeVelocity(float f, float f2) {
        this.mRelativeVelocity[0] = f / 1000.0f;
        this.mRelativeVelocity[1] = f2 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setEdgeType(int i) {
        this.mEdgeType = i;
        return this;
    }

    public AutoScrollHelper setRelativeEdges(float f, float f2) {
        this.mRelativeEdges[0] = f;
        this.mRelativeEdges[1] = f2;
        return this;
    }

    public AutoScrollHelper setMaximumEdges(float f, float f2) {
        this.mMaximumEdges[0] = f;
        this.mMaximumEdges[1] = f2;
        return this;
    }

    public AutoScrollHelper setActivationDelay(int i) {
        this.mActivationDelay = i;
        return this;
    }

    public AutoScrollHelper setRampUpDuration(int i) {
        this.mScroller.a = i;
        return this;
    }

    public AutoScrollHelper setRampDownDuration(int i) {
        this.mScroller.b = i;
        return this;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.mEnabled) {
            return false;
        }
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0:
                this.mNeedsCancel = true;
                this.mAlreadyDelayed = false;
                break;
            case 1:
            case 3:
                requestStop();
                break;
            case 2:
                break;
        }
        float computeTargetVelocity = computeTargetVelocity(0, motionEvent.getX(), (float) view.getWidth(), (float) this.mTarget.getWidth());
        float computeTargetVelocity2 = computeTargetVelocity(1, motionEvent.getY(), (float) view.getHeight(), (float) this.mTarget.getHeight());
        ClampedScroller clampedScroller = this.mScroller;
        clampedScroller.c = computeTargetVelocity;
        clampedScroller.d = computeTargetVelocity2;
        if (!this.mAnimating && shouldAnimate()) {
            startAnimating();
        }
        if (!this.mExclusive || !this.mAnimating) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean shouldAnimate() {
        ClampedScroller clampedScroller = this.mScroller;
        int abs = (int) (clampedScroller.d / Math.abs(clampedScroller.d));
        int abs2 = (int) (clampedScroller.c / Math.abs(clampedScroller.c));
        return (abs != 0 && canTargetScrollVertically(abs)) || (abs2 != 0 && canTargetScrollHorizontally(abs2));
    }

    private void startAnimating() {
        if (this.mRunnable == null) {
            this.mRunnable = new ScrollAnimationRunnable(this, 0);
        }
        this.mAnimating = true;
        this.mNeedsReset = true;
        if (this.mAlreadyDelayed || this.mActivationDelay <= 0) {
            this.mRunnable.run();
        } else {
            ViewCompat.postOnAnimationDelayed(this.mTarget, this.mRunnable, (long) this.mActivationDelay);
        }
        this.mAlreadyDelayed = true;
    }

    private void requestStop() {
        if (this.mNeedsReset) {
            this.mAnimating = false;
            return;
        }
        ClampedScroller clampedScroller = this.mScroller;
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        clampedScroller.k = constrain((int) (currentAnimationTimeMillis - clampedScroller.e), 0, clampedScroller.b);
        clampedScroller.j = clampedScroller.a(currentAnimationTimeMillis);
        clampedScroller.i = currentAnimationTimeMillis;
    }

    private float computeTargetVelocity(int i, float f, float f2, float f3) {
        float edgeValue = getEdgeValue(this.mRelativeEdges[i], f2, this.mMaximumEdges[i], f);
        int i2 = (edgeValue > 0.0f ? 1 : (edgeValue == 0.0f ? 0 : -1));
        if (i2 == 0) {
            return 0.0f;
        }
        float f4 = this.mRelativeVelocity[i];
        float f5 = this.mMinimumVelocity[i];
        float f6 = this.mMaximumVelocity[i];
        float f7 = f4 * f3;
        if (i2 > 0) {
            return constrain(edgeValue * f7, f5, f6);
        }
        return -constrain((-edgeValue) * f7, f5, f6);
    }

    private float getEdgeValue(float f, float f2, float f3, float f4) {
        float f5;
        float constrain = constrain(f * f2, 0.0f, f3);
        float constrainEdgeValue = constrainEdgeValue(f2 - f4, constrain) - constrainEdgeValue(f4, constrain);
        if (constrainEdgeValue < 0.0f) {
            f5 = -this.mEdgeInterpolator.getInterpolation(-constrainEdgeValue);
        } else if (constrainEdgeValue <= 0.0f) {
            return 0.0f;
        } else {
            f5 = this.mEdgeInterpolator.getInterpolation(constrainEdgeValue);
        }
        return constrain(f5, -1.0f, 1.0f);
    }

    private float constrainEdgeValue(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        switch (this.mEdgeType) {
            case 0:
            case 1:
                if (f < f2) {
                    if (f >= 0.0f) {
                        return 1.0f - (f / f2);
                    }
                    return (!this.mAnimating || this.mEdgeType != 1) ? 0.0f : 1.0f;
                }
                break;
            case 2:
                if (f < 0.0f) {
                    return f / (-f2);
                }
                break;
        }
    }

    /* access modifiers changed from: private */
    public void cancelTargetTouch() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        this.mTarget.onTouchEvent(obtain);
        obtain.recycle();
    }
}
