package com.autonavi.minimap.ajx3.modules;

import android.os.Looper;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.HashMap;

@AjxModule("js")
public class ModuleJsBridge extends AbstractModule {
    public static final String MODULE_NAME = "js";
    private HashMap<String, JsFunctionCallback> mJsDataCallbackMap = new HashMap<>();
    private HashMap<String, String> mJsDataMap = new HashMap<>();
    /* access modifiers changed from: private */
    public JsAdapter mJsMethods = new JsAdapter(AMapPageFramework.getPageContext());

    public ModuleJsBridge(IAjxContext iAjxContext) {
        super(iAjxContext);
        getNativeContext();
    }

    @AjxMethod("action")
    public void action(final String str, final JsFunctionCallback jsFunctionCallback) {
        ku.a().c("auiLog", "paramString:".concat(String.valueOf(str)));
        if (this.mJsMethods != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mJsMethods.sendAjx(str, jsFunctionCallback);
                return;
            }
            aho.a(new Runnable() {
                public void run() {
                    ModuleJsBridge.this.mJsMethods.sendAjx(str, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("sendJSData")
    public void sendJSData(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        this.mJsDataMap.put(str, str2);
        this.mJsDataCallbackMap.put(str, jsFunctionCallback);
    }

    public String getJsData(String str) {
        if (this.mJsDataMap.containsKey(str)) {
            return this.mJsDataMap.get(str);
        }
        return null;
    }

    public void callJsDataCallback(String str, String str2) {
        if (this.mJsDataCallbackMap.containsKey(str)) {
            JsFunctionCallback jsFunctionCallback = this.mJsDataCallbackMap.get(str);
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(str2);
            }
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        this.mJsMethods = null;
        this.mJsDataMap.clear();
        this.mJsDataCallbackMap.clear();
    }

    public JsAdapter getJsMethod() {
        return this.mJsMethods;
    }
}
