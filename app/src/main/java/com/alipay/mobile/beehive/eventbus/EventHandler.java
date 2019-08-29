package com.alipay.mobile.beehive.eventbus;

import android.text.TextUtils;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventHandler {
    private SubscriberConfig config;
    private final Object eventKey;
    private final int hashCode;
    private final Method method;
    private final Object subscriber;
    private ThreadMode threadMode;
    private String whiteListKey;

    public EventHandler(Object eventKey2, Object target, Method method2, ThreadMode threadMode2, SubscriberConfig config2) {
        if (target == null) {
            throw new IllegalArgumentException("EventHandler subscriber cannot be null");
        } else if (method2 == null) {
            throw new IllegalArgumentException("EventHandler method cannot be null");
        } else {
            this.eventKey = eventKey2;
            this.config = config2;
            if (isWeakRef()) {
                this.subscriber = new WeakReference(target);
            } else {
                this.subscriber = target;
            }
            this.method = method2;
            method2.setAccessible(true);
            this.threadMode = threadMode2;
            this.hashCode = ((method2.hashCode() + 31) * 31) + target.hashCode();
        }
    }

    public Object getEventKey() {
        return this.eventKey;
    }

    public ThreadMode getThreadMode() {
        return this.threadMode;
    }

    public void handleEvent(Object eventData) {
        try {
            invoke(eventData);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw ((Error) e.getCause());
            }
            throw e;
        }
    }

    private void invoke(Object eventData) {
        try {
            Class[] parameterTypes = this.method.getParameterTypes();
            Object v = getRealSubscriber();
            if (v == null) {
                LoggerFactory.getTraceLogger().warn((String) EventBusManager.TAG, "调用事件(" + this.eventKey.toString() + ")回调失败: (" + this.subscriber.toString() + ")使用了弱引用且实体被回收");
            } else if (v instanceof IEventSubscriber) {
                this.method.invoke(v, new Object[]{this.eventKey, eventData});
            } else if (parameterTypes.length == 1) {
                this.method.invoke(v, new Object[]{eventData});
            } else if (parameterTypes.length == 0) {
                this.method.invoke(v, new Object[0]);
            }
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    public String toString() {
        String s;
        if (isWeakRef()) {
            s = this.subscriber.toString() + "->" + getRealSubscriber();
        } else {
            s = this.subscriber.toString();
        }
        return "EventHandler(key=" + MiscUtil.safeToString(this.eventKey) + ",subscriber=" + s + ",method=" + this.method.getName() + ")";
    }

    public int hashCode() {
        return this.hashCode;
    }

    public String getWhiteListKey() {
        return this.whiteListKey;
    }

    public void setWhiteListKey(String whiteListKey2) {
        this.whiteListKey = whiteListKey2;
    }

    public Object getSubscriber() {
        return this.subscriber;
    }

    public Object getRealSubscriber() {
        if (!isWeakRef() || getWeakRef() == null) {
            return this.subscriber;
        }
        return getWeakRef().get();
    }

    public boolean isZombie() {
        if (!isWeakRef() || getWeakRef() == null || getWeakRef().get() != null) {
            return false;
        }
        return true;
    }

    public boolean isWeakRef() {
        return this.config != null && this.config.isWeakRef;
    }

    private WeakReference getWeakRef() {
        if (!isWeakRef() || !(this.subscriber instanceof WeakReference)) {
            return null;
        }
        return (WeakReference) this.subscriber;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EventHandler other = (EventHandler) obj;
        if (isWeakRef() != other.isWeakRef()) {
            return false;
        }
        if (!this.method.equals(other.method) || (getRealSubscriber() != other.getRealSubscriber() && !isSameByUniqueId(other))) {
            return false;
        }
        return true;
    }

    public boolean isSupportSticky() {
        return this.config != null && (this.config.supportSticky || this.config.supportPending);
    }

    public String getUniqueId() {
        if (this.config != null) {
            return this.config.uniqueId;
        }
        return "";
    }

    public boolean isSameByUniqueId(EventHandler handler) {
        String uid = getUniqueId();
        if (TextUtils.isEmpty(uid) || !TextUtils.equals(uid, handler.getUniqueId())) {
            return false;
        }
        return true;
    }
}
