package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class ViewPropertyAnimatorCompat {
    static final ViewPropertyAnimatorCompatImpl IMPL;
    static final int LISTENER_TAG_ID = 2113929216;
    private static final String TAG = "ViewAnimatorCompat";
    /* access modifiers changed from: private */
    public Runnable mEndAction = null;
    /* access modifiers changed from: private */
    public int mOldLayerType = -1;
    /* access modifiers changed from: private */
    public Runnable mStartAction = null;
    private WeakReference<View> mView;

    static class BaseViewPropertyAnimatorCompatImpl implements ViewPropertyAnimatorCompatImpl {
        WeakHashMap<View, Runnable> a = null;

        class Starter implements Runnable {
            WeakReference<View> a;
            ViewPropertyAnimatorCompat b;

            /* synthetic */ Starter(BaseViewPropertyAnimatorCompatImpl baseViewPropertyAnimatorCompatImpl, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, byte b2) {
                this(viewPropertyAnimatorCompat, view);
            }

            private Starter(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
                this.a = new WeakReference<>(view);
                this.b = viewPropertyAnimatorCompat;
            }

            public void run() {
                View view = (View) this.a.get();
                if (view != null) {
                    BaseViewPropertyAnimatorCompatImpl.this.d(this.b, view);
                }
            }
        }

        public long a(View view) {
            return 0;
        }

        public void a(View view, float f) {
        }

        public void a(View view, long j) {
        }

        public void a(View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        }

        public void a(View view, Interpolator interpolator) {
        }

        public Interpolator b(View view) {
            return null;
        }

        public void b(View view, float f) {
        }

        public void b(View view, long j) {
        }

        public long c(View view) {
            return 0;
        }

        public void c(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
        }

        public void c(View view, float f) {
        }

        public void d(View view, float f) {
        }

        BaseViewPropertyAnimatorCompatImpl() {
        }

        public void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void c(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            viewPropertyAnimatorCompat.mEndAction = runnable;
            e(viewPropertyAnimatorCompat, view);
        }

        public void d(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void e(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void f(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void g(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void h(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void i(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void j(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void k(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void l(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void m(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void n(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void o(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void p(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void q(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void r(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void s(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void t(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            e(viewPropertyAnimatorCompat, view);
        }

        public void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            viewPropertyAnimatorCompat.mStartAction = runnable;
            e(viewPropertyAnimatorCompat, view);
        }

        public void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            view.setTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID, viewPropertyAnimatorListener);
        }

        /* access modifiers changed from: private */
        public void d(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = tag instanceof ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener) tag : null;
            Runnable access$100 = viewPropertyAnimatorCompat.mStartAction;
            Runnable access$000 = viewPropertyAnimatorCompat.mEndAction;
            if (access$100 != null) {
                access$100.run();
            }
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationStart(view);
                viewPropertyAnimatorListener.onAnimationEnd(view);
            }
            if (access$000 != null) {
                access$000.run();
            }
            if (this.a != null) {
                this.a.remove(view);
            }
        }

        private void e(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            Runnable runnable = this.a != null ? this.a.get(view) : null;
            if (runnable == null) {
                runnable = new Starter(this, viewPropertyAnimatorCompat, view, 0);
                if (this.a == null) {
                    this.a = new WeakHashMap<>();
                }
                this.a.put(view, runnable);
            }
            view.removeCallbacks(runnable);
            view.post(runnable);
        }

        public void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            if (this.a != null) {
                Runnable runnable = this.a.get(view);
                if (runnable != null) {
                    view.removeCallbacks(runnable);
                }
            }
            d(viewPropertyAnimatorCompat, view);
        }
    }

    static class ICSViewPropertyAnimatorCompatImpl extends BaseViewPropertyAnimatorCompatImpl {
        WeakHashMap<View, Integer> b = null;

        static class MyVpaListener implements ViewPropertyAnimatorListener {
            ViewPropertyAnimatorCompat a;

            MyVpaListener(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
                this.a = viewPropertyAnimatorCompat;
            }

            public void onAnimationStart(View view) {
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
                if (this.a.mOldLayerType >= 0) {
                    ViewCompat.setLayerType(view, 2, null);
                }
                if (this.a.mStartAction != null) {
                    this.a.mStartAction.run();
                }
                Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                if (tag instanceof ViewPropertyAnimatorListener) {
                    viewPropertyAnimatorListener = (ViewPropertyAnimatorListener) tag;
                }
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationStart(view);
                }
            }

            public void onAnimationEnd(View view) {
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
                if (this.a.mOldLayerType >= 0) {
                    ViewCompat.setLayerType(view, this.a.mOldLayerType, null);
                    this.a.mOldLayerType = -1;
                }
                if (this.a.mEndAction != null) {
                    this.a.mEndAction.run();
                }
                Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                if (tag instanceof ViewPropertyAnimatorListener) {
                    viewPropertyAnimatorListener = (ViewPropertyAnimatorListener) tag;
                }
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationEnd(view);
                }
            }

            public void onAnimationCancel(View view) {
                Object tag = view.getTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID);
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = tag instanceof ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener) tag : null;
                if (viewPropertyAnimatorListener != null) {
                    viewPropertyAnimatorListener.onAnimationCancel(view);
                }
            }
        }

        ICSViewPropertyAnimatorCompatImpl() {
        }

        public final void a(View view, long j) {
            ViewPropertyAnimatorCompatICS.a(view, j);
        }

        public final void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.a(view, f);
        }

        public final void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.b(view, f);
        }

        public final void c(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.c(view, f);
        }

        public final long a(View view) {
            return ViewPropertyAnimatorCompatICS.a(view);
        }

        public final void a(View view, Interpolator interpolator) {
            ViewPropertyAnimatorCompatICS.a(view, interpolator);
        }

        public final void b(View view, long j) {
            ViewPropertyAnimatorCompatICS.b(view, j);
        }

        public final long c(View view) {
            return ViewPropertyAnimatorCompatICS.b(view);
        }

        public final void d(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.d(view, f);
        }

        public final void e(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.e(view, f);
        }

        public final void f(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.f(view, f);
        }

        public final void g(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.g(view, f);
        }

        public final void h(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.h(view, f);
        }

        public final void i(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.i(view, f);
        }

        public final void j(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.j(view, f);
        }

        public final void k(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.k(view, f);
        }

        public final void l(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.l(view, f);
        }

        public final void m(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.m(view, f);
        }

        public final void n(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.n(view, f);
        }

        public final void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompatICS.c(view);
        }

        public final void o(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.o(view, f);
        }

        public final void p(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.p(view, f);
        }

        public final void q(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.q(view, f);
        }

        public final void r(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.r(view, f);
        }

        public final void s(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.s(view, f);
        }

        public final void t(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f) {
            ViewPropertyAnimatorCompatICS.t(view, f);
        }

        public final void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompatICS.d(view);
        }

        public void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            view.setTag(ViewPropertyAnimatorCompat.LISTENER_TAG_ID, viewPropertyAnimatorListener);
            ViewPropertyAnimatorCompatICS.a(view, (ViewPropertyAnimatorListener) new MyVpaListener(viewPropertyAnimatorCompat));
        }

        public void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompatICS.a(view, (ViewPropertyAnimatorListener) new MyVpaListener(viewPropertyAnimatorCompat));
            viewPropertyAnimatorCompat.mEndAction = runnable;
        }

        public void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompatICS.a(view, (ViewPropertyAnimatorListener) new MyVpaListener(viewPropertyAnimatorCompat));
            viewPropertyAnimatorCompat.mStartAction = runnable;
        }

        public void c(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            viewPropertyAnimatorCompat.mOldLayerType = ViewCompat.getLayerType(view);
            ViewPropertyAnimatorCompatICS.a(view, (ViewPropertyAnimatorListener) new MyVpaListener(viewPropertyAnimatorCompat));
        }
    }

    static class JBMr2ViewPropertyAnimatorCompatImpl extends JBViewPropertyAnimatorCompatImpl {
        JBMr2ViewPropertyAnimatorCompatImpl() {
        }

        public final Interpolator b(View view) {
            return ViewPropertyAnimatorCompatJellybeanMr2.a(view);
        }
    }

    static class JBViewPropertyAnimatorCompatImpl extends ICSViewPropertyAnimatorCompatImpl {
        JBViewPropertyAnimatorCompatImpl() {
        }

        public final void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
            ViewPropertyAnimatorCompatJB.a(view, viewPropertyAnimatorListener);
        }

        public final void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompatJB.a(view, runnable);
        }

        public final void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable) {
            ViewPropertyAnimatorCompatJB.b(view, runnable);
        }

        public final void c(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
            ViewPropertyAnimatorCompatJB.a(view);
        }
    }

    static class KitKatViewPropertyAnimatorCompatImpl extends JBMr2ViewPropertyAnimatorCompatImpl {
        KitKatViewPropertyAnimatorCompatImpl() {
        }

        public final void a(View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
            ViewPropertyAnimatorCompatKK.a(view, viewPropertyAnimatorUpdateListener);
        }
    }

    static class LollipopViewPropertyAnimatorCompatImpl extends KitKatViewPropertyAnimatorCompatImpl {
        LollipopViewPropertyAnimatorCompatImpl() {
        }

        public final void c(View view, float f) {
            ViewPropertyAnimatorCompatLollipop.a(view, f);
        }

        public final void d(View view, float f) {
            ViewPropertyAnimatorCompatLollipop.b(view, f);
        }

        public final void a(View view, float f) {
            ViewPropertyAnimatorCompatLollipop.c(view, f);
        }

        public final void b(View view, float f) {
            ViewPropertyAnimatorCompatLollipop.d(view, f);
        }
    }

    interface ViewPropertyAnimatorCompatImpl {
        long a(View view);

        void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener);

        void a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable);

        void a(View view, float f);

        void a(View view, long j);

        void a(View view, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener);

        void a(View view, Interpolator interpolator);

        Interpolator b(View view);

        void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, Runnable runnable);

        void b(View view, float f);

        void b(View view, long j);

        long c(View view);

        void c(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view);

        void c(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void c(View view, float f);

        void d(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void d(View view, float f);

        void e(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void f(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void g(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void h(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void i(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void j(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void k(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void l(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void m(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void n(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void o(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void p(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void q(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void r(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void s(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);

        void t(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view, float f);
    }

    ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference<>(view);
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            IMPL = new LollipopViewPropertyAnimatorCompatImpl();
        } else if (i >= 19) {
            IMPL = new KitKatViewPropertyAnimatorCompatImpl();
        } else if (i >= 18) {
            IMPL = new JBMr2ViewPropertyAnimatorCompatImpl();
        } else if (i >= 16) {
            IMPL = new JBViewPropertyAnimatorCompatImpl();
        } else if (i >= 14) {
            IMPL = new ICSViewPropertyAnimatorCompatImpl();
        } else {
            IMPL = new BaseViewPropertyAnimatorCompatImpl();
        }
    }

    public ViewPropertyAnimatorCompat setDuration(long j) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(view, j);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alpha(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alphaBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.d(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationX(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.b(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationY(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.c(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withEndAction(Runnable runnable) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(this, view, runnable);
        }
        return this;
    }

    public long getDuration() {
        View view = (View) this.mView.get();
        if (view != null) {
            return IMPL.a(view);
        }
        return 0;
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator interpolator) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(view, interpolator);
        }
        return this;
    }

    public Interpolator getInterpolator() {
        View view = (View) this.mView.get();
        if (view != null) {
            return IMPL.b(view);
        }
        return null;
    }

    public ViewPropertyAnimatorCompat setStartDelay(long j) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.b(view, j);
        }
        return this;
    }

    public long getStartDelay() {
        View view = (View) this.mView.get();
        if (view != null) {
            return IMPL.c(view);
        }
        return 0;
    }

    public ViewPropertyAnimatorCompat rotation(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.e(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.f(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationX(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.g(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationXBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.h(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationY(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.i(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationYBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.j(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleX(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.k(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleXBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.l(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleY(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.m(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleYBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.n(this, view, f);
        }
        return this;
    }

    public void cancel() {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(this, view);
        }
    }

    public ViewPropertyAnimatorCompat x(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.o(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat xBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.p(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat y(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.q(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat yBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.r(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationXBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.s(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationYBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.t(this, view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.d(view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZ(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.c(view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat z(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(view, f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat zBy(float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.b(view, f);
        }
        return this;
    }

    public void start() {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.b(this, view);
        }
    }

    public ViewPropertyAnimatorCompat withLayer() {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.c(this, view);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withStartAction(Runnable runnable) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.b(this, view, runnable);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(this, view, viewPropertyAnimatorListener);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setUpdateListener(ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        View view = (View) this.mView.get();
        if (view != null) {
            IMPL.a(view, viewPropertyAnimatorUpdateListener);
        }
        return this;
    }
}
