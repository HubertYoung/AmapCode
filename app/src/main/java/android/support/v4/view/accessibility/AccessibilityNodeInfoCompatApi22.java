package android.support.v4.view.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityNodeInfoCompatApi22 {
    AccessibilityNodeInfoCompatApi22() {
    }

    public static Object a(Object obj) {
        return ((AccessibilityNodeInfo) obj).getTraversalBefore();
    }

    public static void a(Object obj, View view) {
        ((AccessibilityNodeInfo) obj).setTraversalBefore(view);
    }

    public static void a(Object obj, View view, int i) {
        ((AccessibilityNodeInfo) obj).setTraversalBefore(view, i);
    }

    public static Object b(Object obj) {
        return ((AccessibilityNodeInfo) obj).getTraversalAfter();
    }

    public static void b(Object obj, View view) {
        ((AccessibilityNodeInfo) obj).setTraversalAfter(view);
    }

    public static void b(Object obj, View view, int i) {
        ((AccessibilityNodeInfo) obj).setTraversalAfter(view, i);
    }
}
