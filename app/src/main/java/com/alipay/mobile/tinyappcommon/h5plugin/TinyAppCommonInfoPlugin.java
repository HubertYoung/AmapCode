package com.alipay.mobile.tinyappcommon.h5plugin;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;

public class TinyAppCommonInfoPlugin extends H5SimplePlugin {
    public static final String SET_APPX_VERSION = "setAppxVersion";
    private static final String TAG = TinyAppCommonInfoPlugin.class.getSimpleName();

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(SET_APPX_VERSION);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (SET_APPX_VERSION.equals(event.getAction())) {
            setAppxVersion(event, context);
        }
        return true;
    }

    private void setAppxVersion(H5Event event, H5BridgeContext context) {
        String version = H5Utils.getString(event.getParam(), (String) H5TinyAppLogUtil.TINY_APP_STANDARD_EXTRA_APPXVERSION);
        H5Log.d(TAG, "setAppxVersion...version=" + version);
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        TinyAppStorage.getInstance().setAppxVersion(H5Utils.getString(h5Page.getParams(), (String) "appId"), version);
        context.sendSuccess();
    }
}
