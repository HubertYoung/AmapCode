package com.alipay.android.phone.inside.commonbiz.report.facade;

import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationReqPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationResPbPB;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface DeviceLocationFacadeForSdk extends DeviceLocationFacade {
    @SignCheck
    @OperationType("alipay.alideviceinfo.reportDeviceLocation.pb")
    DeviceLocationResPbPB reportDeviceLocationPb(DeviceLocationReqPbPB deviceLocationReqPbPB);
}
