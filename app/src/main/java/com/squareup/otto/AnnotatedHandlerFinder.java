package com.squareup.otto;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class AnnotatedHandlerFinder {
    private static final ConcurrentMap<Class<?>, Map<Class<?>, Method>> PRODUCERS_CACHE = new ConcurrentHashMap();
    private static final ConcurrentMap<Class<?>, Map<Class<?>, Set<Method>>> SUBSCRIBERS_CACHE = new ConcurrentHashMap();

    private static void loadAnnotatedProducerMethods(Class<?> cls, Map<Class<?>, Method> map) {
        loadAnnotatedMethods(cls, map, new HashMap());
    }

    private static void loadAnnotatedSubscriberMethods(Class<?> cls, Map<Class<?>, Set<Method>> map) {
        loadAnnotatedMethods(cls, new HashMap(), map);
    }

    private static void loadAnnotatedMethods(Class<?> cls, Map<Class<?>, Method> map, Map<Class<?>, Set<Method>> map2) {
        Method[] declaredMethods;
        for (Method method : cls.getDeclaredMethods()) {
            if (!method.isBridge()) {
                if (method.isAnnotationPresent(Subscribe.class)) {
                    Class[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        StringBuilder sb = new StringBuilder("Method ");
                        sb.append(method);
                        sb.append(" has @Subscribe annotation but requires ");
                        sb.append(parameterTypes.length);
                        sb.append(" arguments.  Methods must require a single argument.");
                        throw new IllegalArgumentException(sb.toString());
                    }
                    Class cls2 = parameterTypes[0];
                    if (cls2.isInterface()) {
                        StringBuilder sb2 = new StringBuilder("Method ");
                        sb2.append(method);
                        sb2.append(" has @Subscribe annotation on ");
                        sb2.append(cls2);
                        sb2.append(" which is an interface.  Subscription must be on a concrete class type.");
                        throw new IllegalArgumentException(sb2.toString());
                    } else if ((1 & method.getModifiers()) == 0) {
                        StringBuilder sb3 = new StringBuilder("Method ");
                        sb3.append(method);
                        sb3.append(" has @Subscribe annotation on ");
                        sb3.append(cls2);
                        sb3.append(" but is not 'public'.");
                        throw new IllegalArgumentException(sb3.toString());
                    } else {
                        Set set = map2.get(cls2);
                        if (set == null) {
                            set = new HashSet();
                            map2.put(cls2, set);
                        }
                        set.add(method);
                    }
                } else if (method.isAnnotationPresent(Produce.class)) {
                    Class[] parameterTypes2 = method.getParameterTypes();
                    if (parameterTypes2.length != 0) {
                        StringBuilder sb4 = new StringBuilder("Method ");
                        sb4.append(method);
                        sb4.append("has @Produce annotation but requires ");
                        sb4.append(parameterTypes2.length);
                        sb4.append(" arguments.  Methods must require zero arguments.");
                        throw new IllegalArgumentException(sb4.toString());
                    } else if (method.getReturnType() == Void.class) {
                        StringBuilder sb5 = new StringBuilder("Method ");
                        sb5.append(method);
                        sb5.append(" has a return type of void.  Must declare a non-void type.");
                        throw new IllegalArgumentException(sb5.toString());
                    } else {
                        Class<?> returnType = method.getReturnType();
                        if (returnType.isInterface()) {
                            StringBuilder sb6 = new StringBuilder("Method ");
                            sb6.append(method);
                            sb6.append(" has @Produce annotation on ");
                            sb6.append(returnType);
                            sb6.append(" which is an interface.  Producers must return a concrete class type.");
                            throw new IllegalArgumentException(sb6.toString());
                        } else if (returnType.equals(Void.TYPE)) {
                            StringBuilder sb7 = new StringBuilder("Method ");
                            sb7.append(method);
                            sb7.append(" has @Produce annotation but has no return type.");
                            throw new IllegalArgumentException(sb7.toString());
                        } else if ((1 & method.getModifiers()) == 0) {
                            StringBuilder sb8 = new StringBuilder("Method ");
                            sb8.append(method);
                            sb8.append(" has @Produce annotation on ");
                            sb8.append(returnType);
                            sb8.append(" but is not 'public'.");
                            throw new IllegalArgumentException(sb8.toString());
                        } else if (map.containsKey(returnType)) {
                            StringBuilder sb9 = new StringBuilder("Producer for type ");
                            sb9.append(returnType);
                            sb9.append(" has already been registered.");
                            throw new IllegalArgumentException(sb9.toString());
                        } else {
                            map.put(returnType, method);
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        PRODUCERS_CACHE.put(cls, map);
        SUBSCRIBERS_CACHE.put(cls, map2);
    }

    static Map<Class<?>, EventProducer> findAllProducers(Object obj) {
        Class<?> cls = obj.getClass();
        HashMap hashMap = new HashMap();
        Map map = (Map) PRODUCERS_CACHE.get(cls);
        if (map == null) {
            map = new HashMap();
            loadAnnotatedProducerMethods(cls, map);
        }
        if (!map.isEmpty()) {
            for (Entry entry : map.entrySet()) {
                hashMap.put(entry.getKey(), new EventProducer(obj, (Method) entry.getValue()));
            }
        }
        return hashMap;
    }

    static Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object obj) {
        Class<?> cls = obj.getClass();
        HashMap hashMap = new HashMap();
        Map map = (Map) SUBSCRIBERS_CACHE.get(cls);
        if (map == null) {
            map = new HashMap();
            loadAnnotatedSubscriberMethods(cls, map);
        }
        if (!map.isEmpty()) {
            for (Entry entry : map.entrySet()) {
                HashSet hashSet = new HashSet();
                for (Method eventHandler : (Set) entry.getValue()) {
                    hashSet.add(new EventHandler(obj, eventHandler));
                }
                hashMap.put(entry.getKey(), hashSet);
            }
        }
        return hashMap;
    }

    private AnnotatedHandlerFinder() {
    }
}
