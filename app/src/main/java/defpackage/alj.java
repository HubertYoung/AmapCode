package defpackage;

import android.content.Context;
import android.view.MotionEvent;

/* renamed from: alj reason: default package */
/* compiled from: RotateGestureDetector */
public final class alj extends all {
    private final a h;
    private boolean m;

    /* renamed from: alj$a */
    /* compiled from: RotateGestureDetector */
    public interface a {
        boolean a(alj alj);

        boolean b(alj alj);

        void c(alj alj);
    }

    /* renamed from: alj$b */
    /* compiled from: RotateGestureDetector */
    public static class b implements a {
        public boolean a(alj alj) {
            return false;
        }

        public boolean b(alj alj) {
            return true;
        }

        public void c(alj alj) {
        }
    }

    public alj(Context context, a aVar) {
        super(context);
        this.h = aVar;
    }

    /* access modifiers changed from: protected */
    public final void a(int i, MotionEvent motionEvent) {
        if (i != 2) {
            if (i == 5) {
                a();
                this.c = MotionEvent.obtain(motionEvent);
                this.g = 0;
                b(motionEvent);
                this.m = d(motionEvent);
                if (!this.m) {
                    this.b = this.h.b(this);
                }
            }
        } else if (this.m) {
            this.m = d(motionEvent);
            if (!this.m) {
                this.b = this.h.b(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b(int i, MotionEvent motionEvent) {
        if (i != 6) {
            switch (i) {
                case 2:
                    b(motionEvent);
                    if (this.e / this.f > 0.67f && this.h.a(this)) {
                        this.c.recycle();
                        this.c = MotionEvent.obtain(motionEvent);
                        break;
                    }
                case 3:
                    if (!this.m) {
                        this.h.c(this);
                    }
                    a();
                    return;
            }
            return;
        }
        b(motionEvent);
        if (!this.m) {
            this.h.c(this);
        }
        a();
    }

    /* access modifiers changed from: protected */
    public final void a() {
        super.a();
        this.m = false;
    }

    public final float d() {
        return (float) (((Math.atan2((double) this.j, (double) this.i) - Math.atan2((double) this.l, (double) this.k)) * 180.0d) / 3.141592653589793d);
    }
}
