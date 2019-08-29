package com.alipay.mobile.h5container.api;

public class H5SimplePlugin implements H5Plugin {
    public void onInitialize(H5CoreNode coreNode) {
    }

    public void onPrepare(H5EventFilter filter) {
    }

    public void onRelease() {
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        return false;
    }
}
