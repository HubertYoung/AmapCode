package android.support.v4.view;

import android.view.View;

class ViewCompatKitKat {
    ViewCompatKitKat() {
    }

    public static int a(View view) {
        return view.getAccessibilityLiveRegion();
    }

    public static void a(View view, int i) {
        view.setAccessibilityLiveRegion(i);
    }

    public static boolean b(View view) {
        return view.isLaidOut();
    }

    public static boolean c(View view) {
        return view.isAttachedToWindow();
    }
}
