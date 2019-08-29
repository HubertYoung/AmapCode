package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.SmoothScroller;
import android.support.v7.widget.RecyclerView.SmoothScroller.Action;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public abstract class LinearSmoothScroller extends SmoothScroller {
    private final float a;
    protected final LinearInterpolator b = new LinearInterpolator();
    protected final DecelerateInterpolator c = new DecelerateInterpolator();
    protected PointF d;
    protected int e = 0;
    protected int f = 0;

    private static int a(int i, int i2) {
        int i3 = i - i2;
        if (i * i3 <= 0) {
            return 0;
        }
        return i3;
    }

    public abstract PointF a(int i);

    public LinearSmoothScroller(Context context) {
        this.a = 25.0f / ((float) context.getResources().getDisplayMetrics().densityDpi);
    }

    /* access modifiers changed from: protected */
    public final void a() {
        this.f = 0;
        this.e = 0;
        this.d = null;
    }

    private int b(int i) {
        return (int) Math.ceil((double) (((float) Math.abs(i)) * this.a));
    }

    private static int a(int i, int i2, int i3, int i4, int i5) {
        switch (i5) {
            case -1:
                return i3 - i;
            case 0:
                int i6 = i3 - i;
                if (i6 > 0) {
                    return i6;
                }
                int i7 = i4 - i2;
                if (i7 < 0) {
                    return i7;
                }
                return 0;
            case 1:
                return i4 - i2;
            default:
                throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
    }

    /* access modifiers changed from: protected */
    public final void a(View view, Action action) {
        int i;
        int i2 = -1;
        int i3 = 0;
        int i4 = (this.d == null || this.d.x == 0.0f) ? 0 : this.d.x > 0.0f ? 1 : -1;
        LayoutManager layoutManager = this.i;
        if (layoutManager == null || !layoutManager.canScrollHorizontally()) {
            i = 0;
        } else {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            i = a(layoutManager.getDecoratedLeft(view) - layoutParams.leftMargin, layoutManager.getDecoratedRight(view) + layoutParams.rightMargin, layoutManager.getPaddingLeft(), layoutManager.getWidth() - layoutManager.getPaddingRight(), i4);
        }
        if (this.d == null || this.d.y == 0.0f) {
            i2 = 0;
        } else if (this.d.y > 0.0f) {
            i2 = 1;
        }
        LayoutManager layoutManager2 = this.i;
        if (layoutManager2 != null && layoutManager2.canScrollVertically()) {
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            i3 = a(layoutManager2.getDecoratedTop(view) - layoutParams2.topMargin, layoutManager2.getDecoratedBottom(view) + layoutParams2.bottomMargin, layoutManager2.getPaddingTop(), layoutManager2.getHeight() - layoutManager2.getPaddingBottom(), i2);
        }
        int ceil = (int) Math.ceil(((double) b((int) Math.sqrt((double) ((i * i) + (i3 * i3))))) / 0.3356d);
        if (ceil > 0) {
            action.a(-i, -i3, ceil, this.c);
        }
    }

    /* access modifiers changed from: protected */
    public final void a(int i, int i2, Action action) {
        if (this.h.mLayout.getChildCount() == 0) {
            b();
            return;
        }
        this.e = a(this.e, i);
        this.f = a(this.f, i2);
        if (this.e == 0 && this.f == 0) {
            PointF a2 = a(this.g);
            if (a2 == null || (a2.x == 0.0f && a2.y == 0.0f)) {
                action.a = this.g;
                b();
                return;
            }
            double sqrt = Math.sqrt((double) ((a2.x * a2.x) + (a2.y * a2.y)));
            a2.x = (float) (((double) a2.x) / sqrt);
            a2.y = (float) (((double) a2.y) / sqrt);
            this.d = a2;
            this.e = (int) (a2.x * 10000.0f);
            this.f = (int) (a2.y * 10000.0f);
            action.a((int) (((float) this.e) * 1.2f), (int) (((float) this.f) * 1.2f), (int) (((float) b(10000)) * 1.2f), this.b);
        }
    }
}
