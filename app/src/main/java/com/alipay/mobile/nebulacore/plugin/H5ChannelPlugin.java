package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView;
import java.util.HashMap;

public class H5ChannelPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        filter.addAction(H5EmbedWebView.ACTION_TYPE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!TextUtils.equals(event.getAction(), H5EmbedWebView.ACTION_TYPE)) {
            return super.handleEvent(event, context);
        }
        JSONObject params = event.getParam();
        H5Page h5Page = (H5Page) event.getTarget();
        if (h5Page != null) {
            H5Session h5Session = h5Page.getSession();
            if (h5Session != null) {
                String workId = h5Session.getServiceWorkerID();
                if (!TextUtils.isEmpty(workId)) {
                    H5Log.d("H5ChannelPlugin", "postMessage workId " + workId);
                    H5Service h5Service = H5ServiceUtils.getH5Service();
                    if (h5Service != null) {
                        try {
                            HashMap data = new HashMap();
                            data.put("appId", workId);
                            JSONObject jo = new JSONObject();
                            jo.put((String) "pageId", (Object) Integer.valueOf(h5Page.getPageId()));
                            jo.put((String) "viewId", (Object) Integer.valueOf(h5Page.getWebViewId()));
                            jo.put((String) "data", (Object) params);
                            data.put("message", jo.toJSONString());
                            data.put("messageId", System.currentTimeMillis());
                            h5Service.sendServiceWorkerPushMessage(data);
                        } catch (Throwable t) {
                            H5Log.e("H5ChannelPlugin", "catch exception ", t);
                        }
                    }
                }
            }
        }
        return true;
    }
}
