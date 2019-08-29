package com.alipay.mobile.nebula.appcenter.apphandler;

import android.text.TextUtils;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class H5DevAppList {
    private static final String TAG = "H5DevAppList";
    private static H5InstallAppAdvice h5InstallAppAdvice;
    private static H5DevAppList instance;
    private static Map<String, Boolean> tinyProcessMap = new ConcurrentHashMap();
    private Map<String, H5DevAppInfo> mDevAppMaps = new ConcurrentHashMap();
    private Map<String, String> tinyAppRecordMap = new ConcurrentHashMap();

    private H5DevAppList() {
        H5AppCenterPresetProvider h5AppCenterPresetProvider = (H5AppCenterPresetProvider) H5Utils.getProvider(H5AppCenterPresetProvider.class.getName());
        if (h5AppCenterPresetProvider != null) {
            Set<String> appSet = h5AppCenterPresetProvider.getCommonResourceAppList();
            if (appSet != null && !appSet.isEmpty()) {
                for (String id : appSet) {
                    tinyProcessMap.put(id, Boolean.valueOf(false));
                }
            }
        }
    }

    public static synchronized H5DevAppList getInstance() {
        H5DevAppList h5DevAppList;
        synchronized (H5DevAppList.class) {
            try {
                if (instance == null) {
                    instance = new H5DevAppList();
                }
                h5DevAppList = instance;
            }
        }
        return h5DevAppList;
    }

    public synchronized void add(String appId, H5DevAppInfo h5DevAppInfo) {
        if (appId != null) {
            this.mDevAppMaps.put(appId, h5DevAppInfo);
        }
    }

    public synchronized void remove(final String appId) {
        if (appId != null) {
            if (H5Utils.isInTinyProcess()) {
                H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                    public void run() {
                        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                        if (h5EventHandlerService != null) {
                            try {
                                H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                                if (h5IpcServer != null) {
                                    h5IpcServer.removeDevApp(appId);
                                }
                            } catch (Throwable throwable) {
                                H5Log.e((String) H5DevAppList.TAG, throwable);
                            }
                        }
                    }
                });
            } else {
                H5Log.d(TAG, " remove : " + appId);
                this.mDevAppMaps.remove(appId);
            }
        }
    }

    public void addTinyAppRecord(AppInfo appInfo) {
        if (H5TinyAppDebugMode.enableTinyAppDebugMode()) {
            if (this.tinyAppRecordMap == null || appInfo == null || !H5Utils.isTinyApp(appInfo)) {
                H5Log.d(TAG, "is not tinyApp");
                return;
            }
            String appId = appInfo.app_id;
            String version = appInfo.version;
            H5Log.d(TAG, "tinyAppRecordMap put appId : " + appId + " version : " + version);
            this.tinyAppRecordMap.put(appId, version);
        }
    }

    public boolean needStopLiteProcessByAppId(AppInfo appInfo) {
        if (!H5TinyAppDebugMode.enableTinyAppDebugMode()) {
            return false;
        }
        if (appInfo == null) {
            H5Log.d(TAG, "appInfo == null");
            return false;
        }
        String startAppId = appInfo.app_id;
        String startVersion = appInfo.version;
        H5Log.d(TAG, " startAppId : " + startAppId + " startVersion : " + startVersion);
        if (TextUtils.isEmpty(startAppId) || TextUtils.isEmpty(startVersion)) {
            return false;
        }
        if (this.tinyAppRecordMap == null || this.tinyAppRecordMap.isEmpty()) {
            H5Log.d(TAG, "tinyAppRecordMap isEmpty, return false");
            return false;
        } else if (!this.tinyAppRecordMap.containsKey(startAppId)) {
            H5Log.d(TAG, "tinyAppRecordMap not contain appId : " + startAppId);
            return false;
        } else {
            String recordVersion = this.tinyAppRecordMap.get(startAppId);
            H5Log.d(TAG, "recordVersion : " + recordVersion);
            if (!TextUtils.equals(recordVersion, startVersion)) {
                return true;
            }
            return false;
        }
    }

    public synchronized H5DevAppInfo getDevInfo(String appId) {
        H5DevAppInfo h5DevAppInfo;
        try {
            if (TextUtils.isEmpty(appId)) {
                h5DevAppInfo = null;
            } else {
                h5DevAppInfo = this.mDevAppMaps.get(appId);
            }
        }
        return h5DevAppInfo;
    }

    public synchronized String getDevInfoNbsv(String appId) {
        String str = null;
        synchronized (this) {
            if (H5Utils.isInTinyProcess()) {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    try {
                        H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                        if (h5IpcServer != null) {
                            str = h5IpcServer.getDevNbsv(appId);
                        }
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, throwable);
                    }
                }
                str = "";
            } else if (!TextUtils.isEmpty(appId)) {
                H5DevAppInfo h5DevAppInfo = this.mDevAppMaps.get(appId);
                if (h5DevAppInfo != null) {
                    str = h5DevAppInfo.nbsv;
                }
            }
        }
        return str;
    }

    public synchronized boolean contains(String appId) {
        boolean contain;
        if (H5Utils.isInTinyProcess()) {
            Boolean containBool = tinyProcessMap.get(appId);
            if (containBool != null) {
                contain = containBool.booleanValue();
            } else {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    try {
                        H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                        if (h5IpcServer != null) {
                            contain = h5IpcServer.containDevAppId(appId);
                            tinyProcessMap.put(appId, Boolean.valueOf(contain));
                        }
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, throwable);
                    }
                }
                contain = false;
            }
        } else if (H5Utils.isDebuggable(H5Utils.getContext()) && H5DevConfig.getBooleanConfig(H5DevConfig.h5_read_use_dev_db, false)) {
            contain = true;
        } else if (appId == null) {
            contain = false;
        } else {
            contain = this.mDevAppMaps.containsKey(appId);
        }
        return contain;
    }

    public void setUseDevMode(String appId, boolean use) {
        H5Log.d(TAG, "setUseDevMode appId: " + appId + ", use: " + use + ", h5InstallAppAdvice: " + h5InstallAppAdvice);
        if (use) {
            registerInstallAdvice();
        } else {
            if (h5InstallAppAdvice != null) {
                H5InstallAppAdvice.restoreApplicationDescription(appId);
            }
            remove(appId);
            if (this.mDevAppMaps.isEmpty()) {
                unRegisterInstallAdvice();
            } else {
                String log = "";
                for (String id : this.mDevAppMaps.keySet()) {
                    log = log + Token.SEPARATOR + id;
                }
                H5Log.d(TAG, "dev list is not empty still exist " + log);
            }
        }
        if (H5Utils.isInTinyProcess() && !use) {
            tinyProcessMap.remove(appId);
        }
    }

    private void registerInstallAdvice() {
        if (h5InstallAppAdvice == null) {
            h5InstallAppAdvice = new H5InstallAppAdvice();
            FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_INSTALLAPP, (Advice) h5InstallAppAdvice);
        }
    }

    private void unRegisterInstallAdvice() {
        if (h5InstallAppAdvice != null) {
            FrameworkPointCutManager.getInstance().unRegisterPointCutAdvice(h5InstallAppAdvice);
            h5InstallAppAdvice = null;
        }
    }

    public boolean isDevAppInfo(String scene) {
        H5Log.d(TAG, "scene : " + scene);
        if (H5TinyAppDebugMode.enableTinyAppDebugMode() && !TextUtils.isEmpty(scene) && !"online".equalsIgnoreCase(scene)) {
            return true;
        }
        return false;
    }
}
