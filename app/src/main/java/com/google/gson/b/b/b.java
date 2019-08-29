package com.google.gson.b.b;

import com.google.gson.b.e;
import java.lang.reflect.AccessibleObject;

/* compiled from: ReflectionAccessor */
public abstract class b {
    private static final b a = (e.a() < 9 ? new a() : new c());

    public abstract void a(AccessibleObject accessibleObject);

    public static b a() {
        return a;
    }
}
