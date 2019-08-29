package com.alipay.android.phone.inside.offlinecode.rpc;

import com.alipay.android.phone.inside.offlinecode.rpc.request.CloseDeviceReqPB;
import com.alipay.android.phone.inside.offlinecode.rpc.response.MobileSecurityResultPB;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface MobileSecurityRpcFacade {
    @SignCheck
    @OperationType("alipay.mobile.security.otp.auth.device.close")
    MobileSecurityResultPB closeDevice(CloseDeviceReqPB closeDeviceReqPB);
}
