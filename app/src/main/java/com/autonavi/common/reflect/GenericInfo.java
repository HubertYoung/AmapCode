package com.autonavi.common.reflect;

import java.lang.reflect.Type;

public class GenericInfo {
    public Type parameterizedType;
    public Class<?> rawType;

    public void clear() {
        this.rawType = null;
        this.parameterizedType = null;
    }
}
