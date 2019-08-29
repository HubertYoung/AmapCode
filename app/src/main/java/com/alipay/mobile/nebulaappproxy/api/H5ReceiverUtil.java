package com.alipay.mobile.nebulaappproxy.api;

import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.api.H5LoadPresetListen;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PresetResUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class H5ReceiverUtil {
    public static final String TAG = "H5ReceiverUtil";
    private static int a = 0;

    public static void onLoginEvent(boolean switchAccount, boolean firstLogin, boolean withPwd) {
        H5Log.d(TAG, "[onLoginEvent] switchAccount: " + switchAccount + ", firstLogin: " + firstLogin + ", withPwd: " + withPwd);
        c();
        a();
        if (switchAccount) {
            H5PreferAppList.getInstance().clearPreferAppList();
            H5AppScoreList.getInstance().clearAppScoreInfo();
            b();
        } else if (firstLogin || withPwd) {
            b();
        } else {
            int i = a + 1;
            a = i;
            if (i > 3) {
                H5Log.d(TAG, "login has receive 3 time not handler");
                return;
            }
            H5AppScoreList.getInstance().checkPreInstallApp();
            H5AppScoreList.getInstance().updateAppScoreInfo(false, null);
            downLoadNebula(H5DownloadRequest.AUTO_LOGIN);
        }
    }

    private static void a() {
        H5Utils.getScheduledExecutor().schedule(new Runnable() {
            public final void run() {
                final long time = System.currentTimeMillis();
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null && !"NO".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_preUnzipAmr"))) {
                    H5Log.d(H5ReceiverUtil.TAG, "loginPre h5_specialSyncUnzip " + H5PresetResUtil.APP_RESOURCE_PACKAGE_ID + " begin");
                    H5PresetResUtil.unzipPresetResourcePkg(new H5LoadPresetListen() {
                        public void getPresetPath(String path) {
                            if (!TextUtils.isEmpty(path)) {
                                H5Log.d(H5ReceiverUtil.TAG, "loginPre h5_specialSyncUnzip " + H5PresetResUtil.APP_RESOURCE_PACKAGE_ID + " finish cost " + ((System.currentTimeMillis() - time)));
                                H5Log.d(H5ReceiverUtil.TAG, "getPresetPath: " + path);
                            }
                        }
                    });
                }
            }
        }, 3, TimeUnit.SECONDS);
    }

    private static void b() {
        H5AppScoreList.getInstance().updateAppScoreInfo(true, null);
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            h5AppProvider.updateApp(H5UpdateAppParam.newBuilder().setDownLoadAmr(true).setDownloadRandom(true).build());
        }
    }

    private static void c() {
        synchronized (H5SyncUtil.listSync) {
            for (String sync : H5SyncUtil.listSync) {
                H5Log.d(TAG, "handleNotLoginSyncData " + sync);
                H5SyncUtil.execute(sync);
            }
            H5SyncUtil.listSync.clear();
        }
    }

    /* access modifiers changed from: private */
    public static void b(String scene) {
        H5AppCenterService appCenterService = (H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName());
        if (appCenterService != null) {
            long time = System.currentTimeMillis();
            Map nebulaList = appCenterService.getAppDBService().getAllHighestAppInfo();
            long getFromDBTime = System.currentTimeMillis() - time;
            if (nebulaList != null && !nebulaList.isEmpty()) {
                for (String appId : nebulaList.keySet()) {
                    AppInfo appInfo = nebulaList.get(appId);
                    if (H5AppUtil.downloadH5App(appInfo)) {
                        H5BaseApp h5BaseApp = appCenterService.getH5App();
                        h5BaseApp.setAppInfo(appInfo);
                        boolean isAvailable = h5BaseApp.isAvailable();
                        if (!isAvailable) {
                            h5BaseApp.downloadApp(null, scene);
                        }
                        H5Log.d(TAG, "preDownload appId:" + appId + " version:" + appInfo.version + " hasDownload:" + isAvailable);
                    }
                }
                H5Log.d(TAG, "preDownload done AllCost:" + (System.currentTimeMillis() - time) + " getFromDBTime:" + getFromDBTime + " size:" + nebulaList.size());
            }
        }
    }

    public static void downLoadNebula(final String scene) {
        TaskScheduleService scheduleService = (TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService != null) {
            scheduleService.acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
                public final void run() {
                    ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
                    if (configService == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(configService.getConfig("h5_downloadall"))) {
                        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                        if (h5AppProvider == null) {
                            H5Log.e((String) H5ReceiverUtil.TAG, (String) "h5AppProvider == null");
                            return;
                        }
                        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                        if (h5ConfigProvider == null || BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_preDownload"))) {
                            H5AppCenterService appCenterService = (H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName());
                            if (appCenterService != null) {
                                long time = System.currentTimeMillis();
                                Map nebulaList = appCenterService.getAppDBService().getAllHighestAppVersion();
                                long nebulaAppTime = System.currentTimeMillis();
                                H5Log.d(H5ReceiverUtil.TAG, "nebulaList getAllHighestAppVersion " + nebulaList + " cost: " + (nebulaAppTime - time));
                                Map mappingApp = h5AppProvider.syncAppManage();
                                H5Log.d(H5ReceiverUtil.TAG, "openPlatApp:" + mappingApp + " cost：" + (System.currentTimeMillis() - nebulaAppTime));
                                if (mappingApp != null && !mappingApp.isEmpty()) {
                                    for (String appId : mappingApp.keySet()) {
                                        if (nebulaList != null) {
                                            nebulaList.remove(appId);
                                        }
                                        H5ReceiverUtil.download(h5AppProvider, appId, mappingApp.get(appId), false, null, scene);
                                    }
                                }
                                if (nebulaList != null && !nebulaList.isEmpty()) {
                                    H5Log.d(H5ReceiverUtil.TAG, "nebulaList " + nebulaList);
                                    for (String appId2 : nebulaList.keySet()) {
                                        H5ReceiverUtil.download(h5AppProvider, appId2, nebulaList.get(appId2), true, appCenterService.getAppDBService(), scene);
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        H5ReceiverUtil.b(scene);
                    }
                }
            });
        }
    }

    public static void download(H5AppProvider h5AppProvider, String appId, String version, boolean getFromNebula, H5AppDBService h5AppDBService, String scene) {
        AppInfo appInfo;
        if (getFromNebula) {
            appInfo = h5AppDBService.getAppInfo(appId, version);
        } else {
            appInfo = h5AppProvider.getAppInfo(appId, version);
        }
        if (appInfo == null) {
            H5Log.e((String) TAG, appId + " appInfo == null getFromNebula:" + getFromNebula);
        } else if (H5AppUtil.downloadH5App(appInfo)) {
            H5BaseApp h5BaseApp = ((H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName())).getH5App();
            h5BaseApp.setAppInfo(appInfo);
            boolean isAvailable = h5BaseApp.isAvailable();
            if (!isAvailable) {
                h5BaseApp.downloadApp(null, scene);
            }
            H5Log.d(TAG, "queryNebulaApps appId:" + appId + " version:" + appInfo.version + " hasDownload:" + isAvailable);
        }
    }
}
