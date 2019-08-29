package com.alipay.mobile.tinyappcommon.h5plugin;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;

public class TinyAppBackHomePlugin extends H5SimplePlugin {
    public static final String SHOW_BACK_HOME = "showBackHome";
    public static final String SHOW_BACK_TO_HOMEPAGE = "showBackToHomepage";
    private static final String TAG = TinyAppBackHomePlugin.class.getSimpleName();

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(SHOW_BACK_HOME);
        filter.addAction(SHOW_BACK_TO_HOMEPAGE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (SHOW_BACK_HOME.equals(event.getAction())) {
            showBackHome(event);
        } else if (SHOW_BACK_TO_HOMEPAGE.equals(event.getAction())) {
            showBackToHomepage(event, context);
        }
        return true;
    }

    private void showBackHome(H5Event h5Event) {
        H5Page h5Page = h5Event.getH5page();
        if (h5Page != null) {
            H5Log.d(TAG, "showBackHome..");
            h5Page.setExtra(SHOW_BACK_HOME, Boolean.valueOf(true));
        }
    }

    private void showBackToHomepage(H5Event h5Event, H5BridgeContext context) {
        H5Page h5Page = h5Event.getH5page();
        if (h5Page != null) {
            JSONObject eventParams = h5Event.getParam();
            if (eventParams == null) {
                return;
            }
            if (!eventParams.getBoolean("isHomePage").booleanValue()) {
                h5Page.setExtra(SHOW_BACK_TO_HOMEPAGE, Boolean.valueOf(true));
                JSONObject result = new JSONObject();
                result.put((String) "success", (Object) Boolean.valueOf(true));
                context.sendBridgeResult(result);
                return;
            }
            context.sendError(7, (String) "小程序首页无法添加此功能");
        }
    }
}
