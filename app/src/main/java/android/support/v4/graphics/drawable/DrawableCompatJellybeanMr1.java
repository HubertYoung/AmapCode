package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;
import java.lang.reflect.Method;

class DrawableCompatJellybeanMr1 {
    private static Method a;
    private static boolean b;
    private static Method c;
    private static boolean d;

    DrawableCompatJellybeanMr1() {
    }

    public static void a(Drawable drawable, int i) {
        if (!b) {
            try {
                Method declaredMethod = Drawable.class.getDeclaredMethod("setLayoutDirection", new Class[]{Integer.TYPE});
                a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            b = true;
        }
        if (a != null) {
            try {
                a.invoke(drawable, new Object[]{Integer.valueOf(i)});
            } catch (Exception unused2) {
                a = null;
            }
        }
    }

    public static int a(Drawable drawable) {
        if (!d) {
            try {
                Method declaredMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                c = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            d = true;
        }
        if (c != null) {
            try {
                return ((Integer) c.invoke(drawable, new Object[0])).intValue();
            } catch (Exception unused2) {
                c = null;
            }
        }
        return -1;
    }
}
