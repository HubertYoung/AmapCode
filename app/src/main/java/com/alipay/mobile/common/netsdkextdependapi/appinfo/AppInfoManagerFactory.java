package com.alipay.mobile.common.netsdkextdependapi.appinfo;

import com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory;
import com.alipay.mobile.common.netsdkextdependapi.BeanServiceConstants;
import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;

public class AppInfoManagerFactory extends AbstraceExtBeanFactory<AppInfoManager> {
    private static AppInfoManagerFactory a = null;

    public static final AppInfoManagerFactory getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (AppInfoManagerFactory.class) {
            if (a != null) {
                AppInfoManagerFactory appInfoManagerFactory = a;
                return appInfoManagerFactory;
            }
            a = new AppInfoManagerFactory();
            return a;
        }
    }

    /* access modifiers changed from: protected */
    public AppInfoManager newDefaultBean() {
        return (AppInfoManager) InnerMiscUtil.newDefaultBean(BeanServiceConstants.appInfoManagerServiceName, AppInfoManager.class);
    }

    /* access modifiers changed from: protected */
    public AppInfoManager newBackupBean() {
        return new AppInfoManagerAdapter();
    }
}
