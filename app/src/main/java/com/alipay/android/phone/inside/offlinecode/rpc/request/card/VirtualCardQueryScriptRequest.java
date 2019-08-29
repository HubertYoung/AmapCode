package com.alipay.android.phone.inside.offlinecode.rpc.request.card;

import com.alipay.android.phone.inside.offlinecode.rpc.ScardCenterRpcProvider;
import com.alipay.android.phone.inside.offlinecode.rpc.request.base.BaseRPCRequestInfo;

public class VirtualCardQueryScriptRequest {
    public BaseRPCRequestInfo baseRPCRequestInfo;
    public String bizId;
    public String sceneCode = ScardCenterRpcProvider.REQ_VALUE_SCENECODE;
    public String scriptEngineVersion = "0.0.5";
    public String scriptName;
    public String scriptType;
}
