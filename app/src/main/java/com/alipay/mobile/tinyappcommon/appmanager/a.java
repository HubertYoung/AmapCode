package com.alipay.mobile.tinyappcommon.appmanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandlerUtil;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandlerUtil.FallbackResult;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5InstallAppAdvice;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.appcenter.apphandler.H5TinyAppDebugMode;
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
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.b;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: TinyAppManagerProcess */
public final class a {
    public static H5EventHandlerService a;
    /* access modifiers changed from: private */
    public static String b = "TinyAppManagerProcess";
    /* access modifiers changed from: private */
    public static int c = 0;
    /* access modifiers changed from: private */
    public static int d = 4;
    /* access modifiers changed from: private */
    public static final H5AppPrepareData e = new H5AppPrepareData();
    private static boolean f = true;

    /* access modifiers changed from: private */
    public static void c(Bundle bundle) {
        H5ParamParser.parse(bundle, H5Param.LONG_NB_UPDATE, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_OFFLINE, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_URL, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_VERSION, false);
        H5ParamParser.parse(bundle, H5Param.LONG_NB_OFFMODE, false);
    }

    private static Bundle a(String appName, String appIconUrl, String slogan, Bundle param) {
        if (((H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName())) == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(H5AppHandler.sAppIcon, appIconUrl);
        bundle.putString("appName", appName);
        bundle.putString(H5AppHandler.sAppSlogan, slogan);
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService == null) {
            return bundle;
        }
        int lpid = param.getInt(h5EventHandlerService.getMultiProcessTag());
        H5Log.d(b, "lpid " + lpid);
        if (lpid == 0) {
            return bundle;
        }
        bundle.putInt(h5EventHandlerService.getMultiProcessTag(), lpid);
        return bundle;
    }

    private static void b(H5StartAppInfo startAppInfo) {
        e.clear();
        e.setBeginTime(System.currentTimeMillis());
        if (a == null) {
            a = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            if (!H5AppUtil.isOffLine(startAppInfo.targetAppId) || H5InstallAppAdvice.enableUseDevMode(startAppInfo.params)) {
                c = 0;
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
                H5Log.d(b, startAppInfo.targetAppId + " getVersion " + currentNbVersion + " cost:" + cost);
                e.setGetDBVersionTime(cost);
                a(startAppInfo, h5AppProvider.getAppInfo(startAppInfo.targetAppId, currentNbVersion), copyParam, currentNbVersion, h5AppProvider);
                return;
            }
            h5AppProvider.showOfflinePage(startAppInfo.targetAppId, startAppInfo.params);
        }
    }

