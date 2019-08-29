package android.support.v4.view.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import java.util.List;

class AccessibilityNodeInfoCompatApi21 {

    static class CollectionItemInfo {
        CollectionItemInfo() {
        }

        public static boolean a(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) obj).isSelected();
        }
    }

    AccessibilityNodeInfoCompatApi21() {
    }

    static List<Object> a(Object obj) {
        return ((AccessibilityNodeInfo) obj).getActionList();
    }

    static void a(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).addAction((AccessibilityAction) obj2);
    }

    public static boolean b(Object obj, Object obj2) {
        return ((AccessibilityNodeInfo) obj).removeAction((AccessibilityAction) obj2);
    }

    public static Object a(int i, int i2, boolean z, int i3) {
        return CollectionInfo.obtain(i, i2, z, i3);
    }

    public static Object a(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2);
    }

    public static CharSequence b(Object obj) {
        return ((AccessibilityNodeInfo) obj).getError();
    }

    public static void a(Object obj, CharSequence charSequence) {
        ((AccessibilityNodeInfo) obj).setError(charSequence);
    }

    public static void a(Object obj, int i) {
        ((AccessibilityNodeInfo) obj).setMaxTextLength(i);
    }

    public static int c(Object obj) {
        return ((AccessibilityNodeInfo) obj).getMaxTextLength();
    }

    public static Object d(Object obj) {
        return ((AccessibilityNodeInfo) obj).getWindow();
    }

    public static boolean a(Object obj, View view) {
        return ((AccessibilityNodeInfo) obj).removeChild(view);
    }

    public static boolean a(Object obj, View view, int i) {
        return ((AccessibilityNodeInfo) obj).removeChild(view, i);
    }

    static Object a(int i, CharSequence charSequence) {
        return new AccessibilityAction(i, charSequence);
    }

    static int e(Object obj) {
        return ((AccessibilityAction) obj).getId();
    }

    static CharSequence f(Object obj) {
        return ((AccessibilityAction) obj).getLabel();
    }
}
