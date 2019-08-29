package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.Nebula;

public class H5DefaultPlugin extends H5SimplePlugin {
    public static final String TAG = "H5DefaultPlugin";

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.H5_PAGE_SHOULD_LOAD_URL.equals(action)) {
            a(event);
        } else if (CommonEvents.H5_PAGE_SHOULD_LOAD_DATA.equals(action)) {
            b(event);
        } else if (CommonEvents.H5_TOOLBAR_MENU_BT.equals(action)) {
            JSONObject eventParam = event.getParam();
            JSONObject param = new JSONObject();
            param.put((String) "data", (Object) eventParam);
            H5Page h5Page = (H5Page) event.getTarget();
            if (TextUtils.equals("popmenu", eventParam.getString(H5Param.POP_MENU_TYPE))) {
                h5Page.getBridge().sendToWeb("popMenuClick", param, null);
            } else {
                h5Page.getBridge().sendToWeb("toolbarMenuClick", param, null);
            }
        } else if (Nebula.DEBUG) {
            H5Log.d(TAG, "default handler handle event " + action);
        }
        return true;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_SHOULD_LOAD_URL);
        filter.addAction(CommonEvents.H5_PAGE_SHOULD_LOAD_DATA);
        filter.addAction(CommonEvents.H5_TOOLBAR_MENU_BT);
        filter.addAction(CommonEvents.H5_PAGE_PROGRESS);
        filter.addAction(CommonEvents.H5_PAGE_LOAD_RESOURCE);
        filter.addAction(CommonEvents.H5_PAGE_CLOSED);
        filter.addAction(CommonEvents.H5_PAGE_BACKGROUND);
    }

    private static void a(H5Event event) {
        Builder builder = new Builder();
        builder.action(CommonEvents.H5_PAGE_DO_LOAD_URL).param(event.getParam()).target(event.getTarget());
        Nebula.getDispatcher().dispatch(builder.build(), null);
    }

    private static void b(H5Event event) {
        Builder builder = new Builder();
        builder.action(CommonEvents.H5_PAGE_LOAD_DATA).param(event.getParam()).target(event.getTarget());
        Nebula.getDispatcher().dispatch(builder.build(), null);
    }
}
