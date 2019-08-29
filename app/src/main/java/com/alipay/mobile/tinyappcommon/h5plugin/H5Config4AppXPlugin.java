package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.ConfigPlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5Config4AppXPlugin extends H5SimplePlugin {
    private static final String GET_CONFIG_FOR_APPX = "getConfig4Appx";
    private static final String TAG = H5Config4AppXPlugin.class.getSimpleName();
    private String[] SUPPORTED_CONFIG_KEYS = {"tinyApLogLevel"};

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(GET_CONFIG_FOR_APPX);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (GET_CONFIG_FOR_APPX.equals(event.getAction())) {
            getConfigForAppx(event, context);
        }
        return true;
    }

    private void getConfigForAppx(H5Event event, H5BridgeContext context) {
        String configKey = H5Utils.getString(event.getParam(), (String) "key");
        if (TextUtils.isEmpty(configKey)) {
            H5Log.d(TAG, "getConfigForAppx...configKey is empty");
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        boolean legalKey = false;
        String[] strArr = this.SUPPORTED_CONFIG_KEYS;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (strArr[i].equals(configKey)) {
                legalKey = true;
                break;
            } else {
                i++;
            }
        }
        if (!legalKey) {
            H5Log.d(TAG, "getConfigForAppx...illegal configKey : " + configKey);
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        H5Log.d(TAG, "getConfigForAppx...configKey : " + configKey);
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            event.setAction(ConfigPlugin.ACTION);
            JSONObject param = new JSONObject();
            param.put((String) "configKey", (Object) configKey);
            event.setParam(param);
            h5Service.sendEvent(event, context);
        }
    }
}
