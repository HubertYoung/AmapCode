package com.alipay.mobile.nebulacore.bridge;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventDispatchHandler;
import com.alipay.mobile.h5container.api.H5JsCallData;
import com.alipay.mobile.h5container.api.H5JsCallData.Builder;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.provider.H5JSApiPermissionProvider;
import com.alipay.mobile.nebula.provider.H5NewJSApiPermissionProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5BridgeContextImpl;
import com.alipay.mobile.nebulacore.core.H5BridgeRunnable;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SessionPlugin;
import com.alipay.mobile.nebulacore.util.H5JSReplaceUtil;
import com.alipay.mobile.nebulacore.web.H5WebView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5BridgeImpl implements H5Bridge {
    public static final String TAG = "H5BridgeImpl";
    private static List<String> h = new ArrayList();
    private static List<String> i = new ArrayList();
    /* access modifiers changed from: private */
    public H5WebView a;
    private Map<String, H5CallBack> b;
    private Map<String, Long> c;
    private boolean d;
    private H5Page e;
    private List<String> f = new ArrayList();
    /* access modifiers changed from: private */
    public List<Integer> g = new ArrayList();
    private int j = 10000;
    private String k;

    static {
        h.add("JSPlugin_AlipayH5Share");
        h.add("beforeunload");
        h.add("message");
        h.add("nbcomponent.canrender");
        i.add(H5SessionPlugin.SHOW_NETWORK_CHECK_ACTIVITY);
        i.add(H5AlertPlugin.showUCFailDialog);
        i.add("setKeyboardType");
        i.add(H5Param.MONITOR_PERFORMANCE);
        i.add("getStartupParams");
    }

    public H5BridgeImpl(H5WebView webView, H5Page h5Page) {
        this.a = webView;
        this.d = false;
        this.b = new ConcurrentHashMap();
        this.c = new ConcurrentHashMap();
        this.e = h5Page;
        this.k = H5Utils.getString(h5Page.getParams(), (String) "appId");
        try {
            a();
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }

    public void onRelease() {
        this.d = true;
        this.a = null;
        this.b.clear();
        this.c.clear();
        this.c = null;
        this.b = null;
        this.e = null;
    }

    public void sendToNative(H5Event event) {
        if (event != null && !this.d) {
            a(event, (H5BridgeContext) null);
        }
    }

    public void sendToNative(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event != null && !this.d) {
            a(h5Event, h5BridgeContext);
        }
    }

    private void a(final H5Event event, final H5BridgeContext h5BridgeContext) {
        if (!event.isDispatcherOnWorkerThread()) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5BridgeImpl.this.b(event, h5BridgeContext);
                }
            });
        } else if (H5Utils.isMain()) {
            H5EventDispatchHandler.getAsyncHandler().post(new Runnable() {
                public void run() {
                    H5BridgeImpl.this.b(event, h5BridgeContext);
                }
            });
        } else {
            b(event, h5BridgeContext);
        }
    }

    /* access modifiers changed from: private */
    public void b(H5Event event, H5BridgeContext h5BridgeContext) {
        H5BridgeContext context;
        if (event != null && !this.d) {
            String eventId = event.getId();
            boolean poolEvent = this.b.containsKey(eventId);
            JSONObject callParam = event.getParam();
            if (poolEvent) {
                H5CallBack callback = this.b.remove(eventId);
                if (callback != null) {
                    callback.onCallBack(callParam);
                }
                H5Log.d(TAG, "H5 callback for " + eventId);
                return;
            }
            String action = event.getAction();
            boolean showLog = true;
            if (TextUtils.equals(action, "NBComponent.sendMessage") && callParam != null && TextUtils.equals("draw", H5Utils.getString(callParam, (String) "actionType"))) {
                showLog = false;
            }
            this.c.put(event.getId(), Long.valueOf(System.currentTimeMillis()));
            String paramStr = null;
            if (callParam != null) {
                if (showLog) {
                    paramStr = callParam.toJSONString();
                }
                callParam.put((String) "funcName", (Object) action);
            }
            if (showLog) {
                H5Log.d(TAG, "jsapi req name={" + action + "} eventId " + eventId + Token.SEPARATOR + paramStr);
            }
            if (paramStr != null && paramStr.length() > this.j && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_notLogForMaxReq"))) {
                H5Log.d(TAG, "match size substring " + paramStr.length());
                paramStr = paramStr.substring(0, this.j);
            }
            H5JsCallData jsCallData = new Builder().setAction(action).setElapse(-1).setJoMsg(paramStr).build();
            if (this.e.getPageData() != null) {
                this.e.getPageData().addJsapiInfo(event.getId(), jsCallData);
            }
            boolean isH5Page = event.getTarget() instanceof H5Page;
            if (isH5Page && H5ProviderManagerImpl.getInstance().getProvider(H5DevDebugProvider.class.getName()) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "subType", (Object) action);
                jsonObject.put((String) "request", (Object) paramStr);
                jsonObject.put((String) "eventId", (Object) eventId);
                ((H5Page) event.getTarget()).sendEvent(CommonEvents.H5_DEV_JS_API_TO_NATIVE, jsonObject);
            }
            if (h5BridgeContext != null) {
                context = h5BridgeContext;
            } else {
                context = new H5BridgeContextImpl(this, eventId, action);
            }
            if (isH5Page) {
                H5Page h5Page = (H5Page) event.getTarget();
                String url = h5Page.getUrl();
                String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                int shouldInterceptJSApiCallFromTiny = 0;
                if (Nebula.getH5TinyAppService() != null) {
                    shouldInterceptJSApiCallFromTiny = Nebula.getH5TinyAppService().shouldInterceptJSApiCall(event, appId, context, h5Page);
                }
                if (showLog) {
                    H5Log.d(TAG, "shouldInterceptJSApiCall result " + shouldInterceptJSApiCallFromTiny);
                }
                if (shouldInterceptJSApiCallFromTiny == 0) {
                    String h5_newJsapiPermissionConfigStr = null;
                    boolean canIntercept = false;
                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    if (h5ConfigProvider != null) {
                        h5_newJsapiPermissionConfigStr = h5ConfigProvider.getConfigWithProcessCache("h5_newJsapiPermissionConfig");
                    }
                    JSONObject h5_newJsapiPermissionConfigObj = H5Utils.parseObject(h5_newJsapiPermissionConfigStr);
                    if (h5_newJsapiPermissionConfigObj != null && !h5_newJsapiPermissionConfigObj.isEmpty()) {
                        canIntercept = h5_newJsapiPermissionConfigObj.getBoolean("canIntercept").booleanValue();
                    }
                    H5NewJSApiPermissionProvider h5NewJSApiPermissionProvider = null;
                    if (canIntercept) {
                        h5NewJSApiPermissionProvider = (H5NewJSApiPermissionProvider) H5Utils.getProvider(H5NewJSApiPermissionProvider.class.getName());
                    }
                    int status = 2;
                    if (canIntercept && h5NewJSApiPermissionProvider != null) {
                        status = h5NewJSApiPermissionProvider.hasPermissionByUrl(url, action, callParam);
                    }
                    if (showLog) {
                        H5Log.d(TAG, "hasPermissionByUrl result " + status + ", url " + url);
                    }
                    if (status == 0) {
                        if (showLog) {
                            H5Log.e((String) TAG, "use new permission deny action " + action + " pageurl " + url);
                        }
                        context.sendNoRigHtToInvoke4NewJSAPIPermission();
                        return;
                    } else if (status == 1) {
                        if (showLog) {
                            H5Log.d(TAG, "use new permission allow");
                        }
                    } else if (status == 2) {
                        if (showLog) {
                            H5Log.d(TAG, "use old permission");
                        }
                        H5JSApiPermissionProvider provider = (H5JSApiPermissionProvider) H5ProviderManagerImpl.getInstance().getProvider(H5JSApiPermissionProvider.class.getName());
                        boolean oldPermission = true;
                        if (provider != null) {
                            oldPermission = provider.hasDomainPermission(action, url);
                        }
                        if (!oldPermission) {
                            context.sendNoRigHtToInvoke();
                            if (showLog) {
                                H5Log.e((String) TAG, "use old permission deny action " + action + " pageurl " + url);
                                return;
                            }
                            return;
                        }
                    }
                } else if (shouldInterceptJSApiCallFromTiny == 1) {
                    if (showLog) {
                        H5Log.e((String) TAG, "use tinypermission deny action " + action + " pageurl " + url);
                        return;
                    }
                    return;
                } else if (shouldInterceptJSApiCallFromTiny == 2 && showLog) {
                    H5Log.d(TAG, "no permissionfile or in whiteJsApiJsonArray");
                }
            }
            Nebula.getDispatcher().dispatch(event, context);
        }
    }

    public void sendToWeb(H5Event event) {
        if (event != null && !this.d) {
            a(event);
        }
    }

    private void a(final H5Event event) {
        if (!"yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_postWebOnUi"))) {
            try {
                b(event);
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        } else {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    try {
                        H5BridgeImpl.this.b(event);
                    } catch (Throwable throwable) {
                        H5Log.e((String) H5BridgeImpl.TAG, throwable);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void b(H5Event event) {
        long executeWebTime = System.currentTimeMillis();
        if (event != null && this.a != null) {
            String eventId = event.getId();
            String action = event.getAction();
            JSONObject param = event.getParam();
            String type = event.getType();
            boolean keep = event.isKeepCallback();
            JSONObject jo = new JSONObject();
            jo.put((String) "clientId", (Object) eventId);
            jo.put((String) "func", (Object) action);
            jo.put((String) "param", (Object) param);
            jo.put((String) "msgType", (Object) type);
            jo.put((String) H5Param.KEEP_CALLBACK, (Object) Boolean.valueOf(keep));
            String javascript = "AlipayJSBridge._invokeJS(" + JSON.toJSONString(jo.toJSONString()) + ")";
            monitorBridgeLog(action, param, eventId);
            if ((this.c == null || !this.c.containsKey(eventId)) && this.e != null) {
                String appId = H5Utils.getString(this.e.getParams(), (String) "appId");
                boolean hasPermissionFile = false;
                if (Nebula.getH5TinyAppService() != null) {
                    hasPermissionFile = Nebula.getH5TinyAppService().hasPermissionFile(appId, this.e);
                }
                if (hasPermissionFile) {
                    if (!a(event, appId, this.e) && !h.contains(action)) {
                        if (this.b.containsKey(eventId)) {
                            H5CallBack callback = this.b.remove(eventId);
                            if (callback != null) {
                                JSONObject callParam = new JSONObject();
                                callParam.put((String) "prevent", (Object) Boolean.valueOf(false));
                                callback.onCallBack(callParam);
                                H5Log.d(TAG, action + " H5 callback for " + eventId);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
            }
            try {
                if (!eventId.startsWith("native_") && this.c != null && this.c.containsKey(eventId) && !keep) {
                    long nowTime = System.currentTimeMillis();
                    long cost = nowTime - this.c.get(eventId).longValue();
                    H5Log.d(TAG, action + " js_native_is cost time " + cost + " executeWebTime:" + (nowTime - executeWebTime));
                    if (!(this.e == null || this.e.getPageData() == null)) {
                        H5JsCallData jsCallData = this.e.getPageData().getJsapiInfo(eventId);
                        if (jsCallData != null) {
                            jsCallData.setElapse(cost);
                        }
                    }
                }
                if (!a(action, javascript)) {
                    this.a.loadUrl("javascript:(function(){if(typeof AlipayJSBridge === 'object'){" + javascript + "}})();");
                    H5Log.d(TAG, "jsapi rep:" + javascript);
                }
            } catch (Exception e2) {
                H5Log.e(TAG, "loadUrl exception", e2);
            }
        }
    }

    private boolean a(String action, final String javascript) {
        if (H5JSReplaceUtil.enableReplace() && H5JSReplaceUtil.containAction(action)) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    String res = H5JSReplaceUtil.replaceInvisibleStr(javascript);
                    if (H5BridgeImpl.this.a != null) {
                        H5BridgeImpl.this.a.loadUrl("javascript:(function(){if(typeof AlipayJSBridge === 'object'){" + res + "}})();");
                        H5Log.d(H5BridgeImpl.TAG, "H5JSReplaceUtil fixJson jsapi rep:" + res);
                    }
                }
            });
            return true;
        } else if (!this.f.contains(action)) {
            return false;
        } else {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    String res = "";
                    long time = System.currentTimeMillis();
                    for (int i = 0; i < javascript.length(); i++) {
                        if (H5BridgeImpl.this.g == null || !H5BridgeImpl.this.g.contains(Integer.valueOf(javascript.charAt(i)))) {
                            res = res + javascript.charAt(i);
                        } else {
                            H5Log.d(H5BridgeImpl.TAG, "contain white" + javascript.charAt(i) + " not add");
                        }
                    }
                    H5Log.d(H5BridgeImpl.TAG, "coast " + (System.currentTimeMillis() - time) + " for action fixJson");
                    if (H5BridgeImpl.this.a != null) {
                        H5BridgeImpl.this.a.loadUrl("javascript:(function(){if(typeof AlipayJSBridge === 'object'){" + res + "}})();");
                        H5Log.d(H5BridgeImpl.TAG, "fixJson jsapi rep:" + res);
                    }
                }
            });
            return true;
        }
    }

    private void a() {
        String value = H5Environment.getConfig("h5_Whitespace");
        if (!TextUtils.isEmpty(value)) {
            JSONObject whiteChartAppList = H5Utils.parseObject(value);
            if (whiteChartAppList != null && whiteChartAppList.containsKey(this.k)) {
                String apiValue = H5Utils.getString(whiteChartAppList, this.k);
                if (!TextUtils.isEmpty(apiValue)) {
                    this.f = Arrays.asList(apiValue.split("\\|"));
                    String codeValue = H5Utils.getString(whiteChartAppList, (String) "whitespace_code");
                    if (!TextUtils.isEmpty(codeValue)) {
                        for (String s : Arrays.asList(codeValue.split("\\|"))) {
                            this.g.add(Integer.valueOf(Integer.parseInt(s)));
                        }
                    }
                }
            }
        }
    }

    private static boolean a(H5Event h5Event, String appId, H5Page h5Page) {
        if (Nebula.getH5TinyAppService() == null || Nebula.getH5TinyAppService().setPermission(h5Event, appId, null, true, h5Page)) {
            return true;
        }
        return false;
    }

    public void sendToWeb(String action, JSONObject param, H5CallBack callback) {
        HashMap data;
        JSONObject jo;
        JSONObject subData;
        if (!this.d) {
            H5Event event = new H5Event.Builder().action(action).param(param).type("call").build();
            if (callback != null) {
                this.b.put(event.getId(), callback);
            }
            if (this.e != null) {
                H5Session h5Session = this.e.getSession();
                if (h5Session != null) {
                    String workerId = h5Session.getServiceWorkerID();
                    if (!TextUtils.isEmpty(workerId)) {
                        H5Log.d(TAG, "sendToWeb workerId " + workerId);
                        H5Service h5Service = H5ServiceUtils.getH5Service();
                        if (h5Service != null) {
                            try {
                                data = new HashMap();
                                data.put("appId", workerId);
                                jo = new JSONObject();
                                jo.put((String) "func", (Object) action);
                                if (param != null) {
                                    subData = param.getJSONObject("data");
                                    if (subData == null) {
                                        subData = new JSONObject();
                                        param.put((String) "data", (Object) subData);
                                    }
                                    subData.put((String) "NBPageUrl", (Object) this.e.getUrl());
                                    jo.put((String) "param", (Object) param);
                                    if (b()) {
                                        jo.put((String) "viewId", (Object) Integer.valueOf(this.e.getWebViewId()));
                                    }
                                } else {
                                    JSONObject tmpParam = new JSONObject();
                                    JSONObject subData2 = new JSONObject();
                                    subData2.put((String) "NBPageUrl", (Object) this.e.getUrl());
                                    tmpParam.put((String) "data", (Object) subData2);
                                    jo.put((String) "param", (Object) tmpParam);
                                    if (b()) {
                                        jo.put((String) "viewId", (Object) Integer.valueOf(this.e.getWebViewId()));
                                    }
                                }
                            } catch (Throwable t) {
                                H5Log.e(TAG, "catch exception ", t);
                            }
                            data.put("message", jo.toJSONString());
                            data.put("messageId", System.currentTimeMillis());
                            if (!a(action) || callback == null) {
                                h5Service.sendServiceWorkerPushMessage(data);
                            } else {
                                h5Service.sendServiceWorkerPushMessage(data, callback);
                            }
                        }
                    }
                }
            }
            sendToWeb(event);
        }
    }

    private static boolean a(String action) {
        return Nebula.appResume.equals(action);
    }

    private static boolean b() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_enable_viewId_event"));
    }

    public void sendDataWarpToWeb(String action, JSONObject jsonObject, H5CallBack h5CallBack) {
        if (jsonObject == null || jsonObject.isEmpty()) {
            sendToWeb(action, jsonObject, h5CallBack);
            return;
        }
        JSONObject data = new JSONObject();
        data.put((String) "data", (Object) jsonObject);
        sendToWeb(action, data, h5CallBack);
    }

    public void monitorBridgeLog(String action, JSONObject param, String eventId) {
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new H5BridgeRunnable(this.e, param, this.c, action, eventId));
    }
}
