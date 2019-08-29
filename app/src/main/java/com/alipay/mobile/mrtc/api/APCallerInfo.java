package com.alipay.mobile.mrtc.api;

import com.alipay.mobile.mrtc.api.enums.APCallType;

public class APCallerInfo extends BaseCallInfo {
    public APCallerInfo() {
        this.callType = APCallType.CALL_TYPE_STOCK_CALLER.getType();
    }

    public boolean isCaller() {
        return true;
    }
}
