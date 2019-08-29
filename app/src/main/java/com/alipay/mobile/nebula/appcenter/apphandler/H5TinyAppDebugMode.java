package com.alipay.mobile.nebula.appcenter.apphandler;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider.AuthRpcCallback;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5TinyDebugModeProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5TinyAppDebugMode {
    private static final String APP_CENTER_TOKEN = "appcenter";
    public static final String KEY_NB_TOKEN = "nbtoken";
    public static final String KEY_WHITE_LIST = "domainWhiteList";
    private static final String TAG = "H5TinyAppDebugMode";

    public static boolean enableTinyAppDebugMode() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !"true".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("smallprogram_debug_close_switch"))) {
            return true;
        }
        return false;
    }

    public static void addRecentAppForDebugMode(Bundle param, AppInfo appInfo) {
        if (enableTinyAppDebugMode() && H5InstallAppAdvice.enableUseDevMode(param) && checkNbToken(param)) {
            H5DevAppInfo devAppInfo = H5DevAppList.getInstance().getDevInfo(appInfo.app_id);
            if (devAppInfo == null) {
                H5Log.d(TAG, "H5DevAppList not contain appId : " + appInfo.app_id);
                return;
            }
            H5TinyDebugModeProvider debugModeProvider = (H5TinyDebugModeProvider) H5Utils.getProvider(H5TinyDebugModeProvider.class.getName());
            if (debugModeProvider != null) {
                debugModeProvider.addRecentAppForDebugMode(appInfo.app_id, devAppInfo.nbsn, devAppInfo.nbsv, appInfo.name, appInfo.icon_url, appInfo.slogan, null);
            }
        }
    }

    private static boolean checkNbToken(Bundle param) {
        return !APP_CENTER_TOKEN.equalsIgnoreCase(H5Utils.getString(param, (String) KEY_NB_TOKEN));
    }

    public static void doRpcAuth(Bundle param, final H5StartAppInfo startAppInfo) {
        final String devAppId = startAppInfo.targetAppId;
        String nbsn = H5Utils.getString(param, (String) "nbsn");
        String nbsv = H5Utils.getString(param, (String) H5PreferAppList.nbsv);
        String token = H5Utils.getString(param, (String) KEY_NB_TOKEN);
        final H5DevAppInfo h5DevAppInfo = new H5DevAppInfo();
        h5DevAppInfo.nbsn = nbsn;
        h5DevAppInfo.nbsv = nbsv;
        H5Log.d(TAG, "devAppId : " + devAppId + " nbsn : " + nbsn + " nbsv : " + nbsv + " token : " + token);
        if (!H5Utils.isDebuggable(H5Utils.getContext()) || !H5DevConfig.getBooleanConfig(H5DevConfig.h5_read_use_dev_app_config, false)) {
            H5BugMeRpcAuthProvider authProvider = (H5BugMeRpcAuthProvider) H5Utils.getProvider(H5BugMeRpcAuthProvider.class.getName());
            if (authProvider != null) {
                authProvider.rpcAuth(devAppId, nbsn, null, token, new AuthRpcCallback() {
                    public final void onResponse(boolean pass, boolean isSuperUser, String[] domainWhiteList) {
                        H5Log.d(H5TinyAppDebugMode.TAG, "pass : " + pass + " isSuperUser : " + isSuperUser);
                        if (pass) {
                            H5DevAppList.getInstance().add(devAppId, h5DevAppInfo);
                            H5TinyAppDebugMode.handleResponse(startAppInfo, domainWhiteList);
                            return;
                        }
                        H5DevAppList.getInstance().remove(devAppId);
                        H5TinyAppDebugMode.showFailToast();
                    }
                });
                return;
            }
            return;
        }
        H5DevAppList.getInstance().add(devAppId, h5DevAppInfo);
        handleResponse(startAppInfo, null);
    }

    /* access modifiers changed from: private */
    public static void handleResponse(H5StartAppInfo startAppInfo, String[] domainWhiteList) {
        String whiteList = "";
        if (domainWhiteList != null) {
            for (String domain : domainWhiteList) {
                whiteList = whiteList + H5UrlHelper.encode(domain) + MergeUtil.SEPARATOR_KV;
            }
        }
        Bundle startParams = startAppInfo.params;
        if (startParams != null) {
            startParams.putString(KEY_WHITE_LIST, whiteList);
        }
        startAppInfo.hasAuth = true;
        H5Log.d(TAG, "do syncApp");
        H5AppHandler.syncApp(startAppInfo);
    }

    /* access modifiers changed from: private */
    public static void showFailToast() {
        H5Utils.runOnMain(new Runnable() {
            public final void run() {
                try {
                    Context context = H5Utils.getContext();
                    if (context != null) {
                        Toast.makeText(context, "抱歉，您未获得此应用的使用权限", 1).show();
                    }
                } catch (Throwable t) {
                    H5Log.e((String) H5TinyAppDebugMode.TAG, t);
                }
            }
        });
    }
}
