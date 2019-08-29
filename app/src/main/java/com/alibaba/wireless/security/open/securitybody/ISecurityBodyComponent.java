package com.alibaba.wireless.security.open.securitybody;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;
import java.util.HashMap;

@InterfacePluginInfo(pluginName = "securitybody")
public interface ISecurityBodyComponent extends IComponent {
    String getSecurityBodyDataEx(String str, String str2, String str3, HashMap<String, String> hashMap, int i, int i2) throws SecException;
}
