package com.autonavi.minimap.ajx3.widget.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringConfigRegistry;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

public class SpringOverScroller implements SpringListener {
    private static final SpringConfig COASTING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(0.0d, 1.6d);
    private static final int FLING_MODE = 1;
    private static final SpringConfig RUBBERBANDING_CONFIG = SpringConfig.fromOrigamiTensionAndFriction(30.0d, 8.0d);
    private static final int SCROLL_MODE = 0;
    private static final int SPRINGBACK_MODE = 2;
    private int mMode;
    private final OverScroller mOriginScroller;
    private final SpringSystem mSpringSystem;
    private final Spring mSpringX;
    private boolean mSpringXEnabled;
    private final Spring mSpringY;
    private boolean mSpringYEnabled;

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
    }

    public void onSpringActivate(Spring spring) {
    }

    public void onSpringAtRest(Spring spring) {
    }

    public void onSpringEndStateChange(Spring spring) {
    }

    public final void setFriction(float f) {
    }

    public SpringOverScroller(Context context) {
        this(context, null);
    }

    public SpringOverScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public SpringOverScroller(Context context, Interpolator interpolator, float f, float f2) {
        this(context, interpolator, true);
    }

    public SpringOverScroller(Context context, Interpolator interpolator, float f, float f2, boolean z) {
        this(context, interpolator, z);
    }

    public SpringOverScroller(Context context, Interpolator interpolator, boolean z) {
        this.mOriginScroller = new OverScroller(context);
        this.mSpringSystem = SpringSystem.create();
        this.mSpringX = this.mSpringSystem.createSpring().addListener(this);
        this.mSpringY = this.mSpringSystem.createSpring().addListener(this);
        SpringConfigRegistry.getInstance().addSpringConfig(RUBBERBANDING_CONFIG, "rubber-banding");
        SpringConfigRegistry.getInstance().addSpringConfig(COASTING_CONFIG, "coasting");
    }

    /* access modifiers changed from: 0000 */
    public void finishIfNeeded() {
        if (2 != this.mMode && !isFinished()) {
            abortAnimation();
        }
    }

    public final boolean isFinished() {
        if (2 == this.mMode) {
            return true;
        }
        return this.mOriginScroller.isFinished();
    }

    public final int getCurrX() {
        if (2 != this.mMode) {
            return this.mOriginScroller.getCurrX();
        }
        if (((int) Math.round(this.mSpringX.getEndValue())) != ((int) Math.round(this.mSpringX.getCurrentValue()))) {
            return (int) Math.round(this.mSpringX.getCurrentValue());
        }
        this.mSpringXEnabled = false;
        return (int) Math.round(this.mSpringX.getEndValue());
    }

    public final int getCurrY() {
        if (2 != this.mMode) {
            return this.mOriginScroller.getCurrY();
        }
        if (((int) Math.round(this.mSpringY.getEndValue())) != ((int) Math.round(this.mSpringY.getCurrentValue()))) {
            return (int) Math.round(this.mSpringY.getCurrentValue());
        }
        this.mSpringYEnabled = false;
        return (int) Math.round(this.mSpringY.getEndValue());
    }

    public float getCurrVelocity() {
        if (2 != this.mMode) {
            return this.mOriginScroller.getCurrVelocity();
        }
        double velocity = this.mSpringX.getVelocity();
        double velocity2 = this.mSpringX.getVelocity();
        return (float) ((int) Math.sqrt((velocity * velocity) + (velocity2 * velocity2)));
    }

    public final int getStartX() {
        if (2 == this.mMode) {
            return (int) Math.round(this.mSpringX.getStartValue());
        }
        return this.mOriginScroller.getStartX();
    }

    public final int getStartY() {
        if (2 == this.mMode) {
            return (int) Math.round(this.mSpringY.getStartValue());
        }
        return this.mOriginScroller.getStartY();
    }

    public final int getFinalX() {
        if (2 == this.mMode) {
            return (int) Math.round(this.mSpringX.getEndValue());
        }
        return this.mOriginScroller.getFinalX();
    }

    public final int getFinalY() {
        if (2 == this.mMode) {
            return (int) Math.round(this.mSpringY.getEndValue());
        }
        return this.mOriginScroller.getFinalY();
    }

    public boolean computeScrollOffset() {
        if (2 == this.mMode) {
            return !this.mSpringX.isAtRest() || !this.mSpringY.isAtRest() || this.mSpringXEnabled || this.mSpringYEnabled;
        }
        return this.mOriginScroller.computeScrollOffset();
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        finishIfNeeded();
        this.mMode = 0;
        this.mOriginScroller.startScroll(i, i2, i3, i4);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        finishIfNeeded();
        this.mMode = 2;
        return springback(this.mSpringX, i, i3, i4) || springback(this.mSpringY, i2, i5, i6);
    }

    /* access modifiers changed from: 0000 */
    public boolean springback(Spring spring, int i, int i2, int i3) {
        if (i < i2) {
            startSpringBack(spring, i, i2, 0);
            return true;
        } else if (i <= i3) {
            return false;
        } else {
            startSpringBack(spring, i, i3, 0);
            return true;
        }
    }

    private void startSpringBack(Spring spring, int i, int i2, int i3) {
        spring.setSpringConfig(RUBBERBANDING_CONFIG).setCurrentValue((double) i, false).setEndValue((double) i2);
        if (this.mSpringX == spring) {
            this.mSpringXEnabled = true;
        } else {
            this.mSpringYEnabled = true;
        }
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        finishIfNeeded();
        this.mMode = 1;
        this.mOriginScroller.fling(i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }

    public void abortAnimation() {
        this.mSpringX.setAtRest();
        this.mSpringY.setAtRest();
        this.mOriginScroller.abortAnimation();
    }

    public void onSpringUpdate(Spring spring) {
        if (2 == this.mMode && ((int) Math.round(spring.getEndValue())) == ((int) Math.round(spring.getCurrentValue()))) {
            spring.setAtRest();
        }
    }
}
