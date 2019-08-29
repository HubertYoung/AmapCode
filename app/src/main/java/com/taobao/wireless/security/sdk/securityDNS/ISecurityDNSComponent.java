package com.taobao.wireless.security.sdk.securityDNS;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "main")
public interface ISecurityDNSComponent extends IComponent {
    void checkSecurityDNS(String[] strArr);

    boolean initSecurityDNS();
}
