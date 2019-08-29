package com.alipay.mobile.antui.excutor;

public interface ConfigExecutor {
    String getConfig(String str);

    void getConfig(String str, ConfigCallback configCallback);
}
