package com.autonavi.minimap.ajx3.util.test;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import org.json.JSONException;
import org.json.JSONObject;

public class ModuleTest extends AbstractModule implements IModuleTestInterface {
    private int asyncFunctionTestCount = 0;
    private int syncFunctionTestCount = 0;
    private int valueTest = -99;

    public ModuleTest(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public int getValueTest() {
        return this.valueTest;
    }

    public void setValueTest(int i) {
        this.valueTest = i;
    }

    public JSONObject syncFunctionTest(JSONObject jSONObject) {
        try {
            int i = this.syncFunctionTestCount + 1;
            this.syncFunctionTestCount = i;
            jSONObject.put("syncFunctionTest testCount", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void asyncFunctionTest(JsFunctionCallback jsFunctionCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            int i = this.asyncFunctionTestCount + 1;
            this.asyncFunctionTestCount = i;
            jSONObject.put("asyncFunctionTest testCount", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsFunctionCallback.callback(jSONObject);
    }
}
