package android.support.v4.widget;

import android.widget.PopupWindow;
import java.lang.reflect.Method;

class PopupWindowCompatGingerbread {
    private static Method a;
    private static boolean b;
    private static Method c;
    private static boolean d;

    PopupWindowCompatGingerbread() {
    }

    static void a(PopupWindow popupWindow, int i) {
        if (!b) {
            try {
                Method declaredMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", new Class[]{Integer.TYPE});
                a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Exception unused) {
            }
            b = true;
        }
        if (a != null) {
            try {
                a.invoke(popupWindow, new Object[]{Integer.valueOf(i)});
            } catch (Exception unused2) {
            }
        }
    }

    static int a(PopupWindow popupWindow) {
        if (!d) {
            try {
                Method declaredMethod = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
                c = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Exception unused) {
            }
            d = true;
        }
        if (c != null) {
            try {
                return ((Integer) c.invoke(popupWindow, new Object[0])).intValue();
            } catch (Exception unused2) {
            }
        }
        return 0;
    }
}
