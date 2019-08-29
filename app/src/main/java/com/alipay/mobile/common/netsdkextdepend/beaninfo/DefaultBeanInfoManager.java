package com.alipay.mobile.common.netsdkextdepend.beaninfo;

import com.alipay.mobile.common.netsdkextdepend.appinfo.DefaultAppInfoManager;
import com.alipay.mobile.common.netsdkextdepend.deviceinfo.DefaultDeviceInfoManager;
import com.alipay.mobile.common.netsdkextdepend.logger.DefaultLoggerManager;
import com.alipay.mobile.common.netsdkextdepend.monitorinfo.DefaultMonitorInfoManager;
import com.alipay.mobile.common.netsdkextdepend.security.DefaultSecurityManager;
import com.alipay.mobile.common.netsdkextdepend.userinfo.DefaultUserInfoManager;
import com.alipay.mobile.common.netsdkextdependapi.BeanServiceConstants;
import com.alipay.mobile.common.netsdkextdependapi.beaninfo.BeanInfoManagerAdapter;
import java.util.HashMap;
import java.util.Map;

public class DefaultBeanInfoManager extends BeanInfoManagerAdapter {
    private static final Map<String, String> a;

    static {
        HashMap hashMap = new HashMap(6);
        a = hashMap;
        hashMap.put(BeanServiceConstants.loggerInfoManagerServiceName, DefaultLoggerManager.class.getName());
        a.put(BeanServiceConstants.deviceInfoManagerServiceName, DefaultDeviceInfoManager.class.getName());
        a.put(BeanServiceConstants.appInfoManagerServiceName, DefaultAppInfoManager.class.getName());
        a.put(BeanServiceConstants.userInfoManagerServiceName, DefaultUserInfoManager.class.getName());
        a.put(BeanServiceConstants.monitorInfoManagerServiceName, DefaultMonitorInfoManager.class.getName());
        a.put(BeanServiceConstants.securityManagerServiceName, DefaultSecurityManager.class.getName());
    }

    public String getBeanClassName(String beanServiceName) {
        return a.get(beanServiceName);
    }
}
