package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

class AccessibilityNodeInfoCompatJellybeanMr2 {
    AccessibilityNodeInfoCompatJellybeanMr2() {
    }

    public static void a(Object obj, String str) {
        ((AccessibilityNodeInfo) obj).setViewIdResourceName(str);
    }

    public static String a(Object obj) {
        return ((AccessibilityNodeInfo) obj).getViewIdResourceName();
    }

    public static List<Object> b(Object obj, String str) {
        return ((AccessibilityNodeInfo) obj).findAccessibilityNodeInfosByViewId(str);
    }

    public static void a(Object obj, int i, int i2) {
        ((AccessibilityNodeInfo) obj).setTextSelection(i, i2);
    }

    public static int b(Object obj) {
        return ((AccessibilityNodeInfo) obj).getTextSelectionStart();
    }

    public static int c(Object obj) {
        return ((AccessibilityNodeInfo) obj).getTextSelectionEnd();
    }

    public static boolean d(Object obj) {
        return ((AccessibilityNodeInfo) obj).isEditable();
    }

    public static void a(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setEditable(z);
    }

    public static boolean e(Object obj) {
        return ((AccessibilityNodeInfo) obj).refresh();
    }
}
