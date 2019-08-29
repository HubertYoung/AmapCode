package defpackage;

import java.util.HashMap;
import java.util.Map;

/* renamed from: bqn reason: default package */
/* compiled from: AnnotationServiceFactory */
public final class bqn {
    private static final Map<Class, Object> a = new HashMap();

    public static <T> T a(Class<T> cls) {
        Object obj = a.get(cls);
        if (obj != null && cls.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        String name = cls.getPackage().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(".impl");
        String sb2 = sb.toString();
        String simpleName = cls.getSimpleName();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(simpleName.substring(1));
        sb3.append("Impl");
        String sb4 = sb3.toString();
        try {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb2);
            sb5.append(".");
            sb5.append(sb4);
            obj = Class.forName(sb5.toString()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
        if (obj == null || !cls.isAssignableFrom(obj.getClass())) {
            return null;
        }
        a.put(cls, obj);
        return obj;
    }
}
