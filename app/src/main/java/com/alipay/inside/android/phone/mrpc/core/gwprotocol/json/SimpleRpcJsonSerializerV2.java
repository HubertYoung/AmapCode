package com.alipay.inside.android.phone.mrpc.core.gwprotocol.json;

import android.text.TextUtils;

public class SimpleRpcJsonSerializerV2 extends JsonSerializerV2 {
    public SimpleRpcJsonSerializerV2(int i, String str, Object obj) {
        super(i, str, obj);
    }

    public String getRequestDataJson() {
        String valueOf = String.valueOf(((Object[]) this.mParams)[1]);
        return TextUtils.isEmpty(valueOf) ? "[]" : valueOf;
    }
}
