package com.ali.user.mobile.rpc.facade;

import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;

public interface RSAService {
    @OperationType("alipay.client.getRSAKey")
    RSAPKeyResult getRSAKey();
}
