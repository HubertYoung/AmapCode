package com.alipay.mobile.nebula.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.webview.APWebView;

public class TestDataUtils {
    private static final String TAG = "H5TestDataUtils";
    private static JSONObject bridgeParams = new JSONObject();

    public static void storeJSParams(String key, Object value) {
        if (bridgeParams != null && !bridgeParams.containsKey(key)) {
            H5Log.d(TAG, "store key " + key);
            String[] keys = key.split("\\|");
            if (keys.length < 2) {
                bridgeParams.put(key, value);
            } else if (bridgeParams.containsKey(keys[0])) {
                JSONObject subObj = bridgeParams.getJSONObject(keys[0]);
                if (subObj != null) {
                    subObj.put(keys[1], value);
                }
            } else {
                JSONObject subObj2 = new JSONObject();
                subObj2.put(keys[1], value);
                bridgeParams.put(keys[0], (Object) subObj2);
            }
        }
    }

    public static synchronized void injectJSParams(APWebView webView) {
        synchronized (TestDataUtils.class) {
            if (bridgeParams != null && !bridgeParams.isEmpty()) {
                H5Log.d(TAG, "will be inject into AlipayJSBridge.devPerformance");
                webView.loadUrl("javascript:(function(){if(typeof AlipayJSBridge === 'object'){AlipayJSBridge.devPerformance4Test='" + bridgeParams.toJSONString() + "';}})();");
                bridgeParams.clear();
            }
        }
    }
}
