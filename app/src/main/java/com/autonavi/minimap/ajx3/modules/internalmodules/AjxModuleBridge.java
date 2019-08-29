package com.autonavi.minimap.ajx3.modules.internalmodules;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("ajx.bridge")
public class AjxModuleBridge extends AbstractModule {
    public static final String MODULE_NAME = "ajx.bridge";
    private JsFunctionCallback jsFunctionCallback;
    private String penddingAction = null;
    private String penddingData = null;

    public AjxModuleBridge(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void callJsFunction(String str, String str2) {
        this.penddingAction = str;
        this.penddingData = str2;
        if (this.jsFunctionCallback != null) {
            this.jsFunctionCallback.callback(str, str2);
        }
    }

    @AjxMethod("bridgeGet")
    public void setBridge(JsFunctionCallback jsFunctionCallback2) {
        this.jsFunctionCallback = jsFunctionCallback2;
        if (this.penddingAction != null || this.penddingData != null) {
            jsFunctionCallback2.callback(this.penddingAction, this.penddingData);
        }
    }
}
