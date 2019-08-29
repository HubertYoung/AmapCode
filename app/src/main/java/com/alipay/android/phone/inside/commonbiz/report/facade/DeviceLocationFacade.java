package com.alipay.android.phone.inside.commonbiz.report.facade;

import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationReqPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationResPbPB;

public interface DeviceLocationFacade {
    DeviceLocationResPbPB reportDeviceLocationPb(DeviceLocationReqPbPB deviceLocationReqPbPB);
}
