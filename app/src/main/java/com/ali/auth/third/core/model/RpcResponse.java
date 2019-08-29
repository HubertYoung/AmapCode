package com.ali.auth.third.core.model;

public class RpcResponse<T> {
    public String actionType;
    public int code;
    public String codeGroup;
    public String message;
    public String msgCode;
    public String msgInfo;
    public T returnValue;
    public boolean success;
}
