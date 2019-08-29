package com.alipay.mobilecodec.biz.beacon;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobilecodec.biz.beacon.model.BeaconDeviceSyncRequest;
import com.alipay.mobilecodec.biz.beacon.model.BeaconRes;

public interface BeaconServiceFacade {
    @OperationType("alipay.mobilecodec.beaconReport")
    @SignCheck
    BeaconRes notifyBeaconDevice(BeaconDeviceSyncRequest beaconDeviceSyncRequest);
}
