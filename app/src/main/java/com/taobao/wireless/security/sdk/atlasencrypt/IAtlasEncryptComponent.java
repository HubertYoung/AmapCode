package com.taobao.wireless.security.sdk.atlasencrypt;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "main")
public interface IAtlasEncryptComponent extends IComponent {
    String atlasSafeEncrypt(String str);
}
