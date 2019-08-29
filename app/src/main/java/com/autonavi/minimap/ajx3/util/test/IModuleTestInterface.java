package com.autonavi.minimap.ajx3.util.test;

import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONObject;

public interface IModuleTestInterface {
    void asyncFunctionTest(JsFunctionCallback jsFunctionCallback);

    int getValueTest();

    void setValueTest(int i);

    JSONObject syncFunctionTest(JSONObject jSONObject);
}
