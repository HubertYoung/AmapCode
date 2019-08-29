package com.alipay.mobile.common.rpc.protocol.json;

import android.text.TextUtils;

public class SimpleRpcJsonSerializerV2 extends JsonSerializerV2 {
    public SimpleRpcJsonSerializerV2(int id, String operationType, Object params) {
        super(id, operationType, params);
    }

    public String getRequestDataJson() {
        String rpcParam = String.valueOf(((Object[]) this.mParams)[1]);
        if (TextUtils.isEmpty(rpcParam)) {
            return "[]";
        }
        return rpcParam;
    }
}
