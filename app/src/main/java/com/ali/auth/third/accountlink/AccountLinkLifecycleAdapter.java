package com.ali.auth.third.accountlink;

import com.ali.auth.third.accountlink.a.a;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.Constants;
import com.ali.auth.third.core.service.CredentialService;
import com.ali.auth.third.core.service.MemberExecutorService;
import com.ali.auth.third.core.service.RpcService;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.login.RequestCode;
import com.ali.auth.third.login.handler.LoginActivityResultHandler;
import com.ali.auth.third.ui.support.ActivityResultHandler;
import java.util.Collections;
import java.util.Map;

public class AccountLinkLifecycleAdapter {
    public static final String TAG = "Member.AccountLinkLifecycleAdapter";

    public static void init() {
        SDKLogger.d(TAG, "AccountLinkLifecycleAdapter init ");
        a.c = (RpcService) KernelContext.getService(RpcService.class, null);
        a.b = (CredentialService) KernelContext.getService(CredentialService.class, null);
        a.d = (MemberExecutorService) KernelContext.getService(MemberExecutorService.class, null);
        a.e = (LoginService) KernelContext.getService(LoginService.class, null);
        LoginActivityResultHandler loginActivityResultHandler = new LoginActivityResultHandler();
        Map singletonMap = Collections.singletonMap(Constants.ISV_SCOPE_FLAG, "true");
        Class[] clsArr = {AccountLinkService.class};
        KernelContext.registerService(clsArr, new g(), singletonMap);
        KernelContext.registerService(new Class[]{ActivityResultHandler.class}, loginActivityResultHandler, Collections.singletonMap(ActivityResultHandler.REQUEST_CODE_KEY, String.valueOf(RequestCode.OPEN_H5_UNBIND)));
    }
}
