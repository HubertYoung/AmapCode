package com.alipay.android.phone.inside.offlinecode.rpc.request.card;

import com.alipay.android.phone.inside.offlinecode.rpc.request.base.AlipayInsideRPCRequestInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.request.base.BaseRPCRequestInfo;

public class VirtualCardQueryCardDetailRequest {
    public AlipayInsideRPCRequestInfo alipayInsideRPCRequestInfo;
    public boolean autoDecide = false;
    public BaseRPCRequestInfo baseRPCRequestInfo;
    public String bizId;
    public String cardNo;
    public String cardType;
    public String sceneCode;
    public String source;
    public String subSceneCode;
}
