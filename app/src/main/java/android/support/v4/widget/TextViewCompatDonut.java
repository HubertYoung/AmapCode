package android.support.v4.widget;

import android.widget.TextView;
import java.lang.reflect.Field;

class TextViewCompatDonut {
    private static Field a;
    private static boolean b;
    private static Field c;
    private static boolean d;
    private static Field e;
    private static boolean f;
    private static Field g;
    private static boolean h;

    TextViewCompatDonut() {
    }

    static int a(TextView textView) {
        if (!d) {
            c = a((String) "mMaxMode");
            d = true;
        }
        if (c != null && a(c, textView) == 1) {
            if (!b) {
                a = a((String) "mMaximum");
                b = true;
            }
            if (a != null) {
                return a(a, textView);
            }
        }
        return -1;
    }

    static int b(TextView textView) {
        if (!h) {
            g = a((String) "mMinMode");
            h = true;
        }
        if (g != null && a(g, textView) == 1) {
            if (!f) {
                e = a((String) "mMinimum");
                f = true;
            }
            if (e != null) {
                return a(e, textView);
            }
        }
        return -1;
    }

    private static Field a(String str) {
        Field field;
        try {
            field = TextView.class.getDeclaredField(str);
            try {
                field.setAccessible(true);
            } catch (NoSuchFieldException unused) {
            }
        } catch (NoSuchFieldException unused2) {
            field = null;
            StringBuilder sb = new StringBuilder("Could not retrieve ");
            sb.append(str);
            sb.append(" field.");
            return field;
        }
        return field;
    }

    private static int a(Field field, TextView textView) {
        try {
            return field.getInt(textView);
        } catch (IllegalAccessException unused) {
            StringBuilder sb = new StringBuilder("Could not retrieve value of ");
            sb.append(field.getName());
            sb.append(" field.");
            return -1;
        }
    }
}
