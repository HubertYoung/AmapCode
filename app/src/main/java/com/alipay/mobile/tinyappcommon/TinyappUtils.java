package com.alipay.mobile.tinyappcommon;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;
import com.alipay.mobile.tinyappcommon.mode.TinyAppEnvMode;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.tinyappcommon.utils.TinyAppNumberUtils;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptor;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Stack;

public class TinyappUtils {
    private static String[] SUPPORTED_IMAGE_SUFFIX = {"png", "jpg", "jpeg", "webp", "bmp", "gif", "tif"};
    private static final String TAG = "TinyappUtils";

    public enum NetworkType {
        NETWORK_NONE,
        NETWORK_WIFI,
        NETWORK_MOBILE
    }

    public static String getAppId(H5Event event) {
        return H5Utils.getString(((H5Page) event.getTarget()).getParams(), (String) "appId");
    }

    public static String getAppId(Bundle startupParams) {
        if (TinyAppMiniServicePlugin.appIsMiniService(startupParams)) {
            return H5Utils.getString(startupParams, (String) "parentAppId");
        }
        String currentAppId = H5Utils.getString(startupParams, (String) "MINI-PROGRAM-WEB-VIEW-TAG");
        if (TextUtils.isEmpty(currentAppId)) {
            return H5Utils.getString(startupParams, (String) "appId");
        }
        return currentAppId;
    }

