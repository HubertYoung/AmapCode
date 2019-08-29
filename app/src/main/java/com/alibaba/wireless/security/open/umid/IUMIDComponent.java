package com.alibaba.wireless.security.open.umid;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "main")
public interface IUMIDComponent extends IComponent {
    public static final int ENVIRONMENT_DAILY = 2;
    public static final int ENVIRONMENT_ONLINE = 0;
    public static final int ENVIRONMENT_PRE = 1;
    public static final String NOTIFICATION_UMID_DID_CHANGED = "NotificationUmidDidChanged";

    @Deprecated
    String getSecurityToken() throws SecException;

    String getSecurityToken(int i) throws SecException;

    @Deprecated
    void initUMID() throws SecException;

    void initUMID(int i, IUMIDInitListenerEx iUMIDInitListenerEx) throws SecException;

    @Deprecated
    void initUMID(String str, int i, String str2, IUMIDInitListenerEx iUMIDInitListenerEx) throws SecException;

    int initUMIDSync(int i) throws SecException;

    @Deprecated
    void registerInitListener(IUMIDInitListener iUMIDInitListener) throws SecException;

    void setEnvironment(int i) throws SecException;

    @Deprecated
    void setOnlineHost(String str) throws SecException;
}
