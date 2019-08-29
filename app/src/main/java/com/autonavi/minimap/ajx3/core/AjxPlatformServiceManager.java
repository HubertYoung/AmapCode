package com.autonavi.minimap.ajx3.core;

import android.content.Context;
import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.AjxConfig;
import com.autonavi.minimap.ajx3.loader.AjxLoaderManager;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService;
import com.autonavi.minimap.ajx3.platform.ackor.INetworkService;
import com.autonavi.minimap.ajx3.platform.ackor.IPlatformServiceManager;
import com.autonavi.minimap.ajx3.platform.ackor.IResourceService;
import com.autonavi.minimap.ajx3.platform.ackor.IUIThread;
import com.autonavi.minimap.ajx3.platform.impl.DeviceServiceImpl;
import com.autonavi.minimap.ajx3.platform.impl.NetworkServiceImpl;
import com.autonavi.minimap.ajx3.platform.impl.ResourceServiceImpl;
import com.autonavi.minimap.ajx3.platform.impl.UIThreadImpl;

final class AjxPlatformServiceManager implements IPlatformServiceManager {
    private IDeviceService mDeviceService;
    private INetworkService mNetworkService;
    private IResourceService mResourceService;
    private IUIThread mUIThread = new UIThreadImpl();

    AjxPlatformServiceManager(@NonNull Context context, @NonNull AjxLoaderManager ajxLoaderManager, @NonNull AjxConfig ajxConfig) {
        this.mDeviceService = new DeviceServiceImpl(context, ajxLoaderManager);
        this.mResourceService = new ResourceServiceImpl(context, ajxLoaderManager, ajxConfig.getAjxFileInfo(), ajxConfig.getAjxFRListener());
        if (ajxConfig.getNetworkService() == null) {
            this.mNetworkService = new NetworkServiceImpl();
        } else {
            this.mNetworkService = ajxConfig.getNetworkService();
        }
    }

    public final void registerService(IDeviceService iDeviceService) {
        this.mDeviceService = iDeviceService;
    }

    public final void registerService(INetworkService iNetworkService) {
        this.mNetworkService = iNetworkService;
    }

    public final void registerService(IResourceService iResourceService) {
        this.mResourceService = iResourceService;
    }

    public final IDeviceService getDeviceService() {
        return this.mDeviceService;
    }

    public final INetworkService getNetworkService() {
        return this.mNetworkService;
    }

    public final IResourceService getResourceService() {
        return this.mResourceService;
    }

    public final IUIThread getUIThreadInstance() {
        return this.mUIThread;
    }
}
