package com.alipay.mobile.securitycommon.aliauth;

import android.os.Bundle;

public class AliAuthService {
    private static AliAuthService a;
    private GeneralAuthWorker b;

    private AliAuthService() {
    }

    public static AliAuthService getService() {
        if (a == null) {
            synchronized (AliAuthService.class) {
                try {
                    if (a == null) {
                        a = new AliAuthService();
                    }
                }
            }
        }
        return a;
    }

    public void setAuthProvider(IAliAuthProvider iAliAuthProvider) {
        a().setAuthProvider(iAliAuthProvider);
    }

    public boolean canAutoLogin(String str) {
        return a().canAutoLogin(str);
    }

    public synchronized AliAuthResult autoLogin(Bundle bundle) {
        try {
        }
        return a().autoLogin(bundle);
    }

    private GeneralAuthWorker a() {
        if (this.b == null) {
            this.b = new GeneralAuthWorker();
        }
        return this.b;
    }

    public void clearCache(Bundle bundle) {
        a().clearCache(bundle);
    }
}
