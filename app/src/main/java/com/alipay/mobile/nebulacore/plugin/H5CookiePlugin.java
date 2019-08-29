package com.alipay.mobile.nebulacore.plugin;

import android.text.TextUtils;
import android.webkit.CookieManager;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.regex.Pattern;

public class H5CookiePlugin extends H5SimplePlugin {
    public static final String TAG = "H5CookiePlugin";
    private H5Page a = null;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.GET_MTOP_TOKEN);
        filter.addAction(CommonEvents.GET_COOKIE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.GET_MTOP_TOKEN.equals(action)) {
            try {
                a(event, bridgeContext);
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        } else if (CommonEvents.GET_COOKIE.equals(action) && (event.getTarget() instanceof H5Page)) {
            this.a = (H5Page) event.getTarget();
            a(bridgeContext);
        }
        return true;
    }

    private void a(H5BridgeContext bridgeContext) {
        String appId = H5Utils.getString(this.a.getParams(), (String) "appId");
        boolean isH5app = H5Utils.getBoolean(this.a.getParams(), (String) H5Param.isH5app, false);
        H5Log.d(TAG, "appId:" + appId + " isH5App:" + isH5app);
        if (!isH5app) {
            b(bridgeContext);
            return;
        }
        JSONObject result = new JSONObject();
        String currentUrl = this.a.getUrl();
        String cookieStr = H5CookieUtil.getCookie(currentUrl);
        H5Log.d(TAG, "host:" + currentUrl + " cookieStr:" + cookieStr);
        if (!TextUtils.isEmpty(cookieStr)) {
            result.put((String) "success", (Object) Boolean.valueOf(true));
            result.put((String) "cookie", (Object) cookieStr);
            bridgeContext.sendBridgeResult(result);
            return;
        }
        result.put((String) "error", (Object) Integer.valueOf(11));
        result.put((String) "errorMessage", (Object) H5Environment.getResources().getString(R.string.h5_not_get_value));
        bridgeContext.sendBridgeResult(result);
    }

    private static void b(H5BridgeContext bridgeContext) {
        String prompt = H5Environment.getResources().getString(R.string.h5_norightinvoke);
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) Integer.valueOf(4));
        result.put((String) "errorMessage", (Object) prompt);
        bridgeContext.sendBridgeResult(result);
    }

    private static void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        String url = ".taobao.com";
        if (param != null && param.containsKey("domain") && !TextUtils.isEmpty(param.getString("domain"))) {
            url = param.getString("domain");
        }
        String cookie = CookieManager.getInstance().getCookie(url);
        String token = "";
        if (!TextUtils.isEmpty(cookie)) {
            Pattern pattern = H5PatternHelper.compile("; ");
            if (pattern != null) {
                String[] split = pattern.split(cookie);
                int length = split.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String pair = split[i];
                    if (pair != null && pair.startsWith("_m_h5_tk=")) {
                        token = pair.replace("_m_h5_tk=", "");
                        break;
                    }
                    i++;
                }
            }
        }
        JSONObject result = new JSONObject();
        result.put((String) "token", (Object) token);
        bridgeContext.sendBridgeResult(result);
    }
}
