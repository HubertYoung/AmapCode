package com.alipay.mobile.worker.remotedebug;

import com.alipay.mobile.h5container.api.H5Event;

public interface TinyAppRemoteDebugInterceptor {
    boolean isRemoteDebugConnected(String str);

    void registerWorker(String str, H5Event h5Event);

    void remoteLoadUrl(String str);

    void sendMessageToRemoteWorker(String str);
}
