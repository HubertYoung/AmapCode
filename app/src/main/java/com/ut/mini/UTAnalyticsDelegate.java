package com.ut.mini;

import android.app.Application;
import android.os.RemoteException;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.store.LogStoreMgr;
import com.alibaba.analytics.utils.Logger;
import com.ut.mini.core.UTLogTransferMain;
import com.ut.mini.internal.UTTeamWork;
import java.util.HashMap;
import java.util.Map;

public final class UTAnalyticsDelegate {
    private static UTAnalyticsDelegate s_instance;
    private Application mApplication;
    private UTTracker mDefaultTracker;
    private Map<String, UTTracker> mTrackerMap = new HashMap();

    public static synchronized UTAnalyticsDelegate getInstance() {
        UTAnalyticsDelegate uTAnalyticsDelegate;
        synchronized (UTAnalyticsDelegate.class) {
            try {
                if (s_instance == null) {
                    s_instance = new UTAnalyticsDelegate();
                }
                uTAnalyticsDelegate = s_instance;
            }
        }
        return uTAnalyticsDelegate;
    }

    private UTAnalyticsDelegate() {
    }

    public final void initUT(Application application) {
        this.mApplication = application;
        UTTeamWork.getInstance().initialized();
    }

    public final void setAppVersion(String str) {
        Variables.getInstance().setAppVersion(str);
    }

    public final void setChannel(String str) {
        Logger.d((String) null, "channel", str);
        Variables.getInstance().setChannel(str);
    }

    public final void turnOnDebug() {
        Variables.getInstance().turnOnDebug();
    }

    public final void updateUserAccount(String str, String str2, String str3) {
        Variables.getInstance().updateUserAccount(str, str2, str3);
    }

    public final void updateSessionProperties(Map map) {
        Map<String, String> sessionProperties = Variables.getInstance().getSessionProperties();
        HashMap hashMap = new HashMap();
        if (sessionProperties != null) {
            hashMap.putAll(sessionProperties);
        }
        if (map != null) {
            hashMap.putAll(map);
        }
        Variables.getInstance().setSessionProperties(hashMap);
    }

    public final void transferLog(Map<String, String> map) {
        UTLogTransferMain.getInstance().transferLog(map);
    }

    public final void turnOnRealTimeDebug(Map map) throws RemoteException {
        Variables.getInstance().turnOnRealTimeDebug(map);
    }

    public final void turnOffRealTimeDebug() throws RemoteException {
        Variables.getInstance().turnOffRealTimeDebug();
    }

    public final void saveCacheDataToLocal() throws RemoteException {
        LogStoreMgr.getInstance().store();
    }

    public final void setSessionProperties(Map map) {
        Variables.getInstance().setSessionProperties(map);
    }
}
