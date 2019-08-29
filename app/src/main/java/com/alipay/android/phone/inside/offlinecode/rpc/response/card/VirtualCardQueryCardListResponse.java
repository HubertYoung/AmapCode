package com.alipay.android.phone.inside.offlinecode.rpc.response.card;

import com.alipay.android.phone.inside.offlinecode.rpc.response.base.BaseRPCResponseInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.VirtualCardInfo;
import java.util.List;

public class VirtualCardQueryCardListResponse {
    public BaseRPCResponseInfo baseRPCResponseInfo;
    public List<VirtualCardInfo> virtualCardInfoList;
}
