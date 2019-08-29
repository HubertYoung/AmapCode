package com.autonavi.minimap.ajx3.modules;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONObject;

@AjxModule("longlinkmgr")
public class ModuleLongLinkService extends AbstractModule {
    public static final String CALLBACK_KEY_ARRIVED = "arrived";
    public static final String CALLBACK_KEY_RESPONSE = "response";
    public static final String CALLBACK_KEY_STATUS = "status";
    public static final String MODULE_NAME = "longlinkmgr";
    private long contextId;

    public ModuleLongLinkService(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.contextId = iAjxContext.getId();
    }

    @AjxMethod(invokeMode = "sync", value = "connect")
    public void register(int i, JsFunctionCallback jsFunctionCallback) {
        LonglinkManager.register(this.contextId, i, jsFunctionCallback);
    }

    @AjxMethod(invokeMode = "sync", value = "send")
    public void send(int i, JSONObject jSONObject, boolean z) {
        LonglinkManager.send(i, jSONObject, z);
    }

    @AjxMethod(invokeMode = "sync", value = "disconnect")
    public void disconnect(int i) {
        LonglinkManager.disconnect(this.contextId, i);
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        LonglinkManager.clear(this.contextId);
    }
}
