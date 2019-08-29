package com.ali.auth.third.accountlink.b;

import android.app.Activity;
import com.ali.auth.third.core.callback.FailureCallback;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.login.task.AbsLoginByCodeTask;
import com.ali.auth.third.login.util.LoginStatus;

public class a extends AbsLoginByCodeTask {
    private LoginCallback a;

    public a(Activity activity, LoginCallback loginCallback) {
        super(activity);
        this.a = loginCallback;
    }

    public void doWhenException(Throwable th) {
        LoginStatus.resetLoginFlag();
        CommonUtils.onFailure((FailureCallback) this.a, ResultCode.create(10010, th.getMessage()));
    }

    public void doWhenResultFail(int i, String str) {
        if (this.a != null) {
            this.a.onFailure(i, str);
        }
    }

    public void doWhenResultOk() {
        LoginStatus.resetLoginFlag();
        if (this.a != null) {
            this.a.onSuccess(com.ali.auth.third.accountlink.a.a.b.getSession());
        }
    }

    public RpcResponse<LoginReturnData> login(String[] strArr) {
        return com.ali.auth.third.accountlink.a.a.a(strArr[0], strArr[1]);
    }
}
