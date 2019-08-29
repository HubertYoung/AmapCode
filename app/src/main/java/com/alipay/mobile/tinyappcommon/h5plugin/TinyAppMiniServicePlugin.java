package com.alipay.mobile.tinyappcommon.h5plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;

public class TinyAppMiniServicePlugin extends H5SimplePlugin {
    private static final String MINI_SERVICE = "miniservice";
    private static final String MINI_SERVICE_OFFLINE_PARAM = "miniPluginStartParam";
    public static final String NAVIGATE_BACK_FROM_MINI_SERVICE = "navigateBackFromMiniService";
    public static final String NAVIGATE_TO_MINI_SERVICE = "navigateToMiniService";
    private static final String ON_MINI_SERVICE_MESSAGE = "onMiniServiceMessage";
    private static final String START_MINI_SERVICE = "startMiniService";
    /* access modifiers changed from: private */
    public static final String TAG = TinyAppMiniServicePlugin.class.getSimpleName();
    private static final String TINY_SOURCE_TYPE_TAG = "tinySource";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(NAVIGATE_TO_MINI_SERVICE);
        filter.addAction(NAVIGATE_BACK_FROM_MINI_SERVICE);
        filter.addAction(START_MINI_SERVICE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (NAVIGATE_TO_MINI_SERVICE.equals(event.getAction())) {
            navigateToMiniService(false, event, context);
        } else if (START_MINI_SERVICE.equals(event.getAction())) {
            navigateToMiniService(true, event, context);
        } else if (NAVIGATE_BACK_FROM_MINI_SERVICE.equals(event.getAction())) {
            navigateBackFromMiniService(false, event, context);
        }
        return true;
    }

