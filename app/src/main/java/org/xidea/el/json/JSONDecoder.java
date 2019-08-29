package org.xidea.el.json;

import java.lang.reflect.Type;
import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@Keep
public class JSONDecoder {
    private static JSONDecoder a = new JSONDecoder(false);
    private static ClassLoader b = JSONDecoder.class.getClassLoader();
    private JSONTransformer c;
    private boolean d = false;

    public @interface Transformer {
    }

    public interface TypeTransformer<T> {
        T a();
    }

    public JSONDecoder(boolean z) {
        this.d = z;
        try {
            this.c = new OldJSONTransformer();
        } catch (Throwable unused) {
            this.c = new JSONTransformer();
        }
        this.c.a(b);
        if (a != null) {
            for (TypeTransformer<? extends Object> addTransformer : a.c.b.values()) {
                addTransformer(addTransformer);
            }
        }
    }

    public static <T> T decode(String str) {
        return a.decodeObject(str, null);
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.c.a(classLoader);
    }

    public static void setDefaultClassLoader(ClassLoader classLoader) {
        b = classLoader;
    }

    public static <T> T decode(String str, Type type) {
        return a.decodeObject(str, type);
    }

    public TypeTransformer<? extends Object> addTransformer(TypeTransformer<? extends Object> typeTransformer) {
        return this.c.a(typeTransformer);
    }

    public static TypeTransformer<? extends Object> addDefaultTransformer(TypeTransformer<? extends Object> typeTransformer) {
        return a.c.a(typeTransformer);
    }

    public <T> T decodeObject(String str, Type type) {
        try {
            T a2 = new JSONTokenizer(str, this.d).a();
            return (type == null || type == Object.class) ? a2 : transform((Object) a2, type);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public <T> List<T> decodeList(String str, Class<T> cls) {
        try {
            List<T> list = (List) new JSONTokenizer(str, this.d).a();
            if (!(cls == null || cls == Object.class)) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    list.set(size, transform((Object) list.get(size), cls));
                }
            }
            return list;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public static <T> T transform(Object obj, Class<?> cls) {
        return a.c.b(obj, cls);
    }

    public <T> T transform(Object obj, Type type) {
        return this.c.b(obj, type);
    }
}
