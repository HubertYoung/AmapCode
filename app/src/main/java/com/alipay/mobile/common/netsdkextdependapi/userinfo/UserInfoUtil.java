package com.alipay.mobile.common.netsdkextdependapi.userinfo;

public class UserInfoUtil {
    public static final String getLastUserId() {
        return ((UserInfoManager) UserInfoManagerFactory.getInstance().getDefaultBean()).getLastUserId();
    }
}
