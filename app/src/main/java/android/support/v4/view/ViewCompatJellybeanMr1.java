package android.support.v4.view;

import android.graphics.Paint;
import android.view.View;

class ViewCompatJellybeanMr1 {
    ViewCompatJellybeanMr1() {
    }

    public static int a(View view) {
        return view.getLabelFor();
    }

    public static void a(View view, int i) {
        view.setLabelFor(i);
    }

    public static void a(View view, Paint paint) {
        view.setLayerPaint(paint);
    }

    public static int b(View view) {
        return view.getLayoutDirection();
    }

    public static void b(View view, int i) {
        view.setLayoutDirection(i);
    }

    public static int c(View view) {
        return view.getPaddingStart();
    }

    public static int d(View view) {
        return view.getPaddingEnd();
    }

    public static void a(View view, int i, int i2, int i3, int i4) {
        view.setPaddingRelative(i, i2, i3, i4);
    }

    public static int e(View view) {
        return view.getWindowSystemUiVisibility();
    }

    public static boolean f(View view) {
        return view.isPaddingRelative();
    }
}
