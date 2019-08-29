package android.support.v4.view;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.view.View;

class ViewCompatHC {
    ViewCompatHC() {
    }

    static long a() {
        return ValueAnimator.getFrameDelay();
    }

    public static float a(View view) {
        return view.getAlpha();
    }

    public static void a(View view, int i, Paint paint) {
        view.setLayerType(i, paint);
    }

    public static int b(View view) {
        return view.getLayerType();
    }

    public static int a(int i, int i2, int i3) {
        return View.resolveSizeAndState(i, i2, i3);
    }

    public static int c(View view) {
        return view.getMeasuredWidthAndState();
    }

    public static int d(View view) {
        return view.getMeasuredHeightAndState();
    }

    public static int e(View view) {
        return view.getMeasuredState();
    }

    public static float f(View view) {
        return view.getTranslationX();
    }

    public static float g(View view) {
        return view.getTranslationY();
    }

    public static float h(View view) {
        return view.getX();
    }

    public static float i(View view) {
        return view.getY();
    }

    public static float j(View view) {
        return view.getRotation();
    }

    public static float k(View view) {
        return view.getRotationX();
    }

    public static float l(View view) {
        return view.getRotationY();
    }

    public static float m(View view) {
        return view.getScaleX();
    }

    public static float n(View view) {
        return view.getScaleY();
    }

    public static void a(View view, float f) {
        view.setTranslationX(f);
    }

    public static void b(View view, float f) {
        view.setTranslationY(f);
    }

    public static void c(View view, float f) {
        view.setAlpha(f);
    }

    public static void d(View view, float f) {
        view.setX(f);
    }

    public static void e(View view, float f) {
        view.setY(f);
    }

    public static void f(View view, float f) {
        view.setRotation(f);
    }

    public static void g(View view, float f) {
        view.setRotationX(f);
    }

    public static void h(View view, float f) {
        view.setRotationY(f);
    }

    public static void i(View view, float f) {
        view.setScaleX(f);
    }

    public static void j(View view, float f) {
        view.setScaleY(f);
    }

    public static void k(View view, float f) {
        view.setPivotX(f);
    }

    public static void l(View view, float f) {
        view.setPivotY(f);
    }

    public static float o(View view) {
        return view.getPivotX();
    }

    public static float p(View view) {
        return view.getPivotY();
    }

    public static void q(View view) {
        view.jumpDrawablesToCurrentState();
    }

    public static void a(View view, boolean z) {
        view.setSaveFromParentEnabled(z);
    }

    public static void b(View view, boolean z) {
        view.setActivated(z);
    }

    public static int a(int i, int i2) {
        return View.combineMeasuredStates(i, i2);
    }
}
