package com.alipay.mobile.nebula.appcenter.common;

import java.util.List;

public interface NebulaCommonManager {
    List<NebulaCommonInfo> getNebulaAppCallbackList();

    void registerNebulaCommonInfo(NebulaCommonInfo nebulaCommonInfo);

    void unregisterNebulaCommonInfo(NebulaCommonInfo nebulaCommonInfo);
}
