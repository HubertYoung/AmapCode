package com.alipay.mobile.common.netsdkextdependapi.userinfo;

import com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory;
import com.alipay.mobile.common.netsdkextdependapi.BeanServiceConstants;
import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;

public class UserInfoManagerFactory extends AbstraceExtBeanFactory<UserInfoManager> {
    private static UserInfoManagerFactory a = null;

    public static final UserInfoManagerFactory getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (UserInfoManagerFactory.class) {
            if (a != null) {
                UserInfoManagerFactory userInfoManagerFactory = a;
                return userInfoManagerFactory;
            }
            a = new UserInfoManagerFactory();
            return a;
        }
    }

    /* access modifiers changed from: protected */
    public UserInfoManager newDefaultBean() {
        return (UserInfoManager) InnerMiscUtil.newDefaultBean(BeanServiceConstants.userInfoManagerServiceName, UserInfoManager.class);
    }

    /* access modifiers changed from: protected */
    public UserInfoManager newBackupBean() {
        return new UserInfoManagerAdapter();
    }
}
