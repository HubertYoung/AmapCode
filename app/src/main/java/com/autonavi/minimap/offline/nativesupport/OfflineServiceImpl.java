package com.autonavi.minimap.offline.nativesupport;

import com.autonavi.minimap.ackor.ackoramap.IAmapService;
import com.autonavi.minimap.ackor.ackoroffline.IAmapCompat;
import com.autonavi.minimap.ackor.ackoroffline.IOfflineService;
import com.autonavi.minimap.ackor.ackorplatform.IPlatformServiceManager;
import com.autonavi.minimap.nativesupport.amap.AmapServiceImpl;
import com.autonavi.minimap.nativesupport.platform.PlatformServiceManagerImpl;

public class OfflineServiceImpl implements IOfflineService {
    IAmapService mAmapService = new AmapServiceImpl();
    IPlatformServiceManager mPlatformServiceManager = PlatformServiceManagerImpl.getInstance();

    public IAmapCompat getAmapCompat() {
        return new AmapCompat();
    }

    public boolean requestWifiAutoUpdate() {
        return !((dfm) ank.a(dfm.class)).b();
    }

    public IAmapService getAmapService() {
        return this.mAmapService;
    }

    public IPlatformServiceManager getPlatformService() {
        return this.mPlatformServiceManager;
    }
}
