package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean;

public class Info {
    public String errMsg;
    public int errReason;

    public Info(int errReason2, String errMsg2) {
        this.errReason = errReason2;
        this.errMsg = errMsg2;
    }

    public Info() {
    }
}
