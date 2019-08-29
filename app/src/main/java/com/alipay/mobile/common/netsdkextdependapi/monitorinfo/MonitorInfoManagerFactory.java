package com.alipay.mobile.common.netsdkextdependapi.monitorinfo;

import com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory;
import com.alipay.mobile.common.netsdkextdependapi.BeanServiceConstants;
import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;

public class MonitorInfoManagerFactory extends AbstraceExtBeanFactory<MonitorInfoManager> {
    private static MonitorInfoManagerFactory a = null;

    public static final MonitorInfoManagerFactory getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (MonitorInfoManagerFactory.class) {
            if (a != null) {
                MonitorInfoManagerFactory monitorInfoManagerFactory = a;
                return monitorInfoManagerFactory;
            }
            a = new MonitorInfoManagerFactory();
            return a;
        }
    }

    /* access modifiers changed from: protected */
    public MonitorInfoManager newDefaultBean() {
        return (MonitorInfoManager) InnerMiscUtil.newDefaultBean(BeanServiceConstants.monitorInfoManagerServiceName, MonitorInfoManager.class);
    }

    /* access modifiers changed from: protected */
    public MonitorInfoManager newBackupBean() {
        return new MonitorInfoManagerAdapter();
    }
}
