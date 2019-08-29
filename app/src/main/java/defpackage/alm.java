package defpackage;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/* renamed from: alm reason: default package */
/* compiled from: ZoomOutGestureDetector */
public final class alm extends all {
    private static final PointF o = new PointF();
    public PointF h = new PointF();
    private final a m;
    private boolean n;
    private PointF p;
    private PointF q;
    private PointF r = new PointF();

    /* renamed from: alm$a */
    /* compiled from: ZoomOutGestureDetector */
    public interface a {
        void a(alm alm);
    }

    /* renamed from: alm$b */
    /* compiled from: ZoomOutGestureDetector */
    public static class b implements a {
        public void a(alm alm) {
        }
    }

    public alm(Context context, a aVar) {
        super(context);
        this.m = aVar;
    }

    /* access modifiers changed from: protected */
    public final void a(int i, MotionEvent motionEvent) {
        if (i == 5) {
            a();
            this.c = MotionEvent.obtain(motionEvent);
            this.g = 0;
            b(motionEvent);
            this.n = d(motionEvent);
            if (!this.n) {
                this.b = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b(int i, MotionEvent motionEvent) {
        if (i == 3) {
            a();
        } else if (i == 6) {
            b(motionEvent);
            if (!this.n) {
                this.m.a(this);
            }
            a();
        }
    }

    /* access modifiers changed from: protected */
    public final void a() {
        super.a();
        this.n = false;
        this.h.x = 0.0f;
        this.r.x = 0.0f;
        this.h.y = 0.0f;
        this.r.y = 0.0f;
    }

    /* access modifiers changed from: protected */
    public final void b(MotionEvent motionEvent) {
        super.b(motionEvent);
        MotionEvent motionEvent2 = this.c;
        this.p = c(motionEvent);
        this.q = c(motionEvent2);
        this.r = this.c.getPointerCount() != motionEvent.getPointerCount() ? o : new PointF(this.p.x - this.q.x, this.p.y - this.q.y);
        this.h.x += this.r.x;
        this.h.y += this.r.y;
    }
}
