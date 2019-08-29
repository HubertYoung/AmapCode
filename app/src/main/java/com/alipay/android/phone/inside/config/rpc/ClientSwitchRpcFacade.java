package com.alipay.android.phone.inside.config.rpc;

import com.alipay.android.phone.inside.config.rpc.model.SwitchInfoReqPbPB;
import com.alipay.android.phone.inside.config.rpc.model.SwitchInfoRespPbPB;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface ClientSwitchRpcFacade {
    @SignCheck
    @OperationType("alipay.client.switches.all.get.inside")
    SwitchInfoRespPbPB getSwitchesPbInside(SwitchInfoReqPbPB switchInfoReqPbPB);
}
