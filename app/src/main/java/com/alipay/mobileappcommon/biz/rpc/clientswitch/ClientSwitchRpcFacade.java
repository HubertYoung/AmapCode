package com.alipay.mobileappcommon.biz.rpc.clientswitch;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb.SwitchInfoReqPb;
import com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb.SwitchInfoRespPb;

public interface ClientSwitchRpcFacade {
    @OperationType("alipay.client.switches.all.get.afterloginPb")
    @SignCheck
    SwitchInfoRespPb getSwitchesPbAfterLogin(SwitchInfoReqPb switchInfoReqPb);
}
