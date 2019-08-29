package com.sina.weibo.sdk.network.intercept;

import com.sina.weibo.sdk.network.IRequestIntercept;
import java.util.HashMap;

public class GlobalInterceptHelper {
    private static GlobalInterceptHelper globalInterceptHelper;
    private HashMap<String, IRequestIntercept> globalIntercept = new HashMap<>();

    private GlobalInterceptHelper() {
        this.globalIntercept.put(GuestParamInterception.class.getName(), new GuestParamInterception());
        this.globalIntercept.put(CommonParamInterception.class.getName(), new CommonParamInterception());
    }

    public static GlobalInterceptHelper init() {
        if (globalInterceptHelper == null) {
            globalInterceptHelper = new GlobalInterceptHelper();
        }
        return globalInterceptHelper;
    }

    public void addIntercept(String str, IRequestIntercept iRequestIntercept) {
        this.globalIntercept.put(str, iRequestIntercept);
    }

    public void removeIntercept(String str) {
        if (this.globalIntercept.containsKey(str)) {
            this.globalIntercept.remove(str);
        }
    }

    public HashMap<String, IRequestIntercept> getGlobalIntercept() {
        if (this.globalIntercept == null) {
            return new HashMap<>();
        }
        return this.globalIntercept;
    }
}
