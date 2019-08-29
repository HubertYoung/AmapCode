package com.alipay.mobileapp.biz.rpc.taobao.login.vo;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;

public interface AliAutoLoginFacade {
    @OperationType("alipay.client.autologin")
    AutoLoginPbResPB taobaoAutoLogin(AutoLoginPbReqPB autoLoginPbReqPB);
}
