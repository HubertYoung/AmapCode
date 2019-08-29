package android.support.v4.view.accessibility;

import android.os.Parcelable;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;
import java.util.List;

class AccessibilityRecordCompatIcs {
    AccessibilityRecordCompatIcs() {
    }

    public static Object a() {
        return AccessibilityRecord.obtain();
    }

    public static Object a(Object obj) {
        return AccessibilityRecord.obtain((AccessibilityRecord) obj);
    }

    public static int b(Object obj) {
        return ((AccessibilityRecord) obj).getAddedCount();
    }

    public static CharSequence c(Object obj) {
        return ((AccessibilityRecord) obj).getBeforeText();
    }

    public static CharSequence d(Object obj) {
        return ((AccessibilityRecord) obj).getClassName();
    }

    public static CharSequence e(Object obj) {
        return ((AccessibilityRecord) obj).getContentDescription();
    }

    public static int f(Object obj) {
        return ((AccessibilityRecord) obj).getCurrentItemIndex();
    }

    public static int g(Object obj) {
        return ((AccessibilityRecord) obj).getFromIndex();
    }

    public static int h(Object obj) {
        return ((AccessibilityRecord) obj).getItemCount();
    }

    public static Parcelable i(Object obj) {
        return ((AccessibilityRecord) obj).getParcelableData();
    }

    public static int j(Object obj) {
        return ((AccessibilityRecord) obj).getRemovedCount();
    }

    public static int k(Object obj) {
        return ((AccessibilityRecord) obj).getScrollX();
    }

    public static int l(Object obj) {
        return ((AccessibilityRecord) obj).getScrollY();
    }

    public static Object m(Object obj) {
        return ((AccessibilityRecord) obj).getSource();
    }

    public static List<CharSequence> n(Object obj) {
        return ((AccessibilityRecord) obj).getText();
    }

    public static int o(Object obj) {
        return ((AccessibilityRecord) obj).getToIndex();
    }

    public static int p(Object obj) {
        return ((AccessibilityRecord) obj).getWindowId();
    }

    public static boolean q(Object obj) {
        return ((AccessibilityRecord) obj).isChecked();
    }

    public static boolean r(Object obj) {
        return ((AccessibilityRecord) obj).isEnabled();
    }

    public static boolean s(Object obj) {
        return ((AccessibilityRecord) obj).isFullScreen();
    }

    public static boolean t(Object obj) {
        return ((AccessibilityRecord) obj).isPassword();
    }

    public static boolean u(Object obj) {
        return ((AccessibilityRecord) obj).isScrollable();
    }

    public static void v(Object obj) {
        ((AccessibilityRecord) obj).recycle();
    }

    public static void a(Object obj, int i) {
        ((AccessibilityRecord) obj).setAddedCount(i);
    }

    public static void a(Object obj, CharSequence charSequence) {
        ((AccessibilityRecord) obj).setBeforeText(charSequence);
    }

    public static void a(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setChecked(z);
    }

    public static void b(Object obj, CharSequence charSequence) {
        ((AccessibilityRecord) obj).setClassName(charSequence);
    }

    public static void c(Object obj, CharSequence charSequence) {
        ((AccessibilityRecord) obj).setContentDescription(charSequence);
    }

    public static void b(Object obj, int i) {
        ((AccessibilityRecord) obj).setCurrentItemIndex(i);
    }

    public static void b(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setEnabled(z);
    }

    public static void c(Object obj, int i) {
        ((AccessibilityRecord) obj).setFromIndex(i);
    }

    public static void c(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setFullScreen(z);
    }

    public static void d(Object obj, int i) {
        ((AccessibilityRecord) obj).setItemCount(i);
    }

    public static void a(Object obj, Parcelable parcelable) {
        ((AccessibilityRecord) obj).setParcelableData(parcelable);
    }

    public static void d(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setPassword(z);
    }

    public static void e(Object obj, int i) {
        ((AccessibilityRecord) obj).setRemovedCount(i);
    }

    public static void f(Object obj, int i) {
        ((AccessibilityRecord) obj).setScrollX(i);
    }

    public static void g(Object obj, int i) {
        ((AccessibilityRecord) obj).setScrollY(i);
    }

    public static void e(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setScrollable(z);
    }

    public static void a(Object obj, View view) {
        ((AccessibilityRecord) obj).setSource(view);
    }

    public static void h(Object obj, int i) {
        ((AccessibilityRecord) obj).setToIndex(i);
    }
}
