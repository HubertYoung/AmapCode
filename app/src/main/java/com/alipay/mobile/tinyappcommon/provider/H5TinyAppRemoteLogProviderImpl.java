package com.alipay.mobile.tinyappcommon.provider;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.provider.H5TinyAppRemoteLogProvider;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;

public class H5TinyAppRemoteLogProviderImpl implements H5TinyAppRemoteLogProvider {
    public boolean isRemoteOutputConnected(H5Event event) {
        if (!(event.getTarget() instanceof H5Page)) {
            return false;
        }
        H5Page h5Page = (H5Page) event.getTarget();
        if (h5Page == null) {
            return false;
        }
        if (H5Utils.isRemoteDebugConnected(H5Utils.getString(h5Page.getParams(), (String) "appId"))) {
            return true;
        }
        if (H5Utils.isVConsolePanelOpened()) {
            return true;
        }
        return false;
    }

    public String sendStandardLogToRemoteOutput(H5Event event, JSONObject message) {
        if (event.getTarget() instanceof H5Page) {
            final H5Page h5Page = (H5Page) event.getTarget();
            if (h5Page == null) {
                return null;
            }
            String errorMsg = H5TinyAppLogUtil.checkMsgIsValid(message);
            if (!TextUtils.isEmpty(errorMsg)) {
                return errorMsg;
            }
            final String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            final H5Event h5Event = event;
            final JSONObject jSONObject = message;
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    H5Utils.sendMsgToRemoteWorkerOrVConsole(appId, H5TinyAppLogUtil.TINY_APP_STANDARD_LOG, H5TinyAppLogUtil.buildStandardLogInfo(h5Event.getActivity(), h5Page, jSONObject));
                }
            });
        }
        return null;
    }
}
