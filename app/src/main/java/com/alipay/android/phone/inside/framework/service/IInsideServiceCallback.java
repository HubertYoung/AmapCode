package com.alipay.android.phone.inside.framework.service;

public interface IInsideServiceCallback<Result> {
    void onComplted(Result result);

    void onException(Throwable th);
}
