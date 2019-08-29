package com.alipay.mobile.nebula.appcenter.listen;

import java.util.List;

public interface NebulaAppManager {
    List<NebulaAppCallback> getNebulaAppCallbackList();

    void registerNebulaAppCallback(NebulaAppCallback nebulaAppCallback);

    void unregisterNebulaAppCallback(NebulaAppCallback nebulaAppCallback);
}
