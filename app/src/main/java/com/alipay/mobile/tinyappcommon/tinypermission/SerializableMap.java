package com.alipay.mobile.tinyappcommon.tinypermission;

import java.io.Serializable;
import java.util.Map;

public class SerializableMap implements Serializable {
    private Map<String, Boolean> booleanMap;
    private Map<String, String> stringMap;

    public Map<String, String> getStringMap() {
        return this.stringMap;
    }

    public void setStringMap(Map<String, String> stringMap2) {
        this.stringMap = stringMap2;
    }

    public Map<String, Boolean> getBooleanMap() {
        return this.booleanMap;
    }

    public void setBooleanMap(Map<String, Boolean> booleanMap2) {
        this.booleanMap = booleanMap2;
    }
}
