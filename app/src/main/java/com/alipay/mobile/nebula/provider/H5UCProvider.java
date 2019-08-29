package com.alipay.mobile.nebula.provider;

import android.content.res.Configuration;

public interface H5UCProvider {
    String getWebViewCoreSoPath();

    boolean isM40();

    boolean notifyConfigurationChanged(Configuration configuration);
}
