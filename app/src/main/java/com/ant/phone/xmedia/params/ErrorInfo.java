package com.ant.phone.xmedia.params;

import com.alibaba.fastjson.annotation.JSONField;

public class ErrorInfo {
    @JSONField(name = "mErrInfo")
    public int mCode;
    @JSONField(name = "msg")
    public String mMsg;

    public ErrorInfo(int code, String msg) {
        this.mCode = code;
        this.mMsg = msg;
    }

    public String toString() {
        return "[code:" + this.mCode + ", msg:" + this.mMsg + "]";
    }
}
