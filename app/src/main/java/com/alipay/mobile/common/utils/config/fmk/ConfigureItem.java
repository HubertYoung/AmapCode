package com.alipay.mobile.common.utils.config.fmk;

public interface ConfigureItem {
    String getConfigName();

    double getDoubleValue();

    int getIntValue();

    long getLongValue();

    String getStringValue();

    void setValue(String str);
}
