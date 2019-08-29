package com.alipay.mobile.tinyappcustom.h5plugin.openauth;

import com.alipay.inside.mobile.framework.service.annotation.CheckLogin;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.WalletAuthExecuteRequestPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.WalletAuthSkipRequestPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.result.WalletAuthExecuteResultPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.result.WalletAuthSkipResultPB;

public interface Oauth2AuthCodeFacade {
    @CheckLogin
    @SignCheck
    @OperationType("alipay.openauthplatform.authcode.authSign")
    AuthSignRes authSign(AuthSignReq authSignReq);

    @CheckLogin
    @SignCheck
    @OperationType("alipay.openauthplatform.authcode.createAuthCodeUrl")
    WalletAuthCodeCreateRes createAuthCodeUrl(WalletAuthCodeCreateReq walletAuthCodeCreateReq);

    @CheckLogin
    @SignCheck
    @OperationType("com.openauthplatform.userauth.executeAuth")
    WalletAuthExecuteResultPB executeAuth(WalletAuthExecuteRequestPB walletAuthExecuteRequestPB);

    @CheckLogin
    @SignCheck
    @OperationType("com.openauthplatform.userauth.getAuthContentOrAutoAuth")
    WalletAuthSkipResultPB getAuthContentOrAutoAuth(WalletAuthSkipRequestPB walletAuthSkipRequestPB);
}
