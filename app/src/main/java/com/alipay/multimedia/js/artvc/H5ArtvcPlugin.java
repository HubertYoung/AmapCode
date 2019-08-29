package com.alipay.multimedia.js.artvc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.mrtc.api.service.APMultimediaToyMRTVCService;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;
import java.util.Map;

public class H5ArtvcPlugin extends MMH5SimplePlugin {
    public static final String ACTIONS_CONN_TOYM = "connectToToyMachine";
    public static final String ACTIONS_CREATE_VC = "createToyMachineVC";
    public static final String ACTIONS_FETCH_LIVE_URL = "fetchLiveUrl";
    public static final String ACTIONS_LEAVE_TOYM = "leaveToyMachine";
    public static final String ACTIONS_SWITCH_TOYM_CAMERA = "switchToyMachineCamera";
    public static final String RESULT_CODE = "error";
    public static final String RESULT_MSG = "errorMessage";
    public static final String RESULT_SUCCESS = "success";
    private H5Page a;
    private APMultimediaToyMRTVCService b;

    public class ConnParams {
        @JSONField(name = "bizName")
        public String bizName = "";
        @JSONField(name = "machineId")
        public String machineId;
        @JSONField(name = "sign")
        public String sign = "";
        @JSONField(name = "subBiz")
        public String subBiz = "";
        @JSONField(name = "timeout")
        public int timeout = 60;

        public ConnParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class CreateParams {
        @JSONField(name = "url4H5Control")
        public String ctlrUrl = "";
        @JSONField(name = "extendInfo")
        public Map extras = null;
        @JSONField(name = "fullScreen")
        public int fullScreen = 0;
        @JSONField(name = "timeout")
        public int timeout = 60;
        @JSONField(name = "title")
        public String title;
        @JSONField(name = "viewPositionHeight")
        public float viewPositionHeight = 0.0f;
        @JSONField(name = "viewPositionLeft")
        public float viewPositionLeft = 0.0f;
        @JSONField(name = "viewPositionTop")
        public float viewPositionTop = 0.0f;
        @JSONField(name = "viewPositionWidth")
        public float viewPositionWidth = 0.0f;

        public CreateParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class FetchParams {
        @JSONField(name = "bizName")
        public String bizName;
        @JSONField(name = "machineId")
        public String machineId;
        @JSONField(name = "sign")
        public String sign;
        @JSONField(name = "subBiz")
        public String subBiz;

        public FetchParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5ArtvcPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("createToyMachineVC");
        filter.addAction(ACTIONS_CONN_TOYM);
        filter.addAction(ACTIONS_LEAVE_TOYM);
        filter.addAction(ACTIONS_SWITCH_TOYM_CAMERA);
        filter.addAction(ACTIONS_FETCH_LIVE_URL);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        Logger.debug("H5ArtvcPlugin", "handleEvent params: " + event.getParam() + ", action: " + action);
        if (TextUtils.isEmpty(action)) {
            return context.sendError(event, Error.INVALID_PARAM);
        }
        this.a = (H5Page) event.getTarget();
        char c = 65535;
        switch (action.hashCode()) {
            case -831461600:
                if (action.equals(ACTIONS_LEAVE_TOYM)) {
                    c = 2;
                    break;
                }
                break;
            case 444447890:
                if (action.equals("createToyMachineVC")) {
                    c = 0;
                    break;
                }
                break;
            case 1149865186:
                if (action.equals(ACTIONS_SWITCH_TOYM_CAMERA)) {
                    c = 3;
                    break;
                }
                break;
            case 1855291374:
                if (action.equals(ACTIONS_CONN_TOYM)) {
                    c = 1;
                    break;
                }
                break;
            case 2020561097:
                if (action.equals(ACTIONS_FETCH_LIVE_URL)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                a(event, context);
                break;
            case 1:
                b(event, context);
                break;
            case 2:
                a();
                break;
            case 3:
                b();
                break;
            case 4:
                c(event, context);
                break;
            default:
                return context.sendError(event, Error.INVALID_PARAM);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext context) {
        CreateParams params = (CreateParams) parseParams(event, CreateParams.class);
        if (params == null || TextUtils.isEmpty(params.ctlrUrl)) {
            a(context, (String) "input params invalid");
        } else {
            startArtvcApp("20001072", "H5ArtvcPlugin", params);
        }
    }

    private void b(H5Event event, H5BridgeContext context) {
        ConnParams params = (ConnParams) parseParams(event, ConnParams.class);
        if (params != null) {
            Intent intent = new Intent("com.alipay.artvc.actions.startstream");
            intent.putExtra("machineId", params.machineId);
            intent.putExtra("timeout", params.timeout);
            intent.putExtra("bizName", params.bizName);
            intent.putExtra("subBiz", params.subBiz);
            intent.putExtra("sign", params.sign);
            LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext()).sendBroadcast(intent);
            Logger.debug("H5ArtvcPlugin", "handleConnectToToyMachine sendBroadcast");
            return;
        }
        a(context, (String) "input params invalid");
    }

    private static void a() {
        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext()).sendBroadcast(new Intent("com.alipay.artvc.actions.stopstream"));
    }

    private static void b() {
        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext()).sendBroadcast(new Intent("com.alipay.artvc.actions.switchcamera"));
    }

