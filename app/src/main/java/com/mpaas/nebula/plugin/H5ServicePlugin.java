package com.mpaas.nebula.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.location.sdk.fusion.LocationParams;

public class H5ServicePlugin extends H5SimplePlugin {
    public static final String CHECK_APP = "checkApp";
    public static final String GET_CONFIG = "getConfig";
    public static final String GET_THIRD_PARTY_AUTH_CODE = "getThirdPartyAuthcode";
    public static final String LOGIN = "login";
    public static final String TAG = "H5ServicePlugin";
    public static final String THIRD_PARTY_AUTH = "thirdPartyAuth";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(GET_CONFIG);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        event.getH5page();
        if (!GET_CONFIG.equals(action)) {
            return false;
        }
        a(event, bridgeContext);
        return true;
    }

    private static void a(H5Event event, H5BridgeContext bridgeContext) {
        if (event == null || event.getParam() == null || event.getParam().isEmpty()) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        JSONArray jaKeys = H5Utils.getJSONArray(event.getParam(), "configKeys", null);
        if (jaKeys == null || jaKeys.isEmpty()) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        JSONObject data = new JSONObject();
        for (int index = jaKeys.size() - 1; index >= 0; index--) {
            String key = null;
            try {
                key = jaKeys.getString(index);
            } catch (Exception e) {
            }
            if ("rpcUrl".equals(key)) {
                data.put((String) "rpcUrl", (Object) ReadSettingServerUrl.getInstance().getGWFURL(event.getActivity()));
            } else if ("uuid".equals(key)) {
                data.put((String) "uuid", (Object) a());
            } else if (LocationParams.PARA_COMMON_DID.equals(key)) {
                data.put((String) LocationParams.PARA_COMMON_DID, (Object) DeviceInfo.getInstance().getmDid());
            }
        }
        JSONObject result = new JSONObject();
        result.put((String) "data", (Object) data);
        bridgeContext.sendBridgeResult(result);
    }

    private static String a() {
        String userId = LoggerFactory.getLogContext().getUserId();
        if (TextUtils.isEmpty(userId)) {
            userId = H5Utils.getUserId();
        }
        if (!TextUtils.isEmpty(userId)) {
            return H5SecurityUtil.getMD5(userId + userId + userId);
        }
        return null;
    }
}
