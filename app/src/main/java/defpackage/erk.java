package defpackage;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.widget.ScrollerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import java.util.Arrays;

/* renamed from: erk reason: default package */
/* compiled from: ViewDragHelper */
public final class erk {
    private static final Interpolator w = new Interpolator() {
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    public int a;
    public int b;
    public int c = -1;
    public float[] d;
    public float[] e;
    public float[] f;
    public float[] g;
    public int[] h;
    public VelocityTracker i;
    public float j;
    public int k;
    public ScrollerCompat l;
    public final a m;
    public View n;
    public boolean o;
    public final ViewGroup p;
    public final Runnable q = new Runnable() {
        public final void run() {
            erk.this.b(0);
        }
    };
    private int[] r;
    private int[] s;
    private int t;
    private float u;
    private int v;

    /* renamed from: erk$a */
    /* compiled from: ViewDragHelper */
    public static abstract class a {
        public int a(int i, int i2) {
            return 0;
        }

        public void a() {
        }

        public void a(int i) {
        }

        public void a(View view, float f) {
        }

        public abstract boolean a(View view);

        public void b() {
        }

        public int c() {
            return 0;
        }
    }

    private erk(Context context, ViewGroup viewGroup, Interpolator interpolator, a aVar) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (aVar == null) {
            throw new IllegalArgumentException("Callback may not be null");
        } else {
            this.p = viewGroup;
            this.m = aVar;
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            this.v = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
            this.b = viewConfiguration.getScaledTouchSlop();
            this.u = (float) viewConfiguration.getScaledMaximumFlingVelocity();
            this.j = (float) viewConfiguration.getScaledMinimumFlingVelocity();
            this.l = ScrollerCompat.create(context, interpolator == null ? w : interpolator);
        }
    }

