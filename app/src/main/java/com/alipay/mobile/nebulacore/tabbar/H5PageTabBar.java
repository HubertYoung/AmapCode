package com.alipay.mobile.nebulacore.tabbar;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.lang.ref.WeakReference;

public class H5PageTabBar extends H5BaseTabBar {
    private boolean d = false;

    public void setPageViewHolder(H5ViewHolder holder) {
        this.a = holder;
    }

    public void addTabBar() {
        if (this.a != null) {
            this.a.refreshView();
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(TemplateTinyApp.TABBAR_KEY);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        this.context = new WeakReference(event.getActivity());
        if (!TemplateTinyApp.TABBAR_KEY.equals(action)) {
            return super.handleEvent(event, bridgeContext);
        }
        handleTabAction(event.getParam(), bridgeContext);
        return true;
    }

    public void handleTabAction(JSONObject param, H5BridgeContext bridgeContext) {
        if (param != null && !param.isEmpty()) {
            String action = H5Utils.getString(param, (String) "action");
            if ("create".equals(action)) {
                if (!this.d) {
                    createTabBar(param, bridgeContext, null);
                    bridgeContext.sendSuccess();
                    this.d = true;
                } else {
                    return;
                }
            }
            if ("redDot".equals(action)) {
                createTabBadge(param, bridgeContext);
            }
        }
    }
}
