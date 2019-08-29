package com.ali.user.mobile.external.facade;

import com.ali.user.mobile.external.model.SuggestLoginUserResPb;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface PreUserInfoFacade {
    @SignCheck
    @OperationType("ali.user.gw.suggestLoginUser.pb")
    SuggestLoginUserResPb suggestLoginUserPb(Object obj);
}
