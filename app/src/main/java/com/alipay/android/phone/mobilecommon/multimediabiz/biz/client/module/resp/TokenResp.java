package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.Token;

public class TokenResp extends BaseResp {
    @JSONField(name = "data")
    private Token token;

    public Token getToken() {
        return this.token;
    }

    public void setToken(Token token2) {
        this.token = token2;
    }
}
