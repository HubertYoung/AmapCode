package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class Bus {
    public static final String DEFAULT_IDENTIFIER = "default";
    private final ThreadEnforcer enforcer;
    private final ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>> eventsToDispatch;
    private final ConcurrentMap<Class<?>, Set<Class<?>>> flattenHierarchyCache;
    private final HandlerFinder handlerFinder;
    private final ConcurrentMap<Class<?>, Set<EventHandler>> handlersByType;
    private final String identifier;
    private final ThreadLocal<Boolean> isDispatching;
    private final ConcurrentMap<Class<?>, EventProducer> producersByType;

    static class EventWithHandler {
        final Object event;
        final EventHandler handler;

        public EventWithHandler(Object obj, EventHandler eventHandler) {
            this.event = obj;
            this.handler = eventHandler;
        }
    }

    public Bus() {
        this((String) "default");
    }

    public Bus(String str) {
        this(ThreadEnforcer.MAIN, str);
    }

    public Bus(ThreadEnforcer threadEnforcer) {
        this(threadEnforcer, "default");
    }

    public Bus(ThreadEnforcer threadEnforcer, String str) {
        this(threadEnforcer, str, HandlerFinder.ANNOTATED);
    }

    Bus(ThreadEnforcer threadEnforcer, String str, HandlerFinder handlerFinder2) {
        this.handlersByType = new ConcurrentHashMap();
        this.producersByType = new ConcurrentHashMap();
        this.eventsToDispatch = new ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>>() {
            /* access modifiers changed from: protected */
            public ConcurrentLinkedQueue<EventWithHandler> initialValue() {
                return new ConcurrentLinkedQueue<>();
            }
        };
        this.isDispatching = new ThreadLocal<Boolean>() {
            /* access modifiers changed from: protected */
            public Boolean initialValue() {
                return Boolean.FALSE;
            }
        };
        this.flattenHierarchyCache = new ConcurrentHashMap();
        this.enforcer = threadEnforcer;
        this.identifier = str;
        this.handlerFinder = handlerFinder2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[Bus \"");
        sb.append(this.identifier);
        sb.append("\"]");
        return sb.toString();
    }

    public void register(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        this.enforcer.enforce(this);
        Map<Class<?>, EventProducer> findAllProducers = this.handlerFinder.findAllProducers(obj);
        for (Class next : findAllProducers.keySet()) {
            EventProducer eventProducer = findAllProducers.get(next);
            EventProducer putIfAbsent = this.producersByType.putIfAbsent(next, eventProducer);
            if (putIfAbsent != null) {
                StringBuilder sb = new StringBuilder("Producer method for type ");
                sb.append(next);
                sb.append(" found on type ");
                sb.append(eventProducer.target.getClass());
                sb.append(", but already registered by type ");
                sb.append(putIfAbsent.target.getClass());
                sb.append(".");
                throw new IllegalArgumentException(sb.toString());
            }
            Set<EventHandler> set = (Set) this.handlersByType.get(next);
            if (set != null && !set.isEmpty()) {
                for (EventHandler dispatchProducerResultToHandler : set) {
                    dispatchProducerResultToHandler(dispatchProducerResultToHandler, eventProducer);
                }
            }
        }
        Map<Class<?>, Set<EventHandler>> findAllSubscribers = this.handlerFinder.findAllSubscribers(obj);
        for (Class next2 : findAllSubscribers.keySet()) {
            Set set2 = (Set) this.handlersByType.get(next2);
            if (set2 == null) {
                set2 = new CopyOnWriteArraySet();
                Set putIfAbsent2 = this.handlersByType.putIfAbsent(next2, set2);
                if (putIfAbsent2 != null) {
                    set2 = putIfAbsent2;
                }
            }
            if (!set2.addAll(findAllSubscribers.get(next2))) {
                throw new IllegalArgumentException("Object already registered.");
            }
        }
        for (Entry next3 : findAllSubscribers.entrySet()) {
            EventProducer eventProducer2 = (EventProducer) this.producersByType.get((Class) next3.getKey());
            if (eventProducer2 != null && eventProducer2.isValid()) {
                for (EventHandler eventHandler : (Set) next3.getValue()) {
                    if (!eventProducer2.isValid()) {
                        break;
                    } else if (eventHandler.isValid()) {
                        dispatchProducerResultToHandler(eventHandler, eventProducer2);
                    }
                }
            }
        }
    }

    private void dispatchProducerResultToHandler(EventHandler eventHandler, EventProducer eventProducer) {
        Object obj;
        try {
            obj = eventProducer.produceEvent();
        } catch (InvocationTargetException e) {
            StringBuilder sb = new StringBuilder("Producer ");
            sb.append(eventProducer);
            sb.append(" threw an exception.");
            throwRuntimeException(sb.toString(), e);
            obj = null;
        }
        if (obj != null) {
            dispatch(obj, eventHandler);
        }
    }

    public void unregister(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Object to unregister must not be null.");
        }
        this.enforcer.enforce(this);
        for (Entry next : this.handlerFinder.findAllProducers(obj).entrySet()) {
            Class cls = (Class) next.getKey();
            EventProducer producerForEventType = getProducerForEventType(cls);
            EventProducer eventProducer = (EventProducer) next.getValue();
            if (eventProducer == null || !eventProducer.equals(producerForEventType)) {
                StringBuilder sb = new StringBuilder("Missing event producer for an annotated method. Is ");
                sb.append(obj.getClass());
                sb.append(" registered?");
                throw new IllegalArgumentException(sb.toString());
            }
            ((EventProducer) this.producersByType.remove(cls)).invalidate();
        }
        for (Entry next2 : this.handlerFinder.findAllSubscribers(obj).entrySet()) {
            Set<EventHandler> handlersForEventType = getHandlersForEventType((Class) next2.getKey());
            Collection collection = (Collection) next2.getValue();
            if (handlersForEventType == null || !handlersForEventType.containsAll(collection)) {
                StringBuilder sb2 = new StringBuilder("Missing event handler for an annotated method. Is ");
                sb2.append(obj.getClass());
                sb2.append(" registered?");
                throw new IllegalArgumentException(sb2.toString());
            }
            for (EventHandler next3 : handlersForEventType) {
                if (collection.contains(next3)) {
                    next3.invalidate();
                }
            }
            handlersForEventType.removeAll(collection);
        }
    }

    public void post(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Event to post must not be null.");
        }
        this.enforcer.enforce(this);
        boolean z = false;
        for (Class<?> handlersForEventType : flattenHierarchy(obj.getClass())) {
            Set<EventHandler> handlersForEventType2 = getHandlersForEventType(handlersForEventType);
            if (handlersForEventType2 != null && !handlersForEventType2.isEmpty()) {
                z = true;
                for (EventHandler enqueueEvent : handlersForEventType2) {
                    enqueueEvent(obj, enqueueEvent);
                }
            }
        }
        if (!z && !(obj instanceof DeadEvent)) {
            post(new DeadEvent(this, obj));
        }
        dispatchQueuedEvents();
    }

    /* access modifiers changed from: protected */
    public void enqueueEvent(Object obj, EventHandler eventHandler) {
        this.eventsToDispatch.get().offer(new EventWithHandler(obj, eventHandler));
    }

    /* access modifiers changed from: protected */
    public void dispatchQueuedEvents() {
        if (!this.isDispatching.get().booleanValue()) {
            this.isDispatching.set(Boolean.TRUE);
            while (true) {
                try {
                    EventWithHandler eventWithHandler = (EventWithHandler) this.eventsToDispatch.get().poll();
                    if (eventWithHandler == null) {
                        return;
                    }
                    if (eventWithHandler.handler.isValid()) {
                        dispatch(eventWithHandler.event, eventWithHandler.handler);
                    }
                } finally {
                    this.isDispatching.set(Boolean.FALSE);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatch(Object obj, EventHandler eventHandler) {
        try {
            eventHandler.handleEvent(obj);
        } catch (InvocationTargetException e) {
            StringBuilder sb = new StringBuilder("Could not dispatch event: ");
            sb.append(obj.getClass());
            sb.append(" to handler ");
            sb.append(eventHandler);
            throwRuntimeException(sb.toString(), e);
        }
    }

    /* access modifiers changed from: 0000 */
    public EventProducer getProducerForEventType(Class<?> cls) {
        return (EventProducer) this.producersByType.get(cls);
    }

    /* access modifiers changed from: 0000 */
    public Set<EventHandler> getHandlersForEventType(Class<?> cls) {
        return (Set) this.handlersByType.get(cls);
    }

    /* access modifiers changed from: 0000 */
    public Set<Class<?>> flattenHierarchy(Class<?> cls) {
        Set<Class<?>> set = (Set) this.flattenHierarchyCache.get(cls);
        if (set != null) {
            return set;
        }
        Set<Class<?>> classesFor = getClassesFor(cls);
        Set putIfAbsent = this.flattenHierarchyCache.putIfAbsent(cls, classesFor);
        return putIfAbsent == null ? classesFor : putIfAbsent;
    }

    private Set<Class<?>> getClassesFor(Class<?> cls) {
        LinkedList linkedList = new LinkedList();
        HashSet hashSet = new HashSet();
        linkedList.add(cls);
        while (!linkedList.isEmpty()) {
            Class cls2 = (Class) linkedList.remove(0);
            hashSet.add(cls2);
            Class<? super T> superclass = cls2.getSuperclass();
            if (superclass != null) {
                linkedList.add(superclass);
            }
        }
        return hashSet;
    }

    private static void throwRuntimeException(String str, InvocationTargetException invocationTargetException) {
        Throwable cause = invocationTargetException.getCause();
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(cause.getMessage());
            throw new RuntimeException(sb.toString(), cause);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(": ");
        sb2.append(invocationTargetException.getMessage());
        throw new RuntimeException(sb2.toString(), invocationTargetException);
    }
}
