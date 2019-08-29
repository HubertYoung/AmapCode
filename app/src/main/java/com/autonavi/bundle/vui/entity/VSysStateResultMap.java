package com.autonavi.bundle.vui.entity;

import java.util.HashMap;

public class VSysStateResultMap extends HashMap<String, Integer> {
    public Integer get(String str) {
        return (Integer) super.get(str);
    }

    public Integer put(String str, Integer num) {
        return (Integer) super.put(str, num);
    }

    public boolean containsKey(String str) {
        return super.containsKey(str);
    }

    public boolean containsValue(Integer num) {
        return super.containsValue(num);
    }
}
