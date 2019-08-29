package com.alipay.mobile.nebula.appcenter.apphandler;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class H5PreferAppList {
    private static final String TAG = "H5PreferAppList";
    public static final long defaultTime = 10800000;
    private static H5PreferAppList instance = null;
    public static final String nbprefer = "nbprefer";
    public static final String nbsn = "nbsn";
    public static final String nbsv = "nbsv";
    private static Map<String, Boolean> tinyProcessMap = new ConcurrentHashMap();
    private Map<String, H5PreferAppInfo> preferList = new ConcurrentHashMap();

    private H5PreferAppList() {
        Map savedList = jsonToMap(getPreferAppListStr());
        if (savedList != null && savedList.size() > 0) {
            this.preferList.putAll(savedList);
        }
        checkExpired();
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

    private void checkExpired() {
        if (this.preferList != null && this.preferList.size() != 0) {
            Iterator iterator = this.preferList.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry entry = iterator.next();
                H5PreferAppInfo value = (H5PreferAppInfo) entry.getValue();
                if (value != null && isExpired(value.getSaveTime())) {
                    deleteAllAppInfo((String) entry.getKey(), value.getNbsv());
                    iterator.remove();
                }
            }
            savePreferAppList();
        }
    }

    public static synchronized H5PreferAppList getInstance() {
        H5PreferAppList h5PreferAppList;
        synchronized (H5PreferAppList.class) {
            try {
                if (instance == null) {
                    instance = new H5PreferAppList();
                }
                h5PreferAppList = instance;
            }
        }
        return h5PreferAppList;
    }

    public synchronized void add(String appId, String version, String nbsn2) {
        if (!TextUtils.isEmpty(appId)) {
            this.preferList.put(appId, new H5PreferAppInfo(version, System.currentTimeMillis(), nbsn2));
            savePreferAppList();
        }
    }

    public synchronized void remove(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            if (this.preferList.get(appId) != null) {
                this.preferList.remove(appId);
                savePreferAppList();
                H5Log.d(TAG, "also remove from H5DevAppList");
                H5DevAppList.getInstance().remove(appId);
            }
        }
    }

    public synchronized String getPreferVersion(String appId) {
        String nbsv2;
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        nbsv2 = h5IpcServer.getPreferVersion(appId);
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) TAG, throwable);
                }
            }
            nbsv2 = "";
        } else if (TextUtils.isEmpty(appId)) {
            nbsv2 = "";
        } else {
            H5PreferAppInfo h5PreferAppInfo = this.preferList.get(appId);
            if (h5PreferAppInfo == null) {
                nbsv2 = "";
            } else {
                nbsv2 = h5PreferAppInfo.getNbsv();
            }
        }
        return nbsv2;
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
                            contain = h5IpcServer.containPreferAppId(appId);
                            tinyProcessMap.put(appId, Boolean.valueOf(contain));
                        }
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, throwable);
                    }
                }
                contain = false;
            }
        } else if (TextUtils.isEmpty(appId)) {
            contain = false;
        } else {
            contain = this.preferList.containsKey(appId);
            if (contain) {
                H5PreferAppInfo info = this.preferList.get(appId);
                if (info == null) {
                    contain = false;
                } else if (isExpired(info.getSaveTime())) {
                    remove(appId);
                    deleteAllAppInfo(appId, info.getNbsv());
                    contain = false;
                }
            }
        }
        return contain;
    }

    public void clearProcessCache(String appId) {
        if (H5Utils.isInTinyProcess()) {
            tinyProcessMap.remove(appId);
        }
    }

    private void deleteAllAppInfo(final String appId, final String version) {
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(version)) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    H5PreferAppList.this.deleteAppInfoAndInstallPath(appId, version);
                }
            });
        }
    }

    public void deleteAppInfoWithAppId(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            H5PreferAppInfo info = this.preferList.get(appId);
            if (info != null) {
                deleteAppInfoAndInstallPath(appId, info.getNbsv());
                remove(appId);
            }
        }
    }

    /* access modifiers changed from: private */
    public void deleteAppInfoAndInstallPath(String appId, String version) {
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            String installPath = h5AppProvider.getInstallPath(appId, version);
            String downloadPath = h5AppProvider.getDownloadLocalPath(appId, version);
            H5Log.d(TAG, "deleteAllAppInfo, appId: " + appId + " version: " + version + " installPath : " + installPath + " downloadPath :" + downloadPath);
            H5FileUtil.delete(installPath);
            H5FileUtil.delete(downloadPath);
        }
        H5AppDBService h5AppDBService = H5ServiceUtils.getAppDBService();
        if (h5AppDBService != null) {
            H5Log.d(TAG, "deleteDBAppInfo, appId: " + appId + " version: " + version);
            h5AppDBService.deleteAppInfo(appId, version);
        }
    }

    public static boolean enableUsePrefer(Bundle param) {
        if (param == null || !param.containsKey(nbprefer) || !"yes".equalsIgnoreCase(param.getString(nbprefer))) {
            return false;
        }
        return true;
    }

    private void savePreferAppList() {
        String preferListStr = H5Utils.toJSONString(this.preferList);
        H5Log.d(TAG, "set preferList: " + preferListStr);
        H5DevConfig.setStringConfig(H5DevConfig.H5_PREFER_APP_LIST, preferListStr);
    }

    private String getPreferAppListStr() {
        String preferListStr = H5DevConfig.getStringConfig(H5DevConfig.H5_PREFER_APP_LIST, null);
        H5Log.d(TAG, "get preferList: " + preferListStr);
        return preferListStr;
    }

    public void clearPreferAppList() {
        this.preferList.clear();
        H5DevConfig.setStringConfig(H5DevConfig.H5_PREFER_APP_LIST, "");
    }

    public boolean isExpired(long saveTime) {
        long expiredTime;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONObject jsonObject = H5Utils.parseObject(h5ConfigProvider.getConfig("h5_preferExpired"));
            if (jsonObject == null || jsonObject.isEmpty()) {
                expiredTime = defaultTime;
            } else {
                int time = 0;
                try {
                    time = Integer.parseInt(H5Utils.getString(jsonObject, (String) "time"));
                } catch (Throwable t) {
                    H5Log.e((String) TAG, t);
                }
                if (time > 0) {
                    expiredTime = (long) (time * 1000);
                } else {
                    expiredTime = defaultTime;
                }
            }
        } else {
            expiredTime = defaultTime;
        }
        boolean result = System.currentTimeMillis() > saveTime + expiredTime;
        H5Log.d(TAG, "result : " + result + " currentTime " + System.currentTimeMillis() + " saveTime : " + saveTime + " expiredTime : " + expiredTime);
        return result;
    }

    public static boolean enablePreferList() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return true;
        }
        JSONObject jsonObject = H5Utils.parseObject(h5ConfigProvider.getConfig("h5_preferExpired"));
        if (jsonObject == null || jsonObject.isEmpty() || !CameraParams.FLASH_MODE_OFF.equalsIgnoreCase(H5Utils.getString(jsonObject, (String) FunctionSupportConfiger.SWITCH_TAG))) {
            return true;
        }
        return false;
    }

    private Map<String, H5PreferAppInfo> jsonToMap(String jsonStr) {
        JSONObject jsonObject = H5Utils.parseObject(jsonStr);
        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        }
        Map map = new HashMap();
        for (String key : jsonObject.keySet()) {
            try {
                map.put(key, (H5PreferAppInfo) JSON.parseObject(jsonObject.get(key).toString(), H5PreferAppInfo.class));
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
        H5Log.d(TAG, "parse map : " + map.toString());
        return map;
    }

    public static void startCheckPermissionScheme(H5StartAppInfo h5StartAppInfo) {
        StringBuilder encodeSchemeBuilder = new StringBuilder();
        encodeSchemeBuilder.append("alipays://platformapi/startapp?appId=" + h5StartAppInfo.targetAppId);
        Bundle copyParams = new Bundle();
        if (h5StartAppInfo.params != null) {
            copyParams = (Bundle) h5StartAppInfo.params.clone();
        }
        copyParams.putString("nbsource", "debug");
        copyParams.putString("nbsn", getNbsn(h5StartAppInfo.targetAppId));
        copyParams.putString(nbsv, getInstance().getPreferVersion(h5StartAppInfo.targetAppId));
        for (String key : copyParams.keySet()) {
            try {
                encodeSchemeBuilder.append("&" + key + "=" + H5UrlHelper.encode(copyParams.get(key).toString()));
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
        H5Log.d(TAG, "encodeSchemeBuilder : " + encodeSchemeBuilder.toString());
        String encodeScheme = H5UrlHelper.encode(encodeSchemeBuilder.toString());
        H5EnvProvider h5EnvProvider = (H5EnvProvider) H5Utils.getProvider(H5EnvProvider.class.getName());
        if (h5EnvProvider != null) {
            H5Log.d(TAG, "scheme : " + "alipays://platformapi/startapp?appId=20001101&token=preferconvert&scheme=" + encodeScheme);
            h5EnvProvider.goToSchemeService("alipays://platformapi/startapp?appId=20001101&token=preferconvert&scheme=" + encodeScheme);
        }
    }

    private static String getNbsn(String appId) {
        H5PreferAppInfo preferAppInfo = getInstance().preferList.get(appId);
        if (preferAppInfo == null) {
            return "";
        }
        String preferNbsn = preferAppInfo.getNbsn();
        H5Log.d(TAG, " preferNbsn : " + preferNbsn);
        return preferNbsn;
    }
}
