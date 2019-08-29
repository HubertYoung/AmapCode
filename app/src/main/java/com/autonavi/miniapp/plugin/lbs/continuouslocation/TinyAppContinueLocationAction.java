package com.autonavi.miniapp.plugin.lbs.continuouslocation;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.autonavi.miniapp.plugin.lbs.H5BaseLocationAction;
import com.autonavi.miniapp.plugin.lbs.H5Location;

public class TinyAppContinueLocationAction extends H5BaseLocationAction {
    private TinyAppContinueLocation tinyAppContinueLocation;

    public TinyAppContinueLocationAction(H5Event h5Event, H5BridgeContext h5BridgeContext, H5Location h5Location, long j) {
        super(h5Event, h5BridgeContext, h5Location, j);
        this.mTag = "TinyAppContinueLocationAction";
    }

    public void handleValidDomainEvent() {
        super.handleValidDomainEvent();
        if (this.tinyAppContinueLocation != null) {
            this.tinyAppContinueLocation.startContinuousLocation(this.mH5Event, this.mH5BridgeContext);
        }
    }

    public void doPositiveEvent() {
        super.doPositiveEvent();
        if (this.mH5Service != null) {
            this.mH5Service.setSharedData(this.mFinalDomain, "Y");
        }
        if (this.tinyAppContinueLocation != null) {
            this.tinyAppContinueLocation.startContinuousLocation(this.mH5Event, this.mH5BridgeContext);
        }
    }

    public void setTinyAppContinueLocation(TinyAppContinueLocation tinyAppContinueLocation2) {
        this.tinyAppContinueLocation = tinyAppContinueLocation2;
    }
}
