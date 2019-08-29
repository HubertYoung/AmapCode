package com.alibaba.baichuan.android.trade.adapter.ut;

public class UsabilityErrorMsg {
    public String errCode = "";
    public String errMsg = "";
    public String linkCode = "";
    public String modelCode = "";

    public UsabilityErrorMsg(String str, String str2, String str3, String str4) {
        this.modelCode = str;
        this.linkCode = str2;
        this.errCode = str3;
        this.errMsg = str4;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public String getErrorCodeString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.modelCode);
        sb.append(this.linkCode);
        sb.append(this.errCode);
        return sb.toString();
    }
}
