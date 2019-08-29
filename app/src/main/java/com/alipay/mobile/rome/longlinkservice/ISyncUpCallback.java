package com.alipay.mobile.rome.longlinkservice;

public interface ISyncUpCallback {
    static Class sInjector = String.class;

    public enum SyncUpState {
        SUCCEED
    }

    void onSyncUpResult(String str, String str2, SyncUpState syncUpState);
}
