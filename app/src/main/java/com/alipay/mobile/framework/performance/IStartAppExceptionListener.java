package com.alipay.mobile.framework.performance;

public interface IStartAppExceptionListener {
    void onStartAppFail(String str, String str2, String str3);

    void onStartAppReject(String str, String str2, String str3);
}
