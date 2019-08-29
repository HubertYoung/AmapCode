package org.xidea.el.fn;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.xidea.el.Invocable;

class JSArray extends JSObject implements Invocable {
    JSArray() {
    }

    public final Object a(Object obj, Object... objArr) throws Exception {
        Method method = this.a;
        Object[] objArr2 = new Object[2];
        if (obj instanceof Object[]) {
            obj = Arrays.asList((Object[]) obj);
        } else if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            List arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                arrayList.add(Array.get(obj, 1));
            }
            obj = arrayList;
        }
        objArr2[0] = obj;
        objArr2[1] = objArr;
        return method.invoke(this, objArr2);
    }
}
