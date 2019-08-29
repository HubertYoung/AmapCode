package com.alibaba.baichuan.android.trade.model;

public class InitResult {
    public int errorCode;
    public String errorMessage;
    public boolean success;

    public static InitResult newFailureResult(int i, String str) {
        InitResult initResult = new InitResult();
        initResult.errorCode = i;
        initResult.errorMessage = str;
        return initResult;
    }

    public static InitResult newSuccessResult() {
        InitResult initResult = new InitResult();
        initResult.success = true;
        return initResult;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
