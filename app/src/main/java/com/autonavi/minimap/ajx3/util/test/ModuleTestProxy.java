package com.autonavi.minimap.ajx3.util.test;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import org.json.JSONObject;

public class ModuleTestProxy extends AbstractModule implements IModuleTestInterface {
    private IModuleTestInterface mModule;

    public ModuleTestProxy(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mModule = new ModuleTest(iAjxContext);
    }

    public int getValueTest() {
        return this.mModule.getValueTest();
    }

    public void setValueTest(int i) {
        this.mModule.setValueTest(i);
    }

    public JSONObject syncFunctionTest(JSONObject jSONObject) {
        return this.mModule.syncFunctionTest(jSONObject);
    }

    public void asyncFunctionTest(JsFunctionCallback jsFunctionCallback) {
        this.mModule.asyncFunctionTest(jsFunctionCallback);
    }
}
