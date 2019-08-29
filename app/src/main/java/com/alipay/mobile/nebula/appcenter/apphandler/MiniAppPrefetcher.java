package com.alipay.mobile.nebula.appcenter.apphandler;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam.Builder;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.HashMap;
import java.util.Map;

public class MiniAppPrefetcher {
    private static final String PREFETCH_CONFIG = "prefetch_config";
    private static final String TAG = "MiniAppPrefetcher";

    public static void prefetchApp(String dependantAppId) {
        if (!TextUtils.isEmpty(dependantAppId)) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null) {
                JSONObject prefetchConfig = h5ConfigProvider.getConfigJSONObject(PREFETCH_CONFIG);
                if (prefetchConfig != null) {
                    JSONArray deps = H5Utils.getJSONArray(prefetchConfig, dependantAppId, null);
                    if (deps != null && !deps.isEmpty()) {
                        for (int i = 0; i < deps.size(); i++) {
                            prefetchAppCore(deps.getString(i));
                        }
                    }
                }
            }
        }
    }

    private static void prefetchAppCore(final String appId) {
        final H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            String queryVersion = h5AppProvider.getWalletConfigNebulaVersion(appId);
            Map appIdMap = new HashMap();
            appIdMap.put(appId, queryVersion);
            AppReq appReq = new AppReq();
            appReq.reqmode = "syncforce";
            appReq.stableRpc = "NO";
            final Builder builder = H5UpdateAppParam.newBuilder().setAppMap(appIdMap).setAppReq(appReq).setDownLoadAmr(true).setStartTime(System.currentTimeMillis()).setUpdateCallback(new H5UpdateAppCallback() {
                public final void onResult(boolean success, boolean limit) {
                    H5Log.d(MiniAppPrefetcher.TAG, "prefetch app, appId:" + appId + ", update result:" + success);
                    if (H5AppUtil.isOffLine(appId)) {
                        H5Log.d(MiniAppPrefetcher.TAG, "prefetch app, app is offline:" + appId);
                    } else if (success) {
                        MiniAppPrefetcher.offlineApp(h5AppProvider, appId);
                    }
                }
            });
            H5Utils.getExecutor("RPC").execute(new Runnable() {
                public final void run() {
                    try {
                        h5AppProvider.updateApp(builder.build());
                    } catch (Exception e) {
                        H5Log.e((String) MiniAppPrefetcher.TAG, (Throwable) e);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void offlineApp(final H5AppProvider h5AppProvider, final String appId) {
        final AppInfo appInfo = h5AppProvider.getAppInfo(appId);
        if (appInfo == null) {
            H5Log.d(TAG, "prefetch appInfo success, but appInfo null:" + appId);
            return;
        }
        boolean isAvailable = h5AppProvider.isAvailable(appId, appInfo.version);
        H5Log.d(TAG, "appId:" + appId + ", isAvailable:" + isAvailable);
        if (!isAvailable) {
            h5AppProvider.downloadApp(appId, appInfo.version, new H5DownloadCallback() {
                public final void onFinish(H5DownloadRequest h5DownloadRequest, String savePath) {
                    H5Log.d(MiniAppPrefetcher.TAG, "app download finish:" + appId);
                    H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                        public void run() {
                            MiniAppPrefetcher.installApp(h5AppProvider, appId, appInfo);
                        }
                    });
                }

                public final void onFailed(H5DownloadRequest h5DownloadRequest, int errorCode, String errorMsg) {
                    H5Log.d(MiniAppPrefetcher.TAG, "app download failed:" + appId);
                }
            });
            return;
        }
        boolean isInstalled = h5AppProvider.isInstalled(appId, appInfo.version);
        H5Log.d(TAG, "appId:" + appId + ", inInstalled:" + isInstalled);
        if (!isInstalled) {
            installApp(h5AppProvider, appId, appInfo);
        }
    }

    /* access modifiers changed from: private */
    public static void installApp(H5AppProvider h5AppProvider, final String appId, AppInfo appInfo) {
        h5AppProvider.installApp(appId, appInfo.version, (H5AppInstallCallback) new H5AppInstallCallback() {
            public final void onResult(boolean success, boolean isPatch) {
                H5Log.d(MiniAppPrefetcher.TAG, "install app result " + success + appId);
            }
        });
    }
}
