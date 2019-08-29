package com.alipay.android.phone.inside.commonbiz.report.facade;

import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationReqPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationResPbPB;
import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;

public interface DeviceLocationFacadeForAlipay extends DeviceLocationFacade {
    @OperationType("alipay.alideviceinfo.reportDeviceLocation.pb")
    @SignCheck
    DeviceLocationResPbPB reportDeviceLocationPb(DeviceLocationReqPbPB deviceLocationReqPbPB);
}
