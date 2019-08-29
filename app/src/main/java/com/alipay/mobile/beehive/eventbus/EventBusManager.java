package com.alipay.mobile.beehive.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.beehive.util.ServiceUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadPoolExecutor;

public class EventBusManager {
    public static final String TAG = "EventBus";
    private static EventBusManager instance;
    private final int MAX_QUEUE_SIZE;
    private String id;
    private ConcurrentLinkedQueue<Event> pendingEventQueue;
    private SubscribeFinder subscriberFinder;
    private ConcurrentHashMap<Object, Set<EventHandler>> subscriberMap;
    private TaskScheduleService taskScheduleService;
    private Handler uiHandler;
    private ConcurrentHashMap<Object, List<String>> whiteListMap;

    protected static class EventHandlerWrapper {
        final Event event;
        final EventConfig eventConfig;
        final EventHandler handler;

        public EventHandlerWrapper(Event event2, EventHandler handler2, EventConfig config) {
            this.event = event2;
            this.handler = handler2;
            this.eventConfig = config;
        }

        public Object getEventData() {
            if (this.event != null) {
                return this.event.data;
            }
            return null;
        }

        public Object getEventName() {
            if (this.event != null) {
                return this.event.name;
            }
            return null;
        }
    }

    private EventBusManager() {
        this.MAX_QUEUE_SIZE = 32;
        this.id = "DEFAULT";
        init();
    }

    public EventBusManager(String id2) {
        this.MAX_QUEUE_SIZE = 32;
        if ("DEFAULT".equals(id2)) {
            throw new IllegalArgumentException("cannot create 'DEFAULT' event bus, because 'DEFAULT' is global event bus");
        }
        this.id = id2;
        init();
    }

    private void init() {
        this.taskScheduleService = (TaskScheduleService) ServiceUtil.getServiceByInterface(TaskScheduleService.class);
        this.subscriberMap = new ConcurrentHashMap<>();
        this.whiteListMap = new ConcurrentHashMap<>();
        this.pendingEventQueue = new ConcurrentLinkedQueue<>();
        this.subscriberFinder = SubscribeFinder.ANNOTATED;
        this.uiHandler = new Handler(Looper.getMainLooper());
    }

    public static synchronized EventBusManager getInstance() {
        EventBusManager eventBusManager;
        synchronized (EventBusManager.class) {
            try {
                if (instance == null) {
                    instance = new EventBusManager();
                }
                eventBusManager = instance;
            }
        }
        return eventBusManager;
    }

    public synchronized void dispose() {
        this.subscriberMap.clear();
        this.subscriberFinder.dispose();
    }

    public String toString() {
        return "[Bus \"" + this.id + "\"]";
    }

    public boolean register(Object target) {
        SubscriberConfig config = new SubscriberConfig();
        config.isWeakRef = true;
        return innerRegister(target, config);
    }

    public boolean registerRaw(Object target) {
        SubscriberConfig config = new SubscriberConfig();
        config.isWeakRef = false;
        return innerRegister(target, config);
    }

    public void unregister(Object target) {
        innerUnregister(target, true);
    }

    public void unregisterRaw(Object target) {
        innerUnregister(target, false);
    }

    public void register(IEventSubscriber target, ThreadMode tm, String... eventNames) {
        SubscriberConfig config = new SubscriberConfig();
        config.isWeakRef = true;
        innerRegisterNamesWithInterface(target, tm, config, eventNames);
    }

    public void registerRaw(IEventSubscriber target, ThreadMode tm, String... eventNames) {
        SubscriberConfig config = new SubscriberConfig();
        config.isWeakRef = false;
        innerRegisterNamesWithInterface(target, tm, config, eventNames);
    }

    public void unregister(IEventSubscriber target, String... eventNames) {
        innerUnregisterNamesWithInterface(target, true, eventNames);
    }

    public void unregisterRaw(IEventSubscriber target, String... eventNames) {
        innerUnregisterNamesWithInterface(target, false, eventNames);
    }

