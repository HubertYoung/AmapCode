package de.greenrobot.event;

import android.os.Looper;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class EventBus {
    private static /* synthetic */ int[] $SWITCH_TABLE$de$greenrobot$event$ThreadMode = null;
    public static String TAG = "Event";
    private static volatile EventBus defaultInstance;
    private static final Map<Class<?>, List<Class<?>>> eventTypesCache = new HashMap();
    static ExecutorService executorService = Executors.newCachedThreadPool();
    private final AsyncPoster asyncPoster = new AsyncPoster(this);
    private final BackgroundPoster backgroundPoster = new BackgroundPoster(this);
    private final ThreadLocal<List<Object>> currentThreadEventQueue = new ThreadLocal<List<Object>>() {
        /* access modifiers changed from: protected */
        public List<Object> initialValue() {
            return new ArrayList();
        }
    };
    private final ThreadLocal<BooleanWrapper> currentThreadIsPosting = new ThreadLocal<BooleanWrapper>() {
        /* access modifiers changed from: protected */
        public BooleanWrapper initialValue() {
            return new BooleanWrapper();
        }
    };
    private String defaultMethodName = "onEvent";
    private boolean logSubscriberExceptions = true;
    private final HandlerPoster mainThreadPoster = new HandlerPoster(this, Looper.getMainLooper(), 10);
    private final Map<Class<?>, Object> stickyEvents = new ConcurrentHashMap();
    private boolean subscribed;
    private final SubscriberMethodFinder subscriberMethodFinder = new SubscriberMethodFinder();
    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> subscriptionsByEventType = new HashMap();
    private final Map<Object, List<Class<?>>> typesBySubscriber = new HashMap();

    static final class BooleanWrapper {
        boolean value;

        BooleanWrapper() {
        }
    }

    interface PostCallback {
        void onPostCompleted(List<SubscriberExceptionEvent> list);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:3|4|5|6|7|8|9|10|11|12|14) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0027 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0015 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ int[] $SWITCH_TABLE$de$greenrobot$event$ThreadMode() {
        /*
            int[] r0 = $SWITCH_TABLE$de$greenrobot$event$ThreadMode
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            de.greenrobot.event.ThreadMode[] r0 = de.greenrobot.event.ThreadMode.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            de.greenrobot.event.ThreadMode r1 = de.greenrobot.event.ThreadMode.Async     // Catch:{ NoSuchFieldError -> 0x0015 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0015 }
            r2 = 4
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0015 }
        L_0x0015:
            de.greenrobot.event.ThreadMode r1 = de.greenrobot.event.ThreadMode.BackgroundThread     // Catch:{ NoSuchFieldError -> 0x001e }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001e }
            r2 = 3
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001e }
        L_0x001e:
            de.greenrobot.event.ThreadMode r1 = de.greenrobot.event.ThreadMode.MainThread     // Catch:{ NoSuchFieldError -> 0x0027 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = 2
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0027 }
        L_0x0027:
            de.greenrobot.event.ThreadMode r1 = de.greenrobot.event.ThreadMode.PostThread     // Catch:{ NoSuchFieldError -> 0x0030 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0030 }
            r2 = 1
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0030 }
        L_0x0030:
            $SWITCH_TABLE$de$greenrobot$event$ThreadMode = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: de.greenrobot.event.EventBus.$SWITCH_TABLE$de$greenrobot$event$ThreadMode():int[]");
    }

    public static EventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                try {
                    if (defaultInstance == null) {
                        defaultInstance = new EventBus();
                    }
                }
            }
        }
        return defaultInstance;
    }

    public static void clearCaches() {
        SubscriberMethodFinder.clearCaches();
        eventTypesCache.clear();
    }

    public static void skipMethodNameVerificationFor(Class<?> cls) {
        SubscriberMethodFinder.skipMethodNameVerificationFor(cls);
    }

    public static void clearSkipMethodNameVerifications() {
        SubscriberMethodFinder.clearSkipMethodNameVerifications();
    }

    public final void configureLogSubscriberExceptions(boolean z) {
        if (this.subscribed) {
            throw new EventBusException((String) "This method must be called before any registration");
        }
        this.logSubscriberExceptions = z;
    }

    public final void register(Object obj) {
        register(obj, this.defaultMethodName, false);
    }

    public final void register(Object obj, String str) {
        register(obj, str, false);
    }

    public final void registerSticky(Object obj) {
        register(obj, this.defaultMethodName, true);
    }

    public final void registerSticky(Object obj, String str) {
        register(obj, str, true);
    }

    private void register(Object obj, String str, boolean z) {
        for (SubscriberMethod subscribe : this.subscriberMethodFinder.findSubscriberMethods(obj.getClass(), str)) {
            subscribe(obj, subscribe, z);
        }
    }

    public final void register(Object obj, Class<?> cls, Class<?>... clsArr) {
        register(obj, this.defaultMethodName, false, cls, clsArr);
    }

    public final synchronized void register(Object obj, String str, Class<?> cls, Class<?>... clsArr) {
        register(obj, str, false, cls, clsArr);
    }

    public final void registerSticky(Object obj, Class<?> cls, Class<?>... clsArr) {
        register(obj, this.defaultMethodName, true, cls, clsArr);
    }

    public final synchronized void registerSticky(Object obj, String str, Class<?> cls, Class<?>... clsArr) {
        register(obj, str, true, cls, clsArr);
    }

    private synchronized void register(Object obj, String str, boolean z, Class<?> cls, Class<?>... clsArr) {
        for (SubscriberMethod next : this.subscriberMethodFinder.findSubscriberMethods(obj.getClass(), str)) {
            if (cls == next.eventType) {
                subscribe(obj, next, z);
            } else if (clsArr != null) {
                int length = clsArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (clsArr[i] == next.eventType) {
                        subscribe(obj, next, z);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    private void subscribe(Object obj, SubscriberMethod subscriberMethod, boolean z) {
        Object obj2;
        boolean z2 = true;
        this.subscribed = true;
        Class<?> cls = subscriberMethod.eventType;
        CopyOnWriteArrayList copyOnWriteArrayList = this.subscriptionsByEventType.get(cls);
        Subscription subscription = new Subscription(obj, subscriberMethod);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList();
            this.subscriptionsByEventType.put(cls, copyOnWriteArrayList);
        } else {
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                if (((Subscription) it.next()).equals(subscription)) {
                    StringBuilder sb = new StringBuilder("Subscriber ");
                    sb.append(obj.getClass());
                    sb.append(" already registered to event ");
                    sb.append(cls);
                    throw new EventBusException(sb.toString());
                }
            }
        }
        subscriberMethod.method.setAccessible(true);
        copyOnWriteArrayList.add(subscription);
        List list = this.typesBySubscriber.get(obj);
        if (list == null) {
            list = new ArrayList();
            this.typesBySubscriber.put(obj, list);
        }
        list.add(cls);
        if (z) {
            synchronized (this.stickyEvents) {
                obj2 = this.stickyEvents.get(cls);
            }
            if (obj2 != null) {
                if (Looper.getMainLooper() != Looper.myLooper()) {
                    z2 = false;
                }
                postToSubscription(subscription, obj2, z2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0041, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void unregister(java.lang.Object r5, java.lang.Class<?>... r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            int r0 = r6.length     // Catch:{ all -> 0x0042 }
            if (r0 != 0) goto L_0x000c
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0042 }
            java.lang.String r6 = "Provide at least one event class"
            r5.<init>(r6)     // Catch:{ all -> 0x0042 }
            throw r5     // Catch:{ all -> 0x0042 }
        L_0x000c:
            java.util.Map<java.lang.Object, java.util.List<java.lang.Class<?>>> r0 = r4.typesBySubscriber     // Catch:{ all -> 0x0042 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0042 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x0042 }
            if (r0 == 0) goto L_0x0032
            int r1 = r6.length     // Catch:{ all -> 0x0042 }
            r2 = 0
        L_0x0018:
            if (r2 < r1) goto L_0x0027
            boolean r6 = r0.isEmpty()     // Catch:{ all -> 0x0042 }
            if (r6 == 0) goto L_0x0040
            java.util.Map<java.lang.Object, java.util.List<java.lang.Class<?>>> r6 = r4.typesBySubscriber     // Catch:{ all -> 0x0042 }
            r6.remove(r5)     // Catch:{ all -> 0x0042 }
            monitor-exit(r4)
            return
        L_0x0027:
            r3 = r6[r2]     // Catch:{ all -> 0x0042 }
            r4.unubscribeByEventType(r5, r3)     // Catch:{ all -> 0x0042 }
            r0.remove(r3)     // Catch:{ all -> 0x0042 }
            int r2 = r2 + 1
            goto L_0x0018
        L_0x0032:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0042 }
            java.lang.String r0 = "Subscriber to unregister was not registered before: "
            r6.<init>(r0)     // Catch:{ all -> 0x0042 }
            java.lang.Class r5 = r5.getClass()     // Catch:{ all -> 0x0042 }
            r6.append(r5)     // Catch:{ all -> 0x0042 }
        L_0x0040:
            monitor-exit(r4)
            return
        L_0x0042:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: de.greenrobot.event.EventBus.unregister(java.lang.Object, java.lang.Class[]):void");
    }

    private void unubscribeByEventType(Object obj, Class<?> cls) {
        List list = this.subscriptionsByEventType.get(cls);
        if (list != null) {
            int size = list.size();
            int i = 0;
            while (i < size) {
                if (((Subscription) list.get(i)).subscriber == obj) {
                    list.remove(i);
                    i--;
                    size--;
                }
                i++;
            }
        }
    }

    public final synchronized void unregister(Object obj) {
        List<Class> list = this.typesBySubscriber.get(obj);
        if (list != null) {
            for (Class unubscribeByEventType : list) {
                unubscribeByEventType(obj, unubscribeByEventType);
            }
            this.typesBySubscriber.remove(obj);
            return;
        }
        new StringBuilder("Subscriber to unregister was not registered before: ").append(obj.getClass());
    }

    public final void post(Object obj) {
        List list = this.currentThreadEventQueue.get();
        list.add(obj);
        BooleanWrapper booleanWrapper = this.currentThreadIsPosting.get();
        if (!booleanWrapper.value) {
            boolean z = Looper.getMainLooper() == Looper.myLooper();
            booleanWrapper.value = true;
            while (!list.isEmpty()) {
                try {
                    postSingleEvent(list.remove(0), z);
                } finally {
                    booleanWrapper.value = false;
                }
            }
        }
    }

    public final void postSticky(Object obj) {
        post(obj);
        synchronized (this.stickyEvents) {
            this.stickyEvents.put(obj.getClass(), obj);
        }
    }

    public final Object getStickyEvent(Class<?> cls) {
        Object obj;
        synchronized (this.stickyEvents) {
            obj = this.stickyEvents.get(cls);
        }
        return obj;
    }

    public final Object removeStickyEvent(Class<?> cls) {
        Object remove;
        synchronized (this.stickyEvents) {
            remove = this.stickyEvents.remove(cls);
        }
        return remove;
    }

    public final boolean removeStickyEvent(Object obj) {
        synchronized (this.stickyEvents) {
            Class<?> cls = obj.getClass();
            if (!obj.equals(this.stickyEvents.get(cls))) {
                return false;
            }
            this.stickyEvents.remove(cls);
            return true;
        }
    }

    private void postSingleEvent(Object obj, boolean z) throws Error {
        CopyOnWriteArrayList copyOnWriteArrayList;
        Class<?> cls = obj.getClass();
        List<Class<?>> findEventTypes = findEventTypes(cls);
        int size = findEventTypes.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            Class cls2 = findEventTypes.get(i);
            synchronized (this) {
                copyOnWriteArrayList = this.subscriptionsByEventType.get(cls2);
            }
            if (copyOnWriteArrayList != null) {
                Iterator it = copyOnWriteArrayList.iterator();
                while (it.hasNext()) {
                    postToSubscription((Subscription) it.next(), obj, z);
                }
                z2 = true;
            }
        }
        if (!z2) {
            "No subscripers registered for event ".concat(String.valueOf(cls));
            if (cls != NoSubscriberEvent.class && cls != SubscriberExceptionEvent.class) {
                post(new NoSubscriberEvent(this, obj));
            }
        }
    }

    private void postToSubscription(Subscription subscription, Object obj, boolean z) {
        switch ($SWITCH_TABLE$de$greenrobot$event$ThreadMode()[subscription.subscriberMethod.threadMode.ordinal()]) {
            case 1:
                invokeSubscriber(subscription, obj);
                return;
            case 2:
                if (z) {
                    invokeSubscriber(subscription, obj);
                    return;
                } else {
                    this.mainThreadPoster.enqueue(subscription, obj);
                    return;
                }
            case 3:
                if (z) {
                    this.backgroundPoster.enqueue(subscription, obj);
                    return;
                } else {
                    invokeSubscriber(subscription, obj);
                    return;
                }
            case 4:
                this.asyncPoster.enqueue(subscription, obj);
                return;
            default:
                StringBuilder sb = new StringBuilder("Unknown thread mode: ");
                sb.append(subscription.subscriberMethod.threadMode);
                throw new IllegalStateException(sb.toString());
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r5v0, types: [java.lang.Class<?>, java.lang.Class, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.Class<?>> findEventTypes(java.lang.Class r5) {
        /*
            r4 = this;
            java.util.Map<java.lang.Class<?>, java.util.List<java.lang.Class<?>>> r0 = eventTypesCache
            monitor-enter(r0)
            java.util.Map<java.lang.Class<?>, java.util.List<java.lang.Class<?>>> r1 = eventTypesCache     // Catch:{ all -> 0x002c }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x002c }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x002c }
            if (r1 != 0) goto L_0x002a
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x002c }
            r1.<init>()     // Catch:{ all -> 0x002c }
            r2 = r5
        L_0x0013:
            if (r2 != 0) goto L_0x001b
            java.util.Map<java.lang.Class<?>, java.util.List<java.lang.Class<?>>> r2 = eventTypesCache     // Catch:{ all -> 0x002c }
            r2.put(r5, r1)     // Catch:{ all -> 0x002c }
            goto L_0x002a
        L_0x001b:
            r1.add(r2)     // Catch:{ all -> 0x002c }
            java.lang.Class[] r3 = r2.getInterfaces()     // Catch:{ all -> 0x002c }
            addInterfaces(r1, r3)     // Catch:{ all -> 0x002c }
            java.lang.Class r2 = r2.getSuperclass()     // Catch:{ all -> 0x002c }
            goto L_0x0013
        L_0x002a:
            monitor-exit(r0)     // Catch:{ all -> 0x002c }
            return r1
        L_0x002c:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002c }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: de.greenrobot.event.EventBus.findEventTypes(java.lang.Class):java.util.List");
    }

    static void addInterfaces(List<Class<?>> list, Class<?>[] clsArr) {
        for (Class<?> cls : clsArr) {
            if (!list.contains(cls)) {
                list.add(cls);
                addInterfaces(list, cls.getInterfaces());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void invokeSubscriber(PendingPost pendingPost) {
        Object obj = pendingPost.event;
        Subscription subscription = pendingPost.subscription;
        PendingPost.releasePendingPost(pendingPost);
        invokeSubscriber(subscription, obj);
    }

    /* access modifiers changed from: 0000 */
    public final void invokeSubscriber(Subscription subscription, Object obj) throws Error {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, new Object[]{obj});
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (obj instanceof SubscriberExceptionEvent) {
                StringBuilder sb = new StringBuilder("SubscriberExceptionEvent subscriber ");
                sb.append(subscription.subscriber.getClass());
                sb.append(" threw an exception");
                SubscriberExceptionEvent subscriberExceptionEvent = (SubscriberExceptionEvent) obj;
                StringBuilder sb2 = new StringBuilder("Initial event ");
                sb2.append(subscriberExceptionEvent.causingEvent);
                sb2.append(" caused exception in ");
                sb2.append(subscriberExceptionEvent.causingSubscriber);
                return;
            }
            if (this.logSubscriberExceptions) {
                StringBuilder sb3 = new StringBuilder("Could not dispatch event: ");
                sb3.append(obj.getClass());
                sb3.append(" to subscribing class ");
                sb3.append(subscription.subscriber.getClass());
            }
            post(new SubscriberExceptionEvent(this, cause, obj, subscription.subscriber));
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unexpected exception", e2);
        }
    }
}
