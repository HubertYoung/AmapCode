package com.alipay.mobile.nebulaappproxy.api;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.H5BaseApp;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppScoreList;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5BehaviorLogConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class H5AppInfoUploadUtil {
    public static void uploadAllAppInfo() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        H5AppCenterService appCenterService = (H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName());
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5ConfigProvider != null && h5LoginProvider != null && appCenterService != null && h5AppProvider != null) {
            String uploadConfig = h5ConfigProvider.getConfig("h5_webstatZhuangJi");
            if (!TextUtils.isEmpty(uploadConfig) && h5LoginProvider.isLogin()) {
                H5AppDBService h5AppDBService = appCenterService.getAppDBService();
                if (h5AppDBService != null) {
                    JSONObject configObj = H5Utils.parseObject(uploadConfig);
                    if (configObj != null && "YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) FunctionSupportConfiger.SWITCH_TAG))) {
                        long uploadRate = TimeUnit.HOURS.toMillis(24);
                        try {
                            int rate = Integer.parseInt(H5Utils.getString(configObj, (String) "rate"));
                            if (rate > 0) {
                                uploadRate = TimeUnit.SECONDS.toMillis((long) rate);
                            }
                        } catch (Throwable throwable) {
                            H5Log.e((String) "H5AppInfoUploadUtil", throwable);
                        }
                        if (a(uploadRate)) {
                            String appInfoStr = "";
                            String param4Str = "";
                            Map map = h5AppDBService.getAllHighestAppInfo();
                            Map appCenterMap = h5AppProvider.queryAllH5AppVersionFromAppCenter();
                            if (map != null && !map.isEmpty()) {
                                long time = System.currentTimeMillis();
                                for (Entry appEntry : map.entrySet()) {
                                    String appId = (String) appEntry.getKey();
                                    AppInfo appInfo = (AppInfo) appEntry.getValue();
                                    if (appInfo != null) {
                                        H5BaseApp h5BaseApp = appCenterService.getH5App();
                                        h5BaseApp.setAppInfo(appInfo);
                                        String isDownload = h5BaseApp.isAvailable() ? "Y" : "N";
                                        if ("N".equals(isDownload)) {
                                            param4Str = param4Str + appId + "_" + appInfo.unAvailableReason + ";";
                                        }
                                        String isInstall = h5BaseApp.isInstalled() ? "Y" : "N";
                                        String credit = H5AppScoreList.getInstance().getAppCredit(appId);
                                        String isPatch = TextUtils.isEmpty(appInfo.patch) ? "N" : "Y";
                                        String appCenterVersion = "0";
                                        if (appCenterMap != null && !appCenterMap.isEmpty() && appCenterMap.containsKey(appId)) {
                                            appCenterVersion = appCenterMap.get(appId);
                                        }
                                        if (TextUtils.isEmpty(appInfoStr)) {
                                            appInfoStr = appId + "_" + appInfo.version + "_" + isDownload + "_" + isInstall + "_" + credit + "_" + isPatch + "_" + appCenterVersion;
                                        } else {
                                            appInfoStr = appInfoStr + ";" + appId + "_" + appInfo.version + "_" + isDownload + "_" + isInstall + "_" + credit + "_" + isPatch + "_" + appCenterVersion;
                                        }
                                    }
                                }
                                H5Log.d("H5AppInfoUploadUtil", "uploadAllAppInfo getAllHighestAppInfo cost:" + (System.currentTimeMillis() - time) + " map.size():" + map.size());
                            }
                            StringBuilder sb = new StringBuilder("step=upload^info=");
                            H5LogUtil.behaviorLog(H5LogData.seedId("H5_APP_WSZHUANGJI").param3().add(sb.append(appInfoStr).toString(), null).param4().add(param4Str, null), H5BehaviorLogConfig.newH5BehaviorLogConfig().setBehaviourPro(H5BehaviorLogConfig.WEBSTAT_BEHAVIOUR));
                            H5DevConfig.setStringConfig(H5DevConfig.H5_UPLOAD_ALL_APP_INFO, String.valueOf(System.currentTimeMillis()));
                        }
                    }
                }
            }
        }
    }

    private static boolean a(long rate) {
        String lastUploadStr = H5DevConfig.getStringConfig(H5DevConfig.H5_UPLOAD_ALL_APP_INFO, null);
        if (!TextUtils.isEmpty(lastUploadStr)) {
            long lastTime = 0;
            try {
                lastTime = Long.parseLong(lastUploadStr);
            } catch (Throwable throwable) {
                H5Log.e((String) "H5AppInfoUploadUtil", throwable);
            }
            long currentTime = System.currentTimeMillis();
            H5Log.d("H5AppInfoUploadUtil", "currentTime : " + currentTime + " lastTime : " + lastTime + " rate :" + rate);
            if (lastTime <= 0 || currentTime - lastTime <= rate) {
                return false;
            }
            return true;
        }
        H5DevConfig.setStringConfig(H5DevConfig.H5_UPLOAD_ALL_APP_INFO, String.valueOf(System.currentTimeMillis()));
        return true;
    }
}
