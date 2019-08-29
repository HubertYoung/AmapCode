package defpackage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/* renamed from: bpt reason: default package */
/* compiled from: ReflectUtil */
public abstract class bpt {
    private static final Map<Class<?>, Map<String, Method>> a = new HashMap();
    private static final Map<Class<?>, Map<String, Method>> b = new HashMap();
    private static final Map<Class<?>, Map<String, Type[]>> c = new HashMap();
    private static final Map<Class<?>, Map<String, Field>> d = new HashMap();
    private static final Map<Class<?>, Constructor<?>> e = new HashMap();
    private static final Map<Class<? extends Enum<?>>, Enum<?>[]> f = new HashMap();
    private static Object g = new Object();

    public static Type a(Type type, Class<?> cls) {
        return a.a(type, cls, 0);
    }
}
