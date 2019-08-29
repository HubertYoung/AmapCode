package com.alipay.mobile.beehive.eventbus;

import android.text.TextUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class SubscribeFinder {
    public static SubscribeFinder ANNOTATED = new SubscribeFinder();
    private static final ConcurrentMap<Class<?>, Map<Object, Set<Method>>> SUBSCRIBERS_CACHE = new ConcurrentHashMap();

    private void loadAnnotatedSubscriberMethods(Class<?> listenerClass, Map<Object, Set<Method>> subscriberMethods) {
        loadAnnotatedMethods(listenerClass, subscriberMethods);
    }

    private synchronized void loadAnnotatedMethods(Class<?> listenerClass, Map<Object, Set<Method>> subscriberMethods) {
        Method[] methods;
        Object obj;
        synchronized (this) {
            for (Method method : listenerClass.getMethods()) {
                if (!method.isBridge() && method.isAnnotationPresent(Subscribe.class)) {
                    Class[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length > 1) {
                        throw new IllegalArgumentException("method:" + method + "@Subscribe方法参数不能超过1个");
                    }
                    Subscribe subscribe = (Subscribe) method.getAnnotation(Subscribe.class);
                    if (!TextUtils.isEmpty(subscribe.name())) {
                        obj = subscribe.name();
                    } else if (parameterTypes.length <= 0) {
                        throw new IllegalArgumentException("method:" + method + "@Subscribe方法无参数的时候, 注解参数name不能为空");
                    } else {
                        Class eventType = parameterTypes[0];
                        if (eventType.isInterface()) {
                            throw new IllegalArgumentException("method:" + method + " @Subscribe第一个参数不能为接口类型");
                        } else if ((method.getModifiers() & 1) == 0 && (method.getModifiers() & 4) == 0) {
                            throw new IllegalArgumentException("method:" + method + " @Subscribe方法必须为public或protected");
                        } else {
                            obj = eventType;
                        }
                    }
                    Set methods2 = subscriberMethods.get(obj);
                    if (methods2 == null) {
                        methods2 = new CopyOnWriteArraySet();
                        subscriberMethods.put(obj, methods2);
                    }
                    methods2.add(method);
                }
            }
            SUBSCRIBERS_CACHE.put(listenerClass, subscriberMethods);
        }
    }

    public synchronized Map<Object, Set<EventHandler>> findAllSubscribers(Object subscriber, SubscriberConfig config) {
        Map subscriberMap;
        try {
            subscriberMap = new HashMap();
            Map methods = findSubscriberMethods(subscriber);
            if (!methods.isEmpty()) {
                for (Entry e : methods.entrySet()) {
                    Set handlers = new CopyOnWriteArraySet();
                    for (Method m : (Set) e.getValue()) {
                        Subscribe s = (Subscribe) m.getAnnotation(Subscribe.class);
                        String tm = s.threadMode();
                        if (TextUtils.isEmpty(tm)) {
                            tm = ThreadMode.CURRENT.name();
                        }
                        EventHandler eh = new EventHandler(e.getKey(), subscriber, m, ThreadMode.fromString(tm), config);
                        eh.setWhiteListKey(s.whiteListKey());
                        handlers.add(eh);
                    }
                    subscriberMap.put(e.getKey(), handlers);
                }
            }
        }
        return subscriberMap;
    }

    public Map<Object, Set<Method>> findSubscriberMethods(Object subscriber) {
        Class cls = subscriber.getClass();
        Map methods = (Map) SUBSCRIBERS_CACHE.get(cls);
        if (methods != null) {
            return methods;
        }
        Map methods2 = new HashMap();
        loadAnnotatedSubscriberMethods(cls, methods2);
        return methods2;
    }

    private SubscribeFinder() {
    }

    public void dispose() {
        SUBSCRIBERS_CACHE.clear();
    }
}
