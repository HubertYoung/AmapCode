package com.alipay.mobile.tinyappcustom.h5plugin.navigation;

import com.alipay.inside.mobile.framework.service.annotation.CheckLogin;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface MiniAppRelationCheckRpcService {
    @CheckLogin
    @SignCheck
    @OperationType("alipay.openservice.mini.minirelation.check")
    MiniAppRelationCheckResultPB checkRelation(MiniAppRelationCheckRequestPB miniAppRelationCheckRequestPB);
}
