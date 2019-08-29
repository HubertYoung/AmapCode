package de.greenrobot.event;

public final class NoSubscriberEvent {
    public final EventBus eventBus;
    public final Object originalEvent;

    public NoSubscriberEvent(EventBus eventBus2, Object obj) {
        this.eventBus = eventBus2;
        this.originalEvent = obj;
    }
}
