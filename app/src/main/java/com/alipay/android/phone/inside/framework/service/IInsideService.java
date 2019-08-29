package com.alipay.android.phone.inside.framework.service;

public interface IInsideService<Params, Result> {
    void start(IInsideServiceCallback<Result> iInsideServiceCallback, Params params) throws Exception;

    void start(Params params) throws Exception;

    Result startForResult(Params params) throws Exception;
}
