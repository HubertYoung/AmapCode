package com.alibaba.baichuan.android.trade.adapter.mtop;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.Map;

public class NetworkRequest implements Serializable {
    public String accessToken;
    public String apiName;
    public String apiVersion = "1.0";
    public String authParams = "";
    public Map extHeaders;
    public boolean isPost = true;
    public boolean needAuth = false;
    public boolean needCache = false;
    public boolean needLogin = false;
    public boolean needWua = false;
    public String openAppKey;
    public Map paramMap;
    public int requestType;
    public int timeOut = -1;

    public boolean check() {
        return !TextUtils.isEmpty(this.apiName) && !TextUtils.isEmpty(this.apiVersion);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NetworkRequest [apiName=");
        sb.append(this.apiName);
        sb.append(", apiVersion=");
        sb.append(this.apiVersion);
        sb.append(", params=");
        sb.append(this.paramMap);
        sb.append(", openAppKey=");
        sb.append(this.openAppKey);
        sb.append(", accessToken=");
        sb.append(this.accessToken);
        sb.append(", needAuth=");
        sb.append(this.needAuth);
        sb.append(", needWua=");
        sb.append(this.needWua);
        sb.append("]");
        return sb.toString();
    }
}
