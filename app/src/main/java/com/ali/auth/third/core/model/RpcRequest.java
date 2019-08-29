package com.ali.auth.third.core.model;

import java.util.ArrayList;

public class RpcRequest {
    public ArrayList<String> paramNames = new ArrayList<>();
    public ArrayList<Object> paramValues = new ArrayList<>();
    public String target;
    public String version;

    public void addParam(String str, Object obj) {
        this.paramNames.add(str);
        this.paramValues.add(obj);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RpcRequest [target=");
        sb.append(this.target);
        sb.append(", version=");
        sb.append(this.version);
        sb.append(", params=]");
        return sb.toString();
    }
}
