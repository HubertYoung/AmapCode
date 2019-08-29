package android.support.v4.widget;

import android.widget.PopupWindow;

class PopupWindowCompatApi23 {
    PopupWindowCompatApi23() {
    }

    static void a(PopupWindow popupWindow, boolean z) {
        popupWindow.setOverlapAnchor(z);
    }

    static boolean a(PopupWindow popupWindow) {
        return popupWindow.getOverlapAnchor();
    }

    static void a(PopupWindow popupWindow, int i) {
        popupWindow.setWindowLayoutType(i);
    }

    static int b(PopupWindow popupWindow) {
        return popupWindow.getWindowLayoutType();
    }
}
