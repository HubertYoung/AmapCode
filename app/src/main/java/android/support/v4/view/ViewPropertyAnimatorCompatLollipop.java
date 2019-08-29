package android.support.v4.view;

import android.view.View;

class ViewPropertyAnimatorCompatLollipop {
    ViewPropertyAnimatorCompatLollipop() {
    }

    public static void a(View view, float f) {
        view.animate().translationZ(f);
    }

    public static void b(View view, float f) {
        view.animate().translationZBy(f);
    }

    public static void c(View view, float f) {
        view.animate().z(f);
    }

    public static void d(View view, float f) {
        view.animate().zBy(f);
    }
}
