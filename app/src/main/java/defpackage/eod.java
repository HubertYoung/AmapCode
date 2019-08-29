package defpackage;

import android.app.Activity;
import android.content.Context;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* renamed from: eod reason: default package */
/* compiled from: FloatingManager */
public final class eod {
    public static Activity a() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            for (Object next : ((Map) declaredField.get(invoke)).values()) {
                Class<?> cls2 = next.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(next)) {
                    Field declaredField3 = cls2.getDeclaredField(WidgetType.ACTIVITY);
                    declaredField3.setAccessible(true);
                    return (Activity) declaredField3.get(next);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (NoSuchFieldException e4) {
            e4.printStackTrace();
        } catch (IllegalAccessException e5) {
            e5.printStackTrace();
        }
        return null;
    }

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        try {
            Activity a = a();
            if (a == null) {
                return false;
            }
            eoj.a(context).a(a);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
