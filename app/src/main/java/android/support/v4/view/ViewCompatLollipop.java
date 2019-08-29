package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;

class ViewCompatLollipop {
    ViewCompatLollipop() {
    }

    public static void a(View view, String str) {
        view.setTransitionName(str);
    }

    public static String a(View view) {
        return view.getTransitionName();
    }

    public static void b(View view) {
        view.requestApplyInsets();
    }

    public static void a(View view, float f) {
        view.setElevation(f);
    }

    public static float c(View view) {
        return view.getElevation();
    }

    public static void b(View view, float f) {
        view.setTranslationZ(f);
    }

    public static float d(View view) {
        return view.getTranslationZ();
    }

    public static void a(View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        view.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return ((WindowInsetsCompatApi21) onApplyWindowInsetsListener.onApplyWindowInsets(view, new WindowInsetsCompatApi21(windowInsets))).a;
            }
        });
    }

    static ColorStateList e(View view) {
        return view.getBackgroundTintList();
    }

    static void a(View view, ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
    }

    static Mode f(View view) {
        return view.getBackgroundTintMode();
    }

    static void a(View view, Mode mode) {
        view.setBackgroundTintMode(mode);
    }

    public static WindowInsetsCompat a(View view, WindowInsetsCompat windowInsetsCompat) {
        if (!(windowInsetsCompat instanceof WindowInsetsCompatApi21)) {
            return windowInsetsCompat;
        }
        WindowInsets windowInsets = ((WindowInsetsCompatApi21) windowInsetsCompat).a;
        WindowInsets onApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        return onApplyWindowInsets != windowInsets ? new WindowInsetsCompatApi21(onApplyWindowInsets) : windowInsetsCompat;
    }

    public static WindowInsetsCompat b(View view, WindowInsetsCompat windowInsetsCompat) {
        if (!(windowInsetsCompat instanceof WindowInsetsCompatApi21)) {
            return windowInsetsCompat;
        }
        WindowInsets windowInsets = ((WindowInsetsCompatApi21) windowInsetsCompat).a;
        WindowInsets dispatchApplyWindowInsets = view.dispatchApplyWindowInsets(windowInsets);
        return dispatchApplyWindowInsets != windowInsets ? new WindowInsetsCompatApi21(dispatchApplyWindowInsets) : windowInsetsCompat;
    }

    public static void a(View view, boolean z) {
        view.setNestedScrollingEnabled(z);
    }

    public static boolean g(View view) {
        return view.isNestedScrollingEnabled();
    }

    public static boolean a(View view, int i) {
        return view.startNestedScroll(i);
    }

    public static void h(View view) {
        view.stopNestedScroll();
    }

    public static boolean i(View view) {
        return view.hasNestedScrollingParent();
    }

    public static boolean a(View view, int i, int i2, int i3, int i4, int[] iArr) {
        return view.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    public static boolean a(View view, int i, int i2, int[] iArr, int[] iArr2) {
        return view.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    public static boolean a(View view, float f, float f2, boolean z) {
        return view.dispatchNestedFling(f, f2, z);
    }

    public static boolean a(View view, float f, float f2) {
        return view.dispatchNestedPreFling(f, f2);
    }

    public static float j(View view) {
        return view.getZ();
    }
}
