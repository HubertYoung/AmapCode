package com.alipay.mobile.beehive.plugins;

import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.plugins.utils.BeeH5PluginLogger;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;

public abstract class BaseBeehivePlugin extends H5SimplePlugin {
    public static final int ERROR_INTERNAL = 40;
    public static final int ERROR_USER_CANCEL = 10;
    private static final String TAG = "BasePlugin";

    /* access modifiers changed from: protected */
    public abstract boolean onActionCalled(String str, H5Event h5Event, H5BridgeContext h5BridgeContext, Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract String[] registerAction();

    public void onPrepare(H5EventFilter filter) {
        for (String a : registerAction()) {
            filter.addAction(a);
        }
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (!(event.getTarget() instanceof H5Page)) {
            BeeH5PluginLogger.warn((String) TAG, (String) "not from h5 page.");
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return true;
        }
        BeeH5PluginLogger.warn((String) TAG, "onActionCalled:" + action);
        Bundle bundle = new Bundle();
        SpmUtils.addSourceByH5Event(event, bundle);
        return onActionCalled(action, event, bridgeContext, bundle);
    }

    /* access modifiers changed from: protected */
    public void notifyFail(H5BridgeContext bridgeContext, int errorCode, String errorDesc, Runnable postDo) {
        BeeH5PluginLogger.debug(TAG, "fail reason: " + errorDesc);
        JSONObject failedMsg = new JSONObject();
        failedMsg.put((String) "success", (Object) Boolean.valueOf(false));
        failedMsg.put((String) "errorCode", (Object) Integer.valueOf(errorCode));
        failedMsg.put((String) "errorDesc", (Object) errorDesc);
        bridgeContext.sendBridgeResult(failedMsg);
        if (postDo != null) {
            postDo.run();
        }
    }

    /* access modifiers changed from: protected */
    public void notifyFail(H5BridgeContext bridgeContext, int errorCode, String errorDesc) {
        notifyFail(bridgeContext, errorCode, errorDesc, null);
    }

    public static void notifyInvalidParam(H5BridgeContext c) {
        JSONObject failedMsg = new JSONObject();
        failedMsg.put((String) "success", (Object) Boolean.valueOf(false));
        failedMsg.put((String) "errorCode", (Object) Error.INVALID_PARAM);
        failedMsg.put((String) "errorMessage", (Object) Error.INVALID_PARAM.toString());
        c.sendBridgeResultWithCallbackKept(failedMsg);
    }
}
