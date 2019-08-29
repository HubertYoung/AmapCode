package com.alipay.mobile.common.netsdkextdependapi.beaninfo;

public final class BeanInfoUtil {
    public static final String getBeanClassName(String key) {
        return ((BeanInfoManager) BeanInfoManagerFactory.getInstance().getDefaultBean()).getBeanClassName(key);
    }
}
