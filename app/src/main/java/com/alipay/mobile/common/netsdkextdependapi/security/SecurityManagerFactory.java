package com.alipay.mobile.common.netsdkextdependapi.security;

import com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory;
import com.alipay.mobile.common.netsdkextdependapi.BeanServiceConstants;
import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;

public class SecurityManagerFactory extends AbstraceExtBeanFactory<SecurityManager> {
    private static SecurityManagerFactory a = null;

    public static final SecurityManagerFactory getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (SecurityManagerFactory.class) {
            if (a != null) {
                SecurityManagerFactory securityManagerFactory = a;
                return securityManagerFactory;
            }
            a = new SecurityManagerFactory();
            return a;
        }
    }

    /* access modifiers changed from: protected */
    public SecurityManager newDefaultBean() {
        return (SecurityManager) InnerMiscUtil.newDefaultBean(BeanServiceConstants.securityManagerServiceName, SecurityManager.class);
    }

    /* access modifiers changed from: protected */
    public SecurityManager newBackupBean() {
        return new SecurityManagerAdapter();
    }
}
