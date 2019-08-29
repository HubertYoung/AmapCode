package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.web.H5ScriptLoader;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;

public class H5BridgePlugin extends H5SimplePlugin {
    public static final String TAG = "H5BridgePlugin";
    private H5ScriptLoader a = this.b.getScriptLoader();
    private H5PageImpl b;

    public H5BridgePlugin(H5PageImpl page) {
        this.b = page;
    }

    public void onRelease() {
        this.a = null;
        this.b = null;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_STARTED_SYNC);
        filter.addAction(CommonEvents.H5_PAGE_FINISHED_SYNC);
        filter.addAction(CommonEvents.H5_PAGE_RECEIVED_TITLE);
        filter.addAction(CommonEvents.H5_PAGE_JS_PARAM);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        boolean normalUrl;
        String action = event.getAction();
        String url = H5Utils.getString(event.getParam(), (String) "url");
        if (TextUtils.isEmpty(url) || (!url.startsWith("file://") && !url.startsWith(AjxHttpLoader.DOMAIN_HTTP) && !url.startsWith(AjxHttpLoader.DOMAIN_HTTPS))) {
            normalUrl = false;
        } else {
            normalUrl = true;
        }
        if (!normalUrl) {
            H5Log.w(TAG, "skip load js for abnormal url: " + url);
        }
        if (CommonEvents.H5_PAGE_RECEIVED_TITLE.equals(action)) {
            if (normalUrl) {
                this.a.loadScript();
            }
        } else if (CommonEvents.H5_PAGE_FINISHED_SYNC.equals(action)) {
            if (normalUrl) {
                this.a.loadScript();
            }
        } else if (CommonEvents.H5_PAGE_STARTED_SYNC.equals(action)) {
            this.a.resetBridge();
        }
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (!CommonEvents.H5_PAGE_JS_PARAM.equals(event.getAction())) {
            return false;
        }
        JSONObject param = event.getParam();
        for (String k : param.keySet()) {
            String v = H5Utils.getString(param, k);
            if (!TextUtils.isEmpty(k) && !TextUtils.isEmpty(v)) {
                this.a.setParamsToWebPage(k, v);
            }
        }
        return true;
    }
}
