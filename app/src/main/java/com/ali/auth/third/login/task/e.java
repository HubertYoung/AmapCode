package com.ali.auth.third.login.task;

import android.text.TextUtils;
import com.ali.auth.third.core.broadcast.LoginAction;
import com.ali.auth.third.core.callback.FailureCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.service.impl.CredentialManager;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.login.a.a;
import com.ali.auth.third.login.callback.LogoutCallback;

class e implements Runnable {
    final /* synthetic */ LogoutCallback a;

    e(LogoutCallback logoutCallback) {
        this.a = logoutCallback;
    }

    public void run() {
        try {
            StringBuilder sb = new StringBuilder("into logout ");
            sb.append(CredentialManager.INSTANCE.getInternalSession().toString());
            SDKLogger.e("logout task", sb.toString());
            if (!TextUtils.isEmpty(CredentialManager.INSTANCE.getInternalSession().user.userId)) {
                LoginComponent loginComponent = LoginComponent.INSTANCE;
                LoginComponent.logout();
            }
            ResultCode logout = a.b.logout();
            if (ResultCode.SUCCESS.equals(logout)) {
                a.d.logout();
                CommonUtils.sendBroadcast(LoginAction.NOTIFY_LOGOUT);
                KernelContext.executorService.postUITask(new f(this));
                return;
            }
            CommonUtils.onFailure((FailureCallback) this.a, logout);
        } catch (Exception e) {
            e.printStackTrace();
            ResultCode logout2 = a.b.logout();
            if (ResultCode.SUCCESS.equals(logout2)) {
                a.d.logout();
                CommonUtils.sendBroadcast(LoginAction.NOTIFY_LOGOUT);
                KernelContext.executorService.postUITask(new f(this));
                return;
            }
            CommonUtils.onFailure((FailureCallback) this.a, logout2);
        } catch (Throwable th) {
            ResultCode logout3 = a.b.logout();
            if (ResultCode.SUCCESS.equals(logout3)) {
                a.d.logout();
                CommonUtils.sendBroadcast(LoginAction.NOTIFY_LOGOUT);
                KernelContext.executorService.postUITask(new f(this));
            } else {
                CommonUtils.onFailure((FailureCallback) this.a, logout3);
            }
            throw th;
        }
    }
}
