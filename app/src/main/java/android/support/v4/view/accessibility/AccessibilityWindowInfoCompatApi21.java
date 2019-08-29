package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityWindowInfo;

class AccessibilityWindowInfoCompatApi21 {
    AccessibilityWindowInfoCompatApi21() {
    }

    public static Object a() {
        return AccessibilityWindowInfo.obtain();
    }

    public static Object a(Object obj) {
        return AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) obj);
    }

    public static int b(Object obj) {
        return ((AccessibilityWindowInfo) obj).getType();
    }

    public static int c(Object obj) {
        return ((AccessibilityWindowInfo) obj).getLayer();
    }

    public static Object d(Object obj) {
        return ((AccessibilityWindowInfo) obj).getRoot();
    }

    public static Object e(Object obj) {
        return ((AccessibilityWindowInfo) obj).getParent();
    }

    public static int f(Object obj) {
        return ((AccessibilityWindowInfo) obj).getId();
    }

    public static void a(Object obj, Rect rect) {
        ((AccessibilityWindowInfo) obj).getBoundsInScreen(rect);
    }

    public static boolean g(Object obj) {
        return ((AccessibilityWindowInfo) obj).isActive();
    }

    public static boolean h(Object obj) {
        return ((AccessibilityWindowInfo) obj).isFocused();
    }

    public static boolean i(Object obj) {
        return ((AccessibilityWindowInfo) obj).isAccessibilityFocused();
    }

    public static int j(Object obj) {
        return ((AccessibilityWindowInfo) obj).getChildCount();
    }

    public static Object a(Object obj, int i) {
        return ((AccessibilityWindowInfo) obj).getChild(i);
    }

    public static void k(Object obj) {
        ((AccessibilityWindowInfo) obj).recycle();
    }
}
