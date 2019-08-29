package com.jiuyan.inimage.util;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: VersionedGestureDetector */
class t extends s {
    float b;
    float c;
    final float d;
    final float e;
    private VelocityTracker f;
    private boolean g;

    public t(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.e = (float) viewConfiguration.getScaledMinimumFlingVelocity();
        this.d = (float) viewConfiguration.getScaledTouchSlop();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public float b(MotionEvent motionEvent) {
        return motionEvent.getX();
    }

    /* access modifiers changed from: 0000 */
    public float c(MotionEvent motionEvent) {
        return motionEvent.getY();
    }

    public boolean a(MotionEvent motionEvent) {
        boolean z = false;
        switch (motionEvent.getAction()) {
            case 0:
                this.f = VelocityTracker.obtain();
                this.f.addMovement(motionEvent);
                this.b = b(motionEvent);
                this.c = c(motionEvent);
                this.g = false;
                break;
            case 1:
                if (this.g && this.f != null) {
                    this.b = b(motionEvent);
                    this.c = c(motionEvent);
                    this.f.addMovement(motionEvent);
                    this.f.computeCurrentVelocity(1000);
                    float xVelocity = this.f.getXVelocity();
                    float yVelocity = this.f.getYVelocity();
                    if (Math.max(Math.abs(xVelocity), Math.abs(yVelocity)) >= this.e) {
                        this.a.a(this.b, this.c, -xVelocity, -yVelocity);
                    }
                }
                if (this.f != null) {
                    this.f.recycle();
                    this.f = null;
                    break;
                }
                break;
            case 2:
                float b2 = b(motionEvent);
                float c2 = c(motionEvent);
                float f2 = b2 - this.b;
                float f3 = c2 - this.c;
                if (!this.g) {
                    if (Math.sqrt((double) ((f2 * f2) + (f3 * f3))) >= ((double) this.d)) {
                        z = true;
                    }
                    this.g = z;
                }
                if (this.g) {
                    this.a.a(f2, f3);
                    this.b = b2;
                    this.c = c2;
                    if (this.f != null) {
                        this.f.addMovement(motionEvent);
                        break;
                    }
                }
                break;
            case 3:
                if (this.f != null) {
                    this.f.recycle();
                    this.f = null;
                    break;
                }
                break;
        }
        return true;
    }
}
