package com.alipay.android.phone.inside.offlinecode.engine;

import android.webkit.JavascriptInterface;
import com.alipay.android.phone.inside.common.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DPEMethodRouter {
    public static final String NAMESPACE_ROOTER = "dpe_method_router";
    Map<String, DynamicCodePlugin> methodMap = new HashMap();

    public boolean registerPlugin(DynamicCodePlugin dynamicCodePlugin) {
        if (dynamicCodePlugin == null) {
            return false;
        }
        List<String> bridgeMethodNames = dynamicCodePlugin.bridgeMethodNames();
        if (bridgeMethodNames == null || bridgeMethodNames.isEmpty()) {
            return false;
        }
        for (String put : bridgeMethodNames) {
            this.methodMap.put(put, dynamicCodePlugin);
        }
        return true;
    }

    @JavascriptInterface
    public String methodRouter(String str, String str2) {
        if (StringUtils.a(str)) {
            return null;
        }
        try {
            DPECallEvent dPECallEvent = new DPECallEvent(str, new JSONObject(str2));
            DynamicCodePlugin findPlugin = findPlugin(str);
            if (findPlugin != null) {
                findPlugin.handleEvent(dPECallEvent);
                return dPECallEvent.getResult();
            }
        } catch (JSONException unused) {
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public DynamicCodePlugin findPlugin(String str) {
        return this.methodMap.get(str);
    }
}
