package com.autonavi.minimap.route.sharebike.model;

import java.io.Serializable;

public abstract class BaseNetResult implements Serializable {
    public static final int ACCOUNT_NOT_MATCH = 7;
    public static final int NOT_LOGIN = 14;
    public int errorCode;
    public String errorToast;
    public boolean result;
}
