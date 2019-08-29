package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.appmanager.TinyAppUpdateCallBackManager;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TinyAppUpdateManagerPlugin extends H5SimplePlugin {
    private static final String APPLY_UPDATE = "applyUpdate";
    private static final String REGISTER_UPDATE_MANAGER = "registerUpdateManager";
    private static final String TAG = "TinyAppUpdateManagerPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(APPLY_UPDATE);
        filter.addAction(REGISTER_UPDATE_MANAGER);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (APPLY_UPDATE.equals(event.getAction())) {
            applyUpdate(event, context);
            return true;
        } else if (!REGISTER_UPDATE_MANAGER.equals(event.getAction())) {
            return super.handleEvent(event, context);
        } else {
            registerUpdateManager(event, context);
            return true;
        }
    }

    private void registerUpdateManager(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            H5Log.d(TAG, "registerUpdateManager...h5Page is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            H5Log.d(TAG, "registerUpdateManager...appId is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        TinyAppUpdateCallBackManager.sRegisteredUpdateManager.put(appId, Boolean.valueOf(true));
        H5Log.d(TAG, "registerUpdateManager...appId:" + appId);
        context.sendSuccess();
    }

    private void applyUpdate(H5Event event, H5BridgeContext context) {
        H5Log.d(TAG, APPLY_UPDATE);
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            H5Log.d(TAG, "applyUpdate...h5Page is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            H5Log.d(TAG, "applyUpdate...appId is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        exitAndRestartApp(event, appId);
        context.sendSuccess();
    }

    private void exitAndRestartApp(H5Event event, final String appId) {
        if (event != null) {
            if (H5Utils.isInTinyProcess()) {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    try {
                        H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                        if (h5IpcServer != null) {
                            h5IpcServer.process(H5UrlHelper.parseUrl("alipays://platformapi/startapp?appId=" + appId));
                            Activity activity = event.getActivity();
                            if (activity != null) {
                                activity.finish();
                            }
                            h5EventHandlerService.stopSelfProcess();
                        }
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, "exitAndRestartApp...e=" + throwable);
                    }
                }
            } else {
                try {
                    Activity activity2 = event.getActivity();
                    if (activity2 != null) {
                        activity2.finish();
                    }
                    MicroApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findAppById(appId);
                    if (app != null) {
                        app.destroy(null);
                    }
                    ScheduledThreadPoolExecutor executor = H5Utils.getScheduledExecutor();
                    if (executor != null) {
                        executor.schedule(new Runnable() {
                            public void run() {
                                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, appId, null);
                            }
                        }, 1, TimeUnit.SECONDS);
                    }
                } catch (Throwable e) {
                    H5Log.e((String) TAG, "exitAndRestartApp......e=" + e);
                }
            }
        }
    }
}
