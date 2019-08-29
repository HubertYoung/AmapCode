package com.alipay.android.phone.inside.offlinecode.rpc.request.card;

import com.alipay.android.phone.inside.offlinecode.rpc.request.base.AlipayInsideRPCRequestInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.request.base.BaseRPCRequestInfo;

public class VirtualCardQueryCardDataRequest {
    public AlipayInsideRPCRequestInfo alipayInsideRPCRequestInfo;
    public BaseRPCRequestInfo baseRPCRequestInfo;
    public String bizId;
    public String cardNo;
    public String cardType;
    public String channelType;
    public String clientGencodeSDKVersion;
    public String sceneCode;
    public String source;
    public String subSceneCode;
    public boolean supportOfflineCrypto = false;
    public boolean syncQuery;
}
