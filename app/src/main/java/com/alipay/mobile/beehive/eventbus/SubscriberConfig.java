package com.alipay.mobile.beehive.eventbus;

public class SubscriberConfig {
    public boolean isWeakRef;
    public boolean supportPending;
    public boolean supportSticky;
    public String uniqueId;

    public SubscriberConfig() {
        this.isWeakRef = true;
        this.isWeakRef = true;
        this.supportPending = false;
        this.supportSticky = false;
        this.uniqueId = "";
    }

    public boolean isSupportSticky() {
        return this.supportSticky || this.supportPending;
    }
}
