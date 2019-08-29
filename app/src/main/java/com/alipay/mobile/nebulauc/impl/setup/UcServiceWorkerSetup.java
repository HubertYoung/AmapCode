package com.alipay.mobile.nebulauc.impl.setup;

import android.content.Context;
import android.net.Uri;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5EventDispatchHandler;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5ServiceWorkerHook4Bridge;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.ConfigPlugin;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.logging.TinyLoggingConfigPlugin;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ServiceWorkerControllerProvider;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;
import com.alipay.mobile.nebulauc.impl.UCWebResourceRequest;
import com.alipay.mobile.nebulauc.impl.serviceworker.H5ServiceWorkerBridgeContext;
import com.alipay.mobile.nebulauc.impl.serviceworker.H5ServiceWorkerControllerProviderImpl;
import com.alipay.mobile.nebulauc.impl.serviceworker.H5ServiceWorkerPageManager;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView;
import com.alipay.mobile.tinyappcommon.h5plugin.ApiDynamicPermissionPlugin;
import com.alipay.mobile.worker.H5WorkerControllerProvider;
import com.alipay.sdk.cons.c;
import com.uc.webview.export.ServiceWorkerClient;
import com.uc.webview.export.ServiceWorkerController;
import com.uc.webview.export.WebResourceRequest;
import com.uc.webview.export.WebResourceResponse;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class UcServiceWorkerSetup {
    private static final String TAG = "H5UcService::UcServiceWorkerSetup";
    private static final int TIMEOUT = 2000;
    private static List<String> apiList = new ArrayList();
    private static Set<String> enableSyncJsapiSet = null;
    private static Boolean sEnableNativeCanvas = null;
    private static JSONArray sEnableNativeCanvasAppidList = null;
    private static Handler uiHandler = null;
    private static final String viewId = "viewId";

    static {
        apiList.add("getSystemInfo");
        apiList.add("remoteLog");
        apiList.add("httpRequest");
        apiList.add(H5LoggerPlugin.REPORT_DATA);
        apiList.add(H5EventHandler.getAuthCode);
        apiList.add(TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE);
        apiList.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE);
        apiList.add(TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE);
        apiList.add(TinyLoggingConfigPlugin.ACTION_TRACKER_CONFIG);
        apiList.add(H5LoggerPlugin.REPORT_DATA);
        apiList.add(ConfigPlugin.ACTION);
        apiList.add(H5EventHandler.getAuthUserInfo);
        apiList.add("localLog");
    }

    private static Set<String> getDftSyncJsApiArray() {
        if (enableSyncJsapiSet == null) {
            enableSyncJsapiSet = new HashSet();
            enableSyncJsapiSet.add("getSystemInfo");
            enableSyncJsapiSet.add(CommonEvents.SET_AP_DATA_STORAGE);
            enableSyncJsapiSet.add(CommonEvents.GET_AP_DATA_STORAGE);
            enableSyncJsapiSet.add(CommonEvents.REMOVE_AP_DATA_STORAGE);
            enableSyncJsapiSet.add(CommonEvents.CLEAR_AP_DATA_STORAGE);
            enableSyncJsapiSet.add(TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE);
            enableSyncJsapiSet.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE);
            enableSyncJsapiSet.add(TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE);
            enableSyncJsapiSet.add(TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE);
            enableSyncJsapiSet.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE_INFO);
            enableSyncJsapiSet.add("getStartupParams");
            enableSyncJsapiSet.add(ApiDynamicPermissionPlugin.INTERNAL_API);
            enableSyncJsapiSet.add("measureText");
            enableSyncJsapiSet.add("getBackgroundAudioOption");
            enableSyncJsapiSet.add("getForegroundAudioOption");
            enableSyncJsapiSet.add("NBComponent.sendMessage");
        }
        return enableSyncJsapiSet;
    }

    public static void initServiceWorkerCallback() {
        String pid = null;
        if (H5Utils.isInTinyProcess()) {
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService != null) {
                pid = h5EventHandlerService.getLitePid() + "";
            } else {
                H5Log.w(TAG, "h5EventHandlerService == null");
            }
        }
        long ts = System.currentTimeMillis();
        final String workerId = TextUtils.isEmpty(pid) ? String.valueOf(ts) : pid + "_" + ts;
        UcSetupTracing.beginTrace("initServiceWorkerConsoleCallback");
        UCCore.notifyCoreEvent(6, null, new ValueCallback<Object>() {
            public void onReceiveValue(final Object result) {
                if (result != null && !UcServiceWorkerSetup.tryConsoleLogMessaging(result)) {
                    H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                        public void run() {
                            H5Log.d(UcServiceWorkerSetup.TAG, "logServiceWorkerOnReceiveValue " + workerId + " result:" + result);
                            try {
                                UcServiceWorkerSetup.logServiceWorkerOnReceiveValue(workerId, result);
                                H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
                                if (h5TinyAppProvider != null) {
                                    h5TinyAppProvider.handlerOnWorkLog(workerId, result);
                                }
                            } catch (Throwable throwable) {
                                H5Log.e((String) UcServiceWorkerSetup.TAG, throwable);
                            }
                        }
                    });
                }
            }
        });
        UcSetupTracing.endTrace("initServiceWorkerConsoleCallback");
    }

    /* access modifiers changed from: private */
    public static void logServiceWorkerOnReceiveValue(String workerId, Object result) {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null && h5Service.getBugMeManager() != null) {
            h5Service.getBugMeManager().logServiceWorkerOnReceiveValue((HashMap) result, workerId);
        }
    }

    public static void initServiceWorkerController() {
        UcSetupTracing.beginTrace("initServiceWorkerController");
        boolean noCache = true;
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_sw_nocache"))) {
            noCache = false;
        }
        try {
            ServiceWorkerController serviceWorkerController = ServiceWorkerController.getInstance();
            if (serviceWorkerController == null) {
                H5Log.e((String) TAG, (String) "serviceWorkerController == null");
                return;
            }
            final H5ServiceWorkerControllerProvider provider = new H5ServiceWorkerControllerProviderImpl();
            final boolean finalNoCache = noCache;
            serviceWorkerController.setServiceWorkerClient(new ServiceWorkerClient() {
                public WebResourceResponse shouldInterceptRequest(WebResourceRequest webResourceRequest) {
                    android.webkit.WebResourceResponse webResourceResponse = provider.shouldInterceptRequest4ServiceWorker(new UCWebResourceRequest(webResourceRequest));
                    Uri uri = webResourceRequest.getUrl();
                    WebResourceResponse response = null;
                    if (uri != null) {
                        if ("yes".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_log_sw_shouldInterceptRequest"))) {
                            H5Log.d(UcServiceWorkerSetup.TAG, "shouldInterceptRequest " + uri.toString());
                        }
                        String scheme = uri.getScheme();
                        String kylinPrefix = scheme + "://" + uri.getHost();
                        if (webResourceResponse != null) {
                            response = new WebResourceResponse(webResourceResponse.getMimeType(), webResourceResponse.getEncoding(), webResourceResponse.getData());
                            Map rspHeader = new HashMap();
                            if (H5WorkerControllerProvider.KYLIN_BRIDGE.equalsIgnoreCase(kylinPrefix)) {
                                String acao = "*";
                                Map reqHeader = webResourceRequest.getRequestHeaders();
                                if (reqHeader != null) {
                                    Iterator<String> it = reqHeader.keySet().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        String key = it.next();
                                        if ("referer".equalsIgnoreCase(key)) {
                                            Uri referUri = H5UrlHelper.parseUrl(reqHeader.get(key));
                                            if (referUri != null) {
                                                acao = referUri.getScheme() + "://" + referUri.getEncodedAuthority();
                                            }
                                        }
                                    }
                                }
                                rspHeader.put("Access-Control-Allow-Origin", acao);
                            }
                            if (finalNoCache) {
                                rspHeader.put("Cache-Control", "no-cache");
                            }
                            if (!rspHeader.isEmpty()) {
                                response.setResponseHeaders(rspHeader);
                            }
                        }
                    }
                    return response;
                }
            });
            UcSetupTracing.endTrace("initServiceWorkerController");
        } catch (Throwable t) {
            H5Log.e(TAG, "initServiceWorkerController exception ", t);
        }
    }

    private static boolean enableNativeCanvas(String tinyAppId) {
        if (sEnableNativeCanvas == null) {
            JSONObject config = H5ConfigUtil.getConfigJSONObject("h5_nativeCanvasConfig");
            if (config != null) {
                sEnableNativeCanvas = Boolean.valueOf(H5Utils.getBoolean(config, (String) "enable", false));
                sEnableNativeCanvasAppidList = H5Utils.getJSONArray(config, "appId", null);
            }
        }
        if (sEnableNativeCanvas == null || !sEnableNativeCanvas.booleanValue()) {
            return false;
        }
        for (int i = 0; i < sEnableNativeCanvasAppidList.size(); i++) {
            String appId = sEnableNativeCanvasAppidList.getString(i);
            if (!TextUtils.isEmpty(tinyAppId) && tinyAppId.contains(appId)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean tryConsoleLogMessaging(Object result) {
        if (result != null && (result instanceof Map)) {
            Map resultMap = (Map) result;
            if (resultMap.containsKey("msg")) {
                String msg = (String) resultMap.get("msg");
                if (msg.startsWith("kylinBridge://")) {
                    try {
                        JSONObject jsonObject = parseJSONObject(msg.substring("kylinBridge://".length()));
                        if (enableNativeCanvas(H5Utils.getString(jsonObject, (String) "applicationId"))) {
                            dispatchToNative(jsonObject);
                            return true;
                        }
                    } catch (Throwable e) {
                        H5Log.e(TAG, "tryConsoleLogMessaging", e);
                    }
                }
            }
        }
        return false;
    }

    private static JSONObject parseJSONObject(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        boolean z = false;
        try {
            return (JSONObject) JSON.parseObject(data, JSONObject.class);
        } catch (Exception var3) {
            H5Log.e((String) H5Utils.TAG, (Throwable) var3);
            return z;
        }
    }

    private static void dispatchToNative(JSONObject jsonObject) {
        try {
            H5Log.d(TAG, "use console.log message channel");
            if (jsonObject == null || jsonObject.isEmpty()) {
                H5Log.w(TAG, "data parse error");
                return;
            }
            boolean isCanvasDrawCmd = false;
            if ("NBComponent.sendMessage".equals(H5Utils.getString(jsonObject, (String) "action"))) {
                JSONObject innerData = H5Utils.getJSONObject(jsonObject, "data", null);
                if (innerData != null && "draw".equals(H5Utils.getString(innerData, (String) "actionType"))) {
                    isCanvasDrawCmd = true;
                }
            }
            final boolean showLog = !isCanvasDrawCmd;
            if (showLog) {
                H5Log.d(TAG, "console data " + jsonObject);
            }
            final String action = H5Utils.getString(jsonObject, (String) "action");
            final int requestId = H5Utils.getInt(jsonObject, (String) OkApacheClient.REQUESTID);
            final String applicationId = H5Utils.getString(jsonObject, (String) "applicationId");
            String callback = H5Utils.getString(jsonObject, (String) "callback");
            if ("mtop".equalsIgnoreCase(action)) {
                H5Utils.handleTinyAppKeyEvent((String) "main", jsonObject.getJSONObject("data").getString(c.n) + ".start");
            }
            if (TextUtils.equals(action, H5EmbedWebView.ACTION_TYPE)) {
                JSONObject params = H5Utils.getJSONObject(jsonObject, "data", null);
                H5Page h5Page = getTargetH5Page(H5Utils.getInt(params, (String) viewId, -1), applicationId);
                if (h5Page != null && h5Page.getBridge() != null) {
                    JSONObject dataWrap = new JSONObject();
                    dataWrap.put((String) "data", (Object) params);
                    h5Page.getBridge().sendToWeb("message", dataWrap, null);
                }
            } else if (!TextUtils.isEmpty(callback)) {
                boolean hasPermission = false;
                Set<String> dftSyncJsApiArray = getDftSyncJsApiArray();
                JSONObject syncConfig = H5Utils.parseObject(H5ConfigUtil.getConfig("h5_swSyncJsApiConfig"));
                JSONArray addList = H5Utils.getJSONArray(syncConfig, "added", null);
                if (addList != null) {
                    int size = addList.size();
                    for (int i = 0; i < size; i++) {
                        dftSyncJsApiArray.add(addList.getString(i));
                    }
                }
                JSONArray blackList = H5Utils.getJSONArray(syncConfig, "black_list", null);
                if (addList != null) {
                    int size2 = addList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        dftSyncJsApiArray.remove(blackList.getString(i2));
                    }
                }
                if (dftSyncJsApiArray != null && !dftSyncJsApiArray.isEmpty()) {
                    hasPermission = dftSyncJsApiArray.contains(action);
                }
                H5Log.d(TAG, "sync hasPermission " + hasPermission);
                if (hasPermission) {
                    ConditionVariable syncLock = new ConditionVariable();
                    StringBuilder syncResult = new StringBuilder();
                    syncResult.append(callback);
                    syncResult.append("(");
                    final StringBuilder sb = syncResult;
                    final ConditionVariable conditionVariable = syncLock;
                    handleMsgFromJs(action, jsonObject, new H5ServiceWorkerHook4Bridge() {
                        String action_ = new String(action.getBytes("utf-8"));
                        long time = System.currentTimeMillis();

                        public void onReceiveJsapiResult(JSONObject result) {
                            try {
                                long cost = System.currentTimeMillis() - this.time;
                                if (showLog) {
                                    H5Log.d(UcServiceWorkerSetup.TAG, "tinyAppTimeCostLog:" + action + " onReceiveJsapiResult cost " + cost);
                                }
                                if (cost > 2000) {
                                    H5LogUtil.logNebulaTech(H5LogData.seedId("h5_work_sync_timeout").param1().add(action, null));
                                }
                                if (result != null) {
                                    String data = result.toJSONString();
                                    if (showLog) {
                                        H5Log.d(UcServiceWorkerSetup.TAG, "sync onReceiveJsapiResult action " + this.action_ + " sendMsg " + data);
                                    }
                                    sb.append(data);
                                }
                            } catch (Exception e) {
                                H5Log.e(UcServiceWorkerSetup.TAG, "sync failed to get byte array", e);
                            } finally {
                                conditionVariable.open();
                            }
                        }
                    }, applicationId);
                    syncLock.close();
                    syncLock.block(2000);
                    syncResult.append(")");
                }
            } else {
                final JSONObject jSONObject = jsonObject;
                handleMsgFromJs(action, jsonObject, new H5ServiceWorkerHook4Bridge() {
                    String action_ = new String(action.getBytes("utf-8"));
                    String applicationId_ = new String(applicationId.getBytes("utf-8"));
                    int requestId_ = requestId;
                    long startTime = System.currentTimeMillis();

                    public void onReceiveJsapiResult(JSONObject result) {
                        try {
                            if (showLog) {
                                H5Log.d(UcServiceWorkerSetup.TAG, "tinyAppTimeCostLog:" + action + " onReceiveJsapiResult cost " + (System.currentTimeMillis() - this.startTime));
                            }
                            H5Service h5Service = H5ServiceUtils.getH5Service();
                            if (h5Service != null) {
                                HashMap pushData = new HashMap();
                                pushData.put("appId", this.applicationId_);
                                JSONObject msg = new JSONObject();
                                msg.put((String) "param", (Object) result);
                                msg.put((String) OkApacheClient.REQUESTID, (Object) Integer.valueOf(this.requestId_));
                                String message = msg.toJSONString();
                                pushData.put("message", message);
                                pushData.put("messageId", System.currentTimeMillis() + "");
                                if (showLog) {
                                    H5Log.d(UcServiceWorkerSetup.TAG, "async onReceiveJsapiResult h5Service ！= null action " + this.action_ + " sendMsg " + message);
                                }
                                h5Service.sendServiceWorkerPushMessage(pushData);
                                return;
                            }
                            H5Log.d(UcServiceWorkerSetup.TAG, "async onReceiveJsapiResult h5Service == null");
                            if ("mtop".equalsIgnoreCase(action)) {
                                H5Utils.handleTinyAppKeyEvent((String) "main", jSONObject.getJSONObject("data").getString(c.n) + ".end");
                            }
                        } catch (Exception e) {
                            H5Log.e(UcServiceWorkerSetup.TAG, "async failed to get byte array", e);
                        }
                    }
                }, applicationId);
            }
        } catch (Exception ex) {
            H5Log.e((String) TAG, (Throwable) ex);
        }
    }

    private static void handleMsgFromJs(final String action, final JSONObject jsonObject, final H5ServiceWorkerHook4Bridge h5ServiceWorkerHook4Bridge, final String workerId) {
        getHandler(action).post(new Runnable() {
            public void run() {
                H5Page h5Page;
                try {
                    boolean isServiceContext = H5Utils.getBoolean(jsonObject, (String) "service_context", false);
                    if (!jsonObject.containsKey(UcServiceWorkerSetup.viewId)) {
                        h5Page = UcServiceWorkerSetup.getTargetH5Page(-1, workerId);
                    } else {
                        h5Page = UcServiceWorkerSetup.getTargetH5Page(H5Utils.getInt(jsonObject, (String) UcServiceWorkerSetup.viewId, -1), workerId);
                    }
                    if (isServiceContext) {
                        H5Log.d(UcServiceWorkerSetup.TAG, "handleMsgFromJs get h5Page isServiceContext");
                        Context activity = (Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
                        if (activity == null) {
                            activity = H5Utils.getContext();
                        }
                        h5Page = H5ServiceWorkerPageManager.getInstance(activity);
                    }
                    if (h5Page == null) {
                        H5Log.w(UcServiceWorkerSetup.TAG, "handleMsgFromJs h5Page == null return");
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_SW_PageNull").param2().add(action, null).param3().add(workerId, null).param4().add(jsonObject + "", null));
                        return;
                    }
                    String action = H5Utils.getString(jsonObject, (String) "action");
                    JSONObject params = H5Utils.getJSONObject(jsonObject, "data", null);
                    String clientId = H5Utils.getString(jsonObject, (String) "clientId", (String) null);
                    if (TextUtils.isEmpty(clientId)) {
                        clientId = Long.toString(System.nanoTime());
                    }
                    Builder builder = new Builder();
                    if (H5Utils.isMain()) {
                        builder.action(action).param(params).target(h5Page).type("call").id(clientId).keepCallback(false);
                    } else {
                        builder.action(action).param(params).target(h5Page).type("call").id(clientId).keepCallback(false).dispatcherOnWorkerThread(true);
                    }
                    H5Event event = builder.build();
                    H5Bridge h5Bridge = h5Page.getBridge();
                    H5BridgeContext context = new H5ServiceWorkerBridgeContext(h5ServiceWorkerHook4Bridge, clientId, action, h5Bridge, h5Page);
                    if (h5Bridge != null) {
                        h5Bridge.sendToNative(event, context);
                        return;
                    }
                    H5Service h5Service = H5ServiceUtils.getH5Service();
                    if (h5Service != null) {
                        h5Service.sendEvent(event, context);
                    }
                } catch (Throwable e) {
                    H5Log.e((String) UcServiceWorkerSetup.TAG, e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static H5Page getTargetH5Page(int id, String workerId) {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            synchronized (H5ServiceWorkerControllerProviderImpl.class) {
                if (!H5Utils.isInTinyProcess() && !TextUtils.isEmpty(workerId)) {
                    H5Page h5Page = h5Service.getTopH5BaseFragmentByWorkerId(workerId).getH5Page();
                    if (h5Page != null) {
                        return h5Page;
                    }
                    H5Log.e((String) TAG, (String) "fatal error h5Page == null #3");
                } else if (id == -1) {
                    H5BaseFragment h5BaseFragment = h5Service.getTopH5BaseFragment();
                    if (h5BaseFragment == null || h5BaseFragment.getH5Page() == null) {
                        H5Log.e((String) TAG, (String) "fatal error h5Page == null #1");
                        H5Page h5Page2 = h5Service.getTopH5Page();
                        if (h5Page2 == null || !H5AppUtil.isTinyWebView(h5Page2.getParams())) {
                            return h5Page2;
                        }
                        H5Log.d(TAG, "not send work to web-view");
                        H5Page h5Page3 = getTopServiceWorkPage();
                        return h5Page3;
                    }
                    H5Page h5Page4 = h5BaseFragment.getH5Page();
                    return h5Page4;
                } else {
                    H5BaseFragment h5BaseFragment2 = h5Service.getTopH5BaseFragmentByViewId(id);
                    if (h5BaseFragment2 == null || h5BaseFragment2.getH5Page() == null) {
                        H5Log.e((String) TAG, (String) "fatal error h5Page == null #2");
                    } else {
                        H5Page h5Page5 = h5BaseFragment2.getH5Page();
                        return h5Page5;
                    }
                }
            }
        } else {
            H5Log.e((String) TAG, (String) "fatal error h5Service==null");
        }
        return null;
    }

    private static H5Page getTopServiceWorkPage() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            try {
                Stack sessions = h5Service.getSessions();
                if (sessions != null) {
                    synchronized (sessions) {
                        int index = sessions.size() - 1;
                        while (index >= 0) {
                            H5Session session = (H5Session) sessions.get(index);
                            if (session == null || TextUtils.isEmpty(session.getServiceWorkerID())) {
                                index--;
                            } else {
                                H5Page topPage = session.getTopPage();
                                return topPage;
                            }
                        }
                    }
                }
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
        return null;
    }

    private static Handler getHandler(String action) {
        if (apiList.contains(action) && enableAsync()) {
            return H5EventDispatchHandler.getAsyncHandler();
        }
        if (uiHandler == null) {
            uiHandler = new Handler(Looper.getMainLooper());
        }
        return uiHandler;
    }

    private static boolean enableAsync() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableSWAsync"))) {
            return true;
        }
        return false;
    }
}
