package com.taobao.tao.remotebusiness.js;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import com.taobao.tao.remotebusiness.IRemoteCacheListener;
import com.taobao.tao.remotebusiness.IRemoteListener;
import com.taobao.tao.remotebusiness.MtopBusiness;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.JsonTypeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class MtopJSBridge {
    private static final String AUTO_LOGIN_ONLY = "AutoLoginOnly";
    private static final String AUTO_LOGIN_WITH_MANUAL = "AutoLoginAndManualLogin";
    private static final String DATA_TYPE_JSON = "json";
    private static final String DATA_TYPE_ORIGINAL_JSON = "originaljson";
    static final String TAG = "mtopsdk.MtopJSBridge";
    static volatile ScheduledExecutorService scheduledExecutorService;

    static class MtopJSListener implements IRemoteBaseListener, IRemoteCacheListener {
        private MtopResponse cachedResponse;
        private AtomicBoolean isFinish = new AtomicBoolean(false);
        final IRemoteBaseListener listener;
        private final MtopBusiness mtopBusiness;

        public MtopJSListener(MtopBusiness mtopBusiness2, IRemoteBaseListener iRemoteBaseListener) {
            this.mtopBusiness = mtopBusiness2;
            this.listener = iRemoteBaseListener;
        }

        public void onTimeOut() {
            if (this.isFinish.compareAndSet(false, true)) {
                if (TBSdkLog.a(LogEnable.DebugEnable)) {
                    TBSdkLog.a((String) MtopJSBridge.TAG, (String) "callback onTimeOut");
                }
                this.mtopBusiness.cancelRequest();
                try {
                    if (this.cachedResponse != null) {
                        this.listener.onSuccess(0, this.cachedResponse, null, null);
                    } else {
                        this.listener.onSystemError(0, null, null);
                    }
                } catch (Exception e) {
                    TBSdkLog.b((String) MtopJSBridge.TAG, (String) "do onTimeOut callback error.", (Throwable) e);
                }
            }
        }

        public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
            if (this.isFinish.compareAndSet(false, true)) {
                if (TBSdkLog.a(LogEnable.DebugEnable)) {
                    TBSdkLog.a((String) MtopJSBridge.TAG, (String) "callback onSuccess");
                }
                ScheduledExecutorService scheduledExecutorService = MtopJSBridge.getScheduledExecutorService();
                final int i2 = i;
                final MtopResponse mtopResponse2 = mtopResponse;
                final BaseOutDo baseOutDo2 = baseOutDo;
                final Object obj2 = obj;
                AnonymousClass1 r1 = new Runnable() {
                    public void run() {
                        try {
                            MtopJSListener.this.listener.onSuccess(i2, mtopResponse2, baseOutDo2, obj2);
                        } catch (Exception e) {
                            TBSdkLog.b((String) MtopJSBridge.TAG, (String) "do onSuccess callback error.", (Throwable) e);
                        }
                    }
                };
                scheduledExecutorService.submit(r1);
            }
        }

        public void onError(final int i, final MtopResponse mtopResponse, final Object obj) {
            if (this.isFinish.compareAndSet(false, true)) {
                if (TBSdkLog.a(LogEnable.DebugEnable)) {
                    TBSdkLog.a((String) MtopJSBridge.TAG, (String) "callback onError");
                }
                MtopJSBridge.getScheduledExecutorService().submit(new Runnable() {
                    public void run() {
                        try {
                            MtopJSListener.this.listener.onError(i, mtopResponse, obj);
                        } catch (Exception e) {
                            TBSdkLog.b((String) MtopJSBridge.TAG, (String) "do onError callback error.", (Throwable) e);
                        }
                    }
                });
            }
        }

        public void onSystemError(final int i, final MtopResponse mtopResponse, final Object obj) {
            if (this.isFinish.compareAndSet(false, true)) {
                if (TBSdkLog.a(LogEnable.DebugEnable)) {
                    TBSdkLog.a((String) MtopJSBridge.TAG, (String) "callback onSystemError");
                }
                MtopJSBridge.getScheduledExecutorService().submit(new Runnable() {
                    public void run() {
                        try {
                            MtopJSListener.this.listener.onSystemError(i, mtopResponse, obj);
                        } catch (Exception e) {
                            TBSdkLog.b((String) MtopJSBridge.TAG, (String) "do onSystemError callback error.", (Throwable) e);
                        }
                    }
                });
            }
        }

        public void onCached(fer fer, BaseOutDo baseOutDo, Object obj) {
            if (fer != null) {
                this.cachedResponse = fer.a;
            }
            if (TBSdkLog.a(LogEnable.DebugEnable)) {
                TBSdkLog.a((String) MtopJSBridge.TAG, (String) "callback onCached");
            }
        }
    }

    public interface MtopJSParam {
        public static final String API = "api";
        public static final String DATA = "data";
        public static final String DATA_TYPE = "dataType";
        public static final String EXT_HEADERS = "ext_headers";
        public static final String EXT_QUERYS = "ext_querys";
        public static final String METHOD = "method";
        public static final String MP_HOST = "mpHost";
        public static final String NEED_LOGIN = "needLogin";
        public static final String PAGE_URL = "pageUrl";
        public static final String SEC_TYPE = "secType";
        public static final String SESSION_OPTION = "sessionOption";
        public static final String TIMEOUT = "timeout";
        public static final String TTID = "ttid";
        @Deprecated
        public static final String USER_AGENT = "user-agent";
        public static final String V = "v";
        public static final String X_UA = "x-ua";

        @Retention(RetentionPolicy.SOURCE)
        public @interface Definition {
        }
    }

    public static void sendMtopRequest(Map<String, Object> map, @NonNull IRemoteBaseListener iRemoteBaseListener) {
        if (iRemoteBaseListener == null) {
            TBSdkLog.d(TAG, "illegal param listener.");
        } else if (map == null || map.isEmpty()) {
            TBSdkLog.d(TAG, "illegal param jsParamMap.");
            iRemoteBaseListener.onSystemError(0, new MtopResponse("ANDROID_SYS_ILLEGAL_JSPARAM_ERROR", "MTOP JSBridge 参数错误"), null);
        } else {
            MtopBusiness buildMtopBusiness = buildMtopBusiness(map);
            if (buildMtopBusiness == null) {
                iRemoteBaseListener.onSystemError(0, new MtopResponse("ANDROID_SYS_PARSE_JSPARAM_ERROR", "MTOP JSBridge 参数解析错误"), null);
                return;
            }
            int i = 20000;
            try {
                int intValue = ((Integer) map.get("timeout")).intValue();
                if (intValue >= 0) {
                    i = 60000;
                    if (intValue <= 60000) {
                        i = intValue;
                    }
                }
            } catch (Exception unused) {
                TBSdkLog.d(TAG, "parse timeout (jsParam field) error.");
            }
            final MtopJSListener mtopJSListener = new MtopJSListener(buildMtopBusiness, iRemoteBaseListener);
            buildMtopBusiness.registerListener((IRemoteListener) mtopJSListener);
            buildMtopBusiness.startRequest();
            getScheduledExecutorService().schedule(new Runnable() {
                public final void run() {
                    mtopJSListener.onTimeOut();
                }
            }, (long) i, TimeUnit.MILLISECONDS);
        }
    }

    private static MtopBusiness buildMtopBusiness(Map<String, Object> map) {
        MtopBusiness mtopBusiness;
        Map<String, String> map2;
        HashMap hashMap = null;
        try {
            JSONObject jSONObject = new JSONObject(map);
            String string = jSONObject.getString(MtopJSParam.API);
            String optString = jSONObject.optString("v", "*");
            String str = bny.c;
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                HashMap hashMap2 = new HashMap();
                Iterator<String> keys = optJSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    Object obj = optJSONObject.get(next);
                    hashMap2.put(next, obj.toString());
                    if (!(obj instanceof JSONArray) && !(obj instanceof JSONObject)) {
                        optJSONObject.put(next, obj.toString());
                    }
                }
                String jSONObject2 = optJSONObject.toString();
                map2 = hashMap2;
                str = jSONObject2;
            } else {
                map2 = null;
            }
            boolean optBoolean = jSONObject.optBoolean(MtopJSParam.NEED_LOGIN, false);
            String optString2 = jSONObject.optString(MtopJSParam.SESSION_OPTION, AUTO_LOGIN_WITH_MANUAL);
            MtopRequest mtopRequest = new MtopRequest();
            mtopRequest.setApiName(string);
            mtopRequest.setVersion(optString);
            mtopRequest.setNeedEcode(optBoolean);
            mtopRequest.setData(str);
            mtopRequest.dataParams = map2;
            mtopBusiness = MtopBusiness.build(mtopRequest);
            try {
                mtopBusiness.showLoginUI(!AUTO_LOGIN_ONLY.equalsIgnoreCase(optString2));
                if (MethodEnum.POST.getMethod().equalsIgnoreCase(jSONObject.optString("method", "GET"))) {
                    mtopBusiness.reqMethod(MethodEnum.POST);
                }
                String optString3 = jSONObject.optString(MtopJSParam.MP_HOST, "");
                if (fdd.a(optString3)) {
                    mtopBusiness.setCustomDomain(optString3);
                }
                if (jSONObject.optInt(MtopJSParam.SEC_TYPE, 0) > 0) {
                    mtopBusiness.useWua();
                }
                String optString4 = jSONObject.optString("dataType", "");
                if (!fdd.b(optString4) && (DATA_TYPE_JSON.equals(optString4) || DATA_TYPE_ORIGINAL_JSON.equals(optString4))) {
                    mtopBusiness.setJsonType(JsonTypeEnum.valueOf(optString4.toUpperCase(Locale.US)));
                }
                JSONObject optJSONObject2 = jSONObject.optJSONObject(MtopJSParam.EXT_HEADERS);
                if (optJSONObject2 != null) {
                    hashMap = new HashMap();
                    Iterator<String> keys2 = optJSONObject2.keys();
                    while (keys2.hasNext()) {
                        String next2 = keys2.next();
                        String string2 = optJSONObject2.getString(next2);
                        if (!TextUtils.isEmpty(next2) && !TextUtils.isEmpty(string2)) {
                            hashMap.put(next2, string2);
                        }
                    }
                }
                String optString5 = jSONObject.optString(MtopJSParam.X_UA);
                if (!fdd.b(optString5)) {
                    if (hashMap == null) {
                        hashMap = new HashMap();
                    }
                    hashMap.put(MtopJSParam.X_UA, URLEncoder.encode(optString5, "utf-8"));
                }
                mtopBusiness.headers((Map<String, String>) hashMap);
                JSONObject optJSONObject3 = jSONObject.optJSONObject(MtopJSParam.EXT_QUERYS);
                if (optJSONObject3 != null) {
                    Iterator<String> keys3 = optJSONObject3.keys();
                    while (keys3.hasNext()) {
                        String next3 = keys3.next();
                        String string3 = optJSONObject3.getString(next3);
                        if (!TextUtils.isEmpty(next3) && !TextUtils.isEmpty(string3)) {
                            mtopBusiness.addHttpQueryParameter(next3, string3);
                        }
                    }
                }
                String optString6 = jSONObject.optString("ttid");
                if (!fdd.b(optString6)) {
                    mtopBusiness.ttid(optString6);
                }
                String optString7 = jSONObject.optString(MtopJSParam.PAGE_URL);
                if (!fdd.b(optString7)) {
                    mtopBusiness.setPageUrl(optString7);
                }
                mtopBusiness.setReqSource(1);
            } catch (Exception e) {
                e = e;
                TBSdkLog.b((String) TAG, "parse mtop jsParamMap error, jsParamMap=".concat(String.valueOf(map)), (Throwable) e);
                return mtopBusiness;
            }
        } catch (Exception e2) {
            e = e2;
            mtopBusiness = null;
            TBSdkLog.b((String) TAG, "parse mtop jsParamMap error, jsParamMap=".concat(String.valueOf(map)), (Throwable) e);
            return mtopBusiness;
        }
        return mtopBusiness;
    }

    static ScheduledExecutorService getScheduledExecutorService() {
        if (scheduledExecutorService == null) {
            synchronized (MtopJSBridge.class) {
                if (scheduledExecutorService == null) {
                    scheduledExecutorService = Executors.newScheduledThreadPool(1);
                }
            }
        }
        return scheduledExecutorService;
    }
}
