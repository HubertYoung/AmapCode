package com.mpaas.nebula.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TimeService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;

public class H5ServerTimePlugin extends H5SimplePlugin {
    public static final String GET_SERVER_TIME = "getServerTime";
    public static final String TAG = "H5ServerTimePlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(GET_SERVER_TIME);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (TextUtils.equals(GET_SERVER_TIME, event.getAction())) {
            TimeService timeService = (TimeService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TimeService.class.getName());
            H5Log.d(TAG, "timeService == null" + (timeService == null));
            if (timeService != null) {
                long time = timeService.getServerTime(true);
                H5Log.d(TAG, "time is " + time);
                JSONObject result = new JSONObject();
                result.put((String) "time", (Object) Long.valueOf(time));
                context.sendBridgeResult(result);
            }
        }
        return true;
    }
}
