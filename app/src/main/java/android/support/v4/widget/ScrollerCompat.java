package android.support.v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ScrollerCompat {
    static final int CHASE_FRAME_TIME = 16;
    private static final String TAG = "ScrollerCompat";
    ScrollerCompatImpl mImpl;
    Object mScroller;

    interface ScrollerCompatImpl {
        Object a(Context context, Interpolator interpolator);

        void a(Object obj, int i, int i2, int i3);

        void a(Object obj, int i, int i2, int i3, int i4);

        void a(Object obj, int i, int i2, int i3, int i4, int i5);

        void a(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

        void a(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

        boolean a(Object obj);

        boolean a(Object obj, int i, int i2, int i3, int i4, int i5, int i6);

        int b(Object obj);

        void b(Object obj, int i, int i2, int i3);

        int c(Object obj);

        float d(Object obj);

        boolean e(Object obj);

        void f(Object obj);

        boolean g(Object obj);

        int h(Object obj);

        int i(Object obj);
    }

    static class ScrollerCompatImplBase implements ScrollerCompatImpl {
        public final void a(Object obj, int i, int i2, int i3) {
        }

        public final boolean a(Object obj, int i, int i2, int i3, int i4, int i5, int i6) {
            return false;
        }

        public final void b(Object obj, int i, int i2, int i3) {
        }

        public final float d(Object obj) {
            return 0.0f;
        }

        public final boolean g(Object obj) {
            return false;
        }

        ScrollerCompatImplBase() {
        }

        public final Object a(Context context, Interpolator interpolator) {
            return interpolator != null ? new Scroller(context, interpolator) : new Scroller(context);
        }

        public final boolean a(Object obj) {
            return ((Scroller) obj).isFinished();
        }

        public final int b(Object obj) {
            return ((Scroller) obj).getCurrX();
        }

        public final int c(Object obj) {
            return ((Scroller) obj).getCurrY();
        }

        public final boolean e(Object obj) {
            return ((Scroller) obj).computeScrollOffset();
        }

        public final void a(Object obj, int i, int i2, int i3, int i4) {
            ((Scroller) obj).startScroll(i, i2, i3, i4);
        }

        public final void a(Object obj, int i, int i2, int i3, int i4, int i5) {
            ((Scroller) obj).startScroll(i, i2, i3, i4, i5);
        }

        public final void a(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            ((Scroller) obj).fling(i, i2, i3, i4, i5, i6, i7, i8);
        }

        public final void a(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            ((Scroller) obj).fling(i, i2, i3, i4, i5, i6, i7, i8);
        }

        public final void f(Object obj) {
            ((Scroller) obj).abortAnimation();
        }

        public final int h(Object obj) {
            return ((Scroller) obj).getFinalX();
        }

        public final int i(Object obj) {
            return ((Scroller) obj).getFinalY();
        }
    }

    static class ScrollerCompatImplGingerbread implements ScrollerCompatImpl {
        public float d(Object obj) {
            return 0.0f;
        }

        ScrollerCompatImplGingerbread() {
        }

        public final Object a(Context context, Interpolator interpolator) {
            return ScrollerCompatGingerbread.a(context, interpolator);
        }

        public final boolean a(Object obj) {
            return ScrollerCompatGingerbread.a(obj);
        }

        public final int b(Object obj) {
            return ScrollerCompatGingerbread.b(obj);
        }

        public final int c(Object obj) {
            return ScrollerCompatGingerbread.c(obj);
        }

        public final boolean e(Object obj) {
            return ScrollerCompatGingerbread.d(obj);
        }

        public final void a(Object obj, int i, int i2, int i3, int i4) {
            ScrollerCompatGingerbread.a(obj, i, i2, i3, i4);
        }

        public final void a(Object obj, int i, int i2, int i3, int i4, int i5) {
            ScrollerCompatGingerbread.a(obj, i, i2, i3, i4, i5);
        }

        public final void a(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            ScrollerCompatGingerbread.a(obj, i, i2, i3, i4, i5, i6, i7, i8);
        }

        public final void a(Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            ScrollerCompatGingerbread.a(obj, i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
        }

        public final void f(Object obj) {
            ScrollerCompatGingerbread.e(obj);
        }

        public final void a(Object obj, int i, int i2, int i3) {
            ScrollerCompatGingerbread.a(obj, i, i2, i3);
        }

        public final void b(Object obj, int i, int i2, int i3) {
            ScrollerCompatGingerbread.b(obj, i, i2, i3);
        }

        public final boolean g(Object obj) {
            return ScrollerCompatGingerbread.f(obj);
        }

        public final int h(Object obj) {
            return ScrollerCompatGingerbread.g(obj);
        }

        public final int i(Object obj) {
            return ScrollerCompatGingerbread.h(obj);
        }

        public final boolean a(Object obj, int i, int i2, int i3, int i4, int i5, int i6) {
            return ScrollerCompatGingerbread.a(obj, i, i2, i3, i4, i5, i6);
        }
    }

    static class ScrollerCompatImplIcs extends ScrollerCompatImplGingerbread {
        ScrollerCompatImplIcs() {
        }

        public final float d(Object obj) {
            return ScrollerCompatIcs.a(obj);
        }
    }

    public static ScrollerCompat create(Context context) {
        return create(context, null);
    }

    public static ScrollerCompat create(Context context, Interpolator interpolator) {
        return new ScrollerCompat(context, interpolator);
    }

    ScrollerCompat(Context context, Interpolator interpolator) {
        this(VERSION.SDK_INT, context, interpolator);
    }

    private ScrollerCompat(int i, Context context, Interpolator interpolator) {
        if (i >= 14) {
            this.mImpl = new ScrollerCompatImplIcs();
        } else if (i >= 9) {
            this.mImpl = new ScrollerCompatImplGingerbread();
        } else {
            this.mImpl = new ScrollerCompatImplBase();
        }
        this.mScroller = this.mImpl.a(context, interpolator);
    }

    public boolean isFinished() {
        return this.mImpl.a(this.mScroller);
    }

    public int getCurrX() {
        return this.mImpl.b(this.mScroller);
    }

    public int getCurrY() {
        return this.mImpl.c(this.mScroller);
    }

    public int getFinalX() {
        return this.mImpl.h(this.mScroller);
    }

    public int getFinalY() {
        return this.mImpl.i(this.mScroller);
    }

    public float getCurrVelocity() {
        return this.mImpl.d(this.mScroller);
    }

    public boolean computeScrollOffset() {
        return this.mImpl.e(this.mScroller);
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        this.mImpl.a(this.mScroller, i, i2, i3, i4);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mImpl.a(this.mScroller, i, i2, i3, i4, i5);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mImpl.a(this.mScroller, i, i2, i3, i4, i5, i6, i7, i8);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.mImpl.a(this.mScroller, i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.mImpl.a(this.mScroller, i, i2, i3, i4, i5, i6);
    }

    public void abortAnimation() {
        this.mImpl.f(this.mScroller);
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        this.mImpl.a(this.mScroller, i, i2, i3);
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        this.mImpl.b(this.mScroller, i, i2, i3);
    }

    public boolean isOverScrolled() {
        return this.mImpl.g(this.mScroller);
    }
}
