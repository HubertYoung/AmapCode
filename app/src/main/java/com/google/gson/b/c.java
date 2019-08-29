package com.google.gson.b;

import com.google.gson.b.b.b;
import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.k;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/* compiled from: ConstructorConstructor */
public final class c {
    private final Map<Type, f<?>> a;
    private final b b = b.a();

    public c(Map<Type, f<?>> map) {
        this.a = map;
    }

    public final <T> i<T> a(a<T> aVar) {
        final Type b2 = aVar.b();
        Class<? super T> a2 = aVar.a();
        final f fVar = this.a.get(b2);
        if (fVar != null) {
            return new i<T>() {
                public T a() {
                    return fVar.a(b2);
                }
            };
        }
        final f fVar2 = this.a.get(a2);
        if (fVar2 != null) {
            return new i<T>() {
                public T a() {
                    return fVar2.a(b2);
                }
            };
        }
        i<T> a3 = a(a2);
        if (a3 != null) {
            return a3;
        }
        i<T> a4 = a(b2, a2);
        if (a4 != null) {
            return a4;
        }
        return b(b2, a2);
    }

    private <T> i<T> a(Class<? super T> cls) {
        try {
            final Constructor<? super T> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                this.b.a(declaredConstructor);
            }
            return new i<T>() {
                public T a() {
                    try {
                        return declaredConstructor.newInstance(null);
                    } catch (InstantiationException e) {
                        StringBuilder sb = new StringBuilder("Failed to invoke ");
                        sb.append(declaredConstructor);
                        sb.append(" with no args");
                        throw new RuntimeException(sb.toString(), e);
                    } catch (InvocationTargetException e2) {
                        StringBuilder sb2 = new StringBuilder("Failed to invoke ");
                        sb2.append(declaredConstructor);
                        sb2.append(" with no args");
                        throw new RuntimeException(sb2.toString(), e2.getTargetException());
                    } catch (IllegalAccessException e3) {
                        throw new AssertionError(e3);
                    }
                }
            };
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    private <T> i<T> a(final Type type, Class<? super T> cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            if (SortedSet.class.isAssignableFrom(cls)) {
                return new i<T>() {
                    public T a() {
                        return new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(cls)) {
                return new i<T>() {
                    public T a() {
                        if (type instanceof ParameterizedType) {
                            Type type = ((ParameterizedType) type).getActualTypeArguments()[0];
                            if (type instanceof Class) {
                                return EnumSet.noneOf((Class) type);
                            }
                            StringBuilder sb = new StringBuilder("Invalid EnumSet type: ");
                            sb.append(type.toString());
                            throw new k(sb.toString());
                        }
                        StringBuilder sb2 = new StringBuilder("Invalid EnumSet type: ");
                        sb2.append(type.toString());
                        throw new k(sb2.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(cls)) {
                return new i<T>() {
                    public T a() {
                        return new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(cls)) {
                return new i<T>() {
                    public T a() {
                        return new ArrayDeque();
                    }
                };
            }
            return new i<T>() {
                public T a() {
                    return new ArrayList();
                }
            };
        } else if (!Map.class.isAssignableFrom(cls)) {
            return null;
        } else {
            if (ConcurrentNavigableMap.class.isAssignableFrom(cls)) {
                return new i<T>() {
                    public T a() {
                        return new ConcurrentSkipListMap();
                    }
                };
            }
            if (ConcurrentMap.class.isAssignableFrom(cls)) {
                return new i<T>() {
                    public T a() {
                        return new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom(cls)) {
                return new i<T>() {
                    public T a() {
                        return new TreeMap();
                    }
                };
            }
            if (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(a.a(((ParameterizedType) type).getActualTypeArguments()[0]).a())) {
                return new i<T>() {
                    public T a() {
                        return new h();
                    }
                };
            }
            return new i<T>() {
                public T a() {
                    return new LinkedHashMap();
                }
            };
        }
    }

    private <T> i<T> b(final Type type, final Class<? super T> cls) {
        return new i<T>() {
            private final m d = m.a();

            public T a() {
                try {
                    return this.d.a(cls);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("Unable to invoke no-args constructor for ");
                    sb.append(type);
                    sb.append(". Registering an InstanceCreator with Gson for this type may fix this problem.");
                    throw new RuntimeException(sb.toString(), e);
                }
            }
        };
    }

    public final String toString() {
        return this.a.toString();
    }
}
