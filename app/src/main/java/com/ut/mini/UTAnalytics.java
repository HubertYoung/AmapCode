package com.ut.mini;

import android.app.Application;
import android.os.Build.VERSION;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alibaba.analytics.AnalyticsMgr;
import com.alibaba.analytics.core.ClientVariables;
import com.alibaba.analytics.core.Constants.LogContentKeys;
import com.alibaba.analytics.core.config.UTClientConfigMgr;
import com.alibaba.analytics.core.config.UTTPKBiz;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.SpSetting;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.analytics.version.UTBuildInfo;
import com.ut.mini.anti_cheat.AntiCheatTracker;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.core.sign.UTBaseRequestAuthentication;
import com.ut.mini.core.sign.UTSecurityThridRequestAuthentication;
import com.ut.mini.crashhandler.UTMiniCrashHandler;
import com.ut.mini.exposure.TrackerManager;
import com.ut.mini.extend.TLogExtend;
import com.ut.mini.extend.UTExtendSwitch;
import com.ut.mini.extend.WindvaneExtend;
import com.ut.mini.internal.RealtimeDebugSwitch;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import com.ut.mini.module.UTOperationStack;
import com.ut.mini.module.appstatus.UTAppBackgroundTimeoutDetector;
import com.ut.mini.module.appstatus.UTAppStatusRegHelper;
import com.ut.mini.module.plugin.UTPlugin;
import com.ut.mini.module.plugin.UTPluginMgr;
import java.util.HashMap;
import java.util.Map;

public class UTAnalytics {
    private static final String TAG = "UTAnalytics";
    private static volatile boolean mInit = false;
    private static volatile boolean mInit4app = false;
    private static UTAnalytics s_instance = new UTAnalytics();
    private HashMap<String, UTTracker> mAppkeyTrackMap = new HashMap<>();
    private UTTracker mDefaultTracker;
    private Map<String, UTTracker> mTrackerMap = new HashMap();

    public static void setDisableWindvane(boolean z) {
        UTExtendSwitch.bWindvaneExtend = !z;
    }

    private UTAnalytics() {
    }

    public static UTAnalytics getInstance() {
        return s_instance;
    }

    private void initialize(Application application, IUTApplication iUTApplication, boolean z) {
        Logger.i((String) "", "[i_initialize] start...");
        setAppVersion(iUTApplication.getUTAppVersion());
        setChannel(iUTApplication.getUTChannel());
        if (iUTApplication.isAliyunOsSystem()) {
            getInstance().setToAliyunOsPlatform();
        }
        if (iUTApplication.isUTCrashHandlerDisable()) {
            UTMiniCrashHandler.getInstance().turnOff();
        } else {
            UTMiniCrashHandler.getInstance().turnOn(application.getApplicationContext());
            if (iUTApplication.getUTCrashCraughtListener() != null) {
                UTMiniCrashHandler.getInstance().setCrashCaughtListener(iUTApplication.getUTCrashCraughtListener());
            }
        }
        if (iUTApplication.isUTLogEnable()) {
            turnOnDebug();
        }
        if (!mInit || z) {
            setRequestAuthentication(iUTApplication.getUTRequestAuthInstance());
        }
        if (!mInit) {
            UTMI1010_2001Event uTMI1010_2001Event = new UTMI1010_2001Event();
            UTVariables.getInstance().setUTMI1010_2001EventInstance(uTMI1010_2001Event);
            if (VERSION.SDK_INT >= 14) {
                UTAppStatusRegHelper.registeActivityLifecycleCallbacks(application);
                UTAppStatusRegHelper.registerAppStatusCallbacks(UTAppBackgroundTimeoutDetector.getInstance());
                UTAppStatusRegHelper.registerAppStatusCallbacks(uTMI1010_2001Event);
                UTAppStatusRegHelper.registerAppStatusCallbacks(new RealtimeDebugSwitch());
                TrackerManager.getInstance().init(application);
                AntiCheatTracker.getInstance().init(application);
            }
        }
    }

    public void registerWindvane() {
        WindvaneExtend.registerWindvane(mInit);
    }

