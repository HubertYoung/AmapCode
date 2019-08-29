package defpackage;

import com.autonavi.minimap.R.drawable;
import java.lang.reflect.Field;

/* renamed from: cgm reason: default package */
/* compiled from: ResUtil */
public final class cgm {
    public static int a(String str) {
        Field field;
        try {
            field = drawable.class.getField(str);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            field = null;
        }
        if (field == null) {
            return 0;
        }
        try {
            return field.getInt(null);
        } catch (IllegalAccessException | IllegalArgumentException e2) {
            e2.printStackTrace();
            return 0;
        }
    }
}
