package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.View;

public abstract class OrientationHelper {
    protected final LayoutManager a;
    int b;

    public abstract int a(View view);

    public abstract void a(int i);

    public abstract int b();

    public abstract int b(View view);

    public abstract int c();

    public abstract int c(View view);

    public abstract int d();

    public abstract int d(View view);

    public abstract int e();

    public abstract int f();

    public abstract int g();

    public abstract int h();

    /* synthetic */ OrientationHelper(LayoutManager layoutManager, byte b2) {
        this(layoutManager);
    }

    private OrientationHelper(LayoutManager layoutManager) {
        this.b = Integer.MIN_VALUE;
        this.a = layoutManager;
    }

    public final int a() {
        if (Integer.MIN_VALUE == this.b) {
            return 0;
        }
        return e() - this.b;
    }

    public static OrientationHelper a(LayoutManager layoutManager, int i) {
        switch (i) {
            case 0:
                return new OrientationHelper(layoutManager) {
                    public final int c() {
                        return this.a.getWidth() - this.a.getPaddingRight();
                    }

                    public final int d() {
                        return this.a.getWidth();
                    }

                    public final void a(int i) {
                        this.a.offsetChildrenHorizontal(i);
                    }

                    public final int b() {
                        return this.a.getPaddingLeft();
                    }

                    public final int c(View view) {
                        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                        return this.a.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
                    }

                    public final int d(View view) {
                        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                        return this.a.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
                    }

                    public final int b(View view) {
                        return this.a.getDecoratedRight(view) + ((LayoutParams) view.getLayoutParams()).rightMargin;
                    }

                    public final int a(View view) {
                        return this.a.getDecoratedLeft(view) - ((LayoutParams) view.getLayoutParams()).leftMargin;
                    }

                    public final int e() {
                        return (this.a.getWidth() - this.a.getPaddingLeft()) - this.a.getPaddingRight();
                    }

                    public final int f() {
                        return this.a.getPaddingRight();
                    }

                    public final int g() {
                        return this.a.getWidthMode();
                    }

                    public final int h() {
                        return this.a.getHeightMode();
                    }
                };
            case 1:
                return new OrientationHelper(layoutManager) {
                    public final int c() {
                        return this.a.getHeight() - this.a.getPaddingBottom();
                    }

                    public final int d() {
                        return this.a.getHeight();
                    }

                    public final void a(int i) {
                        this.a.offsetChildrenVertical(i);
                    }

                    public final int b() {
                        return this.a.getPaddingTop();
                    }

                    public final int c(View view) {
                        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                        return this.a.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
                    }

                    public final int d(View view) {
                        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                        return this.a.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
                    }

                    public final int b(View view) {
                        return this.a.getDecoratedBottom(view) + ((LayoutParams) view.getLayoutParams()).bottomMargin;
                    }

                    public final int a(View view) {
                        return this.a.getDecoratedTop(view) - ((LayoutParams) view.getLayoutParams()).topMargin;
                    }

                    public final int e() {
                        return (this.a.getHeight() - this.a.getPaddingTop()) - this.a.getPaddingBottom();
                    }

                    public final int f() {
                        return this.a.getPaddingBottom();
                    }

                    public final int g() {
                        return this.a.getHeightMode();
                    }

                    public final int h() {
                        return this.a.getWidthMode();
                    }
                };
            default:
                throw new IllegalArgumentException("invalid orientation");
        }
    }
}
