package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.Token;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.TokenResp;

public interface TokenApi {

    public interface OnGotServerTimeListener {
        void onGotServerTime(long j);
    }

    Token getCurrentToken();

    TokenResp getToken(boolean z);

    String getTokenString();

    void refreshToken(Token token, long j);
}
