package com.alipay.mobile.nebulauc.impl.network.provider;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.provider.H5PreConnectProvider;
import com.alipay.mobile.nebulauc.impl.network.AlipayNetwork;
import com.alipay.mobile.nebulauc.impl.network.H5PreConnectManager;

public class H5PreConnectProviderImpl implements H5PreConnectProvider {
    public void preRequest(String url, H5Page h5Page) {
        if (AlipayNetwork.getInstance() != null && H5PreConnectManager.isPreDownloadEnabled()) {
            H5PreConnectManager.getInstance().preRequest(url, h5Page);
        }
    }

    public void clearPreRequest(H5Page h5Page) {
        if (AlipayNetwork.getInstance() != null && H5PreConnectManager.isPreDownloadEnabled()) {
            H5PreConnectManager.getInstance().clearPreRequest(h5Page);
        }
    }

    public void preConnect(String mainDocUrl, H5Page h5Page) {
        if (AlipayNetwork.getInstance() != null && H5PreConnectManager.isPreConnectEnabled()) {
            H5PreConnectManager.getInstance().preConnect(mainDocUrl, h5Page);
        }
    }
}
