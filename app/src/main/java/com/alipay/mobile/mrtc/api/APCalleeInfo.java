package com.alipay.mobile.mrtc.api;

import com.alipay.mobile.mrtc.api.enums.APCallType;

public class APCalleeInfo extends BaseCallInfo {
    public String remoteUserId;
    public String roomId;

    public APCalleeInfo() {
        this.callType = APCallType.CALL_TYPE_STOCK_CALLEE.getType();
    }

    public boolean isCaller() {
        return false;
    }

    public String toString() {
        return "APCallerInfo{remoteUserId='" + this.remoteUserId + '\'' + ", roomId='" + this.roomId + '\'' + '}' + super.toString();
    }
}
