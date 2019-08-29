package com.alipay.mobile.security.bio.service.local.monitorlog;

import android.content.Context;
import com.alipay.mobile.security.bio.log.BehaviourIdEnum;
import com.alipay.mobile.security.bio.log.VerifyBehavior;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.LocalService;
import java.util.Map;

public abstract class MonitorLogService extends LocalService {
    protected static boolean sInitialized = false;

    public abstract void logBehavior(BehaviourIdEnum behaviourIdEnum, VerifyBehavior verifyBehavior);

    public void install(Context context) {
    }

    public void onCreate(BioServiceManager bioServiceManager) {
        super.onCreate(bioServiceManager);
        if (!sInitialized && bioServiceManager != null) {
            Context bioApplicationContext = bioServiceManager.getBioApplicationContext();
            if (bioApplicationContext != null) {
                install(bioApplicationContext);
                sInitialized = true;
            }
        }
    }

    public void trigUpload() {
    }

    public void keyBizTrace(String str, String str2, String str3, Map<String, String> map) {
    }
}
