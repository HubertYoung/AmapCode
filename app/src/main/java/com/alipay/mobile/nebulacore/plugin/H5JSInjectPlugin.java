package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5NebulaDebugProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.web.H5WebView;

public class H5JSInjectPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        filter.addAction("insertJS");
        filter.addAction("switchWebView");
        filter.addAction("getPhoneStateInfo4Debug");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if ("insertJS".equals(action)) {
            a(event);
            return true;
        } else if ("switchWebView".equals(action)) {
            b(event);
            return true;
        } else if (!"getPhoneStateInfo4Debug".equals(action)) {
            return false;
        } else {
            H5NebulaDebugProvider provider = (H5NebulaDebugProvider) H5Utils.getProvider(H5NebulaDebugProvider.class.getName());
            if (provider == null) {
                return true;
            }
            provider.getPhoneStateInfo4Debug(event, bridgeContext);
            return true;
        }
    }

    private static void a(H5Event event) {
        JSONObject params = event.getParam();
        if (params == null) {
            H5Log.e((String) "H5JSInjectPlugin", (String) "param is null");
            return;
        }
        String jsUrl = params.getString("jsUrl");
        H5Log.d("H5JSInjectPlugin", "jsUrl = " + jsUrl);
        if (!TextUtils.isEmpty(jsUrl)) {
            H5DevConfig.H5_LOAD_JS = jsUrl;
            H5Page h5Page = (H5Page) event.getTarget();
            if (h5Page != null && (h5Page instanceof H5PageImpl)) {
                ((H5PageImpl) h5Page).getScriptLoader().loadDynamicJs4Jsapi((H5WebView) h5Page.getWebView(), jsUrl);
            }
        }
    }

    private static void b(H5Event event) {
        JSONObject param = event.getParam();
        if (param != null && !param.isEmpty()) {
            H5DevConfig.setBooleanConfig(H5DevConfig.H5_READ_USE_WEBVIEW_CONFIG, true);
            boolean useUCWebView = H5Utils.getBoolean(param, (String) "useUC", true);
            H5Log.d("H5JSInjectPlugin", "useUCWebView " + useUCWebView);
            H5DevConfig.setBooleanConfig(H5DevConfig.H5_USE_UC_WEBVIEW, useUCWebView);
        }
    }
}
