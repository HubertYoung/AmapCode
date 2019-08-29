package com.mpaas.nebula.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5TransActivity;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;

public class H5AppPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        filter.addAction(H5PageData.FROM_TYPE_START_APP);
        filter.addAction("exitApp");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (H5PageData.FROM_TYPE_START_APP.equals(action)) {
            a(event, bridgeContext);
        } else if ("exitApp".equals(action)) {
            a(event, bridgeContext, false);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject callParam = event.getParam();
        String targetAppId = H5Utils.getString(callParam, (String) "appId", (String) null);
        JSONObject error = new JSONObject();
        error.put((String) "error", (Object) "2");
        if (TextUtils.isEmpty(targetAppId)) {
            error.put((String) "message", (Object) "invalid app id " + targetAppId);
            bridgeContext.sendBridgeResult(error);
            return;
        }
        Bundle startParams = H5ParamParser.parse(H5Utils.toBundle(null, H5Utils.getJSONObject(callParam, "param", null)), false);
        String startUrl = H5Utils.getString(startParams, (String) "url");
        if (H5Utils.isStripLandingURLEnable(startUrl, "")) {
            String realUrl = H5Utils.getStripLandingURL(startUrl);
            if (TextUtils.equals(startUrl, realUrl)) {
                H5Log.d("H5AppPlugin", "getStripLandingURL equals");
            } else if (!TextUtils.isEmpty(realUrl) && realUrl.startsWith(BehavorReporter.PROVIDE_BY_ALIPAY)) {
                H5Log.d("H5AppPlugin", "getStripLandingURL bingo");
                startParams.putString("url", realUrl);
            }
        }
        boolean closeCurrentApp = ((Boolean) H5Utils.getValue(callParam, (String) "closeCurrentApp", Boolean.valueOf(false))).booleanValue();
        String runningAppId = null;
        try {
            if (LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp() != null) {
                runningAppId = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp().getAppId();
            }
            if (closeCurrentApp) {
                if (TextUtils.equals(targetAppId, runningAppId)) {
                    a(event, bridgeContext, false);
                } else {
                    a(event, bridgeContext, true);
                }
            }
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, targetAppId, startParams);
        } catch (AppLoadException e) {
            H5Log.e("H5AppPlugin", "startApp [targetAppId] " + targetAppId + " failed!", e);
        }
    }

    private void a(H5Event event, H5BridgeContext bridgeContext, boolean delay) {
        if (!(event.getTarget() instanceof H5Page)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String appId = null;
        if (LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp() != null) {
            appId = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp().getAppId();
        }
        H5Log.d("H5AppPlugin", "findTopRunningApp appId:" + appId);
        if (TextUtils.isEmpty(appId) || TextUtils.equals(appId, "20000001")) {
            if (LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity() != null && LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get() != null) {
                Activity topActivity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
                try {
                    String className = H5Utils.getClassName(topActivity);
                    H5Log.d("H5AppPlugin", "className:" + className);
                    if ((className.contains("H5Activity") || className.contains(H5TransActivity.TAG)) && !topActivity.isFinishing()) {
                        topActivity.finish();
                    }
                } catch (Exception e) {
                    H5Log.e((String) "H5AppPlugin", (Throwable) e);
                }
            }
        } else if (delay) {
            final String finalAppId = appId;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    LauncherApplicationAgent.getInstance().getMicroApplicationContext().finishApp(finalAppId, finalAppId, null);
                }
            }, 200);
        } else {
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().finishApp(appId, appId, null);
        }
    }
}
