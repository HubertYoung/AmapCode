package com.alipay.mobile.tinyappcommon.appmanager;

import android.support.annotation.Keep;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Keep
public class TinyAppUpdateCallBackManager extends H5UpdateAppCallback {
    private static final String ON_CHECK_FOR_UPDATE = "checkForUpdate";
    private static final String ON_UPDATE_FAILED = "updateFailed";
    private static final String ON_UPDATE_READY = "updateReady";
    private static final String TAG = "TinyAppUpdateCallBackManager";
    public static volatile Map<String, Boolean> sRegisteredUpdateManager = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public String mAppId;

    public TinyAppUpdateCallBackManager(String appId) {
        this.mAppId = appId;
    }

    private boolean hasRegisterUpdateManager() {
        Boolean registered = sRegisteredUpdateManager.get(this.mAppId);
        return registered != null && registered.booleanValue();
    }

    public void onResult(boolean success, boolean limit) {
        H5Log.d(TAG, "onResult...result: " + success);
        try {
            if (hasRegisterUpdateManager()) {
                JSONObject jsonObject = new JSONObject();
                final JSONObject data = new JSONObject();
                data.put((String) "data", (Object) jsonObject);
                if (success) {
                    String current = getTinyAppCurrentAvailableVersion(this.mAppId);
                    final String highest = getTinyAppHighestVersion(this.mAppId);
                    H5Log.d(TAG, "onResult...current version : " + current);
                    H5Log.d(TAG, "onResult...highest version : " + highest);
                    if (TinyappUtils.versionCompare(highest, current) == 1) {
                        jsonObject.put((String) "hasUpdate", (Object) Boolean.valueOf(true));
                        sendToWebFromMainProcess(ON_CHECK_FOR_UPDATE, data);
                        final H5AppInstallCallback callback = new H5AppInstallCallback() {
                            public final void onResult(boolean success, boolean isPatch) {
                                H5Log.d(TinyAppUpdateCallBackManager.TAG, "onResult...install : " + success);
                                try {
                                    JSONObject updateResult = new JSONObject();
                                    data.clear();
                                    data.put((String) "data", (Object) updateResult);
                                    if (success) {
                                        updateResult.put((String) "success", (Object) Boolean.valueOf(true));
                                        TinyAppUpdateCallBackManager.this.sendToWebFromMainProcess(TinyAppUpdateCallBackManager.ON_UPDATE_READY, data);
                                        return;
                                    }
                                    updateResult.put((String) "success", (Object) Boolean.valueOf(false));
                                    TinyAppUpdateCallBackManager.this.sendToWebFromMainProcess(TinyAppUpdateCallBackManager.ON_UPDATE_FAILED, data);
                                } catch (Throwable e) {
                                    H5Log.e((String) TinyAppUpdateCallBackManager.TAG, "onResult....e:" + e);
                                }
                            }
                        };
                        ScheduledThreadPoolExecutor executor = H5Utils.getScheduledExecutor();
                        if (executor != null) {
                            executor.schedule(new Runnable() {
                                public final void run() {
                                    H5AppUtil.prepare(TinyAppUpdateCallBackManager.this.mAppId, highest, callback);
                                }
                            }, 5, TimeUnit.SECONDS);
                            return;
                        }
                        return;
                    }
                    jsonObject.put((String) "hasUpdate", (Object) Boolean.valueOf(false));
                    sendToWebFromMainProcess(ON_CHECK_FOR_UPDATE, data);
                    return;
                }
                jsonObject.put((String) "hasUpdate", (Object) Boolean.valueOf(false));
                sendToWebFromMainProcess(ON_CHECK_FOR_UPDATE, data);
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, "onResult...e=" + e);
        }
    }

    /* access modifiers changed from: private */
    public void sendToWebFromMainProcess(String action, JSONObject param) {
        H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            h5Service.sendToWebFromMainProcess(this.mAppId, action, param);
        }
    }

    private String getTinyAppCurrentAvailableVersion(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        boolean z = false;
        try {
            return H5ServiceUtils.getAppDBService().findInstallAppVersion(appId);
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getTinyAppCurrentAvailableVersion...e:" + e);
            return z;
        }
    }

    private String getTinyAppHighestVersion(String appId) {
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            H5Log.d(TAG, "getTinyAppHighestVersion...h5AppProvider is null");
            return null;
        }
        boolean z = false;
        try {
            return h5AppProvider.getVersion(appId);
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getTinyAppHighestVersion...e:" + e);
            return z;
        }
    }
}
