package com.alipay.mobile.nebulacore.biz;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.sdk.util.j;
import java.lang.ref.WeakReference;
import java.util.HashMap;

class H5BizUtil {
    private static HashMap<String, WeakReference<H5BizServiceAdvice>> a = new HashMap<>();
    private static HashMap<String, H5BridgeContext> b = new HashMap<>();
    private static HashMap<String, JSONObject> c = new HashMap<>();

    H5BizUtil() {
    }

    static boolean a(String key, H5BridgeContext context) {
        WeakReference ref = a.get(key);
        if (ref != null && ref.get() != null) {
            return false;
        }
        H5BizServiceAdvice advice = new H5BizServiceAdvice(key);
        a.put(key, new WeakReference(advice));
        FrameworkPointCutManager.getInstance().registerPointCutAdvice(new String[]{PointCutConstants.BASEFRAGMENTACTIVITY_FINISH, PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_FINISHAPP}, (Advice) advice);
        b.put(key, context);
        return true;
    }

    static void a(String key) {
        WeakReference ref = a.get(key);
        if (ref != null && ref.get() != null) {
            FrameworkPointCutManager.getInstance().unRegisterPointCutAdvice((Advice) ref.get());
        }
    }

    static boolean a(String key, JSONObject result) {
        result.remove("funcName");
        c.put(key, result);
        return true;
    }

    static boolean b(String key) {
        H5BridgeContext bridgeContext = b.get(key);
        a(key);
        if (bridgeContext != null) {
            JSONObject result = c.get(key);
            if (result == null) {
                a(bridgeContext, (String) "6001");
            } else {
                bridgeContext.sendBridgeResult(result);
            }
        }
        b.remove(key);
        c.remove(key);
        a.remove(key);
        return true;
    }

    static void a(H5BridgeContext bridgeContext, String errorCode) {
        JSONObject result = new JSONObject();
        result.put((String) j.a, (Object) errorCode);
        bridgeContext.sendBridgeResult(result);
    }

    static boolean c(String key) {
        return a.containsKey(key);
    }
}
