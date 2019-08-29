package com.autonavi.miniapp.plugin.safe;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.miniapp.plugin.BasePlugin;
import java.util.Arrays;

public class TinyAmapUserDataPlugin extends BasePlugin {
    private static final String GET_AMAP_USER_DATA = "getAmapUserData";
    private static final String TAG = "TinyAmapUserDataPlugin";

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction(GET_AMAP_USER_DATA);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (GET_AMAP_USER_DATA.equals(h5Event.getAction())) {
            getOpenUserData(h5BridgeContext);
        }
        return true;
    }

    private void getOpenUserData(H5BridgeContext h5BridgeContext) {
        JSONObject jSONObject = new JSONObject();
        String encrypt = TinyAppSafeUtils.encrypt(NetworkParam.getAdiu());
        String encrypt2 = TinyAppSafeUtils.encrypt(NetworkParam.getDiu());
        String encrypt3 = TinyAppSafeUtils.encrypt(NetworkParam.getDiv());
        String encrypt4 = TinyAppSafeUtils.encrypt(NetworkParam.getMac());
        jSONObject.put((String) LocationParams.PARA_COMMON_ADIU, (Object) encrypt);
        jSONObject.put((String) LocationParams.PARA_COMMON_DIU, (Object) encrypt2);
        jSONObject.put((String) LocationParams.PARA_COMMON_DIV, (Object) encrypt3);
        jSONObject.put((String) LocationParams.PARA_COMMON_DIU2, (Object) encrypt4);
        h5BridgeContext.sendBridgeResult(jSONObject);
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(Arrays.asList(new String[]{GET_AMAP_USER_DATA}));
    }
}
