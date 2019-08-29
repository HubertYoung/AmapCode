package com.autonavi.miniapp.plugin.lbs;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;

public class H5GetCurrentLocationAction extends H5BaseLocationAction {
    private SimpleLocationCache mSimpleLocationCache;

    public H5GetCurrentLocationAction(H5Event h5Event, H5BridgeContext h5BridgeContext, H5Location h5Location, long j, SimpleLocationCache simpleLocationCache) {
        super(h5Event, h5BridgeContext, h5Location, j);
        this.mTag = "H5GetCurrentLocationAction";
        this.mSimpleLocationCache = simpleLocationCache;
    }

    /* access modifiers changed from: protected */
    public void handleValidDomainEvent() {
        super.handleValidDomainEvent();
        if (this.mH5Location != null) {
            this.mH5Location.getCurrentLocation(this.mH5Event, this.mH5BridgeContext, null, this.mStartTime, this.mSimpleLocationCache);
        }
    }

    /* access modifiers changed from: protected */
    public void doPositiveEvent() {
        super.doPositiveEvent();
        if (this.mH5Service != null) {
            this.mH5Service.setSharedData(this.mFinalDomain, "Y");
        }
        if (this.mH5Location != null) {
            this.mH5Location.getCurrentLocation(this.mH5Event, this.mH5BridgeContext, null, System.currentTimeMillis(), this.mSimpleLocationCache);
        }
    }
}