    private void c(H5Event event, final H5BridgeContext context) {
        final FetchParams params = (FetchParams) parseParams(event, FetchParams.class);
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                Map urls = H5ArtvcPlugin.this.c().getLiveUrls(params.machineId, params.bizName, params.subBiz, params.sign);
                JSONObject result = new JSONObject();
                if (urls == null || urls.isEmpty()) {
                    result.put((String) "code", (Object) Integer.valueOf(-1));
                    result.put((String) "errMsg", (Object) "rpc fail");
                } else {
                    result.put((String) "code", (Object) Integer.valueOf(0));
                    result.put((String) "errMsg", (Object) "success");
                    result.put((String) "liveUrls", (Object) urls);
                }
                context.sendBridgeResult(result);
            }
        });
    }

    public void startArtvcApp(String appId, String business, CreateParams params) {
        Bundle bundle = new Bundle();
        bundle.putString("actionType", "createToyMachineVC");
        bundle.putString("title", params.title);
        bundle.putString("url4H5Control", params.ctlrUrl);
        bundle.putInt("fullScreen", params.fullScreen);
        bundle.putFloat("viewPositionTop", params.viewPositionTop);
        bundle.putFloat("viewPositionLeft", params.viewPositionLeft);
        bundle.putFloat("viewPositionWidth", params.viewPositionWidth);
        bundle.putFloat("viewPositionHeight", params.viewPositionHeight);
        bundle.putInt("timeout", params.timeout);
        if (params.extras != null) {
            Integer enableStats = (Integer) params.extras.get("enableStats");
            if (enableStats != null) {
                bundle.putInt("enableStats", enableStats.intValue());
            }
        }
        a(null, appId, bundle, business);
    }

    private static void a(H5BridgeContext context, String msg) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(2));
        result.put((String) "errorMessage", (Object) msg);
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public APMultimediaToyMRTVCService c() {
        if (this.b == null) {
            this.b = (APMultimediaToyMRTVCService) Utils.getService(APMultimediaToyMRTVCService.class);
        }
        return this.b;
    }

    private static boolean a(String sourceApp, String targetApp, Bundle bundle, String businessId) {
        Logger.debug("H5ArtvcPlugin", "startApp businessId=" + businessId);
        try {
            if (TextUtils.isEmpty(null)) {
                MicroApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp();
                if (app != null) {
                    sourceApp = app.getAppId();
                }
            }
            Logger.debug("H5ArtvcPlugin", "startApp sourceApp=" + sourceApp + ";targetApp=" + targetApp);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(sourceApp, targetApp, bundle);
            return true;
        } catch (Exception e) {
            Logger.error("H5ArtvcPlugin", "startApp exp", e);
            return false;
        }
    }
}
