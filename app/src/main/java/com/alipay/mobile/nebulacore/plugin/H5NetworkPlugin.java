package com.alipay.mobile.nebulacore.plugin;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;

public class H5NetworkPlugin extends H5SimplePlugin {
    public static final String TAG = "H5NetworkPlugin";

    private static String a() {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = ((ConnectivityManager) H5Environment.getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable t) {
            H5Log.e(TAG, "Exception", t);
        }
        if (networkInfo == null) {
            return UploadDataResult.FAIL_MSG;
        }
        int nType = networkInfo.getType();
        if (nType == 1 || nType == 9) {
            return "wifi";
        }
        return "wwan";
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.GET_NETWORK_TYPE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (CommonEvents.GET_NETWORK_TYPE.equals(event.getAction())) {
            a(bridgeContext);
        }
        return true;
    }

    private static void a(H5BridgeContext bridgeContext) {
        String value = a();
        String err_msg = "network_type:" + value;
        JSONObject data = new JSONObject();
        data.put((String) "err_msg", (Object) err_msg);
        data.put((String) "networkType", (Object) value);
        data.put((String) "networkInfo", (Object) H5Utils.getNetworkType(H5Environment.getContext()));
        data.put((String) "networkAvailable", (Object) Boolean.valueOf(!UploadDataResult.FAIL_MSG.equals(value)));
        bridgeContext.sendBridgeResult(data);
    }
}
