package com.alipay.mobile.tinyappcustom.process;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.cityselect.impl.H5PickCityPlugin;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.alipay.mobile.beehive.compositeui.app.H5BeehiveViewPlugin;
import com.alipay.mobile.beehive.plugin.H5PhotoPlugin;
import com.alipay.mobile.beehive.plugins.capture.CaptureForIndustryPlugin;
import com.alipay.mobile.beehive.plugins.imageedit.ImageEditPlugin;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.common.transport.http.inner.CoreHttpManager;
import com.alipay.mobile.common.transport.utils.ZURLEncodedUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5LoadingView;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.ConfigPlugin;
import com.alipay.mobile.h5plugin.H5LocationPlugin;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcess;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.liteprocess.ipc.IpcCallClient;
import com.alipay.mobile.liteprocess.ipc.IpcCallServer;
import com.alipay.mobile.liteprocess.ipc.IpcMsgClient;
import com.alipay.mobile.liteprocess.ipc.IpcMsgServer;
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ChannelProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5LoadingViewProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5SharePanelProvider;
import com.alipay.mobile.nebula.provider.H5SimpleRpcProvider;
import com.alipay.mobile.nebula.provider.H5ThreadPoolProvider;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandler;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.alipay.mobile.tinyappcustom.h5plugin.H5MiniProgramNavigationPlugin;
import com.alipay.multimedia.js.image.H5CompressImagePlugin;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.mpaas.nebula.plugin.H5ServerTimePlugin;
import com.mpaas.nebula.plugin.H5ServicePlugin;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class H5EventHandlerServiceImpl extends H5EventHandlerService {
    private static final Set<String> a;
    private static String d = null;
    public static final String tradePay = "tradePay";
    private H5IpcServer b;
    private Bundle c;

    static {
        HashSet hashSet = new HashSet();
        a = hashSet;
        hashSet.add(H5PhotoPlugin.IMAGE_VIEWER);
        a.add(H5LocationPlugin.GET_LOCATION);
        a.add(H5ServerTimePlugin.GET_SERVER_TIME);
        a.add("scan");
        a.add(H5PickCityPlugin.GET_CITIES);
        a.add(SelectCityActivity.EXTRA_PARAM_SET_LOCATED_CITY);
        a.add(H5ContactPlugin.CONTACT);
        a.add("chooseContact");
        a.add("chooseVideo");
        a.add("chooseImage");
        a.add(tradePay);
        a.add("photo");
        a.add(H5PageData.FROM_TYPE_START_APP);
        a.add("pushBizWindow");
        a.add("startBizService");
        a.add("saveBizServiceResult");
        a.add(H5CompressImagePlugin.ACTION_COMPRESS_IMAGE);
        a.add("mtop");
        a.add(CommonEvents.SET_AP_DATA_STORAGE);
        a.add(CommonEvents.GET_AP_DATA_STORAGE);
        a.add(CommonEvents.REMOVE_AP_DATA_STORAGE);
        a.add(CommonEvents.CLEAR_AP_DATA_STORAGE);
        a.add(CommonEvents.RESTART_TINY_APP);
        a.add(TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE);
        a.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE);
        a.add(TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE);
        a.add(TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE);
        a.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE_INFO);
        a.add(ConfigPlugin.ACTION);
        a.add(H5BeehiveViewPlugin.MULTILEVEL_SELECT);
        a.add("getClientInfo");
        a.add(H5ServicePlugin.CHECK_APP);
        a.add("uploadImage");
        a.add("downloadImage");
        a.add(H5ServicePlugin.GET_CONFIG);
        a.add("getAppInfo");
        a.add("getUserInfo");
        a.add(H5ServicePlugin.GET_THIRD_PARTY_AUTH_CODE);
        a.add("getAppToken");
        a.add("verifyIdentity");
        a.add("mtBizReport");
        a.add("commonList");
        a.add("beehiveGetPOI");
        a.add("nfch5plugin");
        a.add(CommonEvents.GET_MTOP_TOKEN);
        a.add("getQRCodeImage");
        a.add("FalconAIRec");
        a.add("FalconAIModify");
        a.add(H5LocationPlugin.GET_CURRENT_LOCATION);
        a.add("getLifestyleInfo");
        a.add("addFollow");
        a.add(H5MiniProgramNavigationPlugin.NAVIGATE_TO_MINI_PROGRAM);
        a.add("APSocialNebulaPlugin.selectContactJSAPI");
        a.add("APSocialNebulaPlugin.mockChatMessage");
        a.add("APSocialNebulaPlugin.queryAndSelectAccount");
        a.add("APSocialNebulaPlugin.queryExistingAccounts");
        a.add("mssdk.Security.getModuleState");
        a.add("mssdk.Security.setProtectState");
        a.add("mssdk.Security.getProtectState");
        a.add("mssdk.Security.checkDeviceRiskSync");
        a.add("getLoginToken");
        a.add("aliAutoLogin");
        a.add(CaptureForIndustryPlugin.ACTION_CAPTURE_FOR_INDUSTRY);
        a.add("navigateToAlipayPage");
        a.add("playBackgroundAudio");
        a.add("pauseBackgroundAudio");
        a.add("stopBackgroundAudio");
        a.add("seekBackgroundAudio");
        a.add("getBackgroundAudioPlayerState");
        a.add("startMonitorBackgroundAudio");
        a.add("stopMonitorBackgroundAudio");
        a.add("getBackgroundAudioOption");
        a.add("setBackgroundAudioOption");
        a.add("getCdpSpaceInfo");
        a.add("getCdpSpaceInfos");
        a.add("cdpFeedback");
        a.add("cdpFeedbackForServer");
        a.add("cdpRemoveView");
        a.add("addH5Notice");
        a.add("removeH5Notice");
        a.add("updateSpaceInfosForBiz");
        a.add("addLocalSpceInfo");
        a.add("removeLocalSpceInfo");
        a(a);
        a.add("registerUpdateManager");
        a.add(ZIMH5Plugin.ZIM_IDENTIFY);
        a.add("addFollow");
        a.add("removeFollow");
        a.add("getPPFollowStatus");
        a.add(CommonEvents.SET_SHARE_DATA);
        a.add(CommonEvents.GET_SHARE_DATA);
        a.add("shouldShowAddComponent");
        a.add("addToHomeWithComponent");
        a.add("closeAddComponentAction");
        a.add("questionaireApp2HomeShow");
        a.add(ImageEditPlugin.ACTION_IMAGE_EDIT);
        a.add(TinyAppStoragePlugin.SEND_TINY_LOCAL_STORAGE_TO_IDE);
    }

    private static void a(Set<String> multiProcessSet) {
        ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            String value = configService.getConfig("h5_main_process_invoke_list");
            if (!TextUtils.isEmpty(value)) {
                JSONArray jsonArray = H5Utils.parseArray(value);
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    Iterator<Object> it = jsonArray.iterator();
                    while (it.hasNext()) {
                        Object data = it.next();
                        if (data instanceof String) {
                            multiProcessSet.add((String) data);
                        }
                    }
                }
            }
        }
    }

    public boolean enableHandler(String action) {
        return a.contains(action) && H5Utils.isInTinyProcess();
    }

    public void handlerAction(H5Event h5Event, H5BridgeContext h5BridgeContext) {
    }

    public void registerRspBizHandler(String biz, Handler handler) {
        IpcMsgClient.registerRspBizHandler(biz, handler);
    }

    public void unregisterRspBizHandler(String biz) {
        IpcMsgClient.unregisterRspBizHandler(biz);
    }

    public void registerLiteProcessActivityClass(Class clazz, int lpid, boolean needHookBackKey) {
        LiteProcessApi.registerLiteProcessActivityClass(clazz, lpid, needHookBackKey);
    }

    public void registerReqBizHandler(String biz, Handler bizHandler) {
        IpcMsgServer.registerReqBizHandler(biz, bizHandler);
    }

    public void reply(Messenger messenger, String biz, Message message) {
        IpcMsgServer.reply(messenger, biz, message);
    }

    public void prepare() {
        IpcMsgClient.prepare();
    }

    public String getMultiProcessTag() {
        return Const.KEY_LITE_PROCESS_ID;
    }

    public void clientSenMsg(String biz, Message bizMsg) {
        IpcMsgClient.send(biz, bizMsg);
    }

    public void registerServiceBean(String interfaceClassName, Object objImpl) {
        IpcCallServer.registerServiceBean(interfaceClassName, objImpl);
    }

    public <T> T getIpcProxy(Class<T> t) {
        try {
            return IpcCallClient.getIpcProxy(t);
        } catch (Exception e) {
            H5Log.e((String) "H5EventHandlerImpl", (Throwable) e);
            return null;
        }
    }

    public void moveTaskToBack(Object activity) {
        LiteProcessApi.moveTaskToBack(activity);
    }

    public boolean moveTaskToFront(Activity activity, boolean fromForeground, Bundle bundle) {
        RunningTaskInfo info;
        ActivityManager activityManager = (ActivityManager) Util.getContext().getSystemService(WidgetType.ACTIVITY);
        List recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if (recentTasks == null) {
            return false;
        }
        ComponentName componentName = activity.getComponentName();
        RunningTaskInfo targetInfo = null;
        Iterator<RunningTaskInfo> it = recentTasks.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            info = it.next();
            if (!info.baseActivity.equals(componentName)) {
                if (info.topActivity.equals(componentName)) {
                    break;
                }
            } else {
                break;
            }
        }
        targetInfo = info;
        if (targetInfo == null) {
            return false;
        }
        Activity topActivity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
        if (topActivity == null || topActivity == activity) {
            fromForeground = false;
        }
        try {
            if (recentTasks.get(0).baseActivity.getClassName().contains("H5Activity$")) {
                fromForeground = false;
            }
        } catch (Throwable t) {
            H5Log.e("H5EventHandlerImpl", "moveTaskToFront", t);
        }
        Util.moveTaskToFront(activityManager, topActivity, targetInfo, true, fromForeground, bundle, false);
        return true;
    }

    public void stopSelfProcess() {
        LiteProcessApi.stopSelfInClient();
    }

    public int getLitePid() {
        return LiteProcessApi.getLpid();
    }

    public void setStartParams(Bundle bundle) {
        this.c = bundle;
        try {
            if (H5Utils.getBoolean(bundle, (String) "isTinyApp", false)) {
                a(bundle);
            }
        } catch (Throwable throwable) {
            H5Log.e((String) "H5EventHandlerImpl", throwable);
        }
    }

    private static void a(Bundle bundle) {
        long time = System.currentTimeMillis();
        String appId = H5Utils.getString(bundle, (String) "appId");
        String version = H5Utils.getString(bundle, (String) "appVersion");
        String value = appId + "_" + version + "_" + H5Utils.getString(bundle, (String) "release_type");
        LoggerFactory.getLogContext().putBizExternParams("appUniqueId", value);
        NativeCrashHandlerApi.addCrashHeadInfo("appUniqueId", value);
        H5Log.d("H5EventHandlerImpl", "setLogVersion " + value + " cost:" + (System.currentTimeMillis() - time));
    }

    public Bundle getStartParams() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        H5Log.d("H5EventHandlerImpl", "onCreate " + this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public void sendDataToTinyProcess(String appId, Bundle bundle) {
        try {
            H5Log.d("H5EventHandlerImpl", "appId " + appId);
            LiteProcess liteProcess = LiteProcessApi.findProcessByAppId(appId);
            if (liteProcess == null) {
                H5Log.d("H5EventHandlerImpl", "appId " + appId);
                return;
            }
            Message message = Message.obtain();
            message.setData(bundle);
            reply(liteProcess.getReplyTo(), H5EventHandler.BIZ, message);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5EventHandlerImpl", throwable);
        }
    }

    public void sendDataToTinyProcessWithMsgType(String appId, Bundle bundle, int type) {
        try {
            H5Log.d("H5EventHandlerImpl", "appId " + appId);
            LiteProcess liteProcess = LiteProcessApi.findProcessByAppId(appId);
            if (liteProcess == null) {
                H5Log.d("H5EventHandlerImpl", "appId " + appId);
                return;
            }
            Message message = Message.obtain();
            message.setData(bundle);
            reply(liteProcess.getReplyTo(), H5EventHandler.BIZ, message);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5EventHandlerImpl", throwable);
        }
    }

    public H5IpcServer getH5IpcServerImpl() {
        return f();
    }

    public void stopLiteProcessByAppIdInServer(String appId) {
        try {
            LiteProcessApi.stopLiteProcessByAppIdInServer(appId);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5EventHandlerImpl", throwable);
        }
    }

    public void moveTaskToBackAndStop(final Activity activity, boolean keepAlive) {
        if (activity != null && !activity.isFinishing()) {
            moveTaskToBack(activity);
            if (!keepAlive && H5Utils.isInTinyProcess()) {
                new Handler(activity.getMainLooper()).postDelayed(new Runnable() {
                    public final void run() {
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                        H5EventHandlerServiceImpl.this.stopSelfProcess();
                    }
                }, 500);
            }
        }
    }

    public boolean isAllLiteProcessHide() {
        return LiteProcessApi.isAllLiteProcessHide();
    }

    private static boolean e() {
        ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
        if (configService == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(configService.getConfig("h5_use_tinyLoadingView"))) {
            return true;
        }
        return false;
    }

    public void showTinyLoadingView(boolean show) {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            H5Log.e((String) "H5EventHandlerImpl", (String) "h5Service == null");
        } else if (!show || !e()) {
            h5Service.getProviderManager().setProvider(H5LoadingViewProvider.class.getName(), new H5LoadingView());
        }
    }

    private synchronized H5IpcServer f() {
        return this.b;
    }

    /* JADX WARNING: type inference failed for: r35v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r31v1 */
    /* JADX WARNING: type inference failed for: r39v0 */
    /* JADX WARNING: type inference failed for: r39v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r39v2 */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.io.InputStream] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.nebula.process.H5HttpRequestResult httpRequest(java.lang.String r50, java.lang.String r51, java.util.Map<java.lang.String, java.lang.String> r52, byte[] r53, long r54, java.lang.String r56, java.lang.String r57, boolean r58, com.alipay.mobile.h5container.api.H5Page r59) {
        /*
            r49 = this;
            com.alipay.mobile.common.transport.h5.H5HttpUrlRequest r6 = new com.alipay.mobile.common.transport.h5.H5HttpUrlRequest     // Catch:{ Throwable -> 0x0058 }
            r0 = r50
            r6.<init>(r0)     // Catch:{ Throwable -> 0x0058 }
            r0 = r51
            r6.setRequestMethod(r0)     // Catch:{ Throwable -> 0x0058 }
            java.util.Set r4 = r52.keySet()     // Catch:{ Throwable -> 0x0058 }
            java.util.Iterator r5 = r4.iterator()     // Catch:{ Throwable -> 0x0058 }
        L_0x0014:
            boolean r4 = r5.hasNext()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x00ad
            java.lang.Object r38 = r5.next()     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r38 = (java.lang.String) r38     // Catch:{ Throwable -> 0x0058 }
            r0 = r52
            r1 = r38
            java.lang.Object r4 = r0.get(r1)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r44 = a(r4)     // Catch:{ Throwable -> 0x0058 }
            r0 = r38
            r1 = r44
            r6.addHeader(r0, r1)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r8 = "request headers "
            r7.<init>(r8)     // Catch:{ Throwable -> 0x0058 }
            r0 = r38
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r8 = " "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x0058 }
            r0 = r44
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r7)     // Catch:{ Throwable -> 0x0058 }
            goto L_0x0014
        L_0x0058:
            r28 = move-exception
            java.lang.String r4 = "h5_httpRequest_exception"
            com.alipay.mobile.nebula.log.H5LogData r4 = com.alipay.mobile.nebula.log.H5LogData.seedId(r4)
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.param1()
            r5 = 0
            r0 = r50
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.add(r0, r5)
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.param2()
            java.lang.String r5 = "httpRequest请求异常"
            r7 = 0
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.add(r5, r7)
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.param4()
            java.lang.String r5 = r28.toString()
            r7 = 0
            com.alipay.mobile.nebula.log.H5LogData r4 = r4.add(r5, r7)
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r4)
            java.lang.String r4 = "H5EventHandlerImpl"
            r0 = r28
            com.alipay.mobile.nebula.util.H5Log.e(r4, r0)
            com.alipay.mobile.nebula.process.H5HttpRequestResult r32 = new com.alipay.mobile.nebula.process.H5HttpRequestResult
            r32.<init>()
            r4 = 12
            r0 = r32
            r0.responseStatues = r4
            com.alibaba.fastjson.JSONArray r4 = new com.alibaba.fastjson.JSONArray
            r4.<init>()
            r0 = r32
            r0.responseHeader = r4
            java.lang.String r4 = ""
            r0 = r32
            r0.responseContext = r4
            r4 = 12
            r0 = r32
            r0.error = r4
        L_0x00ac:
            return r32
        L_0x00ad:
            java.lang.String r41 = ""
            boolean r4 = com.alipay.mobile.nebula.util.H5Utils.isInTinyProcess()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x00d1
            java.lang.String r4 = d     // Catch:{ Throwable -> 0x0058 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x02c7
            java.lang.Class<com.alipay.mobile.nebula.provider.H5LoginProvider> r4 = com.alipay.mobile.nebula.provider.H5LoginProvider.class
            java.lang.String r4 = r4.getName()     // Catch:{ Throwable -> 0x0058 }
            java.lang.Object r33 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r4)     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.nebula.provider.H5LoginProvider r33 = (com.alipay.mobile.nebula.provider.H5LoginProvider) r33     // Catch:{ Throwable -> 0x0058 }
            if (r33 == 0) goto L_0x00d1
            java.lang.String r41 = r33.getUserId()     // Catch:{ Throwable -> 0x0058 }
            d = r41     // Catch:{ Throwable -> 0x0058 }
        L_0x00d1:
            boolean r4 = android.text.TextUtils.isEmpty(r41)     // Catch:{ Throwable -> 0x0058 }
            if (r4 != 0) goto L_0x00de
            java.lang.String r4 = "su584userid"
            r0 = r41
            r6.addHeader(r4, r0)     // Catch:{ Throwable -> 0x0058 }
        L_0x00de:
            java.lang.String r4 = "su584channelapplet"
            java.lang.String r5 = "Y"
            r6.addHeader(r4, r5)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r4 = "h5_app_type"
            java.lang.String r5 = "mini_app"
            r6.addTags(r4, r5)     // Catch:{ Throwable -> 0x0058 }
            r0 = r53
            r6.setReqData(r0)     // Catch:{ Throwable -> 0x0058 }
            java.util.UUID r4 = java.util.UUID.randomUUID()     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r9 = r4.toString()     // Catch:{ Throwable -> 0x0058 }
            r4 = 1
            r6.setAsyncMonitorLog(r4)     // Catch:{ Throwable -> 0x0058 }
            if (r58 == 0) goto L_0x02cb
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r7 = "linkType SPDY_LINK: "
            r5.<init>(r7)     // Catch:{ Throwable -> 0x0058 }
            r0 = r50
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Throwable -> 0x0058 }
            r4 = 1
            r6.linkType = r4     // Catch:{ Throwable -> 0x0058 }
        L_0x0118:
            java.lang.String r20 = ""
            if (r59 == 0) goto L_0x012a
            com.alipay.mobile.h5container.api.H5PageData r4 = r59.getPageData()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x012a
            com.alipay.mobile.h5container.api.H5PageData r4 = r59.getPageData()     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r20 = r4.getAppId()     // Catch:{ Throwable -> 0x0058 }
        L_0x012a:
            boolean r4 = com.alipay.mobile.tinyappcommon.TinyappUtils.isRemoteDebugConnected(r20)     // Catch:{ Throwable -> 0x0058 }
            if (r4 != 0) goto L_0x0136
            boolean r4 = com.alipay.mobile.tinyappcommon.TinyappUtils.isVConsolePanelOpened()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x014c
        L_0x0136:
            r10 = r20
            java.lang.String r4 = "IO"
            java.util.concurrent.ThreadPoolExecutor r11 = com.alipay.mobile.nebula.util.H5Utils.getExecutor(r4)     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl$2 r4 = new com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl$2     // Catch:{ Throwable -> 0x0058 }
            r5 = r49
            r7 = r51
            r8 = r50
            r4.<init>(r6, r7, r8, r9, r10)     // Catch:{ Throwable -> 0x0058 }
            r11.execute(r4)     // Catch:{ Throwable -> 0x0058 }
        L_0x014c:
            long r42 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.common.transport.h5.H5NetworkManager r4 = new com.alipay.mobile.common.transport.h5.H5NetworkManager     // Catch:{ Throwable -> 0x0058 }
            android.content.Context r5 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ Throwable -> 0x0058 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0058 }
            java.util.concurrent.Future r29 = r4.enqueue(r6)     // Catch:{ Throwable -> 0x0058 }
            r4 = 0
            int r4 = (r54 > r4 ? 1 : (r54 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x030e
            r4 = 30000(0x7530, double:1.4822E-319)
            int r4 = (r54 > r4 ? 1 : (r54 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x030e
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e6 }
            java.lang.String r7 = "timeout "
            r5.<init>(r7)     // Catch:{ Throwable -> 0x02e6 }
            r0 = r54
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x02e6 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x02e6 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Throwable -> 0x02e6 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x02e6 }
            r0 = r29
            r1 = r54
            java.lang.Object r34 = r0.get(r1, r4)     // Catch:{ Throwable -> 0x02e6 }
            com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r34 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r34     // Catch:{ Throwable -> 0x02e6 }
        L_0x018b:
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r7 = "httpRequest timeCost h5HttpUrlRequest "
            r5.<init>(r7)     // Catch:{ Throwable -> 0x0058 }
            long r46 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0058 }
            long r46 = r46 - r42
            r0 = r46
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r7 = " "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Throwable -> 0x0058 }
            r0 = r50
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Throwable -> 0x0058 }
            com.alibaba.fastjson.JSONArray r36 = new com.alibaba.fastjson.JSONArray     // Catch:{ Throwable -> 0x0058 }
            r36.<init>()     // Catch:{ Throwable -> 0x0058 }
            com.alibaba.fastjson.JSONObject r13 = new com.alibaba.fastjson.JSONObject     // Catch:{ Throwable -> 0x0058 }
            r13.<init>()     // Catch:{ Throwable -> 0x0058 }
            r30 = 0
            if (r34 == 0) goto L_0x0316
            com.alipay.mobile.common.transport.http.HttpUrlHeader r4 = r34.getHeader()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x0316
            com.alipay.mobile.common.transport.http.HttpUrlHeader r4 = r34.getHeader()     // Catch:{ Throwable -> 0x0058 }
            java.util.Map r40 = r4.toMultimap()     // Catch:{ Throwable -> 0x0058 }
            java.util.Set r4 = r40.keySet()     // Catch:{ Throwable -> 0x0058 }
            java.util.Iterator r5 = r4.iterator()     // Catch:{ Throwable -> 0x0058 }
        L_0x01d7:
            boolean r4 = r5.hasNext()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x0316
            java.lang.Object r38 = r5.next()     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r38 = (java.lang.String) r38     // Catch:{ Throwable -> 0x0058 }
            r0 = r40
            r1 = r38
            java.lang.Object r45 = r0.get(r1)     // Catch:{ Throwable -> 0x0058 }
            java.util.List r45 = (java.util.List) r45     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r4 = "Content-Encoding"
            r0 = r38
            boolean r23 = r4.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x0058 }
            java.util.Iterator r7 = r45.iterator()     // Catch:{ Throwable -> 0x0058 }
        L_0x01f9:
            boolean r4 = r7.hasNext()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x01d7
            java.lang.Object r44 = r7.next()     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r44 = (java.lang.String) r44     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r11 = "handleResponse headers "
            r8.<init>(r11)     // Catch:{ Throwable -> 0x0058 }
            r0 = r38
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r11 = " "
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ Throwable -> 0x0058 }
            r0 = r44
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r8)     // Catch:{ Throwable -> 0x0058 }
            if (r23 == 0) goto L_0x0235
            java.lang.String r4 = "gzip"
            r0 = r44
            boolean r4 = r4.equalsIgnoreCase(r0)     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x0235
            r30 = 1
        L_0x0235:
            com.alibaba.fastjson.JSONObject r37 = new com.alibaba.fastjson.JSONObject     // Catch:{ Throwable -> 0x0058 }
            r4 = 1
            r0 = r37
            r0.<init>(r4)     // Catch:{ Throwable -> 0x0058 }
            r0 = r37
            r1 = r38
            r2 = r44
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0058 }
            r0 = r38
            r1 = r44
            r13.put(r0, r1)     // Catch:{ Throwable -> 0x0058 }
            r36.add(r37)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r4 = "set-cookie"
            r0 = r38
            boolean r4 = r0.equalsIgnoreCase(r4)     // Catch:{ Throwable -> 0x02bb }
            if (r4 == 0) goto L_0x01f9
            long r24 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02bb }
            android.os.Bundle r4 = r59.getParams()     // Catch:{ Throwable -> 0x02bb }
            r0 = r50
            r1 = r44
            com.alipay.mobile.nebula.util.H5CookieUtil.setCookie(r4, r0, r1)     // Catch:{ Throwable -> 0x02bb }
            long r46 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02bb }
            long r26 = r46 - r24
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02bb }
            java.lang.String r11 = "httpRequest timeCost setCookie "
            r8.<init>(r11)     // Catch:{ Throwable -> 0x02bb }
            r0 = r26
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x02bb }
            java.lang.String r11 = " "
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ Throwable -> 0x02bb }
            r0 = r50
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x02bb }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x02bb }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r8)     // Catch:{ Throwable -> 0x02bb }
            java.lang.Class<com.alipay.mobile.nebula.provider.H5LogProvider> r4 = com.alipay.mobile.nebula.provider.H5LogProvider.class
            java.lang.String r4 = r4.getName()     // Catch:{ Throwable -> 0x02bb }
            java.lang.Object r4 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r4)     // Catch:{ Throwable -> 0x02bb }
            com.alipay.mobile.nebula.provider.H5LogProvider r4 = (com.alipay.mobile.nebula.provider.H5LogProvider) r4     // Catch:{ Throwable -> 0x02bb }
            if (r4 == 0) goto L_0x01f9
            if (r59 == 0) goto L_0x01f9
            com.alipay.mobile.h5container.api.H5PageData r4 = r59.getPageData()     // Catch:{ Throwable -> 0x02bb }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02bb }
            java.lang.String r11 = "^setCookie="
            r8.<init>(r11)     // Catch:{ Throwable -> 0x02bb }
            r0 = r26
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Throwable -> 0x02bb }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x02bb }
            r4.appendJsApiPerExtra(r8)     // Catch:{ Throwable -> 0x02bb }
            goto L_0x01f9
        L_0x02bb:
            r28 = move-exception
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.String r8 = "exception detail"
            r0 = r28
            com.alipay.mobile.nebula.util.H5Log.e(r4, r8, r0)     // Catch:{ Throwable -> 0x0058 }
            goto L_0x01f9
        L_0x02c7:
            java.lang.String r41 = d     // Catch:{ Throwable -> 0x0058 }
            goto L_0x00d1
        L_0x02cb:
            java.lang.String r4 = "H5EventHandlerImpl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r7 = "linkType HTTP_LINK: "
            r5.<init>(r7)     // Catch:{ Throwable -> 0x0058 }
            r0 = r50
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.nebula.util.H5Log.d(r4, r5)     // Catch:{ Throwable -> 0x0058 }
            r4 = 2
            r6.linkType = r4     // Catch:{ Throwable -> 0x0058 }
            goto L_0x0118
        L_0x02e6:
            r4 = move-exception
            java.lang.String r4 = "TimeoutException"
            r6.cancel(r4)     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.nebula.process.H5HttpRequestResult r32 = new com.alipay.mobile.nebula.process.H5HttpRequestResult     // Catch:{ Throwable -> 0x0058 }
            r32.<init>()     // Catch:{ Throwable -> 0x0058 }
            r4 = 13
            r0 = r32
            r0.responseStatues = r4     // Catch:{ Throwable -> 0x0058 }
            com.alibaba.fastjson.JSONArray r4 = new com.alibaba.fastjson.JSONArray     // Catch:{ Throwable -> 0x0058 }
            r4.<init>()     // Catch:{ Throwable -> 0x0058 }
            r0 = r32
            r0.responseHeader = r4     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r4 = "请求超时异常"
            r0 = r32
            r0.responseContext = r4     // Catch:{ Throwable -> 0x0058 }
            r4 = 13
            r0 = r32
            r0.error = r4     // Catch:{ Throwable -> 0x0058 }
            goto L_0x00ac
        L_0x030e:
            java.lang.Object r34 = r29.get()     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r34 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r34     // Catch:{ Throwable -> 0x0058 }
            goto L_0x018b
        L_0x0316:
            r31 = 0
            java.io.InputStream r35 = r34.getInputStream()     // Catch:{ Throwable -> 0x0058 }
            if (r30 == 0) goto L_0x0327
            java.util.zip.GZIPInputStream r31 = new java.util.zip.GZIPInputStream     // Catch:{ Throwable -> 0x0058 }
            r0 = r31
            r1 = r35
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0058 }
        L_0x0327:
            if (r31 == 0) goto L_0x038e
            r39 = r31
        L_0x032b:
            int r15 = r34.getCode()     // Catch:{ Throwable -> 0x0058 }
            byte[] r18 = com.alipay.mobile.nebula.util.H5Utils.readBytes(r39)     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r21 = ""
            java.lang.String r4 = "base64"
            r0 = r56
            boolean r4 = r4.equals(r0)     // Catch:{ UnsupportedEncodingException -> 0x03b1 }
            if (r4 == 0) goto L_0x0391
            r4 = 2
            r0 = r18
            java.lang.String r21 = android.util.Base64.encodeToString(r0, r4)     // Catch:{ UnsupportedEncodingException -> 0x03b1 }
        L_0x0346:
            boolean r4 = com.alipay.mobile.tinyappcommon.TinyappUtils.isRemoteDebugConnected(r20)     // Catch:{ Throwable -> 0x0058 }
            if (r4 != 0) goto L_0x0352
            boolean r4 = com.alipay.mobile.tinyappcommon.TinyappUtils.isVConsolePanelOpened()     // Catch:{ Throwable -> 0x0058 }
            if (r4 == 0) goto L_0x0372
        L_0x0352:
            org.apache.http.StatusLine r4 = r34.getStatusLine()     // Catch:{ Throwable -> 0x0058 }
            java.lang.String r16 = r4.toString()     // Catch:{ Throwable -> 0x0058 }
            r10 = r20
            java.lang.String r4 = "IO"
            java.util.concurrent.ThreadPoolExecutor r4 = com.alipay.mobile.nebula.util.H5Utils.getExecutor(r4)     // Catch:{ Throwable -> 0x0058 }
            com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl$3 r11 = new com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl$3     // Catch:{ Throwable -> 0x0058 }
            r12 = r49
            r14 = r50
            r17 = r9
            r19 = r10
            r11.<init>(r13, r14, r15, r16, r17, r18, r19)     // Catch:{ Throwable -> 0x0058 }
            r4.execute(r11)     // Catch:{ Throwable -> 0x0058 }
        L_0x0372:
            com.alipay.mobile.nebula.process.H5HttpRequestResult r32 = new com.alipay.mobile.nebula.process.H5HttpRequestResult     // Catch:{ Throwable -> 0x0058 }
            r32.<init>()     // Catch:{ Throwable -> 0x0058 }
            r0 = r32
            r0.responseStatues = r15     // Catch:{ Throwable -> 0x0058 }
            r0 = r36
            r1 = r32
            r1.responseHeader = r0     // Catch:{ Throwable -> 0x0058 }
            r0 = r21
            r1 = r32
            r1.responseContext = r0     // Catch:{ Throwable -> 0x0058 }
            r4 = 0
            r0 = r32
            r0.error = r4     // Catch:{ Throwable -> 0x0058 }
            goto L_0x00ac
        L_0x038e:
            r39 = r35
            goto L_0x032b
        L_0x0391:
            boolean r4 = android.text.TextUtils.isEmpty(r57)     // Catch:{ UnsupportedEncodingException -> 0x03b1 }
            if (r4 != 0) goto L_0x03a5
            java.lang.String r22 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x03b1 }
            r0 = r22
            r1 = r18
            r2 = r57
            r0.<init>(r1, r2)     // Catch:{ UnsupportedEncodingException -> 0x03b1 }
            r21 = r22
            goto L_0x0346
        L_0x03a5:
            java.lang.String r22 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x03b1 }
            r0 = r22
            r1 = r18
            r0.<init>(r1)     // Catch:{ UnsupportedEncodingException -> 0x03b1 }
            r21 = r22
            goto L_0x0346
        L_0x03b1:
            r4 = move-exception
            java.lang.String r4 = r34.getContentType()     // Catch:{ Throwable -> 0x0058 }
            r0 = r49
            r1 = r20
            r2 = r56
            r3 = r59
            r0.a(r1, r4, r2, r3)     // Catch:{ Throwable -> 0x0058 }
            goto L_0x0346
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl.httpRequest(java.lang.String, java.lang.String, java.util.Map, byte[], long, java.lang.String, java.lang.String, boolean, com.alipay.mobile.h5container.api.H5Page):com.alipay.mobile.nebula.process.H5HttpRequestResult");
    }

    private void a(String appId, String contentType, String responseType, H5Page h5Page) {
        if (TinyappUtils.isRemoteDebugConnected(appId) || TinyappUtils.isVConsolePanelOpened()) {
            final String str = contentType;
            final String str2 = responseType;
            final H5Page h5Page2 = h5Page;
            final String str3 = appId;
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    JSONObject remoteMsg = new JSONObject();
                    remoteMsg.put((String) "category", (Object) "error");
                    remoteMsg.put((String) H5TinyAppLogUtil.TINY_APP_STANDARD_LOGID, (Object) "JSAPI_httpRequest_14");
                    remoteMsg.put((String) "message", (Object) "解码失败");
                    remoteMsg.put((String) H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, (Object) "ide");
                    JSONObject descript = new JSONObject();
                    descript.put((String) "contenttype", (Object) str);
                    descript.put((String) "datatype", (Object) str2);
                    remoteMsg.put((String) "description", (Object) descript.toString());
                    Activity topActivity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
                    if (topActivity != null) {
                        TinyappUtils.sendMsgToRemoteWorkerOrVConsole(str3, H5TinyAppLogUtil.TINY_APP_STANDARD_LOG, H5TinyAppLogUtil.buildStandardLogInfo(topActivity, h5Page2, remoteMsg));
                    }
                }
            });
        }
    }

    private static String a(String netValue) {
        return ZURLEncodedUtil.urlEncode(netValue);
    }

    public void preLoadInTinyProcess() {
        H5ThreadPoolFactory.getDefaultScheduledExecutor().schedule(new Runnable() {
            public final void run() {
                H5Page h5Page = ((H5Service) H5Utils.findServiceByInterface(H5Service.class.getName())).getTopH5Page();
                if (h5Page == null) {
                    boolean canInitProvider = true;
                    boolean canInitConfig = true;
                    boolean initCookie = true;
                    boolean initServicePlugin = true;
                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    if (h5ConfigProvider != null) {
                        JSONObject jsonObject = h5ConfigProvider.getConfigJSONObject("h5_preLoadInTinyProcess");
                        if (jsonObject != null && !jsonObject.isEmpty()) {
                            canInitProvider = H5Utils.getBoolean(jsonObject, (String) "canInitProvider", true);
                            canInitConfig = H5Utils.getBoolean(jsonObject, (String) "canInitConfig", true);
                            initCookie = H5Utils.getBoolean(jsonObject, (String) "initCookie", true);
                            initServicePlugin = H5Utils.getBoolean(jsonObject, (String) "initServicePlugin", true);
                        }
                    }
                    if (canInitConfig) {
                        H5EventHandlerServiceImpl.this.k();
                    }
                    if (canInitProvider) {
                        H5EventHandlerServiceImpl.j();
                    }
                    if (initServicePlugin) {
                        H5EventHandlerServiceImpl.g();
                    }
                    if (initCookie) {
                        H5EventHandlerServiceImpl.h();
                    }
                    H5EventHandlerServiceImpl.i();
                    return;
                }
                H5Log.d("H5EventHandlerImpl", String.valueOf(h5Page));
            }
        }, 2, TimeUnit.SECONDS);
    }

    public void preConnectSpdy() {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public final void run() {
                H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (configProvider != null) {
                    JSONObject jsonObject = configProvider.getConfigJSONObject("h5_preLoadInTinyProcess");
                    boolean preConnectSpdy = true;
                    if (jsonObject != null && !jsonObject.isEmpty()) {
                        preConnectSpdy = H5Utils.getBoolean(jsonObject, (String) "preConnectSpdy", true);
                    }
                    if (preConnectSpdy) {
                        long time = System.currentTimeMillis();
                        try {
                            CoreHttpManager.getInstance(H5Utils.getContext());
                            H5Log.d("H5EventHandlerImpl", "AndroidSpdyHttpClient speed " + (System.currentTimeMillis() - time));
                        } catch (Throwable throwable) {
                            H5Log.e((String) "H5EventHandlerImpl", throwable);
                        }
                    }
                }
            }
        });
    }

    public boolean startLiteProcessAsync() {
        if (LiteProcessServerManager.g().hasPreloadProcess()) {
            return false;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equals(h5ConfigProvider.getConfig("h5startLiteProcessAsync"))) {
            H5Log.d("H5EventHandlerImpl", "startLiteProcessAsync");
            LiteProcessServerManager.g().startLiteProcessAsync(0);
        }
        return true;
    }

    public void notifyUcInitSuccess() {
        LiteProcessApi.notifyUcInitSuccess();
    }

    public void onLiteProcessPreloadComplete() {
        LiteProcessClientManager.onLiteProcessPreloadComplete();
    }

    public void onUcInitAbandonedInLiteProcess() {
        PerformanceLogger.setUcInitAbandoned();
    }

    public void onUcReInitSuccessInLiteProcess() {
        PerformanceLogger.setUcReInitSuccess();
    }

    public void onTinyAppProcessEvent(String process, String section) {
        PerformanceLogger.onTinyAppProcessEvent(process, section);
    }

    /* access modifiers changed from: private */
    public static void g() {
        long time = System.currentTimeMillis();
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            h5Service.initServicePlugin();
            H5Log.d("H5EventHandlerImpl", "initServicePlugin  cost:" + (System.currentTimeMillis() - time));
        }
    }

    /* access modifiers changed from: private */
    public static void h() {
        try {
            long time = System.currentTimeMillis();
            H5Log.d("H5EventHandlerImpl", "initCookie " + H5CookieUtil.getCookie("https://www.alipay.com/") + " cost:" + (System.currentTimeMillis() - time));
        } catch (Throwable throwable) {
            H5Log.e((String) "H5EventHandlerImpl", throwable);
        }
    }

    /* access modifiers changed from: private */
    public static void i() {
        long time = System.currentTimeMillis();
        H5NetworkManager h5NetworkManager = new H5NetworkManager(H5Utils.getContext());
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            d = h5LoginProvider.getUserId();
        }
        H5Log.d("H5EventHandlerImpl", "pre init h5NetworkManager " + h5NetworkManager + Token.SEPARATOR + (System.currentTimeMillis() - time));
    }

    /* access modifiers changed from: private */
    public static void j() {
        long time = System.currentTimeMillis();
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            H5ProviderManager h5ProviderManager = h5Service.getProviderManager();
            if (h5ProviderManager != null) {
                H5Log.d("H5EventHandlerImpl", "initProvider cost:" + (System.currentTimeMillis() - time) + Token.SEPARATOR + ((H5AppProvider) h5ProviderManager.getProvider(H5AppProvider.class.getName())) + Token.SEPARATOR + ((H5EnvProvider) h5ProviderManager.getProvider(H5EnvProvider.class.getName())) + Token.SEPARATOR + ((H5LoginProvider) h5ProviderManager.getProvider(H5LoginProvider.class.getName())) + Token.SEPARATOR + ((H5ChannelProvider) h5ProviderManager.getProvider(H5ChannelProvider.class.getName())) + Token.SEPARATOR + ((H5SimpleRpcProvider) h5ProviderManager.getProvider(H5SimpleRpcProvider.class.getName())) + Token.SEPARATOR + ((H5ResourceHandler) h5ProviderManager.getProvider(H5ResourceHandler.class.getName())) + Token.SEPARATOR + ((H5SharePanelProvider) h5ProviderManager.getProvider(H5SharePanelProvider.class.getName())) + Token.SEPARATOR + ((H5LogProvider) h5ProviderManager.getProvider(H5LogProvider.class.getName())) + Token.SEPARATOR + ((H5ThreadPoolProvider) h5ProviderManager.getProvider(H5ThreadPoolProvider.class.getName())) + Token.SEPARATOR + ((H5ApiManager) h5ProviderManager.getProvider(H5ApiManager.class.getName())));
            }
        }
    }

    /* access modifiers changed from: private */
    public void k() {
        try {
            long time = System.currentTimeMillis();
            H5IpcServer h5IpcServer = (H5IpcServer) getIpcProxy(H5IpcServer.class);
            if (h5IpcServer != null) {
                h5IpcServer.getTinyProcessUseConfigList();
                H5Utils.getProvider(H5ConfigProvider.class.getName());
            }
            H5Log.d("H5EventHandlerImpl", "initConfig cost:" + (System.currentTimeMillis() - time));
        } catch (Throwable throwable) {
            H5Log.e((String) "H5EventHandlerImpl", throwable);
        }
    }
}
