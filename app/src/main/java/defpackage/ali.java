package defpackage;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* renamed from: ali reason: default package */
/* compiled from: MoveGestureDetector */
public final class ali extends alf {
    private static final PointF i = new PointF();
    public PointF h = new PointF();
    private final a j;
    private PointF k;
    private PointF l;
    private PointF m = new PointF();
    private long n = 0;
    private int o = 0;

    /* renamed from: ali$a */
    /* compiled from: MoveGestureDetector */
    public interface a {
        boolean a(ali ali);

        boolean b(ali ali);

        void c(ali ali);
    }

    public ali(Context context, a aVar) {
        super(context);
        this.j = aVar;
        this.o = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /* access modifiers changed from: protected */
    public final void a(int i2, MotionEvent motionEvent) {
        if (i2 != 0) {
            if (i2 != 2) {
                if (i2 == 5) {
                    if (this.c != null) {
                        this.c.recycle();
                    }
                    this.c = MotionEvent.obtain(motionEvent);
                    return;
                }
            } else if (!(this.c == null || motionEvent == null || ((Math.abs(motionEvent.getX() - this.c.getX()) < ((float) this.o) && Math.abs(motionEvent.getY() - this.c.getY()) < ((float) this.o)) || motionEvent.getPointerCount() >= 2))) {
                this.b = this.j.b(this);
            }
            return;
        }
        a();
        this.c = MotionEvent.obtain(motionEvent);
        this.g = 0;
        b(motionEvent);
    }

    /* access modifiers changed from: protected */
    public final void b(int i2, MotionEvent motionEvent) {
        switch (i2) {
            case 1:
            case 3:
                this.j.c(this);
                a();
                return;
            case 2:
                if (motionEvent.getPointerCount() < 2) {
                    b(motionEvent);
                    if (this.e / this.f > 0.67f && this.j.a(this)) {
                        this.c.recycle();
                        this.c = MotionEvent.obtain(motionEvent);
                        break;
                    }
                }
                break;
        }
    }

    /* access modifiers changed from: protected */
    public final void b(MotionEvent motionEvent) {
        super.b(motionEvent);
        MotionEvent motionEvent2 = this.c;
        this.k = c(motionEvent);
        this.l = c(motionEvent2);
        boolean z = this.c.getPointerCount() != motionEvent.getPointerCount();
        this.h = z ? i : new PointF(this.k.x - this.l.x, this.k.y - this.l.y);
        if (z) {
            this.c.recycle();
            this.c = MotionEvent.obtain(motionEvent);
        }
        this.m.x += this.h.x;
        this.m.y += this.h.y;
        this.n = motionEvent.getEventTime() - this.c.getEventTime();
    }

    public final void d(MotionEvent motionEvent) {
        if (this.c != null) {
            this.c.recycle();
        }
        this.c = MotionEvent.obtain(motionEvent);
    }
}
