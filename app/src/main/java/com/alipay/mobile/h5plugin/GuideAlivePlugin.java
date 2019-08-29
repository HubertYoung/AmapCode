package com.alipay.mobile.h5plugin;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;

public class GuideAlivePlugin extends H5SimplePlugin {
    public static final String ACTION_GET_AUTHSTATUS = "getAuthStatus";
    public static final String ACTION_GET_OPTION_STATUS = "guideAlivePlugin.getOptionStatus";
    public static final String ACTION_IS_OPTION_SUPPORT = "guideAlivePlugin.isOptionSupport";
    public static final String ACTION_PAGE_IN = "guideAlivePlugin.pageIn";
    public static final String ACTION_PAGE_OUT = "guideAlivePlugin.pageOut";
    public static final String ACTION_SHOW_AUTHGUIDE = "showAuthGuide";
    public static final String ACTION_SHOW_GUIDE = "guideAlivePlugin.showGuide";
    private static final String AUTO_START = "autoboot";
    private static final String BIZ_TYPE = "TransferQRCode";
    private static final String CONFIG_OPTION_STATUS_PREFIX = "GuideAlivePlugin_OptionStatus_";
    private static final String KEEP_ALIVE = "keepalive";
    private static final String OPTION = "option";
    private static final String TAG = "GuideAlivePlugin";
    private static final String VOLUME_ISSUE = "volumeissue";

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        LoggerFactory.getTraceLogger().info(TAG, "handleEvent, action: " + action + ", params: " + event.getParam());
        if ("guideAlivePlugin.pageIn".equals(action)) {
            onEventPageIn(event, context);
            return true;
        } else if ("guideAlivePlugin.getOptionStatus".equals(action)) {
            getOptionStatus(event, context);
            return true;
        } else if ("guideAlivePlugin.isOptionSupport".equals(action)) {
            isOptionSupport(event, context);
            return true;
        } else if ("guideAlivePlugin.showGuide".equals(action)) {
            showGuide(event, context);
            return true;
        } else if ("showAuthGuide".equals(action)) {
            showAuthGuide(event, context);
            return true;
        } else if ("getAuthStatus".equals(action)) {
            getAuthStatus(event, context);
            return true;
        } else if (!"guideAlivePlugin.pageOut".equals(action)) {
            return super.handleEvent(event, context);
        } else {
            onEventPageOut(event, context);
            return true;
        }
    }

    private void isOptionSupport(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "isOptionSupport params is null");
            return;
        }
        params.getString("issue");
        params.getString("source");
        boolean isSupport = false;
        if (VOLUME_ISSUE.equals(params.getString(OPTION))) {
            isSupport = true;
        }
        LoggerFactory.getTraceLogger().info(TAG, "isOptionSupport isSupport = " + isSupport);
        JSONObject jsOb = new JSONObject();
        jsOb.put((String) "support", (Object) Boolean.valueOf(isSupport));
        context.sendBridgeResult(jsOb);
    }

    private void getOptionStatus(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "getOptionStatus params is null");
            return;
        }
        params.getString("issue");
        params.getString("source");
        params.getString(OPTION);
        LoggerFactory.getTraceLogger().info(TAG, "getOptionStatus, status: -1");
        JSONObject jsOb = new JSONObject();
        jsOb.put((String) "status", (Object) Integer.valueOf(-1));
        context.sendBridgeResult(jsOb);
    }

    private void showGuide(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "showGuide params is null");
            return;
        }
        params.getString("issue");
        params.getString("source");
        params.getString(OPTION);
        JSONObject jsOb = new JSONObject();
        jsOb.put((String) "result", (Object) Boolean.valueOf(false));
        context.sendBridgeResult(jsOb);
    }

    private int doAdjustVolume(String appId, String appArg, String issue, String source) {
        if (TextUtils.isEmpty(appId)) {
            appId = "20001108";
        }
        if (appArg == null) {
            appArg = "";
        }
        String scheme = "alipays://platformapi/startapp?appId=" + appId + "&issue=" + issue + "&source=" + source + appArg;
        int result = -1;
        try {
            result = ((SchemeService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(SchemeService.class.getName())).process(Uri.parse(scheme));
            LoggerFactory.getTraceLogger().info(TAG, "doAdjustVolume, scheme: " + scheme + ", result: " + result);
            return result;
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) TAG, t);
            return result;
        }
    }

    private boolean doShowGuide(String option, String issue, String source) {
        if (!AUTO_START.equals(option) && !KEEP_ALIVE.equals(option)) {
            LoggerFactory.getTraceLogger().warn((String) TAG, "doShowGuide fail, option: " + option);
        }
        return false;
    }

    private void getAuthStatus(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "getAuthStatus params is null");
            return;
        }
        params.getString("issue");
        params.getString("source");
        params.getString(OPTION);
        String authType = params.containsKey("authType") ? params.get("authType").toString() : null;
        JSONObject jsOb = new JSONObject();
        if (TextUtils.isEmpty(authType)) {
            context.sendBridgeResult(jsOb);
            return;
        }
        try {
            context.sendBridgeResult(jsOb);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
        }
    }

    private void showAuthGuide(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "showAuthGuide params is null");
            return;
        }
        JSONObject jsOb = new JSONObject();
        params.getString("issue");
        params.getString("source");
        params.getString(OPTION);
        if (TextUtils.isEmpty(params.containsKey("authType") ? params.get("authType").toString() : null)) {
            jsOb.put((String) "shown", (Object) Boolean.valueOf(false));
            context.sendBridgeResult(jsOb);
            return;
        }
        LoggerFactory.getTraceLogger().info(TAG, "getAuthStatus bizType = " + params.get("bizType").toString());
        try {
            context.sendBridgeResult(jsOb);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, e);
            jsOb.put((String) "shown", (Object) Boolean.valueOf(false));
            context.sendBridgeResult(jsOb);
        }
    }

    private void onEventPageIn(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null) {
            LoggerFactory.getTraceLogger().info(TAG, "onEventPageIn params is null");
            return;
        }
        params.getString("issue");
        params.getString("source");
        params.getString(OPTION);
    }

    private void onEventPageOut(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null) {
            LoggerFactory.getTraceLogger().info(TAG, "onEventPageOut params is null");
            return;
        }
        params.getString("issue");
        params.getString("source");
        params.getString(OPTION);
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("guideAlivePlugin.pageIn");
        filter.addAction("guideAlivePlugin.isOptionSupport");
        filter.addAction("guideAlivePlugin.getOptionStatus");
        filter.addAction("guideAlivePlugin.showGuide");
        filter.addAction("showAuthGuide");
        filter.addAction("getAuthStatus");
        filter.addAction("guideAlivePlugin.pageOut");
        super.onPrepare(filter);
    }
}
