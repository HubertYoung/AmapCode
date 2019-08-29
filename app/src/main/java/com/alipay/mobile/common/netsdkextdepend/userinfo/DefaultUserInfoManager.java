package com.alipay.mobile.common.netsdkextdepend.userinfo;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.netsdkextdependapi.userinfo.UserInfoManagerAdapter;

public class DefaultUserInfoManager extends UserInfoManagerAdapter {
    public String getLastUserId() {
        return LoggerFactory.getLogContext().getUserId();
    }
}
