package com.alipay.mobile.logging;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.autonavi.miniapp.plugin.lbs.H5Location;

public class TinyLoggingConfigPlugin extends H5SimplePlugin {
    public static final String ACTION_TRACKER_CONFIG = "trackerConfig";
    public static final String QUERY_TRACKER_CONFIG = "queryTrackerConfig";
    public static final String SHOULD_TRACK = "isTrackerEnable";
    private static String TAG = "TinyLoggingConfigPlugin";
    private static final String TRIGGER_UPLOAD = "triggerUpload";

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!ACTION_TRACKER_CONFIG.equals(event.getAction())) {
            return super.handleEvent(event, context);
        }
        dealAction(event, context);
        return true;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_TRACKER_CONFIG);
        super.onPrepare(filter);
    }

    private void dealAction(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params != null) {
            String requestType = params.get(H5Location.REQUEST_TYPE).toString();
            LoggerFactory.getTraceLogger().info(TAG, "requestType = " + requestType);
            JSONObject p = params.getJSONObject("params");
            if (QUERY_TRACKER_CONFIG.equals(requestType)) {
                queryTrackerConfig(p, context);
            } else if (SHOULD_TRACK.equals(requestType)) {
                shouldTrack(p, context);
            } else if (TRIGGER_UPLOAD.equals(requestType)) {
                triggerUpload(p, context);
            }
        }
    }

    private void queryTrackerConfig(JSONObject params, H5BridgeContext context) {
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "queryTrackerConfig params is null or context is null");
            return;
        }
        try {
            context.sendBridgeResult(JSONObject.parseObject(TinyLoggingConfigManager.getInstance().queryTrackerConfig(params.getString("appId"))));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(TAG, e);
        }
    }

    private void shouldTrack(JSONObject params, H5BridgeContext context) {
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "shouldTrack params is null or context is null");
            return;
        }
        String appId = params.getString("appId");
        boolean shouldTrack = TinyLoggingConfigManager.getInstance().shouldTrack(appId);
        try {
            JSONObject jsOb = new JSONObject();
            jsOb.put((String) "appId", (Object) appId);
            jsOb.put((String) "enable", (Object) Boolean.valueOf(shouldTrack));
            context.sendBridgeResult(jsOb);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(TAG, e);
        }
    }

    private void triggerUpload(JSONObject params, H5BridgeContext context) {
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info(TAG, "triggerUpload params is null or context is null");
            return;
        }
        String appId = params.getString("appId");
        String env = params.getString("env");
        LoggerFactory.getTraceLogger().info(TAG, "triggerUpload appId = " + appId + " env = " + env);
        JSONObject jsOb = new JSONObject();
        jsOb.put((String) "appId", (Object) appId);
        if (TextUtils.isEmpty(appId)) {
            jsOb.put((String) "success", (Object) Boolean.valueOf(false));
        } else {
            TinyLoggingConfigManager.getInstance().triggerUpload(appId, env);
            jsOb.put((String) "success", (Object) Boolean.valueOf(true));
        }
        try {
            context.sendBridgeResult(jsOb);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(TAG, e);
        }
    }
}
