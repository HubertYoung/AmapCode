package com.alibaba.wireless.security.open.securesignature;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;

@InterfacePluginInfo(pluginName = "main")
public interface ISecureSignatureComponent extends IComponent {
    String getSafeCookie(String str, String str2, String str3) throws SecException;

    String signRequest(SecurityGuardParamContext securityGuardParamContext, String str) throws SecException;
}
