package com.alipay.mobile.rome.longlinkservice;

public interface ISyncStateCallback {
    static Class sInjector = String.class;

    void onSyncState(SyncLinkState syncLinkState);
}
