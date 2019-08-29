package com.alipay.mobile.beehive.eventbus;

public interface IEventSubscriber {
    void onEvent(String str, Object obj);
}
