package com.alipay.mobile.rome.longlinkservice;

import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncUpResp;

public interface ISyncUpResp<K> {
    static Class sInjector = String.class;

    void onUpResponse(SyncUpResp<K> syncUpResp);
}
