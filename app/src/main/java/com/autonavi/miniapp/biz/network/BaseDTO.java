package com.autonavi.miniapp.biz.network;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = 7273558851025046665L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
