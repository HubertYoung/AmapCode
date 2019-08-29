package com.alipay.mobile.worker;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventDispatchHandler;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5BridgeContextImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SessionPlugin;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerBridge implements H5Bridge {
    public static final String TAG = "WorkerBridge";
    private static List<String> d = new ArrayList();
    private static List<String> e = new ArrayList();
    private Map<String, H5CallBack> a = new ConcurrentHashMap();
    private Map<String, Long> b = new ConcurrentHashMap();
    private boolean c = false;
    private int f = 10000;

    static {
        d.add("JSPlugin_AlipayH5Share");
        d.add("beforeunload");
        d.add("message");
        d.add("nbcomponent.canrender");
        e.add(H5SessionPlugin.SHOW_NETWORK_CHECK_ACTIVITY);
        e.add(H5AlertPlugin.showUCFailDialog);
        e.add("setKeyboardType");
        e.add(H5Param.MONITOR_PERFORMANCE);
        e.add("getStartupParams");
    }

    public void onRelease() {
        this.c = true;
        this.a.clear();
        this.b.clear();
        this.b = null;
        this.a = null;
    }

    public void sendToNative(H5Event event) {
        if (event != null && !this.c) {
            a(event, null);
        }
    }

    public void sendToNative(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event != null && !this.c) {
            a(h5Event, h5BridgeContext);
        }
    }

    private void a(final H5Event event, final H5BridgeContext h5BridgeContext) {
        if (!event.isDispatcherOnWorkerThread()) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    WorkerBridge.this.b(event, h5BridgeContext);
                }
            });
        } else if (H5Utils.isMain()) {
            H5EventDispatchHandler.getAsyncHandler().post(new Runnable() {
                public void run() {
                    WorkerBridge.this.b(event, h5BridgeContext);
                }
            });
        } else {
            b(event, h5BridgeContext);
        }
    }

    /* access modifiers changed from: private */
    public void b(H5Event event, H5BridgeContext h5BridgeContext) {
        H5BridgeContext context;
        if (event != null && !this.c) {
            String eventId = event.getId();
            boolean poolEvent = this.a.containsKey(eventId);
            JSONObject callParam = event.getParam();
            if (poolEvent) {
                H5CallBack callback = this.a.remove(eventId);
                if (callback != null) {
                    callback.onCallBack(callParam);
                }
                H5Log.d(TAG, "H5 callback for " + eventId);
                return;
            }
            String action = event.getAction();
            this.b.put(event.getId(), Long.valueOf(System.currentTimeMillis()));
            String paramStr = null;
            if (callParam != null) {
                paramStr = callParam.toJSONString();
                callParam.put((String) "funcName", (Object) action);
            }
            H5Log.d(TAG, "jsapi req name={" + action + "} eventId " + eventId + Token.SEPARATOR + paramStr);
            if (paramStr != null && paramStr.length() > this.f && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_notLogForMaxReq"))) {
                H5Log.d(TAG, "match size substring " + paramStr.length());
                paramStr.substring(0, this.f);
            }
            if (h5BridgeContext != null) {
                context = h5BridgeContext;
            } else {
                context = new H5BridgeContextImpl(this, eventId, action);
            }
            Nebula.getDispatcher().dispatch(event, context);
        }
    }

    public void sendToWeb(H5Event event) {
    }

    public void sendToWeb(String action, JSONObject param, H5CallBack callback) {
    }

    public void sendDataWarpToWeb(String action, JSONObject jsonObject, H5CallBack h5CallBack) {
    }

    public void monitorBridgeLog(String action, JSONObject param, String eventId) {
    }
}
