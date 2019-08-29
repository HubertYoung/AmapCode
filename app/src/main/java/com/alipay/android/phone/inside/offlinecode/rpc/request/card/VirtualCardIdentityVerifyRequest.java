package com.alipay.android.phone.inside.offlinecode.rpc.request.card;

import com.alipay.android.phone.inside.offlinecode.rpc.request.base.AlipayInsideRPCRequestInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.request.base.BaseRPCRequestInfo;

public class VirtualCardIdentityVerifyRequest {
    public AlipayInsideRPCRequestInfo alipayInsideRPCRequestInfo;
    public BaseRPCRequestInfo baseRPCRequestInfo;
    public String bizId;
    public String cardNo;
    public String cardType;
    public boolean identityVerified = false;
    public String sceneCode;
    public String source;
    public String subSceneCode;
    public String verifyId;
}
