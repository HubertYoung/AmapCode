package com.alipay.mobile.h5plugin;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;

public class ConfigPlugin extends H5SimplePlugin {
    public static final String ACTION = "configService.getConfig";
    public static final String GET_CLIENT_CONFIG = "getClientConfig";
    private static final String GET_KEY = "configKey";
    private static final String PUT_KEY = "configKey";
    private static final String TAG = "ConfigPlugin";

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (!ACTION.equals(action) && !GET_CLIENT_CONFIG.equals(action)) {
            return super.handleEvent(event, context);
        }
        getConfig(event, context);
        return true;
    }

    public void getConfig(H5Event event, H5BridgeContext context) {
        if (event != null && context != null) {
            JSONObject params = event.getParam();
            if (params != null) {
                Object objGetKey = params.get("configKey");
                if (objGetKey == null) {
                    LoggerFactory.getTraceLogger().info(TAG, "objGetKey is null");
                    JSONObject jsOb = new JSONObject();
                    jsOb.put((String) "configKey", (Object) "");
                    context.sendBridgeResult(jsOb);
                    return;
                }
                String getKey = objGetKey.toString();
                LoggerFactory.getTraceLogger().info(TAG, "getKey = " + getKey);
                String getValue = null;
                try {
                    ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
                    if (configService != null) {
                        getValue = configService.getConfig(getKey);
                    }
                } catch (Exception e) {
                    Log.w(TAG, e);
                }
                if (getValue == null) {
                    getValue = "";
                }
                JSONObject jsOb2 = new JSONObject();
                jsOb2.put((String) "configKey", (Object) getValue);
                context.sendBridgeResult(jsOb2);
            }
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION);
        filter.addAction(GET_CLIENT_CONFIG);
        super.onPrepare(filter);
    }
}