    public void register(Object target, SubscriberConfig config) {
        innerRegister(target, config);
    }

    public void register(IEventSubscriber target, ThreadMode tm, SubscriberConfig config, String... eventNames) {
        innerRegisterNamesWithInterface(target, tm, config, eventNames);
    }

    public void addWhiteList(Object eventKey, List<String> values) {
        if (eventKey == null || values == null || values.isEmpty()) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "values to add white list must not be null");
            return;
        }
        this.whiteListMap.remove(eventKey);
        this.whiteListMap.put(eventKey, values);
    }

    public void removeWhiteList(Object eventKey) {
        if (eventKey == null) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "event to remove from white list must not be null");
        } else {
            this.whiteListMap.remove(eventKey);
        }
    }

    private boolean innerRegister(Object target, SubscriberConfig config) {
        if (target == null) {
            throw new IllegalArgumentException("subscriber to register must not be null.");
        }
        Map foundSubscribers = this.subscriberFinder.findAllSubscribers(target, config);
        for (Object key : foundSubscribers.keySet()) {
            performRegisterEventHandler(config, filterWhiteList(key, foundSubscribers.get(key)), key);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void innerUnregister(Object target, boolean isWeakRef) {
        if (target == null) {
            throw new IllegalArgumentException("待注销的subscriber不能为null");
        }
        SubscriberConfig config = new SubscriberConfig();
        config.isWeakRef = isWeakRef;
        if (target instanceof IEventSubscriber) {
            innerUnregisterAll(target);
        } else {
            Map targetHandlers = this.subscriberFinder.findAllSubscribers(target, config);
            for (Object key : targetHandlers.keySet()) {
                Set handlers = this.subscriberMap.get(key);
                if (handlers != null) {
                    safeRemoveSubscriber(handlers, targetHandlers.get(key));
                }
            }
        }
        recycleSubscribers();
    }

    private void innerRegisterNamesWithInterface(IEventSubscriber target, ThreadMode tm, SubscriberConfig config, String... eventNames) {
        if (eventNames != null) {
            for (String name : eventNames) {
                innerRegisterWithInterface(target, tm, config, name);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean innerRegisterWithInterface(IEventSubscriber target, ThreadMode tm, SubscriberConfig config, String eventName) {
        if (target == null) {
            throw new IllegalArgumentException("target to register must not be null.");
        } else if (!TextUtils.isEmpty(eventName)) {
            return performRegisterEventHandler(config, createEventHandlerForInterface(eventName, target, tm, config), eventName);
        } else {
            throw new IllegalArgumentException("register eventName must not be empty");
        }
    }

    private void innerUnregisterNamesWithInterface(IEventSubscriber target, boolean isWeakRef, String... eventNames) {
        if (eventNames != null) {
            for (String name : eventNames) {
                innerUnregisterWithInterface(target, isWeakRef, name);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void innerUnregisterWithInterface(IEventSubscriber target, boolean isWeakRef, String eventName) {
        if (target == null) {
            throw new IllegalArgumentException("target to unregister must not be null.");
        } else if (TextUtils.isEmpty(eventName)) {
            throw new IllegalArgumentException("unregister eventName must not be empty");
        } else {
            Set handlers = this.subscriberMap.get(eventName);
            if (handlers != null) {
                SubscriberConfig sc = new SubscriberConfig();
                sc.isWeakRef = isWeakRef;
                safeRemoveSubscriber(handlers, createEventHandlerForInterface(eventName, target, ThreadMode.CURRENT, sc));
                recycleSubscribers();
            }
        }
    }

    private void innerUnregisterAll(Object target) {
        for (Set<EventHandler> handlers : this.subscriberMap.values()) {
            if (handlers != null && !handlers.isEmpty()) {
                Set targetHandlers = new CopyOnWriteArraySet();
                for (EventHandler h : handlers) {
                    if (target == h.getRealSubscriber()) {
                        targetHandlers.add(h);
                    }
                }
                safeRemoveSubscriber(handlers, targetHandlers);
            }
        }
    }

    private boolean performRegisterEventHandler(SubscriberConfig config, Set<EventHandler> handlers, Object eventKey) {
        boolean flag = addEventHandlerWithDupCheck(handlers, eventKey);
        if (flag && config.isSupportSticky()) {
            consumePendingEventQueue(this.pendingEventQueue, eventKey, config.uniqueId, handlers);
        }
        return flag;
    }

    private boolean addEventHandlerWithDupCheck(Set<EventHandler> toAddHandlers, Object key) {
        Set curHandlers = getEventHandlerSetWithLazyCreate(key);
        if (curHandlers == null || toAddHandlers == null) {
            return false;
        }
        try {
            if (toAddHandlers.isEmpty()) {
                return false;
            }
            Set toAdd = getNotSameSubSet(curHandlers, toAddHandlers);
            if (toAdd == null || toAdd.isEmpty()) {
                DebugUtil.log((String) TAG, "注册事件失败: (" + key + ")=> " + toAddHandlers.toString() + ", 可能已经注册过?");
                return false;
            }
            boolean flag = curHandlers.addAll(toAdd);
            DebugUtil.log((String) TAG, "注册事件成功: " + toAddHandlers.toString());
            return flag;
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) ex);
            return false;
        }
    }

    private Set<EventHandler> getEventHandlerSetWithLazyCreate(Object key) {
        Set result = this.subscriberMap.get(key);
        if (result != null) {
            return result;
        }
        Set result2 = new CopyOnWriteArraySet();
        Set result3 = this.subscriberMap.putIfAbsent(key, result2);
        if (result3 == null) {
            return result2;
        }
        return result3;
    }

    private Set<EventHandler> createEventHandlerForInterface(String name, IEventSubscriber target, ThreadMode tm, SubscriberConfig config) {
        Set handlers = new CopyOnWriteArraySet();
        try {
            Method m = target.getClass().getMethod("onEvent", new Class[]{String.class, Object.class});
            if (m == null || (m.getModifiers() & 1024) != 0) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (String) "target未实现onEvent方法, 是否被混淆了?");
            } else {
                handlers.add(new EventHandler(name, target, m, tm, config));
            }
        } catch (Throwable ex) {
            LoggerFactory.getTraceLogger().warn((String) TAG, ex.getMessage());
        }
        return handlers;
    }

    private void safeRemoveSubscriber(Set<EventHandler> from, Set<EventHandler> target) {
        if (target != null) {
            try {
                if (target.isEmpty()) {
                    return;
                }
                if (!removeSubscriber(from, target)) {
                    LoggerFactory.getTraceLogger().info(TAG, "注销事件失败: 可能未注册或已注销, " + target.toString());
                } else {
                    DebugUtil.log((String) TAG, "注销事件成功:" + target.toString());
                }
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) ex);
            }
        }
    }

    private boolean removeSubscriber(Set<EventHandler> from, Set<EventHandler> target) {
        Set toDelete = getSameSubscriberSet(from, target);
        if (toDelete == null || toDelete.isEmpty()) {
            return false;
        }
        return from.removeAll(toDelete);
    }

    private Set<EventHandler> getZombieSubscriberSet(Set<EventHandler> target) {
        Set result = null;
        for (EventHandler ev : target) {
            if (ev.isZombie()) {
                if (result == null) {
                    result = new CopyOnWriteArraySet();
                }
                result.add(ev);
            }
        }
        return result;
    }

    private Set<EventHandler> getSameSubscriberSet(Set<EventHandler> from, Set<EventHandler> target) {
        Set result = null;
        for (EventHandler ev : from) {
            Iterator<EventHandler> it = target.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                EventHandler t = it.next();
                if (ev.equals(t)) {
                    if (result == null) {
                        result = new CopyOnWriteArraySet();
                    }
                    result.add(t);
                }
            }
        }
        return result;
    }

    private Set<EventHandler> getNotSameSubSet(Set<EventHandler> from, Set<EventHandler> target) {
        if (from == null || from.isEmpty()) {
            return target;
        }
        Set result = null;
        for (EventHandler t : target) {
            boolean flag = true;
            Iterator<EventHandler> it = from.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().equals(t)) {
                        flag = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (flag) {
                if (result == null) {
                    result = new CopyOnWriteArraySet();
                }
                result.add(t);
            }
        }
        return result;
    }

    private Set<EventHandler> filterWhiteList(Object key, Set<EventHandler> handlers) {
        List whiteList = this.whiteListMap.get(key);
        if (whiteList == null || whiteList.isEmpty()) {
            return handlers;
        }
        Set result = new CopyOnWriteArraySet();
        for (EventHandler handler : handlers) {
            boolean flag = false;
            Iterator it = whiteList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (TextUtils.equals(handler.getWhiteListKey(), (String) it.next())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                result.add(handler);
            }
        }
        return result;
    }

    public void postByName(String eventName) {
        post(null, eventName);
    }

    public void post(Object eventData) {
        post(eventData, "");
    }

    public void postDelayed(Object eventData, String eventName, int millisecond) {
        EventConfig config = new EventConfig();
        config.timestamp = SystemClock.uptimeMillis() + ((long) millisecond);
        post(eventData, eventName, config);
    }

    public void postAtFront(Object eventData, String eventName) {
        EventConfig config = new EventConfig();
        config.isAtFront = true;
        post(eventData, eventName, config);
    }

    public void post(Object eventData, String eventName) {
        post(eventData, eventName, null);
    }

    public void post(Event event) {
        post(event.data, event.name, null);
    }

    public void post(Object eventData, String eventName, EventConfig config) {
        Event event = new Event(eventName, eventData);
        if (Event.isValid(event)) {
            if (config == null) {
                config = new EventConfig();
            }
            Set handlers = getHandlersForEvent(event);
            Set validHandlers = new CopyOnWriteArraySet();
            if (handlers != null) {
                for (EventHandler handler : handlers) {
                    if (handler.getRealSubscriber() != null) {
                        validHandlers.add(handler);
                    }
                }
            }
            if (validHandlers.isEmpty()) {
                logOnDispatchEventFail(event);
            } else {
                dispatchEvent(validHandlers, event, config);
            }
            if (config.isSticky) {
                DebugUtil.log((String) TAG, "添加pending事件入队列: " + event.toString());
                enqueuePendingEvent(this.pendingEventQueue, event, validHandlers);
            }
        } else if (DebugUtil.isDebug()) {
            throw new IllegalArgumentException("事件无效,事件key名或事件数据不能都为null");
        } else {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "事件无效,事件key名或事件数据不能都为null");
        }
    }

    private void dispatchEvent(Set<EventHandler> handlers, Event event, EventConfig config) {
        dispatchEventDirectly(handlers, event, config);
    }

    private void dispatchEventDirectly(Set<EventHandler> handlers, Event event, EventConfig config) {
        List<EventHandlerWrapper> wrappers = new ArrayList<>();
        for (EventHandler handler : handlers) {
            if (handler != null) {
                wrappers.add(new EventHandlerWrapper(event, handler, config));
            }
        }
        for (EventHandlerWrapper wrapper : wrappers) {
            if (!(wrapper == null || wrapper.event == null || wrapper.handler == null)) {
                dispatchEventToThread(wrapper);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchEventToThread(EventHandlerWrapper wrapper) {
        EventHandler handler = wrapper.handler;
        if (handler.getThreadMode() == ThreadMode.CURRENT) {
            handleEvent(wrapper);
        } else if (handler.getThreadMode() == ThreadMode.BACKGROUND) {
            dispatchEventOnBackground(wrapper);
        } else if (handler.getThreadMode() == ThreadMode.UI) {
            dispatchEventUseHandler(this.uiHandler, wrapper);
        }
    }

    private void dispatchEventOnBackground(final EventHandlerWrapper wrapper) {
        ThreadPoolExecutor e = this.taskScheduleService.acquireExecutor(ScheduleType.IO);
        if (e != null) {
            e.execute(new Runnable() {
                public final void run() {
                    EventBusManager.this.handleEvent(wrapper);
                }
            });
        } else {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "获取后台线程池失败");
        }
    }

    private void dispatchEventUseHandler(Handler handler, final EventHandlerWrapper wrapper) {
        long timestamp;
        Runnable r = new Runnable() {
            public final void run() {
                EventBusManager.this.handleEvent(wrapper);
            }
        };
        if (wrapper.eventConfig != null && wrapper.eventConfig.isAtFront) {
            handler.postAtFrontOfQueue(r);
            return;
        }
        if (wrapper.eventConfig != null) {
            timestamp = wrapper.eventConfig.timestamp;
        } else {
            timestamp = 0;
        }
        if (timestamp <= 0) {
            handler.post(r);
        } else {
            handler.postAtTime(r, timestamp);
        }
    }

    /* access modifiers changed from: private */
    public void handleEvent(EventHandlerWrapper wrapper) {
        try {
            wrapper.handler.handleEvent(wrapper.event.data);
        } catch (InvocationTargetException e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
        }
    }

    private void consumePendingEventQueue(Queue<Event> queue, Object eventKey, String uniqueId, Set<EventHandler> handlers) {
        if (handlers != null && !handlers.isEmpty()) {
            Queue consumedEvent = new ConcurrentLinkedQueue();
            EventConfig config = new EventConfig();
            Iterator it = queue.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Event evt = (Event) it.next();
                if (Event.keyEqual(eventKey, evt.key) && !evt.isConsumed(uniqueId)) {
                    DebugUtil.log((String) TAG, "消费pending队列事件: " + evt.toString());
                    dispatchEvent(handlers, evt, config);
                    consumedEvent.add(evt);
                    break;
                }
            }
            if (!consumedEvent.isEmpty()) {
                queue.removeAll(consumedEvent);
            }
        }
    }

    private void limitPendingQueue(Queue<Event> queue) {
        if (queue.size() > 32) {
            int delta = queue.size() - 32;
            DebugUtil.log((String) TAG, "pending事件队列超限: 删除前" + String.valueOf(delta) + "个事件");
            for (int i = 0; i < delta; i++) {
                queue.poll();
            }
        }
    }

    private void enqueuePendingEvent(Queue<Event> queue, Event event, Set<EventHandler> handlers) {
        if (event != null) {
            List uids = new ArrayList();
            for (EventHandler h : handlers) {
                if (h != null && TextUtils.isEmpty(h.getUniqueId())) {
                    uids.add(h.getUniqueId());
                }
            }
            if (queue.add(event)) {
                event.setUniqueIds(uids);
                limitPendingQueue(queue);
            }
        }
    }

    private Set<EventHandler> getHandlersForEvent(Event event) {
        return this.subscriberMap.get(event.key);
    }

    private void logOnDispatchEventFail(Event event) {
        if (DebugUtil.isDebug()) {
            DebugUtil.log((String) TAG, toString() + " 分发事件失败(未找到监听者): " + event.name);
        }
    }

    private void recycleSubscribers() {
        try {
            Iterator it = this.subscriberMap.entrySet().iterator();
            while (it.hasNext()) {
                Set value = (Set) it.next().getValue();
                if (value == null || value.isEmpty()) {
                    it.remove();
                } else {
                    recycleInvalidHandlers(value);
                }
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) ex);
        }
    }

    private void recycleInvalidHandlers(Set<EventHandler> handlers) {
        if (handlers != null && !handlers.isEmpty()) {
            Set toDelete = new CopyOnWriteArraySet();
            for (EventHandler ev : handlers) {
                if (ev != null && ev.isZombie()) {
                    toDelete.add(ev);
                }
            }
            handlers.removeAll(toDelete);
        }
    }
}
