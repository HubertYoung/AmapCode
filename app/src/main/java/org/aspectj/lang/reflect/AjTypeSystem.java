package org.aspectj.lang.reflect;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import org.aspectj.internal.lang.reflect.AjTypeImpl;

public class AjTypeSystem {
    private static Map<Class, WeakReference<AjType>> a = Collections.synchronizedMap(new WeakHashMap());

    public static <T> AjType<T> getAjType(Class<T> fromClass) {
        WeakReference weakRefToAjType = a.get(fromClass);
        if (weakRefToAjType != null) {
            AjType theAjType = (AjType) weakRefToAjType.get();
            if (theAjType != null) {
                return theAjType;
            }
            AjTypeImpl ajTypeImpl = new AjTypeImpl(fromClass);
            a.put(fromClass, new WeakReference(ajTypeImpl));
            return ajTypeImpl;
        }
        AjTypeImpl ajTypeImpl2 = new AjTypeImpl(fromClass);
        a.put(fromClass, new WeakReference(ajTypeImpl2));
        return ajTypeImpl2;
    }
}
