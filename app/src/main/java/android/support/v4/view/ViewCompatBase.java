package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import java.lang.reflect.Field;

class ViewCompatBase {
    private static Field a;
    private static boolean b;
    private static Field c;
    private static boolean d;

    ViewCompatBase() {
    }

    static ColorStateList a(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView) view).getSupportBackgroundTintList();
        }
        return null;
    }

    static void a(View view, ColorStateList colorStateList) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView) view).setSupportBackgroundTintList(colorStateList);
        }
    }

    static Mode b(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView) view).getSupportBackgroundTintMode();
        }
        return null;
    }

    static void a(View view, Mode mode) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView) view).setSupportBackgroundTintMode(mode);
        }
    }

    static boolean c(View view) {
        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    static int d(View view) {
        if (!b) {
            try {
                Field declaredField = View.class.getDeclaredField("mMinWidth");
                a = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
            }
            b = true;
        }
        if (a != null) {
            try {
                return ((Integer) a.get(view)).intValue();
            } catch (Exception unused2) {
            }
        }
        return 0;
    }

    static int e(View view) {
        if (!d) {
            try {
                Field declaredField = View.class.getDeclaredField("mMinHeight");
                c = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
            }
            d = true;
        }
        if (c != null) {
            try {
                return ((Integer) c.get(view)).intValue();
            } catch (Exception unused2) {
            }
        }
        return 0;
    }

    static boolean f(View view) {
        return view.getWindowToken() != null;
    }
}
