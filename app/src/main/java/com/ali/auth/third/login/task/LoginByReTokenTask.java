package com.ali.auth.third.login.task;

import android.app.Activity;
import android.text.TextUtils;
import com.ali.auth.third.core.callback.FailureCallback;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.login.UTConstants;
import com.ali.auth.third.login.a.a;
import com.ali.auth.third.login.util.LoginStatus;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import java.util.HashMap;

public class LoginByReTokenTask extends AbsLoginByCodeTask {
    private LoginCallback a;

    public LoginByReTokenTask(Activity activity, LoginCallback loginCallback) {
        super(activity);
        this.a = loginCallback;
    }

    public void doWhenException(Throwable th) {
        LoginStatus.resetLoginFlag();
        HashMap hashMap = new HashMap();
        hashMap.put("code", H5AppPrepareData.PREPARE_TIMEOUT);
        hashMap.put("message", LogCategory.CATEGORY_EXCEPTION);
        ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_AUTO_LOGIN_FAILURE, hashMap);
        CommonUtils.onFailure((FailureCallback) this.a, ResultCode.create(10010, th.getMessage()));
    }

    /* access modifiers changed from: protected */
    public void doWhenResultFail(int i, String str) {
        if (this.a != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("code", String.valueOf(i));
            if (!TextUtils.isEmpty(str)) {
                hashMap.put("message", str);
            }
            ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_AUTO_LOGIN_FAILURE, hashMap);
            if (this.a != null) {
                this.a.onFailure(i, str);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doWhenResultOk() {
        LoginStatus.resetLoginFlag();
        if (this.a != null) {
            ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_AUTO_LOGIN_SUCCESS, null);
            this.a.onSuccess(a.b.getSession());
        }
    }

    /* access modifiers changed from: protected */
    public RpcResponse<LoginReturnData> login(String[] strArr) {
        LoginComponent loginComponent = LoginComponent.INSTANCE;
        return LoginComponent.loginByRefreshToken();
    }
}
