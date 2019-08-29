package com.alipay.mobile.rome.longlinkservice;

import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncCommand;
import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncMessage;

public interface ISyncCallback {
    static Class sInjector = String.class;

    void onReceiveCommand(SyncCommand syncCommand);

    void onReceiveMessage(SyncMessage syncMessage);
}
