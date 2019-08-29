package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;

public class BaseResp {
    private int code;
    private String msg;
    private String traceId;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public boolean isSuccess() {
        return this.code == DjangoConstant.DJANGO_OK;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId2) {
        this.traceId = traceId2;
    }

    public String toString() {
        return "Response{code=" + this.code + ", msg='" + this.msg + '\'' + ", traceId=" + this.traceId + '}';
    }
}
