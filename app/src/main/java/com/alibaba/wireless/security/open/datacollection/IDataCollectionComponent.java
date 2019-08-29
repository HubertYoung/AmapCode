package com.alibaba.wireless.security.open.datacollection;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;

@InterfacePluginInfo(pluginName = "main")
public interface IDataCollectionComponent extends IComponent {
    String getNick();

    boolean setNick(String str);
}
