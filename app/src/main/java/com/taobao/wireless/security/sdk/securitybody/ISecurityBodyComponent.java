package com.taobao.wireless.security.sdk.securitybody;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "securitybody")
public interface ISecurityBodyComponent extends IComponent {
    String getSecurityBodyData(String str);

    String getSecurityBodyData(String str, String str2);

    String getSecurityBodyDataEx(String str, String str2, int i);

    boolean initSecurityBody(String str);

    boolean putUserActionRecord(String str, String str2, float f, float f2);

    boolean putUserTrackRecord(String str);

    void setSecurityBodyServer(int i);
}