    private static boolean a(String appId, Bundle startupParams) {
        TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
        if (mixActionService != null) {
            Set appIdSet = mixActionService.getUseWholePkgList();
            if (appIdSet == null || !appIdSet.contains(appId)) {
                return false;
            }
        }
        JSONObject subPackages = JSONObject.parseObject(H5Utils.getString(startupParams, (String) TinyAppSubPackagePlugin.SUB_PACKAGES));
        if (subPackages == null || subPackages.isEmpty()) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0194, code lost:
        if (com.alipay.mobile.nebula.appcenter.util.H5AppUtil.compareVersion(r31, r49.version) > 0) goto L_0x0196;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo r48, com.alipay.mobile.nebula.appcenter.model.AppInfo r49, android.os.Bundle r50, java.lang.String r51, com.alipay.mobile.nebula.provider.H5AppProvider r52) {
        /*
            r36 = 0
            if (r49 == 0) goto L_0x0006
            r36 = 1
        L_0x0006:
            r45 = 0
            java.lang.String r41 = ""
            java.lang.String r39 = ""
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r4 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r48
            java.lang.String r5 = r0.targetAppId
            boolean r4 = r4.contains(r5)
            if (r4 == 0) goto L_0x00ba
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r4 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r48
            java.lang.String r5 = r0.targetAppId
            r6 = 1
            r4.setUseDevMode(r5, r6)
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r4 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r48
            java.lang.String r5 = r0.targetAppId
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppInfo r34 = r4.getDevInfo(r5)
            r45 = 1
            r0 = r34
            java.lang.String r0 = r0.nbsv
            r41 = r0
            r0 = r34
            java.lang.String r0 = r0.nbsn
            r39 = r0
            java.lang.String r4 = "nbsn"
            r0 = r50
            java.lang.String r40 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r40)
            if (r4 != 0) goto L_0x0062
            r0 = r40
            r1 = r39
            boolean r4 = r0.equals(r1)
            if (r4 != 0) goto L_0x0062
            r39 = r40
            java.lang.String r4 = "nbsv"
            r0 = r50
            java.lang.String r41 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
        L_0x0062:
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r52
            r1 = r39
            r2 = r41
            com.alipay.mobile.nebula.appcenter.model.AppInfo r49 = r0.getAppInfo(r4, r1, r2)
            if (r49 == 0) goto L_0x00b7
            r36 = 1
        L_0x0074:
            java.lang.String r4 = b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "nbsn "
            r5.<init>(r6)
            r0 = r39
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " nbsv "
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r41
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " hasAppInfo:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r36
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
        L_0x00a2:
            r0 = r50
            r1 = r49
            com.alipay.mobile.nebula.appcenter.util.H5AppUtil.mergeConmonStartParam(r0, r1)
            c(r50)
            r0 = r48
            r1 = r50
            boolean r4 = a(r0, r1)
            if (r4 == 0) goto L_0x00c7
        L_0x00b6:
            return
        L_0x00b7:
            r36 = 0
            goto L_0x0074
        L_0x00ba:
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r4 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r48
            java.lang.String r5 = r0.targetAppId
            r6 = 0
            r4.setUseDevMode(r5, r6)
            goto L_0x00a2
        L_0x00c7:
            java.lang.String r4 = "nbversion"
            r0 = r50
            java.lang.String r47 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            java.lang.String r4 = "nboffline"
            r0 = r50
            java.lang.String r12 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            java.lang.String r4 = "nbupdate"
            r0 = r50
            java.lang.String r24 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            java.lang.String r4 = "nburl"
            r0 = r50
            java.lang.String r19 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            java.lang.String r4 = "nboffmode"
            r0 = r50
            java.lang.String r20 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = com.alipay.mobile.nebula.util.H5Utils.isTinyApp(r49)
            if (r4 == 0) goto L_0x0124
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r50
            boolean r4 = com.alipay.mobile.nebula.util.H5KeepAliveUtil.enableKeepAlive(r0, r4)
            if (r4 == 0) goto L_0x010d
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r4 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r49
            boolean r4 = r4.needStopLiteProcessByAppId(r0)
            if (r4 == 0) goto L_0x0124
        L_0x010d:
            java.lang.Class<com.alipay.mobile.h5container.service.H5EventHandlerService> r4 = com.alipay.mobile.h5container.service.H5EventHandlerService.class
            java.lang.String r4 = r4.getName()
            java.lang.Object r35 = com.alipay.mobile.nebula.util.H5Utils.findServiceByInterface(r4)
            com.alipay.mobile.h5container.service.H5EventHandlerService r35 = (com.alipay.mobile.h5container.service.H5EventHandlerService) r35
            if (r35 == 0) goto L_0x0124
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r35
            r0.stopLiteProcessByAppIdInServer(r4)
        L_0x0124:
            boolean r4 = com.alipay.mobile.nebula.appcenter.apphandler.H5InstallAppAdvice.enableUseDevMode(r50)
            if (r4 == 0) goto L_0x0159
            if (r36 != 0) goto L_0x0159
            java.lang.String r4 = "nbsv"
            r0 = r50
            java.lang.String r4 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x0159
            java.lang.String r4 = "https://render.alipay.com/p/s/tinyapperror/?appId=%s&errorCode=%d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r6 = 0
            r0 = r48
            java.lang.String r10 = r0.targetAppId
            r5[r6] = r10
            r6 = 1
            r10 = 50002(0xc352, float:7.0068E-41)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r5[r6] = r10
            java.lang.String r4 = java.lang.String.format(r4, r5)
            com.alipay.mobile.nebula.util.H5Utils.openUrl(r4)
            goto L_0x00b6
        L_0x0159:
            boolean r4 = com.alipay.mobile.nebula.util.H5Utils.isTinyApp(r49)
            if (r4 == 0) goto L_0x0168
            com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList r4 = com.alipay.mobile.nebula.appcenter.apphandler.H5DevAppList.getInstance()
            r0 = r49
            r4.addTinyAppRecord(r0)
        L_0x0168:
            com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList r4 = com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList.getInstance()
            r0 = r48
            java.lang.String r5 = r0.targetAppId
            boolean r4 = r4.contains(r5)
            if (r4 != 0) goto L_0x0178
            if (r36 != 0) goto L_0x017a
        L_0x0178:
            java.lang.String r24 = "syncforce"
        L_0x017a:
            java.lang.String r4 = "enbsv"
            r0 = r50
            java.lang.String r31 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r4)
            boolean r4 = android.text.TextUtils.isEmpty(r31)
            if (r4 != 0) goto L_0x0198
            if (r49 == 0) goto L_0x0196
            r0 = r49
            java.lang.String r4 = r0.version
            r0 = r31
            int r4 = com.alipay.mobile.nebula.appcenter.util.H5AppUtil.compareVersion(r0, r4)
            if (r4 <= 0) goto L_0x0198
        L_0x0196:
            java.lang.String r24 = "syncforce"
        L_0x0198:
            r38 = 0
            if (r36 == 0) goto L_0x01c8
            boolean r4 = com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager.isNeedForceUpdate(r49)
            if (r4 == 0) goto L_0x03f8
            r38 = 1
            java.lang.String r24 = "syncforce"
        L_0x01a6:
            java.lang.String r4 = b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "[syncApp] outOfReqRate: "
            r5.<init>(r6)
            r0 = r38
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = ", syncUpdate: "
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r24
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
        L_0x01c8:
            if (r49 == 0) goto L_0x041f
            com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5NebulaAppCacheManager.setAppType(r48, r49, r50)
            r0 = r49
            java.lang.String r0 = r0.version
            r42 = r0
            java.lang.String r4 = b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "nebulaVersion "
            r5.<init>(r6)
            r0 = r42
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            r0 = r42
            r1 = r48
            r1.nebulaVersion = r0
        L_0x01ef:
            java.lang.String r4 = b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "targetAppId:"
            r5.<init>(r6)
            r0 = r48
            java.lang.String r6 = r0.targetAppId
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = " wantNebulaVersion:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r47
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " syncOffline:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r12)
            java.lang.String r6 = " syncUpdate:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r24
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " currentNbVersion:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r51
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " nbUrl:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r19
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " nbOffMode: "
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r20
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r4 = e
            r0 = r24
            r4.setRequestMode(r0)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r4 = e
            r0 = r20
            r4.setOfflineMode(r12, r0)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r4 = e
            r0 = r48
            java.lang.String r5 = r0.targetAppId
            r4.setAppId(r5)
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r4 = e
            r0 = r48
            java.lang.String r5 = r0.nebulaVersion
            r4.setVersion(r5)
            java.lang.String r4 = "synctry"
            r0 = r24
            boolean r4 = android.text.TextUtils.equals(r0, r4)
            if (r4 != 0) goto L_0x0281
            java.lang.String r4 = "syncforce"
            r0 = r24
            boolean r4 = android.text.TextUtils.equals(r0, r4)
            if (r4 == 0) goto L_0x0481
        L_0x0281:
            r0 = r51
            r1 = r47
            int r43 = com.alipay.mobile.nebula.appcenter.util.H5AppUtil.compareVersion(r0, r1)
            java.lang.String r4 = b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "currentNbVersion"
            r5.<init>(r6)
            r0 = r51
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " wantNebulaVersion:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r47
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " "
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r43
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            r4 = -1
            r0 = r43
            if (r0 == r4) goto L_0x02d4
            com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList r4 = com.alipay.mobile.nebula.appcenter.rpcblacklist.H5RpcBlackList.getInstance()
            r0 = r48
            java.lang.String r5 = r0.targetAppId
            boolean r4 = r4.contains(r5)
            if (r4 != 0) goto L_0x02d4
            if (r36 == 0) goto L_0x02d4
            boolean r4 = android.text.TextUtils.isEmpty(r47)
            if (r4 != 0) goto L_0x02d4
            if (r38 == 0) goto L_0x046e
        L_0x02d4:
            r8 = r24
            java.lang.String r4 = "synctry"
            r0 = r24
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x02fc
            java.lang.String r4 = "sync"
            boolean r4 = r4.equals(r12)
            if (r4 == 0) goto L_0x02fc
            if (r49 == 0) goto L_0x02fc
            r0 = r49
            java.lang.String r4 = r0.app_id
            r0 = r49
            java.lang.String r5 = r0.version
            r0 = r52
            boolean r4 = r0.isAvailable(r4, r5)
            if (r4 != 0) goto L_0x043b
            java.lang.String r8 = "syncforce"
        L_0x02fc:
            com.alipay.mobile.nebula.appcenter.apphandler.loadingview.H5LoadingManager r7 = e()
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r50
            r1 = r49
            int r9 = a(r4, r8, r0, r1)
            r4 = r48
            r5 = r50
            r6 = r52
            a(r4, r5, r6, r7, r8, r9)
            java.util.HashMap r37 = new java.util.HashMap
            r37.<init>()
            com.alipay.mobile.nebula.appcenter.model.AppReq r29 = new com.alipay.mobile.nebula.appcenter.model.AppReq
            r29.<init>()
            java.lang.String r4 = "synctry"
            r0 = r24
            boolean r4 = android.text.TextUtils.equals(r0, r4)
            if (r4 == 0) goto L_0x044f
            java.lang.String r4 = "synctry"
            r0 = r29
            r0.reqmode = r4
        L_0x032f:
            boolean r4 = com.alipay.mobile.nebula.util.H5Utils.isTinyApp(r49)
            if (r4 != 0) goto L_0x0341
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            int r4 = r4.length()
            r5 = 15
            if (r4 <= r5) goto L_0x0347
        L_0x0341:
            java.lang.String r4 = "NO"
            r0 = r29
            r0.stableRpc = r4
        L_0x0347:
            java.lang.Class<com.alipay.mobile.nebula.provider.H5ConfigProvider> r4 = com.alipay.mobile.nebula.provider.H5ConfigProvider.class
            java.lang.String r4 = r4.getName()
            java.lang.Object r33 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r4)
            com.alipay.mobile.nebula.provider.H5ConfigProvider r33 = (com.alipay.mobile.nebula.provider.H5ConfigProvider) r33
            if (r33 == 0) goto L_0x036d
            java.lang.String r4 = "H5_loading_use_stableRpc"
            r0 = r33
            java.lang.String r46 = r0.getConfig(r4)
            java.lang.String r4 = "yes"
            r0 = r46
            boolean r4 = r4.equalsIgnoreCase(r0)
            if (r4 != 0) goto L_0x036d
            java.lang.String r4 = "NO"
            r0 = r29
            r0.stableRpc = r4
        L_0x036d:
            if (r45 == 0) goto L_0x0457
            java.lang.String r4 = b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "useDev "
            r5.<init>(r6)
            r0 = r41
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = " nbsn:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r39
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r37
            r1 = r41
            r0.put(r4, r1)
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r29
            b(r4, r0)
        L_0x03a5:
            r18 = r24
            r14 = r41
            r17 = r36
            r16 = r50
            r13 = r39
            com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData r4 = e
            long r10 = java.lang.System.currentTimeMillis()
            r4.setRequestBeginTime(r10)
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r4 = com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam.newBuilder()
            r0 = r37
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r4 = r4.setAppMap(r0)
            r5 = 1
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r4 = r4.setForceRpc(r5)
            r0 = r29
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r4 = r4.setAppReq(r0)
            long r10 = java.lang.System.currentTimeMillis()
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r4 = r4.setStartTime(r10)
            java.util.List r5 = d(r50)
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r30 = r4.setResPackageList(r5)
            com.alipay.mobile.tinyappcommon.appmanager.a$1 r9 = new com.alipay.mobile.tinyappcommon.appmanager.a$1
            r10 = r48
            r11 = r52
            r15 = r7
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r0 = r30
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam$Builder r4 = r0.setUpdateCallback(r9)
            com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam r4 = r4.build()
            r0 = r52
            r0.updateApp(r4)
            goto L_0x00b6
        L_0x03f8:
            r0 = r50
            r1 = r49
            boolean r4 = com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigManager.isOutOfReqRate(r0, r1)
            if (r4 == 0) goto L_0x0408
            r38 = 1
            java.lang.String r24 = "synctry"
            goto L_0x01a6
        L_0x0408:
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r50
            boolean r4 = a(r4, r0)
            if (r4 == 0) goto L_0x01a6
            java.lang.String r4 = b
            java.lang.String r5 = "sub package force rpc get whole package"
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)
            java.lang.String r24 = "syncforce"
            goto L_0x01a6
        L_0x041f:
            r4 = 2
            r0 = r48
            r0.tinyType = r4
            java.lang.Class<com.alipay.mobile.nebula.tinypermission.H5ApiManager> r4 = com.alipay.mobile.nebula.tinypermission.H5ApiManager.class
            java.lang.String r4 = r4.getName()
            java.lang.Object r32 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r4)
            com.alipay.mobile.nebula.tinypermission.H5ApiManager r32 = (com.alipay.mobile.nebula.tinypermission.H5ApiManager) r32
            if (r32 == 0) goto L_0x01ef
            r0 = r32
            r1 = r48
            r0.setIfNeedUpDownAnimWithoutAppinfo(r1)
            goto L_0x01ef
        L_0x043b:
            r0 = r49
            java.lang.String r4 = r0.app_id
            r0 = r49
            java.lang.String r5 = r0.version
            r0 = r52
            boolean r4 = r0.isInstalled(r4, r5)
            if (r4 != 0) goto L_0x02fc
            java.lang.String r8 = "syncforce"
            goto L_0x02fc
        L_0x044f:
            java.lang.String r4 = "syncforce"
            r0 = r29
            r0.reqmode = r4
            goto L_0x032f
        L_0x0457:
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r52
            java.lang.String r44 = r0.getWalletConfigNebulaVersion(r4)
            r0 = r48
            java.lang.String r4 = r0.targetAppId
            r0 = r37
            r1 = r44
            r0.put(r4, r1)
            goto L_0x03a5
        L_0x046e:
            r23 = 0
            r21 = r48
            r22 = r12
            r25 = r19
            r26 = r49
            r27 = r20
            r28 = r50
            b(r21, r22, r23, r24, r25, r26, r27, r28)
            goto L_0x00b6
        L_0x0481:
            r23 = 0
            r21 = r48
            r22 = r12
            r25 = r19
            r26 = r49
            r27 = r20
            r28 = r50
            b(r21, r22, r23, r24, r25, r26, r27, r28)
            goto L_0x00b6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.appmanager.a.a(com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo, com.alipay.mobile.nebula.appcenter.model.AppInfo, android.os.Bundle, java.lang.String, com.alipay.mobile.nebula.provider.H5AppProvider):void");
    }

    private static boolean a(H5StartAppInfo startAppInfo, Bundle copyParam) {
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
                    H5Log.d(b, "deleteAppInfo " + startAppInfo.targetAppId + " from H5PreferAppList");
                    H5PreferAppList.getInstance().deleteAppInfoWithAppId(startAppInfo.targetAppId);
                    b(startAppInfo);
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
    public static List<String> d(Bundle copyParam) {
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
            } catch (Exception e2) {
                H5Log.e(b, (Throwable) e2);
            }
        }
        return resPackageList;
    }

    /* access modifiers changed from: private */
    public static void b(String syncUpdate, String url, H5StartAppInfo startAppInfo, H5LoadingManager h5LoadingManager, String syncOffline, AppInfo appInfo, String appId, String offMode, Bundle copyParam, boolean rpcError) {
        if (!TextUtils.equals(syncUpdate, "syncforce")) {
            c(startAppInfo, syncOffline, h5LoadingManager, syncUpdate, url, appInfo, offMode, copyParam);
        } else if (!TextUtils.isEmpty(url)) {
            e.setNbUrl(url);
            e.uploadPrepareLog("finish", H5AppPrepareData.PREPARE_FAIL);
            c(url, h5LoadingManager);
        } else {
            e.uploadPrepareLog("finish", rpcError ? H5AppPrepareData.PREPARE_RPC_FAIL : H5AppPrepareData.PREPARE_FAIL);
            c(String.format("https://cache.amap.com/tiny-app/shortcut/error_page/index.html?appId=%s&errorCode=%s", new Object[]{appId, Integer.valueOf(H5InstallAppAdvice.enableUseDevMode(copyParam) ? 50002 : 1001)}), h5LoadingManager);
        }
    }

    /* access modifiers changed from: private */
    public static void c(final String url, final H5LoadingManager h5LoadingManager) {
        if (h5LoadingManager != null) {
            if (h5LoadingManager.getPageStatues() == 2) {
                H5Log.d(b, "has show fail not open");
                return;
            }
            a(h5LoadingManager, 500);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    if (h5LoadingManager.isReady()) {
                        h5LoadingManager.exit();
                        H5Utils.openUrl(url);
                        return;
                    }
                    a.c = a.c + 1;
                    H5Log.d(a.b, "exitPageAndOpenUrl not ready ,send again " + a.c);
                    if (a.c <= a.d) {
                        a.c(url, h5LoadingManager);
                    }
                }
            }, 1000);
        }
    }

    private static void a(final H5LoadingManager manager, int delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public final void run() {
                if (manager != null && manager.isReady()) {
                    H5Log.d(a.b, "playExitAnimation");
                    manager.playExitAnimation();
                }
            }
        }, (long) delay);
    }

    /* access modifiers changed from: private */
    public static void b(H5StartAppInfo h5StartAppInfo, String syncOffline, H5LoadingManager h5LoadingManager, String syncUpdate, String url, AppInfo appInfo, String offMode, Bundle copyParam) {
        if (H5InstallAppAdvice.enableUseDevMode(copyParam) && H5DevAppList.getInstance().contains(h5StartAppInfo.targetAppId)) {
            H5InstallAppAdvice.updateApplicationDescription(appInfo.app_id, copyParam);
        }
        c(h5StartAppInfo, syncOffline, h5LoadingManager, syncUpdate, url, appInfo, offMode, copyParam);
    }

    /* access modifiers changed from: private */
    public static void b(String appId, AppReq appReq) {
        if (appReq != null) {
            appReq.scene = H5DevAppList.getInstance().getDevInfo(appId).nbsn;
            appReq.nbsource = "debug";
        }
    }

    private static void b(final String appId, final Bundle copyParam) {
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
                            a.b(appId, appReq);
                        } else {
                            map.put(appId, h5AppProvider.getWalletConfigNebulaVersion(appId));
                        }
                        H5Log.d(a.b, "updateNebulaAppAsync appId:" + appId);
                        h5AppProvider.updateApp(H5UpdateAppParam.newBuilder().setDownLoadAmr(true).setAppMap(map).setAppReq(appReq).setStartTime(System.currentTimeMillis()).setResPackageList(a.d(copyParam)).setUpdateCallback(new TinyAppUpdateCallBackManager(appId)).build());
                    }
                }
            }, 4, TimeUnit.SECONDS);
        }
    }

    /* access modifiers changed from: private */
    public static void b(String nbUrl, H5LoadingManager h5LoadingManager, H5StartAppInfo h5StartAppInfo, String errCode, AppInfo appInfo, String offMode) {
        if (!TextUtils.isEmpty(nbUrl)) {
            e.setNbUrl(nbUrl);
            e.uploadPrepareLog("finish", errCode);
            c(nbUrl, h5LoadingManager);
            return;
        }
        a(h5LoadingManager, h5StartAppInfo, errCode, appInfo, offMode);
    }

    /* access modifiers changed from: private */
    public static boolean b(H5StartAppInfo h5StartAppInfo, AppInfo appInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, String syncOffline, String loadingType) {
        if (!TinyAppConfig.getInstance().getMinSdkVersionCheck()) {
            return true;
        }
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            return true;
        }
        if (!h5TinyAppService.isSetAppxMinVersionValid(h5StartAppInfo.targetAppId)) {
            H5Log.d(b, "checkAppxMinVersion...hit blacklist");
            return true;
        }
        String minAppxVersion = H5Utils.getAppxMinVersion(appInfo);
        if (TextUtils.isEmpty(minAppxVersion)) {
            return true;
        }
        String availableAppxVersion = H5Utils.getCurrentAvailableAppxVersion();
        H5Log.d(b, "checkAppxMinVersion...min: " + minAppxVersion + ",current: " + availableAppxVersion);
        if (TextUtils.isEmpty(availableAppxVersion)) {
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider != null) {
                a(h5AppProvider, h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType);
            }
            return false;
        }
        int compare = TinyappUtils.versionCompare(availableAppxVersion, minAppxVersion);
        if (compare == 1 || compare == 0) {
            return true;
        }
        H5AppProvider h5AppProvider2 = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider2 == null) {
            H5Log.d(b, "checkAppxMinVersion...h5AppProvider is null");
            return true;
        }
        String highestVersion = h5AppProvider2.getVersion("66666692");
        H5Log.d(b, "checkAppxMinVersion...highest: " + highestVersion);
        int compare2 = TinyappUtils.versionCompare(minAppxVersion, highestVersion);
        if (compare2 == -1 || compare2 == 0) {
            final H5StartAppInfo h5StartAppInfo2 = h5StartAppInfo;
            final H5LoadingManager h5LoadingManager2 = h5LoadingManager;
            final String str = syncUpdate;
            final Bundle bundle = copyParam;
            final AppInfo appInfo2 = appInfo;
            H5AppUtil.prepare("66666692", highestVersion, new H5AppInstallCallback() {
                public final void onResult(boolean success, boolean isPatch) {
                    H5Log.d(a.b, "checkAppxMinVersion...result: " + success);
                    if (success) {
                        try {
                            b.a("a192.b7351.c17706.d31776", "appId", h5StartAppInfo2.targetAppId);
                        } catch (Throwable e2) {
                            H5Log.e(a.b, "checkAppxMinVersion...e:" + e2);
                            return;
                        }
                    } else {
                        b.a("a192.b7351.c17706.d31775", "appId", h5StartAppInfo2.targetAppId);
                    }
                    a.b(h5StartAppInfo2, h5LoadingManager2, str, bundle, appInfo2);
                }
            });
            return false;
        }
        a(h5AppProvider2, h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType);
        return false;
    }

    private static void a(H5AppProvider h5AppProvider, H5StartAppInfo h5StartAppInfo, AppInfo appInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, String syncOffline, String loadingType) {
        boolean hasChecked = H5Utils.getBoolean(h5StartAppInfo.params, (String) "hasCheckedMinAppxVersion", false);
        if (H5InstallAppAdvice.enableUseDevMode(h5StartAppInfo.params) || hasChecked) {
            d(h5StartAppInfo.targetAppId, h5LoadingManager);
        } else {
            b(h5AppProvider, h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType);
        }
    }

    /* access modifiers changed from: private */
    public static void d(final String appId, final H5LoadingManager h5LoadingManager) {
        H5Log.d(b, "openUpdateAppClient");
        if (h5LoadingManager != null) {
            if (h5LoadingManager.getPageStatues() == 2) {
                H5Log.d(b, "has show fail not open");
                return;
            }
            a(h5LoadingManager, 500);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    if (h5LoadingManager.isReady()) {
                        h5LoadingManager.exit();
                        H5Utils.openUrl(String.format("https://cache.amap.com/tiny-app/shortcut/error_page/index.html?appId=%s&errorCode=%s", new Object[]{appId, Integer.valueOf(50001)}));
                        return;
                    }
                    a.c = a.c + 1;
                    H5Log.d(a.b, "exitPageAndOpenUrl not ready ,send again " + a.c);
                    if (a.c <= a.d) {
                        a.d(appId, h5LoadingManager);
                    }
                }
            }, 1000);
            b.a("a192.b7351.c17706.d31777", "appId", appId);
        }
    }

    private static void b(H5AppProvider h5AppProvider, H5StartAppInfo h5StartAppInfo, AppInfo appInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, String syncOffline, String loadingType) {
        H5Log.d(b, "forceUpdateAppInfo..." + h5StartAppInfo.targetAppId);
        if (h5LoadingManager == null) {
            h5LoadingManager = e();
            a(h5StartAppInfo, copyParam, h5AppProvider, h5LoadingManager, loadingType, a(h5StartAppInfo.targetAppId, syncOffline, copyParam, appInfo));
        } else {
            H5LoadingTypeListen h5LoadingTypeListen = H5LoadingUtil.getH5LoadingTypeListen();
            if (h5LoadingTypeListen != null) {
                h5LoadingTypeListen.onGetType(syncOffline, a(h5StartAppInfo.targetAppId, loadingType, copyParam, appInfo), h5StartAppInfo.targetAppId);
            }
        }
        final H5LoadingManager finalH5LoadingManager = h5LoadingManager;
        String queryVersion = h5AppProvider.getWalletConfigNebulaVersion("66666692");
        Map appIdMap = new HashMap();
        appIdMap.put("66666692", queryVersion);
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
                H5Log.d(a.b, "prepareUpdate...result: " + success);
                Bundle bundle = h5StartAppInfo2.params;
                if (bundle == null) {
                    bundle = new Bundle();
                    h5StartAppInfo2.params = bundle;
                }
                bundle.putBoolean("hasCheckedMinAppxVersion", true);
                if (success) {
                    a.b(h5StartAppInfo2, appInfo2, finalH5LoadingManager, str, bundle, str2, str3);
                } else {
                    a.d(h5StartAppInfo2.targetAppId, finalH5LoadingManager);
                }
            }
        }).build());
    }

    private static void c(H5StartAppInfo h5StartAppInfo, String syncOffline, H5LoadingManager h5LoadingManager, String syncUpdate, String url, AppInfo appInfo, String offMode, Bundle copyParam) {
        String tmpLoadingType;
        TemplateTinyApp.getInstance().prepareTemplateConfig(appInfo, h5StartAppInfo.params);
        if (TextUtils.isEmpty(offMode)) {
            tmpLoadingType = syncOffline;
        } else {
            tmpLoadingType = offMode;
        }
        String loadingType = tmpLoadingType;
        if (TextUtils.equals(syncOffline, "sync") || H5AppUtil.enableDSL(copyParam)) {
            H5LoadingApp.put(h5StartAppInfo.targetAppId, h5StartAppInfo.nebulaVersion);
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider != null && appInfo != null) {
                boolean isAvailable = h5AppProvider.isAvailable(h5StartAppInfo.targetAppId, appInfo.version);
                H5Log.d(b, "syncOffline=sync " + h5StartAppInfo.targetAppId + Token.SEPARATOR + appInfo.version + Token.SEPARATOR + isAvailable);
                h5StartAppInfo.params.putBoolean("is_local", isAvailable);
                h5StartAppInfo.params.putLong("perf_open_app_time", SystemClock.elapsedRealtime());
                h5StartAppInfo.params.putLong("perf_rpc_time", e.getRequestEndTime() - e.getRequestBeginTime());
                if (!isAvailable) {
                    if (h5StartAppInfo.enableMultiProcess) {
                        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                        if (h5EventHandlerService != null) {
                            h5EventHandlerService.startLiteProcessAsync();
                        }
                    }
                    if (h5LoadingManager == null) {
                        h5LoadingManager = e();
                        a(h5StartAppInfo, copyParam, h5AppProvider, h5LoadingManager, loadingType, a(h5StartAppInfo.targetAppId, syncOffline, copyParam, appInfo));
                    } else {
                        H5LoadingTypeListen h5LoadingTypeListen = H5LoadingUtil.getH5LoadingTypeListen();
                        if (h5LoadingTypeListen != null) {
                            h5LoadingTypeListen.onGetType(syncOffline, a(h5StartAppInfo.targetAppId, loadingType, copyParam, appInfo), h5StartAppInfo.targetAppId);
                        }
                    }
                    final H5LoadingManager finalH5LoadingManager = h5LoadingManager;
                    e.setDownloadTime(System.currentTimeMillis());
                    final H5AppProvider h5AppProvider2 = h5AppProvider;
                    final H5StartAppInfo h5StartAppInfo2 = h5StartAppInfo;
                    final AppInfo appInfo2 = appInfo;
                    final String str = syncUpdate;
                    final Bundle bundle = copyParam;
                    final String str2 = syncOffline;
                    final String str3 = loadingType;
                    final String str4 = url;
                    final String str5 = offMode;
                    h5AppProvider.downloadApp(h5StartAppInfo.targetAppId, appInfo.version, new H5DownloadCallback() {
                        public final void onFinish(H5DownloadRequest h5DownloadRequest, String path) {
                            a.e.setDownloadEndTime(System.currentTimeMillis());
                            a.e.setInstallTime(System.currentTimeMillis());
                            H5Log.d(a.b, "onFinish:" + path);
                            H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                                public final void run() {
                                    if (h5AppProvider2 != null) {
                                        h5AppProvider2.installApp(h5StartAppInfo2.targetAppId, appInfo2.version, (H5AppInstallCallback) new H5AppInstallCallback() {
                                            public final void onResult(boolean success, boolean isPatch) {
                                                a.e.setInstallEndTime(System.currentTimeMillis());
                                                H5Log.d(a.b, "install onResult " + success);
                                                if (success) {
                                                    a.b(h5StartAppInfo2, finalH5LoadingManager, str, bundle, appInfo2, str2, str3);
                                                } else {
                                                    a.b(str4, finalH5LoadingManager, h5StartAppInfo2, H5AppPrepareData.PREPARE_UNZIP_FAIL, appInfo2, str5);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }

                        public final void onFailed(H5DownloadRequest h5DownloadRequest, int code, String error) {
                            a.e.setDownloadEndTime(System.currentTimeMillis());
                            if (appInfo2 == null) {
                                H5Log.d(a.b, "appInfo == null");
                                return;
                            }
                            H5Log.d(a.b, "onFailed" + code + error);
                            a.b(str4, finalH5LoadingManager, h5StartAppInfo2, H5AppPrepareData.PREPARE_DOWNLOAD_FAIL, appInfo2, str5);
                        }
                    });
                    return;
                }
                boolean isInstall = h5AppProvider.isInstalled(h5StartAppInfo.targetAppId, appInfo.version);
                H5Log.d(b, "isInstall " + isInstall);
                if (!isInstall) {
                    e.setInstallTime(System.currentTimeMillis());
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
                            a.e.setInstallEndTime(System.currentTimeMillis());
                            H5Log.d(a.b, "install result:" + success);
                            if (success) {
                                a.b(h5StartAppInfo3, finalH5LoadingManager1, str6, bundle2, appInfo3, str7, str8);
                            } else {
                                a.b(str9, finalH5LoadingManager1, h5StartAppInfo3, H5AppPrepareData.PREPARE_UNZIP_FAIL, appInfo3, str10);
                            }
                        }
                    });
                    return;
                }
            } else {
                return;
            }
        } else if (!H5AppUtil.isH5AppPkg(appInfo)) {
            a(h5StartAppInfo.targetAppId, appInfo);
        }
        b(h5StartAppInfo, h5LoadingManager, syncUpdate, copyParam, appInfo, syncOffline, loadingType);
    }

    private static void a(H5StartAppInfo h5StartAppInfo, Bundle copyParams, H5AppProvider h5AppProvider, H5LoadingManager h5LoadingManager, String loadingType, int timeout) {
        String appIconUrl = TinyappUtils.getAppIcon(h5StartAppInfo.targetAppId, h5StartAppInfo.nebulaVersion, copyParams);
        String appName = TinyappUtils.getAppName(h5StartAppInfo.targetAppId, h5StartAppInfo.nebulaVersion, copyParams);
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
        h5StartAppInfo.appPrepareData = e;
        h5LoadingManager.open(a(appName, appIconUrl, slogan, h5StartAppInfo.params), h5StartAppInfo.targetAppId, loadingType, h5StartAppInfo, timeout);
        h5StartAppInfo.params.putBoolean(H5Param.LONG_PACKAGE_LOADING_SHOWN, true);
        e.setPageStatus(1);
    }

    private static void a(final String appId, final AppInfo appInfo) {
        final H5AppProvider nebulaAppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (nebulaAppProvider != null && appInfo != null) {
            boolean isInstalled = nebulaAppProvider.isInstalled(appId, appInfo.version);
            if (!isInstalled) {
                boolean isAvailable = nebulaAppProvider.isAvailable(appId, appInfo.version);
                H5Log.d(b, "offlineNebulaAppAsync App appId:" + appId + " version:" + appInfo.version + " isAvailable:" + isAvailable);
                if (!isAvailable) {
                    H5Log.d(b, "offlineNebulaAppAsync :downloadApp appId:" + appId + " version:" + appInfo.version);
                    nebulaAppProvider.downloadApp(appId, appInfo.version, new H5DownloadCallback() {
                        public final void onFinish(H5DownloadRequest h5DownloadRequest, String savePath) {
                            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                                public final void run() {
                                    a.b(appId, appInfo.version, nebulaAppProvider);
                                }
                            });
                        }
                    });
                    return;
                }
                H5Log.d(b, "offlineNebulaAppAsync App appId:" + appId + " version:" + appInfo.version + " to install ");
                b(appId, appInfo.version, nebulaAppProvider);
                a(appId, appInfo.version, isInstalled);
                return;
            }
            H5Log.d(b, "offlineNebulaAppAsync App appId:" + appId + " version:" + appInfo.version + " is install ");
        }
    }

    private static void a(String appId, String version, boolean isInstall) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_INSTALL_STATUS").param1().add("monitor", null).param3().add("appId", appId).add("version", version).add("isInstall", Boolean.valueOf(isInstall)).add("installPackageConfig", h5ConfigProvider.getConfig("h5_installPackageConfig")));
        }
    }

    /* access modifiers changed from: private */
    public static void b(final String appId, final String version, final H5AppProvider nebulaAppProvider) {
        H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
            public final void run() {
                nebulaAppProvider.installApp(appId, version);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void b(H5StartAppInfo h5StartAppInfo, H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, AppInfo appInfo, String syncOffline, String loadingType) {
        if (!h5StartAppInfo.isCloseBtnClicked) {
            try {
                if (!b(h5StartAppInfo, appInfo, h5LoadingManager, syncUpdate, copyParam, syncOffline, loadingType)) {
                    H5Log.d(b, "openApp...need");
                } else {
                    b(h5StartAppInfo, h5LoadingManager, syncUpdate, copyParam, appInfo);
                }
            } catch (Exception e2) {
                H5Log.e(b, "startApp [targetAppId] " + h5StartAppInfo.targetAppId + " failed!", e2);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(final H5StartAppInfo h5StartAppInfo, final H5LoadingManager h5LoadingManager, String syncUpdate, Bundle copyParam, AppInfo appInfo) {
        H5TinyAppDebugMode.addRecentAppForDebugMode(copyParam, appInfo);
        if (!TextUtils.equals(syncUpdate, "synctry") && !TextUtils.equals(syncUpdate, "syncforce")) {
            b(h5StartAppInfo.targetAppId, copyParam);
        }
        if (h5LoadingManager == null) {
            c(h5StartAppInfo, (H5LoadingManager) null);
        } else if (H5Utils.enableExitAndStartAppOnMain()) {
            H5Utils.runOnMain(new Runnable() {
                public final void run() {
                    H5Log.d(a.b, "exitAndStartApp runOnMain");
                    a.a(h5StartAppInfo, h5LoadingManager);
                }
            });
        } else {
            a(h5StartAppInfo, h5LoadingManager);
        }
    }

    public static void a(final H5StartAppInfo h5StartAppInfo, final H5LoadingManager h5LoadingManager) {
        long extraDelayTime;
        if (h5StartAppInfo != null) {
            if (h5LoadingManager != null) {
                H5Log.d(b, "[exitAndStartApp] h5LoadingManager status:" + h5LoadingManager.getPageStatues());
                if (h5LoadingManager.isPageDestroy()) {
                    H5Log.d(b, "[exitAndStartApp] h5LoadingManager has exit not startApp");
                    return;
                } else if (h5LoadingManager.getPageStatues() == 3) {
                    H5Log.d(b, "[exitAndStartApp] H5PageStatues.HAS_START_APP not startApp again");
                    return;
                } else if (h5LoadingManager.getPageStatues() == 2) {
                    H5Log.d(b, "[exitAndStartApp] H5PageStatues.SHOW_FAIL not startApp again");
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
                a(h5LoadingManager, animDelay);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        H5Log.d(a.b, "begin startApp openMultiProcess");
                        a.c(h5StartAppInfo, h5LoadingManager);
                    }
                }, (long) startAppDelay);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        if (h5LoadingManager.isReady()) {
                            H5Log.d(a.b, "exit");
                            h5LoadingManager.exit();
                        }
                    }
                }, 2500);
            } else if (f) {
                f = false;
                a(h5LoadingManager, 500);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        if (h5LoadingManager != null && h5LoadingManager.isReady()) {
                            H5Log.d(a.b, "exit");
                            h5LoadingManager.exit();
                        }
                    }
                }, 1000);
                H5Log.d(b, "isFirstStartApp startApp");
                c(h5StartAppInfo, h5LoadingManager);
            } else {
                a(h5LoadingManager, 500);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        if (h5LoadingManager != null && h5LoadingManager.isReady()) {
                            H5Log.d(a.b, H5PageData.FROM_TYPE_START_APP);
                            a.c(h5StartAppInfo, h5LoadingManager);
                            H5Log.d(a.b, "exit");
                            h5LoadingManager.exit();
                        }
                    }
                }, 1000);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void c(final H5StartAppInfo h5StartAppInfo, H5LoadingManager h5LoadingManager) {
        boolean enableOuter;
        boolean enableInner;
        boolean enableNoAppinfo;
        if (h5LoadingManager == null || h5LoadingManager.getPageStatues() != 2) {
            e.uploadPrepareLog("finish", "1");
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
            H5Log.d(b, "H5AppHandler.startApp enableOuter " + enableOuter + " enableInner " + enableInner + " enableNoAppinfo " + enableNoAppinfo);
            if (enableOuter || enableInner || enableNoAppinfo) {
                if (h5LoadingManager == null) {
                    H5Log.d(b, "put needAnimInTiny true");
                    h5StartAppInfo.params.putBoolean("needAnimInTiny", true);
                } else {
                    H5Log.d(b, "put needAnimInTiny false");
                    h5StartAppInfo.params.putBoolean("needAnimInTiny", false);
                }
            }
            if (H5Utils.isMain()) {
                H5Utils.getExecutor(H5ThreadType.URGENT_DISPLAY).execute(new Runnable() {
                    public final void run() {
                        a.c(h5StartAppInfo);
                    }
                });
            } else {
                c(h5StartAppInfo);
            }
        } else {
            H5Log.d(b, "pageStatues.currentPageStatues == PageStatues.SHOW_FAIL not startApp again");
        }
    }

    /* access modifiers changed from: private */
    public static void c(H5StartAppInfo h5StartAppInfo) {
        H5Log.d(b, "startApp " + h5StartAppInfo.targetAppId + " nebulaAppType:" + h5StartAppInfo.nebulaAppType);
        h5StartAppInfo.params.putString(H5AppHandler.CHECK_KEY, "yes");
        H5NebulaAppCacheManager.putAppType(h5StartAppInfo.targetAppId, h5StartAppInfo);
        if (h5StartAppInfo.params.containsKey(H5Param.NEBULA_LOADING_VERSION)) {
            h5StartAppInfo.params.remove(H5Param.NEBULA_LOADING_VERSION);
        }
        h5StartAppInfo.params.putString(H5Param.NEBULA_LOADING_VERSION, h5StartAppInfo.targetAppId + "_" + h5StartAppInfo.nebulaVersion);
        H5LoadingApp.remove(h5StartAppInfo.targetAppId);
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(h5StartAppInfo.sourceAppId, h5StartAppInfo.targetAppId, h5StartAppInfo.params, h5StartAppInfo.sceneParams, null);
    }

    private static void a(final H5LoadingManager loadingManager, final H5StartAppInfo h5StartAppInfo, String reason, AppInfo appInfo, String offMode) {
        if ((H5AppPrepareData.PREPARE_DOWNLOAD_FAIL.equals(reason) || H5AppPrepareData.PREPARE_UNZIP_FAIL.equals(reason)) && "try".equals(offMode)) {
            H5AppHandlerUtil.tryFallback(appInfo, new FallbackResult() {
                public final void onResult(boolean result, String reason) {
                    H5Log.d(a.b, "tryFallback " + result + " reason:" + reason);
                    if (result) {
                        h5StartAppInfo.params.remove(H5Param.NEBULA_FORCE_OFFLINE);
                        a.a(h5StartAppInfo, loadingManager);
                        return;
                    }
                    a.b(h5StartAppInfo, reason, loadingManager);
                }
            });
        } else {
            b(h5StartAppInfo, reason, loadingManager);
        }
    }

    /* access modifiers changed from: private */
    public static void b(H5StartAppInfo h5StartAppInfo, String errorCode, final H5LoadingManager loadingManager) {
        h5StartAppInfo.appPrepareData.uploadPrepareLog("finish", errorCode);
        H5DevAppList.getInstance().setUseDevMode(h5StartAppInfo.targetAppId, false);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public final void run() {
                if (loadingManager == null || !loadingManager.isReady()) {
                    H5Log.d(a.b, "h5page is null");
                    H5Utils.runOnMain(new Runnable() {
                        public final void run() {
                            if (loadingManager == null || !loadingManager.isReady()) {
                                H5Log.d(a.b, "h5page is null not try");
                                return;
                            }
                            H5Log.d(a.b, "sendToWebFail again");
                            loadingManager.sendToWebFail();
                        }
                    }, 1000);
                    return;
                }
                H5Log.d(a.b, "sendToWebFail");
                loadingManager.sendToWebFail();
            }
        }, 1000);
    }

    private static int a(String appId, String type, Bundle appStartParam, AppInfo appInfo) {
        return H5LoadingUtil.getTimeout(appId, type, appStartParam, appInfo);
    }

    private static H5LoadingManager e() {
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
