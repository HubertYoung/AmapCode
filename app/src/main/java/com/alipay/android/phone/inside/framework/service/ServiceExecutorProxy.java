package com.alipay.android.phone.inside.framework.service;

import com.alipay.android.phone.inside.framework.plugin.PluginManager;

class ServiceExecutorProxy {
    ServiceExecutorProxy() {
    }

    public static <Params> void a(String str, Params params) throws Exception {
        IInsideService b = PluginManager.b(str);
        if (b != null) {
            b.start(params);
        }
    }

    public static <Params, Result> void a(String str, Params params, IInsideServiceCallback<Result> iInsideServiceCallback) throws Exception {
        IInsideService b = PluginManager.b(str);
        if (b != null) {
            b.start(iInsideServiceCallback, params);
        }
    }

    public static <Params, Result> Result b(String str, Params params) throws Exception {
        IInsideService b = PluginManager.b(str);
        if (b != null) {
            return b.startForResult(params);
        }
        return null;
    }
}
