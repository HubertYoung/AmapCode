package com.alipay.mobile.framework.service;

import android.util.Log;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.framework.service.common.TimeService;
import com.alipay.mobile.framework.service.common.impl.TimeServiceImpl;
import com.alipay.mobile.framework.service.common.loader.CommonServiceLoadAgent;
import com.alipay.mobile.liteprocess.LiteProcessPipeline;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientServicesLoader extends CommonServiceLoadAgent {
    public static final String TAG = "ClientServicesLoader";
    private static AtomicBoolean a = new AtomicBoolean(false);
    private static volatile boolean b = false;

    public void preLoad() {
        try {
            LiteProcessPipeline.registerAdvice(this.mMicroAppContext.getApplicationContext());
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, Log.getStackTraceString(e));
        }
    }

    public void postLoad() {
        registerLazyService(TimeService.class.getName(), TimeServiceImpl.class.getName());
    }

    public void afterBootLoad() {
        if (!b) {
            b = true;
            super.afterBootLoad();
            this.mMicroAppContext.findServiceByInterface(ConfigService.class.getName());
            this.mMicroAppContext.findServiceByInterface(SchemeService.class.getName());
            this.mMicroAppContext.findServiceByInterface(TimeService.class.getName());
        }
        synchronized (a) {
            if (!a.get()) {
                a.set(true);
            }
        }
    }
}
