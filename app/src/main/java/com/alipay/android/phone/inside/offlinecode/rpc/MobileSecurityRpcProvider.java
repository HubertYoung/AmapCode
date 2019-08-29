package com.alipay.android.phone.inside.offlinecode.rpc;

import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.offlinecode.rpc.request.CloseDeviceReqPB;

public class MobileSecurityRpcProvider {
    public boolean closeBusCode() {
        CloseDeviceReqPB closeDeviceReqPB = new CloseDeviceReqPB();
        closeDeviceReqPB.scope = "auth_transport";
        closeDeviceReqPB.tid = RunningConfig.a(true);
        return ((MobileSecurityRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(MobileSecurityRpcFacade.class)).closeDevice(closeDeviceReqPB).success.booleanValue();
    }
}