    public static String getUserId() {
        String userId = TinyAppService.get().getUserId();
        if (!TextUtils.isEmpty(userId)) {
            return userId;
        }
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            return h5LoginProvider.getUserId();
        }
        return userId;
    }

    public static String getAppName(String appId, H5Page h5Page) {
        AppInfo appInfo;
        AppInfo appInfo2;
        String nbsv = "";
        if (h5Page != null) {
            try {
                nbsv = H5Utils.getString(h5Page.getParams(), (String) H5PreferAppList.nbsv);
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
                return "";
            }
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            return "";
        }
        TinyAppEnvMode mode = TinyAppEnvMode.valueOf(h5Page);
        if (mode != TinyAppEnvMode.RELEASE) {
            if (mode != null && !TextUtils.isEmpty(nbsv)) {
                appInfo2 = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), nbsv);
            } else if (!TextUtils.isEmpty(nbsv)) {
                appInfo2 = h5AppProvider.getAppInfo(appId, nbsv);
            } else {
                appInfo2 = h5AppProvider.getAppInfo(appId);
            }
            if (appInfo2 != null) {
                return appInfo2.name;
            }
            return "";
        }
        String appName = h5AppProvider.getAppName(appId);
        if (!TextUtils.isEmpty(appName)) {
            return appName;
        }
        if (mode != null && !TextUtils.isEmpty(nbsv)) {
            appInfo = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), nbsv);
        } else if (!TextUtils.isEmpty(nbsv)) {
            appInfo = h5AppProvider.getAppInfo(appId, nbsv);
        } else {
            appInfo = h5AppProvider.getAppInfo(appId);
        }
        if (appInfo != null) {
            return appInfo.name;
        }
        return appName;
    }

    public static String getAppName(String appId, String version, Bundle params) {
        AppInfo appInfo;
        AppInfo appInfo2;
        try {
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider == null) {
                return "";
            }
            TinyAppEnvMode mode = TinyAppEnvMode.valueOfPage(params);
            if (mode != TinyAppEnvMode.RELEASE) {
                if (mode != null && !TextUtils.isEmpty(version)) {
                    appInfo2 = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), version);
                } else if (!TextUtils.isEmpty(version)) {
                    appInfo2 = h5AppProvider.getAppInfo(appId, version);
                } else {
                    appInfo2 = h5AppProvider.getAppInfo(appId);
                }
                if (appInfo2 != null) {
                    return appInfo2.name;
                }
                return "";
            }
            String appName = h5AppProvider.getAppName(appId, version);
            if (!TextUtils.isEmpty(appName)) {
                return appName;
            }
            if (mode != null && !TextUtils.isEmpty(version)) {
                appInfo = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), version);
            } else if (!TextUtils.isEmpty(version)) {
                appInfo = h5AppProvider.getAppInfo(appId, version);
            } else {
                appInfo = h5AppProvider.getAppInfo(appId);
            }
            if (appInfo != null) {
                return appInfo.name;
            }
            return appName;
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return "";
        }
    }

    public static String getAppDesc(String appId, H5Page h5Page) {
        AppInfo appInfo;
        AppInfo appInfo2;
        String nbsv = "";
        if (h5Page != null) {
            try {
                nbsv = H5Utils.getString(h5Page.getParams(), (String) H5PreferAppList.nbsv);
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
                return "";
            }
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            return "";
        }
        TinyAppEnvMode mode = TinyAppEnvMode.valueOf(h5Page);
        if (mode != TinyAppEnvMode.RELEASE) {
            if (mode != null && !TextUtils.isEmpty(nbsv)) {
                appInfo2 = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), nbsv);
            } else if (!TextUtils.isEmpty(nbsv)) {
                appInfo2 = h5AppProvider.getAppInfo(appId, nbsv);
            } else {
                appInfo2 = h5AppProvider.getAppInfo(appId);
            }
            if (appInfo2 != null) {
                return appInfo2.app_dsec;
            }
            return "";
        }
        String appDesc = h5AppProvider.getAppDesc(appId);
        if (!TextUtils.isEmpty(appDesc)) {
            return appDesc;
        }
        if (mode != null && !TextUtils.isEmpty(nbsv)) {
            appInfo = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), nbsv);
        } else if (!TextUtils.isEmpty(nbsv)) {
            appInfo = h5AppProvider.getAppInfo(appId, nbsv);
        } else {
            appInfo = h5AppProvider.getAppInfo(appId);
        }
        if (appInfo != null) {
            return appInfo.app_dsec;
        }
        return appDesc;
    }

    public static String getAppIcon(String appId, H5Page h5Page) {
        AppInfo appInfo;
        AppInfo appInfo2;
        String nbsv = "";
        if (h5Page != null) {
            try {
                nbsv = H5Utils.getString(h5Page.getParams(), (String) H5PreferAppList.nbsv);
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
                return "";
            }
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            return "";
        }
        TinyAppEnvMode mode = TinyAppEnvMode.valueOf(h5Page);
        if (mode != TinyAppEnvMode.RELEASE) {
            if (mode != null && !TextUtils.isEmpty(nbsv)) {
                appInfo2 = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), nbsv);
            } else if (!TextUtils.isEmpty(nbsv)) {
                appInfo2 = h5AppProvider.getAppInfo(appId, nbsv);
            } else {
                appInfo2 = h5AppProvider.getAppInfo(appId);
            }
            if (appInfo2 != null) {
                return appInfo2.icon_url;
            }
            return "";
        }
        String appIcon = h5AppProvider.getIconUrl(appId);
        if (!TextUtils.isEmpty(appIcon)) {
            return appIcon;
        }
        if (mode != null && !TextUtils.isEmpty(nbsv)) {
            appInfo = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), nbsv);
        } else if (!TextUtils.isEmpty(nbsv)) {
            appInfo = h5AppProvider.getAppInfo(appId, nbsv);
        } else {
            appInfo = h5AppProvider.getAppInfo(appId);
        }
        if (appInfo != null) {
            return appInfo.icon_url;
        }
        return appIcon;
    }

    public static String getAppIcon(String appId, String version, Bundle params) {
        AppInfo appInfo;
        AppInfo appInfo2;
        try {
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider == null) {
                return "";
            }
            TinyAppEnvMode mode = TinyAppEnvMode.valueOfPage(params);
            if (mode != TinyAppEnvMode.RELEASE) {
                if (mode != null && !TextUtils.isEmpty(version)) {
                    appInfo2 = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), version);
                } else if (!TextUtils.isEmpty(version)) {
                    appInfo2 = h5AppProvider.getAppInfo(appId, version);
                } else {
                    appInfo2 = h5AppProvider.getAppInfo(appId);
                }
                if (appInfo2 != null) {
                    return appInfo2.icon_url;
                }
                return "";
            }
            String appIcon = h5AppProvider.getIconUrl(appId, version);
            if (!TextUtils.isEmpty(appIcon)) {
                return appIcon;
            }
            if (mode != null && !TextUtils.isEmpty(version)) {
                appInfo = h5AppProvider.getAppInfo(appId, mode.toStringOfNebula(), version);
            } else if (!TextUtils.isEmpty(version)) {
                appInfo = h5AppProvider.getAppInfo(appId, version);
            } else {
                appInfo = h5AppProvider.getAppInfo(appId);
            }
            if (appInfo != null) {
                return appInfo.icon_url;
            }
            return appIcon;
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return "";
        }
    }

    public static String encodeToLocalId(String path) {
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        return h5IpcServer.encodeToLocalId(path);
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) TAG, throwable);
                }
            }
        } else {
            APMToolService apmToolService = (APMToolService) H5Utils.findServiceByInterface(APMToolService.class.getName());
            if (apmToolService != null) {
                String localId = apmToolService.encodeToLocalId(path);
                H5Log.d(TAG, "localId :" + localId + " path:" + path);
                return localId;
            }
            H5Log.e((String) TAG, (String) "apmToolService ==null ");
        }
        return null;
    }

    public static String transferApPathToLocalPath(String apPath) {
        if (TextUtils.isEmpty(apPath)) {
            return null;
        }
        String localPath = getLocalPathFromId(apPath);
        if (TextUtils.isEmpty(localPath) || !localPath.startsWith("file://")) {
            return localPath;
        }
        return localPath.replaceAll("file://", "");
    }

    public static String getLocalPathFromId(String filePath) {
        String id;
        if (filePath.endsWith("image")) {
            id = matchLocalId(filePath, "image");
        } else if (filePath.endsWith("video")) {
            id = matchLocalId(filePath, "video");
        } else if (filePath.endsWith("audio")) {
            id = matchLocalId(filePath, "audio");
        } else {
            id = matchLocalId(filePath, H5ResourceHandlerUtil.OTHER);
        }
        if (!TextUtils.isEmpty(id)) {
            filePath = decodeToPath(id);
        }
        H5Log.d(TAG, "id:" + id + " filePath:" + filePath);
        return filePath;
    }

    public static String matchLocalId(String url, String type) {
        if (url != null && url.startsWith("https://resource/") && url.endsWith(type)) {
            try {
                Uri uri = H5UrlHelper.parseUrl(url);
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    String[] details = uri.getPath().replace("/", "").split("\\.");
                    if (details.length > 1) {
                        String localId = details[0];
                        if (!TextUtils.isEmpty(localId)) {
                            return localId;
                        }
                    }
                }
            } catch (Exception e) {
                H5Log.e((String) TAG, url + " ##### match local id exception: " + e.getMessage());
            }
        }
        return null;
    }

    public static String decodeToPath(String localId) {
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                try {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        return h5IpcServer.decodeToPath(localId);
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) TAG, throwable);
                }
            }
        } else {
            APMToolService apmToolService = (APMToolService) H5Utils.findServiceByInterface(APMToolService.class.getName());
            if (apmToolService != null) {
                String path = apmToolService.decodeToPath(localId);
                H5Log.d(TAG, "localId :" + localId + " path:" + path);
                return path;
            }
            H5Log.e((String) TAG, (String) "apmToolService ==null ");
        }
        return null;
    }

    public static String decodePathInTinyProcess(String localId) {
        APMToolService apmToolService = (APMToolService) H5Utils.findServiceByInterface(APMToolService.class.getName());
        if (apmToolService != null) {
            String path = apmToolService.decodeToPath(localId);
            H5Log.d(TAG, "localId :" + localId + " path:" + path);
            return path;
        }
        H5Log.e((String) TAG, (String) "apmToolService ==null ");
        return null;
    }

    public static boolean checkSuffixSupported(String url) {
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        H5Log.d(TAG, "checkSuffixSupported...suffix=" + suffix);
        if (TextUtils.isEmpty(suffix)) {
            H5Log.d(TAG, "checkSuffixSupported... suffix is null");
            return false;
        }
        boolean supportedSuffix = false;
        String lowerCase = suffix.toLowerCase();
        String[] strArr = SUPPORTED_IMAGE_SUFFIX;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (strArr[i].equals(lowerCase)) {
                supportedSuffix = true;
                break;
            } else {
                i++;
            }
        }
        if (supportedSuffix) {
            return true;
        }
        H5Log.d(TAG, "checkSuffixSupported...unsupported suffix = " + suffix);
        return false;
    }

    public static byte[] toByteArray(InputStream input, boolean forceReset) {
        if (forceReset) {
            input.reset();
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        while (true) {
            int n = input.read(buffer);
            if (-1 == n) {
                return output.toByteArray();
            }
            output.write(buffer, 0, n);
        }
    }

    public static byte[] toByteArray(InputStream input) {
        return toByteArray(input, true);
    }

    public static String getTypeFromMimeType(String contentType) {
        if (contentType.contains("image")) {
            return "image";
        }
        if (contentType.contains("video")) {
            return "video";
        }
        if (contentType.contains("audio")) {
            return "audio";
        }
        return H5ResourceHandlerUtil.OTHER;
    }

    public static boolean isDebugVersion(H5Page h5Page) {
        return "DEBUG".equals(getScene(h5Page));
    }

    public static boolean isOnlineVersion(H5Page h5Page) {
        String scene = getScene(h5Page);
        return TextUtils.isEmpty(scene) || "ONLINE".equals(scene);
    }

    public static String getScene(H5Page h5Page) {
        if (h5Page == null) {
            return null;
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            return null;
        }
        Bundle bundle = h5Page.getParams();
        return h5AppProvider.getScene(H5Utils.getString(bundle, (String) "appId"), H5Utils.getString(bundle, (String) "appVersion"));
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 55;
    }

    public static float getDensity(Context context) {
        try {
            return context.getResources().getDisplayMetrics().density;
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getDensity...e=" + e);
            return 2.75f;
        }
    }

    public static MicroApplicationContext getMicroApplicationContext() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext();
    }

    public static int versionCompare(String currentVersion, String targetVersion) {
        long v1;
        long v2;
        if (TextUtils.isEmpty(currentVersion) || TextUtils.isEmpty(targetVersion)) {
            return -1;
        }
        String[] s1 = currentVersion.split("\\.");
        String[] s2 = targetVersion.split("\\.");
        int length = Math.max(s1.length, s2.length);
        for (int i = 0; i < length; i++) {
            if (i < s1.length) {
                v1 = TinyAppNumberUtils.parseLong(s1[i]);
            } else {
                v1 = 0;
            }
            if (i < s2.length) {
                v2 = TinyAppNumberUtils.parseLong(s2[i]);
            } else {
                v2 = 0;
            }
            if (v1 < v2) {
                return -1;
            }
            if (v1 > v2) {
                return 1;
            }
        }
        return 0;
    }

    public static H5Page getTopH5Page() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            return null;
        }
        try {
            Stack sessions = h5Service.getSessions();
            if (sessions == null) {
                return null;
            }
            synchronized (sessions) {
                for (int index = sessions.size() - 1; index >= 0; index--) {
                    H5Session session = (H5Session) sessions.get(index);
                    if (session != null) {
                        String id = session.getId();
                        H5Log.d(TAG, "sessionId:" + id);
                        if (!isDevSession(id)) {
                            H5Page h5Page = session.getTopPage();
                            if (h5Page == null) {
                                continue;
                            } else {
                                if (!(!TextUtils.isEmpty(H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG")))) {
                                    return h5Page;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
        return null;
    }

    private static boolean isDevSession(String id) {
        return !TextUtils.isEmpty(id) && id.contains(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID);
    }

    public static boolean isRemoteDebugConnected(String appId) {
        if (!TinyAppRemoteDebugInterceptorManager.get().isRemoteDebug()) {
            return false;
        }
        TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
        if (interceptor != null) {
            return interceptor.isRemoteDebugConnected(appId);
        }
        return false;
    }

    public static boolean isVConsolePanelOpened() {
        if (TinyAppStorage.getInstance().getDebugPanelH5Page() != null) {
            return true;
        }
        return false;
    }

    public static void sendMsgToRemoteWorker(String msg) {
        TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
        if (interceptor != null) {
            interceptor.sendMessageToRemoteWorker(msg);
        }
    }

    public static void sendMsgToRemoteWorkerOrVConsole(String appId, String type, String msg) {
        if (isRemoteDebugConnected(appId)) {
            sendMsgToRemoteWorker(type + ":" + msg);
            return;
        }
        H5Page debugH5Page = TinyAppStorage.getInstance().getDebugPanelH5Page();
        if (debugH5Page != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "type", (Object) type);
            jsonObject.put((String) "content", (Object) msg);
            JSONObject consoleData = new JSONObject();
            consoleData.put((String) "data", (Object) jsonObject);
            debugH5Page.getBridge().sendToWeb(H5VConsolePlugin.ON_TINY_DEBUG_CONSOLE, consoleData, null);
        }
    }

    public static NetworkType getNetworkType(Context context) {
        if (context == null) {
            return NetworkType.NETWORK_NONE;
        }
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connManager == null) {
            return NetworkType.NETWORK_NONE;
        }
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NetworkType.NETWORK_NONE;
        }
        NetworkInfo wifiInfo = connManager.getNetworkInfo(1);
        if (wifiInfo != null) {
            State state = wifiInfo.getState();
            if (state != null && (state == State.CONNECTED || state == State.CONNECTING)) {
                return NetworkType.NETWORK_WIFI;
            }
        }
        return NetworkType.NETWORK_MOBILE;
    }
}