    private void b(View view, int i2) {
        if (view.getParent() != this.p) {
            StringBuilder sb = new StringBuilder("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
            sb.append(this.p);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }
        this.n = view;
        this.c = i2;
        this.m.b();
        b(1);
    }

    public final void a() {
        this.c = -1;
        d();
        if (this.i != null) {
            this.i.recycle();
            this.i = null;
        }
    }

    public final void b() {
        a();
        if (this.a == 2) {
            this.l.getCurrX();
            this.l.getCurrY();
            this.l.abortAnimation();
            this.l.getCurrX();
            this.m.a(this.l.getCurrY());
        }
        b(0);
    }

    public final boolean a(int i2, int i3, int i4, int i5) {
        int left = this.n.getLeft();
        int top = this.n.getTop();
        int i6 = i2 - left;
        int i7 = i3 - top;
        if (i6 == 0 && i7 == 0) {
            this.l.abortAnimation();
            b(0);
            return false;
        }
        this.l.startScroll(left, top, i6, i7, b(i6, i7, i4, i5));
        b(2);
        return true;
    }

    private int b(int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        float f4;
        float f5;
        int b2 = b(i4, (int) this.j, (int) this.u);
        int b3 = b(i5, (int) this.j, (int) this.u);
        int abs = Math.abs(i2);
        int abs2 = Math.abs(i3);
        int abs3 = Math.abs(b2);
        int abs4 = Math.abs(b3);
        int i6 = abs3 + abs4;
        int i7 = abs + abs2;
        if (b2 != 0) {
            f2 = (float) abs3;
            f3 = (float) i6;
        } else {
            f2 = (float) abs;
            f3 = (float) i7;
        }
        float f6 = f2 / f3;
        if (b3 != 0) {
            f4 = (float) abs4;
            f5 = (float) i6;
        } else {
            f4 = (float) abs2;
            f5 = (float) i7;
        }
        float f7 = f4 / f5;
        return (int) ((((float) a(i2, b2, 0)) * f6) + (((float) a(i3, b3, this.m.c())) * f7));
    }

    private int a(int i2, int i3, int i4) {
        int i5;
        if (i2 == 0) {
            return 0;
        }
        int width = this.p.getWidth();
        float f2 = (float) (width / 2);
        float b2 = f2 + (b(Math.min(1.0f, ((float) Math.abs(i2)) / ((float) width))) * f2);
        int abs = Math.abs(i3);
        if (abs > 0) {
            i5 = Math.round(Math.abs(b2 / ((float) abs)) * 1000.0f) * 4;
        } else {
            i5 = (int) (((((float) Math.abs(i2)) / ((float) i4)) + 1.0f) * 256.0f);
        }
        return Math.min(i5, 600);
    }

    private static int b(int i2, int i3, int i4) {
        int abs = Math.abs(i2);
        if (abs < i3) {
            return 0;
        }
        if (abs > i4) {
            return i2 > 0 ? i4 : -i4;
        }
        return i2;
    }

    private static float a(float f2, float f3, float f4) {
        float abs = Math.abs(f2);
        if (abs < f3) {
            return 0.0f;
        }
        if (abs > f4) {
            return f2 > 0.0f ? f4 : -f4;
        }
        return f2;
    }

    private static float b(float f2) {
        return (float) Math.sin((double) ((float) (((double) (f2 - 0.5f)) * 0.4712389167638204d)));
    }

    public final void a(float f2) {
        this.o = true;
        this.m.a(this.n, f2);
        this.o = false;
        if (this.a == 1) {
            b(0);
        }
    }

    private void d() {
        if (this.d != null) {
            Arrays.fill(this.d, 0.0f);
            Arrays.fill(this.e, 0.0f);
            Arrays.fill(this.f, 0.0f);
            Arrays.fill(this.g, 0.0f);
            Arrays.fill(this.h, 0);
            Arrays.fill(this.r, 0);
            Arrays.fill(this.s, 0);
            this.t = 0;
        }
    }

    public final void a(int i2) {
        if (this.d != null && this.d.length > i2) {
            this.d[i2] = 0.0f;
            this.e[i2] = 0.0f;
            this.f[i2] = 0.0f;
            this.g[i2] = 0.0f;
            this.h[i2] = 0;
            this.r[i2] = 0;
            this.s[i2] = 0;
            this.t = (~(1 << i2)) & this.t;
        }
    }

    private void c(int i2) {
        if (this.d == null || this.d.length <= i2) {
            int i3 = i2 + 1;
            float[] fArr = new float[i3];
            float[] fArr2 = new float[i3];
            float[] fArr3 = new float[i3];
            float[] fArr4 = new float[i3];
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            if (this.d != null) {
                System.arraycopy(this.d, 0, fArr, 0, this.d.length);
                System.arraycopy(this.e, 0, fArr2, 0, this.e.length);
                System.arraycopy(this.f, 0, fArr3, 0, this.f.length);
                System.arraycopy(this.g, 0, fArr4, 0, this.g.length);
                System.arraycopy(this.h, 0, iArr, 0, this.h.length);
                System.arraycopy(this.r, 0, iArr2, 0, this.r.length);
                System.arraycopy(this.s, 0, iArr3, 0, this.s.length);
            }
            this.d = fArr;
            this.e = fArr2;
            this.f = fArr3;
            this.g = fArr4;
            this.h = iArr;
            this.r = iArr2;
            this.s = iArr3;
        }
    }

    public final void a(float f2, float f3, int i2) {
        c(i2);
        float[] fArr = this.d;
        this.f[i2] = f2;
        fArr[i2] = f2;
        float[] fArr2 = this.e;
        this.g[i2] = f3;
        fArr2[i2] = f3;
        this.h[i2] = b((int) f2, (int) f3);
        this.t |= 1 << i2;
    }

    public final void a(MotionEvent motionEvent) {
        int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
        for (int i2 = 0; i2 < pointerCount; i2++) {
            int pointerId = MotionEventCompat.getPointerId(motionEvent, i2);
            float x = MotionEventCompat.getX(motionEvent, i2);
            float y = MotionEventCompat.getY(motionEvent, i2);
            if (this.f != null && this.g != null && this.f.length > pointerId && this.g.length > pointerId) {
                this.f[pointerId] = x;
                this.g[pointerId] = y;
            }
        }
    }

    public final void b(int i2) {
        if (this.a != i2) {
            this.a = i2;
            this.m.a();
            if (this.a == 0) {
                this.n = null;
            }
        }
    }

    public final boolean a(View view, int i2) {
        if (view == this.n && this.c == i2) {
            return true;
        }
        if (view == null || !this.m.a(view)) {
            return false;
        }
        this.c = i2;
        b(view, i2);
        return true;
    }

    public final void b(float f2, float f3, int i2) {
        boolean a2 = a(f2, f3, i2, 1);
        if (a(f3, f2, i2, 4)) {
            a2 |= true;
        }
        if (a(f2, f3, i2, 2)) {
            a2 |= true;
        }
        if (a(f3, f2, i2, 8)) {
            a2 |= true;
        }
        if (a2) {
            int[] iArr = this.r;
            iArr[i2] = iArr[i2] | a2;
        }
    }

    private boolean a(float f2, float f3, int i2, int i3) {
        float abs = Math.abs(f2);
        float abs2 = Math.abs(f3);
        if ((this.h[i2] & i3) != i3 || (this.k & i3) == 0 || (this.s[i2] & i3) == i3 || (this.r[i2] & i3) == i3 || ((abs <= ((float) this.b) && abs2 <= ((float) this.b)) || (this.r[i2] & i3) != 0 || abs <= ((float) this.b))) {
            return false;
        }
        return true;
    }

    public final boolean a(View view, float f2) {
        if (view == null) {
            return false;
        }
        return (this.m.c() > 0) && Math.abs(f2) > ((float) this.b);
    }

    public final void c() {
        this.i.computeCurrentVelocity(1000, this.u);
        a(VelocityTrackerCompat.getXVelocity(this.i, this.c), this.j, this.u);
        a(a(VelocityTrackerCompat.getYVelocity(this.i, this.c), this.j, this.u));
    }

    public static boolean a(View view, int i2, int i3) {
        if (view != null && i2 >= view.getLeft() && i2 < view.getRight() && i3 >= view.getTop() && i3 < view.getBottom()) {
            return true;
        }
        return false;
    }

    public final View a(int i2, int i3) {
        for (int childCount = this.p.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.p.getChildAt(childCount);
            if (i2 >= childAt.getLeft() && i2 < childAt.getRight() && i3 >= childAt.getTop() && i3 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    private int b(int i2, int i3) {
        int i4 = i2 < this.p.getLeft() + this.v ? 1 : 0;
        if (i3 < this.p.getTop() + this.v) {
            i4 |= 4;
        }
        if (i2 > this.p.getRight() - this.v) {
            i4 |= 2;
        }
        return i3 > this.p.getBottom() - this.v ? i4 | 8 : i4;
    }

    public static erk a(ViewGroup viewGroup, float f2, Interpolator interpolator, a aVar) {
        erk erk = new erk(viewGroup.getContext(), viewGroup, interpolator, aVar);
        erk.b = (int) (((float) erk.b) * (1.0f / f2));
        return erk;
    }
}
