package defpackage;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/* renamed from: alh reason: default package */
/* compiled from: HoverGestureDetector */
public final class alh extends all {
    private static final PointF m = new PointF();
    public PointF h = new PointF();
    private final a n;
    private boolean o;
    private PointF p;
    private PointF q;
    private PointF r = new PointF();

    /* renamed from: alh$a */
    /* compiled from: HoverGestureDetector */
    public interface a {
        boolean a(alh alh);

        boolean b(alh alh);

        void c(alh alh);
    }

    public alh(Context context, a aVar) {
        super(context);
        this.n = aVar;
    }

    /* access modifiers changed from: protected */
    public final void a(int i, MotionEvent motionEvent) {
        if (i != 2) {
            if (i == 5) {
                a();
                this.c = MotionEvent.obtain(motionEvent);
                this.g = 0;
                b(motionEvent);
                this.o = d(motionEvent);
                if (!this.o) {
                    this.b = this.n.b(this);
                }
            }
        } else if (this.o) {
            this.o = d(motionEvent);
            if (!this.o) {
                this.b = this.n.b(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b(int i, MotionEvent motionEvent) {
        if (i != 6) {
            switch (i) {
                case 2:
                    b(motionEvent);
                    if (this.e / this.f > 0.67f && this.n.a(this)) {
                        this.c.recycle();
                        this.c = MotionEvent.obtain(motionEvent);
                        break;
                    }
                case 3:
                    if (!this.o) {
                        this.n.c(this);
                    }
                    a();
                    return;
            }
            return;
        }
        b(motionEvent);
        if (!this.o) {
            this.n.c(this);
        }
        a();
    }

    /* access modifiers changed from: protected */
    public final void a() {
        super.a();
        this.o = false;
    }

    /* access modifiers changed from: protected */
    public final void b(MotionEvent motionEvent) {
        super.b(motionEvent);
        MotionEvent motionEvent2 = this.c;
        this.p = c(motionEvent);
        this.q = c(motionEvent2);
        this.h = this.c.getPointerCount() != motionEvent.getPointerCount() ? m : new PointF(this.p.x - this.q.x, this.p.y - this.q.y);
        this.r.x += this.h.x;
        this.r.y += this.h.y;
    }
}
