package com.alipay.mobile.nebulaappproxy.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.activity.H5BaseActivity;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5TransActivity;

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
            a(event, bridgeContext, 0);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject callParam = event.getParam();
        String targetAppId = H5Utils.getString(callParam, (String) "appId", (String) null);
        boolean closeSelfWindow = H5Utils.getBoolean(callParam, (String) "closeSelfWindow", false);
        String scheme = H5Utils.getString(callParam, (String) "scheme");
        String startAnimation = H5Utils.getString(callParam, (String) "startAnimation");
        JSONObject error = new JSONObject();
        error.put((String) "error", (Object) "2");
        if (!TextUtils.isEmpty(targetAppId) || !TextUtils.isEmpty(scheme)) {
            Bundle startParams = H5ParamParser.parse(H5Utils.toBundle(null, H5Utils.getJSONObject(callParam, "param", null)), false);
            boolean closeCurrentApp = ((Boolean) H5Utils.getValue(callParam, (String) "closeCurrentApp", Boolean.valueOf(false))).booleanValue();
            if (!TextUtils.isEmpty(startAnimation)) {
                if (closeCurrentApp) {
                    H5Page h5Page = event.getH5page();
                    if (h5Page != null) {
                        h5Page.getParams().putString(H5Param.NEBULA_START_ANIMATION, startAnimation);
                    }
                }
                startParams.putString(H5Param.NEBULA_START_ANIMATION, startAnimation);
            }
            String runningAppId = null;
            try {
                if (LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp() != null) {
                    runningAppId = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp().getAppId();
                }
                if (closeCurrentApp) {
                    if (TextUtils.equals(targetAppId, runningAppId)) {
                        a(event, bridgeContext, 0);
                    } else if (!TextUtils.isEmpty(startAnimation)) {
                        H5Log.d("H5AppPlugin", "setActivityPresentFinish delay 0");
                        a(event, bridgeContext, 0);
                    } else {
                        a(event, bridgeContext, 200);
                    }
                }
                if (!TextUtils.isEmpty(scheme)) {
                    H5EnvProvider h5EnvProvider = (H5EnvProvider) H5Utils.getProvider(H5EnvProvider.class.getName());
                    if (h5EnvProvider != null) {
                        Bundle extParam = null;
                        H5Page h5Page2 = event.getH5page();
                        if (!(h5Page2 == null || h5Page2.getParams() == null)) {
                            extParam = (Bundle) h5Page2.getParams().clone();
                        }
                        h5EnvProvider.goToSchemeService(scheme, extParam);
                    }
                } else {
                    Bundle sceneParam = new Bundle();
                    H5Page h5Page3 = event.getH5page();
                    startParams.remove(H5Param.ORIGIN_FROM_EXTERNAL);
                    if (!(h5Page3 == null || h5Page3.getParams() == null)) {
                        sceneParam.putBoolean(H5Param.ORIGIN_FROM_EXTERNAL, H5Utils.getBoolean(h5Page3.getParams(), (String) H5Param.ORIGIN_FROM_EXTERNAL, false));
                    }
                    LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, targetAppId, startParams, sceneParam, null);
                }
                if (!closeCurrentApp && closeSelfWindow) {
                    if (event.getActivity() instanceof H5BaseActivity) {
                        H5Page h5Page4 = event.getH5page();
                        if (h5Page4 != null) {
                            h5Page4.exitPage();
                            return;
                        }
                        return;
                    }
                    H5Log.d("H5AppPlugin", "not in H5BaseActivity ");
                }
            } catch (AppLoadException e) {
                H5Log.e("H5AppPlugin", "startApp [targetAppId] " + targetAppId + " failed!", e);
            }
        } else {
            error.put((String) "message", (Object) "invalid app id " + targetAppId);
            bridgeContext.sendBridgeResult(error);
        }
    }

    private static String a() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String value = h5ConfigProvider.getConfig("h5_exitAppDefaultType");
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
        }
        return "exitTop";
    }

    private void a(H5Event event, H5BridgeContext bridgeContext, int delay) {
        if (!(event.getTarget() instanceof H5Page)) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String appId = null;
        final String closeActionType = H5Utils.getString(event.getParam(), (String) "closeActionType", a());
        final boolean animated = H5Utils.getBoolean(event.getParam(), (String) "animated", true);
        H5Log.d("H5AppPlugin", "closeActionType " + closeActionType);
        if (LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp() != null) {
            appId = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp().getAppId();
        }
        H5Log.d("H5AppPlugin", "findTopRunningApp appId:" + appId);
        if (!TextUtils.isEmpty(appId) && !TextUtils.equals(appId, "20000001")) {
            boolean closeCurrentApp = true;
            H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (provider != null) {
                String exitCurrentApp = provider.getConfig("h5_exitCurrentApp");
                if (exitCurrentApp != null && !exitCurrentApp.isEmpty()) {
                    closeCurrentApp = "YES".equalsIgnoreCase(exitCurrentApp);
                }
            }
            final boolean onlyCloseCurrentApp = closeCurrentApp;
            if (delay != 0) {
                final String finalAppId = appId;
                final H5Event h5Event = event;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        if ("exitSelf".equals(closeActionType)) {
                            H5AppPlugin.b(h5Event, animated, h5BridgeContext);
                        } else {
                            H5AppPlugin.b(finalAppId, onlyCloseCurrentApp);
                        }
                    }
                }, (long) delay);
            } else if ("exitSelf".equals(closeActionType)) {
                b(event, animated, bridgeContext);
            } else {
                b(appId, onlyCloseCurrentApp);
            }
        } else if (!(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity() == null || LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get() == null)) {
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
        bridgeContext.sendSuccess();
    }

    /* access modifiers changed from: private */
    public static void b(H5Event event, boolean animated, H5BridgeContext bridgeContext) {
        Activity activity = event.getActivity();
        H5Log.d("H5AppPlugin", "exitSelfApp " + activity);
        if (!animated) {
            H5Page h5Page = event.getH5page();
            if (!(h5Page == null || h5Page.getParams() == null)) {
                h5Page.getParams().putBoolean(H5Param.SHOW_ACTIVITY_FINISH_ANIMATION, false);
            }
        }
        if (activity != null && !activity.isFinishing()) {
            bridgeContext.sendSuccess();
            event.getActivity().finish();
        }
    }

    /* access modifiers changed from: private */
    public static void b(String appId, boolean closeCurrentApp) {
        if (closeCurrentApp) {
            MicroApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findAppById(appId);
            if (app != null) {
                app.destroy(null);
                H5Log.d("H5AppPlugin", "finishApp " + app.getAppId());
                return;
            }
            H5Log.d("H5AppPlugin", "finishApp app is null ");
            return;
        }
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().finishApp(appId, appId, null);
    }
}
