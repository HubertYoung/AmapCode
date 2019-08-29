package com.alipay.mobile.h5container.api;

public interface H5Listener {
    void onPageCreated(H5Page h5Page);

    void onPageDestroyed(H5Page h5Page);

    void onSessionCreated(H5Session h5Session);

    void onSessionDestroyed(H5Session h5Session);
}
