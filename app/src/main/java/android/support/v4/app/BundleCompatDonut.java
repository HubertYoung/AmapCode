package android.support.v4.app;

import android.os.Bundle;
import android.os.IBinder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class BundleCompatDonut {
    private static Method a;
    private static boolean b;
    private static Method c;
    private static boolean d;

    BundleCompatDonut() {
    }

    public static IBinder a(Bundle bundle, String str) {
        if (!b) {
            try {
                Method method = Bundle.class.getMethod("getIBinder", new Class[]{String.class});
                a = method;
                method.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            b = true;
        }
        if (a != null) {
            try {
                return (IBinder) a.invoke(bundle, new Object[]{str});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused2) {
                a = null;
            }
        }
        return null;
    }

    public static void a(Bundle bundle, String str, IBinder iBinder) {
        if (!d) {
            try {
                Method method = Bundle.class.getMethod("putIBinder", new Class[]{String.class, IBinder.class});
                c = method;
                method.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            d = true;
        }
        if (c != null) {
            try {
                c.invoke(bundle, new Object[]{str, iBinder});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused2) {
                c = null;
            }
        }
    }
}
