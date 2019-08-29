package defpackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build.VERSION;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/* renamed from: enl reason: default package */
/* compiled from: SoHotfixInjector */
public class enl {
    private static final String a = "enl";
    private enj b;
    private PathClassLoader c;
    private Object d;
    private Field e;
    private Field f;

    public enl(enj enj) {
        this.b = enj;
        a();
    }

    private void a() {
        try {
            Context baseContext = ((ContextWrapper) this.b.a).getBaseContext();
            if (baseContext != null) {
                Field declaredField = baseContext.getClass().getDeclaredField("mPackageInfo");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(baseContext);
                Field declaredField2 = obj.getClass().getDeclaredField("mClassLoader");
                declaredField2.setAccessible(true);
                this.c = (PathClassLoader) declaredField2.get(obj);
                String str = "nativeLibraryPathElements";
                if (VERSION.SDK_INT < 23) {
                    str = "nativeLibraryDirectories";
                }
                this.e = a(this.c.getClass(), "pathList");
                this.e.setAccessible(true);
                this.d = this.e.get(this.c);
                this.f = this.d.getClass().getDeclaredField(str);
                this.f.setAccessible(true);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void a(enm[] enmArr) {
        if (this.f == null) {
            a();
        }
        try {
            Object[] objArr = (Object[]) this.f.get(this.d);
            int length = objArr.length;
            Object[] objArr2 = (Object[]) Array.newInstance(this.f.getType().getComponentType(), length + 1);
            System.arraycopy(objArr, 0, objArr2, 1, length);
            for (int i = 0; i <= 0; i++) {
                String str = enmArr[0].b;
                objArr2[0] = ((Object[]) this.f.get(this.e.get(new DexClassLoader("", str, str, this.c.getParent()))))[0];
            }
            this.f.set(this.d, objArr2);
        } catch (Exception unused) {
        }
    }

    private static Field a(Class<? super T> cls, String str) throws NoSuchFieldException {
        while (cls != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField;
            } catch (Exception unused) {
            } finally {
                cls.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }
}
