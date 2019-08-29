package org.xidea.el.fn;

import java.lang.reflect.Method;
import org.xidea.el.Invocable;

abstract class JSObject implements Invocable {
    protected Method a;
    Class<?>[] b;

    JSObject() {
    }

    public Object a(Object obj, Object... objArr) throws Exception {
        if (this.b == null) {
            return this.a.invoke(this, new Object[]{obj, objArr});
        }
        Object[] objArr2 = new Object[this.b.length];
        int length = objArr2.length - 1;
        while (length > 0) {
            objArr2[length] = ECMA262Impl.b(objArr.length >= length ? objArr[length - 1] : null, this.b[length]);
            length--;
        }
        objArr2[0] = obj;
        return this.a.invoke(this, objArr2);
    }

    public String toString() {
        return this.a.getName();
    }

    static Object a(Object[] objArr, int i, Object obj) {
        return (i < 0 || i >= objArr.length) ? obj : objArr[i];
    }

    static Number a(Object[] objArr, int i, Number number) {
        Object a2 = a(objArr, i, (Object) number);
        if (a2 == null) {
            return null;
        }
        return ECMA262Impl.a(a2);
    }

    static String a(Object[] objArr, String str) {
        Object a2 = a(objArr, 0, (Object) str);
        if (a2 == null) {
            return null;
        }
        return ECMA262Impl.b(a2);
    }
}
