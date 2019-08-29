package com.alipay.mobile.common.utils.config;

public interface ConfigureCtrlManager {
    void addConfigureChangedListener(ConfigureChangedListener configureChangedListener);

    String getConfgureVersion();

    void notifyConfigureChangedEvent();

    void removeConfigureChangedListener(ConfigureChangedListener configureChangedListener);
}
