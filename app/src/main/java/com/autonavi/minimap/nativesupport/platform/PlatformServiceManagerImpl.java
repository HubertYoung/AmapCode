package com.autonavi.minimap.nativesupport.platform;

import com.autonavi.minimap.ackor.ackorplatform.IDeviceService;
import com.autonavi.minimap.ackor.ackorplatform.INetworkService;
import com.autonavi.minimap.ackor.ackorplatform.IPlatformServiceManager;
import com.autonavi.minimap.ackor.ackorplatform.IResourceService;
import com.autonavi.minimap.ackor.ackorplatform.IUIThread;

public class PlatformServiceManagerImpl implements IPlatformServiceManager {
    private static PlatformServiceManagerImpl mInstance;
    private IDeviceService mDeviceService = new DeviceServiceImpl();
    private INetworkService mNetworkService = new NetworkServiceImpl();
    private IResourceService mResourceService = new ResourceServiceImpl();
    private IUIThread mUIThread = new UIThreadImpl();

    private PlatformServiceManagerImpl() {
    }

    public static PlatformServiceManagerImpl getInstance() {
        if (mInstance == null) {
            synchronized (PlatformServiceManagerImpl.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new PlatformServiceManagerImpl();
                    }
                }
            }
        }
        return mInstance;
    }

    public IDeviceService getDeviceService() {
        return this.mDeviceService;
    }

    public INetworkService getNetworkService() {
        return this.mNetworkService;
    }

    public IResourceService getResourceService() {
        return this.mResourceService;
    }

    public IUIThread getUIThreadInstance() {
        return this.mUIThread;
    }
}
