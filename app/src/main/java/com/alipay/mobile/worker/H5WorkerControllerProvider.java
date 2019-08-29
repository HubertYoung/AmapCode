package com.alipay.mobile.worker;

import android.net.Uri;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5EventDispatchHandler;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
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
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackagePool;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.wallet.H5LoggerPlugin;
import com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView;
import com.alipay.mobile.tinyappcommon.h5plugin.ApiDynamicPermissionPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import com.sina.weibo.sdk.constant.WBConstants;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class H5WorkerControllerProvider {
    public static final String KYLIN_BRIDGE = "https://alipay.kylinBridge";
    private static List<String> i;
    protected String a = ("H5WorkerControllerProvider_" + hashCode());
    protected boolean b = true;
    protected WebWorker c;
    private boolean d = true;
    private Set<String> e;
    private WorkerContentProvider f;
    /* access modifiers changed from: private */
    public H5Page g;
    private Handler h;

    public H5WorkerControllerProvider(WebWorker worker) {
        this.c = worker;
    }

    /* access modifiers changed from: protected */
    public final Set<String> a() {
        if (this.e == null) {
            this.e = new HashSet();
            this.e.add("getSystemInfo");
            this.e.add(CommonEvents.SET_AP_DATA_STORAGE);
            this.e.add(CommonEvents.GET_AP_DATA_STORAGE);
            this.e.add(CommonEvents.REMOVE_AP_DATA_STORAGE);
            this.e.add(CommonEvents.CLEAR_AP_DATA_STORAGE);
            this.e.add(TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE);
            this.e.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE);
            this.e.add(TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE);
            this.e.add(TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE);
            this.e.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE_INFO);
            this.e.add("getStartupParams");
            this.e.add(ApiDynamicPermissionPlugin.INTERNAL_API);
            this.e.add("measureText");
            this.e.add("getBackgroundAudioOption");
            this.e.add("getForegroundAudioOption");
            this.e.add("NBComponent.sendMessage");
            this.e.add("getBatteryInfo");
        }
        return this.e;
    }

    public WebResourceResponse shouldInterceptRequest4Worker(String url) {
        if (url.contains("index.worker.js")) {
            Log.e(this.a, "shouldInterceptRequest4Worker index.worker.js");
        }
        try {
            Uri uri = Uri.parse(url);
            String u = uri.toString();
            H5Log.d(this.a, u);
            String scheme = uri.getScheme();
            if (KYLIN_BRIDGE.equalsIgnoreCase(scheme + "://" + uri.getHost())) {
                if (this.b) {
                    this.b = false;
                    H5PageData.swFirstJsApiCallTime = System.currentTimeMillis();
                }
                String data = uri.getQueryParameter("data");
                H5Log.d(this.a, "shouldInterceptRequest4ServiceWorker data " + data);
                JSONObject jsonObject = H5Utils.parseObject(data);
                if (jsonObject == null || jsonObject.isEmpty()) {
                    return null;
                }
                final String action = H5Utils.getString(jsonObject, (String) "action");
                int requestId = H5Utils.getInt(jsonObject, (String) OkApacheClient.REQUESTID);
                final String applicationId = H5Utils.getString(jsonObject, (String) "applicationId");
                String callback = H5Utils.getString(jsonObject, (String) "callback");
                if (!TextUtils.isEmpty(callback)) {
                    boolean hasPermission = false;
                    Set<String> a2 = a();
                    JSONObject syncConfig = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_swSyncJsApiConfig"));
                    JSONArray addList = H5Utils.getJSONArray(syncConfig, "added", null);
                    if (addList != null) {
                        int size = addList.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            a2.add(addList.getString(i2));
                        }
                    }
                    JSONArray blackList = H5Utils.getJSONArray(syncConfig, "black_list", null);
                    if (addList != null) {
                        int size2 = addList.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            a2.remove(blackList.getString(i3));
                        }
                    }
                    if (a2 != null && !a2.isEmpty()) {
                        hasPermission = a2.contains(action);
                    }
                    H5Log.d(this.a, "sync hasPermission " + hasPermission);
                    if (!hasPermission) {
                        return new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream((callback + "('not in H5_SWSYNCAPILIST')").getBytes("utf-8")));
                    }
                    ConditionVariable syncLock = new ConditionVariable();
                    StringBuilder syncResult = new StringBuilder();
                    syncResult.append(callback);
                    syncResult.append("(");
                    final StringBuilder sb = syncResult;
                    final ConditionVariable conditionVariable = syncLock;
                    AnonymousClass1 r0 = new H5ServiceWorkerHook4Bridge() {
                        String a = new String(action.getBytes("utf-8"));
                        long b = System.currentTimeMillis();

                        public void onReceiveJsapiResult(JSONObject result) {
                            try {
                                long cost = System.currentTimeMillis() - this.b;
                                H5Log.d(H5WorkerControllerProvider.this.a, "tinyAppTimeCostLog:" + action + " onReceiveJsapiResult cost " + cost);
                                if (cost > 2000) {
                                    H5LogUtil.logNebulaTech(H5LogData.seedId("h5_work_sync_timeout").param1().add(action, null).add("sys_webview", "h5worker"));
                                }
                                if (result != null) {
                                    String data = result.toJSONString();
                                    H5Log.d(H5WorkerControllerProvider.this.a, "sync onReceiveJsapiResult action " + this.a + " sendMsg " + data);
                                    sb.append(data);
                                }
                            } catch (Exception e2) {
                                H5Log.e(H5WorkerControllerProvider.this.a, "sync failed to get byte array", e2);
                            } finally {
                                conditionVariable.open();
                            }
                        }
                    };
                    a(action, jsonObject, r0, applicationId);
                    syncLock.close();
                    syncLock.block(2000);
                    syncResult.append(")");
                    return new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(syncResult.toString().getBytes("utf-8")));
                }
                H5Log.d(this.a, "caution!!! shouldn't be here!!!!");
                final int i4 = requestId;
                AnonymousClass2 r02 = new H5ServiceWorkerHook4Bridge() {
                    int a = i4;
                    String b = new String(applicationId.getBytes("utf-8"));
                    String c = new String(action.getBytes("utf-8"));
                    long d = System.currentTimeMillis();

                    public void onReceiveJsapiResult(JSONObject result) {
                        try {
                            H5Log.d(H5WorkerControllerProvider.this.a, "tinyAppTimeCostLog:" + action + " onReceiveJsapiResult cost " + (System.currentTimeMillis() - this.d));
                            H5Service h5Service = H5ServiceUtils.getH5Service();
                            if (h5Service != null) {
                                HashMap pushData = new HashMap();
                                pushData.put("appId", this.b);
                                JSONObject msg = new JSONObject();
                                msg.put((String) "param", (Object) result);
                                msg.put((String) OkApacheClient.REQUESTID, (Object) Integer.valueOf(this.a));
                                String message = msg.toJSONString();
                                pushData.put("message", message);
                                pushData.put("messageId", System.currentTimeMillis());
                                H5Log.d(H5WorkerControllerProvider.this.a, "async onReceiveJsapiResult h5Service ！= null action " + this.c + " sendMsg " + message);
                                h5Service.sendServiceWorkerPushMessage(pushData);
                                return;
                            }
                            H5Log.d(H5WorkerControllerProvider.this.a, "async onReceiveJsapiResult h5Service == null");
                        } catch (Exception e2) {
                            H5Log.e(H5WorkerControllerProvider.this.a, "async failed to get byte array", e2);
                        }
                    }
                };
                a(action, jsonObject, r02, applicationId);
                return new WebResourceResponse("application/json", "UTF-8", new ByteArrayInputStream("".getBytes("utf-8")));
            }
            if (!TextUtils.isEmpty(u) && !u.startsWith("blob")) {
                H5Log.d(this.a, "work load url begin:" + u);
                H5Service h5Service = H5ServiceUtils.getH5Service();
                if (h5Service != null) {
                    H5Session h5Session = h5Service.getTopSession();
                    if (h5Session != null) {
                        H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
                        if (h5ContentProvider != null) {
                            WebResourceResponse webResourceResponse = h5ContentProvider.getContent(u);
                            if (webResourceResponse != null) {
                                H5Log.d(this.a, "work load url from pkg " + u);
                                return webResourceResponse;
                            }
                        } else {
                            if (this.f == null && WorkerManager.sParams != null) {
                                this.f = new WorkerContentProvider(H5ContentPackagePool.getPackage(H5Utils.getString(WorkerManager.sParams, (String) "sessionId")));
                            }
                            if (this.f != null) {
                                return this.f.getContent(u);
                            }
                        }
                    } else {
                        if (this.f == null && WorkerManager.sParams != null) {
                            this.f = new WorkerContentProvider(H5ContentPackagePool.getPackage(H5Utils.getString(WorkerManager.sParams, (String) "sessionId")));
                        }
                        if (this.f != null) {
                            return this.f.getContent(u);
                        }
                    }
                    if (h5Session != null) {
                        H5BaseFragment h5BaseFragment = h5Service.getTopH5BaseFragment();
                        if (!(h5BaseFragment == null || h5BaseFragment.getH5Page() == null || !b("h5_enableWebSession"))) {
                            WebResourceResponse webResourceResponse2 = a(h5BaseFragment.getH5Page().getSession(), u);
                            if (webResourceResponse2 != null) {
                                H5Log.d(this.a, "work load url form mainPageResourceResponse :" + u);
                                return webResourceResponse2;
                            }
                        }
                        if (a(h5Session.getId()) && b("h5_enableDevSession")) {
                            H5Log.d(this.a, "is dev session " + h5Session.getId());
                            WebResourceResponse webResourceResponse3 = a(b(), u);
                            if (webResourceResponse3 != null) {
                                H5Log.d(this.a, "work load url form mainPageResourceResponse inDevSession:" + u);
                                return webResourceResponse3;
                            }
                        }
                    }
                }
                H5Log.d(this.a, "work load url form online:" + uri);
            }
            return null;
        } catch (Throwable throwable) {
            H5Log.e(this.a, "catch exception ", throwable);
        }
    }

    private WebResourceResponse a(H5Session workSession, String u) {
        if (workSession != null) {
            H5ContentProvider h5ContentProvider = workSession.getWebProvider();
            if (h5ContentProvider != null) {
                WebResourceResponse webResourceResponse = h5ContentProvider.getContent(u);
                if (webResourceResponse != null) {
                    return webResourceResponse;
                }
            }
        } else {
            H5Log.e(this.a, (String) "workSession is null");
        }
        return null;
    }

    private H5Session b() {
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
                                H5Log.d(this.a, "sessionId:" + id);
                                if (!a(id)) {
                                    return session;
                                }
                            }
                        }
                    }
                }
            } catch (Throwable throwable) {
                H5Log.e(this.a, throwable);
            }
        }
        return null;
    }

    private static boolean a(String id) {
        if (TextUtils.isEmpty(id) || !id.contains(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID)) {
            return false;
        }
        return true;
    }

    private static boolean b(String key) {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache(key))) {
            return false;
        }
        return true;
    }

    private H5Page c() {
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
                H5Log.e(this.a, throwable);
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final H5Page a(int id, String workerId) {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        H5Log.d(this.a, "getH5page viewId " + id + " workerId " + workerId);
        if (h5Service != null) {
            synchronized (H5WorkerControllerProvider.class) {
                if (!H5Utils.isInTinyProcess() && !TextUtils.isEmpty(workerId)) {
                    H5Page h5Page = h5Service.getTopH5BaseFragmentByWorkerId(workerId).getH5Page();
                    if (h5Page != null) {
                        return h5Page;
                    }
                    H5Log.e(this.a, (String) "fatal error h5Page == null #3");
                } else if (id == -1) {
                    H5BaseFragment h5BaseFragment = h5Service.getTopH5BaseFragment();
                    if (h5BaseFragment == null || h5BaseFragment.getH5Page() == null) {
                        H5Log.e(this.a, (String) "fatal error h5Page == null #1");
                        H5Page h5Page2 = h5Service.getTopH5Page();
                        if (h5Page2 == null || !H5AppUtil.isTinyWebView(h5Page2.getParams())) {
                            return h5Page2;
                        }
                        H5Log.d(this.a, "not send work to web-view");
                        H5Page h5Page3 = c();
                        return h5Page3;
                    }
                    H5Page h5Page4 = h5BaseFragment.getH5Page();
                    return h5Page4;
                } else {
                    H5BaseFragment h5BaseFragment2 = h5Service.getTopH5BaseFragmentByViewId(id);
                    if (h5BaseFragment2 == null || h5BaseFragment2.getH5Page() == null) {
                        H5Log.e(this.a, (String) "fatal error h5Page == null #2");
                    } else {
                        H5Page h5Page5 = h5BaseFragment2.getH5Page();
                        return h5Page5;
                    }
                }
            }
        } else {
            H5Log.e(this.a, (String) "fatal error h5Service==null");
        }
        return null;
    }

    private void c(String message) {
        if (this.d) {
            this.d = false;
            if (!"AlipayJSBridgeReady".equals(message)) {
                WebWorkerUtils.workerErrorLogMonitor("consoleMsg: " + message);
            }
        }
    }

    public boolean handleMsgFromWorker(String message) {
        if (TextUtils.isEmpty(message)) {
            return false;
        }
        c(message);
        if ("AlipayJSBridgeReady".equals(message)) {
            this.c.onAlipayJSBridgeReady();
            return true;
        }
        H5Log.d(this.a, "handleMsgFromWorker msg = " + message);
        String prefix = null;
        if (message.startsWith("h5container.message: ")) {
            prefix = "h5container.message: ";
        } else if (message.startsWith("jserror:h5container.message: ")) {
            prefix = "jserror:h5container.message: ";
        }
        if (TextUtils.isEmpty(prefix)) {
            return false;
        }
        JSONObject joMessage = H5Utils.parseObject(message.replaceFirst(prefix, ""));
        if (joMessage == null || joMessage.isEmpty()) {
            return false;
        }
        if (H5Utils.getJSONObject(joMessage, "data", null) == null) {
            H5Log.e(this.a, (String) "invalid param, handleMsgFromWorker data = null");
            return false;
        }
        if (H5EmbedWebView.ACTION_TYPE.equals(joMessage.getString("handlerName"))) {
            a(joMessage);
        } else {
            final String callbackId = joMessage.getString(WBConstants.SHARE_CALLBACK_ID);
            if (TextUtils.isEmpty(callbackId)) {
                H5Log.e(this.a, (String) "invalid callbackId");
                return false;
            }
            a(joMessage.getString("handlerName"), joMessage, new H5ServiceWorkerHook4Bridge() {
                public void onReceiveJsapiResult(JSONObject result) {
                    JSONObject resp = new JSONObject();
                    resp.put((String) "responseId", (Object) callbackId);
                    resp.put((String) "responseData", (Object) result);
                    H5WorkerControllerProvider.this.c.sendMessageToWorker(null, null, resp.toJSONString());
                }
            }, this.c.getWorkerId());
        }
        return true;
    }

    private void a(JSONObject joMessage) {
        H5Page h5Page;
        JSONObject data = H5Utils.getJSONObject(joMessage, "data", null);
        if (!data.containsKey("viewId")) {
            h5Page = a(-1, this.c.getWorkerId());
        } else {
            h5Page = a(H5Utils.getInt(data, (String) "viewId"), this.c.getWorkerId());
        }
        if (h5Page == null) {
            H5Log.e(this.a, (String) "error! can't find target H5Page");
            return;
        }
        h5Page.getBridge().sendToWeb(new Builder().action("message").param(joMessage).type("call").build());
    }

    /* access modifiers changed from: protected */
    public final void a(String action, JSONObject jsonObject, H5ServiceWorkerHook4Bridge h5ServiceWorkerHook4Bridge, String workerId) {
        final JSONObject jSONObject = jsonObject;
        final String str = workerId;
        final String str2 = action;
        final H5ServiceWorkerHook4Bridge h5ServiceWorkerHook4Bridge2 = h5ServiceWorkerHook4Bridge;
        d(action).post(new Runnable() {
            public void run() {
                H5Page h5Page;
                try {
                    if (!jSONObject.containsKey("viewId")) {
                        h5Page = H5WorkerControllerProvider.this.a(-1, str);
                    } else {
                        h5Page = H5WorkerControllerProvider.this.a(H5Utils.getInt(jSONObject, (String) "viewId", -1), str);
                    }
                    if (h5Page == null) {
                        if (H5WorkerControllerProvider.this.g == null && WorkerManager.sParams != null) {
                            H5WorkerControllerProvider.this.g = new MockH5PageImpl(WorkerManager.sParams);
                        }
                        h5Page = H5WorkerControllerProvider.this.g;
                    }
                    if (h5Page == null) {
                        H5Log.w(H5WorkerControllerProvider.this.a, "handleMsgFromJs h5Page == null return");
                        H5LogProvider h5LogProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
                        if (h5LogProvider != null) {
                            h5LogProvider.log("H5_SW_PageNull", null, str2, str, jSONObject);
                            return;
                        }
                        return;
                    }
                    JSONObject params = H5Utils.getJSONObject(jSONObject, "data", null);
                    String clientId = H5Utils.getString(jSONObject, (String) "clientId", (String) null);
                    if (TextUtils.isEmpty(clientId)) {
                        clientId = Long.toString(System.nanoTime());
                    }
                    H5Log.d(H5WorkerControllerProvider.this.a, "clientId is " + clientId);
                    Builder builder = new Builder();
                    if (H5Utils.isMain()) {
                        builder.action(str2).param(params).target(h5Page).type("call").id(clientId).keepCallback(false);
                    } else {
                        builder.action(str2).param(params).target(h5Page).type("call").id(clientId).keepCallback(false).dispatcherOnWorkerThread(true);
                    }
                    H5Event event = builder.build();
                    H5Bridge h5Bridge = h5Page.getBridge();
                    H5BridgeContext context = new H5WorkerBridgeContext(h5ServiceWorkerHook4Bridge2, clientId, str2, h5Bridge, h5Page);
                    if (h5Bridge != null) {
                        H5Log.d(H5WorkerControllerProvider.this.a, "use bridge send event");
                        h5Bridge.sendToNative(event, context);
                        return;
                    }
                    H5Log.d(H5WorkerControllerProvider.this.a, "use service send event");
                    H5Service h5Service = H5ServiceUtils.getH5Service();
                    if (h5Service != null) {
                        h5Service.sendEvent(event, context);
                    }
                } catch (Throwable e2) {
                    H5Log.e(H5WorkerControllerProvider.this.a, e2);
                }
            }
        });
    }

    private Handler d(String action) {
        if (i.contains(action) && d()) {
            return H5EventDispatchHandler.getAsyncHandler();
        }
        if (this.h == null) {
            this.h = new Handler(Looper.getMainLooper());
        }
        return this.h;
    }

    private static boolean d() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableSWAsync"))) {
            return true;
        }
        return false;
    }

    static {
        ArrayList arrayList = new ArrayList();
        i = arrayList;
        arrayList.add("getSystemInfo");
        i.add("remoteLog");
        i.add("httpRequest");
        i.add(H5LoggerPlugin.REPORT_DATA);
        i.add(H5EventHandler.getAuthCode);
        i.add(TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE);
        i.add(TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE);
        i.add(TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE);
        i.add(TinyLoggingConfigPlugin.ACTION_TRACKER_CONFIG);
        i.add(H5LoggerPlugin.REPORT_DATA);
        i.add(ConfigPlugin.ACTION);
        i.add(H5EventHandler.getAuthUserInfo);
        i.add("localLog");
    }
}
