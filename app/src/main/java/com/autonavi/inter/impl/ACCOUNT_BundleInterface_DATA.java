package com.autonavi.inter.impl;

import com.autonavi.bundle.account.api.IAccountService;
import java.util.HashMap;
import proguard.annotation.KeepName;

@KeepName
public final class ACCOUNT_BundleInterface_DATA extends HashMap {
    public ACCOUNT_BundleInterface_DATA() {
        put(IAccountService.class, aof.class);
    }
}
