package com.autonavi.miniapp.plugin;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.monitor.track.TrackIntegrator.PageInfo;
import java.util.ArrayList;
import java.util.List;

public class LoggerJsApiPlugin extends BasePlugin {
    private static final String JSAPI_GET_TOP_PAGEID = "getTopPageId";
    private List<String> mActions = new ArrayList();

    public LoggerJsApiPlugin() {
        this.mActions.add(JSAPI_GET_TOP_PAGEID);
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction(JSAPI_GET_TOP_PAGEID);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (JSAPI_GET_TOP_PAGEID.equals(h5Event.getAction())) {
            PageInfo pageMonitorCurrentPageInfo = TrackIntegrator.getInstance().getPageMonitorCurrentPageInfo();
            String str = null;
            if (pageMonitorCurrentPageInfo != null) {
                str = pageMonitorCurrentPageInfo.pageId;
            }
            h5BridgeContext.sendBridgeResult("result", str);
        }
        return true;
    }

    public String getScope() {
        return SCOPE_SERVICE;
    }

    public String getEvents() {
        return getEvents(this.mActions);
    }
}
