package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.ArrayList;
import java.util.List;

public class TinyCustomPopMenuPlugin extends H5SimplePlugin {
    private static final String SET_CUSTOM_POP_MENU = "setCustomPopMenu";
    private static final String TAG = "TinyCustomPopMenuPlugin";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("setCustomPopMenu");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if ("setCustomPopMenu".equals(event.getAction())) {
            try {
                JSONObject eventParams = event.getParam();
                if (eventParams != null) {
                    JSONArray menus = eventParams.getJSONArray("menus");
                    if (menus == null) {
                        context.sendError(event, Error.INVALID_PARAM);
                    } else if (event.getTarget() instanceof H5Page) {
                        H5Page h5Page = event.getH5page();
                        List menuList = new ArrayList();
                        int size = menus.size();
                        for (int i = 0; i < size; i++) {
                            JSONObject menuJson = menus.getJSONObject(i);
                            if (menuJson != null) {
                                String name = menuJson.getString("name");
                                if (!TextUtils.isEmpty(name)) {
                                    menuList.add(name);
                                }
                            }
                        }
                        if (menuList.size() > 0) {
                            h5Page.setExtra("setCustomPopMenu", menuList);
                            JSONObject result = new JSONObject();
                            result.put((String) "success", (Object) Boolean.valueOf(true));
                            context.sendBridgeResult(result);
                        }
                    }
                }
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
            }
        }
        return true;
    }
}
