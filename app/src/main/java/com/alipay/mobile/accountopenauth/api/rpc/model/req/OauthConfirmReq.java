package com.alipay.mobile.accountopenauth.api.rpc.model.req;

import java.util.Map;

public class OauthConfirmReq {
    public String contextToken;
    public String oauthScene;
    public Map<String, String> postParams;
}
