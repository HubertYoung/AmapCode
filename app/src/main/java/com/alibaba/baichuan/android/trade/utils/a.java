package com.alibaba.baichuan.android.trade.utils;

import android.app.Activity;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class a {
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
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchFieldException e4) {
            e4.printStackTrace();
            return null;
        } catch (IllegalAccessException e5) {
            e5.printStackTrace();
            return null;
        }
        return null;
    }

    public static void a(String str, String str2, String str3) {
        if (AlibcContext.isDebugMode) {
            g.a().b(new b(str, str2, str3));
        }
    }
}
