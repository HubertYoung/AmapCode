package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5RsaUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.drivecommon.tools.DriveUtil;

public class H5SecurePlugin extends H5SimplePlugin {
    public static final String TAG = "H5SecurePlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.RSA);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (CommonEvents.RSA.equals(event.getAction())) {
            a(event, bridgeContext);
        }
        return true;
    }

    private static void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject params = event.getParam();
        String action = H5Utils.getString(params, (String) "action");
        String content = H5Utils.getString(params, (String) "text");
        String key = H5Utils.getString(params, (String) "key");
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(key)) {
            H5Log.e((String) TAG, (String) "invalid text");
            JSONObject data = new JSONObject();
            data.put((String) "error", (Object) Integer.valueOf(10));
            data.put((String) "errorMessage", (Object) "h5RSA params (text and key) must not be empty");
            bridgeContext.sendBridgeResult(data);
        } else if (TextUtils.equals(action, DriveUtil.SCHEME_PARAM_ENCRYPT) || TextUtils.equals(action, "decrypt")) {
            String result = null;
            if (TextUtils.equals(action, DriveUtil.SCHEME_PARAM_ENCRYPT)) {
                result = H5RsaUtil.encrypt(content, key);
            } else if (TextUtils.equals(action, "decrypt")) {
                result = H5RsaUtil.decrypt(content, key);
            }
            if (TextUtils.isEmpty(result)) {
                JSONObject data2 = new JSONObject();
                data2.put((String) "error", (Object) Integer.valueOf(11));
                if (TextUtils.equals(action, DriveUtil.SCHEME_PARAM_ENCRYPT)) {
                    data2.put((String) "errorMessage", (Object) "Encrypt error");
                } else {
                    data2.put((String) "errorMessage", (Object) "Decrypt error");
                }
                bridgeContext.sendBridgeResult(data2);
                return;
            }
            if (TextUtils.equals("KeyError", result)) {
                JSONObject data3 = new JSONObject();
                data3.put((String) "error", (Object) Integer.valueOf(11));
                if (TextUtils.equals(action, DriveUtil.SCHEME_PARAM_ENCRYPT)) {
                    data3.put((String) "errorMessage", (Object) "Encrypt key error");
                } else {
                    data3.put((String) "errorMessage", (Object) "Decrypt key error");
                }
                bridgeContext.sendBridgeResult(data3);
            }
            JSONObject data4 = new JSONObject();
            data4.put((String) "text", (Object) result);
            bridgeContext.sendBridgeResult(data4);
        } else {
            JSONObject data5 = new JSONObject();
            data5.put((String) "error", (Object) Integer.valueOf(10));
            data5.put((String) "errorMessage", (Object) "h5RSA param action must be encrypt/decrypt");
            bridgeContext.sendBridgeResult(data5);
        }
    }
}
