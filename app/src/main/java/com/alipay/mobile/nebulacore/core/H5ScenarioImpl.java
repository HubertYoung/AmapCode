package com.alipay.mobile.nebulacore.core;

import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Scenario;
import com.alipay.mobile.nebulacore.data.H5PrefData;

public class H5ScenarioImpl implements H5Scenario {
    public static final String TAG = "H5Scenario";
    private String a;
    private H5Data b;

    public H5ScenarioImpl(String name) {
        setName(name);
    }

    public H5Data getData() {
        return this.b;
    }

    public void setData(H5Data data) {
        this.b = data;
    }

    public String getName() {
        return this.a;
    }

    public void setName(String name) {
        this.a = name;
        this.b = new H5PrefData(name);
    }
}
