package com.alipay.mobile.nebula.basebridge;

import com.ali.auth.third.login.LoginConstants;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public abstract class H5BaseBridgeContext implements H5BridgeContext {
    public static final String FORBIDDEN = "forbidden!";
    public static final String INVALID_PARAM = "invalid parameter!";
    public static final String NONE_ERROR = "none error!";
    public static final String NOT_FOUND = "not implemented!";
    private static final String TAG = "H5BridgeContextImpl";
    public static final String UNKNOWN_ERROR = "unknown error!";
    public H5Bridge bridge;
    public String id;

    public abstract boolean sendBack(JSONObject jSONObject, boolean z);

    private static String getErrorMsg(Error code) {
        switch (code) {
            case NOT_FOUND:
                return "not implemented!";
            case INVALID_PARAM:
                return "invalid parameter!";
            case UNKNOWN_ERROR:
                return "unknown error!";
            case FORBIDDEN:
                return "forbidden!";
            default:
                return "none error!";
        }
    }

    public boolean sendBridgeResult(JSONObject param) {
        return sendBack(param, false);
    }

    public boolean sendBridgeResult(String key, Object value) {
        JSONObject param = new JSONObject();
        param.put(key, value);
        return sendBridgeResult(param);
    }

    public void sendToWeb(String action, JSONObject param, H5CallBack callback) {
        if (this.bridge != null) {
            this.bridge.sendToWeb(action, param, callback);
        } else {
            H5Log.e((String) TAG, (String) "[FATAL ERROR] in sendToWeb() bridge is null");
        }
    }

    public boolean sendBridgeResultWithCallbackKept(JSONObject param) {
        return sendBack(param, true);
    }

    public boolean sendBridgeResultWithCallbackKept(String key, Object value) {
        JSONObject param = new JSONObject();
        param.put(key, value);
        return sendBridgeResultWithCallbackKept(param);
    }

    public boolean sendError(H5Event event, Error error) {
        H5Log.w(TAG, "sendError " + error + " [action] " + (event == null ? null : event.getAction()));
        JSONObject data = new JSONObject();
        data.put((String) "message", (Object) getErrorMsg(error));
        data.put((String) "error", (Object) Integer.valueOf(error.ordinal()));
        return sendBridgeResult(data);
    }

    public String getId() {
        return this.id;
    }

    public void sendSuccess() {
        sendBridgeResult("success", Boolean.valueOf(true));
    }

    public void sendError(int error, String errorMessage) {
        JSONObject result = new JSONObject();
        result.put((String) "errorMessage", (Object) errorMessage);
        result.put((String) "error", (Object) Integer.valueOf(error));
        sendBridgeResult(result);
    }

    public void sendNoRigHtToInvoke() {
        String prompt = H5Utils.getNebulaResources().getString(R.string.h5_no_right_to_invoke);
        JSONObject result = new JSONObject();
        result.put((String) "errorMessage", (Object) prompt);
        result.put((String) "error", (Object) Integer.valueOf(4));
        sendBridgeResult(result);
    }

    public void sendNoRigHtToInvoke4NewJSAPIPermission() {
        JSONObject result = new JSONObject();
        result.put((String) "errorMessage", (Object) "new jsapi permission deny");
        result.put((String) "error", (Object) Integer.valueOf(4));
        sendBridgeResult(result);
    }

    public void sendNotGrantPermission() {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(10));
        result.put((String) "errorMessage", (Object) H5Utils.getNebulaResources().getString(R.string.h5_no_grant_permission));
        sendBridgeResult(result);
    }

    public void useCancel() {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(11));
        result.put((String) "errorMessage", (Object) H5Utils.getNebulaResources().getString(R.string.h5_default_cancel));
        sendBridgeResult(result);
    }

    public String getInvokeType() {
        return LoginConstants.H5_LOGIN;
    }
}
