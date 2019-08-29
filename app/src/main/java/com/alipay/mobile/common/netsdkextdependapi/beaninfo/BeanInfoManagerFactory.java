package com.alipay.mobile.common.netsdkextdependapi.beaninfo;

import com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory;

public class BeanInfoManagerFactory extends AbstraceExtBeanFactory<BeanInfoManager> {
    private static BeanInfoManagerFactory a = null;

    public static final BeanInfoManagerFactory getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (BeanInfoManagerFactory.class) {
            try {
                if (a != null) {
                    BeanInfoManagerFactory beanInfoManagerFactory = a;
                    return beanInfoManagerFactory;
                }
                a = new BeanInfoManagerFactory();
                return a;
            }
        }
    }

    protected BeanInfoManagerFactory() {
    }

    /* access modifiers changed from: protected */
    public BeanInfoManager newDefaultBean() {
        return (BeanInfoManager) Class.forName("com.alipay.mobile.common.netsdkextdepend.beaninfo.DefaultBeanInfoManager").newInstance();
    }

    /* access modifiers changed from: protected */
    public BeanInfoManager newBackupBean() {
        return new BeanInfoManagerAdapter();
    }
}
