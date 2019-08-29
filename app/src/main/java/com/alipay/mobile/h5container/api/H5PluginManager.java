package com.alipay.mobile.h5container.api;

import java.util.List;

public interface H5PluginManager extends H5Plugin {
    boolean canHandle(String str);

    boolean register(H5Plugin h5Plugin);

    boolean register(List<H5Plugin> list);

    boolean reregisterFront(H5Plugin h5Plugin);

    boolean unregister(H5Plugin h5Plugin);

    boolean unregister(List<H5Plugin> list);
}
