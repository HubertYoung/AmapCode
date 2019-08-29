package com.alipay.android.phone.inside.api.accountopenauth;

public class AccountOAuthServiceManager {
    private static AccountOAuthServiceManager sInstance;
    private IAccountOAuthService mActiveOAuthService;
    private IFastOAuthService mFastOAuthService;
    private IAccountOAuthService mOAuthService;

    private AccountOAuthServiceManager() {
    }

    public static AccountOAuthServiceManager getInstance() {
        if (sInstance == null) {
            synchronized (AccountOAuthServiceManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new AccountOAuthServiceManager();
                    }
                }
            }
        }
        return sInstance;
    }

    public IAccountOAuthService getOAuthService() {
        return this.mOAuthService;
    }

    public void setOAuthService(IAccountOAuthService iAccountOAuthService) {
        this.mOAuthService = iAccountOAuthService;
    }

    public void setActiveOAuthService(IAccountOAuthService iAccountOAuthService) {
        this.mActiveOAuthService = iAccountOAuthService;
    }

    public IAccountOAuthService getActiveOAuthService() {
        return this.mActiveOAuthService;
    }

    public IFastOAuthService getFastOAuthService() {
        return this.mFastOAuthService;
    }

    public void setFastOAuthService(IFastOAuthService iFastOAuthService) {
        this.mFastOAuthService = iFastOAuthService;
    }
}
