package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickNegativeListener;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.basebridge.H5BaseBridgeContext;
import com.alipay.mobile.nebula.provider.H5OpenAuthProvider;
import com.alipay.mobile.nebula.provider.H5OpenAuthProvider.OnGetAuthListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.utils.Callback;
import com.alipay.mobile.tinyappcommon.utils.TinyAppEventUtils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.sdk.cons.c;
import com.taobao.accs.common.Constants;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TinyAppMTopPlugin extends H5SimplePlugin {
    public static final String ACTION_GET_TB_CODE = "getTBCode";
    public static final String ACTION_GET_TB_SINFO = "getTBSessionInfo";
    public static final String ACTION_SEND_MTOP = "sendMtop";
    public static final String ACTION_SET_TB_SINFO = "setTBSessionInfo";
    /* access modifiers changed from: private */
    public ConcurrentLinkedQueue<Runnable> a = new ConcurrentLinkedQueue<>();
    /* access modifiers changed from: private */
    public AtomicBoolean b = new AtomicBoolean(false);

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_SEND_MTOP);
        filter.addAction(ACTION_GET_TB_CODE);
        filter.addAction(ACTION_GET_TB_SINFO);
        filter.addAction(ACTION_SET_TB_SINFO);
    }

    public void onRelease() {
        this.b.set(false);
        this.a.clear();
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        String action = event.getAction();
        if (ACTION_SEND_MTOP.equals(action)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    TinyAppMTopPlugin.this.c(event, context);
                }
            });
            return true;
        } else if (ACTION_GET_TB_CODE.equals(action)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    TinyAppMTopPlugin.this.d(event, context);
                }
            });
            return true;
        } else if (ACTION_GET_TB_SINFO.equals(action)) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    TinyAppMTopPlugin.e(event, context);
                }
            });
            return true;
        } else if (!ACTION_SET_TB_SINFO.equals(action)) {
            return false;
        } else {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public void run() {
                    TinyAppMTopPlugin.f(event, context);
                }
            });
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void c(H5Event event, H5BridgeContext context) {
        JSONObject eventParams = event.getParam();
        H5Page h5Page = event.getH5page();
        if (eventParams == null || h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        final H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        final String appId = TinyAppParamUtils.getAppId(h5Page.getParams());
        if (TextUtils.isEmpty(appId)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String api = H5Utils.getString(eventParams, (String) MtopJSParam.API);
        String apiName = H5Utils.getString(eventParams, (String) c.n);
        if (!TextUtils.isEmpty(api) && TextUtils.isEmpty(apiName)) {
            eventParams.put((String) c.n, (Object) api);
        }
        String v = H5Utils.getString(eventParams, (String) "v");
        String version = H5Utils.getString(eventParams, (String) c.m);
        if (!TextUtils.isEmpty(v) && TextUtils.isEmpty(version)) {
            eventParams.put((String) c.m, (Object) v);
        }
        String dataType = H5Utils.getString(eventParams, (String) "dataType");
        String type = H5Utils.getString(eventParams, (String) "type");
        if (!TextUtils.isEmpty(dataType) && TextUtils.isEmpty(type)) {
            eventParams.put((String) "type", (Object) dataType);
        }
        if (TinyAppEventUtils.contains(event, (String) MtopJSParam.NEED_LOGIN) && !TinyAppEventUtils.contains(eventParams, (String) "needEncodeSign")) {
            eventParams.put((String) "needEncodeSign", (Object) Boolean.valueOf(H5Utils.getBoolean(eventParams, (String) MtopJSParam.NEED_LOGIN, false)));
        }
        if (TinyAppEventUtils.contains(event, (String) MtopJSParam.EXT_HEADERS) && !TinyAppEventUtils.contains(eventParams, (String) "headers")) {
            eventParams.put((String) "headers", (Object) H5Utils.getJSONObject(eventParams, MtopJSParam.EXT_HEADERS, new JSONObject()));
        }
        if (H5Utils.getString(eventParams, (String) "method").equalsIgnoreCase("POST")) {
            eventParams.put((String) "usePost", (Object) Boolean.valueOf(true));
        }
        if (!H5Utils.getBoolean(TinyAppConfig.getInstance().getTaoBaoAppInfo(appId), (String) "licenseEnable", true)) {
            JSONObject mTopParams = eventParams;
            if (eventParams == null) {
                mTopParams = new JSONObject();
            }
            a(mTopParams, appId);
            final H5BridgeContext h5BridgeContext = context;
            h5Service.sendEvent(new Builder(event).action("mtop").param(mTopParams).build(), new H5BaseBridgeContext() {
                public boolean sendBack(JSONObject param, boolean keep) {
                    h5BridgeContext.sendBridgeResult(param);
                    return false;
                }
            });
            return;
        }
        final String userId = WalletTinyappUtils.getUserId();
        String taoBaoSessionId = b(userId, appId);
        if (TextUtils.isEmpty(taoBaoSessionId)) {
            final H5BridgeContext h5BridgeContext2 = context;
            final H5Event h5Event = event;
            AnonymousClass6 r2 = new Callback<String>() {
                public void callback(String sid) {
                    if (TextUtils.isEmpty(sid)) {
                        h5BridgeContext2.sendError(3, (String) "unknown error");
                        return;
                    }
                    TinyAppMTopPlugin.c(userId, appId, sid);
                    TinyAppMTopPlugin.this.a(h5Event, h5BridgeContext2, h5Service, userId, appId, sid);
                }
            };
            if (h(userId, appId)) {
                a(event, h5Service, (Callback<String>) r2);
                return;
            }
            final String str = userId;
            final String str2 = appId;
            final H5BridgeContext h5BridgeContext3 = context;
            final H5Event h5Event2 = event;
            final H5Service h5Service2 = h5Service;
            final AnonymousClass6 r16 = r2;
            a(event, context, h5Service, appId, userId, (Callback<Boolean>) new Callback<Boolean>() {
                public void callback(Boolean auth) {
                    TinyAppMTopPlugin.b(str, str2, auth.booleanValue());
                    if (!auth.booleanValue()) {
                        h5BridgeContext3.sendError(h5Event2, Error.UNKNOWN_ERROR);
                    } else {
                        TinyAppMTopPlugin.this.a(h5Event2, h5BridgeContext3, h5Service2, str2, r16);
                    }
                }
            });
            return;
        }
        a(event, context, h5Service, userId, appId, taoBaoSessionId);
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, H5BridgeContext context, H5Service h5Service, String userId, String appId, String taoBaoSessionId) {
        if (H5Utils.isDebug()) {
            H5Log.d("TinyAppMTopPlugin", "sendMtop taobao session id " + taoBaoSessionId);
        }
        String accessToken = d(userId, appId);
        if (TextUtils.isEmpty(accessToken)) {
            final H5BridgeContext h5BridgeContext = context;
            final String str = userId;
            final String str2 = appId;
            final H5Event h5Event = event;
            final H5Service h5Service2 = h5Service;
            final String accessToken2 = taoBaoSessionId;
            a(event, context, h5Service, appId, new Callback<String>() {
                public void callback(String token) {
                    if (TextUtils.isEmpty(token)) {
                        h5BridgeContext.sendError(3, (String) "unknown error");
                        return;
                    }
                    TinyAppMTopPlugin.d(str, str2, token);
                    TinyAppMTopPlugin.this.a(h5Event, h5BridgeContext, h5Service2, str, str2, accessToken2, token);
                }
            });
            return;
        }
        a(event, context, h5Service, userId, appId, taoBaoSessionId, accessToken);
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, H5BridgeContext context, H5Service h5Service, String userId, String appId, String taoBaoSessionId, String accessToken) {
        if (H5Utils.isDebug()) {
            H5Log.d("TinyAppMTopPlugin", "userId=" + userId + " appid= " + appId + " sid= " + taoBaoSessionId + " token=" + accessToken);
        }
        final JSONObject eventParams = event.getParam();
        JSONObject mTopParams = eventParams;
        if (eventParams == null) {
            mTopParams = new JSONObject();
        }
        a(mTopParams, appId);
        JSONObject headers = H5Utils.getJSONObject(mTopParams, "headers", new JSONObject());
        headers.put((String) "x-exttype", (Object) "isv_open_api");
        headers.put((String) "x-extdata", (Object) "openappkey=" + b(appId) + ";accesstoken=" + accessToken);
        mTopParams.put((String) "isvOpenAppkey", (Object) b(appId));
        mTopParams.put((String) "isvAccessToken", (Object) accessToken);
        final String str = userId;
        final String str2 = appId;
        final H5Event h5Event = event;
        final H5BridgeContext h5BridgeContext = context;
        h5Service.sendEvent(new Builder(event).action("mtop").param(mTopParams).build(), new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject param, boolean keep) {
                if (!TinyAppMTopPlugin.b(param) || H5Utils.getBoolean(eventParams, (String) "RETRIED_TOKEN", false) || eventParams == null) {
                    h5BridgeContext.sendBridgeResult(param);
                } else {
                    eventParams.put((String) "RETRIED_TOKEN", (Object) Boolean.valueOf(true));
                    TinyAppMTopPlugin.d(str, str2, "");
                    TinyAppMTopPlugin.this.c(h5Event, h5BridgeContext);
                }
                return false;
            }
        });
    }

    private static void a(JSONObject mTopParams, String appId) {
        String taoBaoMiniAppId = a(appId);
        if (!TextUtils.isEmpty(taoBaoMiniAppId)) {
            JSONObject headers = H5Utils.getJSONObject(mTopParams, "headers", new JSONObject());
            if (headers.size() == 0) {
                mTopParams.put((String) "headers", (Object) headers);
            }
            headers.put((String) "x-miniapp-id-taobao", (Object) taoBaoMiniAppId);
            headers.put((String) "x-miniapp-id-alipay", (Object) appId);
            JSONObject data = H5Utils.getJSONObject(mTopParams, "data", new JSONObject());
            if (data.size() == 0) {
                mTopParams.put((String) "data", (Object) data);
            }
            if (!H5Utils.contains(data, (String) "appId")) {
                data.put((String) "appId", (Object) taoBaoMiniAppId);
            }
            String taoBaoMiniAppKey = b(appId);
            if (!H5Utils.contains(data, (String) "appkey")) {
                data.put((String) "appkey", (Object) taoBaoMiniAppKey);
            }
            if (!H5Utils.contains(data, (String) "appKey")) {
                data.put((String) "appKey", (Object) taoBaoMiniAppKey);
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(JSONObject result) {
        JSONArray ret = H5Utils.getJSONArray(result, "ret", new JSONArray());
        if (ret.size() == 0) {
            return false;
        }
        int size = ret.size();
        for (int i = 0; i < size; i++) {
            Object obj = ret.get(i);
            if (obj != null && obj.toString().startsWith("FAIL_SYS_TOPAUTH_ACCESSTOKENEXPIRED_ERROR::")) {
                return true;
            }
        }
        return false;
    }

    private static String a(String appId) {
        Object taoBaoId = TinyAppConfig.getInstance().getTaoBaoAppInfo(appId).get("appId");
        return taoBaoId != null ? taoBaoId.toString() : "";
    }

    private static String b(String appId) {
        return H5Utils.getString(TinyAppConfig.getInstance().getTaoBaoAppInfo(appId), (String) "appKey");
    }

    private static String b(String userId, String appId) {
        return H5SharedPreferenceStorage.getInstance().getString(c(userId, appId));
    }

    /* access modifiers changed from: private */
    public static void c(String userId, String appId, String sessionId) {
        H5SharedPreferenceStorage.getInstance().putString(appId, c(userId, appId), sessionId);
    }

    private static String c(String userId, String appId) {
        return userId + "_" + appId + "_ta_tb_sid";
    }

    private void a(H5Event event, H5BridgeContext context, H5Service h5Service, String appId, String userId, Callback<Boolean> callback) {
        JSONObject getAuthInfoData = new JSONObject();
        getAuthInfoData.put((String) "appId", (Object) a(appId));
        getAuthInfoData.put((String) "appKey", (Object) b(appId));
        JSONObject genTokenParams = new JSONObject();
        genTokenParams.put((String) c.n, (Object) "mtop.taobao.openlink.auth.info.get");
        genTokenParams.put((String) c.m, (Object) "1.0");
        genTokenParams.put((String) "data", (Object) getAuthInfoData);
        final H5BridgeContext h5BridgeContext = context;
        final String str = userId;
        final String str2 = appId;
        final Callback<Boolean> callback2 = callback;
        final H5Event h5Event = event;
        h5Service.sendEvent(new Builder(event).action("mtop").param(genTokenParams).build(), new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject param, boolean keep) {
                if (H5Utils.contains(param, (String) "error")) {
                    h5BridgeContext.sendBridgeResult(param);
                    return false;
                }
                JSONObject data = H5Utils.getJSONObject(param, "data", new JSONObject());
                JSONArray authHintArray = H5Utils.getJSONArray(data, "authHint", new JSONArray());
                String logo = H5Utils.getString(data, (String) "logo");
                String protocolName = H5Utils.getString(data, (String) "protocolName");
                String protocolUrl = H5Utils.getString(data, (String) "protocolUrl");
                if (H5Utils.isDebug()) {
                    H5Log.d("TinyAppMTopPlugin", "logo=" + logo + " protocolName=" + protocolName + " protocolUrl=" + protocolUrl);
                }
                final List protocolList = new ArrayList();
                protocolList.add(protocolName);
                final List protocolLink = new ArrayList();
                protocolLink.add(protocolUrl);
                final List authText = new ArrayList();
                int size = authHintArray.size();
                for (int i = 0; i < size; i++) {
                    authText.add(authHintArray.getString(i));
                }
                final Runnable authTaoBaoDialogRelaunchTask = new Runnable() {
                    public void run() {
                        Runnable authTaoBaoDialogTask = (Runnable) TinyAppMTopPlugin.this.a.poll();
                        if (authTaoBaoDialogTask == null) {
                            TinyAppMTopPlugin.this.b.set(false);
                        } else {
                            H5Utils.runOnMain(authTaoBaoDialogTask);
                        }
                    }
                };
                Runnable authTaoBaoDialogTask = new Runnable() {
                    public void run() {
                        if (TinyAppMTopPlugin.h(str, str2)) {
                            callback2.callback(Boolean.valueOf(true));
                            authTaoBaoDialogRelaunchTask.run();
                            return;
                        }
                        H5OpenAuthProvider provider = (H5OpenAuthProvider) H5Utils.getProvider(H5OpenAuthProvider.class.getName());
                        if (provider == null || h5Event.getActivity() == null) {
                            h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                            authTaoBaoDialogRelaunchTask.run();
                            return;
                        }
                        provider.getAuthCodeLocal(h5Event.getActivity(), WalletTinyappUtils.getAppNameByAppId(str2), authText, protocolList, protocolLink, new OnGetAuthListener() {
                            public void onResult(JSONObject result) {
                                if (H5Utils.contains(result, (String) "error")) {
                                    H5Log.d("TinyAppMTopPlugin", "auth error: " + result);
                                    AUNoticeDialog dialog = new AUNoticeDialog(h5Event.getActivity(), null, "小程序需授权后才能正常使用，请先授权登录，谢谢！", "点击授权", "退出小程序");
                                    dialog.setPositiveListener(new OnClickPositiveListener() {
                                        public void onClick() {
                                            callback2.callback(Boolean.valueOf(true));
                                            authTaoBaoDialogRelaunchTask.run();
                                        }
                                    });
                                    dialog.setNegativeListener(new OnClickNegativeListener() {
                                        public void onClick() {
                                            h5BridgeContext.sendError(3, (String) "未授权");
                                            TinyAppMTopPlugin.b(str, str2, false);
                                            LauncherApplicationAgent.getInstance().getMicroApplicationContext().finishApp(str2, str2, null);
                                        }
                                    });
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setCancelable(false);
                                    dialog.show();
                                    return;
                                }
                                if (H5Utils.getBoolean(result, (String) "success", false)) {
                                    if (callback2 != null) {
                                        callback2.callback(Boolean.valueOf(true));
                                    }
                                } else if (callback2 != null) {
                                    callback2.callback(Boolean.valueOf(false));
                                }
                                authTaoBaoDialogRelaunchTask.run();
                            }
                        });
                    }
                };
                if (TinyAppMTopPlugin.this.b.get()) {
                    TinyAppMTopPlugin.this.a.offer(authTaoBaoDialogTask);
                } else {
                    TinyAppMTopPlugin.this.b.set(true);
                    H5Utils.runOnMain(authTaoBaoDialogTask);
                }
                return false;
            }
        });
    }

    private void a(H5Event event, H5Service h5Service, final Callback<String> callback) {
        JSONObject aliAutoLoginParams = new JSONObject();
        aliAutoLoginParams.put((String) "H5AutoLoginUrl", (Object) "https://h5.m.taobao.com/");
        h5Service.sendEvent(new Builder(event).action("aliAutoLogin").param(aliAutoLoginParams).build(), new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject param, boolean keep) {
                String sid = H5Utils.getString(param, (String) Constants.KEY_SID);
                if (H5Utils.isDebug()) {
                    String resultCode = H5Utils.getString(param, (String) "resultCode");
                    String resultMemo = H5Utils.getString(param, (String) "resultMemo");
                    String ecode = H5Utils.getString(param, (String) "ecode");
                    String tbUserId = H5Utils.getString(param, (String) "tbUserId");
                    String tbNick = H5Utils.getString(param, (String) "tbNick");
                    H5Log.d("TinyAppMTopPlugin", "resultCode:" + resultCode + " , resultMemo:" + resultMemo + " , sid:" + sid + " , ecode:" + ecode + " , tbUserId:" + tbUserId + " , tbNick:" + tbNick + " ,redirectUrl:" + H5Utils.getString(param, (String) "redirectUrl"));
                }
                if (callback != null) {
                    callback.callback(sid);
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, final H5BridgeContext context, H5Service h5Service, String appId, final Callback<String> callback) {
        JSONObject genTokenData = new JSONObject();
        genTokenData.put((String) "appId", (Object) a(appId));
        genTokenData.put((String) "appkey", (Object) b(appId));
        JSONObject genTokenParams = new JSONObject();
        genTokenParams.put((String) c.n, (Object) "mtop.taobao.top.oauthtoken.generate");
        genTokenParams.put((String) c.m, (Object) "2.0");
        genTokenParams.put((String) "data", (Object) genTokenData);
        h5Service.sendEvent(new Builder(event).action("mtop").param(genTokenParams).build(), new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject param, boolean keep) {
                if (H5Utils.contains(param, (String) "error")) {
                    context.sendBridgeResult(param);
                } else {
                    String accessToken = H5Utils.getString(H5Utils.getJSONObject(param, "data", new JSONObject()), (String) "access_token");
                    if (H5Utils.isDebug()) {
                        H5Log.d("TinyAppMTopPlugin", "access token is " + accessToken);
                    }
                    if (callback != null) {
                        callback.callback(accessToken);
                    }
                }
                return false;
            }
        });
    }

    private static String d(String userId, String appId) {
        return H5SharedPreferenceStorage.getInstance().getString(e(userId, appId));
    }

    /* access modifiers changed from: private */
    public static void d(String userId, String appId, String accessToken) {
        H5SharedPreferenceStorage.getInstance().putString(appId, e(userId, appId), accessToken);
    }

    private static String e(String userId, String appId) {
        return userId + "_" + appId + "_ta_mtop_token";
    }

    /* access modifiers changed from: private */
    public void d(H5Event event, final H5BridgeContext context) {
        JSONObject eventParams = event.getParam();
        H5Page h5Page = event.getH5page();
        if (eventParams == null || h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = TinyAppParamUtils.getAppId(h5Page.getParams());
        if (TextUtils.isEmpty(appId)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        JSONObject data = new JSONObject();
        data.put((String) "appId", (Object) a(appId));
        data.put((String) "appKey", (Object) b(appId));
        JSONObject getTBCodeParams = new JSONObject();
        getTBCodeParams.put((String) c.n, (Object) "mtop.taobao.openlink.basic.login.auth.code");
        getTBCodeParams.put((String) c.m, (Object) "1.0");
        getTBCodeParams.put((String) "needEncodeSign", (Object) Boolean.valueOf(true));
        getTBCodeParams.put((String) "data", (Object) data);
        h5Service.sendEvent(new Builder(event).action(ACTION_SEND_MTOP).param(getTBCodeParams).build(), new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject param, boolean keep) {
                if (H5Utils.contains(param, (String) "error")) {
                    context.sendBridgeResult(param);
                } else {
                    String code = H5Utils.getString(H5Utils.getJSONObject(param, "data", new JSONObject()), (String) "result");
                    if (TextUtils.isEmpty(code)) {
                        context.sendError(3, (String) "unknown error");
                    } else {
                        if (H5Utils.isDebug()) {
                            H5Log.d("TinyAppMTopPlugin", "taobao code is " + code);
                        }
                        JSONObject result = new JSONObject();
                        result.put((String) "code", (Object) code);
                        context.sendBridgeResult(result);
                    }
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public static void e(H5Event event, H5BridgeContext context) {
        JSONObject eventParams = event.getParam();
        H5Page h5Page = event.getH5page();
        if (eventParams == null || h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = TinyAppParamUtils.getAppId(h5Page.getParams());
        if (TextUtils.isEmpty(appId)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String tbSInfo = f(WalletTinyappUtils.getUserId(), appId);
        if (TextUtils.isEmpty(tbSInfo)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        JSONObject result = new JSONObject();
        try {
            result.put((String) "data", JSON.parse(tbSInfo));
        } catch (Exception e) {
            H5Log.e((String) "TinyAppMTopPlugin", (Throwable) e);
        }
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void f(H5Event event, H5BridgeContext context) {
        JSONObject eventParams = event.getParam();
        H5Page h5Page = event.getH5page();
        if (eventParams == null || h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = TinyAppParamUtils.getAppId(h5Page.getParams());
        if (TextUtils.isEmpty(appId)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String userId = WalletTinyappUtils.getUserId();
        Object session = eventParams.get("sessionInfo");
        if (session == null) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String tbSInfo = session.toString();
        if (TextUtils.isEmpty(tbSInfo)) {
            context.sendError(event, Error.INVALID_PARAM);
        }
        e(userId, appId, tbSInfo);
        context.sendSuccess();
    }

    private static String f(String userId, String appId) {
        return H5SharedPreferenceStorage.getInstance().getString(g(userId, appId));
    }

    private static void e(String userId, String appId, String tbSInfo) {
        H5SharedPreferenceStorage.getInstance().putString(appId, g(userId, appId), tbSInfo);
    }

    private static String g(String userId, String appId) {
        return userId + "_" + appId + "_ta_tb_sinfo";
    }

    /* access modifiers changed from: private */
    public static boolean h(String userId, String appId) {
        return "1".equals(H5SharedPreferenceStorage.getInstance().getString(i(userId, appId)));
    }

    /* access modifiers changed from: private */
    public static void b(String userId, String appId, boolean auth) {
        H5SharedPreferenceStorage.getInstance().putString(appId, i(userId, appId), auth ? "1" : "0");
    }

    private static String i(String userId, String appId) {
        return userId + "_" + appId + "_ta_tb_auth";
    }
}
