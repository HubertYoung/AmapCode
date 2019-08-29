package com.alibaba.wireless.security.framework;

import com.alibaba.wireless.security.open.SecException;

public interface ISGPluginManager {
    <T> T getInterface(Class<T> cls) throws SecException;

    String getMainPluginName();

    ISGPluginInfo getPluginInfo(String str) throws SecException;

    IRouterComponent getRouter();
}
