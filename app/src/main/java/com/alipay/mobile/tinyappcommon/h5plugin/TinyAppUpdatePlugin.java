package com.alipay.mobile.tinyappcommon.h5plugin;

import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;

public class TinyAppUpdatePlugin extends H5SimplePlugin {
    public static final String ACTION_UPDATE_APP = "updateApp";
    private static final String TAG = "TinyAppUpdatePlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_UPDATE_APP);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!ACTION_UPDATE_APP.equals(event.getAction())) {
            return false;
        }
        doUpdateApp(event, context);
        return true;
    }

    private void doUpdateApp(H5Event event, H5BridgeContext context) {
        H5Log.d(TAG, ACTION_UPDATE_APP);
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        Bundle pageParams = h5Page.getParams();
        String pagePath = H5Utils.getString(pageParams, (String) "page");
        String appId = TinyAppParamUtils.getHostAppId(pageParams);
        JSONObject param = new JSONObject();
        param.put((String) "page", (Object) pagePath);
        param.put((String) MicroApplication.KEY_APP_SCENE_ID, (Object) H5Utils.SCAN_APP_ID);
        param.put((String) H5Param.NB_UPDATE, (Object) "syncforce");
        param.put((String) H5Param.NB_OFFLINE, (Object) "sync");
        param.put((String) H5Param.NB_OFFMODE, (Object) "force");
        JSONObject startAppParams = new JSONObject();
        startAppParams.put((String) "appId", (Object) appId);
        startAppParams.put((String) "param", (Object) param);
        h5Service.sendEvent(new Builder().action(H5PageData.FROM_TYPE_START_APP).param(startAppParams).build());
    }
}
