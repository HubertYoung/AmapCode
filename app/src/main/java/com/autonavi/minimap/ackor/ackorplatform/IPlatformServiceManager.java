package com.autonavi.minimap.ackor.ackorplatform;

public interface IPlatformServiceManager {
    IDeviceService getDeviceService();

    INetworkService getNetworkService();

    IResourceService getResourceService();

    IUIThread getUIThreadInstance();
}
