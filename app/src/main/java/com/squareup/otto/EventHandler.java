package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EventHandler {
    private final int hashCode;
    private final Method method;
    private final Object target;
    private boolean valid = true;

    EventHandler(Object obj, Method method2) {
        if (obj == null) {
            throw new NullPointerException("EventHandler target cannot be null.");
        } else if (method2 == null) {
            throw new NullPointerException("EventHandler method cannot be null.");
        } else {
            this.target = obj;
            this.method = method2;
            method2.setAccessible(true);
            this.hashCode = ((method2.hashCode() + 31) * 31) + obj.hashCode();
        }
    }

    public boolean isValid() {
        return this.valid;
    }

    public void invalidate() {
        this.valid = false;
    }

    public void handleEvent(Object obj) throws InvocationTargetException {
        if (!this.valid) {
            StringBuilder sb = new StringBuilder();
            sb.append(toString());
            sb.append(" has been invalidated and can no longer handle events.");
            throw new IllegalStateException(sb.toString());
        }
        try {
            this.method.invoke(this.target, new Object[]{obj});
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof Error) {
                throw ((Error) e2.getCause());
            }
            throw e2;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[EventHandler ");
        sb.append(this.method);
        sb.append("]");
        return sb.toString();
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EventHandler eventHandler = (EventHandler) obj;
        return this.method.equals(eventHandler.method) && this.target == eventHandler.target;
    }
}
