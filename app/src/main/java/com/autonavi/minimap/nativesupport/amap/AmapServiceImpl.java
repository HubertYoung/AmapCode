package com.autonavi.minimap.nativesupport.amap;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.minimap.ackor.ackoramap.IAmapHttpRequest;
import com.autonavi.minimap.ackor.ackoramap.IAmapService;
import com.autonavi.minimap.ackor.ackoramap.IBehavior;
import com.autonavi.minimap.nativesupport.platform.LogPathManager;

public class AmapServiceImpl implements IAmapService {
    private static AmapServiceImpl mInstance;
    private IBehavior mBehavior = new Behavior();

    public AmapServiceImpl() {
        checkOfflineLog();
    }

    public static AmapServiceImpl getInstance() {
        if (mInstance == null) {
            synchronized (AmapServiceImpl.class) {
                if (mInstance == null) {
                    mInstance = new AmapServiceImpl();
                }
            }
        }
        return mInstance;
    }

    public IBehavior getBehavior() {
        return this.mBehavior;
    }

    public IAmapHttpRequest createAmapHttpRequest() {
        return new AmapRequestImpl();
    }

    public void destroyAmapHttpRequest(IAmapHttpRequest iAmapHttpRequest) {
        if (iAmapHttpRequest != null) {
            iAmapHttpRequest.cancel();
        }
    }

    private void checkOfflineLog() {
        ahm.a(new Runnable() {
            public void run() {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue(LogPathManager.OFFLINE_LOG_NAME, LogPathManager.getCurrentPath());
            }
        });
    }
}
