package com.ali.user.mobile.rpc.vo.mobilegw;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface GetRSAPKeyApi {
    @SignCheck
    @OperationType("alipay.client.getRSAKey")
    RSAPKeyResult getRsaKey();
}
