package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityNodeInfoCompatKitKat {

    static class CollectionInfo {
        CollectionInfo() {
        }

        static int a(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) obj).getColumnCount();
        }

        static int b(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) obj).getRowCount();
        }

        static boolean c(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) obj).isHierarchical();
        }
    }

    static class CollectionItemInfo {
        CollectionItemInfo() {
        }

        static int a(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) obj).getColumnIndex();
        }

        static int b(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) obj).getColumnSpan();
        }

        static int c(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) obj).getRowIndex();
        }

        static int d(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) obj).getRowSpan();
        }

        static boolean e(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) obj).isHeading();
        }
    }

    static class RangeInfo {
        RangeInfo() {
        }

        static float a(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) obj).getCurrent();
        }

        static float b(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) obj).getMax();
        }

        static float c(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) obj).getMin();
        }

        static int d(Object obj) {
            return ((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) obj).getType();
        }
    }

    AccessibilityNodeInfoCompatKitKat() {
    }

    static int a(Object obj) {
        return ((AccessibilityNodeInfo) obj).getLiveRegion();
    }

    static void a(Object obj, int i) {
        ((AccessibilityNodeInfo) obj).setLiveRegion(i);
    }

    static Object b(Object obj) {
        return ((AccessibilityNodeInfo) obj).getCollectionInfo();
    }

    static Object c(Object obj) {
        return ((AccessibilityNodeInfo) obj).getCollectionItemInfo();
    }

    public static void a(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setCollectionInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionInfo) obj2);
    }

    public static void b(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setCollectionItemInfo((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) obj2);
    }

    static Object d(Object obj) {
        return ((AccessibilityNodeInfo) obj).getRangeInfo();
    }

    public static void c(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setRangeInfo((android.view.accessibility.AccessibilityNodeInfo.RangeInfo) obj2);
    }

    public static Object a(int i, int i2, boolean z) {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z);
    }

    public static Object a(int i, int i2, int i3, int i4, boolean z) {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z);
    }

    public static void a(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setContentInvalid(z);
    }

    public static boolean e(Object obj) {
        return ((AccessibilityNodeInfo) obj).isContentInvalid();
    }

    public static boolean f(Object obj) {
        return ((AccessibilityNodeInfo) obj).canOpenPopup();
    }

    public static void b(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setCanOpenPopup(z);
    }

    public static Bundle g(Object obj) {
        return ((AccessibilityNodeInfo) obj).getExtras();
    }

    public static int h(Object obj) {
        return ((AccessibilityNodeInfo) obj).getInputType();
    }

    public static void b(Object obj, int i) {
        ((AccessibilityNodeInfo) obj).setInputType(i);
    }

    public static boolean i(Object obj) {
        return ((AccessibilityNodeInfo) obj).isDismissable();
    }

    public static void c(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setDismissable(z);
    }

    public static boolean j(Object obj) {
        return ((AccessibilityNodeInfo) obj).isMultiLine();
    }

    public static void d(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setMultiLine(z);
    }
}
