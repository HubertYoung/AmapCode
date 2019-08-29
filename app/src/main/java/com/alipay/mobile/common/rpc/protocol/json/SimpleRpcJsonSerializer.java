package com.alipay.mobile.common.rpc.protocol.json;

import android.text.TextUtils;

public class SimpleRpcJsonSerializer extends JsonSerializer {
    public SimpleRpcJsonSerializer(int id, String operationType, Object params) {
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
