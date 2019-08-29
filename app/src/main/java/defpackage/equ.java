package defpackage;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/* renamed from: equ reason: default package */
/* compiled from: CupcakePhtoGestureDetector */
public class equ implements eqy {
    protected eqx a;
    float b;
    float c;
    final float d;
    final float e;
    private VelocityTracker f;
    private boolean g;

    public boolean a() {
        return false;
    }

    public final void a(eqx eqx) {
        this.a = eqx;
    }

    public equ(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.e = (float) viewConfiguration.getScaledMinimumFlingVelocity();
        this.d = (float) viewConfiguration.getScaledTouchSlop();
    }

    /* access modifiers changed from: 0000 */
    public float a(MotionEvent motionEvent) {
        return motionEvent.getX();
    }

    /* access modifiers changed from: 0000 */
    public float b(MotionEvent motionEvent) {
        return motionEvent.getY();
    }

    public boolean c(MotionEvent motionEvent) {
        boolean z = false;
        switch (motionEvent.getAction()) {
            case 0:
                this.f = VelocityTracker.obtain();
                if (this.f != null) {
                    this.f.addMovement(motionEvent);
                }
                this.b = a(motionEvent);
                this.c = b(motionEvent);
                this.g = false;
                break;
            case 1:
                if (this.g && this.f != null) {
                    this.b = a(motionEvent);
                    this.c = b(motionEvent);
                    this.f.addMovement(motionEvent);
                    this.f.computeCurrentVelocity(1000);
                    float xVelocity = this.f.getXVelocity();
                    float yVelocity = this.f.getYVelocity();
                    if (Math.max(Math.abs(xVelocity), Math.abs(yVelocity)) >= this.e) {
                        this.a.b(-xVelocity, -yVelocity);
                    }
                }
                if (this.f != null) {
                    this.f.recycle();
                    this.f = null;
                    break;
                }
                break;
            case 2:
                float a2 = a(motionEvent);
                float b2 = b(motionEvent);
                float f2 = a2 - this.b;
                float f3 = b2 - this.c;
                if (!this.g) {
                    if (Math.sqrt((double) ((f2 * f2) + (f3 * f3))) >= ((double) this.d)) {
                        z = true;
                    }
                    this.g = z;
                }
                if (this.g) {
                    this.a.a(f2, f3);
                    this.b = a2;
                    this.c = b2;
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
