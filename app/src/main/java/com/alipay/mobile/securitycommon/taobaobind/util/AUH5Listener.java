package com.alipay.mobile.securitycommon.taobaobind.util;

import com.alipay.mobile.h5container.api.H5Listener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Session;

public class AUH5Listener implements H5Listener {
    private final AUH5Plugin a;

    public void onPageDestroyed(H5Page h5Page) {
    }

    public void onSessionCreated(H5Session h5Session) {
    }

    public void onSessionDestroyed(H5Session h5Session) {
    }

    public AUH5Listener(AUH5Plugin aUH5Plugin) {
        this.a = aUH5Plugin;
    }

    public void onPageCreated(H5Page h5Page) {
        this.a.setPage(h5Page);
        h5Page.getPluginManager().register((H5Plugin) this.a);
    }
}
