package com.alipay.mobile.nebulacore.data;

import com.alipay.mobile.h5container.api.H5Data;
import java.util.HashMap;
import java.util.Map;

public class H5MemData implements H5Data {
    private Map<String, String> a = new HashMap();

    public void set(String name, String value) {
        this.a.put(name, value);
    }

    public String get(String name) {
        return this.a.get(name);
    }

    public String remove(String name) {
        return this.a.remove(name);
    }

    public boolean has(String name) {
        return this.a.containsKey(name);
    }
}
