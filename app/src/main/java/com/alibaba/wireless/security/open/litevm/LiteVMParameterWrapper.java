package com.alibaba.wireless.security.open.litevm;

import com.alibaba.wireless.security.open.litevm.LiteVMParamType.PARAM_TYPE;

public class LiteVMParameterWrapper {
    private PARAM_TYPE a;
    private Object b;

    public LiteVMParameterWrapper(PARAM_TYPE param_type, Object obj) {
        this.a = param_type;
        this.b = obj;
    }

    public int getType() {
        return this.a.getValue();
    }

    public Object getValue() {
        return this.b;
    }
}
