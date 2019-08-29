package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;

public class TinyAppUCFailPlugin extends H5SimplePlugin {
    private static final String TAG = "TinyAppUCFailPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(H5AlertPlugin.showUCFailDialog);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (H5AlertPlugin.showUCFailDialog.equals(event.getAction())) {
            tackleUcFailEvent(event);
        }
        return super.interceptEvent(event, context);
    }

    private void tackleUcFailEvent(H5Event event) {
        H5Page h5Page = event.getH5page();
        if (h5Page != null && TextUtils.isEmpty(H5Utils.getString(h5Page.getParams(), (String) H5Param.LONG_NB_URL))) {
            String fallbackAppId = H5Utils.getString(h5Page.getParams(), (String) H5Param.LONG_FALLBACK_APP_ID);
            if (openFallbackAppId(event, fallbackAppId)) {
                H5LogData logData = H5LogData.seedId("H5_UC_FAIL_FALLBACK_APP_ID");
                if (h5Page.getParams() != null) {
                    logData.param4().add(H5Utils.getString(h5Page.getParams(), (String) "appId"), null).add(H5Param.LONG_FALLBACK_APP_ID, fallbackAppId);
                }
                H5LogUtil.logNebulaTech(logData);
            } else if (degradeToSystemWebView(event)) {
                H5LogData logData2 = H5LogData.seedId("H5_UC_FAIL_USE_SYS_WEBVIEW");
                if (h5Page.getParams() != null) {
                    logData2.param4().add(H5Utils.getString(h5Page.getParams(), (String) "appId"), null).add(H5Param.USE_SYS_WEBVIEW, "1");
                }
                H5LogUtil.logNebulaTech(logData2);
            }
        }
    }

    private boolean openFallbackAppId(H5Event event, final String fallbackAppId) {
        if (TextUtils.isEmpty(fallbackAppId)) {
            return false;
        }
        if (event == null) {
            H5Log.d(TAG, "openFallbackAppId...event is null");
            return false;
        }
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            H5Log.d(TAG, "openFallbackAppId...page is null");
            return false;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            H5Log.d(TAG, "openFallbackAppId...appId is null");
            return false;
        }
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService != null && !h5TinyAppService.isUCFailFallbackAppSupported(appId)) {
            H5Log.d(TAG, "openFallbackAppId...switch is not supported");
            return false;
        } else if (H5Utils.isInTinyProcess()) {
            Nebula.moveTaskToBack(event.getActivity());
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                    if (h5EventHandlerService != null) {
                        try {
                            H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                            if (h5IpcServer != null) {
                                h5IpcServer.process(H5UrlHelper.parseUrl("alipays://platformapi/startapp?appId=" + fallbackAppId));
                                h5EventHandlerService.stopSelfProcess();
                            }
                        } catch (Throwable throwable) {
                            H5Log.e((String) TinyAppUCFailPlugin.TAG, "openFallbackAppId...e=" + throwable);
                        }
                    }
                }
            }, 500);
            return true;
        } else {
            Activity activity = event.getActivity();
            if (activity != null) {
                activity.finish();
            }
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, fallbackAppId, null);
            return true;
        }
    }

    private boolean degradeToSystemWebView(H5Event event) {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService != null && !h5TinyAppService.isWebWorkerSupported()) {
            H5Log.d(TAG, "degradeToSystemWebView...switch is not supported");
            return false;
        } else if (event == null) {
            H5Log.d(TAG, "degradeToSystemWebView...event is null");
            return false;
        } else {
            H5Page h5Page = event.getH5page();
            if (h5Page == null) {
                H5Log.d(TAG, "degradeToSystemWebView...page is null");
                return false;
            }
            String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if (TextUtils.isEmpty(appId)) {
                H5Log.d(TAG, "degradeToSystemWebView...appId is null");
                return false;
            }
            H5Log.d(TAG, "degradeToSystemWebView...appId: " + appId);
            String chInfo = H5Utils.getString(h5Page.getParams(), (String) "chInfo");
            if (H5Utils.isInTinyProcess()) {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    try {
                        H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                        if (h5IpcServer != null) {
                            String scheme = "alipays://platformapi/startapp?appId=" + appId + "&enablePolyfillWorker=true";
                            if (!TextUtils.isEmpty(chInfo)) {
                                scheme = scheme + "&chInfo=" + chInfo;
                            }
                            h5IpcServer.process(H5UrlHelper.parseUrl(scheme));
                            Activity activity = event.getActivity();
                            if (activity != null) {
                                activity.finish();
                            }
                            h5EventHandlerService.stopSelfProcess();
                        }
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, "degradeToSystemWebView...e=" + throwable);
                        return false;
                    }
                }
                return true;
            }
            try {
                Activity activity2 = event.getActivity();
                if (activity2 != null) {
                    activity2.finish();
                }
                MicroApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findAppById(appId);
                if (app != null) {
                    app.destroy(null);
                }
                Bundle params = new Bundle();
                params.putString(H5AlertPlugin.STARTUP_PARAM_USE_SYS_WEBVIEW, "true");
                if (!TextUtils.isEmpty(chInfo)) {
                    params.putString("chInfo", chInfo);
                }
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, appId, params);
                return true;
            } catch (Throwable e) {
                H5Log.e((String) TAG, "degradeToSystemWebView......e=" + e);
                return false;
            }
        }
    }
}
