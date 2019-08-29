package com.alipay.mobile.nebula.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public class H5KeepAliveUtil {
    public static final long DEFAULT_KEEP_ALIVE_MS = 300000;
    public static final String TAG = "H5KeepAliveUtil";
    public static boolean hasStartActivity = false;
    @SuppressLint({"StaticFieldLeak"})
    private static KeepAliveInfo sCurrentKeepAliveInfo;

    public static class DestroyRunnable implements Runnable {
        private boolean invalid = false;
        private H5Session session;

        public DestroyRunnable(H5Session session2) {
            this.session = session2;
        }

        public void setInvalid() {
            H5Log.d(H5KeepAliveUtil.TAG, "DestroyRunnable set invalid");
            this.invalid = true;
        }

        public void run() {
            try {
                H5Log.d(H5KeepAliveUtil.TAG, "DestroyRunnable run for session: " + this.session.getId() + " invalid: " + this.invalid + " session exit: " + this.session.isExited());
                if (!this.invalid && !this.session.isExited()) {
                    this.session.exitSession();
                    H5KeepAliveUtil.removeRunningTinyActivity();
                }
            } catch (Throwable th) {
                H5Log.e((String) H5KeepAliveUtil.TAG, (String) "remove delay error");
            }
        }
    }

    public static class KeepAliveInfo {
        String appId;
        long keepAliveMillis = 0;
        Activity runningActivity;

        public KeepAliveInfo(String appId2, Activity runningActivity2) {
            this.appId = appId2;
            this.runningActivity = runningActivity2;
        }
    }

    @Nullable
    private static ComponentName getTopTaskBaseActivity() {
        try {
            return ((ActivityManager) H5Utils.getContext().getSystemService(WidgetType.ACTIVITY)).getRunningTasks(1).get(0).baseActivity;
        } catch (Throwable th) {
            return null;
        }
    }

    public static boolean enableMainProcKeepAlive(String appId) {
        boolean z;
        if ("NO".equalsIgnoreCase(H5WalletWrapper.getConfigWithProcessCache("h5_mainProcKeepAlive"))) {
            return false;
        }
        if (sCurrentKeepAliveInfo == null || System.currentTimeMillis() - sCurrentKeepAliveInfo.keepAliveMillis >= 5000) {
            ComponentName componentName = getTopTaskBaseActivity();
            if (componentName == null) {
                return true;
            }
            String className = componentName.getClassName();
            if (className.contains("H5MainProcTinyActivity") || className.contains("H5MainProcTinyTransActivity")) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                return true;
            }
            return false;
        }
        H5Log.d(TAG, "too frequent not use keep alive activity");
        return false;
    }

    public static boolean updateFromActivityInMain() {
        boolean fromForeground = false;
        ComponentName topComponent = getTopTaskBaseActivity();
        if (topComponent != null) {
            try {
                Class.forName("com.alipay.mobile.liteprocess.LiteProcessActivity").getField("fromBaseActivity").set(null, topComponent.getClassName());
                if (topComponent.getPackageName().equals(H5Utils.getContext().getPackageName())) {
                    fromForeground = true;
                }
                H5Log.d(TAG, "updateFromActivityInMain fromForeground: " + fromForeground + Token.SEPARATOR + topComponent);
            } catch (Throwable t) {
                H5Log.e(TAG, "updateFromActivityInMain error", t);
            }
        }
        return fromForeground;
    }

    public static boolean handleRestartInMain(String appId, Bundle param) {
        if (sCurrentKeepAliveInfo == null || !TextUtils.equals(sCurrentKeepAliveInfo.appId, appId)) {
            return false;
        }
        Activity activity = sCurrentKeepAliveInfo.runningActivity;
        if (activity == null) {
            return false;
        }
        H5Log.d(TAG, "handleRestartInMain " + appId + Token.SEPARATOR + activity);
        boolean fromForeground = updateFromActivityInMain();
        H5EventHandler h5EventHandlerService = (H5EventHandler) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService == null || h5EventHandlerService.moveTaskToFront(activity, fromForeground, param)) {
            return true;
        }
        return false;
    }

    @Nullable
    public static Activity getRunningActivity() {
        if (sCurrentKeepAliveInfo == null || sCurrentKeepAliveInfo.runningActivity == null) {
            return null;
        }
        H5Log.d(TAG, "getRunningActivity " + sCurrentKeepAliveInfo.runningActivity);
        return sCurrentKeepAliveInfo.runningActivity;
    }

    public static void putRunningTinyActivity(String appId, Activity activity) {
        H5Log.d(TAG, "putRunningTinyActivity: " + appId + Token.SEPARATOR + activity);
        if (!TextUtils.isEmpty(appId)) {
            sCurrentKeepAliveInfo = new KeepAliveInfo(appId, activity);
        }
    }

    public static void removeRunningTinyActivity() {
        H5Log.d(TAG, "removeRunningTinyActivity: " + sCurrentKeepAliveInfo);
        if (sCurrentKeepAliveInfo != null) {
            sCurrentKeepAliveInfo = null;
        }
    }

    public static boolean needRelaunchInNebula(String appId, @Nullable Intent intent, String lastChInfo, MicroApplication microApplication) {
        if (intent == null || "NO".equals(H5WalletWrapper.getConfigWithProcessCache("h5_shouldUseNewRelanch")) || intent.getBooleanExtra("IS_LITE_MOVE_TASK", false)) {
            return false;
        }
        String homePage = H5StartParamManager.getInstance().getHomePage(appId);
        String targetPage = intent.getStringExtra("page");
        String chInfo = getChInfo(intent, microApplication);
        H5Log.d(TAG, "needRelaunchInNebula targetPage: " + targetPage + " homePage: " + homePage + " chInfo: " + chInfo + " lastChInfo: " + lastChInfo);
        if (TextUtils.isEmpty(homePage) || !TextUtils.isEmpty(targetPage)) {
            return false;
        }
        if (TextUtils.isEmpty(lastChInfo) || !lastChInfo.equals(chInfo)) {
            return true;
        }
        return false;
    }

    @Nullable
    public static String getChInfo(Intent intent, MicroApplication application) {
        if (intent == null) {
            return null;
        }
        String chInfoRaw = intent.getStringExtra("chInfo");
        if (TextUtils.isEmpty(chInfoRaw) && application != null) {
            chInfoRaw = H5Utils.getString(application.getSceneParams(), (String) "chInfo");
        }
        if (TextUtils.isEmpty(chInfoRaw)) {
            return null;
        }
        int index = chInfoRaw.indexOf("__");
        if (index > 0) {
            return chInfoRaw.substring(0, index);
        }
        return chInfoRaw;
    }

    public static boolean enableKeepAlive(Bundle bundle, String appId) {
        if (!"YES".equalsIgnoreCase(H5Utils.getString(bundle, (String) "enableKeepAlive")) || !configOpen(bundle) || !openAllKeepAlive(appId)) {
            return false;
        }
        return true;
    }

    private static boolean configOpen(Bundle bundle) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return true;
        }
        String value = h5ConfigProvider.getConfig("h5_NotKeepAliveList");
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        JSONArray jsonArray = H5Utils.parseArray(value);
        if (jsonArray == null || jsonArray.isEmpty()) {
            return true;
        }
        String sceneId = H5Utils.getString(bundle, (String) MicroApplication.KEY_APP_SCENE_ID);
        if (TextUtils.isEmpty(sceneId) || !jsonArray.contains(sceneId)) {
            return true;
        }
        H5Log.d(TAG, "sceneId " + sceneId + "setEnableKeepAlive");
        return false;
    }

    private static boolean openAllKeepAlive(String appId) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null) {
            return false;
        }
        String appIdList = h5ConfigProvider.getConfig("h5_keepAliveBlackList");
        if (!TextUtils.isEmpty(appIdList)) {
            JSONArray jsonArray = H5Utils.parseArray(appIdList);
            if (!TextUtils.isEmpty(appId) && jsonArray != null && jsonArray.contains(appId)) {
                H5Log.d(TAG, " not keepAlive appId is in BlackList " + appId);
                return false;
            }
        }
        JSONObject jsonObject = H5Utils.parseObject(h5ConfigProvider.getConfig("h5_appKeepAliveConfig"));
        if (jsonObject == null || jsonObject.isEmpty() || !"yes".equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "shouldKeepAlive"))) {
            return false;
        }
        return true;
    }

    public static void updateKeepAliveTime() {
        if (sCurrentKeepAliveInfo != null) {
            sCurrentKeepAliveInfo.keepAliveMillis = System.currentTimeMillis();
        }
    }
}
