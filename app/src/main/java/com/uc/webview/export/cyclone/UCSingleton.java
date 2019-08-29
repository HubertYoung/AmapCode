package com.uc.webview.export.cyclone;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Constant
/* compiled from: ProGuard */
public class UCSingleton<T> {
    private Class<?> mClazz;
    private Class<?>[] mConstructorParameterTypes;
    private Object mInst = null;

    public UCSingleton(Class<?> cls, Class<?>... clsArr) {
        this.mClazz = cls;
        this.mConstructorParameterTypes = clsArr;
    }

    public T initInst(Object... objArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (this.mInst == null) {
            synchronized (this) {
                try {
                    if (this.mInst == null) {
                        Constructor<?> constructor = this.mClazz.getConstructor(this.mConstructorParameterTypes);
                        if (!constructor.isAccessible()) {
                            constructor.setAccessible(true);
                        }
                        this.mInst = constructor.newInstance(objArr);
                    }
                }
            }
        }
        return this.mInst;
    }

    public T getInst() {
        return this.mInst;
    }
}
