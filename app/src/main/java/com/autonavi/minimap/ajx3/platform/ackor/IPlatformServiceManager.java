package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IPlatformServiceManager {
    IDeviceService getDeviceService();

    INetworkService getNetworkService();

    IResourceService getResourceService();

    IUIThread getUIThreadInstance();
}
