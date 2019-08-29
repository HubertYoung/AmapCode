package com.alipay.android.phone.inside.api.accountopenauth;

import java.util.HashMap;
import java.util.Map;

public class AccountOAuthHelper {
    private static AccountOAuthHelper sInstance;
    private Object mLock = new Object();
    private Map<Long, String> mOAuthLoginTaskMap = new HashMap();

    private AccountOAuthHelper() {
    }

    public static AccountOAuthHelper getInstance() {
        if (sInstance == null) {
            synchronized (AccountOAuthHelper.class) {
                if (sInstance == null) {
                    sInstance = new AccountOAuthHelper();
                }
            }
        }
        return sInstance;
    }

    public Object getLock() {
        return this.mLock;
    }

    public Map<Long, String> getOAuthLoginTaskMap() {
        return this.mOAuthLoginTaskMap;
    }
}
