package android.support.v4.view.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityNodeInfoCompatJellybeanMr1 {
    AccessibilityNodeInfoCompatJellybeanMr1() {
    }

    public static void a(Object obj, View view) {
        ((AccessibilityNodeInfo) obj).setLabelFor(view);
    }

    public static void a(Object obj, View view, int i) {
        ((AccessibilityNodeInfo) obj).setLabelFor(view, i);
    }

    public static Object a(Object obj) {
        return ((AccessibilityNodeInfo) obj).getLabelFor();
    }

    public static void b(Object obj, View view) {
        ((AccessibilityNodeInfo) obj).setLabeledBy(view);
    }

    public static void b(Object obj, View view, int i) {
        ((AccessibilityNodeInfo) obj).setLabeledBy(view, i);
    }

    public static Object b(Object obj) {
        return ((AccessibilityNodeInfo) obj).getLabeledBy();
    }
}
