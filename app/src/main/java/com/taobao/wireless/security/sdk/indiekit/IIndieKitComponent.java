package com.taobao.wireless.security.sdk.indiekit;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;
import com.taobao.wireless.security.sdk.SecurityGuardParamContext;

@InterfacePluginInfo(pluginName = "misc")
public interface IIndieKitComponent extends IComponent {
    String indieKitRequest(SecurityGuardParamContext securityGuardParamContext);

    int reportSusText(String str, String str2);

    int validateFileSignature(String str, String str2, String str3);
}
