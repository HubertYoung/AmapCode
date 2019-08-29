package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;

class ViewPropertyAnimatorCompatICS {
    ViewPropertyAnimatorCompatICS() {
    }

    public static void a(View view, long j) {
        view.animate().setDuration(j);
    }

    public static void a(View view, float f) {
        view.animate().alpha(f);
    }

    public static void b(View view, float f) {
        view.animate().translationX(f);
    }

    public static void c(View view, float f) {
        view.animate().translationY(f);
    }

    public static long a(View view) {
        return view.animate().getDuration();
    }

    public static void a(View view, Interpolator interpolator) {
        view.animate().setInterpolator(interpolator);
    }

    public static void b(View view, long j) {
        view.animate().setStartDelay(j);
    }

    public static long b(View view) {
        return view.animate().getStartDelay();
    }

    public static void d(View view, float f) {
        view.animate().alphaBy(f);
    }

    public static void e(View view, float f) {
        view.animate().rotation(f);
    }

    public static void f(View view, float f) {
        view.animate().rotationBy(f);
    }

    public static void g(View view, float f) {
        view.animate().rotationX(f);
    }

    public static void h(View view, float f) {
        view.animate().rotationXBy(f);
    }

    public static void i(View view, float f) {
        view.animate().rotationY(f);
    }

    public static void j(View view, float f) {
        view.animate().rotationYBy(f);
    }

    public static void k(View view, float f) {
        view.animate().scaleX(f);
    }

    public static void l(View view, float f) {
        view.animate().scaleXBy(f);
    }

    public static void m(View view, float f) {
        view.animate().scaleY(f);
    }

    public static void n(View view, float f) {
        view.animate().scaleYBy(f);
    }

    public static void c(View view) {
        view.animate().cancel();
    }

    public static void o(View view, float f) {
        view.animate().x(f);
    }

    public static void p(View view, float f) {
        view.animate().xBy(f);
    }

    public static void q(View view, float f) {
        view.animate().y(f);
    }

    public static void r(View view, float f) {
        view.animate().yBy(f);
    }

    public static void s(View view, float f) {
        view.animate().translationXBy(f);
    }

    public static void t(View view, float f) {
        view.animate().translationYBy(f);
    }

    public static void d(View view) {
        view.animate().start();
    }

    public static void a(final View view, final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.animate().setListener(new AnimatorListenerAdapter() {
            public final void onAnimationCancel(Animator animator) {
                viewPropertyAnimatorListener.onAnimationCancel(view);
            }

            public final void onAnimationEnd(Animator animator) {
                viewPropertyAnimatorListener.onAnimationEnd(view);
            }

            public final void onAnimationStart(Animator animator) {
                viewPropertyAnimatorListener.onAnimationStart(view);
            }
        });
    }
}
