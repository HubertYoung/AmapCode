package com.alipay.android.nebulaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam.Builder;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcustom.api.MiniProgramAuthService;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MiniAppUtil {
    private static final String MINIAPP_CLOUD_CONTROL_KEY = "miniapp";
    private static final String MINIAPP_ENABLE_KEY = "miniapp_enable";
    private static final String MINIAPP_SINGLE_ENABLE_KEY = "enable";
    private static final String MINIAPP_SINGLE_ENABLE_LOGIN_KEY = "needLogin";
    private static final String TAG = "MiniAppUtil";
    /* access modifiers changed from: private */
    public static boolean amapAppxUpdateSuccess = false;
    private static List<Activity> h5Activities = new LinkedList();
    /* access modifiers changed from: private */
    public static boolean isDoingAppxUpdate = false;
    private static boolean isStartedMiniApp = false;

    private static void startMiniAppNotLogin(Context context, String str, String str2, Bundle bundle) {
        MiniProgramAuthService.get(context).openMiniProgram(str, str2, bundle);
    }

    public static void startMiniApp(Context context, String str, String str2, Bundle bundle) {
        if (bno.a || isMiniAppEnable()) {
            MiniAppInitHelper.getInstance().initMiniApp();
            isStartedMiniApp = true;
            refreshAlipayLoggingInfo();
            loadConfig();
            finishH5Activity();
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            startMiniAppNotLogin(context, str, str2, bundle);
            if (h5ConfigProvider != null) {
                String configWithProcessCache = h5ConfigProvider.getConfigWithProcessCache("h5_amapUpdateAppx");
                if (!amapAppxUpdateSuccess && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(configWithProcessCache) && !isDoingAppxUpdate) {
                    H5Log.d(TAG, "pre update appx for newest version");
                    updateAppx();
                }
            }
            return;
        }
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_not_support_and_update));
    }

    public static void loadConfig() {
        ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            configService.loadConfig();
        }
    }

    public static void refreshAfterLogin(String str) {
        ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            configService.refreshAfterLogin(str);
        }
    }

    public static void refreshAfterLogout() {
        ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            configService.refreshAfterLogout();
        }
    }

    private static void updateAppx() {
        final H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            isDoingAppxUpdate = true;
            String walletConfigNebulaVersion = h5AppProvider.getWalletConfigNebulaVersion(Config.ABOUT_APP_ID);
            HashMap hashMap = new HashMap();
            hashMap.put(Config.ABOUT_APP_ID, walletConfigNebulaVersion);
            AppReq appReq = new AppReq();
            appReq.reqmode = "syncforce";
            final Builder updateCallback = H5UpdateAppParam.newBuilder().setAppMap(hashMap).setForceRpc(true).setAppReq(appReq).setDownLoadAmr(true).setStartTime(System.currentTimeMillis()).setUpdateCallback(new H5UpdateAppCallback() {
                public final void onResult(boolean z, boolean z2) {
                    H5Log.d(MiniAppUtil.TAG, "amap update appx result: ".concat(String.valueOf(z)));
                    if (z) {
                        MiniAppUtil.amapAppxUpdateSuccess = true;
                    }
                    MiniAppUtil.isDoingAppxUpdate = false;
                }
            });
            new Handler().postDelayed(new Runnable() {
                public final void run() {
                    h5AppProvider.updateApp(updateCallback.build());
                }
            }, 2000);
        }
    }

    public static void refreshAlipayLoggingInfo() {
        LoggerFactory.getLogContext().setUserId(MiniAppDataUtil.getAlipayId());
    }

    public static void finishAllApps() {
        if (MiniAppInitHelper.getInstance().isMiniAppInited()) {
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().finishAllApps();
        }
    }

    public static void reportMiniAppLog(boolean z) {
        if (isStartedMiniApp) {
            LoggerFactory.getLogContext().notifyClientEvent(z ? LogContext.ENVENT_GOTOBACKGROUND : LogContext.ENVENT_GOTOFOREGROUND, null);
        }
    }

    public static boolean isSupportScheme(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str.toLowerCase());
        String scheme = parse.getScheme();
        String host = parse.getHost();
        List<String> pathSegments = parse.getPathSegments();
        boolean equals = "amapuri".equals(scheme);
        boolean equals2 = "applets".equals(host);
        boolean z = pathSegments != null && 2 == pathSegments.size() && "platformapi".equals(pathSegments.get(0)) && "startapp".equals(pathSegments.get(1));
        boolean z2 = !TextUtils.isEmpty(parse.getQueryParameter("appid"));
        if (!equals || !equals2 || !z || !z2) {
            return false;
        }
        return true;
    }

    public static boolean isMiniAppEnable() {
        if (VERSION.SDK_INT <= 18) {
            return false;
        }
        boolean z = true;
        String a = lo.a().a((String) MINIAPP_CLOUD_CONTROL_KEY);
        if (!TextUtils.isEmpty(a)) {
            try {
                if (new JSONObject(a).getInt(MINIAPP_ENABLE_KEY) == 0) {
                    z = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    public static boolean isEnableById(String str) {
        String a = lo.a().a((String) MINIAPP_CLOUD_CONTROL_KEY);
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(a).getJSONObject(str);
            if (jSONObject == null || jSONObject.getInt("enable") != 1) {
                return false;
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNeedLoginById(String str) {
        String a = lo.a().a((String) MINIAPP_CLOUD_CONTROL_KEY);
        if (TextUtils.isEmpty(a)) {
            return true;
        }
        try {
            JSONObject jSONObject = new JSONObject(a).getJSONObject(str);
            if (jSONObject == null || jSONObject.getInt("needLogin") != 0) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getMiniAppCloudConfig() {
        return lo.a().a((String) MINIAPP_CLOUD_CONTROL_KEY);
    }

    public static void startNebulaDebug() {
        MiniAppInitHelper.getInstance().initMiniApp();
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(AMapAppGlobal.getApplication());
        Intent intent = new Intent(H5Param.H5_BUG_ME_STARTUP);
        intent.putExtra("h5devType", "h5devH5App");
        instance.sendBroadcast(intent);
    }

    static String getMiniAppLogo(String str) {
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            AppInfo appInfo = h5AppProvider.getAppInfo(str);
            if (appInfo != null) {
                return appInfo.icon_url;
            }
        }
        return null;
    }

    public static void onH5ActivityCreated(Activity activity) {
        h5Activities.add(activity);
    }

    public static void onH5ActivityDestroyed(Activity activity) {
        h5Activities.remove(activity);
    }

    private static void finishH5Activity() {
        for (Activity finish : h5Activities) {
            finish.finish();
        }
        h5Activities.clear();
    }

    public static void openH5Page(Bundle bundle) {
        MiniAppInitHelper.getInstance().initMiniApp();
        H5Bundle h5Bundle = new H5Bundle();
        h5Bundle.setParams(bundle);
        ((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName())).startPage(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication(), h5Bundle);
    }
}
