package android.support.v4.view;

import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewCompatEclairMr1 {
    private static Method a;

    ViewCompatEclairMr1() {
    }

    public static boolean a(View view) {
        return view.isOpaque();
    }

    public static void a(ViewGroup viewGroup, boolean z) {
        if (a == null) {
            try {
                a = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
            } catch (NoSuchMethodException unused) {
            }
            a.setAccessible(true);
        }
        try {
            a.invoke(viewGroup, new Object[]{Boolean.valueOf(z)});
        } catch (IllegalAccessException | IllegalArgumentException unused2) {
        } catch (InvocationTargetException unused3) {
        }
    }
}
