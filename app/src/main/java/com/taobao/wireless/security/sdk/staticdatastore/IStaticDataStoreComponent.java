package com.taobao.wireless.security.sdk.staticdatastore;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "main")
public interface IStaticDataStoreComponent extends IComponent {
    String getAppKeyByIndex(int i);

    String getExtraData(String str);

    int getKeyType(String str);
}
