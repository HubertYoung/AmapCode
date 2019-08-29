package com.alipay.android.phone.inside.offlinecode.rpc.request.card;

import com.alipay.android.phone.inside.offlinecode.rpc.request.base.AlipayInsideRPCRequestInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.request.base.BaseRPCRequestInfo;

public class VirtualCardQueryCardListRequest {
    public static final String LIST_TYPE_ALL = "ALL";
    public static final String LIST_TYPE_ISSUABLE = "ISSUABLE";
    public static final String LIST_TYPE_ISSUED = "ISSUED";
    public AlipayInsideRPCRequestInfo alipayInsideRPCRequestInfo;
    public BaseRPCRequestInfo baseRPCRequestInfo;
    public String bizId;
    public String cardNo;
    public String cardType;
    public String listType;
    public String sceneCode;
    public String source;
    public String subSceneCode;
}
