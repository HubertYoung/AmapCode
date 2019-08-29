package com.alipay.mobile.nebula.appcenter.apphandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandlerUtil.FallbackResult;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingFrameworkImpl;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingManager;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingTypeListen;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingUtil;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingViewImpl;
import com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5NebulaAppCacheManager;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.appcenter.util.H5LoadingApp;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppClientUpgradeProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class H5AppHandler {
    private static final String APPX_UPDATE_FAILED_SPM_ID = "a192.b7351.c17706.d31775";
    private static final String APPX_UPDATE_SUCCESS_SPM_ID = "a192.b7351.c17706.d31776";
    public static final String CHECK_KEY = "nebulaStartflag";
    public static final String CHECK_VALUE = "yes";
    private static final int DEBUG_ERROR_CODE = 50002;
    private static final String HAS_CHECKED_MIN_APPX_VERSION = "hasCheckedMinAppxVersion";
    private static final int NORMAL_EROOR_CODE = 1001;
    public static final String PERF_IS_LOCAL_KEY = "is_local";
    public static final String PERF_OPENAPP_TIME_KEY = "perf_open_app_time";
    private static final String PERF_RPC_TIME_KEY = "perf_rpc_time";
    /* access modifiers changed from: private */
    public static String TAG = "H5AppHandler";
    private static final int TIME = 1000;
    private static final String TINY_APP_BIZ_TYPE = "TINYAPP";
    private static final String UPGRADE_CLIENT_SPM_ID = "a192.b7351.c17706.d31777";
    private static final String appInfoEmptyUrl = "https://render.alipay.com/p/s/tinyapperror/?appId=%s&errorCode=%d";
    public static H5EventHandlerService h5EventHandlerService = null;
    private static boolean isFirstStartApp = true;
    private static final Set<String> mergeParamKeys = new HashSet<String>() {
        {
            add("enableKeepAlive");
            add(H5Param.ENABLE_DSL);
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableTransparentInOfflinePkg"))) {
                add(H5Param.LONG_TRANSPARENT);
            }
        }
    };
    /* access modifiers changed from: private */
    public static final H5AppPrepareData prepareData = new H5AppPrepareData();
    public static final String sAppIcon = "appIcon";
    public static final String sAppName = "appName";
    public static final String sAppSlogan = "appSlogan";
    /* access modifiers changed from: private */
    public static int waitCount = 0;
    /* access modifiers changed from: private */
    public static int waitMax = 4;

    public static boolean hasCheckParam(Bundle param) {
        return param != null && TextUtils.equals(param.getString(CHECK_KEY), "yes");
    }

    /* access modifiers changed from: private */
    public static void paramParse(Bundle bundle) {
        H5ParamParser.parse(bundle, H5Param.LONG_NB_UPDATE, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_OFFLINE, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_URL, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_VERSION, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_OFFMODE, false);
    }

    private static Bundle setStartParam(String appName, String appIconUrl, String slogan, Bundle param) {
        if (((H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName())) == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(sAppIcon, appIconUrl);
        bundle.putString("appName", appName);
        bundle.putString(sAppSlogan, slogan);
        H5EventHandlerService h5EventHandlerService2 = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService2 == null) {
            return bundle;
        }
        int lpid = param.getInt(h5EventHandlerService2.getMultiProcessTag());
        H5Log.d(TAG, "lpid " + lpid);
        if (lpid == 0) {
            return bundle;
        }
        bundle.putInt(h5EventHandlerService2.getMultiProcessTag(), lpid);
        return bundle;
    }

    private static boolean isTinyApp(H5AppProvider h5AppProvider, String appId, String appVersion, AppInfo appInfo) {
        if (appInfo == null) {
            if (h5AppProvider == null) {
                return true;
            }
            Map map = h5AppProvider.getExtra(appId, appVersion);
            if (map != null) {
                return enableDsl(map.get(H5Param.LAUNCHER_PARAM));
            }
            return true;
        } else if (appInfo.app_channel == 4) {
            return true;
        } else {
            Map extendInfo = appInfo.extend_info;
            if (extendInfo == null || extendInfo.isEmpty()) {
                return false;
            }
            return enableDsl(extendInfo.get(H5Param.LAUNCHER_PARAM));
        }
    }

    private static boolean enableDsl(String launchParam) {
        boolean z = false;
        if (TextUtils.isEmpty(launchParam)) {
            return z;
        }
        try {
            return "yes".equalsIgnoreCase(JSON.parseObject(launchParam).getString(H5Param.ENABLE_DSL));
        } catch (Throwable e) {
            H5Log.e(TAG, "isTinyApp...e=" + e);
            return z;
        }
    }

    private static boolean isAllowedUseTinyAppManagerProcess() {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            return false;
        }
        return h5TinyAppService.isUseTinyAppManagerProcess();
    }

    public static void syncApp(H5StartAppInfo startAppInfo) {
        prepareData.clear();
        prepareData.setBeginTime(System.currentTimeMillis());
        if (h5EventHandlerService == null) {
            h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            if (!H5AppUtil.isOffLine(startAppInfo.targetAppId) || H5InstallAppAdvice.enableUseDevMode(startAppInfo.params)) {
                waitCount = 0;
                Bundle copyParam = new Bundle();
                if (startAppInfo.params != null) {
                    copyParam = (Bundle) startAppInfo.params.clone();
                }
                if (!H5InstallAppAdvice.enableUseDevMode(copyParam)) {
                    H5DevAppList.getInstance().remove(startAppInfo.targetAppId);
                }
                long millis = System.currentTimeMillis();
                String currentNbVersion = h5AppProvider.getVersion(startAppInfo.targetAppId);
                long cost = System.currentTimeMillis() - millis;
                H5Log.d(TAG, startAppInfo.targetAppId + " getVersion " + currentNbVersion + " cost:" + cost);
                prepareData.setGetDBVersionTime(cost);
                AppInfo appInfo = h5AppProvider.getAppInfo(startAppInfo.targetAppId, currentNbVersion);
                if (appInfo != null) {
                    mergeAppStartParam(startAppInfo.params, appInfo);
                }
                if (!isTinyApp(h5AppProvider, startAppInfo.targetAppId, currentNbVersion, appInfo) || !isAllowedUseTinyAppManagerProcess()) {
                    doSyncApp(startAppInfo, appInfo, copyParam, currentNbVersion, h5AppProvider);
                } else {
                    H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
                    if (h5TinyAppProvider != null) {
                        H5NebulaAppCacheManager.setAppType(startAppInfo, appInfo, copyParam);
                        h5TinyAppProvider.startSyncApp(startAppInfo, appInfo, copyParam, currentNbVersion, h5AppProvider);
                    } else {
                        doSyncApp(startAppInfo, appInfo, copyParam, currentNbVersion, h5AppProvider);
                    }
                }
                MiniAppPrefetcher.prefetchApp(startAppInfo.targetAppId);
                return;
            }
            h5AppProvider.showOfflinePage(startAppInfo.targetAppId, startAppInfo.params);
        }
    }

    private static void mergeAppStartParam(Bundle bundle, AppInfo appInfo) {
        JSONObject launchParams = H5Utils.getJSONObject(H5Utils.parseObject(appInfo.extend_info_jo), H5Param.LAUNCHER_PARAM, null);
        if (launchParams != null) {
            for (String key : mergeParamKeys) {
                if (launchParams.containsKey(key)) {
                    bundle.putString(key, launchParams.getString(key));
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0196, code lost:
        if (com.alipay.mobile.nebula.appcenter.util.H5AppUtil.compareVersion(r25, r44.version) > 0) goto L_0x0198;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void doSyncApp(com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo r43, com.alipay.mobile.nebula.appcenter.model.AppInfo r44, android.os.Bundle r45, java.lang.String r46, com.alipay.mobile.nebula.provider.H5AppProvider r47) {
        /*
            r30 = 0
            if (r44 == 0) goto L_0x0006
            r30 = 1
        L_0x0006:
            r40 = 0
            java.lang.String r36 = ""
            java.lang.String r34 = ""
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r5 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r43
            java.lang.String r6 = r0.targetAppId
            boolean r5 = r5.contains(r6)
            if (r5 == 0) goto L_0x00ba
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r5 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r43
            java.lang.String r6 = r0.targetAppId
            r15 = 1
            r5.setUseDevMode(r6, r15)
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r5 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r43
            java.lang.String r6 = r0.targetAppId
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppInfo r28 = r5.getDevInfo(r6)
            r40 = 1
            r0 = r28
            java.lang.String r0 = r0.nbsv
            r36 = r0
            r0 = r28
            java.lang.String r0 = r0.nbsn
            r34 = r0
            java.lang.String r5 = "nbsn"
            r0 = r45
            java.lang.String r35 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            boolean r5 = android.text.TextUtils.isEmpty(r35)
            if (r5 != 0) goto L_0x0062
            r0 = r35
            r1 = r34
            boolean r5 = r0.equals(r1)
            if (r5 != 0) goto L_0x0062
            r34 = r35
            java.lang.String r5 = "nbsv"
            r0 = r45
            java.lang.String r36 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
        L_0x0062:
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r47
            r1 = r34
            r2 = r36
            com.alipay.mobile.nebula.appcenter.model.AppInfo r44 = r0.getAppInfo(r5, r1, r2)
            if (r44 == 0) goto L_0x00b7
            r30 = 1
        L_0x0074:
            java.lang.String r5 = TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r15 = "nbsn "
            r6.<init>(r15)
            r0 = r34
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " nbsv "
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r36
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " hasAppInfo:"
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r30
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)
        L_0x00a2:
            r0 = r45
            r1 = r44
            com.alipay.mobile.nebula.appcenter.util.H5AppUtil.mergeConmonStartParam(r0, r1)
            paramParse(r45)
            r0 = r43
            r1 = r45
            boolean r5 = checkDebugMode(r0, r1)
            if (r5 == 0) goto L_0x00c7
        L_0x00b6:
            return
        L_0x00b7:
            r30 = 0
            goto L_0x0074
        L_0x00ba:
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r5 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r43
            java.lang.String r6 = r0.targetAppId
            r15 = 0
            r5.setUseDevMode(r6, r15)
            goto L_0x00a2
        L_0x00c7:
            java.lang.String r5 = "nbversion"
            r0 = r45
            java.lang.String r42 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            java.lang.String r5 = "nboffline"
            r0 = r45
            java.lang.String r7 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            java.lang.String r5 = "nbupdate"
            r0 = r45
            java.lang.String r18 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            java.lang.String r5 = "nburl"
            r0 = r45
            java.lang.String r12 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            java.lang.String r5 = "nboffmode"
            r0 = r45
            java.lang.String r13 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            boolean r5 = com.alipay.mobile.nebula.util.H5Utils.isTinyApp(r44)
            if (r5 == 0) goto L_0x0124
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r45
            boolean r5 = com.alipay.mobile.nebula.util.H5KeepAliveUtil.enableKeepAlive(r0, r5)
            if (r5 == 0) goto L_0x010d
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r5 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r44
            boolean r5 = r5.needStopLiteProcessByAppId(r0)
            if (r5 == 0) goto L_0x0124
        L_0x010d:
            java.lang.Class<com.alipay.mobile.h5container.service.H5EventHandlerService> r5 = com.alipay.mobile.h5container.service.H5EventHandlerService.class
            java.lang.String r5 = r5.getName()
            java.lang.Object r29 = com.alipay.mobile.nebula.util.H5Utils.findServiceByInterface(r5)
            com.alipay.mobile.h5container.service.H5EventHandlerService r29 = (com.alipay.mobile.h5container.service.H5EventHandlerService) r29
            if (r29 == 0) goto L_0x0124
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r29
            r0.stopLiteProcessByAppIdInServer(r5)
        L_0x0124:
            boolean r5 = com.alipay.mobile.nebula.appcenter.apphandler.H5InstallAppAdvice.enableUseDevMode(r45)
            if (r5 == 0) goto L_0x015b
            if (r30 != 0) goto L_0x015b
            java.lang.String r5 = "nbsv"
            r0 = r45
            java.lang.String r5 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L_0x015b
            java.lang.String r5 = "https://render.alipay.com/p/s/tinyapperror/?appId=%s&errorCode=%d"
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r15 = 0
            r0 = r43
            java.lang.String r0 = r0.targetAppId
            r16 = r0
            r6[r15] = r16
            r15 = 1
            r16 = 50002(0xc352, float:7.0068E-41)
            java.lang.Integer r16 = java.lang.Integer.valueOf(r16)
            r6[r15] = r16
            java.lang.String r5 = java.lang.String.format(r5, r6)
            com.alipay.mobile.nebula.util.H5Utils.openUrl(r5)
            goto L_0x00b6
        L_0x015b:
            boolean r5 = com.alipay.mobile.nebula.util.H5Utils.isTinyApp(r44)
            if (r5 == 0) goto L_0x016a
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r5 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r44
            r5.addTinyAppRecord(r0)
        L_0x016a:
            com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList r5 = com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList.getInstance()
            r0 = r43
            java.lang.String r6 = r0.targetAppId
            boolean r5 = r5.contains(r6)
            if (r5 != 0) goto L_0x017a
            if (r30 != 0) goto L_0x017c
        L_0x017a:
            java.lang.String r18 = "syncforce"
        L_0x017c:
            java.lang.String r5 = "enbsv"
            r0 = r45
            java.lang.String r25 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)
            boolean r5 = android.text.TextUtils.isEmpty(r25)
            if (r5 != 0) goto L_0x019a
            if (r44 == 0) goto L_0x0198
            r0 = r44
            java.lang.String r5 = r0.version
            r0 = r25
            int r5 = com.alipay.mobile.nebula.appcenter.util.H5AppUtil.compareVersion(r0, r5)
            if (r5 <= 0) goto L_0x019a
        L_0x0198:
            java.lang.String r18 = "syncforce"
        L_0x019a:
            r32 = 0
            if (r30 == 0) goto L_0x01ca
            boolean r5 = com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager.isNeedForceUpdate(r44)
            if (r5 == 0) goto L_0x03f7
            r32 = 1
            java.lang.String r18 = "syncforce"
        L_0x01a8:
            java.lang.String r5 = TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r15 = "[syncApp] outOfReqRate: "
            r6.<init>(r15)
            r0 = r32
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = ", syncUpdate: "
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r18
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)
        L_0x01ca:
            if (r44 == 0) goto L_0x0407
            com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5NebulaAppCacheManager.setAppType(r43, r44, r45)
            r0 = r44
            java.lang.String r0 = r0.version
            r37 = r0
            java.lang.String r5 = TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r15 = "nebulaVersion "
            r6.<init>(r15)
            r0 = r37
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)
            r0 = r37
            r1 = r43
            r1.nebulaVersion = r0
        L_0x01f1:
            java.lang.String r5 = TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r15 = "targetAppId:"
            r6.<init>(r15)
            r0 = r43
            java.lang.String r15 = r0.targetAppId
            java.lang.StringBuilder r6 = r6.append(r15)
            java.lang.String r15 = " wantNebulaVersion:"
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r42
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " syncOffline:"
            java.lang.StringBuilder r6 = r6.append(r15)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r15 = " syncUpdate:"
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r18
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " currentNbVersion:"
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r46
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " nbUrl:"
            java.lang.StringBuilder r6 = r6.append(r15)
            java.lang.StringBuilder r6 = r6.append(r12)
            java.lang.String r15 = " nbOffMode: "
            java.lang.StringBuilder r6 = r6.append(r15)
            java.lang.StringBuilder r6 = r6.append(r13)
            java.lang.String r6 = r6.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r5 = prepareData
            r0 = r18
            r5.setRequestMode(r0)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r5 = prepareData
            r5.setOfflineMode(r7, r13)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r5 = prepareData
            r0 = r43
            java.lang.String r6 = r0.targetAppId
            r5.setAppId(r6)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r5 = prepareData
            r0 = r43
            java.lang.String r6 = r0.nebulaVersion
            r5.setVersion(r6)
            java.lang.String r5 = "synctry"
            r0 = r18
            boolean r5 = android.text.TextUtils.equals(r0, r5)
            if (r5 != 0) goto L_0x027d
            java.lang.String r5 = "syncforce"
            r0 = r18
            boolean r5 = android.text.TextUtils.equals(r0, r5)
            if (r5 == 0) goto L_0x0469
        L_0x027d:
            r0 = r46
            r1 = r42
            int r38 = com.alipay.mobile.nebula.appcenter.util.H5AppUtil.compareVersion(r0, r1)
            java.lang.String r5 = TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r15 = "currentNbVersion"
            r6.<init>(r15)
            r0 = r46
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " wantNebulaVersion:"
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r42
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " "
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r38
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)
            r5 = -1
            r0 = r38
            if (r0 == r5) goto L_0x02d0
            com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList r5 = com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList.getInstance()
            r0 = r43
            java.lang.String r6 = r0.targetAppId
            boolean r5 = r5.contains(r6)
            if (r5 != 0) goto L_0x02d0
            if (r30 == 0) goto L_0x02d0
            boolean r5 = android.text.TextUtils.isEmpty(r42)
            if (r5 != 0) goto L_0x02d0
            if (r32 == 0) goto L_0x0456
        L_0x02d0:
            r33 = r18
            java.lang.String r5 = "synctry"
            r0 = r18
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x02f8
            java.lang.String r5 = "sync"
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x02f8
            if (r44 == 0) goto L_0x02f8
            r0 = r44
            java.lang.String r5 = r0.app_id
            r0 = r44
            java.lang.String r6 = r0.version
            r0 = r47
            boolean r5 = r0.isAvailable(r5, r6)
            if (r5 != 0) goto L_0x0423
            java.lang.String r33 = "syncforce"
        L_0x02f8:
            com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingManager r9 = getLoadingImpl()
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r33
            r1 = r45
            r2 = r44
            int r5 = com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingUtil.getTimeout(r5, r0, r1, r2)
            r0 = r43
            r1 = r47
            r2 = r33
            showPackageLoadingPage(r0, r1, r9, r2, r5)
            java.util.HashMap r31 = new java.util.HashMap
            r31.<init>()
            com.alipay.mobile.nebula.appcenter.model.AppReq r23 = new com.alipay.mobile.nebula.appcenter.model.AppReq
            r23.<init>()
            java.lang.String r5 = "synctry"
            r0 = r18
            boolean r5 = android.text.TextUtils.equals(r0, r5)
            if (r5 == 0) goto L_0x0437
            java.lang.String r5 = "synctry"
            r0 = r23
            r0.reqmode = r5
        L_0x032d:
            boolean r5 = com.alipay.mobile.nebula.util.H5Utils.isTinyApp(r44)
            if (r5 != 0) goto L_0x033f
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            int r5 = r5.length()
            r6 = 15
            if (r5 <= r6) goto L_0x0345
        L_0x033f:
            java.lang.String r5 = "NO"
            r0 = r23
            r0.stableRpc = r5
        L_0x0345:
            java.lang.Class<com.alipay.mobile.nebula.provider.H5ConfigProvider> r5 = com.alipay.mobile.nebula.provider.H5ConfigProvider.class
            java.lang.String r5 = r5.getName()
            java.lang.Object r27 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r5)
            com.alipay.mobile.nebula.provider.H5ConfigProvider r27 = (com.alipay.mobile.nebula.provider.H5ConfigProvider) r27
            if (r27 == 0) goto L_0x036b
            java.lang.String r5 = "H5_loading_use_stableRpc"
            r0 = r27
            java.lang.String r41 = r0.getConfig(r5)
            java.lang.String r5 = "yes"
            r0 = r41
            boolean r5 = r5.equalsIgnoreCase(r0)
            if (r5 != 0) goto L_0x036b
            java.lang.String r5 = "NO"
            r0 = r23
            r0.stableRpc = r5
        L_0x036b:
            if (r40 == 0) goto L_0x043f
            java.lang.String r5 = TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r15 = "useDev "
            r6.<init>(r15)
            r0 = r36
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r15 = " nbsn:"
            java.lang.StringBuilder r6 = r6.append(r15)
            r0 = r34
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r31
            r1 = r36
            r0.put(r5, r1)
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r23
            setDevReq(r5, r0)
        L_0x03a3:
            r11 = r18
            r8 = r36
            r10 = r30
            r14 = r45
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r5 = prepareData
            long r16 = java.lang.System.currentTimeMillis()
            r0 = r16
            r5.setRequestBeginTime(r0)
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r5 = com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam.newBuilder()
            r0 = r31
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r5 = r5.setAppMap(r0)
            r6 = 1
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r5 = r5.setForceRpc(r6)
            r0 = r23
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r5 = r5.setAppReq(r0)
            long r16 = java.lang.System.currentTimeMillis()
            r0 = r16
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r5 = r5.setStartTime(r0)
            java.util.List r6 = getResourcePackageList(r45)
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r24 = r5.setResPackageList(r6)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler$2 r4 = new com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler$2
            r5 = r43
            r6 = r47
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            r0 = r24
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r5 = r0.setUpdateCallback(r4)
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam r5 = r5.build()
            r0 = r47
            r0.updateApp(r5)
            goto L_0x00b6
        L_0x03f7:
            r0 = r45
            r1 = r44
            boolean r5 = com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager.isOutOfReqRate(r0, r1)
            if (r5 == 0) goto L_0x01a8
            r32 = 1
            java.lang.String r18 = "synctry"
            goto L_0x01a8
        L_0x0407:
            r5 = 2
            r0 = r43
            r0.tinyType = r5
            java.lang.Class<com.alipay.mobile.nebula.tinypermission.H5ApiManager> r5 = com.alipay.mobile.nebula.tinypermission.H5ApiManager.class
            java.lang.String r5 = r5.getName()
            java.lang.Object r26 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r5)
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r26 = (com.alipay.mobile.nebula.tinypermission.H5ApiManager) r26
            if (r26 == 0) goto L_0x01f1
            r0 = r26
            r1 = r43
            r0.setIfNeedUpDownAnimWithoutAppinfo(r1)
            goto L_0x01f1
        L_0x0423:
            r0 = r44
            java.lang.String r5 = r0.app_id
            r0 = r44
            java.lang.String r6 = r0.version
            r0 = r47
            boolean r5 = r0.isInstalled(r5, r6)
            if (r5 != 0) goto L_0x02f8
            java.lang.String r33 = "syncforce"
            goto L_0x02f8
        L_0x0437:
            java.lang.String r5 = "syncforce"
            r0 = r23
            r0.reqmode = r5
            goto L_0x032d
        L_0x043f:
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r47
            java.lang.String r39 = r0.getWalletConfigNebulaVersion(r5)
            r0 = r43
            java.lang.String r5 = r0.targetAppId
            r0 = r31
            r1 = r39
            r0.put(r5, r1)
            goto L_0x03a3
        L_0x0456:
            r17 = 0
            r15 = r43
            r16 = r7
            r19 = r12
            r20 = r44
            r21 = r13
            r22 = r45
            updateSuccess(r15, r16, r17, r18, r19, r20, r21, r22)
            goto L_0x00b6
        L_0x0469:
            r17 = 0
            r15 = r43
            r16 = r7
            r19 = r12
            r20 = r44
            r21 = r13
            r22 = r45
            updateSuccess(r15, r16, r17, r18, r19, r20, r21, r22)
            goto L_0x00b6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler.doSyncApp(com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo, com.alipay.mobile.nebula.appcenter.model.AppInfo, android.os.Bundle, java.lang.String, com.alipay.mobile.nebula.provider.H5AppProvider):void");
    }

    private static boolean checkDebugMode(H5StartAppInfo startAppInfo, Bundle copyParam) {
        if (H5TinyAppDebugMode.enableTinyAppDebugMode()) {
            if (H5InstallAppAdvice.enableUseDevMode(copyParam) && !startAppInfo.hasAuth) {
                H5TinyAppDebugMode.doRpcAuth(copyParam, startAppInfo);
                return true;
            }
        } else if (H5PreferAppList.enablePreferList()) {
            if (H5PreferAppList.enableUsePrefer(copyParam)) {
                if (H5InstallAppAdvice.enableUseDevMode(copyParam)) {
                    String preferVersion = H5Utils.getString(copyParam, (String) H5PreferAppList.nbsv);
                    String nbScene = H5Utils.getString(copyParam, (String) "nbsn");
                    if (!TextUtils.isEmpty(preferVersion) && !TextUtils.isEmpty(nbScene)) {
                        H5PreferAppList.getInstance().add(startAppInfo.targetAppId, preferVersion, nbScene);
                    }
                } else if (H5PreferAppList.getInstance().contains(startAppInfo.targetAppId)) {
                    H5Log.d(TAG, "deleteAppInfo " + startAppInfo.targetAppId + " from H5PreferAppList");
                    H5PreferAppList.getInstance().deleteAppInfoWithAppId(startAppInfo.targetAppId);
                    syncApp(startAppInfo);
                    return true;
                }
            }
            if (!H5PreferAppList.enableUsePrefer(copyParam) && !H5InstallAppAdvice.enableUseDevMode(copyParam) && H5PreferAppList.getInstance().contains(startAppInfo.targetAppId)) {
                H5PreferAppList.startCheckPermissionScheme(startAppInfo);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static List<String> getResourcePackageList(Bundle copyParam) {
        JSONArray packageList = H5Utils.parseArray(H5Utils.getString(copyParam, (String) "nbpkgres"));
        List resPackageList = new ArrayList();
        if (packageList != null) {
            try {
                if (packageList.size() > 0) {
                    Iterator<Object> it = packageList.iterator();
                    while (it.hasNext()) {
                        resPackageList.add((String) it.next());
                    }
                }
            } catch (Exception e) {
                H5Log.e(TAG, (Throwable) e);
            }
        }
        return resPackageList;
    }

    /* access modifiers changed from: private */
    public static void updateFail(String syncUpdate, String url, H5StartAppInfo startAppInfo, H5LoadingManager h5LoadingManager, String syncOffline, AppInfo appInfo, String appId, String offMode, Bundle copyParam, boolean rpcError) {
        String errorPageUrl;
        if (!TextUtils.equals(syncUpdate, "syncforce")) {
            setSyncOffline(startAppInfo, syncOffline, h5LoadingManager, syncUpdate, url, appInfo, offMode, copyParam);
        } else if (!TextUtils.isEmpty(url)) {
            prepareData.setNbUrl(url);
            prepareData.uploadPrepareLog("finish", H5AppPrepareData.PREPARE_FAIL);
            exitPageAndOpenUrl(url, h5LoadingManager);
        } else if (rpcError) {
            showLoadingError(startAppInfo, H5AppPrepareData.PREPARE_RPC_FAIL, h5LoadingManager);
        } else {
            logError(startAppInfo, H5AppPrepareData.PREPARE_FAIL);
            if (H5InstallAppAdvice.enableUseDevMode(copyParam)) {
                errorPageUrl = String.format(appInfoEmptyUrl, new Object[]{appId, Integer.valueOf(DEBUG_ERROR_CODE)});
            } else {
                errorPageUrl = String.format(appInfoEmptyUrl, new Object[]{appId, Integer.valueOf(1001)});
            }
            exitPageAndOpenUrl(errorPageUrl, h5LoadingManager);
        }
    }

    public static void exitPageAndOpenUrl(final String url, final H5LoadingManager h5LoadingManager) {
        if (h5LoadingManager != null) {
            if (h5LoadingManager.getPageStatues() == 2) {
                H5Log.d(TAG, "has show fail not open");
                return;
            }
            playExitAnimation(h5LoadingManager, 500);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    if (h5LoadingManager.isReady()) {
                        h5LoadingManager.exit();
                        H5Utils.openUrl(url);
                        return;
                    }
                    H5AppHandler.waitCount = H5AppHandler.waitCount + 1;
                    H5Log.d(H5AppHandler.TAG, "exitPageAndOpenUrl not ready ,send again " + H5AppHandler.waitCount);
                    if (H5AppHandler.waitCount <= H5AppHandler.waitMax) {
                        H5AppHandler.exitPageAndOpenUrl(url, h5LoadingManager);
                    }
                }
            }, 1000);
        }
    }

    private static void playExitAnimation(final H5LoadingManager manager, int delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public final void run() {
                if (manager != null && manager.isReady()) {
                    H5Log.d(H5AppHandler.TAG, "playExitAnimation");
                    manager.playExitAnimation();
                }
            }
        }, (long) delay);
    }

    /* access modifiers changed from: private */
    public static void updateSuccess(H5StartAppInfo h5StartAppInfo, String syncOffline, H5LoadingManager h5LoadingManager, String syncUpdate, String url, AppInfo appInfo, String offMode, Bundle copyParam) {
        if (H5InstallAppAdvice.enableUseDevMode(copyParam) && H5DevAppList.getInstance().contains(h5StartAppInfo.targetAppId)) {
            H5InstallAppAdvice.updateApplicationDescription(appInfo.app_id, copyParam);
        }
        setSyncOffline(h5StartAppInfo, syncOffline, h5LoadingManager, syncUpdate, url, appInfo, offMode, copyParam);
    }

    /* access modifiers changed from: private */
    public static void setDevReq(String appId, AppReq appReq) {
        if (appReq != null) {
            appReq.scene = H5DevAppList.getInstance().getDevInfo(appId).nbsn;
            appReq.nbsource = "debug";
        }
    }

    private static void updateNebulaAppAsync(final String appId, final Bundle copyParam) {
        ScheduledThreadPoolExecutor executor = H5Utils.getScheduledExecutor();
        if (executor != null) {
            executor.schedule(new Runnable() {
                public final void run() {
                    Map map = new HashMap();
                    H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                    if (h5AppProvider != null) {
                        AppReq appReq = new AppReq();
                        appReq.reqmode = "async";
                        if (H5DevAppList.getInstance().contains(appId)) {
                            map.put(appId, H5DevAppList.getInstance().getDevInfo(appId).nbsv);
                            H5AppHandler.setDevReq(appId, appReq);
                        } else {
                            map.put(appId, h5AppProvider.getWalletConfigNebulaVersion(appId));
                        }
                        H5Log.d(H5AppHandler.TAG, "updateNebulaAppAsync appId:" + appId);
                        h5AppProvider.updateApp(H5UpdateAppParam.newBuilder().setDownLoadAmr(true).setAppMap(map).setAppReq(appReq).setStartTime(System.currentTimeMillis()).setResPackageList(H5AppHandler.getResourcePackageList(copyParam)).build());
                    }
                }
            }, 5, TimeUnit.SECONDS);
        }
    }

    /* access modifiers changed from: private */
    public static void offlineFail(String nbUrl, H5LoadingManager h5LoadingManager, H5StartAppInfo h5StartAppInfo, String errCode, AppInfo appInfo, String offMode) {
        if (!TextUtils.isEmpty(nbUrl)) {
            prepareData.setNbUrl(nbUrl);
            prepareData.uploadPrepareLog("finish", errCode);
            exitPageAndOpenUrl(nbUrl, h5LoadingManager);
            return;
        }
        offlineFailTryFallback(h5LoadingManager, h5StartAppInfo, errCode, appInfo, offMode);
    }

    /* access modifiers changed from: private */
    public static boolean checkAppxMinVersion(H5StartAppInfo h5StartAppInfo, AppInfo appInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, String syncOffline, String loadingType) {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            return true;
        }
        if (!h5TinyAppService.isSetAppxMinVersionValid(h5StartAppInfo.targetAppId)) {
            H5Log.d(TAG, "checkAppxMinVersion...hit blacklist");
            return true;
        }
        String minAppxVersion = H5Utils.getAppxMinVersion(appInfo);
        if (TextUtils.isEmpty(minAppxVersion)) {
            return true;
        }
        String availableAppxVersion = H5Utils.getCurrentAvailableAppxVersion();
        H5Log.d(TAG, "checkAppxMinVersion...min: " + minAppxVersion + ",current: " + availableAppxVersion);
        if (TextUtils.isEmpty(availableAppxVersion)) {
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider != null) {
                checkForceUpdateAppInfo(h5AppProvider, h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType);
            }
            return false;
        }
        int compare = H5AppUtil.compareVersion(availableAppxVersion, minAppxVersion);
        if (compare == 1 || compare == 0) {
            return true;
        }
        H5AppProvider h5AppProvider2 = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider2 == null) {
            H5Log.d(TAG, "checkAppxMinVersion...h5AppProvider is null");
            return true;
        }
        String appxAppId = H5AppUtil.getAppxAppId();
        String highestVersion = h5AppProvider2.getVersion(appxAppId);
        H5Log.d(TAG, "checkAppxMinVersion...highest: " + highestVersion);
        int compare2 = H5AppUtil.compareVersion(minAppxVersion, highestVersion);
        if (compare2 == -1 || compare2 == 0) {
            final H5StartAppInfo h5StartAppInfo2 = h5StartAppInfo;
            final H5LoadingManager h5LoadingManager2 = h5LoadingManager;
            final String str = syncUpdate;
            final Bundle bundle = copyParam;
            final AppInfo appInfo2 = appInfo;
            H5AppUtil.prepare(appxAppId, highestVersion, new H5AppInstallCallback() {
                public final void onResult(boolean success, boolean isPatch) {
                    H5Log.d(H5AppHandler.TAG, "checkAppxMinVersion...result: " + success);
                    if (success) {
                        try {
                            H5AppHandler.markSpmBehavor(H5AppHandler.APPX_UPDATE_SUCCESS_SPM_ID, "appId", h5StartAppInfo2.targetAppId);
                        } catch (Throwable e) {
                            H5Log.e(H5AppHandler.TAG, "checkAppxMinVersion...e:" + e);
                            return;
                        }
                    } else {
                        H5AppHandler.markSpmBehavor(H5AppHandler.APPX_UPDATE_FAILED_SPM_ID, "appId", h5StartAppInfo2.targetAppId);
                    }
                    H5AppHandler.doOpenApp(h5StartAppInfo2, h5LoadingManager2, str, bundle, appInfo2);
                }
            });
            return false;
        }
        checkForceUpdateAppInfo(h5AppProvider2, h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType);
        return false;
    }

    private static void checkForceUpdateAppInfo(H5AppProvider h5AppProvider, H5StartAppInfo h5StartAppInfo, AppInfo appInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, String syncOffline, String loadingType) {
        boolean hasChecked = H5Utils.getBoolean(h5StartAppInfo.params, (String) HAS_CHECKED_MIN_APPX_VERSION, false);
        if (H5InstallAppAdvice.enableUseDevMode(h5StartAppInfo.params) || hasChecked) {
            openUpdateAppClient(h5StartAppInfo.targetAppId, h5LoadingManager);
        } else {
            forceUpdateAppInfo(h5AppProvider, h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType);
        }
    }

    /* access modifiers changed from: private */
    public static void openUpdateAppClient(final String appId, final H5LoadingManager h5LoadingManager) {
        H5Log.d(TAG, "openUpdateAppClient");
        if (h5LoadingManager != null) {
            if (h5LoadingManager.getPageStatues() == 2) {
                H5Log.d(TAG, "has show fail not open");
                return;
            }
            playExitAnimation(h5LoadingManager, 500);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    if (h5LoadingManager.isReady()) {
                        h5LoadingManager.exit();
                        H5AppClientUpgradeProvider appClientUpgradeProvider = (H5AppClientUpgradeProvider) H5Utils.getProvider(H5AppClientUpgradeProvider.class.getName());
                        if (appClientUpgradeProvider != null) {
                            appClientUpgradeProvider.showAppClientUpgrade(appId);
                            return;
                        }
                        return;
                    }
                    H5AppHandler.waitCount = H5AppHandler.waitCount + 1;
                    H5Log.d(H5AppHandler.TAG, "exitPageAndOpenUrl not ready ,send again " + H5AppHandler.waitCount);
                    if (H5AppHandler.waitCount <= H5AppHandler.waitMax) {
                        H5AppHandler.openUpdateAppClient(appId, h5LoadingManager);
                    }
                }
            }, 1000);
            markSpmBehavor(UPGRADE_CLIENT_SPM_ID, "appId", appId);
        }
    }

    private static void forceUpdateAppInfo(H5AppProvider h5AppProvider, H5StartAppInfo h5StartAppInfo, AppInfo appInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, String syncOffline, String loadingType) {
        H5Log.d(TAG, "forceUpdateAppInfo..." + h5StartAppInfo.targetAppId);
        if (h5LoadingManager == null) {
            h5LoadingManager = getLoadingImpl();
            showPackageLoadingPage(h5StartAppInfo, h5AppProvider, h5LoadingManager, loadingType, H5LoadingUtil.getTimeout(h5StartAppInfo.targetAppId, syncOffline, copyParam, appInfo));
        } else {
            H5LoadingTypeListen h5LoadingTypeListen = H5LoadingUtil.getH5LoadingTypeListen();
            if (h5LoadingTypeListen != null) {
                h5LoadingTypeListen.onGetType(syncOffline, H5LoadingUtil.getTimeout(h5StartAppInfo.targetAppId, loadingType, copyParam, appInfo), h5StartAppInfo.targetAppId);
            }
        }
        final H5LoadingManager finalH5LoadingManager = h5LoadingManager;
        String appxAppId = H5AppUtil.getAppxAppId();
        String queryVersion = h5AppProvider.getWalletConfigNebulaVersion(appxAppId);
        Map appIdMap = new HashMap();
        appIdMap.put(appxAppId, queryVersion);
        AppReq appReq = new AppReq();
        appReq.reqmode = "syncforce";
        final H5StartAppInfo h5StartAppInfo2 = h5StartAppInfo;
        final AppInfo appInfo2 = appInfo;
        final String str = syncUpdate;
        final Bundle bundle = copyParam;
        final String str2 = syncOffline;
        final String str3 = loadingType;
        H5AppProvider h5AppProvider2 = h5AppProvider;
        h5AppProvider2.updateApp(H5UpdateAppParam.newBuilder().setAppMap(appIdMap).setForceRpc(true).setAppReq(appReq).setStartTime(System.currentTimeMillis()).setUpdateCallback(new H5UpdateAppCallback() {
            public final void onResult(boolean success, boolean limit) {
                H5Log.d(H5AppHandler.TAG, "prepareUpdate...result: " + success);
                Bundle bundle = h5StartAppInfo2.params;
                if (bundle == null) {
                    bundle = new Bundle();
                    h5StartAppInfo2.params = bundle;
                }
                bundle.putBoolean(H5AppHandler.HAS_CHECKED_MIN_APPX_VERSION, true);
                if (success) {
                    H5AppHandler.checkAppxMinVersion(h5StartAppInfo2, appInfo2, finalH5LoadingManager, str, bundle, str2, str3);
                } else {
                    H5AppHandler.openUpdateAppClient(h5StartAppInfo2.targetAppId, finalH5LoadingManager);
                }
            }
        }).build());
    }

    private static void exitLoadingPage(final H5LoadingManager h5LoadingManager) {
        if (h5LoadingManager != null) {
            playExitAnimation(h5LoadingManager, 0);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    if (h5LoadingManager.isReady()) {
                        H5Log.d(H5AppHandler.TAG, "exitLoadingPage...exit");
                        h5LoadingManager.exit();
                    }
                }
            }, 0);
        }
    }

    /* access modifiers changed from: private */
    public static void markSpmBehavor(String seedId, String key, String value) {
        Behavor behavor = new Behavor();
        behavor.setBehaviourPro(TINY_APP_BIZ_TYPE);
        behavor.setSeedID(seedId);
        behavor.addExtParam(key, value);
        LoggerFactory.getBehavorLogger().click(behavor);
    }

    private static void setSyncOffline(H5StartAppInfo h5StartAppInfo, String syncOffline, H5LoadingManager h5LoadingManager, String syncUpdate, String url, AppInfo appInfo, String offMode, Bundle copyParam) {
        String tmpLoadingType;
        if (TextUtils.isEmpty(offMode)) {
            tmpLoadingType = syncOffline;
        } else {
            tmpLoadingType = offMode;
        }
        final String loadingType = tmpLoadingType;
        if (TextUtils.equals(syncOffline, "sync") || H5AppUtil.enableDSL(copyParam)) {
            H5LoadingApp.put(h5StartAppInfo.targetAppId, h5StartAppInfo.nebulaVersion);
            final H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider != null && appInfo != null) {
                boolean isAvailable = h5AppProvider.isAvailable(h5StartAppInfo.targetAppId, appInfo.version);
                H5Log.d(TAG, "syncOffline=sync " + h5StartAppInfo.targetAppId + Token.SEPARATOR + appInfo.version + Token.SEPARATOR + isAvailable);
                h5StartAppInfo.params.putBoolean("is_local", isAvailable);
                h5StartAppInfo.params.putLong("perf_open_app_time", SystemClock.elapsedRealtime());
                h5StartAppInfo.params.putLong(PERF_RPC_TIME_KEY, prepareData.getRequestEndTime() - prepareData.getRequestBeginTime());
                if (!isAvailable) {
                    if (h5StartAppInfo.enableMultiProcess) {
                        H5EventHandlerService h5EventHandlerService2 = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                        if (h5EventHandlerService2 != null) {
                            h5EventHandlerService2.startLiteProcessAsync();
                        }
                    }
                    if (h5LoadingManager == null) {
                        h5LoadingManager = getLoadingImpl();
                        showPackageLoadingPage(h5StartAppInfo, h5AppProvider, h5LoadingManager, loadingType, H5LoadingUtil.getTimeout(h5StartAppInfo.targetAppId, syncOffline, copyParam, appInfo));
                    } else {
                        H5LoadingTypeListen h5LoadingTypeListen = H5LoadingUtil.getH5LoadingTypeListen();
                        if (h5LoadingTypeListen != null) {
                            h5LoadingTypeListen.onGetType(syncOffline, H5LoadingUtil.getTimeout(h5StartAppInfo.targetAppId, loadingType, copyParam, appInfo), h5StartAppInfo.targetAppId);
                        }
                    }
                    final H5LoadingManager finalH5LoadingManager = h5LoadingManager;
                    prepareData.setDownloadTime(System.currentTimeMillis());
                    String str = h5StartAppInfo.targetAppId;
                    final H5StartAppInfo h5StartAppInfo2 = h5StartAppInfo;
                    final AppInfo appInfo2 = appInfo;
                    final String str2 = syncUpdate;
                    final Bundle bundle = copyParam;
                    final String str3 = syncOffline;
                    final String str4 = url;
                    final String str5 = offMode;
                    h5AppProvider.downloadApp(str, appInfo.version, new H5DownloadCallback() {
                        public final void onFinish(H5DownloadRequest h5DownloadRequest, String path) {
                            H5AppHandler.prepareData.setDownloadEndTime(System.currentTimeMillis());
                            H5AppHandler.prepareData.setInstallTime(System.currentTimeMillis());
                            H5Log.d(H5AppHandler.TAG, "onFinish:" + path);
                            H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                                public void run() {
                                    if (h5AppProvider != null) {
                                        h5AppProvider.installApp(h5StartAppInfo2.targetAppId, appInfo2.version, (H5AppInstallCallback) new H5AppInstallCallback() {
                                            public void onResult(boolean success, boolean isPatch) {
                                                H5AppHandler.prepareData.setInstallEndTime(System.currentTimeMillis());
                                                H5Log.d(H5AppHandler.TAG, "install onResult " + success);
                                                if (success) {
                                                    H5AppHandler.openApp(h5StartAppInfo2, finalH5LoadingManager, str2, bundle, appInfo2, str3, loadingType);
                                                } else {
                                                    H5AppHandler.offlineFail(str4, finalH5LoadingManager, h5StartAppInfo2, H5AppPrepareData.PREPARE_UNZIP_FAIL, appInfo2, str5);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }

                        public final void onFailed(H5DownloadRequest h5DownloadRequest, int code, String error) {
                            H5AppHandler.prepareData.setDownloadEndTime(System.currentTimeMillis());
                            if (appInfo2 == null) {
                                H5Log.d(H5AppHandler.TAG, "appInfo == null");
                                return;
                            }
                            H5Log.d(H5AppHandler.TAG, "onFailed" + code + error);
                            H5AppHandler.offlineFail(str4, finalH5LoadingManager, h5StartAppInfo2, H5AppPrepareData.PREPARE_DOWNLOAD_FAIL, appInfo2, str5);
                        }
                    });
                    return;
                }
                boolean isInstall = h5AppProvider.isInstalled(h5StartAppInfo.targetAppId, appInfo.version);
                H5Log.d(TAG, "isInstall " + isInstall);
                if (!isInstall) {
                    prepareData.setInstallTime(System.currentTimeMillis());
                    final H5LoadingManager finalH5LoadingManager1 = h5LoadingManager;
                    final H5StartAppInfo h5StartAppInfo3 = h5StartAppInfo;
                    final String str6 = syncUpdate;
                    final Bundle bundle2 = copyParam;
                    final AppInfo appInfo3 = appInfo;
                    final String str7 = syncOffline;
                    final String str8 = loadingType;
                    final String str9 = url;
                    final String str10 = offMode;
                    h5AppProvider.installApp(h5StartAppInfo.targetAppId, appInfo.version, (H5AppInstallCallback) new H5AppInstallCallback() {
                        public final void onResult(boolean success, boolean isPatch) {
                            H5AppHandler.prepareData.setInstallEndTime(System.currentTimeMillis());
                            H5Log.d(H5AppHandler.TAG, "install result:" + success);
                            if (success) {
                                H5AppHandler.openApp(h5StartAppInfo3, finalH5LoadingManager1, str6, bundle2, appInfo3, str7, str8);
                            } else {
                                H5AppHandler.offlineFail(str9, finalH5LoadingManager1, h5StartAppInfo3, H5AppPrepareData.PREPARE_UNZIP_FAIL, appInfo3, str10);
                            }
                        }
                    });
                    return;
                }
            } else {
                return;
            }
        } else if (!H5AppUtil.isH5AppPkg(appInfo)) {
            offlineNebulaAppAsync(h5StartAppInfo.targetAppId, appInfo);
        }
        openApp(h5StartAppInfo, h5LoadingManager, syncUpdate, copyParam, appInfo, syncOffline, loadingType);
    }

    private static void showPackageLoadingPage(H5StartAppInfo h5StartAppInfo, H5AppProvider h5AppProvider, H5LoadingManager h5LoadingManager, String loadingType, int timeout) {
        String appIconUrl = h5AppProvider.getIconUrl(h5StartAppInfo.targetAppId, h5StartAppInfo.nebulaVersion);
        String appName = h5AppProvider.getAppName(h5StartAppInfo.targetAppId, h5StartAppInfo.nebulaVersion);
        h5StartAppInfo.params.putString(H5Param.LONG_WALLET_APP_NAME, appName);
        boolean enableSlogan = true;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_loadpageslogan"))) {
            enableSlogan = false;
        }
        String slogan = "";
        if (enableSlogan && h5AppProvider.isSmallProgramFromOpenPlat(h5StartAppInfo.targetAppId)) {
            slogan = h5AppProvider.getSlogan(h5StartAppInfo.targetAppId, h5StartAppInfo.nebulaVersion);
        }
        h5StartAppInfo.appPrepareData = prepareData;
        h5LoadingManager.open(setStartParam(appName, appIconUrl, slogan, h5StartAppInfo.params), h5StartAppInfo.targetAppId, loadingType, h5StartAppInfo, timeout);
        h5StartAppInfo.params.putBoolean(H5Param.LONG_PACKAGE_LOADING_SHOWN, true);
        prepareData.setPageStatus(1);
    }

    private static void offlineNebulaAppAsync(final String appId, final AppInfo appInfo) {
        final H5AppProvider nebulaAppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (nebulaAppProvider != null && appInfo != null) {
            boolean isInstalled = nebulaAppProvider.isInstalled(appId, appInfo.version);
            if (!isInstalled) {
                boolean isAvailable = nebulaAppProvider.isAvailable(appId, appInfo.version);
                H5Log.d(TAG, "offlineNebulaAppAsync App appId:" + appId + " version:" + appInfo.version + " isAvailable:" + isAvailable);
                if (!isAvailable) {
                    H5Log.d(TAG, "offlineNebulaAppAsync :downloadApp appId:" + appId + " version:" + appInfo.version);
                    nebulaAppProvider.downloadApp(appId, appInfo.version, new H5DownloadCallback() {
                        public final void onFinish(H5DownloadRequest h5DownloadRequest, String savePath) {
                            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                                public void run() {
                                    H5AppHandler.install(appId, appInfo.version, nebulaAppProvider);
                                }
                            });
                        }
                    });
                    return;
                }
                H5Log.d(TAG, "offlineNebulaAppAsync App appId:" + appId + " version:" + appInfo.version + " to install ");
                install(appId, appInfo.version, nebulaAppProvider);
                logInstallStatus(appId, appInfo.version, isInstalled);
                return;
            }
            H5Log.d(TAG, "offlineNebulaAppAsync App appId:" + appId + " version:" + appInfo.version + " is install ");
        }
    }

    private static void logInstallStatus(String appId, String version, boolean isInstall) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_INSTALL_STATUS").param1().add("monitor", null).param3().add("appId", appId).add("version", version).add("isInstall", Boolean.valueOf(isInstall)).add("installPackageConfig", h5ConfigProvider.getConfig("h5_installPackageConfig")));
        }
    }

    /* access modifiers changed from: private */
    public static void install(final String appId, final String version, final H5AppProvider nebulaAppProvider) {
        H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
            public final void run() {
                nebulaAppProvider.installApp(appId, version);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void openApp(H5StartAppInfo h5StartAppInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, AppInfo appInfo, String syncOffline, String loadingType) {
        if (!h5StartAppInfo.isCloseBtnClicked) {
            try {
                if (!checkAppxMinVersion(h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType)) {
                    H5Log.d(TAG, "openApp...need");
                } else {
                    doOpenApp(h5StartAppInfo, h5LoadingManager, syncUpdate, copyParam, appInfo);
                }
            } catch (Exception e) {
                H5Log.e(TAG, "startApp [targetAppId] " + h5StartAppInfo.targetAppId + " failed!", e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void doOpenApp(final H5StartAppInfo h5StartAppInfo, final H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, AppInfo appInfo) {
        H5TinyAppDebugMode.addRecentAppForDebugMode(copyParam, appInfo);
        if (!TextUtils.equals(syncUpdate, "synctry") && !TextUtils.equals(syncUpdate, "syncforce")) {
            updateNebulaAppAsync(h5StartAppInfo.targetAppId, copyParam);
        }
        if (h5LoadingManager == null) {
            startApp(h5StartAppInfo, null);
        } else if (H5Utils.enableExitAndStartAppOnMain()) {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    H5Log.d(H5AppHandler.TAG, "exitAndStartApp runOnMain");
                    H5AppHandler.exitAndStartApp(h5StartAppInfo, h5LoadingManager);
                }
            });
        } else {
            exitAndStartApp(h5StartAppInfo, h5LoadingManager);
        }
    }

    public static void exitAndStartApp(final H5StartAppInfo h5StartAppInfo, final H5LoadingManager h5LoadingManager) {
        long extraDelayTime;
        if (h5StartAppInfo != null) {
            if (h5LoadingManager != null) {
                H5Log.d(TAG, "[exitAndStartApp] h5LoadingManager status:" + h5LoadingManager.getPageStatues());
                if (h5LoadingManager.isPageDestroy()) {
                    H5Log.d(TAG, "[exitAndStartApp] h5LoadingManager has exit not startApp");
                    return;
                } else if (h5LoadingManager.getPageStatues() == 3) {
                    H5Log.d(TAG, "[exitAndStartApp] H5PageStatues.HAS_START_APP not startApp again");
                    return;
                } else if (h5LoadingManager.getPageStatues() == 2) {
                    H5Log.d(TAG, "[exitAndStartApp] H5PageStatues.SHOW_FAIL not startApp again");
                    return;
                } else {
                    h5LoadingManager.setPageStatue(3);
                }
            }
            if (h5LoadingManager != null && H5NebulaAppCacheManager.NEBULA_H5TINY_APP.equals(h5StartAppInfo.nebulaAppType)) {
                long extraDelayTime2 = System.currentTimeMillis() - h5LoadingManager.getStartLoadingTime();
                if (extraDelayTime2 > 300 || extraDelayTime2 < 0) {
                    extraDelayTime = 0;
                } else {
                    extraDelayTime = 300 - extraDelayTime2;
                }
                int animDelay = (int) extraDelayTime;
                int startAppDelay = (int) extraDelayTime;
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null) {
                    String config = h5ConfigProvider.getConfig("h5_openLoadingDelay");
                    if (!TextUtils.isEmpty(config) && "yes".equalsIgnoreCase(config)) {
                        startAppDelay += 300;
                    }
                }
                playExitAnimation(h5LoadingManager, animDelay);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        H5Log.d(H5AppHandler.TAG, "begin startApp openMultiProcess");
                        H5AppHandler.startApp(h5StartAppInfo, h5LoadingManager);
                    }
                }, (long) startAppDelay);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        if (h5LoadingManager.isReady()) {
                            H5Log.d(H5AppHandler.TAG, "exit");
                            h5LoadingManager.exit();
                        }
                    }
                }, 2500);
            } else if (isFirstStartApp) {
                isFirstStartApp = false;
                playExitAnimation(h5LoadingManager, 500);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        if (h5LoadingManager != null && h5LoadingManager.isReady()) {
                            H5Log.d(H5AppHandler.TAG, "exit");
                            h5LoadingManager.exit();
                        }
                    }
                }, 1000);
                H5Log.d(TAG, "isFirstStartApp startApp");
                startApp(h5StartAppInfo, h5LoadingManager);
            } else {
                playExitAnimation(h5LoadingManager, 500);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        if (h5LoadingManager != null && h5LoadingManager.isReady()) {
                            H5Log.d(H5AppHandler.TAG, H5PageData.FROM_TYPE_START_APP);
                            H5AppHandler.startApp(h5StartAppInfo, h5LoadingManager);
                            H5Log.d(H5AppHandler.TAG, "exit");
                            h5LoadingManager.exit();
                        }
                    }
                }, 1000);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void startApp(final H5StartAppInfo h5StartAppInfo, H5LoadingManager h5LoadingManager) {
        boolean enableOuter;
        boolean enableInner;
        boolean enableNoAppinfo;
        if (h5LoadingManager == null || h5LoadingManager.getPageStatues() != 2) {
            prepareData.uploadPrepareLog("finish", "1");
            if (!h5StartAppInfo.useAppX || h5StartAppInfo.tinyType != 0 || TextUtils.equals("66666672", h5StartAppInfo.targetAppId)) {
                enableOuter = false;
            } else {
                enableOuter = true;
            }
            if (!h5StartAppInfo.useAppX || h5StartAppInfo.tinyType != 1 || !h5StartAppInfo.isUsePresetPopmenu) {
                enableInner = false;
            } else {
                enableInner = true;
            }
            if (h5StartAppInfo.tinyType != 2 || !h5StartAppInfo.enableUpDownAnimWithoutAppinfo) {
                enableNoAppinfo = false;
            } else {
                enableNoAppinfo = true;
            }
            H5Log.d(TAG, "H5AppHandler.startApp enableOuter " + enableOuter + " enableInner " + enableInner + " enableNoAppinfo " + enableNoAppinfo);
            if (enableOuter || enableInner || enableNoAppinfo || H5Utils.canTransferH5ToTinyWithAnimation(h5StartAppInfo.targetAppId, h5StartAppInfo.params)) {
                if (h5LoadingManager == null) {
                    H5Log.d(TAG, "put needAnimInTiny true");
                    h5StartAppInfo.params.putBoolean("needAnimInTiny", true);
                } else {
                    H5Log.d(TAG, "put needAnimInTiny false");
                    h5StartAppInfo.params.putBoolean("needAnimInTiny", false);
                }
            }
            if (H5Utils.isMain()) {
                H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                    public final void run() {
                        H5AppHandler.finalStartApp(h5StartAppInfo);
                    }
                });
            } else {
                finalStartApp(h5StartAppInfo);
            }
        } else {
            H5Log.d(TAG, "pageStatues.currentPageStatues == PageStatues.SHOW_FAIL not startApp again");
        }
    }

    /* access modifiers changed from: private */
    public static void finalStartApp(H5StartAppInfo h5StartAppInfo) {
        H5Log.d(TAG, "startApp " + h5StartAppInfo.targetAppId + " nebulaAppType:" + h5StartAppInfo.nebulaAppType);
        h5StartAppInfo.params.putString(CHECK_KEY, "yes");
        H5NebulaAppCacheManager.putAppType(h5StartAppInfo.targetAppId, h5StartAppInfo);
        if (h5StartAppInfo.params.containsKey(H5Param.NEBULA_LOADING_VERSION)) {
            h5StartAppInfo.params.remove(H5Param.NEBULA_LOADING_VERSION);
        }
        h5StartAppInfo.params.putString(H5Param.NEBULA_LOADING_VERSION, h5StartAppInfo.targetAppId + "_" + h5StartAppInfo.nebulaVersion);
        H5LoadingApp.remove(h5StartAppInfo.targetAppId);
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(h5StartAppInfo.sourceAppId, h5StartAppInfo.targetAppId, h5StartAppInfo.params, h5StartAppInfo.sceneParams, null);
    }

    private static void logError(H5StartAppInfo h5StartAppInfo, String reason) {
        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_PREPARE").param1().add("monitor", null).param3().add("appId", h5StartAppInfo.targetAppId).add("step", reason));
    }

    private static void offlineFailTryFallback(final H5LoadingManager loadingManager, final H5StartAppInfo h5StartAppInfo, String reason, AppInfo appInfo, String offMode) {
        if ((H5AppPrepareData.PREPARE_DOWNLOAD_FAIL.equals(reason) || H5AppPrepareData.PREPARE_UNZIP_FAIL.equals(reason)) && "try".equals(offMode)) {
            H5AppHandlerUtil.tryFallback(appInfo, new FallbackResult() {
                public final void onResult(boolean result, String reason) {
                    H5Log.d(H5AppHandler.TAG, "tryFallback " + result + " reason:" + reason);
                    if (result) {
                        h5StartAppInfo.params.remove(H5Param.NEBULA_FORCE_OFFLINE);
                        H5AppHandler.exitAndStartApp(h5StartAppInfo, loadingManager);
                        return;
                    }
                    H5AppHandler.showLoadingError(h5StartAppInfo, reason, loadingManager);
                }
            });
        } else {
            showLoadingError(h5StartAppInfo, reason, loadingManager);
        }
    }

    /* access modifiers changed from: private */
    public static void showLoadingError(H5StartAppInfo h5StartAppInfo, String errorCode, final H5LoadingManager loadingManager) {
        h5StartAppInfo.appPrepareData.uploadPrepareLog("finish", errorCode);
        H5DevAppList.getInstance().setUseDevMode(h5StartAppInfo.targetAppId, false);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public final void run() {
                if (loadingManager == null || !loadingManager.isReady()) {
                    H5Log.d(H5AppHandler.TAG, "h5page is null");
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            if (loadingManager == null || !loadingManager.isReady()) {
                                H5Log.d(H5AppHandler.TAG, "h5page is null not try");
                                return;
                            }
                            H5Log.d(H5AppHandler.TAG, "sendToWebFail again");
                            loadingManager.sendToWebFail();
                        }
                    }, 1000);
                    return;
                }
                H5Log.d(H5AppHandler.TAG, "sendToWebFail");
                loadingManager.sendToWebFail();
            }
        }, 1000);
    }

    public static boolean isSyncType(String type) {
        return "syncforce".equals(type) || "sync".equals(type);
    }

    private static H5LoadingManager getLoadingImpl() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_newloadpage"))) {
            return new H5LoadingViewImpl();
        }
        if (LauncherApplicationAgent.getInstance().getMicroApplicationContext().getLoadingPageManager() == null) {
            return new H5LoadingViewImpl();
        }
        return new H5LoadingFrameworkImpl();
    }
}
