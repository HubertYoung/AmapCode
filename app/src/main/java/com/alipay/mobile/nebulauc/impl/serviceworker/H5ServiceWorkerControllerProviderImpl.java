package com.alipay.mobile.nebulauc.impl.serviceworker;

import android.content.Context;
import android.net.Uri;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
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
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5ServiceWorkerHook4Bridge;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.ConfigPlugin;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.logging.TinyLoggingConfigPlugin;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ServiceWorkerControllerProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView;
import com.alipay.mobile.tinyappcommon.h5plugin.ApiDynamicPermissionPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import com.alipay.sdk.cons.c;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class H5ServiceWorkerControllerProviderImpl implements H5ServiceWorkerControllerProvider {
    private static final int TIMEOUT = 2000;
    private static List<String> apiList = new ArrayList();
    private static final String kylinBridge = "https://alipay.kylinBridge";
    private static final String viewId = "viewId";
    /* access modifiers changed from: private */
    public String TAG = ("H5ServiceWorkerControllerProviderImpl_" + hashCode());
    private Set<String> enableSyncJsapiSet;
    private boolean isFirstJsApi = true;
    private Handler uiHandler;

    private boolean isFromTinyApp(APWebResourceRequest apWebResourceRequest) {
        try {
            if (!"yes".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_swReqCheckFromTinyApp"))) {
                return true;
            }
            Map headers = apWebResourceRequest.getRequestHeaders();
            String refererUrl = headers.get(H5Param.REFERER);
            if (refererUrl == null || TextUtils.isEmpty(refererUrl)) {
                refererUrl = headers.get("referer");
            }
            if (!TextUtils.isEmpty(H5AppUtil.matchAppId(H5UrlHelper.getScheme(refererUrl) + "://" + H5UrlHelper.getHost(refererUrl)))) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return true;
        }
    }

    private Set<String> getDftSyncJsApiArray() {
        if (this.enableSyncJsapiSet == null) {
            this.enableSyncJsapiSet = new HashSet();
            this.enableSyncJsapiSet.add("getSystemInfo");
            this.enableSyncJsapiSet.add(CommonEvents.SET_AP_DATA_STORAGE);
            this.enableSyncJsapiSet.add(CommonEvents.GET_AP_DATA_STORAGE);
            this.enableSyncJsapiSet.add(CommonEvents.REMOVE_AP_DATA_STORAGE);
            this.enableSyncJsapiSet.add(CommonEvents.CLEAR_AP_DATA_STORAGE);
            this.enableSyncJsapiSet.add(TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE);
            this.enableSyncJsapiSet.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE);
            this.enableSyncJsapiSet.add(TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE);
            this.enableSyncJsapiSet.add(TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE);
            this.enableSyncJsapiSet.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE_INFO);
            this.enableSyncJsapiSet.add("getStartupParams");
            this.enableSyncJsapiSet.add(ApiDynamicPermissionPlugin.INTERNAL_API);
            this.enableSyncJsapiSet.add("measureText");
            this.enableSyncJsapiSet.add("getBackgroundAudioOption");
            this.enableSyncJsapiSet.add("getForegroundAudioOption");
            this.enableSyncJsapiSet.add("NBComponent.sendMessage");
            this.enableSyncJsapiSet.add("getBatteryInfo");
        }
        return this.enableSyncJsapiSet;
    }

    public WebResourceResponse shouldInterceptRequest4ServiceWorker(APWebResourceRequest apWebResourceRequest) {
        Uri uri = apWebResourceRequest.getUrl();
        if (uri != null) {
            try {
                String u = uri.toString();
                H5Log.d(this.TAG, u);
                if ("https://alipay.kylinBridge".equalsIgnoreCase(uri.getScheme() + "://" + uri.getHost())) {
                    if (this.isFirstJsApi) {
                        this.isFirstJsApi = false;
                        H5PageData.swFirstJsApiCallTime = System.currentTimeMillis();
                    }
                    String data = uri.getQueryParameter("data");
                    final JSONObject jsonObject = H5Utils.parseObject(data);
                    if (jsonObject == null || jsonObject.isEmpty()) {
                        H5Log.w(this.TAG, "shouldInterceptRequest4ServiceWorker data parse error for: " + data);
                        return null;
                    }
                    if (!("canvas".equals(H5Utils.getString(jsonObject, (String) "element")) && "draw".equals(H5Utils.getString(jsonObject, (String) "actionType")))) {
                        H5Log.d(this.TAG, "shouldInterceptRequest4ServiceWorker data " + data);
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
                        if (!(h5Page == null || h5Page.getBridge() == null)) {
                            JSONObject dataWrap = new JSONObject();
                            dataWrap.put((String) "data", (Object) params);
                            h5Page.getBridge().sendToWeb("message", dataWrap, null);
                        }
                        WebResourceResponse webResourceResponse = new WebResourceResponse("application/json", "UTF-8", new ByteArrayInputStream("".getBytes("utf-8")));
                        return webResourceResponse;
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
                        H5Log.d(this.TAG, "sync hasPermission " + hasPermission);
                        if (hasPermission) {
                            ConditionVariable syncLock = new ConditionVariable();
                            StringBuilder syncResult = new StringBuilder();
                            syncResult.append(callback);
                            syncResult.append("(");
                            if (isFromTinyApp(apWebResourceRequest)) {
                                final StringBuilder sb = syncResult;
                                final ConditionVariable conditionVariable = syncLock;
                                handleMsgFromJs(action, jsonObject, new H5ServiceWorkerHook4Bridge() {
                                    String action_ = new String(action.getBytes("utf-8"));
                                    long time = System.currentTimeMillis();

                                    public void onReceiveJsapiResult(JSONObject result) {
                                        try {
                                            long cost = System.currentTimeMillis() - this.time;
                                            H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "tinyAppTimeCostLog:" + action + " onReceiveJsapiResult cost " + cost);
                                            if (cost > 2000) {
                                                H5LogUtil.logNebulaTech(H5LogData.seedId("h5_work_sync_timeout").param1().add(action, null));
                                            }
                                            if (result != null) {
                                                String data = result.toJSONString();
                                                H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "sync onReceiveJsapiResult action " + this.action_ + " sendMsg " + data);
                                                sb.append(data);
                                            }
                                        } catch (Exception e) {
                                            H5Log.e(H5ServiceWorkerControllerProviderImpl.this.TAG, "sync failed to get byte array", e);
                                        } finally {
                                            conditionVariable.open();
                                        }
                                    }
                                }, applicationId);
                                syncLock.close();
                                syncLock.block(2000);
                                syncResult.append(")");
                                WebResourceResponse webResourceResponse2 = new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(syncResult.toString().getBytes("utf-8")));
                                return webResourceResponse2;
                            }
                            H5Log.w(this.TAG, "reject " + action);
                        } else {
                            WebResourceResponse webResourceResponse3 = new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream((callback + "('not in H5_SWSYNCAPILIST')").getBytes("utf-8")));
                            return webResourceResponse3;
                        }
                    } else if (isFromTinyApp(apWebResourceRequest)) {
                        handleMsgFromJs(action, jsonObject, new H5ServiceWorkerHook4Bridge() {
                            String action_ = new String(action.getBytes("utf-8"));
                            String applicationId_ = new String(applicationId.getBytes("utf-8"));
                            int requestId_ = requestId;
                            long startTime = System.currentTimeMillis();

                            public void onReceiveJsapiResult(JSONObject result) {
                                try {
                                    H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "tinyAppTimeCostLog:" + action + " onReceiveJsapiResult cost " + (System.currentTimeMillis() - this.startTime));
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
                                        H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "async onReceiveJsapiResult h5Service ！= null action " + this.action_ + " sendMsg " + message);
                                        h5Service.sendServiceWorkerPushMessage(pushData);
                                        return;
                                    }
                                    H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "async onReceiveJsapiResult h5Service == null");
                                    if ("mtop".equalsIgnoreCase(action)) {
                                        H5Utils.handleTinyAppKeyEvent((String) "main", jsonObject.getJSONObject("data").getString(c.n) + ".end");
                                    }
                                } catch (Exception e) {
                                    H5Log.e(H5ServiceWorkerControllerProviderImpl.this.TAG, "async failed to get byte array", e);
                                }
                            }
                        }, applicationId);
                        WebResourceResponse webResourceResponse4 = new WebResourceResponse("application/json", "UTF-8", new ByteArrayInputStream("".getBytes("utf-8")));
                        return webResourceResponse4;
                    } else {
                        H5Log.w(this.TAG, "reject " + action);
                    }
                }
                if (!TextUtils.isEmpty(u) && !u.startsWith("blob")) {
                    H5Log.d(this.TAG, "work load url begin:" + u);
                    H5Service h5Service = H5ServiceUtils.getH5Service();
                    if (h5Service != null) {
                        H5Session h5Session = h5Service.getTopSession();
                        if (h5Session != null) {
                            H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
                            if (h5ContentProvider != null) {
                                WebResourceResponse webResourceResponse5 = h5ContentProvider.getContent(u);
                                if (webResourceResponse5 != null) {
                                    H5Log.d(this.TAG, "work load url from pkg " + u);
                                    return webResourceResponse5;
                                }
                            }
                        }
                        if (h5Session != null) {
                            H5BaseFragment h5BaseFragment = h5Service.getTopH5BaseFragment();
                            if (!(h5BaseFragment == null || h5BaseFragment.getH5Page() == null || !enableWorkSession("h5_enableWebSession"))) {
                                WebResourceResponse webResourceResponse6 = getWorkResponse(h5BaseFragment.getH5Page().getSession(), u);
                                if (webResourceResponse6 != null) {
                                    H5Log.d(this.TAG, "work load url form mainPageResourceResponse :" + u);
                                    return webResourceResponse6;
                                }
                            }
                            if (isDevSession(h5Session.getId()) && enableWorkSession("h5_enableDevSession")) {
                                H5Log.d(this.TAG, "is dev session " + h5Session.getId());
                                WebResourceResponse webResourceResponse7 = getWorkResponse(getNotDevSession(), u);
                                if (webResourceResponse7 != null) {
                                    H5Log.d(this.TAG, "work load url form mainPageResourceResponse inDevSession:" + u);
                                    return webResourceResponse7;
                                }
                            }
                        }
                    }
                    H5Log.d(this.TAG, "work load url form online:" + uri);
                }
            } catch (Throwable throwable) {
                H5Log.e(this.TAG, "catch exception ", throwable);
            }
        }
        return null;
    }

    private WebResourceResponse getWorkResponse(H5Session workSession, String u) {
        if (workSession != null) {
            H5ContentProvider h5ContentProvider = workSession.getWebProvider();
            if (h5ContentProvider != null) {
                WebResourceResponse webResourceResponse = h5ContentProvider.getContent(u);
                if (webResourceResponse != null) {
                    return webResourceResponse;
                }
            }
        } else {
            H5Log.e(this.TAG, (String) "workSession is null");
        }
        return null;
    }

    private H5Session getNotDevSession() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            try {
                Stack sessions = h5Service.getSessions();
                if (sessions != null) {
                    synchronized (sessions) {
                        for (int index = sessions.size() - 1; index >= 0; index--) {
                            H5Session session = (H5Session) sessions.get(index);
                            if (session != null) {
                                String id = session.getId();
                                H5Log.d(this.TAG, "sessionId:" + id);
                                if (!isDevSession(id)) {
                                    return session;
                                }
                            }
                        }
                    }
                }
            } catch (Throwable throwable) {
                H5Log.e(this.TAG, throwable);
            }
        }
        return null;
    }

    private boolean isDevSession(String id) {
        return !TextUtils.isEmpty(id) && id.contains(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID);
    }

    private boolean enableWorkSession(String key) {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig(key));
    }

    private H5Page getTopServiceWorkPage() {
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
                H5Log.e(this.TAG, throwable);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public H5Page getTargetH5Page(int id, String workerId) {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        H5Log.d(this.TAG, "getH5page viewId " + id + " workerId " + workerId);
        if (h5Service != null) {
            synchronized (H5ServiceWorkerControllerProviderImpl.class) {
                if (!H5Utils.isInTinyProcess() && !TextUtils.isEmpty(workerId)) {
                    H5Page h5Page = h5Service.getTopH5BaseFragmentByWorkerId(workerId).getH5Page();
                    if (h5Page != null) {
                        return h5Page;
                    }
                    H5Log.e(this.TAG, (String) "fatal error h5Page == null #3");
                } else if (id == -1) {
                    H5BaseFragment h5BaseFragment = h5Service.getTopH5BaseFragment();
                    if (h5BaseFragment == null || h5BaseFragment.getH5Page() == null) {
                        H5Log.e(this.TAG, (String) "fatal error h5Page == null #1");
                        H5Page h5Page2 = h5Service.getTopH5Page();
                        if (h5Page2 == null || !H5AppUtil.isTinyWebView(h5Page2.getParams())) {
                            return h5Page2;
                        }
                        H5Log.d(this.TAG, "not send work to web-view");
                        H5Page h5Page3 = getTopServiceWorkPage();
                        return h5Page3;
                    }
                    H5Page h5Page4 = h5BaseFragment.getH5Page();
                    return h5Page4;
                } else {
                    H5BaseFragment h5BaseFragment2 = h5Service.getTopH5BaseFragmentByViewId(id);
                    if (h5BaseFragment2 == null || h5BaseFragment2.getH5Page() == null) {
                        H5Log.e(this.TAG, (String) "fatal error h5Page == null #2");
                    } else {
                        H5Page h5Page5 = h5BaseFragment2.getH5Page();
                        return h5Page5;
                    }
                }
            }
        } else {
            H5Log.e(this.TAG, (String) "fatal error h5Service==null");
        }
        return null;
    }

    private void handleMsgFromJs(String action, JSONObject jsonObject, H5ServiceWorkerHook4Bridge h5ServiceWorkerHook4Bridge, String workerId) {
        final JSONObject jSONObject = jsonObject;
        final String str = workerId;
        final String str2 = action;
        final H5ServiceWorkerHook4Bridge h5ServiceWorkerHook4Bridge2 = h5ServiceWorkerHook4Bridge;
        getHandler(action).post(new Runnable() {
            public void run() {
                H5Page h5Page;
                try {
                    boolean isServiceContext = H5Utils.getBoolean(jSONObject, (String) "service_context", false);
                    if (!jSONObject.containsKey(H5ServiceWorkerControllerProviderImpl.viewId)) {
                        h5Page = H5ServiceWorkerControllerProviderImpl.this.getTargetH5Page(-1, str);
                    } else {
                        h5Page = H5ServiceWorkerControllerProviderImpl.this.getTargetH5Page(H5Utils.getInt(jSONObject, (String) H5ServiceWorkerControllerProviderImpl.viewId, -1), str);
                    }
                    if (isServiceContext) {
                        H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "handleMsgFromJs get h5Page isServiceContext");
                        Context activity = (Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
                        if (activity == null) {
                            activity = H5Utils.getContext();
                        }
                        h5Page = H5ServiceWorkerPageManager.getInstance(activity);
                    }
                    if (h5Page == null) {
                        H5Log.w(H5ServiceWorkerControllerProviderImpl.this.TAG, "handleMsgFromJs h5Page == null return");
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_SW_PageNull").param2().add(str2, null).param3().add(str, null).param4().add(jSONObject + "", null));
                        return;
                    }
                    String action = H5Utils.getString(jSONObject, (String) "action");
                    JSONObject params = H5Utils.getJSONObject(jSONObject, "data", null);
                    String clientId = H5Utils.getString(jSONObject, (String) "clientId", (String) null);
                    if (TextUtils.isEmpty(clientId)) {
                        clientId = Long.toString(System.nanoTime());
                    }
                    H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "clientId is " + clientId);
                    Builder builder = new Builder();
                    if (H5Utils.isMain()) {
                        builder.action(action).param(params).target(h5Page).type("call").id(clientId).keepCallback(false).eventSource(H5Event.FROM_WORK);
                    } else {
                        builder.action(action).param(params).target(h5Page).type("call").id(clientId).keepCallback(false).dispatcherOnWorkerThread(true).eventSource(H5Event.FROM_WORK);
                    }
                    H5Event event = builder.build();
                    H5Bridge h5Bridge = h5Page.getBridge();
                    H5BridgeContext context = new H5ServiceWorkerBridgeContext(h5ServiceWorkerHook4Bridge2, clientId, action, h5Bridge, h5Page);
                    if (h5Bridge != null) {
                        H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "use bridge send event");
                        h5Bridge.sendToNative(event, context);
                        return;
                    }
                    H5Log.d(H5ServiceWorkerControllerProviderImpl.this.TAG, "use service send event");
                    H5Service h5Service = H5ServiceUtils.getH5Service();
                    if (h5Service != null) {
                        h5Service.sendEvent(event, context);
                    }
                } catch (Throwable e) {
                    H5Log.e(H5ServiceWorkerControllerProviderImpl.this.TAG, e);
                }
            }
        });
    }

    private Handler getHandler(String action) {
        if (apiList.contains(action) && enableAsync()) {
            return H5EventDispatchHandler.getAsyncHandler();
        }
        if (this.uiHandler == null) {
            this.uiHandler = new Handler(Looper.getMainLooper());
        }
        return this.uiHandler;
    }

    private static boolean enableAsync() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableSWAsync"))) {
            return true;
        }
        return false;
    }

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
}
