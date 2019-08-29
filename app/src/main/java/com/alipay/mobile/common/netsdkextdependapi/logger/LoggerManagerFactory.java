package com.alipay.mobile.common.netsdkextdependapi.logger;

import com.alipay.mobile.common.netsdkextdependapi.AbstraceExtBeanFactory;
import com.alipay.mobile.common.netsdkextdependapi.BeanServiceConstants;
import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;

public class LoggerManagerFactory extends AbstraceExtBeanFactory<LoggerManager> {
    private static LoggerManagerFactory a = null;

    public static final LoggerManagerFactory getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (LoggerManagerFactory.class) {
            try {
                if (a != null) {
                    LoggerManagerFactory loggerManagerFactory = a;
                    return loggerManagerFactory;
                }
                a = new LoggerManagerFactory();
                return a;
            }
        }
    }

    /* access modifiers changed from: protected */
    public LoggerManager newDefaultBean() {
        return (LoggerManager) InnerMiscUtil.newDefaultBean(BeanServiceConstants.loggerInfoManagerServiceName, LoggerManager.class);
    }

    /* access modifiers changed from: protected */
    public LoggerManager newBackupBean() {
        return new LoggerManagerAdapter();
    }
}
