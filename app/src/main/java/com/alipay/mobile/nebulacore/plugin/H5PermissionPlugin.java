package com.alipay.mobile.nebulacore.plugin;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5PermissionPlugin extends H5SimplePlugin {
    public static final String TAG = "H5PermissionPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.GET_CAMERA_AUTHORIZED_STATUS);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (CommonEvents.GET_CAMERA_AUTHORIZED_STATUS.equals(event.getAction())) {
            a(bridgeContext);
        }
        return true;
    }

    private static void a(H5BridgeContext bridgeContext) {
        String[] reqPermissionsPermissions = {"android.permission.CAMERA"};
        Context context = H5Environment.getContext();
        JSONObject data = new JSONObject();
        if (PermissionUtils.hasSelfPermissions(context, reqPermissionsPermissions)) {
            H5Log.d(TAG, "get CAMERA permission PERMISSION_GRANTED!");
            data.put((String) "authorizedStatus", (Object) "Authorized");
        } else {
            H5Log.d(TAG, "get CAMERA permission PERMISSION_DENIED!");
            data.put((String) "authorizedStatus", (Object) "NotDetermined");
        }
        bridgeContext.sendBridgeResult(data);
    }
}
