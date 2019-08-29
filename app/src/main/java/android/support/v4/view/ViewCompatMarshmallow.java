package android.support.v4.view;

import android.view.View;

class ViewCompatMarshmallow {
    ViewCompatMarshmallow() {
    }

    public static void a(View view, int i) {
        view.setScrollIndicators(i);
    }

    public static void a(View view, int i, int i2) {
        view.setScrollIndicators(i, i2);
    }

    public static int a(View view) {
        return view.getScrollIndicators();
    }
}
