package com.alipay.android.phone.inside.api.result;

public class ResultCode {
    private String mMemo = "";
    private String mValue = "";

    protected ResultCode(String str) {
        this.mValue = str;
    }

    protected ResultCode(String str, String str2) {
        this.mValue = str;
        this.mMemo = str2;
    }

    public String getMemo() {
        return this.mMemo;
    }

    public String getValue() {
        return this.mValue;
    }
}
