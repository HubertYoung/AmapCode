package de.greenrobot.event;

final class Subscription {
    final Object subscriber;
    final SubscriberMethod subscriberMethod;

    Subscription(Object obj, SubscriberMethod subscriberMethod2) {
        this.subscriber = obj;
        this.subscriberMethod = subscriberMethod2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Subscription)) {
            return false;
        }
        Subscription subscription = (Subscription) obj;
        if (this.subscriber != subscription.subscriber || !this.subscriberMethod.equals(subscription.subscriberMethod)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.subscriber.hashCode() + this.subscriberMethod.methodString.hashCode();
    }
}
