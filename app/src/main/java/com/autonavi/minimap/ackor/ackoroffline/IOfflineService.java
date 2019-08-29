package com.autonavi.minimap.ackor.ackoroffline;

import com.autonavi.minimap.ackor.ackoramap.IAmapService;
import com.autonavi.minimap.ackor.ackorplatform.IPlatformServiceManager;

public interface IOfflineService {
    IAmapCompat getAmapCompat();

    IAmapService getAmapService();

    IPlatformServiceManager getPlatformService();

    boolean requestWifiAutoUpdate();
}
