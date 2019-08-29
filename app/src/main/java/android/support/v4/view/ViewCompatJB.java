package android.support.v4.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;

class ViewCompatJB {
    ViewCompatJB() {
    }

    public static boolean a(View view) {
        return view.hasTransientState();
    }

    public static void a(View view, boolean z) {
        view.setHasTransientState(z);
    }

    public static void b(View view) {
        view.postInvalidateOnAnimation();
    }

    public static void a(View view, int i, int i2, int i3, int i4) {
        view.postInvalidate(i, i2, i3, i4);
    }

    public static void a(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    public static void a(View view, Runnable runnable, long j) {
        view.postOnAnimationDelayed(runnable, j);
    }

    public static int c(View view) {
        return view.getImportantForAccessibility();
    }

    public static void a(View view, int i) {
        view.setImportantForAccessibility(i);
    }

    public static boolean a(View view, int i, Bundle bundle) {
        return view.performAccessibilityAction(i, bundle);
    }

    public static Object d(View view) {
        return view.getAccessibilityNodeProvider();
    }

    public static ViewParent e(View view) {
        return view.getParentForAccessibility();
    }

    public static int f(View view) {
        return view.getMinimumWidth();
    }

    public static int g(View view) {
        return view.getMinimumHeight();
    }

    public static void h(View view) {
        view.requestFitSystemWindows();
    }

    public static boolean i(View view) {
        return view.getFitsSystemWindows();
    }

    public static boolean j(View view) {
        return view.hasOverlappingRendering();
    }
}
