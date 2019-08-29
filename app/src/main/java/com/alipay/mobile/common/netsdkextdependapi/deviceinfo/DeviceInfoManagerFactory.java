package com.alipay.mobile.common.netsdkextdependapi.deviceinfo;

import com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory;
import com.alipay.mobile.common.netsdkextdependapi.BeanServiceConstants;
import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;

public class DeviceInfoManagerFactory extends AbstraceExtBeanFactory<DeviceInfoManager> {
    private static DeviceInfoManagerFactory a = null;

    public static final DeviceInfoManagerFactory getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (DeviceInfoManagerFactory.class) {
            if (a != null) {
                DeviceInfoManagerFactory deviceInfoManagerFactory = a;
                return deviceInfoManagerFactory;
            }
            a = new DeviceInfoManagerFactory();
            return a;
        }
    }

    protected DeviceInfoManagerFactory() {
    }

    /* access modifiers changed from: protected */
    public DeviceInfoManager newDefaultBean() {
        return (DeviceInfoManager) InnerMiscUtil.newDefaultBean(BeanServiceConstants.deviceInfoManagerServiceName, DeviceInfoManager.class);
    }

    /* access modifiers changed from: protected */
    public DeviceInfoManager newBackupBean() {
        return new DeviceInfoManagerAdapter();
    }
}
