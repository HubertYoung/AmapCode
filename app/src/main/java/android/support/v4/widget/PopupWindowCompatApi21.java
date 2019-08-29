package android.support.v4.widget;

import android.widget.PopupWindow;
import java.lang.reflect.Field;

class PopupWindowCompatApi21 {
    private static Field a;

    PopupWindowCompatApi21() {
    }

    static {
        try {
            Field declaredField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
            a = declaredField;
            declaredField.setAccessible(true);
        } catch (NoSuchFieldException unused) {
        }
    }

    static void a(PopupWindow popupWindow, boolean z) {
        if (a != null) {
            try {
                a.set(popupWindow, Boolean.valueOf(z));
            } catch (IllegalAccessException unused) {
            }
        }
    }

    static boolean a(PopupWindow popupWindow) {
        if (a != null) {
            try {
                return ((Boolean) a.get(popupWindow)).booleanValue();
            } catch (IllegalAccessException unused) {
            }
        }
        return false;
    }
}
