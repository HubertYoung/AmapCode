package com.alibaba.baichuan.android.trade.adapter.security;

import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.adapter.ut.performance.Point4Init;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.InitResult;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdataencrypt.IDynamicDataEncryptComponent;
import com.alibaba.wireless.security.open.opensdk.IOpenSDKComponent;

public class AlibcSecurityGuard implements AlibcSecurity {
    private static AlibcSecurityGuard a = null;
    private static final String c = "AlibcSecurityGuard";
    private SecurityGuardManager b;
    private String d;
    private boolean e;

    private AlibcSecurityGuard() {
    }

    private Long a(String str, byte[] bArr) {
        try {
            IOpenSDKComponent openSDKComp = SecurityGuardManager.getInstance(AlibcContext.context).getOpenSDKComp();
            if (openSDKComp != null) {
                try {
                    return openSDKComp.analyzeOpenId(str, "AppIDKey", "OpenIDSaltKey", bArr, null);
                } catch (SecException e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        } catch (SecException unused) {
            return null;
        }
    }

    private void a(int i) {
        if (i != 212) {
            switch (i) {
                case 202:
                case 204:
                case 205:
                    break;
                case 203:
                    UserTrackerCompoment.sendUseabilityFailure(UserTrackerConstants.U_SECURITYGUARD_INIT, UserTrackerConstants.EM_NO_PICTURE);
                    return;
                default:
                    return;
            }
        }
        UserTrackerCompoment.sendUseabilityFailure(UserTrackerConstants.U_SECURITYGUARD_INIT, UserTrackerConstants.EM_WRONG_PICTURE);
    }

    public static synchronized AlibcSecurityGuard getInstance() {
        AlibcSecurityGuard alibcSecurityGuard;
        synchronized (AlibcSecurityGuard.class) {
            try {
                if (a == null) {
                    a = new AlibcSecurityGuard();
                }
                alibcSecurityGuard = a;
            }
        }
        return alibcSecurityGuard;
    }

    public Long analyzeItemId(String str) {
        return a(str, IOpenSDKComponent.OPEN_BIZ_IID);
    }

    public String dynamicDecrypt(String str) {
        if (this.b != null) {
            IDynamicDataEncryptComponent dynamicDataEncryptComp = this.b.getDynamicDataEncryptComp();
            if (dynamicDataEncryptComp != null) {
                try {
                    return dynamicDataEncryptComp.dynamicDecrypt(str);
                } catch (SecException e2) {
                    AlibcLogger.e(c, e2.toString());
                }
            }
        }
        return str;
    }

    public String dynamicEncrypt(String str) {
        if (this.b != null) {
            IDynamicDataEncryptComponent dynamicDataEncryptComp = this.b.getDynamicDataEncryptComp();
            if (dynamicDataEncryptComp != null) {
                try {
                    return dynamicDataEncryptComp.dynamicEncrypt(str);
                } catch (SecException e2) {
                    AlibcLogger.e(c, e2.toString());
                }
            }
        }
        return str;
    }

    public String getAppKey() {
        return this.d;
    }

    public synchronized InitResult init(Point4Init point4Init) {
        point4Init.timeBegin(UserTrackerConstants.PM_SECURITY_INIT_TIME);
        if (!this.e) {
            try {
                int initialize = SecurityGuardManager.getInitializer().initialize(AlibcContext.context);
                this.b = SecurityGuardManager.getInstance(AlibcContext.context);
                this.d = this.b.getStaticDataStoreComp().getAppKeyByIndex(0, null);
                if (initialize == 0) {
                    if (this.d != null) {
                        this.e = true;
                        point4Init.timeEnd(UserTrackerConstants.PM_SECURITY_INIT_TIME);
                        return InitResult.newSuccessResult();
                    }
                }
                a(initialize);
                AlibcLogger.e(c, "SecurityGuard init error : ".concat(String.valueOf(initialize)));
                return InitResult.newFailureResult(initialize, "SecurityGuard init error");
            } catch (SecException e2) {
                a(e2.getErrorCode());
                AlibcLogger.e(c, e2.getMessage());
                return InitResult.newFailureResult(e2.getErrorCode(), "SecurityGuard init error");
            }
        } else {
            return InitResult.newSuccessResult();
        }
    }
}
