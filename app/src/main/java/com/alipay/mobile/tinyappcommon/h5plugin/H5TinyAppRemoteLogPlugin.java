package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;

public class H5TinyAppRemoteLogPlugin extends H5SimplePlugin {
    public static final String TAG = "H5TinyAppLogPlugin";
    private static final int TINY_APP_STANDARD_LACK_VALUE = 103;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(H5TinyAppLogUtil.TINY_APP_STANDARD_LOG);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (H5TinyAppLogUtil.TINY_APP_STANDARD_LOG.equals(event.getAction()) && (event.getTarget() instanceof H5Page)) {
            H5Page h5Page = (H5Page) event.getTarget();
            if (h5Page != null) {
                sendTinyAppStandardLogToRemoteOutput(event, H5Utils.getString(h5Page.getParams(), (String) "appId"), h5Page, context);
            }
        }
        return true;
    }

    private void sendTinyAppStandardLogToRemoteOutput(H5Event event, String appId, H5Page h5Page, H5BridgeContext context) {
        final H5Event h5Event = event;
        final H5BridgeContext h5BridgeContext = context;
        final H5Page h5Page2 = h5Page;
        final String str = appId;
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                JSONObject params = h5Event.getParam();
                String errorMsg = H5TinyAppLogUtil.checkMsgIsValid(params);
                if (TextUtils.isEmpty(errorMsg) || h5BridgeContext == null) {
                    H5Utils.sendMsgToRemoteWorkerOrVConsole(str, H5TinyAppLogUtil.TINY_APP_STANDARD_LOG, H5TinyAppLogUtil.buildStandardLogInfo(h5Event.getActivity(), h5Page2, params));
                    if (h5BridgeContext != null) {
                        h5BridgeContext.sendSuccess();
                        return;
                    }
                    return;
                }
                h5BridgeContext.sendError(103, errorMsg);
            }
        });
    }
}
