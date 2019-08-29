package com.ali.auth.third.mtop.rpc;

import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.util.ReflectionUtils;
import com.taobao.tao.remotebusiness.login.IRemoteLogin;
import com.taobao.tao.remotebusiness.login.LoginContext;
import com.taobao.tao.remotebusiness.login.onLoginListener;

public class MtopRemoteLoginImpl implements IRemoteLogin {
    public LoginContext getLoginContext() {
        LoginContext loginContext = new LoginContext();
        try {
            loginContext.nickname = KernelContext.credentialService.getSession().nick;
        } catch (Exception unused) {
        }
        return loginContext;
    }

    public boolean isLogining() {
        try {
            Object invoke = ReflectionUtils.invoke("com.ali.auth.third.login.util.LoginStatus", "isLogining", null, Class.forName("com.ali.auth.third.login.util.LoginStatus").newInstance(), null);
            if (invoke != null && (invoke instanceof Boolean)) {
                return ((Boolean) invoke).booleanValue();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public boolean isSessionValid() {
        return KernelContext.credentialService.isSessionValid();
    }

    public void login(onLoginListener onloginlistener, boolean z) {
        b bVar = new b(this, onloginlistener);
        try {
            ReflectionUtils.invoke("com.ali.auth.third.login.LoginServiceImpl", "auth", new String[]{"com.ali.auth.third.core.callback.LoginCallback"}, Class.forName("com.ali.auth.third.login.LoginServiceImpl").newInstance(), new Object[]{bVar});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InstantiationException e3) {
            e3.printStackTrace();
        }
    }
}
