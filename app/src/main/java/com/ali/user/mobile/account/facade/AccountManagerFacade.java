package com.ali.user.mobile.account.facade;

import com.ali.user.mobile.account.bean.Tid;
import com.ali.user.mobile.account.model.MobileSecurityResult;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import java.util.List;

public interface AccountManagerFacade {
    @OperationType("alipay.mobile.security.accountmanager.clearAccount")
    MobileSecurityResult clearAccount(Tid tid, Tid tid2, String str, String str2, String str3, String str4);

    @OperationType("alipay.mobile.security.accountmanager.updateWalletLoginAuth")
    MobileSecurityResult updateWalletLoginAuth(Tid tid, List<String> list);
}