    private void navigateToMiniService(final boolean supportParentId, final H5Event event, final H5BridgeContext context) {
        H5Utils.getExecutor("RPC").execute(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0074, code lost:
                if (android.text.TextUtils.isEmpty(r18) != false) goto L_0x0076;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r22 = this;
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5Event r0 = r4
                    r19 = r0
                    com.alipay.mobile.h5container.api.H5Page r5 = r19.getH5page()
                    if (r5 != 0) goto L_0x0027
                    java.lang.String r19 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.TAG
                    java.lang.String r20 = "navigateToMiniService...h5Page is null"
                    com.alipay.mobile.nebula.util.H5Log.d(r19, r20)
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5BridgeContext r0 = r5
                    r19 = r0
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5Event r0 = r4
                    r20 = r0
                    com.alipay.mobile.h5container.api.H5Event$Error r21 = com.alipay.mobile.h5container.api.H5Event.Error.INVALID_PARAM
                    r19.sendError(r20, r21)
                L_0x0026:
                    return
                L_0x0027:
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5Event r0 = r4
                    r19 = r0
                    com.alibaba.fastjson.JSONObject r19 = r19.getParam()
                    java.lang.String r20 = "serviceId"
                    java.lang.String r14 = com.alipay.mobile.nebula.util.H5Utils.getString(r19, r20)
                    boolean r19 = android.text.TextUtils.isEmpty(r14)
                    if (r19 == 0) goto L_0x0058
                    java.lang.String r19 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.TAG
                    java.lang.String r20 = "navigateToMiniService...serviceId is null"
                    com.alipay.mobile.nebula.util.H5Log.d(r19, r20)
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5BridgeContext r0 = r5
                    r19 = r0
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5Event r0 = r4
                    r20 = r0
                    com.alipay.mobile.h5container.api.H5Event$Error r21 = com.alipay.mobile.h5container.api.H5Event.Error.INVALID_PARAM
                    r19.sendError(r20, r21)
                    goto L_0x0026
                L_0x0058:
                    r0 = r22
                    boolean r0 = r3
                    r19 = r0
                    if (r19 == 0) goto L_0x0076
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5Event r0 = r4
                    r19 = r0
                    com.alibaba.fastjson.JSONObject r19 = r19.getParam()
                    java.lang.String r20 = "parentAppId"
                    java.lang.String r18 = com.alipay.mobile.nebula.util.H5Utils.getString(r19, r20)
                    boolean r19 = android.text.TextUtils.isEmpty(r18)
                    if (r19 == 0) goto L_0x0082
                L_0x0076:
                    r0 = r22
                    com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.this
                    r19 = r0
                    r0 = r19
                    java.lang.String r18 = r0.getTinyAppId(r5)
                L_0x0082:
                    boolean r6 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.appIsMiniService(r5)
                    boolean r19 = android.text.TextUtils.isEmpty(r18)
                    if (r19 == 0) goto L_0x00aa
                    java.lang.String r19 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.TAG
                    java.lang.String r20 = "navigateToMiniService...appId is null"
                    com.alipay.mobile.nebula.util.H5Log.d(r19, r20)
                    r0 = r22
                    com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.this
                    r19 = r0
                    r20 = 1005(0x3ed, float:1.408E-42)
                    java.lang.String r21 = "打开失败"
                    r0 = r19
                    r1 = r20
                    r2 = r21
                    r0.onMiniServiceMessage(r14, r5, r1, r2)
                    goto L_0x0026
                L_0x00aa:
                    r0 = r22
                    com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.this
                    r19 = r0
                    android.os.Bundle r20 = r5.getParams()
                    android.os.Bundle r13 = r19.getOfflineModeParams(r20)
                    r12 = 0
                    r11 = 0
                    boolean r19 = r13.isEmpty()
                    if (r19 != 0) goto L_0x00f2
                    java.util.Set r19 = r13.keySet()
                    java.util.Iterator r19 = r19.iterator()
                L_0x00c8:
                    boolean r20 = r19.hasNext()
                    if (r20 == 0) goto L_0x00f2
                    java.lang.Object r7 = r19.next()
                    java.lang.String r7 = (java.lang.String) r7
                    java.lang.String r20 = "nbsv"
                    r0 = r20
                    boolean r20 = r0.equals(r7)
                    if (r20 == 0) goto L_0x00e3
                    java.lang.String r12 = r13.getString(r7)
                    goto L_0x00c8
                L_0x00e3:
                    java.lang.String r20 = "nbsn"
                    r0 = r20
                    boolean r20 = r0.equals(r7)
                    if (r20 == 0) goto L_0x00c8
                    java.lang.String r11 = r13.getString(r7)
                    goto L_0x00c8
                L_0x00f2:
                    com.alipay.mobile.tinyappcommon.api.TinyAppService r19 = com.alipay.mobile.tinyappcommon.api.TinyAppService.get()
                    com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService r10 = r19.getMixActionService()
                    if (r10 == 0) goto L_0x0119
                    r0 = r18
                    boolean r19 = r10.canOpenMiniService(r0, r14, r12, r11)
                    if (r19 != 0) goto L_0x0119
                    r0 = r22
                    com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.this
                    r19 = r0
                    r20 = 1005(0x3ed, float:1.408E-42)
                    java.lang.String r21 = "打开失败"
                    r0 = r19
                    r1 = r20
                    r2 = r21
                    r0.onMiniServiceMessage(r14, r5, r1, r2)
                    goto L_0x0026
                L_0x0119:
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5Event r0 = r4
                    r19 = r0
                    com.alibaba.fastjson.JSONObject r19 = r19.getParam()
                    java.lang.String r20 = "params"
                    r21 = 0
                    com.alibaba.fastjson.JSONObject r9 = com.alipay.mobile.nebula.util.H5Utils.getJSONObject(r19, r20, r21)
                    android.os.Bundle r3 = new android.os.Bundle
                    android.os.Bundle r19 = com.alipay.mobile.nebula.util.H5Utils.toBundle(r9)
                    r0 = r19
                    r3.<init>(r0)
                    java.lang.String r19 = "START_APP_IN_CURRENT_PROCESS"
                    r20 = 1
                    r0 = r19
                    r1 = r20
                    r3.putBoolean(r0, r1)
                    java.lang.String r19 = "tinySource"
                    java.lang.String r20 = "miniservice"
                    r0 = r19
                    r1 = r20
                    r3.putString(r0, r1)
                    java.lang.String r19 = "parentAppId"
                    r0 = r19
                    r1 = r18
                    r3.putString(r0, r1)
                    java.lang.String r19 = "miniServiceId"
                    r0 = r19
                    r3.putString(r0, r14)
                    java.lang.String r19 = "ap_framework_sceneId"
                    java.lang.String r20 = "miniService"
                    r0 = r19
                    r1 = r20
                    r3.putString(r0, r1)
                    java.lang.String r19 = "chInfo"
                    java.lang.String r20 = "ch_miniService"
                    r0 = r19
                    r1 = r20
                    r3.putString(r0, r1)
                    android.os.Bundle r19 = r5.getParams()
                    java.lang.String r20 = "sessionId"
                    java.lang.String r16 = com.alipay.mobile.nebula.util.H5Utils.getString(r19, r20)
                    java.lang.String r19 = "parentSessionId"
                    r0 = r19
                    r1 = r16
                    r3.putString(r0, r1)
                    boolean r19 = r13.isEmpty()
                    if (r19 != 0) goto L_0x018e
                    r3.putAll(r13)
                L_0x018e:
                    r0 = r22
                    com.alipay.mobile.h5container.api.H5Event r0 = r4
                    r19 = r0
                    com.alibaba.fastjson.JSONObject r19 = r19.getParam()
                    java.lang.String r20 = "servicePage"
                    java.lang.String r15 = com.alipay.mobile.nebula.util.H5Utils.getString(r19, r20)
                    boolean r19 = android.text.TextUtils.isEmpty(r15)
                    if (r19 != 0) goto L_0x01ab
                    java.lang.String r19 = "page"
                    r0 = r19
                    r3.putString(r0, r15)
                L_0x01ab:
                    android.os.Bundle r19 = r5.getParams()
                    java.lang.String r20 = "appId"
                    java.lang.String r4 = com.alipay.mobile.nebula.util.H5Utils.getString(r19, r20)
                    android.os.Bundle r19 = r5.getParams()
                    java.lang.String r20 = "miniServiceId"
                    java.lang.String r8 = com.alipay.mobile.nebula.util.H5Utils.getString(r19, r20)
                    if (r6 == 0) goto L_0x01dc
                    boolean r19 = android.text.TextUtils.equals(r4, r8)
                    if (r19 != 0) goto L_0x01dc
                    r0 = r22
                    com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.this
                    r19 = r0
                    r20 = 1005(0x3ed, float:1.408E-42)
                    java.lang.String r21 = "打开失败"
                    r0 = r19
                    r1 = r20
                    r2 = r21
                    r0.onMiniServiceMessage(r14, r5, r1, r2)
                    goto L_0x0026
                L_0x01dc:
                    boolean r19 = android.text.TextUtils.isEmpty(r4)
                    if (r19 == 0) goto L_0x01ec
                    android.os.Bundle r19 = r5.getParams()
                    java.lang.String r20 = "parentAppId"
                    java.lang.String r4 = com.alipay.mobile.nebula.util.H5Utils.getString(r19, r20)
                L_0x01ec:
                    if (r6 == 0) goto L_0x01f5
                    java.lang.String r19 = "sourceServiceId"
                    r0 = r19
                    r3.putString(r0, r4)
                L_0x01f5:
                    android.os.Bundle r19 = r5.getParams()
                    java.lang.StringBuilder r20 = new java.lang.StringBuilder
                    java.lang.String r21 = "mini_service_stack_size_"
                    r20.<init>(r21)
                    r0 = r20
                    r1 = r18
                    java.lang.StringBuilder r20 = r0.append(r1)
                    java.lang.String r20 = r20.toString()
                    r21 = 0
                    int r17 = com.alipay.mobile.nebula.util.H5Utils.getInt(r19, r20, r21)
                    r19 = 9
                    r0 = r17
                    r1 = r19
                    if (r0 < r1) goto L_0x022f
                    r0 = r22
                    com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.this
                    r19 = r0
                    r20 = 1005(0x3ed, float:1.408E-42)
                    java.lang.String r21 = "跳转小服务数超过最大限制"
                    r0 = r19
                    r1 = r20
                    r2 = r21
                    r0.onMiniServiceMessage(r14, r5, r1, r2)
                    goto L_0x0026
                L_0x022f:
                    java.lang.StringBuilder r19 = new java.lang.StringBuilder
                    java.lang.String r20 = "mini_service_stack_size_"
                    r19.<init>(r20)
                    r0 = r19
                    r1 = r18
                    java.lang.StringBuilder r19 = r0.append(r1)
                    java.lang.String r19 = r19.toString()
                    int r17 = r17 + 1
                    r0 = r19
                    r1 = r17
                    r3.putInt(r0, r1)
                    com.alipay.mobile.framework.MicroApplicationContext r19 = com.alipay.mobile.tinyappcommon.TinyappUtils.getMicroApplicationContext()
                    r0 = r19
                    r0.startApp(r4, r14, r3)
                    r0 = r22
                    com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin r0 = com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.this
                    r19 = r0
                    r19.registerBackEventInterceptor()
                    goto L_0x0026
                */
                throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin.AnonymousClass1.run():void");
            }
        });
    }

    /* access modifiers changed from: private */
    public void registerBackEventInterceptor() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            h5Service.getPluginManager().register((H5Plugin) new H5SimplePlugin() {
                public void onPrepare(H5EventFilter filter) {
                    super.onPrepare(filter);
                    filter.addAction(CommonEvents.H5_PAGE_BACK);
                }

                public boolean interceptEvent(H5Event event, H5BridgeContext context) {
                    if (CommonEvents.H5_PAGE_BACK.equals(event.getAction())) {
                        H5Page h5Page = event.getH5page();
                        if (!(h5Page == null || h5Page.getParams() == null)) {
                            String tinyAppId = TinyAppMiniServicePlugin.this.getTinyAppId(h5Page);
                            if (!TextUtils.isEmpty(tinyAppId)) {
                                int stackSize = H5Utils.getInt(h5Page.getParams(), "mini_service_stack_size_" + tinyAppId, 0);
                                if (stackSize >= 0) {
                                    h5Page.getParams().putInt("mini_service_stack_size_" + tinyAppId, stackSize - 1);
                                }
                            }
                        }
                        if (TinyAppMiniServicePlugin.this.shouldInterceptBackEvent(event)) {
                            H5Log.d(TinyAppMiniServicePlugin.TAG, "interceptEvent...");
                            TinyAppMiniServicePlugin.this.navigateBackFromMiniService(true, event, context);
                            return true;
                        }
                    }
                    return super.interceptEvent(event, context);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public boolean shouldInterceptBackEvent(H5Event event) {
        H5Page h5Page = event.getH5page();
        if (h5Page == null || !appIsMiniService(h5Page)) {
            return false;
        }
        String sessionId = H5Utils.getString(h5Page.getParams(), (String) "sessionId");
        if (TextUtils.isEmpty(sessionId)) {
            return false;
        }
        H5Session h5Session = Nebula.getService().getSession(sessionId);
        if (h5Session == null || h5Session.getPages() == null || Nebula.getSessionPagesWithOutPrerender(h5Session.getPages()).size() != 1) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void navigateBackFromMiniService(boolean backEvent, H5Event event, H5BridgeContext context) {
        try {
            H5Page h5Page = event.getH5page();
            if (h5Page == null) {
                H5Log.d(TAG, "navigateBackFromMiniService...h5Page is null");
                context.sendError(event, Error.UNKNOWN_ERROR);
                return;
            }
            String serviceId = H5Utils.getString(h5Page.getParams(), (String) "miniServiceId");
            if (TextUtils.isEmpty(serviceId)) {
                H5Log.d(TAG, "navigateBackFromMiniService...serviceId is null");
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
            JSONObject data = H5Utils.getJSONObject(event.getParam(), "data", null);
            String parentSessionId = H5Utils.getString(h5Page.getParams(), (String) "parentSessionId");
            if (TextUtils.isEmpty(parentSessionId)) {
                H5Log.d(TAG, "navigateBackFromMiniService...parent sessionId is null");
                context.sendError(event, Error.UNKNOWN_ERROR);
                return;
            }
            H5Session h5Session = Nebula.getService().getSession(parentSessionId);
            if (h5Session == null) {
                H5Log.d(TAG, "navigateBackFromMiniService...h5Session is null");
                context.sendError(event, Error.UNKNOWN_ERROR);
                return;
            }
            H5Page parentH5Page = h5Session.getTopPage();
            if (parentH5Page == null) {
                H5Log.d(TAG, "navigateBackFromMiniService...parentH5Page is null");
                context.sendError(event, Error.UNKNOWN_ERROR);
                return;
            }
            H5Log.d(TAG, "navigateBackFromMiniService...serviceId=" + serviceId);
            JSONObject object = new JSONObject();
            object.put((String) "serviceId", (Object) serviceId);
            object.put((String) "data", (Object) data);
            object.put((String) "action", (Object) backEvent ? "cancel" : "complete");
            onMiniServiceMessage(parentH5Page, object);
            JSONObject param = new JSONObject();
            param.put((String) "closeActionType", (Object) "exitSelf");
            h5Page.sendEvent("exitApp", param);
        } catch (Throwable e) {
            H5Log.e(TAG, "navigateBackFromMiniService...e=" + e);
            context.sendError(event, Error.UNKNOWN_ERROR);
        }
    }

    public static boolean appIsMiniService(H5Page h5Page) {
        if (h5Page == null) {
            return false;
        }
        return appIsMiniService(h5Page.getParams());
    }

    public static boolean appIsMiniService(Bundle startParams) {
        if (startParams == null) {
            return false;
        }
        return MINI_SERVICE.equals(H5Utils.getString(startParams, (String) TINY_SOURCE_TYPE_TAG));
    }

    private void onMiniServiceMessage(H5Page h5Page, JSONObject object) {
        if (h5Page != null && h5Page.getBridge() != null) {
            h5Page.getBridge().sendDataWarpToWeb(ON_MINI_SERVICE_MESSAGE, object, null);
        }
    }

    /* access modifiers changed from: private */
    public void onMiniServiceMessage(String serviceId, H5Page h5Page, int error, String errorMessage) {
        JSONObject object = new JSONObject();
        object.put((String) "serviceId", (Object) serviceId);
        object.put((String) "error", (Object) Integer.valueOf(error));
        object.put((String) "errorMessage", (Object) errorMessage);
        onMiniServiceMessage(h5Page, object);
    }

    /* access modifiers changed from: private */
    public Bundle getOfflineModeParams(Bundle startupParams) {
        Bundle bundle = new Bundle();
        try {
            String offlineParam = H5Utils.getString(startupParams, (String) MINI_SERVICE_OFFLINE_PARAM);
            if (!TextUtils.isEmpty(offlineParam)) {
                H5Log.d(TAG, "getOfflineModeParams...offline params: " + offlineParam);
                for (String split : offlineParam.split("&")) {
                    String[] keyValuePair = split.split("=");
                    bundle.putString(keyValuePair[0], keyValuePair[1]);
                }
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "getOfflineModeParams...e=" + e);
        }
        return bundle;
    }

    /* access modifiers changed from: private */
    public String getTinyAppId(H5Page h5Page) {
        if (!appIsMiniService(h5Page)) {
            String tinyAppId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if (!TextUtils.isEmpty(tinyAppId)) {
                return tinyAppId;
            }
        }
        return H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
    }
}
