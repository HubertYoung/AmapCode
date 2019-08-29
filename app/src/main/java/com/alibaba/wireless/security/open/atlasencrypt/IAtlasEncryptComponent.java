package com.alibaba.wireless.security.open.atlasencrypt;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "main")
public interface IAtlasEncryptComponent extends IComponent {
    String atlasSafeEncrypt(String str, String str2) throws SecException;
}
