package com.alipay.mobile.accountopenauth.api.rpc.facade;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobile.accountopenauth.api.rpc.model.req.OauthConfirmReq;
import com.alipay.mobile.accountopenauth.api.rpc.model.req.OauthPrepareReq;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.OauthPrepareResult;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.TinyAppAuthExecuteResult;

public interface OauthServiceGw {
    @SignCheck
    @OperationType("alipay.member.authcenter.oauth.fast.confirm")
    TinyAppAuthExecuteResult confirm(OauthConfirmReq oauthConfirmReq);

    @SignCheck
    @OperationType("alipay.member.authcenter.oauth.fast.prepare")
    OauthPrepareResult prepare(OauthPrepareReq oauthPrepareReq);
}
