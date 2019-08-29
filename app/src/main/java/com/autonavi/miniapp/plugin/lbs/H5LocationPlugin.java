package com.autonavi.miniapp.plugin.lbs;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.ali.auth.third.core.context.KernelContext;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.miniapp.plugin.BasePlugin;
import com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation;
import com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocation.ContinuousLocationCallBack;
import com.autonavi.miniapp.plugin.lbs.continuouslocation.TinyAppContinueLocationAction;
import java.util.Arrays;
import java.util.List;

public class H5LocationPlugin extends BasePlugin implements ContinuousLocationCallBack {
    private static final String CHOOSE_LOCATION = "chooseLocation";
    private static final String GET_CURRENT_LOCATION = "getCurrentLocation";
    static final String GET_KBLOCATION = "getKBLocation";
    private static final String GET_LOCATION = "getLocation";
    private static final String OPEN_LOCATION = "openLocation";
    private static final String OPEN_NAVI = "openAMapNavi";
    private static final String START_CONTINUOUS_LOCATION = "startContinuousLocation";
    private static final String STOP_CONTINUOUS_LOCATION = "stopContinuousLocation";
    public static final String TAG = "H5LocationPlugin";
    private BroadcastReceiver backgroundLocationReceiver;
    private Context context;
    private H5Location h5Location = new H5Location();
    private boolean mReceiverTag = false;
    private SimpleLocationCache mSimpleLocationCache = new SimpleLocationCache();
    private BroadcastReceiver screenOffBroadcastReceiver;
    private TinyAppContinueLocation tinyAppContinueLocation = new TinyAppContinueLocation();

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction("getLocation");
        h5EventFilter.addAction("getCurrentLocation");
        h5EventFilter.addAction(GET_KBLOCATION);
        h5EventFilter.addAction("openLocation");
        h5EventFilter.addAction("chooseLocation");
        h5EventFilter.addAction(OPEN_NAVI);
        h5EventFilter.addAction("startContinuousLocation");
        h5EventFilter.addAction("stopContinuousLocation");
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event == null) {
            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "event == null");
            return false;
        }
        this.context = h5Event.getActivity();
        String action = h5Event.getAction();
        long currentTimeMillis = System.currentTimeMillis();
        if ("getLocation".equals(action) || GET_KBLOCATION.equals(action)) {
            getLocation(h5Event, h5BridgeContext, currentTimeMillis);
        } else if ("getCurrentLocation".equals(action)) {
            getCurrentLocation(h5Event, h5BridgeContext, currentTimeMillis);
        } else if ("openLocation".equals(action)) {
            openLocation(h5Event, h5BridgeContext);
        } else if ("chooseLocation".equals(action)) {
            chooseLocation(h5Event, h5BridgeContext, currentTimeMillis);
        } else if (OPEN_NAVI.equals(action)) {
            openNavi(h5Event, h5BridgeContext);
        } else if ("startContinuousLocation".equals(action)) {
            startContinuousLocation(h5Event, h5BridgeContext);
        } else if ("stopContinuousLocation".equals(action)) {
            stopContinuousLocationAction(h5BridgeContext);
        }
        return true;
    }

    private void getLocation(H5Event h5Event, H5BridgeContext h5BridgeContext, long j) {
        LoggerFactory.getTraceLogger().info("H5LocationPlugin", "getLocation");
        if (judgeGrant(h5Event.getTarget() instanceof H5Page ? (H5Page) h5Event.getTarget() : null, h5BridgeContext)) {
            H5GetCurrentLocationAction h5GetCurrentLocationAction = new H5GetCurrentLocationAction(h5Event, h5BridgeContext, this.h5Location, j, this.mSimpleLocationCache);
            h5GetCurrentLocationAction.handleEvent();
            return;
        }
        LoggerFactory.getTraceLogger().info("H5LocationPlugin", "getLocation, no grant auth");
    }

    private void getCurrentLocation(H5Event h5Event, H5BridgeContext h5BridgeContext, long j) {
        H5GetCurrentLocationAction h5GetCurrentLocationAction = new H5GetCurrentLocationAction(h5Event, h5BridgeContext, this.h5Location, j, this.mSimpleLocationCache);
        h5GetCurrentLocationAction.handleEvent();
    }

    private void chooseLocation(H5Event h5Event, H5BridgeContext h5BridgeContext, long j) {
        H5ChooseLocationAction h5ChooseLocationAction = new H5ChooseLocationAction(h5Event, h5BridgeContext, this.h5Location, j, this.mSimpleLocationCache);
        h5ChooseLocationAction.handleEvent();
    }

    private void openLocation(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (this.h5Location != null) {
            this.h5Location.openLocation(h5Event, h5BridgeContext);
        }
    }

    private boolean judgeGrant(H5Page h5Page, H5BridgeContext h5BridgeContext) {
        if (h5Page == null) {
            return false;
        }
        boolean hasLocationPermission = LBSCommonUtil.hasLocationPermission();
        H5Log.d("H5LocationPlugin", "location grant:".concat(String.valueOf(hasLocationPermission)));
        if (!hasLocationPermission) {
            H5Log.d("H5LocationPlugin", "no grant location.");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "error", (Object) Integer.valueOf(11));
            jSONObject.put((String) "errorMessage", (Object) "请确认定位相关权限已开启");
            if (h5BridgeContext != null) {
                h5BridgeContext.sendBridgeResult(jSONObject);
            }
        }
        return hasLocationPermission;
    }

    private void openNavi(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (this.h5Location != null) {
            this.h5Location.openNavi(h5Event, h5BridgeContext);
        }
    }

    public void onRelease() {
        if (this.h5Location == null) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", (String) "onRelease,h5Location == null");
            return;
        }
        if (this.tinyAppContinueLocation != null) {
            this.tinyAppContinueLocation.stopContinuousLocation();
        }
        this.tinyAppContinueLocation = null;
        this.h5Location = null;
        try {
            unRegisterReceiver();
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", th);
        }
        this.context = null;
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(Arrays.asList(new String[]{"getLocation", "getCurrentLocation", "openLocation", "chooseLocation", OPEN_NAVI, "startContinuousLocation", "stopContinuousLocation"}));
    }

    private void startContinuousLocation(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        H5Log.d("H5LocationPlugin", "startContinuousLocation");
        h5Event.getParam().put((String) "bizType", (Object) TinyAppParamUtils.getAppId(h5Event));
        this.tinyAppContinueLocation.setCallBack(this);
        startTinyAppContinuousLocation(h5Event, h5BridgeContext);
    }

    /* access modifiers changed from: private */
    public void startTinyAppContinuousLocation(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        try {
            String string = H5Utils.getString(h5Event.getParam(), (String) "bizType", (String) null);
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(",registerReceiver,startTinyAppContinuousLocation");
            traceLogger.info("H5LocationPlugin", sb.toString());
            registerTinyAppFgBgReceiver(h5Event, h5BridgeContext);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", "startTinyAppContinuousLocation, th=".concat(String.valueOf(th)));
        }
        TinyAppContinueLocationAction tinyAppContinueLocationAction = new TinyAppContinueLocationAction(h5Event, h5BridgeContext, this.h5Location, System.currentTimeMillis());
        tinyAppContinueLocationAction.setTinyAppContinueLocation(this.tinyAppContinueLocation);
        tinyAppContinueLocationAction.handleEvent();
    }

    private void stopContinuousLocationAction(H5BridgeContext h5BridgeContext) {
        try {
            unRegisterReceiver();
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", th);
        }
        stopTinyAppContinuousLocation(h5BridgeContext);
    }

    /* access modifiers changed from: private */
    public void stopContinuousLocation(H5BridgeContext h5BridgeContext) {
        stopTinyAppContinuousLocation(h5BridgeContext);
    }

    private void stopTinyAppContinuousLocation(H5BridgeContext h5BridgeContext) {
        LoggerFactory.getTraceLogger().info("H5LocationPlugin", "stopTinyAppContinuousLocation, isTinyApp");
        if (this.tinyAppContinueLocation != null) {
            this.tinyAppContinueLocation.stopContinuousLocation();
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "success", (Object) Boolean.TRUE);
        if (h5BridgeContext != null) {
            h5BridgeContext.sendBridgeResult(jSONObject);
        }
    }

    private void registerReceiver(Context context2, final H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        if (this.backgroundLocationReceiver == null) {
            this.backgroundLocationReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (context != null && intent != null) {
                        String action = intent.getAction();
                        H5Log.d("H5LocationPlugin", "receive msg: ".concat(String.valueOf(action)));
                        if (MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME.equals(action)) {
                            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "backgroundLocationReceiver, turn to foreground");
                            H5LocationPlugin.this.startTinyAppContinuousLocation(h5Event, h5BridgeContext);
                            return;
                        }
                        if (MsgCodeConstants.FRAMEWORK_ACTIVITY_PAUSE.equals(action)) {
                            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "backgroundLocationReceiver, turn to background");
                            H5LocationPlugin.this.stopContinuousLocation(h5BridgeContext);
                        }
                    }
                }
            };
            if (!this.mReceiverTag) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME);
                intentFilter.addAction(MsgCodeConstants.FRAMEWORK_ACTIVITY_PAUSE);
                LocalBroadcastManager.getInstance(context2).registerReceiver(this.backgroundLocationReceiver, intentFilter);
                this.mReceiverTag = true;
                LoggerFactory.getTraceLogger().info("H5LocationPlugin", "registerReceiver");
            }
        }
        if (this.screenOffBroadcastReceiver == null) {
            this.screenOffBroadcastReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (context != null && intent != null) {
                        String action = intent.getAction();
                        H5Log.d("H5LocationPlugin", "receive msg: ".concat(String.valueOf(action)));
                        if ("android.intent.action.SCREEN_OFF".equals(action)) {
                            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "screenOffBroadcastReceiver, screen off");
                            H5LocationPlugin.this.stopContinuousLocation(h5BridgeContext);
                            return;
                        }
                        if ("android.intent.action.SCREEN_ON".equals(action)) {
                            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "screenOffBroadcastReceiver, screen on");
                            if (!H5LocationPlugin.this.checkIsBackGroundRunning()) {
                                H5LocationPlugin.this.startTinyAppContinuousLocation(h5Event, h5BridgeContext);
                                return;
                            }
                            H5Log.w("H5LocationPlugin", "mini app is background runnning.");
                        }
                    }
                }
            };
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.SCREEN_OFF");
            intentFilter2.addAction("android.intent.action.SCREEN_ON");
            context2.registerReceiver(this.screenOffBroadcastReceiver, intentFilter2);
        }
    }

    /* access modifiers changed from: private */
    public boolean checkIsBackGroundRunning() {
        return ActivityHelper.isBackgroundRunning() || !isRunForeground();
    }

    private boolean isRunForeground() {
        String packageName = KernelContext.getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) KernelContext.getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo next : runningAppProcesses) {
            if (next.processName.equals(packageName) && next.importance == 100) {
                return true;
            }
        }
        return false;
    }

    private void registerTinyAppFgBgReceiver(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        registerReceiver(this.context, h5Event, h5BridgeContext);
    }

    public void unRegisterReceiver() {
        if (this.context == null) {
            H5Log.e((String) "H5LocationPlugin", (String) "unregisterReceiver failed without context!");
            return;
        }
        if (this.mReceiverTag) {
            H5Log.d("H5LocationPlugin", "unregisterReceiver");
            if (this.backgroundLocationReceiver != null) {
                LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.backgroundLocationReceiver);
                this.mReceiverTag = false;
                this.backgroundLocationReceiver = null;
            }
            if (this.screenOffBroadcastReceiver != null) {
                this.context.unregisterReceiver(this.screenOffBroadcastReceiver);
                this.screenOffBroadcastReceiver = null;
            }
        }
    }
}
