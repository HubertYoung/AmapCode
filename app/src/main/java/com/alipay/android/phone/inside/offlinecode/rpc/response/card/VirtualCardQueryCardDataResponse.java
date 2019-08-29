package com.alipay.android.phone.inside.offlinecode.rpc.response.card;

import com.alipay.android.phone.inside.offlinecode.rpc.response.base.BaseRPCResponseInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.OfflineDataInfo;

public class VirtualCardQueryCardDataResponse {
    public BaseRPCResponseInfo baseRPCResponseInfo;
    public boolean enableOnsitePay = false;
    public boolean isFirstTime = false;
    public OfflineDataInfo offlineDataInfo;
}