    public void setAppApplicationInstance(Application application, IUTApplication iUTApplication) {
        try {
            if (!mInit4app) {
                if (application == null || iUTApplication == null || application.getBaseContext() == null) {
                    throw new IllegalArgumentException("application and callback must not be null");
                }
                ClientVariables.getInstance().setContext(application.getBaseContext());
                UTClientConfigMgr.getInstance().init();
                TLogExtend.registerTLog();
                AnalyticsMgr.init(application);
                initialize(application, iUTApplication, true);
                registerWindvane();
                mInit = true;
                mInit4app = true;
            }
        } catch (Throwable unused) {
        }
    }

    public void setAppApplicationInstance4sdk(Application application, IUTApplication iUTApplication) {
        try {
            if (!mInit) {
                if (application == null || iUTApplication == null || application.getBaseContext() == null) {
                    throw new IllegalArgumentException("application and callback must not be null");
                }
                ClientVariables.getInstance().setContext(application.getBaseContext());
                UTClientConfigMgr.getInstance().init();
                TLogExtend.registerTLog();
                AnalyticsMgr.init(application);
                initialize(application, iUTApplication, false);
                registerWindvane();
                mInit = true;
            }
        } catch (Throwable unused) {
        }
    }

    private boolean checkInit() {
        if (!AnalyticsMgr.isInit) {
            Logger.w((String) "Please call  () before call other method", new Object[0]);
        }
        return AnalyticsMgr.isInit;
    }

    private void setRequestAuthentication(IUTRequestAuthentication iUTRequestAuthentication) {
        boolean z;
        String str;
        String str2;
        boolean z2 = false;
        Logger.i((String) TAG, "[setRequestAuthentication] start...", UTBuildInfo.getInstance().getFullSDKVersion(), Boolean.valueOf(AnalyticsMgr.isInit));
        if (iUTRequestAuthentication == null) {
            throw new NullPointerException("签名不能为空!");
        }
        if (iUTRequestAuthentication instanceof UTSecurityThridRequestAuthentication) {
            UTSecurityThridRequestAuthentication uTSecurityThridRequestAuthentication = (UTSecurityThridRequestAuthentication) iUTRequestAuthentication;
            str2 = uTSecurityThridRequestAuthentication.getAppkey();
            str = uTSecurityThridRequestAuthentication.getAuthcode();
            z = false;
            z2 = true;
        } else if (iUTRequestAuthentication instanceof UTBaseRequestAuthentication) {
            UTBaseRequestAuthentication uTBaseRequestAuthentication = (UTBaseRequestAuthentication) iUTRequestAuthentication;
            str2 = uTBaseRequestAuthentication.getAppkey();
            str = uTBaseRequestAuthentication.getAppSecret();
            z = uTBaseRequestAuthentication.isEncode();
        } else {
            throw new IllegalArgumentException("此签名方式暂不支持!请使用 UTSecuritySDKRequestAuthentication 或 UTBaseRequestAuthentication 设置签名!");
        }
        ClientVariables.getInstance().setAppKey(str2);
        AnalyticsMgr.setRequestAuthInfo(z2, z, str2, str);
    }

    private void setAppVersion(String str) {
        AnalyticsMgr.setAppVersion(str);
    }

