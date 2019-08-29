package de.greenrobot.event;

import java.lang.reflect.Method;

final class SubscriberMethod {
    final Class<?> eventType;
    final Method method;
    String methodString;
    final ThreadMode threadMode;

    SubscriberMethod(Method method2, ThreadMode threadMode2, Class<?> cls) {
        this.method = method2;
        this.threadMode = threadMode2;
        this.eventType = cls;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof SubscriberMethod)) {
            return false;
        }
        checkMethodString();
        return this.methodString.equals(((SubscriberMethod) obj).methodString);
    }

    private synchronized void checkMethodString() {
        if (this.methodString == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(this.method.getDeclaringClass().getName());
            sb.append('#');
            sb.append(this.method.getName());
            sb.append('(');
            sb.append(this.eventType.getName());
            this.methodString = sb.toString();
        }
    }

    public final int hashCode() {
        return this.method.hashCode();
    }
}
