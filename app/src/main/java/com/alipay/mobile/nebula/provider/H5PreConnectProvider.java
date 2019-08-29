package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Page;

public interface H5PreConnectProvider {
    void clearPreRequest(H5Page h5Page);

    void preConnect(String str, H5Page h5Page);

    void preRequest(String str, H5Page h5Page);
}
