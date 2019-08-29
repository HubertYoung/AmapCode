package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ServerAddress.ServerType;

public class TokenApiInfo extends BaseApiInfo {
    public static TokenApiInfo GET_TOKEN = new TokenApiInfo(ServerType.API, "rest/1.1/token", HttpMethod.GET);

    public TokenApiInfo(ServerType serverType, String apiPath, HttpMethod httpMethod) {
        super(serverType, apiPath, httpMethod);
    }
}