    private void setChannel(final String str) {
        AnalyticsMgr.setChanel(str);
        try {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public void run() {
                    SpSetting.put(ClientVariables.getInstance().getContext(), "channel", str);
                }
            });
        } catch (Throwable unused) {
        }
    }

    private void turnOffCrashHandler() {
        UTMiniCrashHandler.getInstance().turnOff();
    }

    private void turnOnDebug() {
        AnalyticsMgr.turnOnDebug();
    }

    public void registerPlugin(UTPlugin uTPlugin) {
        UTPluginMgr.getInstance().registerPlugin(uTPlugin);
    }

    public void unregisterPlugin(UTPlugin uTPlugin) {
        UTPluginMgr.getInstance().unregisterPlugin(uTPlugin);
    }

    @Deprecated
    public void updateUserAccount(String str, String str2) {
        try {
            throw new Exception("this interface is Deprecated，please call UTAnalytics.getInstance().updateUserAccount(String aUsernick, String aUserid,String openid)");
        } catch (Throwable unused) {
            updateUserAccount(str, str2, null);
        }
    }

    public void updateUserAccount(String str, String str2, String str3) {
        AnalyticsMgr.updateUserAccount(str, str2, str3);
        if (!StringUtils.isEmpty(str)) {
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder("UT", 1007, str, str2, null, null);
            uTOriginalCustomHitBuilder.setProperty(LogContentKeys.PRIORITY, "5");
            getInstance().getDefaultTracker().send(uTOriginalCustomHitBuilder.build());
        }
    }

    public void userRegister(String str) {
        if (!StringUtils.isEmpty(str)) {
            UTTracker defaultTracker = getDefaultTracker();
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder("UT", 1006, str, null, null, null);
            defaultTracker.send(uTOriginalCustomHitBuilder.build());
            return;
        }
        throw new IllegalArgumentException("Usernick can not be null or empty!");
    }

    public void updateSessionProperties(Map<String, String> map) {
        AnalyticsMgr.updateSessionProperties(map);
    }

    public void sessionTimeout() {
        UTTPKBiz.getInstance().sessionTimeout();
    }

    public void turnOffAutoPageTrack() {
        UTPageHitHelper.getInstance().turnOffAutoPageTrack();
    }

    public synchronized UTTracker getDefaultTracker() {
        if (this.mDefaultTracker == null && !TextUtils.isEmpty(ClientVariables.getInstance().getAppKey())) {
            this.mDefaultTracker = new UTTracker();
        }
        if (this.mDefaultTracker == null) {
            throw new RuntimeException("getDefaultTracker error,must call setRequestAuthentication method first");
        }
        return this.mDefaultTracker;
    }

    public synchronized UTTracker getTracker(String str) {
        try {
            if (StringUtils.isEmpty(str)) {
                throw new IllegalArgumentException("TrackId is null");
            } else if (this.mTrackerMap.containsKey(str)) {
                return this.mTrackerMap.get(str);
            } else {
                UTTracker uTTracker = new UTTracker();
                uTTracker.setTrackId(str);
                this.mTrackerMap.put(str, uTTracker);
                return uTTracker;
            }
        }
    }

    public synchronized UTTracker getTrackerByAppkey(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("appkey is null");
        } else if (this.mAppkeyTrackMap.containsKey(str)) {
            return this.mAppkeyTrackMap.get(str);
        } else {
            UTTracker uTTracker = new UTTracker();
            uTTracker.setAppKey(str);
            this.mAppkeyTrackMap.put(str, uTTracker);
            return uTTracker;
        }
    }

    /* access modifiers changed from: protected */
    public void transferLog(Map<String, String> map) {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(createTransferLogTask(map));
        }
    }

    public void turnOnRealTimeDebug(Map<String, String> map) {
        AnalyticsMgr.turnOnRealTimeDebug(map);
    }

    public void turnOffRealTimeDebug() {
        AnalyticsMgr.turnOffRealTimeDebug();
    }

    public void setToAliyunOsPlatform() {
        ClientVariables.getInstance().setToAliyunOSPlatform();
    }

    public String getOperationHistory(int i, String str) {
        return UTOperationStack.getInstance().getOperationHistory(i, str);
    }

    public void dispatchLocalHits() {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public void run() {
                    try {
                        AnalyticsMgr.iAnalytics.dispatchLocalHits();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Deprecated
    public void saveCacheDataToLocal() {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public void run() {
                    try {
                        AnalyticsMgr.iAnalytics.saveCacheDataToLocal();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public String selfCheck(String str) {
        if (!checkInit()) {
            return "local not init";
        }
        if (AnalyticsMgr.iAnalytics == null) {
            return "not bind remote service，waitting 10 second";
        }
        try {
            return AnalyticsMgr.iAnalytics.selfCheck(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Runnable createTransferLogTask(final Map<String, String> map) {
        return new Runnable() {
            public void run() {
                try {
                    AnalyticsMgr.iAnalytics.transferLog(map);
                } catch (Throwable unused) {
                }
            }
        };
    }
}
