package com.ali.user.mobile.rpc.facade;

import com.ali.user.mobile.rpc.vo.sso.UnifyVerifySSOTokenRequestPb;
import com.ali.user.mobile.rpc.vo.sso.UnifyVerifySSOTokenResultPb;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;

public interface CreateSsoTokenFacade {
    @OperationType("alipay.client.unifyVerifySsoToken.pb")
    UnifyVerifySSOTokenResultPb unifyVerifySsoTokenPb(UnifyVerifySSOTokenRequestPb unifyVerifySSOTokenRequestPb);
}
