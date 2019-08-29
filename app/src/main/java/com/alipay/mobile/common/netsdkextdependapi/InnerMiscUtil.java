package com.alipay.mobile.common.netsdkextdependapi;

import com.alipay.mobile.common.netsdkextdependapi.beaninfo.BeanInfoUtil;
import java.util.logging.Logger;

public final class InnerMiscUtil {
    public static Logger logger = Logger.getLogger(InnerMiscUtil.class.getName());

    public static final boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static final <T> T newDefaultBean(String beanKey, Class<T> serviceInterfaceClass) {
        String beanClassName = BeanInfoUtil.getBeanClassName(beanKey);
        if (isEmpty(beanClassName)) {
            return null;
        }
        return serviceInterfaceClass.getClassLoader().loadClass(beanClassName).newInstance();
    }
}
